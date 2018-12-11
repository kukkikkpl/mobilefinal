package th.ac.kmitl.a59070117;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterFragment extends Fragment {

    private DBHelper helper;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRegisterNewAccountBtn();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    private void initRegisterNewAccountBtn() {
        Button _registerBtn = (Button) getView().findViewById(R.id.register_register_btn);
        _registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });
    }

    private void registerNewUser(){
        EditText id = (EditText) getView().findViewById(R.id.register_id);
        EditText name = (EditText) getView().findViewById(R.id.register_name);
        EditText age = (EditText) getView().findViewById(R.id.register_age);
        EditText password = (EditText) getView().findViewById(R.id.register_password);
        String idStr = id.getText().toString();
        String nameStr = name.getText().toString();
        int ageInt = Integer.parseInt(age.getText().toString());
        String passwordStr = password.getText().toString();

        if (idStr.isEmpty() || nameStr.isEmpty() || age.getText().toString().isEmpty() ||
                passwordStr.isEmpty()) {
            Toast.makeText(getActivity(), "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show();
        } else if (idStr.length() >= 6 && idStr.length() <= 12 && nameStr.contains(" ") &&
                ageInt >= 10 && ageInt <= 80 && passwordStr.length() >= 6) {
            User user = new User(idStr, nameStr, ageInt, passwordStr);
            helper.addUser(user);
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, new LoginFragment())
                    .addToBackStack(null).commit();
        } else {
            Toast.makeText(getActivity(), "ข้อมูลไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
        }

    }
}
