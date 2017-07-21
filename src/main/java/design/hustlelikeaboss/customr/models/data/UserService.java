package design.hustlelikeaboss.customr.models.data;

import design.hustlelikeaboss.customr.models.User;

/**
 * Created by quanjin on 7/20/17.
 */
public interface UserService {
    User findUserByEmail(String email);
    void saveUser(User user);
    User retrieveUser();
}
