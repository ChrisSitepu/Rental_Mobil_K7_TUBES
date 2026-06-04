package model;

public class AuthResponse {

    private AuthStatus status;
    private User user;

    public AuthResponse(AuthStatus status, User user) {
        this.status = status;
        this.user = user;
    }

    public AuthStatus getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }
}