package th.ac.kmitl.a59070117.friend;

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

public class FriendAdapter extends ArrayAdapter {
    ArrayList<JSONObject> friendList;
    Context context;

    public FriendAdapter(Context context, int resource, ArrayList<JSONObject> objects) {
        super(context, resource, objects);
        this.friendList = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View friendItem = LayoutInflater.from(context).inflate(R.layout.fragment_friend_item, parent, false);
        TextView title = (TextView) friendItem.findViewById(R.id.friend_item_title);
        TextView email = (TextView) friendItem.findViewById(R.id.friend_item_email);
        TextView phone = (TextView) friendItem.findViewById(R.id.friend_item_phone);
        TextView website = (TextView) friendItem.findViewById(R.id.friend_item_website);
        JSONObject object = friendList.get(position);
        try {

            title.setText(object.getString("id") + " : " + object.getString("name"));
            email.setText(object.getString("email"));
            phone.setText(object.getString("phone"));
            website.setText(object.getString("website"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return friendItem;
    }
}
