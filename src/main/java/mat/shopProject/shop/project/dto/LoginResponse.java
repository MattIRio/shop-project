package mat.shopProject.shop.project.dto;

public record LoginResponse(String token, String type, long expiresIn) {

    public LoginResponse(String token) {
        this(token, "Bearer", 3600L);
    }
}
