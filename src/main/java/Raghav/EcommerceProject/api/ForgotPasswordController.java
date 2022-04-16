package Raghav.EcommerceProject.api;

import Raghav.EcommerceProject.ModelRequest.RequestPassword;
import Raghav.EcommerceProject.entities.*;
import Raghav.EcommerceProject.exceptions.InvalidTokenException;
import Raghav.EcommerceProject.repositories.*;
import Raghav.EcommerceProject.security.config.JwtAuthenticationFilter;
import Raghav.EcommerceProject.security.helper.JwtUtil;
import Raghav.EcommerceProject.security.model.JwtRequest;
import Raghav.EcommerceProject.security.model.JwtResponse;
import Raghav.EcommerceProject.security.service.CustomUserDetailsService;
import Raghav.EcommerceProject.service.EmailSenderService;
import Raghav.EcommerceProject.tokens.AccessToken;
import Raghav.EcommerceProject.tokens.ResetPasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/forgot")
public class ForgotPasswordController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ResetPasswordTokenRepository resetPasswordTokenRepository;





    @Autowired
    private EmailSenderService emailSenderService;

    //Send a  token to your emailId
    @PostMapping("/user/{email}")
    public String receiveToken(@PathVariable("email") String email) throws Exception {

        User u1 = userRepository.findByEmail(email);


        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("raghav","raghav123"));
        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername("raghav");
        String token = this.jwtUtil.generateToken(userDetails);
        System.out.println("JWT token is  "+ token);



        ResetPasswordToken rt = new ResetPasswordToken();
        rt.setResetPasswordTokenName(token);
        resetPasswordTokenRepository.save(rt);


        emailSenderService.sendEmail(email,"subject","HELLO, A NEW TOKEN HAS BEEN GENERATED FOR YOU WHICH CAN BE USED" +
                "TO RESET THE PASSWORD THE TOKEN IS "+token+" THIS TOKEN IS VALID FOR ONLY 2 MINUTES");

      return "A NEW TOKEN HAS BEEN SENT TO YOUR EMAIL ID "+email;
    }


    @PutMapping("/user/reset/{email}")
    public String resetPassword(HttpServletRequest request,@PathVariable("email") String email, @RequestBody RequestPassword requestPassword) throws InvalidTokenException {



        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!resetPasswordTokenRepository.findByResetPasswordTokenName(jwtToken).getResetPasswordTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }

        User u1 = userRepository.findByEmail(email);
        User u2 = new User();
        u2.setId(u1.getId());
        u2.setEmail(u1.getEmail());
        u2.setFirst_name(u1.getFirst_name());
        u2.setMiddle_name(u1.getMiddle_name());
        u2.setLast_name(u1.getLast_name());
        u2.setPassword(requestPassword.getPassword());
        u2.setDeleted(u1.isDeleted());
        u2.setActive(u1.isActive());
        u2.setExpired(u1.isExpired());
        u2.setLocked(u1.isLocked());
        u2.setInvalid_attempt_count(u1.getInvalid_attempt_count());
        u2.setPassword_update_date(new Date(System.currentTimeMillis()));
        if(u1.getAddress()!=null) {
            int addId = u1.getAddress().getId();
            Address a1 = addressRepository.findById(addId).get();
            u2.setAddress(a1);
        }
        if(u1.getSeller() !=null) {

            int sellerId = u1.getSeller().getId();
            Seller s1 = sellerRepository.findById(sellerId).get();
            u2.setSeller(s1);
        }

        if(u1.getCustomer()!=null) {
            int customerId = u1.getCustomer().getId();
            Customer c1 = customerRepository.findById(customerId).get();
            u2.setCustomer(c1);
        }

        int roleId = u1.getRole().getId();
        Role r1 = roleRepository.findById(roleId).get();
        u2.setRole(r1);

        userRepository.save(u2);

        return "PASSWORD HAS BEEN UPDATED";


    }


}
