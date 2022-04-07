package Raghav.EcommerceProject.entities;

import Raghav.EcommerceProject.enums.FromStatus;
import Raghav.EcommerceProject.enums.ToStatus;

import javax.persistence.*;


@Entity
public class OrderStatus {

    @Id
    private int id;

    //foreign value
   // private int order_product_id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_product_id",referencedColumnName = "id")
    private OrderProduct orderProduct;


    private FromStatus fromStatus;
    private ToStatus toStatus;
    private String transition_notes_comments;


}
