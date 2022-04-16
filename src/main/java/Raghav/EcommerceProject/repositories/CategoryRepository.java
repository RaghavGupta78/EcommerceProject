package Raghav.EcommerceProject.repositories;

import Raghav.EcommerceProject.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @Query(value = "select * from category",nativeQuery = true)
    List<Category> findAllCategory();
}
