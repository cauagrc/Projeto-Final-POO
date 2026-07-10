package Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexCondicionais {

    private static final String REGEX_IF_ELSE = 
        "^if\\s*\\((?<condicao>.*?)\\)\\s*\\{(?<blocoIf>.*?)\\}(?:\\s*else\\s*\\{(?<blocoElse>.*?)\\})?$";
    
    private static final Pattern PATTERN_ESTRUTURAL = Pattern.compile(REGEX_IF_ELSE, Pattern.DOTALL);

   
     //conteudo da minha fase
    public static class FaseConfig {
        int idFase;
        String comandoTela;
        String comandoIfObrigatorio;
        String comandoElseObrigatorio; // null se a fase não usar else
        boolean exigeVariavelNaCondicao; // diz se a fase exige criar uma variável

        public FaseConfig(int id, String comando, String cmdIf, String cmdElse, boolean exigeVar) {
            this.idFase = id;
            this.comandoTela = comando;
            this.comandoIfObrigatorio = cmdIf;
            this.comandoElseObrigatorio = cmdElse;
            this.exigeVariavelNaCondicao = exigeVar;
        }
    }
    
    
     
    public static boolean verificarJogada(String codigoDoJogador, FaseConfig faseAtual) {
    	//se ta vazio
        if (codigoDoJogador == null || codigoDoJogador.trim().isEmpty()) {
            return false;
        }

        
        Matcher matcher = PATTERN_ESTRUTURAL.matcher(codigoDoJogador.trim());

       
        if (!matcher.matches()) {
            System.out.println("Erro: Estrutura do if/else escrita incorretamente.");
            return false;
        }

        //extrai o texto dos marcadores pra poder comparar
        String condicaoEntreParenteses = matcher.group("condicao").trim();
        String textoDentroDoIf = matcher.group("blocoIf").trim();
        String textoDentroDoElse = matcher.group("blocoElse");

     
        String operadorIdentificado = regexMarcus(condicaoEntreParenteses);
        
        if (operadorIdentificado == null) {
            System.out.println("Erro: Nenhum operador de comparação válido detectado nos parênteses.");
            return false;
        }

        
        String[] termos = condicaoEntreParenteses.split(Pattern.quote(operadorIdentificado));
        if (termos.length != 2) return false;

        String ladoEsquerdo = termos[0].trim();
        String ladoDireito = termos[1].trim();
        
        
       //Regex do CAUâ
        if (faseAtual.exigeVariavelNaCondicao) {
            
            
            boolean ehUmaVariavelValida = regexCaua(ladoEsquerdo);
            
            if (!ehUmaVariavelValida) {
                System.out.println("Erro: O lado esquerdo da comparação precisa ser uma variável cadastrada.");
                return false;
            }

            // O lado direito, por padrão do jogo, tem que comparar com um número 
            if (!ladoDireito.matches("^\\d+$")) {
                System.out.println("Erro: Você deve comparar a variável contra um valor numérico fixo.");
                return false;
            }

        } else {
            // Fase 1 não tem variáveis, apenas números brutos dos dois lados ( 15 < 30)
            if (!ladoEsquerdo.matches("^\\d+$") || !ladoDireito.matches("^\\d+$")) {
                System.out.println("Erro: Nesta primeira fase, use apenas números diretos na comparação.");
                return false;
            }
        }

        
        if (!textoDentroDoIf.contains(faseAtual.comandoIfObrigatorio)) {
            System.out.println("Erro: Faltou executar o comando correto dentro do bloco 'if'.");
            return false;
        }

        // caso a fase mande utilizar o else
        if (faseAtual.comandoElseObrigatorio != null) {
            if (textoDentroDoElse == null) {
                System.out.println("Erro: Esta fase exige que você adicione um bloco 'else { ... }'.");
                return false;
            }
            if (!textoDentroDoElse.trim().contains(faseAtual.comandoElseObrigatorio)) {
                System.out.println("Erro: Faltou executar o comando correto dentro do bloco 'else'.");
                return false;
            }
        } else if (textoDentroDoElse != null) {
            System.out.println("Erro: Remova o bloco 'else'. Esta etapa pede apenas uma estrutura de 'if' simples.");
            return false;
        }

        return true; 
    }

  //fiz os dois de baixo pra testar enquanto não tinha os regex restantes
    private static String regexMarcus(String expressaoInterna) {
       
    	
        if (expressaoInterna.contains("<")) return "<";
        if (expressaoInterna.contains(">")) return ">";
        if (expressaoInterna.contains("==")) return "==";
        return null; 
    }

    private static boolean regexCaua(String textoLadoEsquerdo) {
        
        return textoLadoEsquerdo.equals("pontos") || textoLadoEsquerdo.equals("vida");
    }
}
