package service;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import modelo.Aluno;
import modelo.Curso;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import modelo.Endereco;

@Named
@ApplicationScoped
public class AlunoService extends GenericService<Aluno> {
    public AlunoService() {
        super(Aluno.class);
    }
    
    @Transactional
    public List<Aluno> filtrarRelatorio(Long idCurso, Integer idadeMin, Integer idadeMax, String cidade) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Aluno> cq = cb.createQuery(Aluno.class);
        Root<Aluno> root = cq.from(Aluno.class);
        Join<Aluno, Curso> cursoJoin = root.join("curso");
        Join<Aluno, Endereco> enderecoJoin = root.join("endereco");

        List<Predicate> predicates = new ArrayList<>();

        if (idCurso != null && idCurso != 0) {
            predicates.add(cb.equal(cursoJoin.get("id"), idCurso));
        }

        if (idadeMin != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("idade"), idadeMin));
        }

        if (idadeMax != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("idade"), idadeMax));
        }

        if (cidade != null && !cidade.trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(enderecoJoin.get("cidade")), "%" + cidade.toLowerCase() + "%"));
        }

        cq.select(root).where(predicates.toArray(new Predicate[0])).orderBy(cb.asc(root.get("nome")));

        return getEntityManager().createQuery(cq).getResultList();
    }
}
