package BTEC.ASM.project.modules.identity.exception.refresh_tokens;

public class RefreshTokenExpiredException extends RuntimeException {
    public RefreshTokenExpiredException(String message) {
        super(message);
    }
}
