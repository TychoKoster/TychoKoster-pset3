package koster.kosterpset3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;



public class MovieInfo  extends AppCompatActivity {

    String title;
    String url;
    SharedPreferences sp;
    SharedPreferences sp_pos;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_info);
        Intent intent = this.getIntent();
        String movieInfo = intent.getStringExtra("Movie");
        /* Initializes the layout. */
        TextView title = (TextView) findViewById(R.id.movie_title);
        TextView year = (TextView) findViewById(R.id.year);
        TextView description = (TextView) findViewById(R.id.description);
        TextView director = (TextView) findViewById(R.id.director);
        TextView actors = (TextView) findViewById(R.id.Actors);
        ImageView poster = (ImageView) findViewById(R.id.poster);
        try {
            JSONObject movie = new JSONObject(movieInfo);
            /* Changes layout to movie info */
            String movietitle = movie.get("Title").toString();
            String yearnumber = movie.get("Released").toString();
            String plot = movie.get("Plot").toString();
            String actor = movie.get("Actors").toString();
            String direct = movie.get("Director").toString();
            String imageurl = movie.get("Poster").toString();
            new RetrievePoster(poster).execute(imageurl);
            title.setText(movietitle);
            year.setText(yearnumber);
            actors.setText(actor);
            description.setText(plot);
            director.setText(direct);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /* Turns the json string into an object and adds the poster url and title to the SharedPreferences
     * editor */
    public void addToWatchlist(View v) {
        Intent intent = getIntent();
        String movieTitle = intent.getStringExtra("Movie");
        try {
            JSONObject movie = new JSONObject(movieTitle);
            title = movie.get("Title").toString();
            url = movie.get("Poster").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /* Adds both title and poster to the SharedPreferences */
        sp = getApplicationContext().getSharedPreferences("Movies", 0);
        SharedPreferences.Editor editor = sp.edit().putString(title,title);
        editor.apply();
        sp_pos = getApplicationContext().getSharedPreferences("Movies_Pos", 0);
        SharedPreferences.Editor editor_pos = sp_pos.edit().putString(title, url);
        editor_pos.apply();
        Toast.makeText(this, "Movie added to watchlist!", Toast.LENGTH_SHORT).show();
    }
}
