package Elements;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;

public class BotaoGrupo extends  Button{
	private String descricao;
	private Label areaDoTexto;
	private Button botaoDeEntrar;
	// lista de arrays de botoes de fase
	
	public BotaoGrupo(String texto, String descricao, Label areaDoTexto, Button botaoDeEntrar) {
		super(texto);
		this.descricao = descricao;
		this.areaDoTexto = areaDoTexto;
		this.botaoDeEntrar = botaoDeEntrar;
	}
	
	private void enviarDescricao() {
		areaDoTexto.setText(descricao);
	}
	
	private void enviarFases() {
		// preciso do botao de entrar para saber pra onde enviar o array de fases
	}
	 
	// Fazer as verificacoes
}
