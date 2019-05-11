package protocol;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

public class ClassConverter {

    private static final Gson gsonEngine = new Gson();

    private ClassConverter() {}

    public static <T extends MessageBody> String encode(T payload) {
        MessageData<T> command = MessageData.create(payload.getClass().getName(), payload);
        return gsonEngine.toJson(command);
    }

    @SuppressWarnings("unchecked")
    public static <T extends MessageBody> T decode(String rawJson, Class<T> expectedType) throws ClassNotFoundException {
        LinkedTreeMap<?,?> asMap = gsonEngine.fromJson(rawJson, LinkedTreeMap.class);
        String className = asMap.get("type").toString();
        if (!expectedType.isAssignableFrom(Class.forName(className))){
            throw new ClassNotFoundException("Illegal class");
        }
        Class<? extends MessageBody> messageClass;
        messageClass = (Class<? extends MessageBody>) Class.forName(className);
        JsonObject jsonObject = gsonEngine.toJsonTree(asMap.get("data")).getAsJsonObject();
        return (T) gsonEngine.fromJson(jsonObject, messageClass);
    }

}