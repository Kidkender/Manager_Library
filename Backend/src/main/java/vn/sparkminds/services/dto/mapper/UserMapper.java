package vn.sparkminds.services.dto.mapper;

import java.util.Objects;
import vn.sparkminds.model.User;
import vn.sparkminds.services.dto.response.UserResponse;

public class UserMapper {
    public UserResponse toUserResponse(User user) {
        if (Objects.isNull(user) == true) {
            return null;
        }
        return new UserResponse(user.getEmail(), user.getPassword(), user.getName(),
                user.getAddress());
    }
}
