package dev.gamecrash.anvilaccess.nbt.snbt;

import dev.gamecrash.anvilaccess.nbt.snbt.serializer.Serializable;
import dev.gamecrash.anvilaccess.nbt.snbt.serializer.Serializer;
import dev.gamecrash.anvilaccess.nbt.tag.Tag;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.WeakHashMap;

public class SNBT {
    private final Map<Class<? extends Serializer>, Serializer> knownSerializers = new WeakHashMap<>();

    public String serialize(Tag<?> tag) {
        Serializer s = getSerializer(getSerializerClass(tag));

        return s.serialize(tag, this);
    }

    private Serializer getSerializer(Class<? extends Serializer> clazz) {
        return knownSerializers.computeIfAbsent(clazz, c -> {
            try {
                return c.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Class<? extends Serializer> getSerializerClass(Tag<?> tag) {
        Class<?> clazz = tag.getClass();
        if (!clazz.isAnnotationPresent(Serializable.class)) throw new IllegalArgumentException("Tag missing Serializable annotation, cannot proceed");
        return clazz.getAnnotation(Serializable.class).serializer();
    }
}
