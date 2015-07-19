package coffee.chris.gopherstudios.dontpanic;

import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.*;

public class ContactSettingsFragment extends Fragment {
    EditText nameField;
    EditText phoneField;
    EditText messageField;
    CheckBox audioField;
    EditText audioTime;

    Button saveContactButton;

    ContactSettings m_ContactSettings;

    public static final ContactSettingsFragment newInstance( ContactSettings a_ContactSettings )
    {
        ContactSettingsFragment f = new ContactSettingsFragment();
        f.m_ContactSettings = a_ContactSettings;
        return f;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        nameField = (EditText) view.findViewById(R.id.Name);
        phoneField = (EditText) view.findViewById(R.id.Phone);
        messageField = (EditText) view.findViewById(R.id.Message);
        audioField = (CheckBox) view.findViewById(R.id.AudioBool);
        audioTime = (EditText) view.findViewById(R.id.AudioTime);

        StringBuilder text = new StringBuilder();
        try {
            File sdcard = Environment.getExternalStorageDirectory();
            File file = new File(sdcard,"testFile.txt");

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close() ;
        }catch (IOException e) {
            e.printStackTrace();
        }

        saveContactButton = (Button) view.findViewById(R.id.saveContact);
        saveContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!audioTime.getText().toString().equals("")){
                    m_ContactSettings.addContact(
                            nameField.toString(),
                            phoneField.toString(),
                            messageField.toString(),
                            audioField.isChecked(),
                    Integer.parseInt(audioTime.getText().toString()));

                    String filename = "contact_settings";
                    FileOutputStream outputStream;
                    try {
                        outputStream = getActivity().openFileOutput(filename, getActivity().MODE_PRIVATE);
                        outputStream.write(m_ContactSettings.toString().getBytes());
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    m_ContactSettings.addContact(
                            nameField.toString(),
                            phoneField.toString(),
                            messageField.toString(),
                            audioField.isChecked(),
                            20 *1000  //20 * 1000 milliseconds
                    );
                }

            }
        });

        return view;
    }
}