package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mTvOrigin, mTvAlsoknown, mTvingredients, mTvDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        layoutPlaceOfOrigin(sandwich);

        layoutAlsoKnown(sandwich);
        layoutDescription(sandwich);
        layoutIngredients(sandwich);

    }

    private void layoutDescription(Sandwich sandwich) {
        if (sandwich.getDescription().isEmpty()) {
            mTvDescription.setText("Empty");

        } else {
            mTvDescription.setText(sandwich.getDescription());
        }

    }

    private void layoutPlaceOfOrigin(Sandwich sandwich) {
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            mTvOrigin.setText("Ëmpty");
        } else {
            mTvOrigin.setText(sandwich.getPlaceOfOrigin());
        }

    }

    private void layoutIngredients(Sandwich sandwich) {
        if (sandwich.getIngredients().isEmpty()) {
            mTvingredients.setText("Empty");
        } else {
            mTvingredients.setText(listToString(sandwich.getIngredients()));
        }
    }

    private void layoutAlsoKnown(Sandwich sandwich) {
        if (sandwich.getAlsoKnownAs().isEmpty()) {
            mTvAlsoknown.setText("Empty");
        } else {
            mTvAlsoknown.setText(listToString(sandwich.getAlsoKnownAs()));
        }

    }


    private void init() {
        mTvAlsoknown = findViewById(R.id.also_known_tv);
        mTvDescription = findViewById(R.id.description_tv);
        mTvingredients = findViewById(R.id.ingredients_tv);
        mTvOrigin = findViewById(R.id.origin_tv);


    }

    private StringBuilder listToString(List<String> stringList) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < stringList.size(); i++) {
            builder.append(stringList.get(i)).append("," + " ");

        }
        return builder;
    }
}
