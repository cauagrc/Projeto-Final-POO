package game.poo.fases;

import java.net.URL;
import java.util.ResourceBundle;

import Elements.Interpretador;
import game.poo.controllers.FaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Variaveis1 extends FaseController {
	
	@FXML
	Label descricao;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		definirBotoes("/game/poo/fxml/Fases/grupoFase2.fxml");
		descricao.setText(""
				+ "Uma sacolinha de aniversário contém 10 doces."
				+ " Crie uma variável inteira chamada saquinho, armazene nela a quantidade de doces"
				+ " e imprima esse valor."
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

						if (nome.equals("saquinho") && valor.equals("10")) {
							declaracaoConcluida = true;
						}
					}

					continue;
				}

				if (linha.startsWith("printf")) {
					if (!declaracaoConcluida)
						throw new IllegalArgumentException("Linha " + (i + 1) + ": declare corretamente a variavel saquinho antes de imprimir o valor.");

					if (Interpretador.verificarPrintf(linha) == 0)
						throw new IllegalArgumentException("Linha " + (i + 1) + ": o printf esta incorreto ou nao corresponde a variavel saquinho.");

					saidaConcluida = true;
					continue;
				}

				throw new IllegalArgumentException("Linha " + (i + 1) + ": instrucao invalida para este desafio.");
			}

			if (quantidadeInstrucoes > 2) throw new IllegalArgumentException("Este desafio deve possuir apenas as duas instrucoes necessarias.");

			if (!declaracaoConcluida) throw new IllegalArgumentException("Crie uma variavel inteira chamada 'saquinho' e armazene nela o valor 10.");

			if (!saidaConcluida) throw new IllegalArgumentException("Imprima o valor armazenado na variavel saquinho.");

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