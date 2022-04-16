package Raghav.EcommerceProject.api;

import Raghav.EcommerceProject.entities.*;
import Raghav.EcommerceProject.exceptions.*;
import Raghav.EcommerceProject.repositories.*;
import Raghav.EcommerceProject.security.helper.JwtUtil;
import Raghav.EcommerceProject.security.model.JwtResponse;
import Raghav.EcommerceProject.security.service.CustomUserDetailsService;
import Raghav.EcommerceProject.service.EmailSenderService;
import Raghav.EcommerceProject.tokens.AccessToken;
import Raghav.EcommerceProject.tokens.ActivationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    @Autowired
    private ActivationTokenRepository activationTokenRepository;

    @Value("${file.upload-dir}")
    String FILE_DIRECTORY;


    //Register a customer
    @PostMapping("/customer")
    public String createCustomer(@RequestBody Customer customer) throws PasswordValidationException, InvalidEmailException, PhoneValidationException {

        int id = customer.getUser().getId();
        customer.setUser(userRepository.findById(id).get());
        String valid = customer.getEmail().substring(customer.getEmail().length()-4);
        System.out.println(valid);
        if (!valid.equals(".com")){
            throw new InvalidEmailException("ENTERED EMAIL ID IS NOT VALID");
        }

        if (!customer.getPassword().equals(customer.getConfirmPassword())){
            throw new PasswordValidationException("PASSWORD VALIDATION FAILED");
        }
        if(customer.getContact().length()<10 || customer.getPhoneNumber().length()<10){
            throw new PhoneValidationException("THE CONTACT NUMBER IS TOO SHORT");
        }
        if(!isValidPassword(customer.getPassword())){
            throw new PasswordValidationException("YOU DON'T FULFILL THE PASSWORD REQUIREMENTS");
        }


        if(customer.getEmail()==null){
            return "The email id does not exist";
        }
        customerRepository.save(customer);
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("raghav","raghav123"));
        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername("raghav");
        String token = this.jwtUtil.generateToken(userDetails);
        System.out.println("JWT token is  "+ token);

        ActivationToken at = new ActivationToken();
        at.setActivationTokenName(token);
        activationTokenRepository.save(at);



        emailSenderService.sendEmail(customer.getEmail(),"Customer Registration to E-commerce","Hi there "+customer.getFirstName()+" you have been registered" +
                " here is your token "+token+" Use this token for activation, It is valid for 2 minutes only");

        return "THE CUSTOMER HAS BEEN REGISTERED AND A MAIL HAS BEEN SENT TO YOU WITH THE ACTIVATION TOKEN AT "+customer.getEmail();
    }


    @PostMapping("/customerImage/{id}")
    public String createCustomerImage(@PathVariable("id") Integer id,@RequestParam("File") MultipartFile file) throws IOException{


        User u1 = userRepository.findById(id).get();

        //File myFile = new File(FILE_DIRECTORY+file.getOriginalFilename());
        File myFile = new File(FILE_DIRECTORY+u1.getId());

        myFile.createNewFile();


        System.out.println(myFile);
        FileOutputStream fos = new FileOutputStream(myFile);
        fos.write(file.getBytes());
        fos.close();
        return "THE IMAGE OF THE CUSTOMER WITH USER ID "+u1.getId()+" HAS BEEN ADDED TO THE DATABASE";

    }




    //Activate a customer
    @PatchMapping("/customer-activate/{id}")
    public String activateCustomer(HttpServletRequest request,@PathVariable("id") Integer id) throws AlreadyActiveException, UnauthorizedAccessException, InvalidTokenException {


        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!activationTokenRepository.findByActivationTokenName(jwtToken).getActivationTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }


        Customer customer = customerRepository.findById(id).get();
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
//        String role = u1.getRole().getAuthority();
//        if (role!="CUSTOMER"){
//            throw new UnauthorizedAccessException("YOU ARE NOT AUTHORIZED");
//        }
        u1.setActive(true);
        c1.setUser(u1);
        userRepository.save(u1);
        Customer updatedCustomer = customerRepository.save(c1);
        return "THE USER IS ACTIVATED";

    }

    //Re-send Activation link
    @GetMapping("/resend/activation/{email}")
    public String resendToken(HttpServletRequest request,@PathVariable ("email") String email)
    {



        int id = customerRepository.findByEmail(email).getId();
        Customer customer = customerRepository.findById(id).get();

        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("raghav","raghav123"));
        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername("raghav");
        String token = this.jwtUtil.generateToken(userDetails);
        System.out.println("JWT token is  "+ token);

        ActivationToken at = new ActivationToken();
        at.setActivationTokenName(token);
        activationTokenRepository.save(at);


        emailSenderService.sendEmail(customer.getEmail(),"Customer Registration to E-commerce","Hi there "+customer.getFirstName()+" you have been registered" +
                " here is your token "+token+" Use this token for activation, It is valid for 2 minutes only");

        return "THE CUSTOMER HAS BEEN REGISTERED AND A MAIL HAS BEEN SENT TO YOU WITH THE ANOTHER NEW ACTIVATION TOKEN AT "+customer.getEmail();

    }



    //Register a seller
    @PostMapping("/seller")
    public String create(@RequestBody Seller seller) throws PasswordValidationException, InvalidEmailException, PhoneValidationException {


        String valid = seller.getEmail().substring(seller.getEmail().length()-4);
        System.out.println(valid);
        if (!valid.equals(".com")){
            throw new InvalidEmailException("ENTERED EMAIL ID IS NOT VALID");
        }

        if(!seller.getPassword().equals(seller.getConfirmPassword())){
            throw new PasswordValidationException("PASSWORD VALIDATION FAILED");
        }
        if(seller.getContact_number().length()<10 ){
            throw new PhoneValidationException("THE CONTACT NUMBER IS TOO SHORT");
        }
        if(!isValidPassword(seller.getPassword())){
            throw new PasswordValidationException("YOU DON'T FULFILL THE PASSWORD REQUIREMENTS");
        }
        int id = seller.getUser().getId();
        seller.setUser(userRepository.findById(id).get());
        sellerRepository.save(seller);
        System.out.println(seller.getCompany_name());

        emailSenderService.sendEmail(seller.getEmail(),"Seller Registration to E-commerce","Hi there "+seller.getFirstName()+" your account has been created" +
                " Please Wait for admin to approve your account");
        return "THE SELLER HAS BEEN REGISTERED AND A MAIL HAS BEEN SENT TO "+seller.getEmail()+" PLEASE WAIT FOR THE APPROVAL BY ADMIN";
    }

    @PostMapping("/sellerImage/{id}")
    public String createSellerImage(@PathVariable("id") Integer id,@RequestParam("File") MultipartFile file) throws IOException{


        User u1 = userRepository.findById(id).get();

        //File myFile = new File(FILE_DIRECTORY+file.getOriginalFilename());
        File myFile = new File(FILE_DIRECTORY+u1.getId());
        myFile.createNewFile();
        System.out.println(myFile);
        FileOutputStream fos = new FileOutputStream(myFile);
        fos.write(file.getBytes());
        fos.close();
        return "THE IMAGE OF THE SELLER WITH USER ID "+u1.getId()+" HAS BEEN ADDED TO THE DATABASE";

    }




    //Register a user
    @PostMapping("/user")
    public User create(@RequestBody User user) throws InvalidEmailException, PasswordValidationException {

        int id = user.getRole().getId();
        user.setRole(roleRepository.findById(id).get());
        String valid = user.getEmail().substring(user.getEmail().length()-4);
        System.out.println(valid);
        if (!valid.equals(".com")){
            throw new InvalidEmailException("ENTERED EMAIL ID IS NOT VALID");
        }
        if(!isValidPassword(user.getPassword())){
            throw new PasswordValidationException("YOU DON'T FULFILL THE PASSWORD REQUIREMENTS");
        }

        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("raghav","raghav123"));
        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername("raghav");
        String token = this.jwtUtil.generateToken(userDetails);
        System.out.println("JWT token is  "+ token);



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

    public static boolean
    isValidPassword(String password)
    {

        // Regex to check valid password.
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
        if (password == null) {
            return false;
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }


    @GetMapping("/admin/token")
    public ResponseEntity<?> create() {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("raghav","raghav123"));
        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername("raghav");
        String token = this.jwtUtil.generateToken(userDetails);
        System.out.println("JWT token is  "+ token);

        AccessToken at = new AccessToken();
        at.setAccessTokenName(token);
        accessTokenRepository.save(at);
        return ResponseEntity.ok(new JwtResponse(token));
    }


}
