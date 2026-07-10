package game.poo.grupos;

import java.net.URL;
import java.util.ResourceBundle;

import Elements.BotaoGrupo;
import game.poo.controllers.FaseMenuController;
import javafx.fxml.FXML;

public class GrupoCondicionais extends FaseMenuController {
	

	// Botoes de Destino
	@FXML
	private BotaoGrupo fase1;

	@FXML
	private BotaoGrupo fase2;

	@FXML
	private BotaoGrupo fase3;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		definirBotoes();
		definirFase(fase1, "/game/poo/fxml/Jogos/condicionais1.fxml");
		definirFase(fase2, "/game/poo/fxml/Jogos/condicionais2.fxml");
		definirFase(fase3, "/game/poo/fxml/Jogos/condicionais3.fxml");
	}
}
