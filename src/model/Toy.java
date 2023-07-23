package model;

public class Toy {

    private final Integer id;
    private final String toyName;
    private Integer weight;

    public Toy(Integer id, String toyName, Integer weight) {
        this.id = id;
        this.toyName = toyName;
        this.weight = weight;
    }

    public Integer getId() {
        return id;
    }

    public String getToyName() {
        return toyName;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
