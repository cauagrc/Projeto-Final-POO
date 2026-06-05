package Elements;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import java.util.ArrayList;

public class BotaoGrupo extends  Button{
	private String descricao;
	private Label painel;
	private Button botaoEntrar;
	private ArrayList<Button> listaFases;
	
	public BotaoGrupo(String nome, String descricao, Label painel, Button botaoEntrar) {
		super(nome); // nome do botao
		setDescricao(descricao);
		setPainel(painel);
		setBotao(botaoEntrar);
		listaFases = new ArrayList<Button>();
	}
	
	public void enviarDescricao() {
		painel.setText(descricao);
	}
	
	private void enviarFases() {
		// preciso do botao de entrar para saber pra onde enviar o array de fases
	}
	
	public void adicionarBotaodeFase(Button botaoDeFase) {
		if (verificar(botaoDeFase)) throw new NullPointerException("Botao de Fase nao definido");
		else {
			listaFases.add(botaoDeFase);
		}
	}
	
	// Fazer as verificacoes
	private boolean verificar(String texto) {
		if (texto.trim().isEmpty() || texto.isBlank()) return true;
		return false;
	}
	
	private boolean verificar(Label painel) {
		if (painel == null) return true;
		return false;
	}
	
	private boolean verificar(Button painel) {
		if (painel == null) return true;
		return false;
	}
	
	// Fazer os Sets
	private void setDescricao(String texto) {
		if (verificar(texto)) throw new NullPointerException("Texto da Descricao Vazio");
		else descricao = texto;
	}
	
	private void setPainel(Label painel) {
		if (verificar(painel)) throw new NullPointerException("label da Descricao nao Definido");
		else this.painel = painel;
	}
	
	private void setBotao(Button botao) {
		if (verificar(botao)) throw new NullPointerException("Botao Alvo nao Definido");
		else this.botaoEntrar = botao;
	}
}
