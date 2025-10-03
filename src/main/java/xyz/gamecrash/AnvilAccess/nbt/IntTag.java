package xyz.gamecrash.AnvilAccess.nbt;

public class IntTag extends Tag {
    private final int value;

    public IntTag(int value) {
        super(TagType.INT);
        this.value = value;
    }

    @Override
    public Integer getValue() { return value; }

    @Override
    public IntTag copy() { return new IntTag(value); }

    @Override
    public String toString() { return String.valueOf(value); }
}
