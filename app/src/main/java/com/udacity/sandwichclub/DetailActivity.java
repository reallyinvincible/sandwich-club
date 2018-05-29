package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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

        TextView sandwichNameTextView = findViewById(R.id.sandwich_name_tv);
        sandwichNameTextView.setText(sandwich.getMainName());

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(DetailActivity.this);
            }
        });
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        //Adding information to the Detail UI
        TextView alsoKnownAsTV = findViewById(R.id.also_known_tv);
        if (sandwich.getAlsoKnownAs() == null) {
            //If sandwich doesn't have an alternate name, hide the "Also Known As" view
            alsoKnownAsTV.setVisibility(View.GONE);
            findViewById(R.id.also_known_title).setVisibility(View.GONE);
        } else {
            String alsoKnownAsText = sandwich.getAlsoKnownAs().toString();
            alsoKnownAsTV.setText(alsoKnownAsText.substring(1, alsoKnownAsText.length()-1));
        }

        TextView descriptionTV = findViewById(R.id.description_tv);
        descriptionTV.setText(sandwich.getDescription());

        TextView originTV = findViewById(R.id.origin_tv);
        if (sandwich.getPlaceOfOrigin().equals("")){
            //If sandwich doesn't have an origin, hide the "Origin" view
            originTV.setVisibility(View.GONE);
            findViewById(R.id.origin_title).setVisibility(View.GONE);
        } else {
            originTV.setText(sandwich.getPlaceOfOrigin());
        }

        TextView ingredientsTV = findViewById(R.id.ingredients_tv);
        String ingredientText = sandwich.getIngredients().toString();
        ingredientsTV.setText(ingredientText.substring(1, ingredientText.length()-1));
    }
}
