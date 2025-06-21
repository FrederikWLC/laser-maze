package model.persistence.serializer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.domain.token.base.Token;

public interface ISerializer<T> {

    ObjectNode serialize(T object);

    T deserialize(ObjectNode json);

}
