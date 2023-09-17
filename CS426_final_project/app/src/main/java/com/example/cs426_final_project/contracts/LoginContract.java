package com.example.cs426_final_project.contracts;

import androidx.compose.runtime.MutableState;

public interface LoginContract {
    void login();
    void returnToWelcome();
    void onConfirm(String username, String password);
    MutableState<String> getEmail();
}
