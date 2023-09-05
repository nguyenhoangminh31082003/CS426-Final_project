package com.example.cs426_final_project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.TypedValue;
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

        flowLayout.setChildSpacing(40);

        this.typeOptionsStatus = new TypeOptionsStatus((new MutableTypeOptionsData()).toList());

        final int numberOfOptions = this.typeOptionsStatus.getSize();

        for (int i = 0; i < numberOfOptions; ++i) {
            final int immutable_i = i;
            Button btnOption = new Button(this);

            btnOption.setLayoutParams(
                    new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
            );

            String text = this.typeOptionsStatus.getTypeOption(i);
            btnOption.setText(text);
            // strictly wrap content
            btnOption.setMinWidth(40);

            btnOption.setMinimumWidth(40);
            btnOption.setMaxWidth(Integer.MAX_VALUE);


            if(text.length() >= 16){
                btnOption.setPadding(0, 20, 0, 20);
                btnOption.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            } else {
                btnOption.setPadding(25, 20, 25, 20);
                btnOption.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            }



            btnOption.setTextColor(ContextCompat.getColor(this, R.color.white));

            btnOption.setOnClickListener(view -> {
                typeOptionsStatus.toggle(immutable_i);
                if (typeOptionsStatus.checkChosen(immutable_i))
                    btnOption.setBackgroundResource(R.drawable.search_page_chosen_option_customization);
                else
                    btnOption.setBackgroundResource(R.drawable.search_page_unchosen_option_customization);
            });
            btnOption.setAllCaps(false);
            btnOption.setBackgroundResource(R.drawable.search_page_unchosen_option_customization);

            flowLayout.addView(btnOption);
        }
    }
}