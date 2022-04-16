package Raghav.EcommerceProject.api;

import Raghav.EcommerceProject.entities.Category;
import Raghav.EcommerceProject.entities.Product;
import Raghav.EcommerceProject.entities.ProductVariation;
import Raghav.EcommerceProject.entities.Seller;
import Raghav.EcommerceProject.exceptions.InvalidTokenException;
import Raghav.EcommerceProject.repositories.*;
import Raghav.EcommerceProject.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private ProductVariationRepository productVariationRepository;

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    //add a product
    @PostMapping("/add")
    public String addProduct(HttpServletRequest request, @RequestBody Product product) throws InvalidTokenException {

        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }

        Product p1 = new Product();
        p1.setId(product.getId());
        p1.setBrand(product.getBrand());
        p1.setDescription(product.getDescription());
        p1.setIs_active(product.isIs_active());
        p1.setIs_cancellable(product.isIs_cancellable());
        p1.setIs_deleted(product.isIs_deleted());
        p1.setIs_returnable(product.isIs_returnable());
        p1.setName(product.getName());
        int categoryId = product.getCategory().getId();
        Category c1 = categoryRepository.findById(categoryId).get();
        p1.setCategory(c1);
        int sellerId = product.getSeller().getId();
        Seller s1 = sellerRepository.findById(sellerId).get();
        p1.setSeller(s1);
        productRepository.save(p1);
        return "THE PRODUCT HAS BEEN ADDED";
    }

    //

    //get a product
    @GetMapping("/{id}")
    public Product find(HttpServletRequest request,@PathVariable ("id") Integer id) throws InvalidTokenException {

        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }

        return productRepository.findById(id).get();
    }

    //find all product
    @GetMapping("/seller/viewAll")
    private List<Product> viewAll(HttpServletRequest request) throws InvalidTokenException {


        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }

        List<Product> p = productRepository.findAllProduct();
        for (int i = 0;i<p.size();i++){
            System.out.println(p.get(i));
        }

        System.out.println(productRepository.findAllProduct());
        return p;
    }

    //delete a product
    @DeleteMapping("/seller/remove/{id}")
    public String delete(HttpServletRequest request,@PathVariable("id") Integer id) throws InvalidTokenException {

        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }

        Product p1 = productRepository.findById(id).get();
        productRepository.deleteById(id);
        return "PRODUCT IS REMOVED";
    }

    //update a product
    @PutMapping("/update/{id}")
    private String update(HttpServletRequest request,@RequestBody Product product) throws InvalidTokenException {

        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }

        Product p1 = new Product();
        p1.setId(product.getId());
        p1.setBrand(product.getBrand());
        p1.setDescription(product.getDescription());
        p1.setIs_active(product.isIs_active());
        p1.setIs_cancellable(product.isIs_cancellable());
        p1.setIs_deleted(product.isIs_deleted());
        p1.setIs_returnable(product.isIs_returnable());
        p1.setName(product.getName());
        int categoryId = product.getCategory().getId();
        Category c1 = categoryRepository.findById(categoryId).get();
        p1.setCategory(c1);
        int sellerId = product.getSeller().getId();
        Seller s1 = sellerRepository.findById(sellerId).get();
        p1.setSeller(s1);
        productRepository.save(p1);
        return "THE PRODUCT HAS BEEN UPDATED";

    }

    //add a product variation
    @PostMapping("/add/variation")
    private String addVariation(HttpServletRequest request,@RequestBody ProductVariation productVariation) throws InvalidTokenException {

        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }

        ProductVariation v1 = new ProductVariation();
        v1.setId(productVariation.getId());
        v1.setIs_active(productVariation.isIs_active());
        v1.setQuantity_available(productVariation.getQuantity_available());
        v1.setPrice(productVariation.getPrice());
        int productId = productVariation.getProduct().getId();
        Product p1 = productRepository.findById(productId).get();
        v1.setProduct(p1);
        v1.setMetadata(v1.getMetadata());

        productVariationRepository.save(v1);

        return "A VARIATION HAS BEEN ADDED";
    }

    //view a product variation
    @GetMapping("/seller/viewVariation/{id}")
    private ProductVariation view(HttpServletRequest request,@PathVariable("id") Integer id) throws InvalidTokenException {

        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }


        return productVariationRepository.findById(id).get();
    }

    //view all variation for a product
    @GetMapping("/viewAllVariation/{id}")
    private List<ProductVariation> viewAll(HttpServletRequest request,@PathVariable ("id") Integer id) throws InvalidTokenException {

        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }

        List<ProductVariation> p1 = productVariationRepository.findAllByProductId(id);
        return p1;
    }

    //update product variation
    @PostMapping("/update/variation")
    private String UpdateVariation(HttpServletRequest request,@RequestBody ProductVariation productVariation) throws InvalidTokenException {

        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }

        ProductVariation v1 = new ProductVariation();
        v1.setId(productVariation.getId());
        v1.setIs_active(productVariation.isIs_active());
        v1.setQuantity_available(productVariation.getQuantity_available());
        v1.setPrice(productVariation.getPrice());
        int productId = productVariation.getProduct().getId();
        Product p1 = productRepository.findById(productId).get();
        v1.setProduct(p1);
        v1.setMetadata(v1.getMetadata());
        productVariationRepository.save(v1);
        return "THE VARIATION HAS BEEN UPDATED";
    }





    //find all product customer
    @GetMapping("/customer/viewAll/{id}")
    private List<Product> viewAllProducts(HttpServletRequest request,@PathVariable ("id") Integer id) throws InvalidTokenException {

        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }

        List<Product> p = productRepository.findAllProduct();
        List<Product> p1 = new ArrayList<>();
        for (int i = 0;i<p.size();i++){
            if(p.get(i).getCategory().getId()==id) {

                p1.add(p.get(i));
            }
        }


        return p1;

    }

    //find a product
    @GetMapping("/customer/{id}")
    private Product viewProduct(HttpServletRequest request,@PathVariable ("id") Integer id) throws InvalidTokenException {


        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }

        return productRepository.findById(id).get();
    }

    //find similar product
    @GetMapping("/customer-similar/{id}")
    private List<Product> viewSimilar(HttpServletRequest request,@PathVariable ("id") Integer id) throws InvalidTokenException {

        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }

        Product p1 = productRepository.findById(id).get();
        int categoryId = p1.getCategory().getId();
        List<Product> p2 = new ArrayList<>();
        List<Product> p = productRepository.findAllProduct();
        for (int i = 0;i<p.size();i++){
            if(p.get(i).getCategory().getId()==id) {

                p2.add(p.get(i));

            }
        }

        return p2;


    }


    //find a product admin
    @GetMapping("/admin/{id}")
    private Product viewAdminProduct(HttpServletRequest request,@PathVariable ("id") Integer id) throws InvalidTokenException {

        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }

        return productRepository.findById(id).get();
    }

    //find all product
    @GetMapping("/admin/viewAll")
    private List<Product> viewAllAdmin(HttpServletRequest request) throws InvalidTokenException {

        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }

        List<Product> p = productRepository.findAllProduct();
        for (int i = 0;i<p.size();i++){
            System.out.println(p.get(i));
        }

        System.out.println(productRepository.findAllProduct());
        return p;
    }

    //activate a product
    @GetMapping("/admin/activate/{id}")
    public String activateProduct(HttpServletRequest request,@PathVariable ("id") Integer id) throws InvalidTokenException {

        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }

        Product p1 = new Product();
        Product product = productRepository.findById(id).get();
        p1.setId(product.getId());
        p1.setBrand(product.getBrand());
        p1.setDescription(product.getDescription());
        p1.setIs_active(true);
        p1.setIs_cancellable(product.isIs_cancellable());
        p1.setIs_deleted(product.isIs_deleted());
        p1.setIs_returnable(product.isIs_returnable());
        p1.setName(product.getName());
        int categoryId = product.getCategory().getId();
        Category c1 = categoryRepository.findById(categoryId).get();
        p1.setCategory(c1);
        int sellerId = product.getSeller().getId();
        Seller s1 = sellerRepository.findById(sellerId).get();
        p1.setSeller(s1);
        productRepository.save(p1);

        emailSenderService.sendEmail(s1.getEmail(),"PRODUCT ACTIVATION","YOUR PRODUCT "+p1.getName()+" HAS BEEN ACTIVATED");
        return "THE PRODUCT "+p1.getName()+" HAS BEEN ACTIVATED THE MAIL HAS BEEN SENT TO THE SELLER INFORMING THE SAME";


    }

    //deactivate a product
    @GetMapping("/admin/de-activate/{id}")
    public String deActivateProduct(HttpServletRequest request,@PathVariable ("id") Integer id) throws InvalidTokenException {

        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }

        Product p1 = new Product();
        Product product = productRepository.findById(id).get();
        p1.setId(product.getId());
        p1.setBrand(product.getBrand());
        p1.setDescription(product.getDescription());
        p1.setIs_active(false);
        p1.setIs_cancellable(product.isIs_cancellable());
        p1.setIs_deleted(product.isIs_deleted());
        p1.setIs_returnable(product.isIs_returnable());
        p1.setName(product.getName());
        int categoryId = product.getCategory().getId();
        Category c1 = categoryRepository.findById(categoryId).get();
        p1.setCategory(c1);
        int sellerId = product.getSeller().getId();
        Seller s1 = sellerRepository.findById(sellerId).get();
        p1.setSeller(s1);
        productRepository.save(p1);

        emailSenderService.sendEmail(s1.getEmail(),"PRODUCT DE-ACTIVATION","YOUR PRODUCT "+p1.getName()+" HAS BEEN DE-ACTIVATED");
        return "THE PRODUCT "+p1.getName()+" HAS BEEN DE-ACTIVATED THE MAIL HAS BEEN SENT TO THE SELLER INFORMING THE SAME";


    }


}
