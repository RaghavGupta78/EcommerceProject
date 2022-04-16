package Raghav.EcommerceProject.entities;

import javax.persistence.*;

@Entity
public class CategoryMetadataField {

    @Id
    private int id;
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
