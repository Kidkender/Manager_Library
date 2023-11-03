package vn.sparkminds.services.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddCategoryRequest {
    private String name;
    private String description;
    private int total;
}
