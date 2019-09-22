package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView originTV;
    private TextView alsoKnownAsTV;
    private TextView ingredientsTV;
    private TextView descriptionTV;
    Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        ImageView ingredientsIv = findViewById(R.id.image_iv);
        originTV = (TextView) findViewById(R.id.origin_tv);
        alsoKnownAsTV = (TextView) findViewById(R.id.also_known_tv);
        ingredientsTV = (TextView) findViewById(R.id.ingredients_tv);
        descriptionTV = (TextView) findViewById(R.id.description_tv);

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
        sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /*في حالة عدم وجود معلومات في احد الخانات
    * من الافضل اظهار رسالة للمستخدم توضح عدم توفر المعلومة*/
    private void populateUI() {
        if (sandwich.getPlaceOfOrigin().isEmpty())
            originTV.setText(R.string.detail_origin_empty);
        else
            originTV.setText(sandwich.getPlaceOfOrigin());

        // استخدام ميثود TextUtils.join() لعرض محتويات الليسته بشكل مبسط
        if (sandwich.getAlsoKnownAs().isEmpty())
            alsoKnownAsTV.setText(R.string.detail_alsoknown_empty);
        else
            alsoKnownAsTV.setText(TextUtils.join(" - ",sandwich.getAlsoKnownAs()));

        // استخدام ميثود TextUtils.join() لعرض محتويات الليسته بشكل مبسط
        if (sandwich.getIngredients().isEmpty())
            ingredientsTV.setText(R.string.detail_ingredient_empty);
        else
            ingredientsTV.setText(TextUtils.join(" - ", sandwich.getIngredients()));

        if (sandwich.getDescription().isEmpty())
            descriptionTV.setText(R.string.detail_description_empty);
        else
            descriptionTV.setText(sandwich.getDescription());
    }
}
