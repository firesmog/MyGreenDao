package javaDemo;

/**
 * Created by 20256473 on 2017/2/22.
 */

public class Control {
    private Command onCommand, offCommand, changeChannel;

    public Control(Command on, Command off, Command channel) {
         onCommand = on;
         offCommand = off;
           changeChannel = channel;
        }

    public void turnOn() {
           onCommand.execute();
        }

    public void turnOff() {
           offCommand.execute();
        }

    public void changeChannel() {
         changeChannel.execute();
        }
}
