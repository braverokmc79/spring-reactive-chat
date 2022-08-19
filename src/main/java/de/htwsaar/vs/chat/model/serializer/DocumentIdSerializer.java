package de.htwsaar.vs.chat.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import de.htwsaar.vs.chat.model.BaseDocument;

import java.io.IOException;

/**
* {@link BaseDocument}를 확장하는 필드에 사용되는 Jackson 직렬 변환기.
 * <p>
 * 주어진 문서의 id를 JSON에 씁니다.
 *
 * @author Arthur Kelsch
 */
public class DocumentIdSerializer extends StdSerializer<BaseDocument> {

    public DocumentIdSerializer() {
        super(BaseDocument.class);
    }

    @Override
    public void serialize(BaseDocument value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.getId());
    }
}
