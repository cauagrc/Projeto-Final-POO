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

public class Tutorial4 extends FaseController{
	@FXML
	Label descricao;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		definirBotoes("/game/poo/fxml/Fases/grupoFase1.fxml");
		descricao.setText("Agora que sabemos a regra, vamos olhar para as possibilidades, aprendemos que o int serve para variáveis do tipo inteiro, isso significa que ela só armazena números sem vírgula, se você foi curioso e tentou declara um número decimal, já percebeu que com o int não é possível, por isso existem outros tipos de variável que podemos usar, outro tipo é o \n"
				+ "\n"
				+ "float -> para números decimais\n"
				+ "\n"
				+ "Com ele você consegue armazenar número com vírgula sem problema, mas tem um detalhe, o C não entende os números decimais como a gente, para um ser humano os números decimais utilizam virgula (,) para separar a parte inteira da fracionaria, por exemplo: 1,5 , 2,540, 3,14 etc . Enquanto isso o C usa o ponto (.) para dividir: 2.5, 4.5, 100.234 etc, isso significa que sempre que você pensar em usar vírgula você vai usar o ponto:\n"
				+ "\n"
				+ "1,5 -> 1.5\n"
				+ "24,23 -> 24.23\n"
				+ "100,222 -> 100.222\n"
				+ "\n"
				+ "Para declarar uma variável float é a mesma estrutura anterior, mas substitua o int pelo float\n"
				+ "\n"
				+ "float numero decimal = 2.0;\n"
				+ "\n"
				+ "Agora faça você!");
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
			
			Interpretador.adicionarVariavelFloat(linhas[linhaInicio]);
			
			System.out.println("Concluido");
		}catch(IllegalArgumentException e) {
			alerta.setTitle("ERRO");
			alerta.setHeaderText(e.getMessage());
			alerta.showAndWait();
		}
	}
}

