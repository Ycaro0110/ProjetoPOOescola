package service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import modelo.Aluno;
import modelo.Curso;

@Named
@ApplicationScoped
public class CursoService extends GenericService<Curso> {
    public CursoService() {
        super(Curso.class);
    }
    
    @Transactional
    public boolean possuiAlunosVinculados(Curso curso) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Aluno> root = cq.from(Aluno.class);
        
        cq.select(cb.count(root));
        cq.where(cb.equal(root.get("curso"), curso));

        Long total = getEntityManager().createQuery(cq).getSingleResult();
        return total > 0;
    }
}
