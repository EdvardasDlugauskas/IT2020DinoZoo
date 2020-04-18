package lt.vu.persistence;

import lt.vu.entities.Enclosure;
import lt.vu.entities.Team;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
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

    public Enclosure findOne(Integer id) {
        return em.find(Enclosure.class, id);
    }
}