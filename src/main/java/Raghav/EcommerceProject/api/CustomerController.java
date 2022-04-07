package Raghav.EcommerceProject.api;

import Raghav.EcommerceProject.ModelRequest.RequestPassword;
import Raghav.EcommerceProject.ModelResponse.CustomerResponse;
import Raghav.EcommerceProject.entities.Address;
import Raghav.EcommerceProject.entities.Customer;
import Raghav.EcommerceProject.entities.Seller;
import Raghav.EcommerceProject.entities.User;
import Raghav.EcommerceProject.repositories.AddressRepository;
import Raghav.EcommerceProject.repositories.CustomerRepository;
import Raghav.EcommerceProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    //view customer profile
    @GetMapping("/view-profile/{id}")
    public CustomerResponse viewProfile(@PathVariable ("id") Integer id){
        Customer c1 = customerRepository.findById(id).get();
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(c1.getId());
        customerResponse.setFirstName(c1.getFirstName());
        customerResponse.setLastName(c1.getLastName());
        customerResponse.setContact(c1.getContact());

        int userId = c1.getUser().getId();
        User u = userRepository.findById(userId).get();
        boolean status = u.isActive();

        customerResponse.setActive(status);

        return customerResponse;
    }

    //view customer address
    @GetMapping("/view-address/{id}")
    public Address viewAddress(@PathVariable ("id") Integer id){
        Customer c1 = customerRepository.findById(id).get();
        int UserId = c1.getUser().getId();
        User u1 = userRepository.findById(UserId).get();
        int addressId = u1.getAddress().getId();
        Address a1 = addressRepository.findById(addressId).get();
        return a1;
    }

    //update profile
    @PutMapping("/update-profile/{id}")
    public Customer updateCustomer(@PathVariable("id") Integer id,@RequestBody Customer customer){

        Customer c1 = customerRepository.findById(id).get();
        c1.setId(customer.getId());
        c1.setContact(customer.getContact());
        c1.setEmail(customer.getEmail());
        c1.setPhoneNumber(customer.getPhoneNumber());
        c1.setPassword(customer.getPassword());
        c1.setConfirmPassword(customer.getConfirmPassword());
        c1.setFirstName(customer.getFirstName());
        c1.setLastName(customer.getLastName());

        int userId = c1.getUser().getId();
        User u1 = userRepository.findById(userId).get();
        c1.setUser(u1);

        return c1;

    }

    //update password
    @PutMapping("/update-password/{id}")
    public Customer updatePassword(@PathVariable("id") Integer id, @RequestBody RequestPassword requestPassword){

        Customer s1 = customerRepository.findById(id).get();
        s1.setPassword(requestPassword.getPassword());
        s1.setConfirmPassword(requestPassword.getConfirmPassword());
        customerRepository.save(s1);
        return s1;

    }


    //add customer address
    @PostMapping("/add-address/{id}")
    public Address addUserAddress(@PathVariable("id") Integer id,@RequestBody Address address){
        int idUser = address.getUser().getId();
        address.setUser(userRepository.findById(idUser).get());
        addressRepository.save(address);
        return address;
    }

    //Update customer address
    @PutMapping("/update-address/{id}")
    public Customer addAddress(@PathVariable("id") Integer id,@RequestBody Address address){
        Customer c1 = customerRepository.findById(id).get();
        int userId = c1.getUser().getId();
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

        return c1;
    }

    //delete customer address
    @DeleteMapping("/delete-address/{id}")
    public String deleteAddress(@PathVariable("id") Integer id){

        Customer c1 = customerRepository.findById(id).get();
        int userId = c1.getUser().getId();
        User u1 = userRepository.findById(userId).get();
        int addressId = u1.getAddress().getId();
        addressRepository.deleteById(addressId);

        return "CUSTOMER ADDRESS IS DELETED";



    }



}
