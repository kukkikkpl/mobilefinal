package th.ac.kmitl.a59070117;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;

public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase sqLiteDatabase;

    public DBHelper(Context context) {
        super(context, "mobile_final.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS user (id VARCHAR(13) PRIMARY KEY," +
                " name VARCHAR(50), age INTEGER, password VARCHAR(50));";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS user";
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void addUser(User user) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", user.getId());
        values.put("name", user.getName());
        values.put("age", user.getAge());
        values.put("password", user.getPassword());
        sqLiteDatabase.insert("user", null, values);
        sqLiteDatabase.close();
    }

    public boolean checkUser(String id, String password) {
        Cursor cursor = null;
        String sql = "SELECT id FROM user WHERE id = '" + id + "' AND password = '" + password + "';";
        cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public User getUser(String id) {
        Cursor cursor = null;
        String sql = "SELECT id FROM user WHERE id = \"" + id + "\"";
        cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        User user = new User();
        user.setId(cursor.getString(0));
        user.setName(cursor.getString(1));
        user.setAge(cursor.getInt(2));
        user.setPassword(cursor.getString(3));
        return user;
    }
}
