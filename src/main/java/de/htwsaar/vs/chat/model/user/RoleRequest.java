package de.htwsaar.vs.chat.model.user;

import de.htwsaar.vs.chat.model.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
* 역할 개체 모델.
 *
 * @author Mahan Karimi
 * @see User
 */
@Data
public class RoleRequest {

    @NotBlank
    @Pattern(regexp = "ROLE_\\w+")
    private String role;
}
