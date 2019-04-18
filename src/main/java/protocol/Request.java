package protocol;

public abstract class Request extends MessageBody {
    public Request(MessageType type) {
        super(type);
    }
}
