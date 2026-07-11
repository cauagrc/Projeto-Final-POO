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

public class Tutorial5 extends FaseController{
	@FXML
	Label descricao;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		definirBotoes("/game/poo/fxml/Fases/grupoFase1.fxml");
		descricao.setText("Aprendemos os tipos numéricos, mas ainda falta algo importante, os caracteres, caracteres são qualquer todos os símbolos que você pode representar no seu teclado, desde letras até sinais de pontuação, operadores matemáticos etc. O importante é entender como o C entende eles, sempre que tiver que definir um caracter lembre que eles ficam entre aspas simples (''), por exemplo: 'a', '#', '3', se fizer isso nunca terá problemas com a interpretação. Nunca coloca mais de um único caracter, isso não é aceito pelo C.\n"
				+ "\n"
				+ "Agora que você já sabe que podemos guardar caracteres, tente guardar também, o tipo do caracter é o char\n"
				+ "\n"
				+ "char nome = 'c';");
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
			
			Interpretador.adicionarVariavelChar(linhas[linhaInicio]);
			
			System.out.println("Concluido");
		}catch(IllegalArgumentException e) {
			alerta.setTitle("ERRO");
			alerta.setHeaderText(e.getMessage());
			alerta.showAndWait();
		}
	}
}

