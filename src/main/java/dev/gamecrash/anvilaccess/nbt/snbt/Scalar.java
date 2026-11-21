package dev.gamecrash.anvilaccess.nbt.snbt;

import dev.gamecrash.anvilaccess.nbt.tag.*;

public class Scalar {
    private Scalar() {}

    public static Tag<?> parse(String token) {
        if (token.equals("true")) return new ByteTag((byte) 1);
        if (token.equals("false")) return new ByteTag((byte) 0);

        try {
            char last = Character.toLowerCase(token.charAt(token.length() - 1));
            boolean hasSuffix = last == 'b' || last == 's' || last == 'l'
                || last == 'f' || last == 'd';
            String number = hasSuffix ? token.substring(0, token.length() - 1) : token;

            return switch (last) {
                case 'b' -> new ByteTag(Byte.parseByte(number));
                case 's' -> new ShortTag(Short.parseShort(number));
                case 'l' -> new LongTag(Long.parseLong(number));
                case 'f' -> new FloatTag(Float.parseFloat(number));
                case 'd' -> new DoubleTag(Double.parseDouble(number));
                default -> {
                    if (token.indexOf('.') >= 0 || token.indexOf('E') >= 0 || token.indexOf('e') >= 0) {
                        yield new DoubleTag(Double.parseDouble(token));
                    }
                    yield new IntTag(Integer.parseInt(token));
                }
            };
        } catch (NumberFormatException e) {
            return new StringTag(token);
        }
    }
}
