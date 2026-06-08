package Elements;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.ArrayList;

public class BotaoGrupo extends  Button{
	private String descricao;
	private Label painel;
	private Button botaoEntrar;
	private ArrayList<Button> listaFases;
	
	public BotaoGrupo() {
		super();
	    listaFases = new ArrayList<>();
	}
	
	public void enviarDescricao() {
		painel.setText(descricao);
	}
	
	private void enviarFases() {
		// preciso do botao de entrar para saber pra onde enviar o array de fases
	}
	
	public void adicionarBotaodeFase(Button botaoDeFase) {
		listaFases.add(botaoDeFase);
	}
	
	// Fazer os Sets
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public void setPainel(Label painel) {
		this.painel = painel;
	}
	
	public void setBotao(Button botao) {
		this.botaoEntrar = botao;
	}
	
	//Fazer os Sets
	public String getDescricao() {
	    return descricao;
	}
}
