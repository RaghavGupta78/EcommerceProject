package Raghav.EcommerceProject.api;

import Raghav.EcommerceProject.entities.Customer;
import Raghav.EcommerceProject.repositories.CustomerRepository;
import Raghav.EcommerceProject.security.helper.JwtUtil;
import Raghav.EcommerceProject.security.model.JwtRequest;
import Raghav.EcommerceProject.security.model.JwtResponse;
import Raghav.EcommerceProject.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/log")
public class LogController {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;


    //customer log in
    @PostMapping("/login/customer")
    public ResponseEntity<?> generateTokenCustomer(@RequestBody JwtRequest jwtRequest) throws Exception {
        System.out.println(jwtRequest);
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword()));
        }catch (UsernameNotFoundException e){
            e.printStackTrace();
            throw new Exception("bad credentials");
        }catch (BadCredentialsException e){
            e.printStackTrace();
            throw new Exception("bad credentials");
        }

        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtUtil.generateToken(userDetails);
        System.out.println("JWT token is  "+ token);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    //Seller log in
    @PostMapping("/login/seller")
    public ResponseEntity<?> generateTokenSeller(@RequestBody JwtRequest jwtRequest) throws Exception {
        System.out.println(jwtRequest);
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword()));
        }catch (UsernameNotFoundException e){
            e.printStackTrace();
            throw new Exception("bad credentials");
        }catch (BadCredentialsException e){
            e.printStackTrace();
            throw new Exception("bad credentials");
        }

        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtUtil.generateToken(userDetails);
        System.out.println("JWT token is  "+ token);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    //admin log in
    @PostMapping("/login/admin")
    public ResponseEntity<?> generateTokenAdmin(@RequestBody JwtRequest jwtRequest) throws Exception {
        System.out.println(jwtRequest);
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword()));
        }catch (UsernameNotFoundException e){
            e.printStackTrace();
            throw new Exception("bad credentials");
        }catch (BadCredentialsException e){
            e.printStackTrace();
            throw new Exception("bad credentials");
        }

        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtUtil.generateToken(userDetails);

        System.out.println("JWT token is  "+ token);
        return ResponseEntity.ok(new JwtResponse(token));

    }

    //logout customer
    @GetMapping("/logout/customer")
    public String logoutCustomer(){


        return "YOU HAVE SAFELY LOGGED OUT YOUR TOKEN WILL NO LONGER WORK AS EACH TOKEN IS VALID DOR 2 MINUTES PLEASE ISSUE A NEW ONE";
    }

    //logout seller
    @GetMapping("/logout/seller")
    public String logoutSeller(){


        return "YOU HAVE SAFELY LOGGED OUT YOUR TOKEN WILL NO LONGER WORK AS EACH TOKEN IS VALID DOR 2 MINUTES PLEASE ISSUE A NEW ONE";
    }

    //logout admin
    @GetMapping("/logout/admin")
    public String logoutAdmin(){


        return "YOU HAVE SAFELY LOGGED OUT YOUR TOKEN WILL NO LONGER WORK AS EACH TOKEN IS VALID DOR 2 MINUTES PLEASE ISSUE A NEW ONE";
    }





}
