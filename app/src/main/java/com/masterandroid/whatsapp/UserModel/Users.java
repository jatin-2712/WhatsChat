package com.masterandroid.whatsapp.UserModel;

public class Users {

    String profilepic ,emailId,password,userName,userNamee,about,status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Users(String profilepic, String emailId, String password, String userName, String userNamee, String about)
    {
        this.profilepic=profilepic;
        this.emailId=emailId;
        this.password=password;
        this.userName=userName;
        this.userNamee=userNamee;
        this.about=about;

    }

    public String getUserNamee() {
        return userNamee;
    }

    public void setUserNamee(String userNamee) {
        this.userNamee = userNamee;
    }

    public Users()
    {

    }
    //SignUp Constructor
    public Users(String userName,String emailId, String password) {

        this.emailId = emailId;
        this.password = password;
        this.userName = userName;

    }


    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
