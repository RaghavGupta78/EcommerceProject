package Raghav.EcommerceProject.entities;

import javax.persistence.*;

@Entity
public class Category {

    @Id
    private int id;
    private String name;

    //foreign ??????
    //private int parent_category_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_category_id",referencedColumnName = "id")
    private ParentCategory parentCategory;


    public ParentCategory getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(ParentCategory parentCategory) {
        this.parentCategory = parentCategory;
    }

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
