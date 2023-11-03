package vn.sparkminds.services.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import vn.sparkminds.model.Address;

@Data
@AllArgsConstructor
public class UserResponse {
    private String email;
    private String password;
    private String userName;
    private Address address;
    private String phoneNumber;
}
