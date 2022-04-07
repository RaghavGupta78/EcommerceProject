package Raghav.EcommerceProject.repositories;

import Raghav.EcommerceProject.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findByContact(String contact);

    @Query(value = "select * from customer",nativeQuery = true)
    List<Customer> findAllCustomers();

    Customer findByEmail(String email);
}
