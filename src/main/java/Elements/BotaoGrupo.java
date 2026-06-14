package Elements;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class BotaoGrupo extends  Button{
	private String descricao;
	private Scene cena;
	
	public BotaoGrupo() {
		super();
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
	    return descricao;
	}
	
	public void setCena(String path) throws IOException{
		FXMLLoader arquivo = new FXMLLoader(getClass().getResource(path));
		
		Parent cenaCarregada = arquivo.load();

		cena = new Scene(cenaCarregada);
	}
	
	public Scene getCena() {
		return cena;
	}
	
}
