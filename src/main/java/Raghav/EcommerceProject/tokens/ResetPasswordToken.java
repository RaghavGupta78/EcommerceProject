package Raghav.EcommerceProject.tokens;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ResetPasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String resetPasswordTokenName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResetPasswordTokenName() {
        return resetPasswordTokenName;
    }

    public void setResetPasswordTokenName(String resetPasswordTokenName) {
        this.resetPasswordTokenName = resetPasswordTokenName;
    }
}
