package Raghav.EcommerceProject.repositories;

import Raghav.EcommerceProject.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Integer> {
}
