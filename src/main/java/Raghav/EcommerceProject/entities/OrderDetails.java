package Raghav.EcommerceProject.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
public class OrderDetails {

    @Id
    private int id;

    private int customer_user_id;
    private double amount_paid;
    private Date date_created;
    private String payment_method;

    //foreign
    private String customer_address_city;
    private String customer_address_state;
    private String customer_address_country;
    private String customer_address_address_line;
    private String customer_address_zip_code;
    private String customer_address_label;


    @OneToOne(mappedBy = "orderDetails")
    private OrderProduct orderProduct;

}
