package Elements;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BotaoCena extends Button{

    public BotaoCena() {
        super();
    }
    
    public void mudarPara(Scene novaCena) {
        if (novaCena != null) {
            
        	Stage janelaPrincipal = (Stage) this.getScene().getWindow();
        	
        	if(janelaPrincipal != null) {
        		janelaPrincipal.setScene(novaCena);
        		janelaPrincipal.show();
        	}else {
        		System.err.println("Erro: Não foi possível encontrar a janela atual");
        	}
        } else {
            System.err.println("Erro: Nova Cena está nula!");
        }
    }
}
