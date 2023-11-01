package vn.sparkminds.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.sparkminds.exceptions.CategoryException;
import vn.sparkminds.exceptions.UserException;
import vn.sparkminds.model.Category;
import vn.sparkminds.model.User;
import vn.sparkminds.services.CategoryService;
import vn.sparkminds.services.UserService;
import vn.sparkminds.services.dto.response.ApiResponse;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Category> createCategoryHandler(
            @RequestHeader("Authorization") String jwt, @RequestBody Category req)
            throws UserException {
        User user = userService.findUserByJwt(jwt);
        Category created = categoryService.createCategory(req);
        return new ResponseEntity<Category>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategoriesHandler() {
        List<Category> result = categoryService.findAllCategories();
        return new ResponseEntity<List<Category>>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long id)
            throws CategoryException {
        Category category = categoryService.findCategoryById(id);
        return new ResponseEntity<Category>(category, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategoryHandler(@PathVariable("id") Long id,
            @RequestHeader("Authorization") String authorization)
            throws UserException, CategoryException {
        User user = userService.findUserByJwt(authorization);
        categoryService.deleteCategory(id);
        ApiResponse response = new ApiResponse("Delete category sucessfully", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
