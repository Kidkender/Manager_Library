package vn.sparkminds.services;

import java.util.List;
import vn.sparkminds.exceptions.CategoryException;
import vn.sparkminds.model.Category;
import vn.sparkminds.services.dto.request.AddCategoryRequest;
import vn.sparkminds.services.dto.request.UpdateCategoryRequest;

public interface CategoryService {
    public Category createCategory(AddCategoryRequest category);

    public void deleteCategory(Long categoryId) throws CategoryException;

    public List<Category> createMultipleCategories(AddCategoryRequest[] cateReq);

    public Category updateCategory(Long categoryId, UpdateCategoryRequest req)
            throws CategoryException;

    public Category findCategoryById(Long categoryId) throws CategoryException;

    public List<Category> findAllCategories();
}
