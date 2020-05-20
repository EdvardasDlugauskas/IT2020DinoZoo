package lt.vu.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Dinosaur.findAll", query = "SELECT d from Dinosaur as d")
})
@Table(name = "DINOSAUR")
@Getter @Setter
public class Dinosaur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 100, min = 1)
    private String name;

    @ManyToOne
    private Enclosure enclosure;

    @ManyToMany
    private List<Food> foods = new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Dinosaur dinosaur = (Dinosaur) obj;
        return Objects.equals(name, dinosaur.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
