package vn.sparkminds.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.sparkminds.exceptions.CategoryException;
import vn.sparkminds.model.Category;
import vn.sparkminds.repositories.CategoryRepository;
import vn.sparkminds.services.CategoryService;
import vn.sparkminds.services.dto.request.AddCategoryRequest;
import vn.sparkminds.services.dto.request.UpdateCategoryRequest;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(AddCategoryRequest category) {
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        newCategory.setDescription(category.getDescription());
        newCategory.setImageUrl(category.getImageUrl());
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
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new CategoryException("Category not found with id " + categoryId));

        return category;
    }

    @Override
    @Transactional
    public Category updateCategory(Long categoryId, UpdateCategoryRequest req)
            throws CategoryException {
        Category category = findCategoryById(categoryId);
        if (Objects.isNull(req)) {
            throw new CategoryException("Data of category invalid...");
        }
        if (req.getName() != null) {
            category.setName(req.getName());
        }

        if (req.getDescription() != null) {
            category.setDescription(req.getDescription());
        }
        if (req.getImageUrl() != null) {
            category.setImageUrl(req.getImageUrl());
        }

        category.setUpdatedAt(LocalDateTime.now());
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> createMultipleCategories(AddCategoryRequest[] cateReq) {
        List<Category> categories = new ArrayList<Category>();
        for (AddCategoryRequest cate : cateReq) {
            Category createCate = createCategory(cate);
            categories.add(createCate);
        }
        return categories;
    }

}
