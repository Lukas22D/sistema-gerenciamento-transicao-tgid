package app.tgid.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.tgid.model.Cliente;
import app.tgid.repository.ClienteRepository;
import app.tgid.validator.CPFValidator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador responsável por gerenciar as operações CRUD (Create, Read, Update, Delete)
 * relacionadas à entidade Cliente.
 * <p>
 * Este controlador oferece APIs que permitem interagir com os dados dos clientes no sistema,
 * oferecendo funcionalidades como buscar, adicionar, atualizar e deletar clientes.
 * Ele também realiza a validação de CPF antes de persistir os dados no banco.
 * </p>
 * 
 * <p>
 * Funcionalidades principais:
 * <ul>
 *     <li>Buscar todos os clientes cadastrados.</li>
 *     <li>Buscar um cliente específico pelo seu ID.</li>
 *     <li>Adicionar um novo cliente com validação de CPF.</li>
 *     <li>Atualizar os dados de um cliente existente.</li>
 *     <li>Deletar um cliente com base no seu ID.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Esta classe utiliza os seguintes componentes:
 * <ul>
 *     <li>{@link ClienteRepository}: Responsável pelo acesso e persistência dos dados dos clientes.</li>
 *     <li>{@link CPFValidator}: Utilizado para validar o CPF antes de realizar operações de cadastro ou atualização.</li>
 * </ul>
 * </p>
 */
@RestController
@Tag(name = "Cliente API", description = "APIs para operações com Clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CPFValidator cpfValidator;

    /**
     * Busca todos os clientes cadastrados no sistema.
     * <p>
     * Esta operação recupera todas as entradas de clientes presentes no banco de dados.
     * Se não houver clientes cadastrados, uma exceção será lançada.
     * </p>
     *
     * @return Lista de todos os clientes cadastrados.
     * @throws IllegalArgumentException Se nenhum cliente for encontrado.
     */
    @Operation(summary = "Busca todos os clientes", description = "Retorna uma lista de todos os clientes")
    @GetMapping("/cliente")
    public List<Cliente> buscarClientes() {
        var resultado = clienteRepository.findAll();
        if (resultado.isEmpty()) {
            throw new IllegalArgumentException("Nenhum cliente encontrado!");
        }
        return resultado;
    }

    /**
     * Busca um cliente específico pelo seu identificador (ID).
     * <p>
     * Retorna um cliente correspondente ao ID fornecido, se este existir no banco de dados.
     * Caso contrário, uma exceção será lançada.
     * </p>
     *
     * @param id Identificador único do cliente.
     * @return Cliente correspondente ao ID fornecido.
     * @throws IllegalArgumentException Se o cliente com o ID fornecido não for encontrado.
     */
    @Operation(summary = "Busca um cliente pelo ID", description = "Retorna um cliente específico com base no ID fornecido")
    @GetMapping("/cliente/{id}")
    public Cliente buscarCliente(@PathVariable int id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado!"));
    }

    /**
     * Adiciona um novo cliente ao banco de dados.
     * <p>
     * Antes de salvar os dados, o CPF fornecido pelo cliente será validado utilizando o
     * {@link CPFValidator}. Se o CPF for válido, o cliente será salvo e retornado.
     * Caso contrário, uma exceção será lançada.
     * </p>
     *
     * @param cliente Objeto contendo os dados do cliente a serem salvos.
     * @return O cliente salvo no banco de dados.
     * @throws IllegalArgumentException Se o CPF do cliente for inválido.
     */
    @Operation(summary = "Salva um novo cliente", description = "Adiciona um novo cliente ao banco de dados")
    @PostMapping("/cliente")
    public Cliente salvarCliente(@RequestBody Cliente cliente) {
        if (cpfValidator.isValidCPF(cliente.getcpfCliente())) {
            Cliente cliente_rec = clienteRepository.save(cliente);
            return cliente_rec;
        } else {
            throw new IllegalArgumentException("CPF inválido!");
        }
    }

    /**
     * Atualiza os dados de um cliente existente no banco de dados.
     * <p>
     * Esta operação atualiza os dados de um cliente já existente, verificando se o cliente
     * com o ID especificado existe no banco de dados. Se o cliente existir, ele será atualizado
     * e os novos dados serão persistidos.
     * </p>
     *
     * @param cliente Objeto contendo os dados atualizados do cliente.
     * @return O cliente atualizado.
     * @throws IllegalArgumentException Se o cliente com o ID fornecido não for encontrado.
     */
    @Operation(summary = "Atualiza um cliente", description = "Atualiza os dados de um cliente existente")
    @PutMapping("/cliente")
    public Cliente atualizarCliente(@RequestBody Cliente cliente) {
        if (clienteRepository.existsById(cliente.getId())) {
            clienteRepository.save(cliente);
            return cliente;
        } else {
            throw new IllegalArgumentException("Cliente não encontrado!");
        }
    }

    /**
     * Remove um cliente do banco de dados com base no seu identificador (ID).
     * <p>
     * Esta operação exclui um cliente com base no ID fornecido. Caso o cliente não seja encontrado,
     * uma exceção será lançada.
     * </p>
     *
     * @param id Identificador único do cliente a ser deletado.
     * @return Mensagem confirmando que o cliente foi deletado com sucesso.
     * @throws IllegalArgumentException Se o cliente com o ID fornecido não for encontrado.
     */
    @Operation(summary = "Deleta um cliente", description = "Remove um cliente do banco de dados com base no ID fornecido")
    @DeleteMapping("/cliente/{id}")
    public String deletarCliente(@PathVariable int id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return "Cliente deletado com sucesso!";
        } else {
            throw new IllegalArgumentException("Cliente não encontrado!");
        }
    }
}
