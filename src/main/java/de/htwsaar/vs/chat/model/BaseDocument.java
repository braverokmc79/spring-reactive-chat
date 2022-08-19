package de.htwsaar.vs.chat.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
* 다른 모든 문서 클래스에 대한 기본 MongoDB 문서.
 *
 * @author Arthur Kelsch
 */
@Data
public abstract class BaseDocument {

    @Id
    private String id;
}
