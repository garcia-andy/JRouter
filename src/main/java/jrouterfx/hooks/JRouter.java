package jrouterfx.hooks;

import java.lang.annotation.Target;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
/**
 * Mark all controllers as JRouters
*/
public @interface JRouter {
    String value() default "";
    String url() default "";
    String view() default "";
}
