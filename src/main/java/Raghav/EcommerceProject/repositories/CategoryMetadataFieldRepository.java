package Raghav.EcommerceProject.repositories;

import Raghav.EcommerceProject.entities.CategoryMetadataField;
import Raghav.EcommerceProject.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryMetadataFieldRepository extends JpaRepository<CategoryMetadataField,Integer> {

    @Query(value = "select * from category_metadata_field",nativeQuery = true)
    List<CategoryMetadataField> findAllCategoryMetaDataField();

}
