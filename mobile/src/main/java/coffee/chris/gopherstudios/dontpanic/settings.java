package coffee.chris.gopherstudios.dontpanic;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class settings extends Fragment {
    EditText nameField;
    EditText phoneField;
    Button saveContactButton;

    EditText messageField;
    Button saveMessageButton;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        nameField = (EditText) view.findViewById(R.id.Name);
        phoneField = (EditText) view.findViewById(R.id.Phone);
        saveContactButton = (Button) view.findViewById(R.id.saveContact);
        saveContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((main) getActivity()).setName(nameField.getText().toString());
                ((main) getActivity()).setPhone(phoneField.getText().toString());
            }
        });

        messageField = (EditText) view.findViewById(R.id.Message);
        saveMessageButton = (Button) view.findViewById(R.id.saveMessage);
        saveMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((main) getActivity()).setMessage(messageField.getText().toString());
            }
        });

        return view;
    }
}