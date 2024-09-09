package app.tgid.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import app.tgid.model.Transacao;
import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {
    List<Transacao> findByClienteCpfCliente(String cpf);
    List<Transacao> findByEmpresaCnpj(String cnpj);
    
}
