package Raghav.EcommerceProject.repositories;

import Raghav.EcommerceProject.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {
}
