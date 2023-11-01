package vn.sparkminds.services;

import java.util.List;
import vn.sparkminds.exceptions.CategoryException;
import vn.sparkminds.model.Category;

public interface CategoryService {
    public Category createCategory(Category category);

    public void deleteCategory(Long categoryId) throws CategoryException;

    public Category findCategoryById(Long categoryId) throws CategoryException;

    public List<Category> findAllCategories();
}
