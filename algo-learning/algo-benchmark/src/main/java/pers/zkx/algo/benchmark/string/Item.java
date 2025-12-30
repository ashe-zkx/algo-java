package pers.zkx.algo.benchmark.string;

public class Item {
    private final String f1;
    private final Integer f2;
    private String id;

    public Item(String f1, Integer f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

    public String getF1() {
        return f1;
    }

    public Integer getF2() {
        return f2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void buildIdByFormat() {
        this.id = String.format("%s-%d", this.f1, this.f2);
    }

    public void buildIdByPlus() {
        this.id = this.f1 + "-" + this.f2;
    }

    public void buildIdByBuilder() {
        this.id = new StringBuilder(16)
                .append(this.f1)
                .append('-')
                .append(this.f2)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return java.util.Objects.equals(f1, item.f1) &&
               java.util.Objects.equals(f2, item.f2) &&
               java.util.Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(f1, f2, id);
    }

    @Override
    public String toString() {
        return "Item{" +
               "f1='" + f1 + '\'' +
               ", f2=" + f2 +
               ", id='" + id + '\'' +
               '}';
    }
}

