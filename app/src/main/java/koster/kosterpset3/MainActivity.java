package koster.kosterpset3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectedListener;


public class MainActivity extends AppCompatActivity
{
    BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBottomBar(savedInstanceState, 0);
    }

    /* Sets correct bottombar tab on reset */
    @Override
    protected void onRestart()
    {
        super.onRestart();
        if(bottomBar != null)
        {
            bottomBar.selectTabAtPosition(0, true);
        }
    }

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

    /* Saves bottombar tab */
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