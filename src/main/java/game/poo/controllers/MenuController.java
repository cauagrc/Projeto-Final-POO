package game.poo.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Elements.BotaoGrupo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MenuController implements Initializable {
	@FXML
	private Label descricao;

	@FXML
	private VBox painelGrupos;
	
	@FXML
	private BotaoGrupo grupo1;

	@FXML
	private BotaoGrupo grupo2;

	@FXML
	private BotaoGrupo grupo3;
	
	@FXML
	private void enviarInfos(ActionEvent event) {

	    BotaoGrupo selecionado = (BotaoGrupo) event.getSource();

	    descricao.setText(selecionado.getDescricao());
	    
	    
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}

}
