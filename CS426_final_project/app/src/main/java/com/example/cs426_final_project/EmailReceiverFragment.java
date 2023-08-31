package com.example.cs426_final_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmailReceiverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmailReceiverFragment extends Fragment {

    public EmailReceiverFragment() {
    }

    public static EmailReceiverFragment newInstance() {
        EmailReceiverFragment fragment = new EmailReceiverFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_email_receiver, container, false);
    }
}