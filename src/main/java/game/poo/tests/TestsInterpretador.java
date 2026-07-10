package game.poo.tests;

import Elements.Interpretador;

public class TestsInterpretador {

	public static void main(String[] args) {
		
		/*
		Interpretador.limparVariaveis();

		
        String codigo = """
            int main() {
        		return 0;
            
            """;

        int resultado = Interpretador.verificarMain(codigo);
        System.out.println("Resultado: " + resultado);
        */

		
		Interpretador.limparVariaveis();
		
		String codigo = "int main() {return 1;}";
		
		int resultado = Interpretador.verificarMain(codigo);
        System.out.println("Resultado: " + resultado);
	}

}
