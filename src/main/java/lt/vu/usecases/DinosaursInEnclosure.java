package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Dinosaur;
import lt.vu.entities.Enclosure;
import lt.vu.persistence.DinosaursDao;
import lt.vu.persistence.EnclosuresDao;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Map;

@Model
public class DinosaursInEnclosure implements Serializable {

    @Inject
    private EnclosuresDao enclosuresDao;

    @Inject
    private DinosaursDao dinosaursDao;

    @Getter @Setter
    private Enclosure enclosure;

    @Getter @Setter
    private Dinosaur dinosaurToCreate = new Dinosaur();

    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer enclosureId = Integer.parseInt(requestParameters.get("enclosureId"));
        this.enclosure = enclosuresDao.findOne(enclosureId);
    }

    @Transactional
    public String createDinosaur() {
        dinosaurToCreate.setEnclosure(this.enclosure);
        dinosaursDao.persist(dinosaurToCreate);
        return "dinosaurs?faces-redirect=true&enclosureId=" + this.enclosure.getId();
    }
}
