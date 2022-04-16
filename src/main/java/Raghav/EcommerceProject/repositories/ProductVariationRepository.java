package Raghav.EcommerceProject.repositories;

import Raghav.EcommerceProject.entities.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductVariationRepository extends JpaRepository<ProductVariation,Integer> {
    List<ProductVariation> findAllByProductId(Integer id);
}
