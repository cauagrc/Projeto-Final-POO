package game.poo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Elements.BotaoCena;
import Elements.BotaoGrupo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class FaseMenuController implements Initializable {
	private Scene proxCena; // Cena de destino do Usuario
	private Alert alerta = new Alert(Alert.AlertType.INFORMATION); // Caixa de Alerta para Erros e Mensagens de Aviso

	@FXML
	private Label descricao; // Label de Texto

	// Botoes de Destino
	@FXML
	private BotaoGrupo fase1;

	@FXML
	private BotaoGrupo fase2;

	@FXML
	private BotaoGrupo fase3;
	
	@FXML
	private BotaoCena bEntrar;
	
	// Botao de Troca de Cena
	@FXML
	private BotaoCena bVoltar;
	
	// Metodo que altera a Label de descricao e define a Cena alvo
	@FXML
	private void enviarInfos(ActionEvent event) {

		// Definindo descricao da fase
	    BotaoGrupo selecionado = (BotaoGrupo) event.getSource();

	    descricao.setText(selecionado.getDescricao());
	    
	    // Definindo Cena alvo
	    proxCena = selecionado.getCena();
	    
	    System.out.println(proxCena);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Definindo cena de cada fase
		try {
		fase1.setCena("/game/poo/fxml/faseModelo.fxml"); // Definir Fases
		fase2.setCena("/game/poo/fxml/faseModelo.fxml"); // Definir Fases
		fase3.setCena("/game/poo/fxml/faseModelo.fxml"); // Definir Fases
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
				alerta.setHeaderText("Por favor Selecione uma Fase");
				alerta.showAndWait();
			}
		});
		
		bVoltar.setOnAction(event -> {
			try {
			FXMLLoader fxml = new FXMLLoader(getClass().getResource("/game/poo/fxml/menu.fxml"));
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

