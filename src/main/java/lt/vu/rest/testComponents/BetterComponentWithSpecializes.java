package lt.vu.rest.testComponents;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Specializes;

@Specializes
@RequestScoped
public class BetterComponentWithSpecializes extends ComponentWithSpecializes {
}
