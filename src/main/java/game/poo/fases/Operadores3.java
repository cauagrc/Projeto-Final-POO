package game.poo.fases;

import java.net.URL;
import java.util.ResourceBundle;

import game.poo.controllers.FaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Operadores3 extends FaseController {
	
	@FXML
	Label descricao;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		definirBotoes("/game/poo/fxml/Fases/grupoFase3.fxml");
		descricao.setText(""
				+ "Operadores."
				);
	}

	@Override
	public void chamarInterpretador() {
		// TODO Auto-generated method stub
		
		
	}

}
