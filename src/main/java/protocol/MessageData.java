package protocol;

public class MessageData<T extends MessageBody> {

    private String type;
    private T data;

    private MessageData() {
    }

    public String getType() {
        return type;
    }

    public T getData() {
        return data;
    }

    public static<T extends MessageBody> MessageData<T> create(String type, T data) {
        MessageData<T> command = new MessageData<T>();
        command.type = type;
        command.data = data;
        return command;
    }

}
