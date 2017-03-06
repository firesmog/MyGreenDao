package javaDemo;

/**
 * Created by 20256473 on 2017/2/22.
 */

public class CommandOff implements Command {
    private Tv myTv;

    public CommandOff(Tv tv) {
        myTv = tv;
        }

    public void execute() {
          myTv.turnOff();
        }
}