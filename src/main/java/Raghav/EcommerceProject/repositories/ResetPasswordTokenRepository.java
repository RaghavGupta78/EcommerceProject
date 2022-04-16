package Raghav.EcommerceProject.repositories;

import Raghav.EcommerceProject.tokens.ActivationToken;
import Raghav.EcommerceProject.tokens.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken,Integer> {

    ResetPasswordToken findByResetPasswordTokenName(String jwtToken);


}
