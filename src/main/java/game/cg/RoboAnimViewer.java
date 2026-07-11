package game.cg;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelBuffer;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

/**
 * Janela de visualização do RoboAnim, capaz de executar uma sequência de
 * exemplos (scripts) um após o outro.
 *
 * Cada exemplo roda numa CENA LIMPA: antes de carregar o próximo script,
 * o contexto C++ é recriado do zero (armazém vazio, esteira IN recarregada
 * com os objetos de demonstração, robô na posição inicial). Isso é
 * necessário porque loadScript() apenas troca o script/estado de execução
 * — ele NÃO reseta a cena (ver RoboAnimation.h). Sem essa recriação, as
 * variáveis e os objetos enviados de um exemplo "vazariam" para o
 * seguinte, deixando a cena acumulada.
 *
 * A exibição usa DOUBLE BUFFERING: mantemos dois ByteBuffers/PixelBuffers/
 * WritableImages e alternamos qual deles o C++ escreve a cada frame. O
 * JavaFX (thread QuantumRenderer) lê o buffer recém-apresentado enquanto o
 * próximo frame já é escrito no OUTRO buffer — evitando a corrida de
 * leitura/escrita sobre um único buffer, que causava tearing/flicker.
 */
public class RoboAnimViewer extends Stage {

    /** Um exemplo a ser executado: um rótulo (exibido na tela) + o script em si. */
    public static final class Example {
        final String label;
        final String script;

        public Example(String label, String script) {
            this.label = label;
            this.script = script;
        }
    }

    // Tempo de espera, em nanossegundos, depois que um exemplo termina
    // antes de carregar o próximo — dá tempo do usuário perceber que
    // aquele exemplo concluiu antes da tela mudar.
    //
    // Observação: o lado C++ agora ANIMA o movimento do robô ao longo do
    // tempo (ele se desloca pela cena, pega/larga o objeto), então cada
    // comando se distribui por vários step(). Basta chamar step() a cada
    // frame com o deltaTime real (ver handle()).
    private static final long SETTLE_NANOS = 1_500_000_000L; // 1.5s

    private final RoboAnimJNI api;
    private final int width;
    private final int height;

    // Double buffering: [0] e [1] alternam entre "sendo exibido" e
    // "sendo escrito pelo C++".
    private final ByteBuffer[] byteBuffers = new ByteBuffer[2];
    private final PixelBuffer<ByteBuffer>[] pixelBuffers = new PixelBuffer[2];
    private final WritableImage[] images = new WritableImage[2];
    private final ImageView imageView;
    private int writeIndex = 0;

    private final Label statusLabel;

    private final List<Example> examples;
    private int currentExampleIndex = -1;

    // Contexto C++ (RoboAnimContext*) do exemplo atual; recriado a cada
    // exemplo para resetar a cena. 0 = nenhum contexto vivo.
    private long ctx = 0;

    private AnimationTimer timer;
    private long lastFrameTime = 0;
    private long settleTimer = 0;
    private boolean waitingToAdvance = false;

    /**
     * @param examples Exemplos a executar em sequência (ao menos um).
     * @param width    Largura da janela/framebuffer.
     * @param height   Altura da janela/framebuffer.
     */
    @SuppressWarnings("unchecked")
    public RoboAnimViewer(List<Example> examples, int width, int height) {
        if (examples == null || examples.isEmpty()) {
            throw new IllegalArgumentException("E preciso fornecer ao menos um exemplo");
        }
        this.examples = examples;
        this.width = width;
        this.height = height;

        setTitle("Visualizacao - RoboAnim");

        api = new RoboAnimJNI();

        // Aloca os DOIS buffers diretos e conecta cada um ao sistema de
        // imagem do JavaFX (BGRA pre-multiplicado).
        final PixelFormat<ByteBuffer> format = PixelFormat.getByteBgraPreInstance();
        for (int i = 0; i < 2; i++) {
            byteBuffers[i] = ByteBuffer.allocateDirect(width * height * 4);
            byteBuffers[i].order(ByteOrder.nativeOrder());
            pixelBuffers[i] = new PixelBuffer<>(width, height, byteBuffers[i], format);
            images[i] = new WritableImage(pixelBuffers[i]);
        }

        imageView = new ImageView(images[0]);

        // Rotulo indicando qual exemplo esta rodando no momento
        statusLabel = new Label();
        statusLabel.setTextFill(Color.WHITE);
        statusLabel.setStyle("-fx-font-size: 16px; -fx-font-family: monospace; "
            + "-fx-background-color: rgba(0,0,0,0.45); -fx-padding: 6 10 6 10; -fx-background-radius: 4;");
        StackPane.setAlignment(statusLabel, Pos.TOP_LEFT);
        StackPane.setMargin(statusLabel, new Insets(12));

        StackPane root = new StackPane(imageView, statusLabel);
        root.setStyle("-fx-background-color: #1a1a1e;");
        Scene scene = new Scene(root, width, height);
        setScene(scene);

        setResizable(false);
        setOnCloseRequest(event -> fecharVisualizacao());

        advanceToNextExample(); // cria o 1o contexto e carrega o 1o script
        iniciarAnimacao();
    }

