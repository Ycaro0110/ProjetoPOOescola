package controle;

import modelo.Curso;
import service.CursoService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class CursoBean implements Serializable {

    private Curso curso = new Curso();
    private List<Curso> cursos;

    @Inject
    private CursoService cursoServ;

    private boolean editando = false;

    @PostConstruct
    public void init() {
        cursos = cursoServ.listarTodos();
    }

    public void cadastrar() {
        cursoServ.cadastrar(curso);
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Curso cadastrado com sucesso!", null));
        curso = new Curso();
        editando = false;
        init();
    }

    public void editar(Curso cursoSelecionado) {
        this.curso = cursoSelecionado;
        this.editando = true;
    }

    public void atualizar() {
        cursoServ.atualizar(curso);
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Curso atualizado com sucesso!", null));
        curso = new Curso();
        editando = false;
        init();
    }

    public void cancelar() {
        curso = new Curso();
        editando = false;
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Edição cancelada.", null));
        init();
    }

    public void excluir(Curso curso) {
        if (cursoServ.possuiAlunosVinculados(curso)) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "O curso possui alunos vinculados. Não é possivel realizar a exclusão",
                    null));
        } else {
            cursoServ.remover(curso);
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Curso excluído com sucesso!", null));
            init();
        }
    }

    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }

    public List<Curso> getCursos() { return cursos; }
    public boolean isEditando() { return editando; }
}
