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

public abstract class FaseMenuController implements Initializable {
	protected Scene proxCena; // Cena de destino do Usuario
	protected Alert alerta = new Alert(Alert.AlertType.INFORMATION); // Caixa de Alerta para Erros e Mensagens de Aviso

	@FXML
	protected Label descricao; // Label de Texto
	
	@FXML
	protected BotaoCena bEntrar;
	
	// Botao de Troca de Cena
	@FXML
	protected BotaoCena bVoltar;
	
	// Metodo que altera a Label de descricao e define a Cena alvo
	@FXML
	private void enviarInfos(ActionEvent event) {

		// Definindo descricao da fase
	    BotaoGrupo selecionado = (BotaoGrupo) event.getSource();

	    descricao.setText(selecionado.getDescricao());
	    
	    // Definindo Cena alvo
	    proxCena = selecionado.getCena();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	
	protected void definirFase(BotaoGrupo fase, String fxmlFase) {
		// Definindo cena de cada fase
		try {
			fase.setCena(fxmlFase); // Definir Fases
		} catch(IOException e) {
			alerta.setTitle("ERRO");
			alerta.setHeaderText("Houve um erro ao Carregar as Cenas, Por Favor Reinicie");
			alerta.showAndWait();
		}
	}
	
	protected void definirBotoes() {
		// Chamada do metodo de troca de cena ao clicar
		bEntrar.setOnAction(event -> {
			try {
				bEntrar.mudarPara(proxCena);
			} catch(IllegalArgumentException e) {
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