    /**
     * Carrega o próximo exemplo da lista numa cena limpa, ou fecha a janela
     * se não houver mais nenhum. Recria o contexto C++ (destrói o anterior)
     * para garantir que a cena comece do zero.
     */
    private void advanceToNextExample() {
        currentExampleIndex++;
        if (currentExampleIndex >= examples.size()) {
            fecharVisualizacao();
            return;
        }

        // Reseta a cena recriando o contexto: armazém vazio, esteira IN
        // recarregada, robô na posição inicial. (loadScript sozinho não
        // reseta a cena — ver RoboAnimation.h.)
        if (ctx != 0) {
            api.destroy(ctx);
            ctx = 0;
        }
        ctx = api.create(width, height);
        if (ctx == 0) {
            throw new RuntimeException("Falha ao (re)inicializar o motor 3D em C++.");
        }

        Example example = examples.get(currentExampleIndex);
        System.out.println("== " + example.label + " ==");
        System.out.println(example.script);

        if (!api.loadScript(ctx, example.script)) {
            System.err.println("Erro no script '" + example.label + "': " + api.getLastError(ctx));
        }

        updateStatusLabel();
        waitingToAdvance = false;
    }

    private void updateStatusLabel() {
        final String text = String.format("Exemplo %d/%d: %s",
            currentExampleIndex + 1, examples.size(), examples.get(currentExampleIndex).label);
        Platform.runLater(() -> statusLabel.setText(text));
    }

    private void iniciarAnimacao() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastFrameTime == 0) {
                    lastFrameTime = now;
                    return;
                }

                final long elapsedNanos = now - lastFrameTime;
                lastFrameTime = now;

                if (!api.isFinished(ctx)) {
                    // Avanca a animacao com o deltaTime real do frame.
                    final float stepDeltaTime = elapsedNanos / 1_000_000_000.0f;
                    api.step(ctx, stepDeltaTime);

                    // Checa e avisa imediatamente se o C++ encontrar um erro.
                    final String erro = api.getLastError(ctx);
                    if (erro != null) {
                        System.err.println("[ERRO C++] " + erro);
                    }

                    // Renderiza no buffer "de escrita" e o apresenta. O
                    // outro buffer (o que estava na tela) fica livre para o
                    // QuantumRenderer terminar de ler sem corrida.
                    final int idx = writeIndex;
                    api.render(ctx, byteBuffers[idx], true);
                    pixelBuffers[idx].updateBuffer(pb -> (Rectangle2D) null);
                    imageView.setImage(images[idx]);
                    writeIndex ^= 1; // alterna para o proximo frame
                } else if (!waitingToAdvance) {
                    waitingToAdvance = true;
                    settleTimer = 0;
                } else {
                    settleTimer += elapsedNanos;
                    if (settleTimer >= SETTLE_NANOS) {
                        advanceToNextExample();
                    }
                }
            }
        };
        timer.start();
    }

    private void fecharVisualizacao() {
        if (timer != null) {
            timer.stop();
        }
        if (ctx != 0) {
            api.destroy(ctx);
            ctx = 0;
        }
        Platform.runLater(this::close);
        System.out.println("Visualizacao finalizada e recursos C++ liberados.");
    }
}
