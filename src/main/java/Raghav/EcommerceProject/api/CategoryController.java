package Raghav.EcommerceProject.api;

import Raghav.EcommerceProject.ModelRequest.CategoryRequest;
import Raghav.EcommerceProject.entities.Category;
import Raghav.EcommerceProject.entities.CategoryMetadataField;
import Raghav.EcommerceProject.entities.CategoryMetadataFieldValues;
import Raghav.EcommerceProject.entities.ParentCategory;
import Raghav.EcommerceProject.exceptions.InvalidTokenException;
import Raghav.EcommerceProject.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ParentCategoryRepository parentCategoryRepository;

    @Autowired
    private CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Autowired
    private CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    @PostMapping("/addParentCategory")
    public String createParentCategory(@RequestBody ParentCategory parentCategory){
        parentCategoryRepository.save(parentCategory);
        return "A PARENT CATEGORY HAS BEEN ADDED";
    }

    @PostMapping("/addCategory")
    public String createCategory(HttpServletRequest request, @RequestBody Category category) throws InvalidTokenException {


        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }

        int id = category.getParentCategory().getId();
        ParentCategory parentCategory = parentCategoryRepository.findById(id).get();
        ParentCategory p1 = new ParentCategory();
        p1.setId(parentCategory.getId());
        p1.setCategoryType(parentCategory.getCategoryType());
        System.out.println(p1.getCategoryType());

        categoryRepository.save(category);
        parentCategoryRepository.save(p1);

        return "A CATEGORY HAS BEEN ADDED ";
    }

    @GetMapping("/view/{id}")
    public Category viewCategory(HttpServletRequest request,@PathVariable ("id") Integer id) throws InvalidTokenException {


        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }

        return categoryRepository.findById(id).get();
    }

    @GetMapping("/viewAll/category")
    public List<Category> printAllCategory(HttpServletRequest request) throws InvalidTokenException {


        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }


        List<Category> c = categoryRepository.findAllCategory();
        for (int i =0;i<c.size();i++){
            System.out.println(c.get(i));
        }
        System.out.println(categoryRepository.findAllCategory());
        return c;
    }

    @PutMapping("/updateCategory")
    public String updateCategory(HttpServletRequest request,@RequestBody CategoryRequest categoryRequest) throws InvalidTokenException {


        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }

        int id = categoryRequest.getId();
        String name = categoryRequest.getName();

        Category c1 = categoryRepository.findById(id).get();
        int parentId = c1.getParentCategory().getId();
        ParentCategory p1 = parentCategoryRepository.findById(parentId).get();


        Category c2 = new Category();
        c2.setId(id);
        c2.setName(name);
        c2.setParentCategory(p1);

        categoryRepository.save(c2);

        return "THE CATEGORY HAS BEEN UPDATED";


    }


    @PostMapping("/addMetaDataField")
    public String createMetaDataField(HttpServletRequest request,@RequestBody CategoryMetadataField categoryMetadataField) throws InvalidTokenException {

        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }


        int id = categoryMetadataField.getCategory().getId();
        Category category = categoryRepository.findById(id).get();
        Category c1 = new Category();
        c1.setId(category.getId());
        c1.setName(category.getName());
        c1.setParentCategory(category.getParentCategory());

        categoryMetadataFieldRepository.save(categoryMetadataField);
        categoryRepository.save(c1);

        return "A CATEGORY METADATA FIELD HAS BEEN CREATED";
    }

    @GetMapping("/viewAll/metaDataField")
    public List<CategoryMetadataField> printAll(HttpServletRequest request) throws InvalidTokenException {

        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }


        List<CategoryMetadataField> mf = categoryMetadataFieldRepository.findAllCategoryMetaDataField();
        for(int i = 0;i< mf.size();i++){
            System.out.println(mf.get(i));
        }
        System.out.println(categoryMetadataFieldRepository.findAllCategoryMetaDataField());
        return mf;
    }

    @PutMapping("/updateMetaDataField")
    public String updateMetaDataField(HttpServletRequest request,@RequestBody CategoryRequest categoryRequest) throws InvalidTokenException {

        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }


        int id = categoryRequest.getId();
        String name = categoryRequest.getName();

        CategoryMetadataField cf = categoryMetadataFieldRepository.findById(id).get();
        int categoryId = cf.getCategory().getId();
        Category c1 = categoryRepository.findById(categoryId).get();

        CategoryMetadataField cf2 = new CategoryMetadataField();
        cf2.setId(id);
        cf2.setName(name);
        cf2.setCategory(c1);

        categoryMetadataFieldRepository.save(cf2);

        return "THE METADATA FIELD HAS BEEN UPDATED";

    }




    @GetMapping("/seller/viewAll/category")
    public List<Category> printAllSellerCategory(HttpServletRequest request) throws InvalidTokenException {


        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }


        List<Category> c = categoryRepository.findAllCategory();
        for (int i =0;i<c.size();i++){
            System.out.println(c.get(i));
        }
        System.out.println(categoryRepository.findAllCategory());
        return c;
    }


    @GetMapping("/customer/viewAll/category")
    public List<Category> printAllCustomerCategory(HttpServletRequest request) throws InvalidTokenException {

        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }


        List<Category> c = categoryRepository.findAllCategory();
        for (int i =0;i<c.size();i++){
            System.out.println(c.get(i));
        }
        System.out.println(categoryRepository.findAllCategory());
        return c;
    }

    @PostMapping("/addValues")
    public String addValues(HttpServletRequest request,@RequestBody CategoryMetadataFieldValues categoryMetadataFieldValues) throws InvalidTokenException {


        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }


        int idMetaData = categoryMetadataFieldValues.getCategoryMetadataField().getId();
        CategoryMetadataField cmf = categoryMetadataFieldRepository.findById(idMetaData).get();
        CategoryMetadataField c1 = new CategoryMetadataField();
        c1.setId(cmf.getId());
        c1.setName(cmf.getName());
        c1.setCategory(cmf.getCategory());



        int idCategory = categoryMetadataFieldValues.getCategory().getId();
        Category category = categoryRepository.findById(idCategory).get();
        Category c2 = new Category();
        c2.setId(category.getId());
        c2.setName(category.getName());
        c2.setParentCategory(category.getParentCategory());

        categoryMetadataFieldValuesRepository.save(categoryMetadataFieldValues);
        categoryMetadataFieldRepository.save(c1);
        categoryRepository.save(c2);

        return "VALUES ADDED";
    }

    @GetMapping("/viewAll/values")
    public List<CategoryMetadataFieldValues> printAllValues(HttpServletRequest request) throws InvalidTokenException {


        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        jwtToken = requestTokenHeader.substring(7);

        if(!accessTokenRepository.findByAccessTokenName(jwtToken).getAccessTokenName().equals(jwtToken)){
            throw new InvalidTokenException("THE TOKEN YOU ENTERED IS INVALID");
        }

//
//        List<Category> c = categoryRepository.findAllCategory();
//        for (int i =0;i<c.size();i++){
//            System.out.println(c.get(i));
//        }
//        System.out.println(categoryRepository.findAllCategory());
//        return c;

        List<CategoryMetadataFieldValues> c = categoryMetadataFieldValuesRepository.findAllCategoryMetaDataFieldValues();
        for (int i =0;i<c.size();i++){
            System.out.println(c.get(i));
        }
        return c;

    }



}
