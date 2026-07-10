package game.poo.grupos;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Elements.BotaoCena;
import Elements.BotaoGrupo;
import game.poo.controllers.FaseMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class GrupoTutorial2 extends FaseMenuController {	
	
	// Botoes de Destino
	@FXML
	private BotaoGrupo fase1;

	@FXML
	private BotaoGrupo fase2;

	@FXML
	private BotaoGrupo fase3;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}

