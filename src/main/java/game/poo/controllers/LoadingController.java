package game.poo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class LoadingController implements Initializable {
	@FXML 
	private BorderPane menuInicio;
	
	@FXML
	private Button botaoJogar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		botaoJogar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				try {
					mudarConteudo();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	@FXML
	public void mudarConteudo() throws IOException {
		Pane novoPane = FXMLLoader.load(getClass().getResource("/game/poo/fxml/menu.fxml"));
		
		menuInicio.getChildren().clear();
		menuInicio.setCenter(novoPane);
	}

}
