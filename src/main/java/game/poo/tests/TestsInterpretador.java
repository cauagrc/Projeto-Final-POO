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
		/*
		String codigo = "int main() {return 1;}";
		
		int resultado = Interpretador.verificarMain(codigo);
        System.out.println("Resultado: " + resultado);
        */
		
		Interpretador.adicionarVariavelInt("int idade = 0;");
        Interpretador.adicionarVariavelFloat("float altura = 0.0;");
        Interpretador.adicionarVariavelChar("char letra = 'A';");
        
		System.out.println(Interpretador.verificarScanf("scanf(\"%d\", &idade);"));

		System.out.println(Interpretador.verificarScanf("scanf(\"%f\", &altura);"));

		System.out.println(Interpretador.verificarScanf("scanf(\"%c\", &letra);"));

		System.out.println(Interpretador.verificarScanf("scanf(\"%f\", &idade);"));
		
		System.out.println(Interpretador.verificarPrintf("printf(\"%d\", idade);"));

		System.out.println(Interpretador.verificarPrintf("printf(\"%f\", altura);"));

		System.out.println(Interpretador.verificarPrintf("printf(\"%c\", letra);"));

		System.out.println(Interpretador.verificarPrintf("printf(\"%f\", idade);"));
	}

}
