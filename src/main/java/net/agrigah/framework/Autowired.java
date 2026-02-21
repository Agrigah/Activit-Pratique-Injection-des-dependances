package net.agrigah.framework;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Autowired {
}