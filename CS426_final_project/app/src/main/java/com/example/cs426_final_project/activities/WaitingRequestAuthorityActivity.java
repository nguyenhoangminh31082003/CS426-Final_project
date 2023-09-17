package com.example.cs426_final_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;

import com.example.cs426_final_project.R;
import com.example.cs426_final_project.api.FeedApi;
import com.example.cs426_final_project.api.UsersApi;
import com.example.cs426_final_project.models.response.ProfileResponse;
import com.example.cs426_final_project.utilities.api.ApiUtilityClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WaitingRequestAuthorityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_waiting_request_authority);
        this.requestAuthority();
    }
    
    private void requestAuthority() {
        UsersApi usersApi = ApiUtilityClass
                .Companion
                .getApiClient(this)
                .create(UsersApi.class);

        Call<ProfileResponse> call = usersApi.getLoggedProfile();

        Callback<ProfileResponse> callback = new Callback<ProfileResponse>() {
            @Override
            public void onResponse(
                    Call<ProfileResponse> call,
                    Response<ProfileResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200)
                        finish();
                    else
                        call.enqueue(this);
                } else
                    call.enqueue(this);
            }

            @Override
            public void onFailure(
                    Call<ProfileResponse> call,
                    Throwable t
            ) {
                call.enqueue(this);
            }
        };

        call.enqueue(callback);
    }
}