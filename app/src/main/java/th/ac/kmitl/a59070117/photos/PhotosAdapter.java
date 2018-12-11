package th.ac.kmitl.a59070117.photos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

import th.ac.kmitl.a59070117.R;

public class PhotosAdapter extends ArrayAdapter {
    ArrayList<JSONObject> photosList;
    Context context;

    public PhotosAdapter(Context context, int resource, ArrayList<JSONObject> objects) {
        super(context, resource, objects);
        this.photosList = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View photosItem = LayoutInflater.from(context).inflate(R.layout.fragment_photos_item, parent, false);
        TextView id = (TextView) photosItem.findViewById(R.id.photos_item_id);
        TextView title = (TextView) photosItem.findViewById(R.id.photos_item_title);
        ImageView thumbnail = (ImageView) photosItem.findViewById(R.id.photos_thumbnail);
        JSONObject object = photosList.get(position);
        try {
            id.setText(object.getString("id"));
            title.setText(object.getString("title"));
            new DownloadImageTask(thumbnail)
                    .execute(object.getString("thumbnailUrl"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return photosItem;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
