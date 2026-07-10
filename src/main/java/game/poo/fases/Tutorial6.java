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

public class Tutorial6 extends FaseController{
	@FXML
	Label descricao;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		definirBotoes("/game/poo/fxml/Fases/grupoFase1.fxml");
		descricao.setText("Os operadores aritméticos são utilizados para realizar cálculos matemáticos com valores e variáveis. Os principais operadores são:\n"
				+ "\n"
				+ "+ → realiza uma soma.\n"
				+ "- → realiza uma subtração.\n"
				+ "* → realiza uma multiplicação.\n"
				+ "/ → realiza uma divisão.\n"
				+ "\n"
				+ "Esses operadores podem ser usados para efetuar cálculos, atualizar valores de variáveis e resolver diversos problemas durante a programação."
				+ "\n"
				+ "Agora que voce aprendeu, tente praticar no codigo ao lado, crie uma variavel que numerica que receba uma operacao!");
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
			
			try {
				Interpretador.adicionarVariavelInt(linhas[linhaInicio]);
			}catch(Exception e) {
				Interpretador.adicionarVariavelFloat(linhas[linhaInicio]);	
			}
		}catch(IllegalArgumentException e) {
			alerta.setTitle("ERRO");
			alerta.setHeaderText(e.getMessage());
			alerta.showAndWait();
		}
	}
}

