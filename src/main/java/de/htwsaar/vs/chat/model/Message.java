package de.htwsaar.vs.chat.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.htwsaar.vs.chat.model.serializer.DocumentIdSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
* 메시지 객체 모델(MongoDB 문서).
 *
 * @author Niklas Reinhard
 * @author Julian Quint
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Document(collection = "messages")
public class Message extends BaseDocument {

    @CreatedDate
    private Instant createdDate;

    @NotNull
    @JsonSerialize(using = DocumentIdSerializer.class)
    @DBRef
    private Chat chat;

    @NotNull
    @JsonSerialize(using = DocumentIdSerializer.class)
    @DBRef
    private User sender;

    @NotBlank
    private String content;
}
