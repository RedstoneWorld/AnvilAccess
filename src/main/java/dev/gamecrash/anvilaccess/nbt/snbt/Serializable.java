package dev.gamecrash.anvilaccess.nbt.snbt;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Serializable {
    Class<? extends Serializer> serializer() default Serializer.class;
}
