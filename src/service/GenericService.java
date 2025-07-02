package service;

import java.util.List;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

@TransactionManagement(TransactionManagementType.CONTAINER)
public abstract class GenericService<T> {

    @PersistenceContext(unitName="punit")
    private EntityManager entityManager;

    private final Class<T> classe;

    public GenericService(Class<T> classe) {
        this.classe = classe;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void cadastrar(T entity) {
        getEntityManager().persist(entity);
    }

    @Transactional
    public T atualizar(T entity) {
        return getEntityManager().merge(entity);
    }

    @Transactional
    public void remover(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    @Transactional
    public T buscarPorId(Long id) {
        return getEntityManager().find(classe, id);
    }

    @Transactional
    public List<T> listarTodos() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(classe);
        Root<T> root = cq.from(classe);
        cq.select(root);
        return getEntityManager().createQuery(cq).getResultList();
    }
}
