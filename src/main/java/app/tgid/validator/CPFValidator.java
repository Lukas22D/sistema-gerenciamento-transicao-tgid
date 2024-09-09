package app.tgid.validator;

public class CPFValidator {

    // Método que valida o CPF
    public  boolean isValidCPF(String cpf) {
        // Remove pontuações, caso o CPF tenha sido formatado com pontos e traços
        cpf = cpf.replaceAll("\\D", "");

        // Verifica se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais, o que seria um CPF inválido
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Calcula o primeiro dígito verificador
        int primeiroDigitoVerificador = calcularDigitoVerificador(cpf.substring(0, 9));
        // Calcula o segundo dígito verificador
        int segundoDigitoVerificador = calcularDigitoVerificador(cpf.substring(0, 9) + primeiroDigitoVerificador);

        // Verifica se os dígitos calculados correspondem aos dígitos fornecidos no CPF
        return cpf.equals(cpf.substring(0, 9) + primeiroDigitoVerificador + segundoDigitoVerificador);
    }

    // Método auxiliar para calcular o dígito verificador
    private static int calcularDigitoVerificador(String cpfParcial) {
        int soma = 0;
        int peso = cpfParcial.length() + 1;

        // Realiza o cálculo de multiplicação e soma dos dígitos
        for (int i = 0; i < cpfParcial.length(); i++) {
            int digito = Character.getNumericValue(cpfParcial.charAt(i));
            soma += digito * peso--;
        }

        int resto = soma % 11;
        if (resto < 2) {
            return 0;
        } else {
            return 11 - resto;
        }
    }

}

