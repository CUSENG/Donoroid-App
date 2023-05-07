package com.asazing.loginui.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.asazing.loginui.R;


public class NotificationsFragment extends Fragment {

    private EditText editTextName, editTextGender, editTextAge, editTextCity, editTextBloodGroup, editTextPhoneNumber;
    private Button buttonSubmit;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        editTextName = root.findViewById(R.id.editTextName);
        editTextGender = root.findViewById(R.id.editTextGender);
        editTextAge = root.findViewById(R.id.editTextAge);
        editTextCity = root.findViewById(R.id.editTextCity);
        editTextBloodGroup = root.findViewById(R.id.editTextBloodGroup);
        editTextPhoneNumber = root.findViewById(R.id.editTextPhoneNumber);
        buttonSubmit = root.findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String gender = editTextGender.getText().toString();
                int age = Integer.parseInt(editTextAge.getText().toString());
                String city = editTextCity.getText().toString();
                String bloodGroup = editTextBloodGroup.getText().toString();
                String phoneNumber = editTextPhoneNumber.getText().toString();

                // Verileri birleştir
                String data = "Name: " + name + "\n" +
                        "Gender: " + gender + "\n" +
                        "Age: " + age + "\n" +
                        "City: " + city + "\n" +
                        "Blood Group: " + bloodGroup + "\n" +
                        "Phone Number: " + phoneNumber;

                // Kullanıcıya verileri göster
                Toast.makeText(getActivity(), "Kullanıcı başarıyla güncellendi.", Toast.LENGTH_SHORT).show();

                // Submit butonu için özelleştirilmiş işlemler burada yapılabilir
            }
        });

        return root;
    }

}
