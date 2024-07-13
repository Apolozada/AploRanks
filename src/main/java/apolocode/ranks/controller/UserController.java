package apolocode.ranks.controller;

import apolocode.ranks.model.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class UserController {

    @Getter
    private final static List<User> users = new ArrayList<>();

    public static User findUserByName(String userName) {
        return users.stream().filter(user -> user.getUser().equalsIgnoreCase(userName)).findFirst().orElse(null);
    }
}