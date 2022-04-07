package Raghav.EcommerceProject.entities;

import javax.persistence.*;
import java.util.Set;


@Entity
public class Product {

    @Id
    private int id;
     //foreign value
     //private int seller_user_id;
    private String name;
    private String description;

    //foreign value from table category
   // private int category_id;

    private boolean is_cancellable;
    private boolean is_returnable;
    private String brand;
    private boolean is_active;
    private boolean is_deleted;

   // @ManyToOne
    //@JoinColumn(name = "seller_user_id")
    //private Seller seller_user_id;



    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private Set<ProductVariation> productVariation;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private Set<ProductReview> productReview;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public boolean isIs_cancellable() {
        return is_cancellable;
    }

    public void setIs_cancellable(boolean is_cancellable) {
        this.is_cancellable = is_cancellable;
    }

    public boolean isIs_returnable() {
        return is_returnable;
    }

    public void setIs_returnable(boolean is_returnable) {
        this.is_returnable = is_returnable;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }
}
