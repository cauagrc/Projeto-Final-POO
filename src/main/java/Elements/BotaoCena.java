package Elements;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BotaoCena extends Button{
    private Stage janelaPrincipal;

    public BotaoCena() {
        super();
    }
    
    public BotaoCena(Stage janelaPrincipal) {
    	super();
        this.janelaPrincipal = janelaPrincipal;
    }

    
    public void mudarPara(Scene novaCena) {
        if (novaCena != null && janelaPrincipal != null) {
            janelaPrincipal.setScene(novaCena);
            janelaPrincipal.show();
        } else {
            System.err.println("Erro: Janela ou Nova Cena estão nulas!");
        }
    }
}
