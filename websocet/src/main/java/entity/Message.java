package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Message {
    //发送者
    String from;
    //发送者名称
    String fromName;
    //接受者
    String to;
    //发送文本
    String Text;

    //发送时间
    Date  time;

    //在线用户列表
    List<User>  userList = new ArrayList<>();

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
