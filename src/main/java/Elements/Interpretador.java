package Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Interpretador {
	static HashMap<String, Integer> variaveisInt = new HashMap<String, Integer>();
	static HashMap<String, Double> variaveisFloat = new HashMap<String, Double>();
	static HashMap<String, Boolean> variaveisBool = new HashMap<String, Boolean>();
	static HashMap<String, Character> variaveisChar = new HashMap<String, Character>();

	static final String[] palavras_reservadas = {"int", "float", "double", "char", "void", "if", "else", "while", "for", "return",
	        "switch", "case", "break", "continue", "do", "default", "struct", "typedef", "const", "static", "sizeof"};
	
	//Regex de condicionais
	 private static final String REGEX_IF_ELSE = 
		        "^if\\s*\\((?<condicao>.*?)\\)\\s*\\{(?<blocoIf>.*?)\\}(?:\\s*else\\s*\\{(?<blocoElse>.*?)\\})?$";
		    
	 private static final Pattern PATTERN_ESTRUTURAL = Pattern.compile(REGEX_IF_ELSE, Pattern.DOTALL);
	
	 //Classe da fase de condicionais
	 public static class FaseCond {
		        int idFase;
		        String comandoTela;
		        String comandoIfObrigatorio;
		        String comandoElseObrigatorio; 
		        boolean exigeVariavelNaCondicao; 

		        public FaseCond(int id, String comando, String cmdIf, String cmdElse, boolean exigeVar) {
		            this.idFase = id;
		            this.comandoTela = comando;
		            this.comandoIfObrigatorio = cmdIf;
		            this.comandoElseObrigatorio = cmdElse;
		            this.exigeVariavelNaCondicao = exigeVar;
		        }
		    }
	
	 //-------------------------VERIFICAÇÕES----------------------------------------
	
	// Verificacao via Regex se existe uma funcao main() em qualquer lugar do codigo
	public static int verificarMain(String codigo) throws IllegalArgumentException {
		Pattern pattern = Pattern.compile("int\\s+main\\s*\\(\\s*\\)\\s*\\{[\\s\\S]*?\\}\\s*");
		Matcher matcher = pattern.matcher(codigo);
		if (!matcher.find()) return 1; //throw new IllegalArgumentException("A sua Funcao Principal nao foi Encontrada ou Esta Mal Declarada!");
		else if (matcher.find()) return 2; //throw new IllegalArgumentException("Houve mais de uma declaracao de main no seu codigo! Apenas um main() eh Permitido");
		return 0;
	}
	
	public static void verificarChaves(String[] codigo) throws IllegalArgumentException{
		int abreChave = 0;
		int fechaChave = 0;
		
		// Contando onde comeca o Main()
		for(String linha : codigo) {
			if (Interpretador.verificarAbertura(linha)) abreChave++;
			if (Interpretador.verificarFechamento(linha)) fechaChave++;
		}
		
		if (abreChave > fechaChave) throw new IllegalArgumentException("Foram Detectados Chaves `{` que nao tem `}`");
		if (abreChave < fechaChave) throw new IllegalArgumentException("Foram Detectados Chaves `}` em excesso");
	}
	// Verificar onde Comeca a {
	public static boolean verificarAbertura(String linha){
		
		Pattern pattern = Pattern.compile("\\{");
		Matcher matcher = pattern.matcher(linha);
		if (matcher.find()) return true;
		return false;
	}
	// Verificar onde Termina a }
	public static boolean verificarFechamento(String linha){
		Pattern pattern = Pattern.compile("\\}");
		Matcher matcher = pattern.matcher(linha);
		if (matcher.find()) return true;
		return false;
	}
	
	public static int verificarTipo(String tipo) {
		switch(tipo) {
			case "int":
				return 1;
			case "float":
				return 2;
			case "char":
				return 3;
			case "bool":
				return 4;
			default:
				return 0;
		}
	}
	
	//Verifica o código de condicionais
	public static boolean verificarCodigoCondicionais(String codigoDoJogador, FaseCond faseAtual) {
        if (codigoDoJogador == null || codigoDoJogador.trim().isEmpty()) {
            return false;
        }

        Matcher matcher = PATTERN_ESTRUTURAL.matcher(codigoDoJogador.trim());

        if (!matcher.matches()) {
            System.out.println("Erro: Estrutura do if/else escrita incorretamente.");
            return false;
        }
		
        String condicaoEntreParenteses = matcher.group("condicao").trim();
        String textoDentroDoIf = matcher.group("blocoIf").trim();
        String textoDentroDoElse = matcher.group("blocoElse");

        
        ArrayList<String> listaTokens = tokenizarExpressao(condicaoEntreParenteses);
        
        // Validação da regra da fase (se exige variável na condição)
        if (faseAtual.exigeVariavelNaCondicao) {
            boolean encontrouVariavelValida = false;
            for (String token : listaTokens) {
                if (Interpretador.variaveisInt.containsKey(token) || 
                    Interpretador.variaveisFloat.containsKey(token) || 
                    Interpretador.variaveisChar.containsKey(token) || 
                    Interpretador.variaveisBool.containsKey(token)) {
                    encontrouVariavelValida = true;
                    break;
                }
            }
            if (!encontrouVariavelValida) {
                System.out.println("Erro: Esta fase exige que você use pelo menos uma variável declarada na condição!");
                return false;
            }
        }

        boolean resultadoExpressao = false;


        try {
            // passa o ArrayList para o substituirVariavel() para trocar os nomes pelos valores reais
            listaTokens = Interpretador.substituirVariavel(listaTokens);
            
            // passa o ArrayList atualizado para o calcularExpressaoLogica(), que retorna o booleano final
            resultadoExpressao = Interpretador.calcularExpressaoLogica(listaTokens);
            
        } catch (IllegalArgumentException e) {
            System.out.println("Erro na análise da condição: " + e.getMessage());
            return false;
        }

        // Validação dos blocos com base no resultado booleano obtido
        if (resultadoExpressao) {
            if (!textoDentroDoIf.contains(faseAtual.comandoIfObrigatorio)) {
                System.out.println("Erro: Faltou executar o comando correto dentro do bloco 'if'.");
                return false;
            }
            System.out.println("Condição VERDADEIRA: Bloco 'if' executado com sucesso!");
        } else {
            if (faseAtual.comandoElseObrigatorio != null) {
                if (textoDentroDoElse == null) {
                    System.out.println("Erro: A condição resultou em FALSE, mas você não adicionou o bloco 'else'.");
                    return false;
                }
                if (!textoDentroDoElse.trim().contains(faseAtual.comandoElseObrigatorio)) {
                    System.out.println("Erro: Faltou executar o comando correto dentro do bloco 'else'.");
                    return false;
                }
                System.out.println("Condição FALSA: Bloco 'else' executado com sucesso!");
            } else {
                System.out.println("A condição do 'if' resultou em FALSE. Teste falhou.");
                return false;
            }
        }

        if (faseAtual.comandoElseObrigatorio == null && textoDentroDoElse != null) {
            System.out.println("Erro: Remova o bloco 'else'. Esta etapa pede apenas uma estrutura de 'if' simples.");
            return false;
        }

        return true; 
    }

    public static boolean verificarNome(String nome) {

        if (!nome.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
            return false;
        }

        for (String palavra : palavras_reservadas) {
        	if (palavra.equals(nome)) return false;
        }
        
        return true;
    }
	
	private static double numero(String s) {
		try {
		    return Double.parseDouble(s);
		} catch (NumberFormatException e) {
		    throw new IllegalArgumentException("Número inválido: " + s);
		}
	}

	
	//----------------------CALCULOS----------------------------------------
	public static double calcular(ArrayList<String> lista) throws ArithmeticException, IllegalArgumentException{
		// Primeiro resolve *, / e %
        for (int i = 1; i < lista.size() - 1;) {

            String op = lista.get(i);

            if (op.equals("*") || op.equals("/") || op.equals("%")) {

                double a = numero(lista.get(i - 1));
                double b = numero(lista.get(i + 1));

                double resultado;

                switch (op) {
                    case "*":
                        resultado = a * b;
                        break;

                    case "/":
                        if (b == 0)
                            throw new ArithmeticException("Divisão por zero!");
                        resultado = a / b;
                        break;

                    case "%":
                        resultado = a % b;
                        break;

                    default:
                        throw new IllegalArgumentException("Operador Invalido!");
                }

                lista.set(i - 1, String.valueOf(resultado));
                lista.remove(i);     // operador
                lista.remove(i);     // segundo número

            } else {
                i += 2;
            }
        }

        // Depois resolve + e -
        double resultado = numero(lista.get(0));

        for (int i = 1; i < lista.size(); i += 2) {

            String op = lista.get(i);
            double valor = numero(lista.get(i + 1));

            switch (op) {
                case "+":
                    resultado += valor;
                    break;

                case "-":
                    resultado -= valor;
                    break;

                default:
                    throw new IllegalArgumentException("Operador inválido: " + op);
            }
        }

        return resultado;
	}
	
	// Calcula 1 Expressao Logica
	public static boolean calcularLogica(String operador, double a, double b) {

	    switch (operador) {

	        case "==":
	            return a == b;

	        case "!=":
	            return a != b;

	        case ">":
	            return a > b;

	        case "<":
	            return a < b;

	        case ">=":
	            return a >= b;

	        case "<=":
	            return a <= b;

	        default:
	            throw new IllegalArgumentException("Operador lógico inválido: " + operador);
	    }
	}
	
	public static boolean calcularExpressaoLogica(List<String> tokens) {

	    // Resolve comparações
	    for (int i = 1; i < tokens.size() - 1;) {

	        String op = tokens.get(i);

	        if (op.matches("==|!=|>=|<=|>|<")) {

	            double a = Double.parseDouble(tokens.get(i - 1));
	            double b = Double.parseDouble(tokens.get(i + 1));

	            boolean r = calcularLogica(op, a, b);

	            tokens.set(i - 1, String.valueOf(r));
	            tokens.remove(i);
	            tokens.remove(i);

	        } else {
	            i += 2;
	        }
	    }

	    // Resolve &&
	    for (int i = 1; i < tokens.size() - 1;) {

	        if (tokens.get(i).equals("&&")) {

	            boolean a = Boolean.parseBoolean(tokens.get(i - 1));
	            boolean b = Boolean.parseBoolean(tokens.get(i + 1));

	            tokens.set(i - 1, String.valueOf(a && b));
	            tokens.remove(i);
	            tokens.remove(i);

	        } else {
	            i += 2;
	        }
	    }

	    // Resolve ||
	    for (int i = 1; i < tokens.size() - 1;) {

	        if (tokens.get(i).equals("||")) {

	            boolean a = Boolean.parseBoolean(tokens.get(i - 1));
	            boolean b = Boolean.parseBoolean(tokens.get(i + 1));

	            tokens.set(i - 1, String.valueOf(a || b));
	            tokens.remove(i);
	            tokens.remove(i);

	        } else {
	            i += 2;
	        }
	    }

	    return Boolean.parseBoolean(tokens.get(0));
	}
	
	public static ArrayList<String> substituirVariavel(ArrayList<String> elementos) throws IllegalArgumentException {
		for (int i = 0; i < elementos.size(); i++) {

	        // Ignora operadores
	        if (elementos.get(i).matches("\\+|-|\\*|/|%|==|!=|>=|<=|>|<|&&|\\|\\||!|=")) continue;

	        // É um char?
	        if (elementos.get(i).matches("'[^']'")) continue;

	        // Número inteiro
	        if (elementos.get(i).matches("-?\\d+")) continue;

	        // Número decimal
	        if (elementos.get(i).matches("-?\\d+\\.\\d+")) continue;
	        
	        // Entao eh Variavel
	        if(variaveisInt.get(elementos.get(i)) != null) {
	        	elementos.set(i, "" + variaveisInt.get(elementos.get(i)));
	        	continue;
	        }
	        if(variaveisFloat.get(elementos.get(i)) != null) {
	        	elementos.set(i, "" + variaveisFloat.get(elementos.get(i)));
	        	continue;
	        }
	        if(variaveisChar.get(elementos.get(i)) != null) {
	        	elementos.set(i, "" + variaveisChar.get(elementos.get(i)));
	        	continue;
	        }
	        if(variaveisBool.get(elementos.get(i)) != null) {
	        	elementos.set(i, "" + variaveisBool.get(elementos.get(i)));
	        	continue;
	        }
	        
	        throw new IllegalArgumentException("Existe um elemento nao declarado na Operacao!");
	    }
		
        return elementos;
	}
	
	// Associa uma variavel Int ao seu valor
	public static void adicionarVariavelInt(String linha) throws IllegalArgumentException{
		String[] elementos = linha.split(linha);
		
		if (elementos.length == 0) return;
		if (elementos.length < 4) throw new  IllegalArgumentException("Declaracao de Variavel incorreta!");
		
		
		if (verificarTipo(elementos[0]) != 1) throw new  IllegalArgumentException("Tipo incorreto de Variavel!");
		
		if (!verificarNome(elementos[1])) throw new  IllegalArgumentException("Nome de Variavel Invalido!");
		
		if (!elementos[2].equals("=")) throw new  IllegalArgumentException("Operador Incorreto!");
		
		ArrayList<String> lista = new ArrayList<>();
		for (int i = 3; i < elementos.length - 1; i++) {
			lista.add(elementos[i]);
		}   			
		
		lista = substituirVariavel(lista);
		
		double resultado = calcular(lista);
		
		if (resultado == (int) resultado) {
			variaveisInt.put(elementos[1], (int) resultado);
		}else throw new  IllegalArgumentException("O valor inserido nao eh Compativel com o Tipo Inteiro!");
	}
	
	// Associa uma variavel Float ao seu valor
	public static void adicionarVariavelFloat(String linha) throws IllegalArgumentException{
		String[] elementos = linha.split(linha);
		
		if (elementos.length == 0) return;
		if (elementos.length < 4) throw new  IllegalArgumentException("Declaracao de Variavel incorreta!");
		
		
		if (verificarTipo(elementos[0]) != 2) throw new  IllegalArgumentException("Tipo incorreto de Variavel!");
		
		if (!verificarNome(elementos[1])) throw new  IllegalArgumentException("Nome de Variavel Invalido!");
		
		if (!elementos[2].equals("=")) throw new  IllegalArgumentException("Operador Incorreto!");
		
		ArrayList<String> lista = new ArrayList<>();
		for (int i = 3; i < elementos.length - 1; i++) {
			lista.add(elementos[i]);
		}   			
		
		lista = substituirVariavel(lista);
		
		double resultado = calcular(lista);
		
		variaveisFloat.put(elementos[1], resultado);
	}
	
	// Associa uma variavel Char ao seu valor
		public static void adicionarVariavelChar(String linha) throws IllegalArgumentException{
			String[] elementos = linha.split(linha);
			
			if (elementos.length == 0) return;
			if (elementos.length != 4) throw new  IllegalArgumentException("Declaracao de Variavel incorreta!");
			
			
			if (verificarTipo(elementos[0]) != 3) throw new  IllegalArgumentException("Tipo incorreto de Variavel!");
			
			if (!verificarNome(elementos[1])) throw new  IllegalArgumentException("Nome de Variavel Invalido!");
			
			if (!elementos[2].equals("=")) throw new  IllegalArgumentException("Operador Incorreto!");
			
			if (!elementos[3].matches("'[^']'")) throw new IllegalArgumentException("O tipo Char nao Aceita esse formato");
		    
			variaveisChar.put(elementos[1], elementos[3].charAt(1));
		}
		
		public static void adicionarVariavelBoolean(String linha) throws IllegalArgumentException{
			String[] elementos = linha.split(linha);
			
			if (elementos.length == 0) return;
			if (elementos.length != 4) throw new  IllegalArgumentException("Declaracao de Variavel incorreta!");
			
			if (verificarTipo(elementos[0]) != 3) throw new  IllegalArgumentException("Tipo incorreto de Variavel!");
			
			if (!verificarNome(elementos[1])) throw new  IllegalArgumentException("Nome de Variavel Invalido!");
			
			if (!elementos[2].equals("=")) throw new  IllegalArgumentException("Operador Incorreto!");
			
			ArrayList<String> lista = new ArrayList<>();
			for (int i = 3; i < elementos.length - 1; i++) {
				lista.add(elementos[i]);
			}   			
			
			lista = substituirVariavel(lista);
			
			boolean resultado = calcularExpressaoLogica(lista);
			
			variaveisChar.put(elementos[1], elementos[3].charAt(1));
		}
	
	// Operaccao Solo ++ e -- e !
	
	public static void verificarOperacaoDupla(String elemento1, String elemento2, String operador) throws IllegalArgumentException{
		if (verificarOperador(operador) == 0 || verificarOperador(operador) == 1) throw new IllegalArgumentException("Em uma das suas operacoes voce usou: " + operador + "\nNao eh um operador Valido!");
		
		if (operador == "+" || operador == "-" || operador == "/" || operador == "*" || operador == "%" || 
				operador == ">" || operador == "<"  || operador == ">="  || operador == "<=") {
			try {
				int numero = Integer.valueOf(elemento1);
				
			}catch(Exception e) {
				try {
					float numero = Float.valueOf(elemento1);
				}catch(Exception er) {
					throw new IllegalArgumentException("O Elemento: " + elemento1 + "\nEsta sofrendo uma operacao invalida!");
				}
			}
			
			try {
				int numero = Integer.valueOf(elemento2);
				
			}catch(Exception e) {
				try {
					float numero = Float.valueOf(elemento2);
				}catch(Exception er) {
					throw new IllegalArgumentException("O Elemento: " + elemento2 + "\nEsta sofrendo uma operacao invalida!");
				}
			}
		}
		else if(operador == "=" || operador == "+=" || operador == "-=" || operador == "/=" || operador == "*=" ) {
			// Verifica o Primeiro Elemento
			try {
				int numero = Integer.valueOf(elemento1);
				
			}catch(Exception e) {
				try {
					float numero = Float.valueOf(elemento1);
				}catch(Exception er) {
					// Verificar se o priomeiro elemento eh uma variavel e pode receber o segundo elemento
					return;
				}
				throw new IllegalArgumentException("O Elemento: " + elemento1+ "\nEsta sofrendo uma operacao invalida!");
			}
			throw new IllegalArgumentException("O Elemento: " + elemento1 + "\nEsta sofrendo uma operacao invalida!");
		}
		else if(operador == "!=" || operador == "==" || operador == "&&" || operador == "||") {
			// Verificar se ambos sao variaveis ou booleanos ou numeros
		}
	}
	
	// Verificar se eh uma operacao Valida
	public static int verificarOperador(String operador) {
		String[] operadores = {
			    "+", "-", "*", "/", "%",
			    "++", "--",
			    "=", "+=", "-=", "*=", "/=", "%=",
			    "==", "!=", ">", "<", ">=", "<=",
			    "&&", "||", "!"
			};
		
		for (String op : operadores) {
			if (op.equals(operador)) {
				if (op == "++" || op == "--" || op == "!") return 1;
				else return 2;
			}
		}
		
		return 0;
	}
	
	 private static ArrayList<String> tokenizarExpressao(String expressao) {
	        ArrayList<String> tokens = new ArrayList<>();
	        
	        // Captura operadores compostos primeiro, depois simples, números e palavras 
	        Pattern p = Pattern.compile("==|!=|>=|<=|&&|\\|\\||[<>+\\-*/%=!]|[a-zA-Z_][a-zA-Z0-9_]*|\\d+");
	        Matcher m = p.matcher(expressao);
	        
	        while (m.find()) {
	            tokens.add(m.group());
	        }
	        
	        return tokens;
	    }
	 
	   // Limpar HashMap
		public static void limparVariaveis() {
			variaveisInt = new HashMap<String, Integer>();
			variaveisFloat = new HashMap<String, Double>();
			variaveisBool = new HashMap<String, Boolean>();
			variaveisChar = new HashMap<String, Character>();
			
		}
}
