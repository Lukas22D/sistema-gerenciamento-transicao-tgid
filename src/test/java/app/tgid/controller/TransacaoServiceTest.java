package app.tgid.controller;


import app.tgid.model.Cliente;
import app.tgid.model.Empresa;
import app.tgid.model.Transacao;
import app.tgid.repository.ClienteRepository;
import app.tgid.repository.EmpresaRepository;
import app.tgid.repository.TransacaoRepository;
import app.tgid.service.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class TransacaoServiceTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TransacaoService transacaoService;

    private Cliente cliente;
    private Empresa empresa;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Setup de um cliente de exemplo
        cliente = new Cliente();
        cliente.setcpfCliente("12345678909");
        cliente.setEmail("cliente@example.com");

        // Setup de uma empresa de exemplo
        empresa = new Empresa();
        empresa.setCnpj("98765432100");
        empresa.setSaldo(BigDecimal.valueOf(1000.00));
        empresa.setTaxaAdministracao(BigDecimal.valueOf(50.00));
    }

    @Test
    public void realizarTransacao_deveRealizarTransacaoComSucesso() {
        // Mock dos repositórios
        when(clienteRepository.findByCpfCliente("12345678909")).thenReturn(cliente);
        when(empresaRepository.findByCnpj("98765432100")).thenReturn(empresa);
        when(transacaoRepository.save(any(Transacao.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Realizar transação
        empresa.setSaldo(BigDecimal.valueOf(1000.00));
        BigDecimal valor = BigDecimal.valueOf(500.00);
        BigDecimal taxa = BigDecimal.valueOf(10.00);
        Transacao transacao = transacaoService.realizarTransacao("12345678909", "98765432100", valor, "deposito", taxa);
        
        // Verificações
        assertNotNull(transacao);
        assertEquals(cliente, transacao.getCliente());
        assertEquals(empresa, transacao.getEmpresa());
        assertEquals(BigDecimal.valueOf(489.00), transacao.getValor().setScale(1));
    }
}