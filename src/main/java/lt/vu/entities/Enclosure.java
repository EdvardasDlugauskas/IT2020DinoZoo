package lt.vu.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Enclosure.findAll", query = "SELECT e from Enclosure as e")
})
@Table(name="ENCLOSURE")
@Getter
@Setter
@NoArgsConstructor
public class Enclosure implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "enclosure")
    private List<Dinosaur> dinosaurs = new ArrayList<>();

    public Enclosure(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Enclosure enclosure = (Enclosure) obj;
        return Objects.equals(name, enclosure.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Version
    @Column(name = "OPT_LOCK_VERSION")
    private Integer version;
}
