package model.persistence.serializer;


import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class Serializer<T> {

    public abstract ObjectNode serialize(T object);

    public abstract T deserialize(ObjectNode json);

    public T clone(T object) {
        return deserialize(serialize(object));
    }
}
