package io.github.andy030124.hooks;

import java.lang.annotation.Target;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface JRouter {
}
