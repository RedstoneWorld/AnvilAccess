package dev.gamecrash.anvilaccess.util;

import dev.gamecrash.anvilaccess.nbt.tag.*;

public class SnbtScalar {
    private SnbtScalar() {}

    static Tag<?> parse(String token) {
        if (token.equals("true")) return new ByteTag((byte) 1);
        if (token.equals("false")) return new ByteTag((byte) 0);

        try {
            String b = token.substring(0, token.length() - 1);
            switch (Character.toLowerCase(token.charAt(token.length() - 1))) {
                case 'b': return new ByteTag(Byte.parseByte(b));
                case 's': return new ShortTag(Short.parseShort(b));
                case 'l': return new LongTag(Long.parseLong(b));
                case 'f': return new FloatTag(Float.parseFloat(b));
                case 'd': return new DoubleTag(Double.parseDouble(b));
                default: {
                    if (token.indexOf('.') >= 0) return new DoubleTag(Double.parseDouble(b));
                    return new IntTag(Integer.parseInt(token));
                }
            }
        } catch (NumberFormatException e) {
            return new StringTag(token);
        }
    }
}
