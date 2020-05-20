package lt.vu.rest.testComponents;

import lombok.Getter;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import java.io.Serializable;

@RequestScoped
@Getter
public class ComponentWithSpecializes implements Serializable {
}
