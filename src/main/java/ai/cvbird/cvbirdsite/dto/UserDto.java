package ai.cvbird.cvbirdsite.dto;

import ai.cvbird.cvbirdsite.validator.PasswordMatches;
import ai.cvbird.cvbirdsite.validator.UniqueEmail;
import ai.cvbird.cvbirdsite.validator.ValidEmail;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@PasswordMatches
@Builder
@ToString
public class UserDto {
    @UniqueEmail
    @ValidEmail(message="Please provide a valid email address")
    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private String password;
    private String confirmPassword;
}
