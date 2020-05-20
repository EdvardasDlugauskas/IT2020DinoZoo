package lt.vu.decorators;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

@Decorator
public abstract class DecoratedClassDecorator implements DecoratedClass {

    @Inject @Delegate @Any
    DecoratedClass decoratedClass;

    @Override
    public String getName() {
        return "FANCY-" + decoratedClass.getName() + "-FANCY";
    }
}
