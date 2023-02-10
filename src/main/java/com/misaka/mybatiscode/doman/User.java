package com.misaka.mybatiscode.doman;

import java.io.Serializable;

public class User implements Serializable {
  private   int users_id;
  private   String user_mobile;
  private   String user_password;
  private   int usergroup_id;

    public int getUsers_id() {
        return users_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "users_id=" + users_id +
                ", user_mobile='" + user_mobile + '\'' +
                ", user_password='" + user_password + '\'' +
                ", usergroup_id=" + usergroup_id +
                '}';
    }

    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public int getUsergroup_id() {
        return usergroup_id;
    }

    public void setUsergroup_id(int usergroup_id) {
        this.usergroup_id = usergroup_id;
    }
}
