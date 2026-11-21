package dev.gamecrash.anvilaccess.nbt.snbt.deserializer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CursorReader {
    private final String input;
    private int pos;

    char peek() {
        skipWhitespace();
        return pos < input.length() ? input.charAt(pos) : '\0';
    }

    boolean accept(char c) {
        if (peek() != c) return false;
        pos++;
        return true;
    }

    void expect(char c) {
        if (!accept(c)) throw error("Expected '" + c + "'");
    }

    void expectEnd() {
        if (peek() != '\0') throw error("Expected end");
    }

    char readArrayPrefix() {
        if (pos + 1 < input.length() && input.charAt(pos + 1) == ';' && "BIL".indexOf(input.charAt(pos)) >= 0) {
            char kind = input.charAt(pos);
            pos += 2;
            return kind;
        }
        return 0;
    }

    String readKey() {
        char c = peek();
        return c == '"' || c == '\'' ? readQuoted() : readUnquoted();
    }

    String readQuoted() {
        char quote = input.charAt(pos++);
        StringBuilder sb = new StringBuilder();

        while (pos < input.length()) {
            char c = input.charAt(pos++);
            if (c == quote) return sb.toString();
            if (c != '\\') {
                sb.append(c);
                continue;
            }

            char escaped = input.charAt(pos++);
            switch (escaped) {
                case 'n' -> sb.append('\n');
                case 't' -> sb.append('\t');
                case 'r' -> sb.append('\r');
                case 'u' -> {
                    sb.append((char) Integer.parseInt(input.substring(pos, pos + 4), 16));
                    pos += 4;
                }
                default -> sb.append(escaped);
            }
        }

        throw error("Unterminated string");
    }

    String readUnquoted() {
        peek();
        int start = pos;
        while (pos < input.length() && isUnquoted(input.charAt(pos))) pos++;
        if (start == pos) throw error("Unexpected character");

        return input.substring(start, pos);
    }

    private void skipWhitespace() {
        while (pos < input.length() && Character.isWhitespace(input.charAt(pos))) pos++;
    }

    private boolean isUnquoted(char c) {
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_' || c == '-' || c == '.' || c == '+';
    }

    private IllegalArgumentException error(String message) {
        return new IllegalArgumentException("Error while parsing SNBT: " + message + " @ " + pos);
    }
}
