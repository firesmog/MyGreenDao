package javaDemo;

/**
 * Created by 20256473 on 2017/2/22.
 */

public class CommandOn implements Command {
    private Tv myTv;

    public CommandOn(Tv tv) {
         myTv = tv;
        }

    public void execute() {
           myTv.turnOn();
        }
}
