package vn.sparkminds.controllers.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import vn.sparkminds.exceptions.CategoryException;
import vn.sparkminds.exceptions.UserException;
import vn.sparkminds.model.Category;
import vn.sparkminds.model.User;
import vn.sparkminds.services.CategoryService;
import vn.sparkminds.services.UserService;
import vn.sparkminds.services.dto.request.AddCategoryRequest;
import vn.sparkminds.services.dto.request.UpdateCategoryRequest;
import vn.sparkminds.services.dto.response.ApiResponse;

@RestController
@RequestMapping("/api/admin/categories")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Category> createCategoryHandler(
            @RequestHeader("Authorization") String jwt, @Valid @RequestBody AddCategoryRequest req)
            throws UserException {
        User user = userService.findUserByJwt(jwt);
        Category created = categoryService.createCategory(req);
        return new ResponseEntity<Category>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Category> updateCategoryHandler(
            @RequestHeader("Authorization") String authorization, @PathVariable("id") Long id,
            @Valid @RequestBody UpdateCategoryRequest req) throws UserException, CategoryException {

        User user = userService.findUserByJwt(authorization);
        Category category = categoryService.updateCategory(id, req);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> deleteCategoryHandler(@PathVariable("id") Long id,
            @RequestHeader("Authorization") String authorization)
            throws UserException, CategoryException {
        User user = userService.findUserByJwt(authorization);
        categoryService.deleteCategory(id);
        ApiResponse response = new ApiResponse("Delete category sucessfully", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add/multiple")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Category>> addMutipleCategoriesHandler(
            @RequestHeader("Authorization") String authorization,
            @Valid @RequestBody AddCategoryRequest[] categories) throws UserException {

        User user = userService.findUserByJwt(authorization);
        List<Category> listCategory = categoryService.createMultipleCategories(categories);
        return new ResponseEntity<>(listCategory, HttpStatus.OK);
    }
}
