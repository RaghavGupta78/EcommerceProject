package Raghav.EcommerceProject.entities;

import javax.persistence.*;


@Entity
public class OrderProduct {

    @Id
    private int id;

    //foreign value from table order
    //private int order_id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_details_id",referencedColumnName = "id")
    private OrderDetails orderDetails;

    private int quantity;
    private double price;

    //foreign value from product variation
    //private int product_variation_id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_variation_id",referencedColumnName = "id")
    private ProductVariation productVariation;

    @OneToOne(mappedBy = "orderProduct")
    private OrderStatus orderStatus;

}
