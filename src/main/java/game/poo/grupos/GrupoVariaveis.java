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
		definirFase(fase2, "/game/poo/fxml/Jogos/variaveis2.fxml");
		definirFase(fase3, "/game/poo/fxml/Jogos/variaveis3.fxml");
		definirFase(fase4, "/game/poo/fxml/Jogos/variaveis4.fxml");
		definirFase(fase5, "/game/poo/fxml/Jogos/variaveis5.fxml");
		definirFase(fase6, "/game/poo/fxml/Jogos/variaveis6.fxml");
		definirFase(fase7, "/game/poo/fxml/Jogos/variaveis7.fxml");
		definirFase(fase8, "/game/poo/fxml/Jogos/variaveis8.fxml");
	}
}
