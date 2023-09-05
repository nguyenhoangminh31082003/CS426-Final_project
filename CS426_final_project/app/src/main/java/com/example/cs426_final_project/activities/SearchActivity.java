package com.example.cs426_final_project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cs426_final_project.MutableTypeOptionsData;
import com.example.cs426_final_project.R;
import com.example.cs426_final_project.TypeOptionsStatus;

public class SearchActivity extends AppCompatActivity {

    TypeOptionsStatus typeOptionsStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_search);
        this.setTypeOptionsStatus();
        this.setSearchView();

        initBackButton();

        initSearchView();
    }

    private void initSearchView() {
        androidx.appcompat.widget.SearchView sevSearch = this.findViewById(R.id.sevSearch);
        sevSearch.setQueryHint("Type here to search");
//        sevSearch.setIconified(false);
//        sevSearch.setOnTouchListener((v, event) -> {
//            // Open the SearchView when touched anywhere on it
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                sevSearch.setIconified(false); // Expand the SearchView
//                return true;
//            }
//            return false;
//        });
        sevSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    private void initBackButton() {
        AppCompatImageView btnSearchBack = this.findViewById(R.id.acivSearchBack);
        btnSearchBack.setOnClickListener(view -> finish());
    }

    private void setSearchView() {
        androidx.appcompat.widget.SearchView searchView = this.findViewById(R.id.sevSearch);
        EditText editText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        ImageView closeButton = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        ImageView searchMagIcon = searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
        //ImageView openButton = searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
        //ImageView openButton = searchView.findViewById(androidx.appcompat.R.id.search_go_btn);
        ImageView openButton = searchView.findViewById(androidx.appcompat.R.id.search_button);
        openButton.setImageResource(R.drawable.search_page_search_icon);
        searchMagIcon.setImageResource(R.drawable.search_page_search_icon);
        closeButton.setImageResource(R.drawable.search_page_close_icon);
        editText.setHintTextColor(ContextCompat.getColor(this, R.color.white));
        searchView.setQueryHint("Type here to search");
    }

    private void setTypeOptionsStatus() {
        com.nex3z.flowlayout.FlowLayout flowLayout = this.findViewById(R.id.fllSearchOption);
        this.typeOptionsStatus = new TypeOptionsStatus((new MutableTypeOptionsData()).toList());

        final int numberOfOptions = this.typeOptionsStatus.getSize();

        for (int i = 0; i < numberOfOptions; ++i) {
            final int immutable_i = i;
            Button button = new Button(this);

            button.setLayoutParams(
                    new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
            );
            button.setPadding(4, 0, 4, 0);
            button.setText(this.typeOptionsStatus.getTypeOption(i));
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            button.setTextColor(ContextCompat.getColor(this, R.color.white));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    typeOptionsStatus.toggle(immutable_i);
                    if (typeOptionsStatus.checkChosen(immutable_i))
                        button.setBackgroundResource(R.drawable.search_page_chosen_option_customization);
                    else
                        button.setBackgroundResource(R.drawable.search_page_unchosen_option_customization);
                }
            });
            button.setAllCaps(false);
            button.setBackgroundResource(R.drawable.search_page_unchosen_option_customization);

            flowLayout.addView(button);
        }
    }
}