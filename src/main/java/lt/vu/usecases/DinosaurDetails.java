package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Dinosaur;
import lt.vu.entities.Enclosure;
import lt.vu.entities.Food;
import lt.vu.persistence.DinosaursDao;
import lt.vu.persistence.EnclosuresDao;
import lt.vu.persistence.FoodsDao;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Model
public class DinosaurDetails implements Serializable {

    @Inject
    private FoodsDao foodsDao;

    @Inject
    private DinosaursDao dinosaursDao;

    @Getter @Setter
    private Dinosaur dinosaur;

    @Getter @Setter
    private String foodToAddName = "";

    @Getter @Setter
    private List<Food> allFoods;

    @PostConstruct
    public void init() {
        loadAllFoods();
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer dinosaurId = Integer.parseInt(requestParameters.get("dinosaurId"));
        this.dinosaur = dinosaursDao.findOne(dinosaurId);
    }

    private void loadAllFoods() {
        allFoods = foodsDao.loadAll();
    }

    @Transactional
    public String addFoodToDinosaur() {
        final Food foodFromDb = allFoods.stream().filter(x -> x.getName().equals(foodToAddName)).findFirst().orElseThrow();
        dinosaur.getFoods().add(foodFromDb);
        dinosaursDao.persist(dinosaur);
        foodsDao.persist(foodFromDb);
        return "dinosaurDetails?faces-redirect=true&dinosaurId=" + this.dinosaur.getId().toString();
    }
}
