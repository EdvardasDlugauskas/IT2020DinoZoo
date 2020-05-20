package lt.vu.decorators;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;

@RequestScoped
@Default
public class DecoratedClassImpl implements DecoratedClass{

    public String getName () {
        return "SimpleClass";
    }
}
