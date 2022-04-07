package Raghav.EcommerceProject.api;

import Raghav.EcommerceProject.entities.Customer;
import Raghav.EcommerceProject.entities.Seller;
import Raghav.EcommerceProject.entities.User;
import Raghav.EcommerceProject.exceptions.AlreadyActiveException;
import Raghav.EcommerceProject.exceptions.AlreadyDeactivatedException;
import Raghav.EcommerceProject.repositories.CustomerRepository;
import Raghav.EcommerceProject.repositories.SellerRepository;
import Raghav.EcommerceProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/welcome")
    public String message(){
        return "hello this is raghav";
    }

    //list all customers
    @GetMapping("/customers")
    public List<Customer> printAll(){
       List<Customer> c = customerRepository.findAllCustomers();
       for(int i=0; i< c.size();i++){
           System.out.println(c.get(i));
       }
        System.out.println(customerRepository.findAllCustomers());
       return c;
    }

    //list all seller
    @GetMapping("/seller")
    public List<Seller> printAllSeller(){
        List<Seller> s = sellerRepository.findAllSeller();
        System.out.println(sellerRepository.findAllSeller());
        return s;
    }

    //Activate a customer
    @PatchMapping("/customer-activate/{id}")
    public ResponseEntity<Customer> activateCustomer(@PathVariable("id") Integer id,@RequestBody Customer customer) throws AlreadyActiveException {

        Customer c1 = customerRepository.findById(id).get();
        c1.setId(customer.getId());
        c1.setContact(customer.getContact());
        c1.setEmail(customer.getEmail());
        c1.setPhoneNumber(customer.getPhoneNumber());
        c1.setPassword(customer.getPassword());
        c1.setConfirmPassword(customer.getConfirmPassword());
        c1.setFirstName(customer.getFirstName());
        c1.setLastName(customer.getLastName());
        User u1 = c1.getUser();
        if(u1.isActive()==true){
            throw new AlreadyActiveException("THE USER IS ALREADY ACTIVATED");
        }
        u1.setActive(true);
        c1.setUser(u1);
        userRepository.save(u1);
        Customer updatedCustomer = customerRepository.save(c1);
        return ResponseEntity.ok(updatedCustomer);

    }

    //De-activate a customer
    @PatchMapping("/customer-deactivate/{id}")
    public ResponseEntity<Customer> deactivateCustomer(@PathVariable("id") Integer id,@RequestBody Customer customer) throws Exception {

        Customer c1 = customerRepository.findById(id).get();
        c1.setId(customer.getId());
        c1.setContact(customer.getContact());
        c1.setEmail(customer.getEmail());
        c1.setPhoneNumber(customer.getPhoneNumber());
        c1.setPassword(customer.getPassword());
        c1.setConfirmPassword(customer.getConfirmPassword());
        c1.setFirstName(customer.getFirstName());
        c1.setLastName(customer.getLastName());
        User u1 = c1.getUser();
        if(u1.isActive()==false){
            throw new AlreadyDeactivatedException("THE USER IS ALREADY DEACTIVATED");
        }
        u1.setActive(false);
        c1.setUser(u1);
        userRepository.save(u1);
        Customer updatedCustomer = customerRepository.save(c1);
        return ResponseEntity.ok(updatedCustomer);

    }

    //Activate a seller
    @PatchMapping("/seller-activate/{id}")
    public ResponseEntity<Seller> activateSeller(@PathVariable("id") Integer id,@RequestBody Seller seller) throws AlreadyActiveException {
        Seller s1 = sellerRepository.findById(id).get();

        s1.setId(seller.getId());
        s1.setGst(seller.getGst());
        s1.setContact_number(seller.getContact_number());
        s1.setCompany_name(seller.getCompany_name());
        s1.setEmail(seller.getEmail());
        s1.setPassword(seller.getPassword());
        s1.setConfirmPassword(seller.getConfirmPassword());
        s1.setCompanyAddress(seller.getCompanyAddress());
        s1.setFirstName(seller.getFirstName());
        s1.setLastName(seller.getLastName());
        User u1 = s1.getUser();
        if(u1.isActive()==true){
            throw new AlreadyActiveException("THE USER IS ALREADY ACTIVATED");
        }
        u1.setActive(true);
        s1.setUser(u1);
        userRepository.save(u1);
        Seller updatedSeller = sellerRepository.save(s1);
        return ResponseEntity.ok(updatedSeller);

    }

    //De-activate a seller
    @PatchMapping("/seller-deactivate/{id}")
    public ResponseEntity<Seller> deactivateSeller(@PathVariable("id") Integer id,@RequestBody Seller seller) throws AlreadyDeactivatedException {
        Seller s1 = sellerRepository.findById(id).get();

        s1.setId(seller.getId());
        s1.setGst(seller.getGst());
        s1.setContact_number(seller.getContact_number());
        s1.setCompany_name(seller.getCompany_name());
        s1.setEmail(seller.getEmail());
        s1.setPassword(seller.getPassword());
        s1.setConfirmPassword(seller.getConfirmPassword());
        s1.setCompanyAddress(seller.getCompanyAddress());
        s1.setFirstName(seller.getFirstName());
        s1.setLastName(seller.getLastName());
        User u1 = s1.getUser();
        if(u1.isActive()==false){
            throw new AlreadyDeactivatedException("THE USER IS ALREADY DEACTIVATED");
        }
        u1.setActive(false);
        s1.setUser(u1);
        userRepository.save(u1);
        Seller updatedSeller = sellerRepository.save(s1);
        return ResponseEntity.ok(updatedSeller);

    }




}
