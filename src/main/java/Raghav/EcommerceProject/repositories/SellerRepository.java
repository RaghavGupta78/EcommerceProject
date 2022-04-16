package Raghav.EcommerceProject.repositories;

import Raghav.EcommerceProject.entities.Customer;
import Raghav.EcommerceProject.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller,Integer> {

    @Query(value = "select * from seller",nativeQuery = true)
    List<Seller> findAllSeller();

    Seller findByEmail(String username);
}
