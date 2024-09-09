package app.tgid.model;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;



@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(unique = true, nullable = false, length = 11)
    private String cpfCliente;

    @Column(name = "e-mail")
    private String email;
    

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getcpfCliente() {
        return cpfCliente;
    }

    public void setcpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "Cliente [id=" + id + ", cpfCliente=" + cpfCliente + "]";
    }
  
    
}
