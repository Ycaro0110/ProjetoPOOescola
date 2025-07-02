package service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import modelo.Endereco;

@Named
@ApplicationScoped
public class EnderecoService extends GenericService<Endereco> {
    public EnderecoService() {
        super(Endereco.class);
    }
}
