package Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Interpretador {
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
	
	// Verificar onde Comeca a Area do Main()
	public static boolean verificarAbertura(String linha){
		Pattern pattern = Pattern.compile("\\{");
		Matcher matcher = pattern.matcher(linha);
		if (matcher.find()) return true;
		return false;
	}
	
	public static boolean verificarFechamento(String linha){
		Pattern pattern = Pattern.compile("\\}");
		Matcher matcher = pattern.matcher(linha);
		if (matcher.find()) return true;
		return false;
	}
	
	// Operaccao Solo ++ e -- e !
	
	public static void verificarOperacaoDupla(String elemento1, String elemento2, String operador) throws IllegalArgumentException{
		if (verificarOperador(operador) == 0 || verificarOperador(operador) == 1) throw new IllegalArgumentException("Em uma das suas operacoes voce usou: " + operador + "\nNao eh um operador Valido!");
		
		if (operador == "+" || operador == "-" || operador == "/" || operador == "*" || operador == "%" || 
				operador == "+=" || operador == "-=" || operador == "/=" || operador == "*=" || operador == ">" ||
				operador == "<"  || operador == ">="  || operador == "<=") {
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
		else if(operador == "=") {
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
}
