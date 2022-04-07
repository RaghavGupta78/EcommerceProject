package Raghav.EcommerceProject.entities;

import javax.persistence.*;

@Entity
public class Cart {

    @Id
    private int id;
    //foreign value
    private int customer_user_id;
    private int quantity;
    private boolean is_wishlist_item;


    //foreign value
    //private int product_variation_id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_variation_id",referencedColumnName = "id")
    private ProductVariation productVariation;
}
