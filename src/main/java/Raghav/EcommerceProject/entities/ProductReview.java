package Raghav.EcommerceProject.entities;

import javax.persistence.*;

@Entity
public class ProductReview {

    @Id
    private int id;
    //foreign value
    //private int customer_user_id;
    private String review;
    private int rating;

    //foreign value
    //private int product_id;
    @ManyToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product product;
}
