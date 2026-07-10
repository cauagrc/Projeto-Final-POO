package game.poo.fases;

import java.net.URL;
import java.util.ResourceBundle;
import Elements.Interpretador;
import game.poo.controllers.FaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

/*CÓDIGO ESPERADO PELO USUÁRIO:
if(15<30){
avancar();
}
  */

public class Condicionais1 extends FaseController {

    @Override
    public void initialize(URL location, ResourceBundle resources) {    
        // Configura o botão para voltar ao menu do grupo de condicionais
        definirBotoes("/game/poo/fxml/Fases/grupoFase4.fxml");
        
        // Texto da descrição na interface
        descricao.setText("""
As estruturas condicionais permitem que o programa tome caminhos diferentes baseando-se em condições lógicas.

Agora é sua vez:
Escreva um 'if' dentro da função main() que verifique se o número 15 é menor que 30. Dentro das chaves, chame a função 'avancar();'.
                """);
    }

    @Override
    public void chamarInterpretador() {
        String cod = codigo.getText(); 
        try {
            if (cod == null || cod.trim().isEmpty()) throw new IllegalArgumentException("O campo de código está vazio!");
            String[] linhas = cod.split("\\R");
                
            if (Interpretador.verificarMain(cod) == 1) throw new IllegalArgumentException("Função main() não encontrada!");
            Interpretador.verificarChaves(linhas);
            
            // Limpa o ambiente antes do teste
            Interpretador.limparVariaveis();

            // Configura a Regra Estrita da Fase 1
            Interpretador.FaseCond regrasFase1 = new Interpretador.FaseCond(
                1, "Verifique se o número 15 é menor que 30", "avancar();", null, false
            );

            // Extrai o conteúdo interno e valida
            String mioloCodigo = extrairTextoMain(linhas);
            boolean passou = Interpretador.verificarCodigoCondicionais(mioloCodigo, regrasFase1);

            if (passou) {
                exibirSucesso("CÓDIGO CORRETO!", "Parabéns! Você dominou o If simples.");
            } else {
                throw new IllegalArgumentException("Estrutura incorreta. Certifique-se de usar (15 < 30) e chamar 'avancar();' dentro.");
            }
        } catch(IllegalArgumentException e) {
            exibirErro("ERRO NO CÓDIGO", e.getMessage());
        }
    }
}
