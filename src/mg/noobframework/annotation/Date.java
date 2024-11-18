package mg.noobframework.annotation;

public @interface Date {
    String format() default "yyyy-MM-dd";
}
