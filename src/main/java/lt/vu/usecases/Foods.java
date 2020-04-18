package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Food;
import lt.vu.persistence.FoodsDao;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class Foods {

    @Inject
    private FoodsDao foodsDao;

    @Getter
    @Setter
    private Food foodToCreate = new Food();

    @Getter
    private List<Food> allFoods;

    @PostConstruct
    public void init(){
        loadAllFoods();
    }

    @Transactional
    public String createFood(){
        this.foodsDao.persist(foodToCreate);
        return "index?faces-redirect=true";
    }

    private void loadAllFoods(){
        this.allFoods = foodsDao.loadAll();
    }
}
