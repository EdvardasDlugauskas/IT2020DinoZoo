package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mybatis.dao.FoodMapper;
import lt.vu.mybatis.model.FoodMyBatis;


import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class FoodsMyBatis {

    @Inject
    private FoodMapper foodMapper;

    @Getter
    @Setter
    private FoodMyBatis foodToCreate = new FoodMyBatis();

    @Getter
    private List<FoodMyBatis> allFoods;

    @PostConstruct
    public void init(){
        loadAllFoods();
    }

    @Transactional
    public String createFood() {
        this.foodMapper.insert(foodToCreate);
        return "index?faces-redirect=true";
    }

    private void loadAllFoods(){
        this.allFoods = foodMapper.selectAll();
    }
}
