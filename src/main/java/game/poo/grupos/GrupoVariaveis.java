package game.poo.grupos;

import java.net.URL;
import java.util.ResourceBundle;

import Elements.BotaoGrupo;
import game.poo.controllers.FaseMenuController;
import javafx.fxml.FXML;

public class GrupoVariaveis extends FaseMenuController {
	// Botoes de Destino
	@FXML
	private BotaoGrupo fase1;

	@FXML
	private BotaoGrupo fase2;

	@FXML
	private BotaoGrupo fase3;

	@FXML
	private BotaoGrupo fase4;

	@FXML
	private BotaoGrupo fase5;

	@FXML
	private BotaoGrupo fase6;

	@FXML
	private BotaoGrupo fase7;

	@FXML
	private BotaoGrupo fase8;

	@FXML
	private BotaoGrupo fase9;

	@FXML
	private BotaoGrupo fase10;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		definirBotoes();
		definirFase(fase1, "/game/poo/fxml/Jogos/variaveis1.fxml");
	}
}
