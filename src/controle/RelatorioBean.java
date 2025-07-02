package controle;

import modelo.Aluno;
import modelo.Curso;
import service.AlunoService;
import service.CursoService;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.inject.Inject;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class RelatorioBean implements Serializable {

    private Long idCursoSelecionado;
    private Integer idadeMin;
    private Integer idadeMax;
    private String cidade;

    private List<Curso> cursos;
    private List<Aluno> alunosFiltrados;

    @Inject
    private CursoService cursoServ;

    @Inject
    private AlunoService alunoServ;

    @PostConstruct
    public void init() {
        cursos = cursoServ.listarTodos();
        alunosFiltrados = new ArrayList<>();
    }

    public void filtrar() {
        alunosFiltrados = alunoServ.filtrarRelatorio(idCursoSelecionado, idadeMin, idadeMax, cidade);
    }

    // Getters e Setters

    public Long getIdCursoSelecionado() { return idCursoSelecionado; }
    public void setIdCursoSelecionado(Long idCursoSelecionado) { this.idCursoSelecionado = idCursoSelecionado; }

    public Integer getIdadeMin() { return idadeMin; }
    public void setIdadeMin(Integer idadeMin) { this.idadeMin = idadeMin; }

    public Integer getIdadeMax() { return idadeMax; }
    public void setIdadeMax(Integer idadeMax) { this.idadeMax = idadeMax; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public List<Curso> getCursos() { return cursos; }

    public List<Aluno> getAlunosFiltrados() { return alunosFiltrados; }
}

