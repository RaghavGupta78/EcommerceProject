package Raghav.EcommerceProject.repositories;

import Raghav.EcommerceProject.entities.CategoryMetadataField;
import Raghav.EcommerceProject.entities.CategoryMetadataFieldValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryMetadataFieldValuesRepository extends JpaRepository<CategoryMetadataFieldValues,Integer> {
    @Query(value = "select * from category_metadata_field_values",nativeQuery = true)
    List<CategoryMetadataFieldValues> findAllCategoryMetaDataFieldValues();
}
