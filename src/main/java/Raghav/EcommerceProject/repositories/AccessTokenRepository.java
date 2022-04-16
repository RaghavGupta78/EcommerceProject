package Raghav.EcommerceProject.repositories;

import Raghav.EcommerceProject.tokens.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessTokenRepository extends JpaRepository<AccessToken,Integer> {
    AccessToken findByAccessTokenName(String jwtToken);

}
