package BTEC.ASM.project.common.utils;

import jakarta.servlet.http.HttpServletRequest;

public class IpUtils {

    public static String getClientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");

        if (xff != null && !xff.isBlank()) {
            return xff.split(",")[0].trim();
        }

        String realIp = request.getHeader("X-Real-IP");
        if (realIp != null && !realIp.isBlank()) {
            return realIp;
        }

        return request.getRemoteAddr();
    }
}
