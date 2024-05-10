package ai.cvbird.cvbirdsite.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Component
public class CVBirdAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final String USER_IS_DISABLED = "User is disabled";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, Object> data = new HashMap<>();
        String message = exception.getMessage();
        if (USER_IS_DISABLED.equals(message)) {
            message = message + ". The token have been sent to your email. Please, confirm your email.";
        }
        data.put(
                "timestamp",
                Calendar.getInstance().getTime());
        data.put(
                "exception",
                message);

        response.getOutputStream()
                .println(objectMapper.writeValueAsString(data));
    }
}
