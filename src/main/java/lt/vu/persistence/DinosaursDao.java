package lt.vu.persistence;

import lt.vu.entities.Dinosaur;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class DinosaursDao {

    @Inject
    private EntityManager em;

    public List<Dinosaur> loadAll() {
        return em.createNamedQuery("Dinosaur.findAll", Dinosaur.class).getResultList();
    }

    public void persist(Dinosaur dinosaur){
        this.em.persist(dinosaur);
    }

    public Dinosaur findOne(Integer id) {
        return em.find(Dinosaur.class, id);
    }
}