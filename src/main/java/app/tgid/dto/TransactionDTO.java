package app.tgid.dto;

import java.math.BigDecimal;

public class TransactionDTO {
    private String cpf_cliente;
    private String cnpj_empresa;
    private BigDecimal valor;
    private String tipo;
    private BigDecimal taxa;

    public String getCpf_cliente() {
        return cpf_cliente;
    }

    public void setCpf_cliente(String cpf_cliente) {
        this.cpf_cliente = cpf_cliente;
    }

    public String getCnpj_empresa() {
        return cnpj_empresa;
    }

    public void setCnpj_empresa(String cnpj_empresa) {
        this.cnpj_empresa = cnpj_empresa;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getTaxa() {
        return taxa;
    }

    public void setTaxa(BigDecimal taxa) {
        this.taxa = taxa;
    }

    @Override
    public String toString() {
        return "TransactionDTO [cpf_cliente=" + cpf_cliente + ", cnpj_empresa=" + cnpj_empresa + ", taxa=" + taxa
                + ", tipo=" + tipo + ", valor=" + valor + "]";
    }
}
