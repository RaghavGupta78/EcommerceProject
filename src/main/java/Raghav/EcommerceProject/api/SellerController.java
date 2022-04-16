package Raghav.EcommerceProject.api;

import Raghav.EcommerceProject.ModelRequest.RequestPassword;
import Raghav.EcommerceProject.entities.Address;
import Raghav.EcommerceProject.entities.Seller;
import Raghav.EcommerceProject.entities.User;
import Raghav.EcommerceProject.exceptions.InvalidTokenException;
import Raghav.EcommerceProject.exceptions.PasswordValidationException;
import Raghav.EcommerceProject.repositories.AccessTokenRepository;
import Raghav.EcommerceProject.repositories.AddressRepository;
import Raghav.EcommerceProject.repositories.SellerRepository;
import Raghav.EcommerceProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/seller")
public class SellerController {


    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    @Value("${file.upload-dir}")
    String FILE_DIRECTORY;



    //view profile
    @GetMapping("/view-profile/{id}")
    public Seller viewProfile(HttpServletRequest request, @PathVariable("id") Integer id) throws IOException, InvalidTokenException {


        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }

        Seller s1 = sellerRepository.findById(id).get();

        return s1;

    }


    //update-profile
    @PutMapping("/update-profile/{id}")
    public Seller updateProfile(HttpServletRequest request,@PathVariable("id") Integer id,@RequestBody Seller seller) throws InvalidTokenException {


        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }



        Seller s1 = sellerRepository.findById(id).get();
        s1.setId(seller.getId());
        s1.setGst(seller.getGst());
        s1.setEmail(seller.getEmail());

        int userId = seller.getUser().getId();
        User u1 = userRepository.findById(userId).get();

        s1.setUser(u1);

        s1.setCompanyAddress(seller.getCompanyAddress());
        s1.setCompany_name(seller.getCompany_name());
        s1.setContact_number(seller.getContact_number());
        s1.setPassword(seller.getPassword());
        s1.setConfirmPassword(seller.getConfirmPassword());
        s1.setFirstName(seller.getFirstName());
        s1.setLastName(seller.getLastName());

        sellerRepository.save(s1);
        return s1;
    }



    //update password
    @PutMapping("/update-password/{id}")
    public Seller updatePassword(HttpServletRequest request,@PathVariable("id") Integer id, @RequestBody RequestPassword requestPassword) throws InvalidTokenException, PasswordValidationException {


        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }
        if(!requestPassword.getPassword().equals(requestPassword.getConfirmPassword())){
            throw new PasswordValidationException("PASSWORD VALIDATION FILLED");
        }

        Seller s1 = sellerRepository.findById(id).get();
        s1.setPassword(requestPassword.getPassword());
        s1.setConfirmPassword(requestPassword.getConfirmPassword());
        sellerRepository.save(s1);
        return s1;

    }


    //update address
    @PutMapping("/update-address/{id}")
    private Seller updateAddress(HttpServletRequest request,@PathVariable("id") Integer id,@RequestBody Address address) throws InvalidTokenException {


        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }


        Seller s1 = sellerRepository.findById(id).get();
        int userId = s1.getUser().getId();
        User u1 = userRepository.findById(userId).get();
        int addressId = u1.getAddress().getId();
        Address a1 = addressRepository.findById(addressId).get();
        a1.setId(address.getId());
        a1.setCity(address.getCity());
        a1.setState(address.getState());
        a1.setCountry(address.getCountry());
        a1.setLabel(address.getLabel());
        a1.setAddress_line(address.getAddress_line());
        a1.setZip_code(address.getZip_code());
        addressRepository.save(a1);

        return s1;


    }

}
