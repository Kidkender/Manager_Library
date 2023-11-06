package vn.sparkminds.services.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateCategoryRequest {
    private String name;
    private String description;
    private String imageUrl;
}
