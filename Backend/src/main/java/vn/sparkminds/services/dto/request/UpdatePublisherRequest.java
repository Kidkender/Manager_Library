package vn.sparkminds.services.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdatePublisherRequest {
    private String email;
    private String phone;
}
