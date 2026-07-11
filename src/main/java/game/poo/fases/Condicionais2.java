package game.poo.fases;

import java.net.URL;
import java.util.ResourceBundle;
import Elements.Interpretador;
import game.poo.controllers.FaseController;

/*CÓDIGO ESPERADO PELO USUÁRIO:
  if(caixa==0){
  printf("Esta vazia");
}
*/

public class Condicionais2 extends FaseController {

    @Override
    public void initialize(URL location, ResourceBundle resources) {    
        definirBotoes("/game/poo/fxml/Fases/grupoFase4.fxml");
        descricao.setText("""
Podemos utilizar variáveis criadas anteriormente dentro da nossa condição lógica.

Considere que uma variável chamada 'caixa' já existe no sistema. 

Seu objetivo:
Escreva a função 'int main(){}' e dentro dela um 'if' que verifique se 'caixa' é igual a 0. Dentro das chaves, utilize o comando printf("Esta vazia"); para exibir a mensagem.
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
            
            Interpretador.limparVariaveis();
            Interpretador.adicionarVariavelInt("int caixa = 0 ;");

            boolean possuiPrintfValido = false;
            StringBuilder textPrintf = new StringBuilder();

            String textoMain = extrairTextoMain(linhas);
            String[] linhasDotxtMain = textoMain.split("\\R");

            for (String linha : linhasDotxtMain) {
                if (linha.trim().contains("printf")) {
                    if (linha.contains("Esta vazia")) {
                        possuiPrintfValido = true;
                    }
                    // Mantém chaves ou espaçamentos que possam estar na mesma linha
                    String limpa = linha.replace(linha.substring(linha.indexOf("printf"), linha.lastIndexOf(";") + 1), "vazia();");
                    textPrintf.append(limpa).append("\n");
                } else {
                    textPrintf.append(linha).append("\n");
                }
            }

            if (!possuiPrintfValido) {
                throw new IllegalArgumentException("Você deve utilizar o comando 'printf(\"Esta vazia\");' dentro do if!");
            }

            Interpretador.FaseCond regrasFase2 = new Interpretador.FaseCond(2, "", "vazia();", null, true);
            boolean passou = Interpretador.verificarCodigoCondicionais(textPrintf.toString().trim(), regrasFase2);

            if (passou) {
                exibirSucesso("CÓDIGO CORRETO!", "Perfeito! Você validou a variável utilizando o printf corretamente.");
            } else {
                throw new IllegalArgumentException("Estrutura incorreta. Verifique se utilizou (caixa == 0) de forma correta.");
            }
        } catch(IllegalArgumentException e) {
            exibirErro("CÓDIGO ERRADO", e.getMessage());
        }
    }
}
