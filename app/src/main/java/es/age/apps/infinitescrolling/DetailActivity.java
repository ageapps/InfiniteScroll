package es.age.apps.infinitescrolling;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * Created by Adrián García Espinosa on 25/3/16.
 */
public class DetailActivity extends AppCompatActivity {


    public static String EXTRA_NAME =  "name";
    public static String EXTRA_LOCATION =  "locartion";
    public static String EXTRA_DESCRIPTION =  "description";
    public static String EXTRA_IMAGE =  "image";

    private TextView title,location,description;
    private ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = (TextView) findViewById(R.id.title);
        location = (TextView) findViewById(R.id.location);
        description = (TextView) findViewById(R.id.description);
        image = (ImageView) findViewById(R.id.image);

        Intent i = getIntent();

        getSupportActionBar().setTitle(i.getStringExtra(EXTRA_NAME));
        title.setText(i.getStringExtra(EXTRA_NAME));
        location.setText(i.getStringExtra(EXTRA_LOCATION));
        description.setText(i.getStringExtra(EXTRA_DESCRIPTION));
        Glide.with(this)
                .load(i.getIntExtra(EXTRA_IMAGE,R.drawable.madrid))
                .centerCrop()
                .into(image);


    }

}
