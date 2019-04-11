package fhict.eventsurf.eventsurf;

/**
 * Created by emese on 23/03/2018.
 */

public class Festival {

    private String id;
    private String name;
    private String place;
    private double price;

    public Festival(){
        super();
    }

    public Festival(String name){
        this.name = name;
    }

    public Festival(String id, String name, String place) {
        super();
        this.id = id;
        this.name = name;
        this.place = place;
        this.price = 100;
    }

    @Override
    public String toString() {
        return this.id + ". " + this.name + " will take place in " + this.place + " [$" + this.price + "]";
    }
}
