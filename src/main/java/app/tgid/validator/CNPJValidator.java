package app.tgid.validator;

public class CNPJValidator {

    // Método que valida o CNPJ
    public boolean isValidCNPJ(String cnpj) {
        // Remove pontuações, caso o CNPJ tenha sido formatado com pontos, barras e traços
        cnpj = cnpj.replaceAll("\\D", "");

        // Verifica se o CNPJ tem 14 dígitos
        if (cnpj.length() != 14) {
            return false;
        }

        // Verifica se todos os dígitos são iguais, o que seria um CNPJ inválido
        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        // Calcula o primeiro dígito verificador
        int primeiroDigitoVerificador = calcularDigitoVerificador(cnpj.substring(0, 12), new int[]{5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});
        // Calcula o segundo dígito verificador
        int segundoDigitoVerificador = calcularDigitoVerificador(cnpj.substring(0, 12) + primeiroDigitoVerificador, new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});

        // Verifica se os dígitos calculados correspondem aos dígitos fornecidos no CNPJ
        return cnpj.equals(cnpj.substring(0, 12) + primeiroDigitoVerificador + segundoDigitoVerificador);
    }

    // Método auxiliar para calcular o dígito verificador
    private static int calcularDigitoVerificador(String cnpjParcial, int[] pesos) {
        int soma = 0;

        // Realiza o cálculo de multiplicação e soma dos dígitos
        for (int i = 0; i < cnpjParcial.length(); i++) {
            int digito = Character.getNumericValue(cnpjParcial.charAt(i));
            soma += digito * pesos[i];
        }

        int resto = soma % 11;
        if (resto < 2) {
            return 0;
        } else {
            return 11 - resto;
        }
    }
}
