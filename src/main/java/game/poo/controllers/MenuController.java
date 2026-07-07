package game.poo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Elements.BotaoCena;
import Elements.BotaoGrupo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class MenuController implements Initializable {
	private Scene proxCena; // Cena de destino do Usuario
	private Alert alerta = new Alert(Alert.AlertType.INFORMATION); // Caixa de Alerta para Erros e Mensagens de Aviso

	@FXML
	private Label descricao; // Label de Texto

	// Botoes de Destino
	@FXML
	private BotaoGrupo grupo1;

	@FXML
	private BotaoGrupo grupo2;

	@FXML
	private BotaoGrupo grupo3;

	@FXML
	private BotaoGrupo grupo4;
	
	// Botao de Troca de Cena
	@FXML
	private BotaoCena bEntrar;
	
	// Metodo que altera a Label de descricao e define a Cena alvo
	@FXML
	private void enviarInfos(ActionEvent event) {

		// Definindo descricao do grupo
	    BotaoGrupo selecionado = (BotaoGrupo) event.getSource();

	    descricao.setText(selecionado.getDescricao());
	    
	    // Definindo Cena alvo
	    proxCena = selecionado.getCena();
	    
	    System.out.println(proxCena);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Definindo Cena das fases de cada grupo
		try {
			grupo1.setCena("/game/poo/fxml/Fases/grupoFase1.fxml");
			grupo2.setCena("/game/poo/fxml/Fases/grupoFase2.fxml");
			grupo3.setCena("/game/poo/fxml/Fases/grupoFase3.fxml");
			grupo4.setCena("/game/poo/fxml/Fases/grupoFase4.fxml");
		}catch(IOException e) {
			alerta.setTitle("ERRO");
			alerta.setHeaderText("Houve um erro ao Carregar as Cenas, Por Favor Reinicie");
			alerta.showAndWait();
		}
		
		// Chamada do metodo de troca de cena ao clicar
		bEntrar.setOnAction(event -> {
			try {
			bEntrar.mudarPara(proxCena);
			}catch(IllegalArgumentException e) {
				alerta.setTitle("FALTA DE INFORMACAO");
				alerta.setHeaderText("Por favor Selecione um Grupo");
				alerta.showAndWait();
			}
		});
	}

}

