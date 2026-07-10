package game.poo.fases;

import java.net.URL;
import java.util.ResourceBundle;
import Elements.Interpretador;
import game.poo.controllers.FaseController;

public class Condicionais3 extends FaseController {

    @Override
    public void initialize(URL location, ResourceBundle resources) {    
        definirBotoes("/game/poo/fxml/Fases/grupoFase4.fxml");
        
        descricao.setText("""
Agora vamos juntar tudo!

Seu objetivo:
1. Crie uma variável 'caixa' do tipo int e atribua um valor inicial a ela.
2. Escreva uma estrutura completa de 'if' e 'else'. O 'if' deve verificar se a variável 'caixa' é maior que 10. Se for, utilize o printf("Caixa grande");. Caso contrário, utilize o printf("Caixa pequena"); no 'else'.
                
OBS: O código inteiro deve obrigatoriamente estar dentro da função 'int main(){}'
""");
    }
    
    @Override
    public void chamarInterpretador() {
        String cod = codigo.getText(); 
        try {
            if (cod == null || cod.trim().isEmpty()) throw new IllegalArgumentException("O campo de código está vazio!");
            String[] linhas = cod.split("\\R");
                
            // Valida a presença da função main e o fechamento correto das chaves globais
            if (Interpretador.verificarMain(cod) == 1) throw new IllegalArgumentException("Função main() não encontrada!");
            Interpretador.verificarChaves(linhas);
            
            Interpretador.limparVariaveis();

            // 1. Identifica e adiciona a variável declarada pelo usuário no Interpretador
            boolean declarouVariavel = false;
            for (String linha : linhas) {
                if (linha.trim().startsWith("int ") && linha.contains("caixa")) {
                    Interpretador.adicionarVariavelInt(linha.trim());
                    declarouVariavel = true;
                    break;
                }
            }
            if (!declarouVariavel) {
                throw new IllegalArgumentException("Você precisa declarar a variável 'caixa' do tipo int dentro do main!");
            }

            // 2. Extrai o miolo do main e faz o mapeamento dos printfs
            boolean possuiPrintGrande = false;
            boolean possuiPrintPequena = false;
            StringBuilder mioloAdaptado = new StringBuilder();

            String mioloOriginal = extrairTextoMain(linhas);
            String[] linhasDoMiolo = mioloOriginal.split("\\R");

            for (String linha : linhasDoMiolo) {
                // Remove a linha da declaração da variável para isolar apenas a estrutura do IF/ELSE
                if (linha.trim().startsWith("int ") && linha.contains("caixa")) {
                    continue; 
                }

                if (linha.trim().contains("printf")) {
                    if (linha.contains("Caixa grande")) {
                        possuiPrintGrande = true;
                        mioloAdaptado.append("grande();").append("\n");
                        continue;
                    } else if (linha.contains("Caixa pequena")) {
                        possuiPrintPequena = true;
                        mioloAdaptado.append("pequena();").append("\n");
                        continue;
                    }
                }
                mioloAdaptado.append(linha).append("\n");
            }

            if (!possuiPrintGrande || !possuiPrintPequena) {
                throw new IllegalArgumentException("Certifique-se de usar printf(\"Caixa grande\"); no 'if' e printf(\"Caixa pequena\"); no 'else'!");
            }


            String mioloLimpo = mioloAdaptado.toString().replace("\n", "").replace("\r", "").replace(" ", "");

            Interpretador.FaseCond regrasFase3 = new Interpretador.FaseCond(
                3, "", "grande();", "pequena();", true
            );

            
            boolean passou = Interpretador.verificarCodigoCondicionais(mioloLimpo, regrasFase3);

            if (passou) {
                exibirSucesso("CÓDIGO CORRETO!", "Sensacional! Estrutura condicional completa dentro do main() validada com sucesso.");
            } else {
                throw new IllegalArgumentException("Sua estrutura deve validar (caixa > 10) de forma correta utilizando 'if' e 'else'.");
            }
        } catch(IllegalArgumentException e) {
            exibirErro("CÓDIGO ERRADO", e.getMessage());
        }
    }
}
