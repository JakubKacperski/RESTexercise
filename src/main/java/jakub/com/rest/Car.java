package jakub.com.rest;

/**
 * Created by Praca on 2016-11-30.
 */
public class Car {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWeigth() {
        return weigth;
    }

    public void setWeigth(int weigth) {
        this.weigth = weigth;
    }

    private String name;
    private int length;
    private int weigth;

}
