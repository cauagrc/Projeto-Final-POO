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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class FaseController implements Initializable {	
	protected Alert alerta = new Alert(Alert.AlertType.INFORMATION); // Caixa de Alerta para Erros e Mensagens de Aviso

	@FXML
	protected GridPane root;
	
	// Painel do Jogo
	@FXML
	protected AnchorPane jogo;
	
	// Label de Descricao da Fase
	@FXML
	protected Label descricao; // Label de Texto

	// Botao de Troca de Cena
	@FXML
	protected BotaoCena bVoltar;

	// Botao de Rodar Codigo
	@FXML
	protected Button bComecar;

	// Local onde o usuario escreve o codigo
	@FXML
	protected TextArea codigo;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	

	}
	
	// Definir pra qual grupo de Fases deve voltar
	public void definirBotoes(String grupoFase) {
		bComecar.setOnAction(event -> {
			chamarInterpretador();
		});
		
		// Metodo do Botao Voltar
		bVoltar.setOnAction(event -> {
			try {
			FXMLLoader fxml = new FXMLLoader(getClass().getResource(grupoFase));
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
	
	public abstract void chamarInterpretador();
}

