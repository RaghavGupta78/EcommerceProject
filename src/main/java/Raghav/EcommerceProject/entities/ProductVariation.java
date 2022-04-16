package Raghav.EcommerceProject.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
public class ProductVariation {

    @Id
    private int id;


    //foreign value from product table
    //private int product_id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product product;

    private int quantity_available;
    private double price;


    @Type(type = "org.hibernate.type.TextType")
    private String metadata;

    private boolean is_active;


    @OneToOne(mappedBy = "productVariation")
    private Cart cart;

    @OneToOne(mappedBy = "productVariation")
    private OrderProduct orderProduct;




    public String setMetadata(Category c) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(c);
        return s;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getQuantity_available() {
        return quantity_available;
    }

    public void setQuantity_available(int quantity_available) {
        this.quantity_available = quantity_available;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
