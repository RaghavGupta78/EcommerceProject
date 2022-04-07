package Raghav.EcommerceProject.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Category {

    @Id
    private int id;
    private String name;

    //foreign ??????
    private int parent_category_id;


    @OneToOne(mappedBy = "category")
    private CategoryMetadataFieldValues categoryMetadataFieldValues;



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

    public int getParent_category_id() {
        return parent_category_id;
    }

    public void setParent_category_id(int parent_category_id) {
        this.parent_category_id = parent_category_id;
    }
}
