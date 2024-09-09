package app.tgid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.tgid.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
    Empresa findByCnpj(String cnpj);
    
}
