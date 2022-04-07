package Raghav.EcommerceProject.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class CategoryMetadataField {

    @Id
    private int id;
    private String name;


    @OneToOne(mappedBy = "categoryMetadataField")
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
}
