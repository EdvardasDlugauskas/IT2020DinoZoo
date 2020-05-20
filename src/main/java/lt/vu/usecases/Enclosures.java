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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

    @Transactional
    public void OptimisticLockTest() throws InterruptedException {
        MakeSureTestEnclosureCreated();
        ExecutorService es = Executors.newFixedThreadPool(2);
            es.execute(() -> {
                try {
                    ChangeTestEnclosure("TestEnclosureUpdate1");
                } catch (Exception e) {
                    System.out.println("-- exception thrown during update 1 --");
                    e.printStackTrace();
                }
            });
            es.execute(() -> {
                try {
                    ChangeTestEnclosure("TestEnclosureUpdate2");
                } catch (Exception e) {
                    System.out.println("-- exception thrown during update 2 --");
                    e.printStackTrace();
                }
            });
            es.shutdown();
            try {
                es.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void MakeSureTestEnclosureCreated() throws InterruptedException {
        final Enclosure testEnclosureFromStorage = this.enclosuresDao.findOne(2);

        if (testEnclosureFromStorage != null) {
            testEnclosureFromStorage.setName("TestEnclosure");
            this.enclosuresDao.persistAndFlush(testEnclosureFromStorage);
        }
        else {
            final Enclosure testEnclosure = new Enclosure("TestEnclosure");
            this.enclosuresDao.persistAndFlush(testEnclosure);
        }
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void ChangeTestEnclosure(String name) {
        try {
            final Enclosure testEnclosure = this.enclosuresDao.findOne(2);
            Thread.sleep(100);
            testEnclosure.setName(name);
            this.enclosuresDao.persistAndFlush(testEnclosure);
        }
        catch (Exception e) {
            System.out.println(e);
        }

    }

    private void loadAllEnclosures(){
        this.allEnclosures = enclosuresDao.loadAll();
    }
}
