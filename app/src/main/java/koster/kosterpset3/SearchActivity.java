package koster.kosterpset3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectedListener;

import java.util.concurrent.ExecutionException;


public class SearchActivity extends AppCompatActivity {
    BottomBar bottomBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        setBottomBar(savedInstanceState, 2);
    }

    /* Sets correct bottombar tab on restart */
    @Override
    protected void onRestart()
    {
        super.onRestart();
        if(bottomBar != null)
        {
            bottomBar.selectTabAtPosition(0, true);
        }
    }

    /* Searches for a movie by using the title inserted in the Edittext. It then retrieves it from
    * the API. */
    public void searchMovie(View v) {
        EditText movieTitle = (EditText) findViewById(R.id.movieTitle);
        RetrieveMovie activity = new RetrieveMovie();
        String title = movieTitle.getText().toString();
        title = title.replaceAll("\\s+", "+");
        movieTitle.setText("");
        try {
            String movie = activity.execute(title).get();
            Intent intent = new Intent(this, MovieInfo.class);
            intent.putExtra("Movie", movie);
            startActivity(intent);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    /* Sets the Bottombar for the search activity */
    private void setBottomBar(Bundle savedInstanceState, int defaultPosition)
    {
        bottomBar = BottomBar.attach(this, savedInstanceState);

        bottomBar.setItems(
                new BottomBarTab(R.drawable.ic_home_black_24dp, "Home"),
                new BottomBarTab(R.drawable.ic_favorite_black_24dp, "Watch List"),
                new BottomBarTab(R.drawable.ic_search_black_24dp, "Search")
        );
        bottomBar.selectTabAtPosition(defaultPosition, false);

        bottomBar.setOnItemSelectedListener(new OnTabSelectedListener()
        {
            @Override
            public void onItemSelected(int position)
            {
                Intent intent;
                switch(position)
                {
                    case 0:
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(getApplicationContext(), FavoriteActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(getApplicationContext(), SearchActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    /* Saves bottombar */
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        if (bottomBar != null)
        {
            bottomBar.onSaveInstanceState(outState);
        }
    }
}
