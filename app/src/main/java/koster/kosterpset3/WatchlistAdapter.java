package koster.kosterpset3;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WatchlistAdapter extends BaseAdapter {
    private ArrayList<String> thewatchlist;
    private ArrayList<String> posters;
    Context c;
    public WatchlistAdapter(ArrayList<String> watchlist, ArrayList<String> poster, Context context) {
        thewatchlist = watchlist;
        posters = poster;
        this.c = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return thewatchlist.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return thewatchlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    /* Implements the adapter when called upon. Sets text to movie title and sets poster to movie image
     * also sets onclick on the checkbox, which will remove an item */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        LayoutInflater inflater = (LayoutInflater) c
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
            view =  inflater.inflate(R.layout.item_list, parent,
                    false);
        }
        else {
            view = convertView;
        }
        final String title = thewatchlist.get(position);
        TextView item = (TextView) view.findViewById(R.id.textitem);
        item.setText(title);
        ImageView poster = (ImageView) view.findViewById(R.id.poster);
        String url = posters.get(position);
        new RetrievePoster(poster).execute(url);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
        final FavoriteActivity activity = (FavoriteActivity) c;
        checkBox.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                activity.remove(c, title);
            }
        });
        return view;
    }
}