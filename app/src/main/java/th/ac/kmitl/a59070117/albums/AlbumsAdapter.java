package th.ac.kmitl.a59070117.albums;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import th.ac.kmitl.a59070117.R;

public class AlbumsAdapter extends ArrayAdapter {
    ArrayList<JSONObject> albumsList;
    Context context;

    public AlbumsAdapter(Context context, int resource, ArrayList<JSONObject> objects) {
        super(context, resource, objects);
        this.albumsList = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View albumsItem = LayoutInflater.from(context).inflate(R.layout.fragment_albums_item, parent, false);
        TextView title = (TextView) albumsItem.findViewById(R.id.albums_item_title);
        TextView id = (TextView) albumsItem.findViewById(R.id.albums_item_id);
        JSONObject object = albumsList.get(position);
        try {

            title.setText(object.getString("title"));
            id.setText(object.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return albumsItem;
    }
}
