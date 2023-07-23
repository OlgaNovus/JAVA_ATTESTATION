package model;

public class ToyWithCount {

    private final Toy toy;
    private Integer count;

    public ToyWithCount(Toy toy) {
        this.toy = toy;
    }

    public Toy getToy() {
        return toy;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
