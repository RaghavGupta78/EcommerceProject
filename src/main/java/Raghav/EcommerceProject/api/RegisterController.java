package Raghav.EcommerceProject.api;

import Raghav.EcommerceProject.entities.*;
import Raghav.EcommerceProject.exceptions.AlreadyActiveException;
import Raghav.EcommerceProject.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AddressRepository addressRepository;

    //get a customer
    @GetMapping("/customer/{contact}")
    public void getCustomer(@PathVariable("contact") String contact){
        Customer c = customerRepository.findByContact(contact);
        System.out.println("=======================================================>");
        System.out.println(c);
    }


    //Register a customer
    @PostMapping("/customer")
    public Customer create(@RequestBody Customer customer){


        int id = customer.getUser().getId();

        customer.setUser(userRepository.findById(id).get());
        customerRepository.save(customer);

        System.out.println(customer.getContact());
        return customer;
    }


    //Register a seller
    @PostMapping("/seller")
    public Seller create(@RequestBody Seller seller){

        int id = seller.getUser().getId();

        seller.setUser(userRepository.findById(id).get());
        sellerRepository.save(seller);
        System.out.println(seller.getCompany_name());
        return seller;
    }

    //Register a user
    @PostMapping("/user")
    public User create(@RequestBody User user){

//        Role r1 = user.getRole();
//        roleRepository.save(r1);

        int id = user.getRole().getId();
        user.setRole(roleRepository.findById(id).get());


        userRepository.save(user);

        System.out.println(user.getFirst_name());
        return user;

    }


    //register roles
    @PostMapping("/role")
    public Role create(@RequestBody Role role){

        roleRepository.save(role);
        System.out.println(role.getId());
        System.out.println(role.getAuthority());
        return role;
    }

    //register address
    @PostMapping("/address")
    public Address create(@RequestBody Address address){


        int id = address.getUser().getId();

        address.setUser(userRepository.findById(id).get());

        addressRepository.save(address);
        return address;
    }


    //Activate a customer
    @PatchMapping("/customer-activate/{id}")
    public ResponseEntity<Customer> activateCustomer(@PathVariable("id") Integer id, @RequestBody Customer customer) throws AlreadyActiveException {

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





}
