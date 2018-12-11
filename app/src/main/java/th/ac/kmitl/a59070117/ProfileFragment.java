package th.ac.kmitl.a59070117;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class ProfileFragment extends Fragment {

    private DBHelper helper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initSaveBtn();
    }

    public void initSaveBtn() {
        Button saveBtn = (Button) getView().findViewById(R.id.profile_save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void save() {
        EditText id = (EditText) getView().findViewById(R.id.profile_id);
        EditText name = (EditText) getView().findViewById(R.id.profile_name);
        EditText age = (EditText) getView().findViewById(R.id.profile_age);
        EditText password = (EditText) getView().findViewById(R.id.profile_password);
        EditText quote = (EditText) getView().findViewById(R.id.profile_quote);
        String idStr = id.getText().toString();
        String nameStr = name.getText().toString();
        int ageInt = Integer.parseInt(age.getText().toString());
        String passwordStr = password.getText().toString();
        String quoteStr = quote.getText().toString();

        if (idStr.isEmpty() || nameStr.isEmpty() || age.getText().toString().isEmpty() ||
                passwordStr.isEmpty()) {
            Toast.makeText(getActivity(), "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show();
        } else if (idStr.length() >= 6 && idStr.length() <= 12 && nameStr.contains(" ") &&
                ageInt >= 10 && ageInt <= 80 && passwordStr.length() >= 6) {
            User user = new User(idStr, nameStr, ageInt, passwordStr);
            helper.updateUser(user);
            writeToFile(quoteStr, getContext());
        } else {
            Toast.makeText(getActivity(), "ข้อมูลไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
        }

    }

    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("quote.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, new HomeFragment())
                    .commit();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

}
