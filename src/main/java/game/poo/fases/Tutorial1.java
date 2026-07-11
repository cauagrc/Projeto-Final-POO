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

public class Tutorial1 extends FaseController{
	@FXML
	Label descricao;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		definirBotoes("/game/poo/fxml/Fases/grupoFase1.fxml");
		descricao.setText("Neste jogo, Nos queremos te ensiar como a linguagem C funciona de uma maneira divertida. Preparamos alguns desafios e tutoriais que vão ensinar os principais conceitos da linguagem."
				+ ""
				+ "\n\nPara começar, você precisa conhecer a Função Principal (main). Ela é responsável por iniciar a execução dos comandos que o seu código irá realizar.  "
				+ "\n\nSempre que você criar um programa em C, a função principal deverá estar presente, pois é por ela que o computador saberá onde começar a executar suas instruções. "
				+ "\n\nVamos com calma! No quadro ao lado, escreva a estrutura da Função Principal:"
				+ "\n\nint main() { "
				+ "\n"
				+ "\n}");
	}
	
	@Override
	public void chamarInterpretador() {
		String cod = codigo.getText(); 
		
		try {
			int linhaMain = 0; // Linha que Abre o Main {
			int linhaFinal = 0; // Linha que Termina o Main }
			
			String[] linhas = cod.split("\\R");
				
			// Verificando se Existe o Main e apenas 1 unico Main
			if (Interpretador.verificarMain(cod) == 1) throw new IllegalArgumentException("A sua Funcao Principal nao foi Encontrada ou Esta Mal Declarada!");
			else if (Interpretador.verificarMain(cod) == 2) throw new IllegalArgumentException("Houve mais de uma declaracao de main no seu codigo! Apenas um main() eh Permitido");
			
			// Verifica se a Quantidade de {} esta correta
			Interpretador.verificarChaves(linhas);
			
			// Contando onde comeca o Main()
			for(String linha : linhas) {
				if (Interpretador.verificarAbertura(linha)) break; 
				linhaMain++;
			}
				
			// Marca onde fica a linha do } do Main
			if (Interpretador.verificarFechamento(linhas[linhas.length - 1])) linhaFinal = linhas.length - 1;

			
			System.out.println("Concluido");
		}catch(IllegalArgumentException e) {
			alerta.setTitle("ERRO");
			alerta.setHeaderText(e.getMessage());
			alerta.showAndWait();
		}
	}
}

