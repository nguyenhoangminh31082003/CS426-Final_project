package com.example.cs426_final_project.models.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SuggestionFields {

    @SerializedName("password")
    private String password = "";
    @SerializedName("last_login")
    private String lastLogin;
    @SerializedName("is_superuser")
    private boolean isSuperuser;
    @SerializedName("username")
    private String username;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("email")
    private String email;
    @SerializedName("is_staff")
    private boolean isStaff;
    @SerializedName("is_active")
    private boolean isActive;
    @SerializedName("date_join")
    private String dateJoin;
    @SerializedName("phone_number")
    private String phoneNumber;
    @SerializedName("profile")
    private int profile;
    @SerializedName("groups")
    private List<String> groups;
    @SerializedName("user_permissions")
    private List<String> userPermissions;

    public String getUSername() {
        return this.username;
    }

}
