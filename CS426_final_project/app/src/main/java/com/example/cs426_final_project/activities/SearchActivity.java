package com.example.cs426_final_project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;

import com.example.cs426_final_project.MutableTypeOptionsData;
import com.example.cs426_final_project.R;
import com.example.cs426_final_project.TypeOptionsStatus;
import com.google.android.material.internal.FlowLayout;

public class SearchActivity extends AppCompatActivity {

    TypeOptionsStatus typeOptionsStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_search);
        this.setTypeOptionsStatus();
    }

    public void setTypeOptionsStatus() {
        FlowLayout flowLayout = this.findViewById(R.id.flow_list_of_type_options);
        this.typeOptionsStatus = new TypeOptionsStatus((new MutableTypeOptionsData()).toList());

        final int numberOfOptions = this.typeOptionsStatus.getSize();

        for (int i = 0; i < numberOfOptions; ++i) {
            final int immutable_i = i;
            Button button = new Button(this);

            button.setText(this.typeOptionsStatus.getTypeOption(i));
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            button.setTextColor(ContextCompat.getColor(this, R.color.white));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    typeOptionsStatus.toggle(immutable_i);
                }
            });

            flowLayout.addView(button);
        }
    }
}