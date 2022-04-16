package Raghav.EcommerceProject.api;

import Raghav.EcommerceProject.entities.Customer;
import Raghav.EcommerceProject.entities.Seller;
import Raghav.EcommerceProject.entities.User;
import Raghav.EcommerceProject.exceptions.InvalidCredentialsExceptions;
import Raghav.EcommerceProject.exceptions.UnauthorizedAccessException;
import Raghav.EcommerceProject.repositories.AccessTokenRepository;
import Raghav.EcommerceProject.repositories.CustomerRepository;
import Raghav.EcommerceProject.repositories.SellerRepository;
import Raghav.EcommerceProject.repositories.UserRepository;
import Raghav.EcommerceProject.security.config.JwtAuthenticationFilter;
import Raghav.EcommerceProject.security.helper.JwtUtil;
import Raghav.EcommerceProject.security.model.JwtRequest;
import Raghav.EcommerceProject.security.model.JwtResponse;
import Raghav.EcommerceProject.security.service.CustomUserDetailsService;
import Raghav.EcommerceProject.tokens.AccessToken;
import Raghav.EcommerceProject.tokens.ActivationToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccessTokenRepository accessTokenRepository;


    //customer log in
    @PostMapping("/login/customer")
    public ResponseEntity<?> generateTokenCustomer(HttpServletRequest request,@RequestBody JwtRequest jwtRequest) throws Exception {




        String username = jwtRequest.getUsername();
        String password = jwtRequest.getPassword();
        Customer c1 = customerRepository.findByEmail(username);

        String role = c1.getUser().getRole().getAuthority();

        if(!role.equalsIgnoreCase("customer")){
            throw new UnauthorizedAccessException("YOU ARE NOT AUTHORIZED");
        }

        System.out.println(password);
        System.out.println(c1.getPassword());
        if( c1!=null && password.equals(c1.getPassword())){
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("raghav","raghav123"));
            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername("raghav");
            String token = this.jwtUtil.generateToken(userDetails);
            System.out.println("JWT token is  "+ token);


            AccessToken at = new AccessToken();
            at.setAccessTokenName(token);
            accessTokenRepository.save(at);

            return ResponseEntity.ok(new JwtResponse(token));
        }
        else{
            throw new InvalidCredentialsExceptions("THE CREDENTIALS ARE NOT VALID");
        }


    }

    //Seller log in
    @PostMapping("/login/seller")
    public ResponseEntity<?> generateTokenSeller(@RequestBody JwtRequest jwtRequest) throws Exception {


        String username = jwtRequest.getUsername();
        String password = jwtRequest.getPassword();
        Seller c1 = sellerRepository.findByEmail(username);

        String role = c1.getUser().getRole().getAuthority();

        if(!role.equalsIgnoreCase("seller")){
            throw new UnauthorizedAccessException("YOU ARE NOT AUTHORIZED");
        }


        System.out.println(password);
        System.out.println(c1.getPassword());
        if( c1!=null && password.equals(c1.getPassword())){
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("raghav","raghav123"));
            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername("raghav");
            String token = this.jwtUtil.generateToken(userDetails);
            System.out.println("JWT token is  "+ token);

            AccessToken at = new AccessToken();
            at.setAccessTokenName(token);
            accessTokenRepository.save(at);


            return ResponseEntity.ok(new JwtResponse(token));
        }
        else{
            throw new InvalidCredentialsExceptions("THE CREDENTIALS ARE NOT VALID");
        }

    }

    //admin log in
    @PostMapping("/login/admin")
    public ResponseEntity<?> generateTokenAdmin(@RequestBody JwtRequest jwtRequest) throws Exception {

        String username = jwtRequest.getUsername();
        String password = jwtRequest.getPassword();
        User c1 = userRepository.findByEmail(username);

        String role = c1.getRole().getAuthority();
        if(!role.equalsIgnoreCase("admin")){
            throw new UnauthorizedAccessException("YOU ARE NOT AUTHORIZED");
        }


        System.out.println(password);
        System.out.println(c1.getPassword());
        if( c1!=null && password.equals(c1.getPassword())){
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("raghav","raghav123"));
            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername("raghav");
            String token = this.jwtUtil.generateToken(userDetails);
            System.out.println("JWT token is  "+ token);

            AccessToken at = new AccessToken();
            at.setAccessTokenName(token);
            accessTokenRepository.save(at);


            return ResponseEntity.ok(new JwtResponse(token));
        }
        else{
            throw new InvalidCredentialsExceptions("THE CREDENTIALS ARE NOT VALID");
        }

    }

    //logout customer
    @GetMapping("/logout/customer")
    public String logoutCustomer(HttpServletRequest request){


        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        AccessToken a1 = accessTokenRepository.findByAccessTokenName(jwtToken);
        accessTokenRepository.deleteById(a1.getId());



        return "YOU HAVE SAFELY LOGGED OUT YOUR TOKEN WILL NO LONGER WORK AS EACH TOKEN IS VALID DOR 2 MINUTES PLEASE ISSUE A NEW ONE";
    }

    //logout seller
    @GetMapping("/logout/seller")
    public String logoutSeller(HttpServletRequest request){

        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        AccessToken a1 = accessTokenRepository.findByAccessTokenName(jwtToken);
        accessTokenRepository.deleteById(a1.getId());
        return "YOU HAVE SAFELY LOGGED OUT YOUR TOKEN WILL NO LONGER WORK AS EACH TOKEN IS VALID DOR 2 MINUTES PLEASE ISSUE A NEW ONE";
    }

    //logout admin
    @GetMapping("/logout/admin")
    public String logoutAdmin(HttpServletRequest request){

        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        AccessToken a1 = accessTokenRepository.findByAccessTokenName(jwtToken);
        accessTokenRepository.deleteById(a1.getId());

        return "YOU HAVE SAFELY LOGGED OUT YOUR TOKEN WILL NO LONGER WORK AS EACH TOKEN IS VALID DOR 2 MINUTES PLEASE ISSUE A NEW ONE";
    }





}
