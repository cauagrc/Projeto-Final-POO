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
		iniciarJogo(getFxmlJogo());
		definirBotoes("/game/poo/fxml/Fases/grupoFase1.fxml");
		descricao.setText("""
Variáveis são espaços na memória utilizados para armazenar informações durante a execução de um programa.
Cada variável possui um nome, um tipo de dado (inteiro, de ponto flutuante, caractere ou booleano) e um valor, que pode ser alterado ao longo da execução.Elas permitem que o programa guarde resultados de cálculos, dados fornecidos pelo usuário e outras informações necessárias para realizar suas tarefas.

Para declarar uma variável, informe primeiro o tipo de dado, depois o nome da variável e, opcionalmente, atribua um valor inicial. Exemplo:

int idade = 20;

Agora é a sua vez! Crie uma variável do tipo inteiro (int) com o nome que desejar e atribua um valor a ela.
				""");
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
			
			for(int i = linhaMain; i < linhaFinal; i++) {
				
			}

		}catch(IllegalArgumentException e) {
			alerta.setTitle("ERRO");
			alerta.setHeaderText(e.getMessage());
			alerta.showAndWait();
		}
	}
	
	@Override // Colocar o caminho do fxml da Representacao 3D
	public String getFxmlJogo() {
		return "/game/poo/fxml/jogoModelo.fxml";
	}
}

