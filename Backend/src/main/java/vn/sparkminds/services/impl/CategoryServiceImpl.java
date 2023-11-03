package vn.sparkminds.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.sparkminds.exceptions.CategoryException;
import vn.sparkminds.model.Category;
import vn.sparkminds.repositories.CategoryRepository;
import vn.sparkminds.services.CategoryService;
import vn.sparkminds.services.dto.request.AddCategoryRequest;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(AddCategoryRequest category) {
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        newCategory.setDescription(category.getDescription());
        newCategory.setQuantityInvetory(category.getTotal());
        newCategory.setTotalBook(category.getTotal());
        newCategory.setCreatedAt(LocalDateTime.now());
        return categoryRepository.save(newCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) throws CategoryException {
        Category category = findCategoryById(categoryId);
        categoryRepository.delete(category);

    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findCategoryById(Long categoryId) throws CategoryException {
        // TODO Auto-generated method stub
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new CategoryException("Category not found with id " + categoryId));

        return category;
    }

}
