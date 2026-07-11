package game.poo.fases;

import java.net.URL;
import java.util.ResourceBundle;

import Elements.Interpretador;
import game.poo.controllers.FaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Tutorial7 extends FaseController {
	
	@FXML
	Label descricao;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		definirBotoes("/game/poo/fxml/Fases/grupoFase1.fxml");
		descricao.setText(""
				+ "Até agora aprendemos a criar variáveis e armazenar informações nelas. Porém, existe um problema: como saber qual valor está guardado?"
				+ "\n"
				+ "\n"
				+ "Para isso existe a função printf(). Ela é responsável por imprimir informações no terminal, permitindo que o usuário visualize o resultado do programa."
				+ "\n"
				+ "\n"
				+ "Quando queremos imprimir uma variável, precisamos informar também o seu especificador de formato, que indica ao C qual é o tipo da informação que será exibida."
				+ "\n"
				+ "\n"
				+ "%d → imprime um número inteiro (int);\r\n"
				+ "%f → imprime um número decimal (float);\r\n"
				+ "%c → imprime um único caractere (char)."
				+ "\n"
				+ "\n"
				+ "Veja um exemplo:"
				+ "\n"
				+ "\n"
				+ "printf(\"%d\", numero);"
				+ "\n"
				+ "\n"
				+ "Nesse caso, %d informa ao programa que a variável numero é do tipo inteiro."
				+ "\n"
				+ "Agora é sua vez! Crie uma variável inteira e utilize o printf() para imprimir seu valor no terminal."
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

				if (linha.startsWith("printf")) {
					if (!declaracaoConcluida)
						throw new IllegalArgumentException("Linha " + (i + 1) + ": declare corretamente a variavel numero antes de imprimir o valor.");

					if (Interpretador.verificarPrintf(linha) == 0)
						throw new IllegalArgumentException("Linha " + (i + 1) + ": o printf esta incorreto ou nao corresponde a variavel numero.");

					saidaConcluida = true;
					continue;
				}

				throw new IllegalArgumentException("Linha " + (i + 1) + ": instrucao invalida para este desafio.");
			}

			if (quantidadeInstrucoes > 2) throw new IllegalArgumentException("Este desafio deve possuir apenas as duas instrucoes necessarias.");

			if (!declaracaoConcluida) throw new IllegalArgumentException("Crie uma variavel inteira chamada 'numero' e armazene nela o valor 10.");

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