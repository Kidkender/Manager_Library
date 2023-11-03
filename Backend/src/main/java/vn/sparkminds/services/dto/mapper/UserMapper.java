package vn.sparkminds.services.dto.mapper;

import java.util.Objects;
import org.springframework.stereotype.Component;
import vn.sparkminds.model.User;
import vn.sparkminds.services.dto.response.UserResponse;

@Component
public class UserMapper {
    public UserResponse toUserResponse(User user) {
        if (Objects.isNull(user) == true) {
            return null;
        }
        return new UserResponse(user.getEmail(), user.getPassword(), user.getUserName(),
                user.getAddress(), user.getPhone());
    }
}
