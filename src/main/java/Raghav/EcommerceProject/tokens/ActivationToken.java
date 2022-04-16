package Raghav.EcommerceProject.tokens;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ActivationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String activationTokenName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivationTokenName() {
        return activationTokenName;
    }

    public void setActivationTokenName(String activationTokenName) {
        this.activationTokenName = activationTokenName;
    }
}
