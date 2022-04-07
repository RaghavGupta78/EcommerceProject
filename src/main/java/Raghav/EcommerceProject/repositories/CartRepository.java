package Raghav.EcommerceProject.repositories;

import Raghav.EcommerceProject.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Integer> {
}
