package BTEC.ASM.project.modules.identity.exception.refresh_tokens;

public class RefreshTokenNotFoundException extends RuntimeException {
    public RefreshTokenNotFoundException(String message) {
        super(message);
    }
}
