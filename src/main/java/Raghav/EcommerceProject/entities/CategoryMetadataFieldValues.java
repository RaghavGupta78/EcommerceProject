package Raghav.EcommerceProject.entities;

import javax.persistence.*;

@Entity
public class CategoryMetadataFieldValues {

    @Id
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_metadata_field_id",referencedColumnName = "id")
    private CategoryMetadataField categoryMetadataField;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    private Category category;

}
