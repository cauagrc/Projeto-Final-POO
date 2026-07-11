package game.poo.fases;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Elements.BotaoCena;
import Elements.BotaoGrupo;
import Elements.Interpretador;
import game.cg.RoboAnimViewer;
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

public class Tutorial2 extends FaseController{
	@FXML
	Label descricao;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		definirBotoes("/game/poo/fxml/Fases/grupoFase1.fxml");
		descricao.setText("Você deve ter notado que na última fase não aconteceu nada, correto? Isso aconteceu porque ainda não escrevemos nenhuma instrução para o programa executar. Agora vamos mudar isso aprendendo um dos conceitos mais importantes da programação: as variáveis!"
				+ "\nVariáveis são espaços utilizados pelo programa para armazenar informações. Elas podem guardar diferentes tipos de dados, como números inteiros (int), números decimais (float), letras (char), entre outros."
				+ "\nToda variável possui um nome, e é através dele que podemos acessar ou modificar o valor que ela armazena sempre que precisarmos. Vamos começar com números e criar uma variável que armazenará um valor inteiro!"
				+ "\nPara isso, siga esta estrutura:"
				+ "\n\nint numero = 10;"
				+ "\n\nNesse exemplo, criamos uma variável chamada numero e armazenamos o valor 10 dentro dela."
				+ "\nVocê pode escolher diferentes nomes para suas variáveis, mas lembre-se que o nome deve seguir algumas regras da linguagem C (logo mais explicaremos)."
				+ "\nAgora tente criar sua primeira variável dentro das chaves {} da função principal! Não esqueça de colocar ';' sempre no final da linha!");
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
			//else if (Interpretador.verificarMain(cod) == 2) throw new IllegalArgumentException("Houve mais de uma declaracao de main no seu codigo! Apenas um main() eh Permitido");
			
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
			
			String variavel = Interpretador.removerPontoVirgula(linhas[linhaInicio].split(" ")[3]);
			
			// Cada exemplo roda numa CENA LIMPA (o RoboAnimViewer recria o
	        // contexto C++ antes de cada um), entao todos comecam com a
	        // esteira IN cheia, na ordem: int=42, float=3.14, char='A',
	        // bool=true (ver domain::SceneGraph::build()). FETCH_INPUT sempre
	        // retira o PROXIMO disponivel (FIFO).

	        // Exemplo 1: fluxo basico - cria uma variavel, pega a 1a entrada
	        // (int=42), armazena e envia para a saida.
	        String script1 =
	        	"SET_INPUT int " + variavel + "\n" +
	            "CREATE_VAR resultado int\n" +
	            "FETCH_INPUT r0\n" +
	            "STORE resultado r0\n";
	        
	        List<RoboAnimViewer.Example> exemplos = List.of(
	            new RoboAnimViewer.Example("Fluxo basico: entrada -> armazena -> saida", script1)
	        );

	        // Instancia a janela de visualizacao, ja com os 3 exemplos em fila
	        RoboAnimViewer viewer = new RoboAnimViewer(exemplos, 800, 600);

	        // Exibe a janela
	        viewer.show();
	        
			alerta.setTitle("SUCESSO");
			alerta.setHeaderText("Desafio concluido.");
			alerta.showAndWait();
		}catch(IllegalArgumentException e) {
			alerta.setTitle("ERRO");
			alerta.setHeaderText(e.getMessage());
			alerta.showAndWait();
		}
		
	}
}

