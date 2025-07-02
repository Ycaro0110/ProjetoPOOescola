package controle;

import modelo.Aluno;
import modelo.Curso;
import service.AlunoService;
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
public class AlunoBean implements Serializable {

    private Aluno aluno = new Aluno();
    private List<Aluno> alunos;
    private List<Curso> cursos;

    @Inject
    private AlunoService alunoServ;

    @Inject
    private CursoService cursoServ;
    
    private Long idCursoSelecionado;

    @PostConstruct
    public void init() {
        alunos = alunoServ.listarTodos();
        cursos = cursoServ.listarTodos(); // usado no <h:selectOneMenu>
    }

    public void cadastrar() {
        Curso curso = cursoServ.buscarPorId(idCursoSelecionado);
        aluno.setCurso(curso);
        alunoServ.cadastrar(aluno);
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Aluno cadastrado com sucesso!", null));
        aluno = new Aluno();
        idCursoSelecionado = null;
        init();
    }

    public void editar(Aluno alunoSelecionado) {
        this.aluno = alunoSelecionado;
        if (aluno.getCurso() != null) {
            this.idCursoSelecionado = aluno.getCurso().getId();
        }
    }

    public void atualizar() {
        Curso curso = cursoServ.buscarPorId(idCursoSelecionado);
        aluno.setCurso(curso);
        alunoServ.atualizar(aluno);
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Aluno atualizado com sucesso!", null));
        aluno = new Aluno();
        idCursoSelecionado = null;
        init();
    }

    public void excluir(Aluno aluno) {
        alunoServ.remover(aluno);
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Aluno excluído com sucesso!", null));
        init();
    }

    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }

    public List<Aluno> getAlunos() { return alunos; }
    public List<Curso> getCursos() { return cursos; }
    
    public Long getIdCursoSelecionado() {
        return idCursoSelecionado;
    }

    public void setIdCursoSelecionado(Long idCursoSelecionado) {
        this.idCursoSelecionado = idCursoSelecionado;
    }
}
