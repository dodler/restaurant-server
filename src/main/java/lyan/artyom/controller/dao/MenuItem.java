package lyan.artyom.controller.dao;

import com.mongodb.BasicDBObject;

public class MenuItem {
    private String name;
    private Double price;
    private Integer quantity;

    public static final MenuItem from(String name, Double price, Integer quantity){

        MenuItem menuItem = new MenuItem();
        menuItem.name = name;
        menuItem.price = price;
        menuItem.quantity = quantity;
        return menuItem;
    }

    public final BasicDBObject toBasicDbObject(){
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("name", name);
        basicDBObject.append("price", price);
        basicDBObject.append("quantity", quantity);
        return basicDBObject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
