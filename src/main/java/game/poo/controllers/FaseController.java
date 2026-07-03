package game.poo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Elements.BotaoCena;
import Elements.BotaoGrupo;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FaseController implements Initializable {
	private Alert alerta = new Alert(Alert.AlertType.INFORMATION); // Caixa de Alerta para Erros e Mensagens de Aviso

	// Painel do Jogo
	@FXML
	private AnchorPane jogo;
	
	// Label de Descricao da Fase
	@FXML
	private Label descricao; // Label de Texto

	// Botao de Troca de Cena
	@FXML
	private BotaoCena bVoltar;

	// Botao de Rodar Codigo
	@FXML
	private Button bComecar;

	// Local onde o usuario escreve o codigo
	@FXML
	private TextArea codigo;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		try {
            // Carregar o FXML da janela acoplada
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/game/poo/fxml/jogoModelo.fxml"));
            Parent janelaAcoplada = loader.load();
            
            // Adicionar a tela ao Painel dentro da Janela da Fase
            jogo.getChildren().add(janelaAcoplada);   
            
		}catch(IOException e) {
			alerta.setTitle("ERRO");
			alerta.setHeaderText("Houve um erro ao Carregar o Jogo, Por Favor Reinicie");
			alerta.showAndWait();
		}
		
		// Metodo do Botao Voltar
		bVoltar.setOnAction(event -> {
			try {
			FXMLLoader fxml = new FXMLLoader(getClass().getResource("/game/poo/fxml/Fases/grupoFase1.fxml"));
			Parent cena = fxml.load();
			Scene menu = new Scene(cena);
			bVoltar.mudarPara(menu);
			}catch(Exception e) {
				alerta.setTitle("ERRO");
				alerta.setHeaderText("Erro ao tentar Voltar, Por Favor Reinicie");
				alerta.showAndWait();
			}
			
		});
	}
}

