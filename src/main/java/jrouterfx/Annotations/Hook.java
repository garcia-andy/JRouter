package jrouterfx.Annotations;

import java.lang.annotation.Target;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
/**
 * Mark a method as Hook
*/
public @interface Hook {
}
