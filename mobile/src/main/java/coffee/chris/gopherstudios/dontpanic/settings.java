package coffee.chris.gopherstudios.dontpanic;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class settings extends Fragment {
    EditText nameField;
    EditText phoneField;
    EditText messageField;
    CheckBox audioField;
    EditText audioTime;

    Button saveContactButton;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        nameField = (EditText) view.findViewById(R.id.Name);
        phoneField = (EditText) view.findViewById(R.id.Phone);
        messageField = (EditText) view.findViewById(R.id.Message);
        audioField = (CheckBox) view.findViewById(R.id.AudioBool);
        audioTime = (EditText) view.findViewById(R.id.AudioTime);


        saveContactButton = (Button) view.findViewById(R.id.saveContact);
        saveContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((main) getActivity()).getContactSettings().newContact(nameField.getText().toString(),
                        phoneField.getText().toString(),
                        messageField.getText().toString(),
                        audioField.isChecked(),
                        Integer.parseInt(audioTime.getText().toString()));
            }
        });

        return view;
    }
}