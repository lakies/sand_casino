package protocol;

public abstract class MessageBody {
    private final MessageType type;

    protected MessageBody(MessageType type) {
        this.type = type;
    }

    public MessageType getType() {
        return type;
    }
}
