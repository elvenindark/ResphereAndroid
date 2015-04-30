package com.resphere.android.util;

/**
 * Define propiedades de un determinado Widget para poder instanciarlo
 */
@java.lang.annotation.Target({java.lang.annotation.ElementType.FIELD})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)

public @interface ItemWidget {
    int identifier(); Class className();
}