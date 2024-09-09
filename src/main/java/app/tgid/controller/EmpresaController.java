package app.tgid.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import app.tgid.model.Empresa;
import app.tgid.repository.EmpresaRepository;
import app.tgid.validator.CNPJValidator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador responsável por gerenciar as operações relacionadas à entidade Empresa.
 * <p>
 * Este controlador fornece APIs para manipular os dados de empresas no sistema, permitindo
 * buscar, cadastrar e atualizar empresas. Ele inclui validações de CNPJ antes de realizar
 * operações de persistência no banco de dados.
 * </p>
 * 
 * <p>
 * Funcionalidades principais:
 * <ul>
 *     <li>Buscar todas as empresas cadastradas.</li>
 *     <li>Buscar uma empresa específica pelo seu ID.</li>
 *     <li>Salvar uma nova empresa no banco de dados, com validação de CNPJ.</li>
 *     <li>Atualizar os dados de uma empresa existente.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Esta classe faz uso dos seguintes componentes:
 * <ul>
 *     <li>{@link EmpresaRepository}: Responsável pela persistência e acesso aos dados das empresas.</li>
 *     <li>{@link CNPJValidator}: Utilizado para validar o CNPJ das empresas antes de realizar operações.</li>
 * </ul>
 * </p>
 */
@RestController
@Tag(name = "Empresa API", description = "APIs para operações com Empresas")
public class EmpresaController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private CNPJValidator cnpjValidator;

    /**
     * Busca todas as empresas cadastradas no sistema.
     * <p>
     * Esta operação recupera todas as empresas registradas no banco de dados e retorna como uma lista.
     * Caso não haja nenhuma empresa cadastrada, uma exceção é lançada.
     * </p>
     *
     * @return Lista de todas as empresas cadastradas.
     * @throws IllegalArgumentException Se não houver empresas cadastradas no sistema.
     */
    @Operation(summary = "Busca todas as empresas", description = "Retorna uma lista de todas as empresas cadastradas")
    @GetMapping("/empresa")
    public List<Empresa> buscarEmpresas() {
        var resultado = empresaRepository.findAll();
        if (resultado.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma empresa encontrada!");
        }
        return resultado;
    }

    /**
     * Busca uma empresa específica pelo seu identificador (ID).
     * <p>
     * Esta operação retorna uma empresa com base no ID fornecido. Se a empresa com o ID especificado
     * não for encontrada, uma exceção será lançada.
     * </p>
     *
     * @param id Identificador único da empresa.
     * @return A empresa correspondente ao ID fornecido.
     * @throws IllegalArgumentException Se a empresa com o ID fornecido não for encontrada.
     */
    @Operation(summary = "Busca uma empresa pelo ID", description = "Retorna uma empresa específica com base no ID fornecido")
    @GetMapping("/empresa/{id}")
    public Empresa buscarEmpresa(@PathVariable int id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada!"));
    }

    /**
     * Adiciona uma nova empresa ao banco de dados.
     * <p>
     * Antes de salvar uma nova empresa, o CNPJ fornecido é validado usando o {@link CNPJValidator}.
     * Se o CNPJ for inválido, uma exceção será lançada. Caso contrário, a empresa será salva e uma
     * mensagem de sucesso será retornada.
     * </p>
     *
     * @param empresa Dados da empresa a serem salvos no banco.
     * @return Mensagem de sucesso informando que a empresa foi salva com sucesso.
     * @throws IllegalArgumentException Se o CNPJ da empresa for inválido.
     */
    @Operation(summary = "Salva uma nova empresa", description = "Adiciona uma nova empresa ao banco de dados")
    @PostMapping("/empresa")
    public String salvarEmpresa(@RequestBody Empresa empresa) {
        if (cnpjValidator.isValidCNPJ(empresa.getCnpj())) {
            empresaRepository.save(empresa);
            return "Empresa salva com sucesso!";
        } else {
            throw new IllegalArgumentException("CNPJ inválido!");
        }
    }

    /**
     * Atualiza os dados de uma empresa existente no banco de dados.
     * <p>
     * Esta operação permite a atualização dos dados de uma empresa já existente. Antes da atualização,
     * o ID da empresa é verificado para garantir que a empresa exista no banco de dados. Se a empresa não
     * for encontrada, uma exceção será lançada.
     * </p>
     *
     * @param empresa Dados atualizados da empresa.
     * @return Mensagem de sucesso informando que a empresa foi atualizada com sucesso.
     * @throws IllegalArgumentException Se a empresa com o ID fornecido não for encontrada.
     */
    @Operation(summary = "Atualiza uma empresa", description = "Atualiza os dados de uma empresa existente")
    @PutMapping("/empresa")
    public String atualizarEmpresa(@RequestBody Empresa empresa) {
        if (empresaRepository.existsById(empresa.getId())) {
            empresaRepository.save(empresa);
            return "Empresa atualizada com sucesso!";
        } else {
            throw new IllegalArgumentException("Empresa não encontrada!");
        }
    }
}
