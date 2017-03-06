package javaDemo;

/**
 * Created by 20256473 on 2017/2/22.
 */

public class CommandChange implements Command {
    private Tv myTv;

    private int channel;

    public CommandChange(Tv tv, int channel) {
          myTv = tv;
         this.channel = channel;
        }

    public void execute() {
          myTv.changeChannel(channel);
    }
}
