package com.t3h.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.t3h.appbanhang.R;

public class LienHeActivity extends AppCompatActivity {

    private Toolbar toolbarLienhe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);
        toolbarLienhe = findViewById(R.id.toolbarLienhe);
        actionBar();
    }

    private void actionBar() {
        setSupportActionBar(toolbarLienhe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLienhe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
