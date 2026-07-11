package game.poo.fases;

import java.net.URL;
import java.util.ResourceBundle;

import Elements.Interpretador;
import game.poo.controllers.FaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Tutorial8 extends FaseController {
	
	@FXML
	Label descricao;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		definirBotoes("/game/poo/fxml/Fases/grupoFase1.fxml");
		descricao.setText(""
				+ "Já sabemos como mostrar informações no terminal utilizando o printf(). Agora precisamos aprender como receber informações do usuário."
				+ "\n"
				+ "\n"
				+ "Para isso utilizamos a função scanf(). Ela realiza a leitura de um valor digitado pelo usuário e o armazena dentro de uma variável."
				+ "\n"
				+ "\n"
				+ "Assim como no printf(), também devemos informar o especificador correspondente ao tipo da variável."
				+ "\n"
				+ "\n"
				+ "Um detalhe muito importante é o símbolo &, colocado antes do nome da variável."
				+ "\n"
				+ "\n"
				+ "Esse símbolo informa ao programa onde o valor digitado deve ser armazenado. Se ele for esquecido, o programa normalmente será compilado, mas o valor digitado não será armazenado corretamente, podendo causar comportamentos inesperados durante a execução."
				+ "\n"
				+ "\n"
				+ "Observe o exemplo:"
				+ "\n"
				+ "\n"
				+ "scanf(\"%d\", &numero);"
				+ "\n"
				+ "\n"
				+ "Nesse exemplo: %d informa que será lido um número inteiro; &numero indica que o valor digitado deve ser armazenado na variável numero."
				+ "\n"
				+ "Agora pratique! Crie uma variável inteira, utilize o scanf() para ler um valor digitado pelo usuário e, em seguida, utilize o printf() para imprimir o valor armazenado."
		);
	}

	@Override
	public void chamarInterpretador() {
		String cod = codigo.getText();

		try {
			int linhaInicio = 0;
			int linhaFinal = 0;
			int quantidadeInstrucoes = 0;
			boolean declaracaoConcluida = false;
			boolean entradaConcluida = false;
			boolean saidaConcluida = false;
			boolean returnEncontrado = false;

			Interpretador.limparVariaveis();

			String[] linhas = cod.split("\\R");

			int resultadoMain = Interpretador.verificarMain(cod);

			if (resultadoMain == 1) throw new IllegalArgumentException("A sua função principal nao foi encontrada ou esta mal declarada.");

			if (resultadoMain == 2) throw new IllegalArgumentException("Houve mais de uma declaracao de main no seu codigo.");

			Interpretador.verificarChaves(linhas);

			for (String linha : linhas) {
				if (Interpretador.verificarAbertura(linha)) break;
				linhaInicio++;
			}

			linhaInicio++;

			if (Interpretador.verificarFechamento(linhas[linhas.length - 1])) linhaFinal = linhas.length - 1;

			for (int i = linhaInicio; i < linhaFinal; i++) {
				String linha = linhas[i].trim();

				if (linha.isBlank()) {
					continue;
				}

				if (returnEncontrado) {
					throw new IllegalArgumentException("Linha " + (i + 1) + ": esta instrucao nunca sera executada, pois existe um return antes dela.");
				}

				if (linha.equals("return 0;") || linha.equals("return 1;")) {
					returnEncontrado = true;
					continue;
				}

				quantidadeInstrucoes++;

				if (linha.startsWith("int ")) {
					try {
						Interpretador.adicionarVariavelInt(linha);
					} catch (IllegalArgumentException e) {
						throw new IllegalArgumentException("Linha " + (i + 1) + ": " + e.getMessage());
					}

					String[] elementos = linha.split(" ");

					if (elementos.length >= 4) {
						String nome = elementos[1];
						String valor = elementos[3];

						if (valor.endsWith(";")) {
							valor = valor.substring(0, valor.length() - 1);
						}

						if (nome.equals("numero") && valor.equals("0")) {
							declaracaoConcluida = true;
						}
					}

					continue;
				}

				if (linha.startsWith("scanf")) {
					if (!declaracaoConcluida)
						throw new IllegalArgumentException("Linha " + (i + 1) + ": declare corretamente a variavel numero antes de ler o valor.");

					if (Interpretador.verificarScanf(linha) == 0)
						throw new IllegalArgumentException("Linha " + (i + 1) + ": o scanf esta incorreto ou nao corresponde a variavel numero.");

					entradaConcluida = true;
					continue;
				}

				if (linha.startsWith("printf")) {
					if (!entradaConcluida)
						throw new IllegalArgumentException("Linha " + (i + 1) + ": utilize o scanf para ler e armazenar um valor antes de imprimi-lo.");

					if (Interpretador.verificarPrintf(linha) == 0)
						throw new IllegalArgumentException("Linha " + (i + 1) + ": o printf esta incorreto ou nao corresponde a variavel numero.");

					saidaConcluida = true;
					continue;
				}

				throw new IllegalArgumentException("Linha " + (i + 1) + ": instrucao invalida para este desafio.");
			}

			if (quantidadeInstrucoes > 3) throw new IllegalArgumentException("Este desafio deve possuir apenas as tres instrucoes necessarias.");

			if (!declaracaoConcluida) throw new IllegalArgumentException("Crie uma variavel inteira chamada 'numero' com o valor inicial 0.");

			if (!entradaConcluida) throw new IllegalArgumentException("Utilize o scanf para ler e armazenar um valor na variavel numero.");

			if (!saidaConcluida) throw new IllegalArgumentException("Utilize o printf para imprimir o valor armazenado na variavel numero.");

			alerta.setTitle("SUCESSO");
			alerta.setHeaderText("Desafio concluído.");
			alerta.showAndWait();

		} catch (IllegalArgumentException e) {
			alerta.setTitle("ERRO");
			alerta.setHeaderText(e.getMessage());
			alerta.showAndWait();
		}
	}
	
}