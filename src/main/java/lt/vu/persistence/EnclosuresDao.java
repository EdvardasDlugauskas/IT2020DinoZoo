package lt.vu.persistence;

import lt.vu.entities.Enclosure;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import java.util.List;

@ApplicationScoped
public class EnclosuresDao {

    @Inject
    private EntityManager em;

    public List<Enclosure> loadAll() {
        return em.createNamedQuery("Enclosure.findAll", Enclosure.class).getResultList();
    }

    public void persist(Enclosure enclosure){
        this.em.persist(enclosure);
    }

    public void persistAndFlush(Enclosure enclosure){
        this.em.persist(enclosure);
        this.em.flush();
    }

    public void flush() {
        this.em.setFlushMode(FlushModeType.COMMIT);
        this.em.flush();
    }

    public Enclosure findOne(Integer id) {
        return em.find(Enclosure.class, id);
    }

    public void remove(Enclosure e) {
        em.remove(e);
    }
}