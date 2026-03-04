package BTEC.ASM.project.modules.identity.exception.refresh_tokens;

public class RefreshTokenRevokedException extends RuntimeException {
    public RefreshTokenRevokedException(String message) {
        super(message);
    }
}
