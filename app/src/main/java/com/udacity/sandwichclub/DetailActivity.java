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

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
/*        StringBuilder namesBuilder = new StringBuilder();
        for (String names : sandwich.getAlsoKnownAs()) {
            namesBuilder.append(names + "\n");
        }*/

        StringBuilder namesBuilder = new StringBuilder();
        for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
            namesBuilder.append(sandwich.getAlsoKnownAs().get(i));
            if ( i != sandwich.getAlsoKnownAs().size()-1) {
                namesBuilder.append(", ");
            }
        }

        StringBuilder ingredientsBuilder = new StringBuilder();
        for (int i = 0; i < sandwich.getIngredients().size(); i++) {
            ingredientsBuilder.append(sandwich.getIngredients().get(i));
            if ( i != sandwich.getIngredients().size()-1) {
                ingredientsBuilder.append(", ");
            }
        }
        TextView alsoKnownAsIv = findViewById(R.id.also_known_tv);
        TextView placeOfOriginIv = findViewById(R.id.origin_tv);
        TextView ingredientsIv = findViewById(R.id.ingredients_tv);
        TextView descriptionIv = findViewById(R.id.description_tv);


        alsoKnownAsIv.setText(namesBuilder.toString());
        placeOfOriginIv.setText(sandwich.getPlaceOfOrigin());
        ingredientsIv.setText(ingredientsBuilder.toString());
        descriptionIv.setText(sandwich.getDescription());

    }
}
