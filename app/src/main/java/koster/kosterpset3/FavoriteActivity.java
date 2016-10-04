package koster.kosterpset3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectedListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class FavoriteActivity extends AppCompatActivity {
    BottomBar bottomBar;
    SharedPreferences sp;
    SharedPreferences sp_pos;
    ListView movieList;

    ArrayList<String> watchlist = new ArrayList<String>();
    ArrayList<String> watchlistposters = new ArrayList<String>();
    WatchlistAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.watchlist);
        setBottomBar(savedInstanceState, 1);
        retrieveMovie();
        showList();
    }

    /* Selects the right bottombar tab and creates the watchlist again on restart */
    @Override
    protected void onRestart()
    {
        super.onRestart();
        retrieveMovie();
        showList();
        if(bottomBar != null)
        {
            bottomBar.selectTabAtPosition(1, true);
        }
    }

    private void retrieveMovie() {
        watchlist.clear();
        watchlistposters.clear();
        sp = getSharedPreferences("Movies", 0);
        Map<String, ?> movies = sp.getAll();
        for(Map.Entry<String,?> entry : movies.entrySet()){
            watchlist.add(entry.getValue().toString());
        }
        sp_pos = getSharedPreferences("Movies_Pos", 0);
        Map<String, ?> posters = sp_pos.getAll();
        for (Map.Entry<String, ?> entry : posters.entrySet()) {
            watchlistposters.add(entry.getValue().toString());
        }
    }

    /* Shows the watchlist by creating an adapter and set that adapter to the watchlist. */
    private void showList() {
        movieList = (ListView) findViewById(R.id.itemList);
        adapter = new WatchlistAdapter(watchlist, watchlistposters, this);
        movieList.setAdapter(adapter);
        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = movieList.getItemAtPosition(position).toString();
                /* Replaces spaces with "+" for the url  */
                title = title.replaceAll("\\s+", "+");
                RetrieveMovie act = new RetrieveMovie();
                Intent intent = new Intent(getApplicationContext(), MovieInfo.class);
                try {
                    String movie = act.execute(title).get();
                    intent.putExtra("Movie", movie);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /* Removes an item from the watchlist if clicked on the checkbox */
    public void remove(Context c, String title) {
        sp = getSharedPreferences("Movies", 0);
        sp_pos = getSharedPreferences("Movies_Pos", 0);
        SharedPreferences.Editor editor = sp.edit();
        SharedPreferences.Editor editor_pos = sp_pos.edit();
        editor.remove(title);
        editor_pos.remove(title);
        editor.apply();
        editor_pos.apply();
        Toast.makeText(this, "Movie removed!", Toast.LENGTH_SHORT).show();
        retrieveMovie();
        showList();
    }

    /* Sets the Bottombar for the watchlist activity */
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

    /* Saves the Bottombar */
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
