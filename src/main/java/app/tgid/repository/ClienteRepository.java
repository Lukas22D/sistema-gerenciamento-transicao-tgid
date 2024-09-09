package app.tgid.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.tgid.model.Cliente;
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Cliente findByCpfCliente(String cpf);
   
    
}
