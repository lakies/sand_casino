package Games;

public class Wheel extends GameInstance {
    @Override
    public void runGameLogic() {


    }
    public Wheel(){
        super(1, 1);
    }

    @Override
    public void cleanup() {

    }

    @Override
    public boolean ifFinished() {
        return false;
    }
}
