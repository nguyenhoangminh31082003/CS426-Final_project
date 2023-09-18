package com.example.cs426_final_project.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cs426_final_project.R;
import com.example.cs426_final_project.api.UsersApi;
import com.example.cs426_final_project.fragments.decoration.WaitingFragment;
import com.example.cs426_final_project.models.data.LoginDataModel;
import com.example.cs426_final_project.models.response.ProfileResponse;
import com.example.cs426_final_project.models.response.StatusResponse;
import com.example.cs426_final_project.utilities.ApiUtilityClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WaitingRequestAuthorityActivity extends AppCompatActivity {

    private String email;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_waiting_request_authority);

        FragmentContainerView fcvWaitingScreenAuthority = findViewById(R.id.fcvWaitingScreenAuthority);
        getSupportFragmentManager()
                .beginTransaction()
                .add(fcvWaitingScreenAuthority.getId(), new WaitingFragment())
                .commit();

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        this.requestAuthority();
    }

    private void callLoginApi(){
        UsersApi usersApi = ApiUtilityClass
                .Companion
                .getApiClient(this)
                .create(UsersApi.class);

        Call<StatusResponse> call = usersApi.userLogin(new LoginDataModel(email, password));
        call.enqueue( new Callback<StatusResponse>() {
            @Override
            public void onResponse(
                    @NonNull Call<StatusResponse> call,
                    @NonNull Response<StatusResponse> response) {
                if (response.isSuccessful()) {
                    requestAuthority();
                } else{
                    Toast.makeText(WaitingRequestAuthorityActivity.this, "Fail to login", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            @Override
            public void onFailure(
                    @NonNull Call<StatusResponse> call,
                    @NonNull Throwable t
            ) {
//                call.enqueue(this);
                Toast.makeText(WaitingRequestAuthorityActivity.this, "There is an error in the connection", Toast.LENGTH_SHORT).show();
                finish();
            }

        });
    }

    private void requestAuthority() {
        UsersApi usersApi = ApiUtilityClass
                .Companion
                .getApiClient(this)
                .create(UsersApi.class);

        Call<ProfileResponse> call = usersApi.getLoggedProfile();
        call.enqueue( new Callback<ProfileResponse>() {
            @Override
            public void onResponse(
                    @NonNull Call<ProfileResponse> call,
                    @NonNull Response<ProfileResponse> response) {
                if (response.isSuccessful()) {
                    // set result ok
                    setResult(RESULT_OK);
                    finish();
                } else{
                    callLoginApi();
                }

            }

            @Override
            public void onFailure(
                    @NonNull Call<ProfileResponse> call,
                    @NonNull Throwable t
            ) {
                Toast.makeText(WaitingRequestAuthorityActivity.this, "There is an error in the connection", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}