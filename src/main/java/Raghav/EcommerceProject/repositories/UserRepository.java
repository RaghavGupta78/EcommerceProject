package Raghav.EcommerceProject.repositories;

import Raghav.EcommerceProject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);
}
