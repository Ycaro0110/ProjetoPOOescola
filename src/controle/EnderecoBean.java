package controle;

import modelo.Endereco;
import service.EnderecoService;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class EnderecoBean implements Serializable {

    private Endereco endereco = new Endereco();
    private List<Endereco> enderecos;

    @Inject
    private EnderecoService enderecoServ;

    @PostConstruct
    public void init() {
        enderecos = enderecoServ.listarTodos();
    }

    public void cadastrar() {
        enderecoServ.cadastrar(endereco);
        endereco = new Endereco();
        init();
    }

    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }

    public List<Endereco> getEnderecos() { return enderecos; }
}