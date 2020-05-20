package lt.vu.rest.testComponents;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;

@Alternative
@RequestScoped
public class ComponentFromAlternativeA extends ComponentFromAlternatives {

}
