package game.poo.fases;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Elements.BotaoCena;
import Elements.BotaoGrupo;
import Elements.Interpretador;
import game.poo.controllers.FaseController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Tutorial3 extends FaseController{
	@FXML
	Label descricao;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		definirBotoes("/game/poo/fxml/Fases/grupoFase1.fxml");
		descricao.setText("Você declarou sua primeira variável, mas ainda falta um pouco de prática, é interessante entender algumas regras antes:\n"
				+ "1 - nome de variáveis não podem começar com caracteres especiais (#. ?, !, etc)\n"
				+ "2 - nome de variáveis não podem começar com números (1nome, 34pedro, 2.3var, etc)\n"
				+ "3 - os únicos caracteres especiais que o nome da sua variável pode ter são  o '-' e '_'\n"
				+ "4 - existem palavras reservadas na linguagem C, por exemplo o 'int', 'float', 'main' e etc, não se pode usa-las para nada além de suas funções pré-estabelecidas, caso queira saber quais são as outras, pesquise na internet\n"
				+ "5 - existem diferença entre letras minúsculas e maiúsculas, ou seja, se você cria a variável de nome martelo e outra com nome Martelo, elas não serão a mesma variável\n"
				+ "\n"
				+ "Agora que você sabe as regras, você pode usar o espaço ao lado para testar os erros, para completar essa fase escreva 2 variáveis do tipo inteiro, igual você fez na anterior");
	}
	
	@Override
	public void chamarInterpretador() {
		String cod = codigo.getText();
		
		try {
			int linhaInicio = 0; // Linha que Abre o Main {
			int linhaFinal = 0; // Linha que Termina o Main }
			Interpretador.limparVariaveis();
			
			String[] linhas = cod.split("\\R");
				
			// Verificando se Existe o Main e apenas 1 unico Main
			if (Interpretador.verificarMain(cod) == 1) throw new IllegalArgumentException("A sua Funcao Principal nao foi Encontrada ou Esta Mal Declarada!");
			else if (Interpretador.verificarMain(cod) == 2) throw new IllegalArgumentException("Houve mais de uma declaracao de main no seu codigo! Apenas um main() eh Permitido");
			
			// Verifica se a Quantidade de {} esta correta
			Interpretador.verificarChaves(linhas);
			
			// Contando onde comeca o Main()
			for(String linha : linhas) {
				if (Interpretador.verificarAbertura(linha)) break; 
				linhaInicio++;
			}
			linhaInicio++;
				
			// Marca onde fica a linha do } do Main
			if (Interpretador.verificarFechamento(linhas[linhas.length - 1])) linhaFinal = linhas.length - 1;
			
			Interpretador.adicionarVariavelInt(linhas[linhaInicio]);
			Interpretador.adicionarVariavelInt(linhas[linhaInicio + 1]);
		}catch(IllegalArgumentException e) {
			alerta.setTitle("ERRO");
			alerta.setHeaderText(e.getMessage());
			alerta.showAndWait();
		}
	}
}

