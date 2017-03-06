package javaDemo;

/**
 * Created by 20256473 on 2017/2/22.
 */

public class TestCommand {
    public static void main(String[] args) {

         Tv myTv = new Tv();
         // 开机命令ConcreteCommond
         CommandOn on = new CommandOn(myTv);
        // 关机命令ConcreteCommond
        CommandOff off = new CommandOff(myTv);
         // 频道切换命令ConcreteCommond
          CommandChange channel = new CommandChange(myTv, 2);
         // 命令控制对象Invoker
          Control control = new Control(on, off, channel);
         // 开机
         control.turnOn();
          // 切换频道
          control.changeChannel();
          // 关机
          control.turnOff();
        }
}
