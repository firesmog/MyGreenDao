package example.com.myapplication.Bean;

/**
 * Created by 20256473 on 2017/2/23.
 */

public class Contactor {
    private  String name;

    public Contactor(String name, String message) {
        this.name = name;
        this.message = message;
    }

    private String message;
    public  Contactor(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
