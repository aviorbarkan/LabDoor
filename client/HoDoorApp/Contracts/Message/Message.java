package hodoorhaifa.HoDoorApp.Contracts.Message;

import java.io.Serializable;

public class Message implements Serializable {
    private String username;
    private String password;
    private Boolean isLogin;

    public Message(String username, String password, Boolean isLogin){
        this.username = username;
        this.password = password;
        this.isLogin = isLogin;
    }

    public Message(){

    }

    public void setIsLogin(Boolean login) {
        isLogin = login;
    }

    public Boolean getIsLogin() {
        return isLogin;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public void setUserName(String userName){
        this.username = userName;
    }

    public String getUserName(){
        return username;
    }
}
