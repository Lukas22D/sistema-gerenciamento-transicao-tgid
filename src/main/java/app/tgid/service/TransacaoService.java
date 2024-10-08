package app.tgid.service;

import app.tgid.model.Cliente;
import app.tgid.model.Empresa;
import app.tgid.model.Transacao;
import app.tgid.repository.ClienteRepository;
import app.tgid.repository.EmpresaRepository;
import app.tgid.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Serviço responsável pela gestão e execução de transações entre clientes e empresas.
 * <p>
 * Este serviço oferece funcionalidades para realizar transações financeiras, como saque e depósito,
 * entre um cliente e uma empresa. Ele calcula taxas administrativas, valida o saldo da empresa
 * para saques e atualiza os saldos após a transação.
 * <p>
 * Além disso, o serviço envia notificações, como callbacks para a empresa e e-mails de confirmação
 * para o cliente, após a realização da transação.
 * </p>
 *
 * <p>
 * Principais responsabilidades:
 * <ul>
 *     <li>Realização de transações entre clientes e empresas.</li>
 *     <li>Cálculo de taxas administrativas e manipulação de saldos.</li>
 *     <li>Notificação do cliente por e-mail e da empresa por callback.</li>
 * </ul>
 * </p>
 * 
 * Dependências utilizadas:
 * <ul>
 *     <li>{@link ClienteRepository}: Para acessar e manipular dados de clientes.</li>
 *     <li>{@link EmpresaRepository}: Para acessar e manipular dados de empresas.</li>
 *     <li>{@link TransacaoRepository}: Para salvar e recuperar informações de transações.</li>
 *     <li>{@link JavaMailSender}: Para enviar notificações de e-mail aos clientes.</li>
 *     <li>{@link RestTemplate}: Para enviar callbacks HTTP às empresas.</li>
 * </ul>
 */
@Service
public class TransacaoService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Realiza uma transação entre um cliente e uma empresa, aplicando taxas e atualizando o saldo da empresa.
     * <p>
     * Esta operação realiza tanto saques quanto depósitos entre um cliente e uma empresa. O saldo da
     * empresa é ajustado com base no tipo da transação. Se for um saque, o saldo da empresa é reduzido,
     * e se for um depósito, o saldo é aumentado. Uma taxa de administração é aplicada antes de finalizar
     * a transação. A transação é então registrada no banco de dados, e notificações (callback e e-mail) são enviadas.
     * </p>
     *
     * @param clienteId        Identificador do cliente (CPF).
     * @param empresaId        Identificador da empresa (CNPJ).
     * @param valor            Valor da transação.
     * @param tipo             Tipo da transação ("saque" ou "depósito").
     * @param taxaSistema Taxa SIstema aplicada à transação (se aplicável).
     * @return A transação realizada e salva no banco de dados.
     * @throws IllegalArgumentException Se o cliente ou empresa não for encontrado, ou se o saldo da empresa for insuficiente para o saque.
     */
    public Transacao realizarTransacao(String clienteId, String empresaId, BigDecimal valor, String tipo, BigDecimal taxaSistema) {
        // Buscar Cliente e Empresa no banco
        Cliente cliente = clienteRepository.findByCpfCliente(clienteId);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }
        Empresa empresa = empresaRepository.findByCnpj(empresaId);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }

        // Calcular a taxa da empresa
        BigDecimal taxa = calcularTaxa(empresa, taxaSistema);
        BigDecimal valorFinal = valor.subtract(taxa);

        // Verificar se a transação é válida (se for saque, verificar saldo da empresa)
        if (tipo.equalsIgnoreCase("saque") && empresa.getSaldo().compareTo(valorFinal) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente na empresa");
        }

        // Criar a transação
        Transacao transacao = new Transacao();
        transacao.setCliente(cliente);
        transacao.setEmpresa(empresa);
        transacao.setValor(valorFinal);
        transacao.setTipo(tipo);
        transacao.setDataHora(LocalDateTime.now());

        // Atualizar saldo da empresa (para saques e depósitos)
        if (tipo.equalsIgnoreCase("deposito")) {
            empresa.setSaldo(empresa.getSaldo().add(valorFinal));
        } else if (tipo.equalsIgnoreCase("saque")) {
            empresa.setSaldo(empresa.getSaldo().subtract(valorFinal));
        }
        empresaRepository.save(empresa);

        // Salvar a transação
        Transacao transacaoSalva = transacaoRepository.save(transacao);

        // Enviar callback para a empresa (simulação)
        enviarCallback(empresa, transacaoSalva);
        // Enviar e-mail para o cliente (simulação)
        enviarEmail(cliente);

        return transacaoSalva;
    }

    /**
     * Calcula a taxa a ser aplicada na transação de acordo com a taxa de administração da empresa
     * e a taxa de administração adicional fornecida.
     * 
     * @param empresa Empresa para a qual a taxa será calculada.
     * @param taxaSistema Taxa de Sistema adicional fornecida na transação.
     * @return A taxa total calculada.
     */
    private BigDecimal calcularTaxa(Empresa empresa, BigDecimal taxaSistema) {
        // Simulando uma taxa de administração de 2% sobre o valor da transação
        BigDecimal taxa = empresa.getTaxaAdministracao().multiply(BigDecimal.valueOf(0.02));
        if(taxaSistema != null) {
            // Adicionando taxa de sistema, simulando uma taxa fixa de acordo com o valor informado
            taxa = taxa.add(taxaSistema);
        }
        return taxa;        
    }

    /**
     * Envia um callback para a empresa após a transação ser realizada.
     * <p>
     * Simula o envio de um callback utilizando o {@link RestTemplate} para realizar uma chamada HTTP POST
     * para uma URL de callback fornecida. Esta função é utilizada para notificar a empresa após a
     * finalização da transação.
     * </p>
     *
     * @param empresa   Empresa que receberá o callback.
     * @param transacao Transação realizada.
     */
    private void enviarCallback(Empresa empresa, Transacao transacao) {
        String callbackUrl = "https://webhook.site/82b07e34-2b90-45fa-bfbf-b547b7597ae1";  // URL de exemplo
        try {
            restTemplate.postForEntity(callbackUrl, transacao, String.class);
        } catch (Exception e) {
            System.out.println("Erro ao enviar callback para a empresa: " + e.getMessage());
        }
    }

    /**
     * Envia um e-mail de confirmação para o cliente, informando que a transação foi realizada com sucesso.
     * <p>
     * Utiliza o {@link JavaMailSender} para enviar um e-mail simples ao cliente após a realização da transação.
     * </p>
     *
     * @param cliente Cliente que receberá o e-mail de confirmação.
     */
    private void enviarEmail(Cliente cliente) {
        String assunto = "Transação realizada com sucesso";
        String corpo = "Sua transação foi realizada com sucesso!";
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(cliente.getEmail());
        mensagem.setSubject(assunto);
        mensagem.setText(corpo);
        mailSender.send(mensagem);
    }
}
