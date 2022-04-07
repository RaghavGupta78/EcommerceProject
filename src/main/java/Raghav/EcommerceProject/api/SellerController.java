package Raghav.EcommerceProject.api;

import Raghav.EcommerceProject.ModelRequest.RequestPassword;
import Raghav.EcommerceProject.entities.Address;
import Raghav.EcommerceProject.entities.Seller;
import Raghav.EcommerceProject.entities.User;
import Raghav.EcommerceProject.repositories.AddressRepository;
import Raghav.EcommerceProject.repositories.SellerRepository;
import Raghav.EcommerceProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller")
public class SellerController {


    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;


    //view profile
    @GetMapping("/view-profile/{id}")
    public Seller viewProfile(@PathVariable("id") Integer id){

        Seller s1 = sellerRepository.findById(id).get();
        Seller s2 = new Seller();
        s2.setId(s1.getId());
        s2.setFirstName(s1.getFirstName());
        s2.setLastName(s1.getLastName());

        User uTemp = s1.getUser();
        User u2 = new User();
        u2.setActive(uTemp.isActive());

        Address a1 = uTemp.getAddress();
        Address a2 = new Address();
        a2.setZip_code(a1.getZip_code());
        a2.setState(a1.getState());
        a2.setCity(a1.getCity());
        a2.setCountry(a1.getCountry());
        a2.setAddress_line(a1.getAddress_line());
        a2.setLabel(a1.getLabel());

        u2.setAddress(a2);


        s2.setUser(u2);


        s2.setCompany_name(s1.getCompany_name());
        s2.setGst(s1.getGst());
        s2.setCompanyAddress(s1.getCompanyAddress());

        return s2;

    }


    //update-profile
    @PutMapping("/update-profile/{id}")
    public Seller updateProfile(@PathVariable("id") Integer id,@RequestBody Seller seller){


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
    public Seller updatePassword(@PathVariable("id") Integer id, @RequestBody RequestPassword requestPassword){

        Seller s1 = sellerRepository.findById(id).get();
        s1.setPassword(requestPassword.getPassword());
        s1.setConfirmPassword(requestPassword.getConfirmPassword());
        sellerRepository.save(s1);
        return s1;

    }


    //update address
    @PutMapping("/update-address/{id}")
    private Seller updateAddress(@PathVariable("id") Integer id,@RequestBody Address address){


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
