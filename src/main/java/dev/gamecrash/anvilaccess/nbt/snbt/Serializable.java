package dev.gamecrash.anvilaccess.nbt.snbt;

public @interface Serializable {
    Class<? extends Serializer> serializer() default Serializer.class;
}
