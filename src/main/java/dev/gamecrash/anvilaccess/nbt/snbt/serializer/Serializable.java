package dev.gamecrash.anvilaccess.nbt.snbt.serializer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Serializable {
    Class<? extends Serializer> serializer() default PrimitiveSerializer.class;
}
