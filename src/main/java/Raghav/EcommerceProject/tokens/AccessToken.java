package Raghav.EcommerceProject.tokens;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AccessToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String accessTokenName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccessTokenName() {
        return accessTokenName;
    }

    public void setAccessTokenName(String accessToken) {
        this.accessTokenName = accessToken;
    }
}
