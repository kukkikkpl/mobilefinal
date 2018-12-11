package th.ac.kmitl.a59070117.comment;

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

public class CommentAdapter extends ArrayAdapter {
    ArrayList<JSONObject> commentList;
    Context context;

    public CommentAdapter(Context context, int resource, ArrayList<JSONObject> objects) {
        super(context, resource, objects);
        this.commentList = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View commentItem = LayoutInflater.from(context).inflate(R.layout.fragment_comment_item, parent, false);
        TextView title = (TextView) commentItem.findViewById(R.id.comment_item_title);
        TextView body = (TextView) commentItem.findViewById(R.id.comment_item_body);
        TextView name = (TextView) commentItem.findViewById(R.id.comment_item_name);
        TextView email = (TextView) commentItem.findViewById(R.id.comment_item_email);
        JSONObject object = commentList.get(position);
        try {
            title.setText(object.getString("postId") + " : " + object.getString("id"));
            body.setText(object.getString("body"));
            name.setText(object.getString("name"));
            email.setText(object.getString("email"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return commentItem;
    }
}