package Raghav.EcommerceProject.repositories;

import Raghav.EcommerceProject.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Query(value = "select * from product",nativeQuery = true)
    List<Product> findAllProduct();

}
