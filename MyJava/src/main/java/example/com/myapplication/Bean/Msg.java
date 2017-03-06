package example.com.myapplication.Bean;

/**
 * Created by 20256473 on 2017/3/3.
 */

public class Msg
{
    public static final int TYPE_RECEIVE = 0;
    public static final int TYPE_SEND = 1;
    private CharSequence content;
    private int type;

    public String getUser_id() {
        return user_id;
    }

    private String user_id;

    public Msg(CharSequence content, int type)
    {
        this.content = content;
        this.type = type;

    }

    public CharSequence getContent()
    {
        return content;
    }

    public int getType()
    {
        return type;
    }

}
