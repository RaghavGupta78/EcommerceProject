package Raghav.EcommerceProject.repositories;

import Raghav.EcommerceProject.tokens.AccessToken;
import Raghav.EcommerceProject.tokens.ActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivationTokenRepository extends JpaRepository<ActivationToken,Integer> {
    ActivationToken findByActivationTokenName(String jwtToken);
}
