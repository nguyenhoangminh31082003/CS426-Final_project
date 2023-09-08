package com.example.cs426_final_project.fragments.access;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cs426_final_project.R;
import com.example.cs426_final_project.contracts.SignInContract;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class WelcomeFragment extends Fragment {

    public WelcomeFragment() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SignInContract contract;

        try {
            contract = (SignInContract) getActivity();
            if(contract == null)
                throw new ClassCastException();
        } catch (ClassCastException e) {
            throw new ClassCastException(requireActivity() + " must implement SignInContract");
        }

        MaterialButton mbSignIn = view.findViewById(R.id.mbSignIn);

        mbSignIn.setOnClickListener(v -> {
            contract.signIn();
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }
}