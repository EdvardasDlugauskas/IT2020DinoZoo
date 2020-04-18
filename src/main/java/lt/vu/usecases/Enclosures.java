package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Enclosure;
import lt.vu.persistence.EnclosuresDao;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class Enclosures {

    @Inject
    private EnclosuresDao enclosuresDao;

    @Getter
    @Setter
    private Enclosure enclosureToCreate = new Enclosure();

    @Getter
    private List<Enclosure> allEnclosures;

    @PostConstruct
    public void init(){
        loadAllEnclosures();
    }

    @Transactional
    public String createEnclosure(){
        this.enclosuresDao.persist(enclosureToCreate);
        return "index?faces-redirect=true";
    }

    private void loadAllEnclosures(){
        this.allEnclosures = enclosuresDao.loadAll();
    }
}
