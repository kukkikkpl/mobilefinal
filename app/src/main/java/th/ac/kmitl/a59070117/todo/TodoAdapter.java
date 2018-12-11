package th.ac.kmitl.a59070117.todo;

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

public class TodoAdapter extends ArrayAdapter {
    ArrayList<JSONObject> todoList;
    Context context;

    public TodoAdapter(Context context, int resource, ArrayList<JSONObject> objects) {
        super(context, resource, objects);
        this.todoList = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View todoItem = LayoutInflater.from(context).inflate(R.layout.fragment_todo_item, parent, false);
        TextView id = (TextView) todoItem.findViewById(R.id.todo_item_id);
        TextView title = (TextView) todoItem.findViewById(R.id.todo_item_title);
        TextView complete = (TextView) todoItem.findViewById(R.id.todo_item_complete);
        JSONObject object = todoList.get(position);
        try {
            id.setText(object.getString("id"));
            title.setText(object.getString("title"));
            if (object.getBoolean("complete")) {
                complete.setText("Complete");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return todoItem;
    }
}
