package model.persistence.serializer;


import com.fasterxml.jackson.databind.node.ObjectNode;

public interface ISerializer<T> {

    ObjectNode serialize(T object);

    T deserialize(ObjectNode json);

}
