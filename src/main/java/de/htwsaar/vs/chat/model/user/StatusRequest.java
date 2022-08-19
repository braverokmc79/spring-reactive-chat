package de.htwsaar.vs.chat.model.user;

import de.htwsaar.vs.chat.model.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 
* 상태 개체 모델.
 *
 * @author Mahan Karimi
 * @see User
 */
@Data
public class StatusRequest {

    @NotBlank
    private String status;
}
