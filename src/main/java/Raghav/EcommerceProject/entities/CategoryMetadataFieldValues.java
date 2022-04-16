package Raghav.EcommerceProject.entities;

import Raghav.EcommerceProject.converter.StringListConverter;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
public class CategoryMetadataFieldValues {

    @Id
    private int id;

    @Convert(converter = StringListConverter.class)
    private List<String> metadata;


    public List<String> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<String> metadata) {
        this.metadata = metadata;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_metadata_field_id",referencedColumnName = "id")
    private CategoryMetadataField categoryMetadataField;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    private Category category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public CategoryMetadataField getCategoryMetadataField() {
        return categoryMetadataField;
    }

    public void setCategoryMetadataField(CategoryMetadataField categoryMetadataField) {
        this.categoryMetadataField = categoryMetadataField;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
