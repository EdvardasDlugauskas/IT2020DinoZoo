package lt.vu.persistence;

import lt.vu.entities.Food;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class FoodsDao {

    @Inject
    private EntityManager em;

    public List<Food> loadAll() {
        return em.createNamedQuery("Food.findAll", Food.class).getResultList();
    }

    public void persist(Food food) {
        this.em.persist(food);
    }

    public Food findOne(Integer id) {
        return em.find(Food.class, id);
    }
}
