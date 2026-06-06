package service;

import model.*;

public class AuthService {

    private UserService userService = new UserService();

    public AuthResponse login(String email, String password) {

        User user = userService.getUserByEmailAndPassword(email, password);

        if (user != null) {
            return new AuthResponse(
                    AuthStatus.SUCCESS,
                    user);
        }

        return new AuthResponse(
                AuthStatus.ACCOUNT_NOT_FOUND,
                null);
    }
}