package mg.noobframework.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Numerique {
    double min() default Double.MIN_VALUE;

    double max() default Double.MAX_VALUE;
}
