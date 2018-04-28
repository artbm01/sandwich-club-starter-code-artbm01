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
    private static final String DEFAULT_EMPTY_TEXT = "No data!";

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
        String alsoKnownAsString = listToString(sandwich.getAlsoKnownAs());
        String ingredientsString = listToString(sandwich.getIngredients());

        TextView alsoKnownAsIv = findViewById(R.id.also_known_tv);
        TextView placeOfOriginIv = findViewById(R.id.origin_tv);
        TextView ingredientsIv = findViewById(R.id.ingredients_tv);
        TextView descriptionIv = findViewById(R.id.description_tv);

        alsoKnownAsIv.setText(checkForEmptyString(alsoKnownAsString));
        placeOfOriginIv.setText(checkForEmptyString(sandwich.getPlaceOfOrigin()));
        ingredientsIv.setText(checkForEmptyString(ingredientsString));
        descriptionIv.setText(checkForEmptyString(sandwich.getDescription()));

    }

    private String listToString (List<String> myList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < myList.size(); i++) {
            stringBuilder.append(myList.get(i));
            if ( i != myList.size()-1) {
                stringBuilder.append(", ");
            }
            else stringBuilder.append(".");

        }
        return stringBuilder.toString();
    }

    private String checkForEmptyString (String myString){
        if (myString.equals("")){
            myString = DEFAULT_EMPTY_TEXT;
        }
        return myString;
    }
}