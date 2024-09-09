package app.tgid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import app.tgid.repository.TransacaoRepository;
import app.tgid.model.Transacao;
import app.tgid.dto.TransactionDTO;
import app.tgid.service.TransacaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador responsável pelas operações relacionadas às transações entre
 * clientes e empresas. Esta classe fornece APIs para buscar e realizar transações.
 * <p>
 * A transação consiste em operações de depósito ou saque que podem ser realizadas
 * entre uma empresa e um cliente. As APIs expostas permitem buscar todas as
 * transações registradas e registrar novas transações.
 * </p>
 *
 * <p>
 * Funcionalidades principais:
 * <ul>
 *     <li>Busca todas as transações registradas no sistema.</li>
 *     <li>Realiza uma nova transação, com validação de CPF do cliente e CNPJ da empresa.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Esta classe depende de dois componentes principais:
 * <ul>
 *     <li>{@link TransacaoRepository}: Responsável pelo acesso e persistência dos dados de transação.</li>
 *     <li>{@link TransacaoService}: Contém a lógica de negócio para realizar transações, incluindo validações e operações de saldo.</li>
 * </ul>
 * </p>
 */
@RestController
@Tag(name = "Transação API", description = "APIs para operações com Transações")
public class TransactionController {

    @Autowired
    private TransacaoRepository transactionRepository;

    @Autowired
    private TransacaoService transacaoService;

    /**
     * Busca todas as transações registradas no sistema.
     * <p>
     * Esta operação retorna uma lista de todas as transações registradas no banco de dados.
     * Se nenhuma transação for encontrada, uma exceção será lançada.
     * </p>
     *
     * @return Lista de todas as transações.
     * @throws IllegalArgumentException Se nenhuma transação for encontrada.
     */
    @Operation(summary = "Busca todas as transações", description = "Retorna uma lista de todas as transações registradas")
    @GetMapping("/transaction")
    public List<Transacao> buscarTransacoes() {
        List<Transacao> resultado = transactionRepository.findAll();
        if(resultado.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma transação encontrada!");
        }
        return resultado;
    }

    /**
     * Realiza uma nova transação entre um cliente e uma empresa.
     * <p>
     * Esta operação cria uma nova transação no sistema, que pode ser um depósito ou saque.
     * A transação é registrada com base nos dados fornecidos no DTO de transação, que inclui
     * informações como CPF do cliente, CNPJ da empresa, valor da transação, tipo (saque ou depósito),
     * e a taxa administrativa.
     * </p>
     *
     * <p>
     * A lógica de realização da transação, incluindo validações e atualizações de saldo da empresa,
     * é tratada pelo serviço {@link TransacaoService}.
     * </p>
     *
     * @param transactionDTO Dados da transação que será realizada (CPF do cliente, CNPJ da empresa, valor, tipo e taxa).
     * @return Mensagem de sucesso confirmando que a transação foi realizada.
     * @throws IllegalArgumentException Se os dados fornecidos forem inválidos, como CPF ou CNPJ incorretos, ou saldo insuficiente para saque.
     */
    @Operation(summary = "Realiza uma nova transação", description = "Registra uma nova transação entre um cliente e uma empresa")
    @PostMapping("/transaction")
    public String salvarTransacao(@RequestBody TransactionDTO transactionDTO) {
        transacaoService.realizarTransacao(
            transactionDTO.getCpf_cliente(),
            transactionDTO.getCnpj_empresa(),
            transactionDTO.getValor(),
            transactionDTO.getTipo(),
            transactionDTO.getTaxa()
        );
       
        return "Transação realizada com sucesso!";
    }
}
