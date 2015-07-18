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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        EditText nameField = (EditText)view.findViewById(R.id.Name);
        EditText phoneField = (EditText)view.findViewById(R.id.Phone);
        Button saveContactButton = (Button)view.findViewById(R.id.saveContact);

        EditText messageField = (EditText)view.findViewById(R.id.Message);
        Button saveMessageButton = (Button)view.findViewById(R.id.saveMessage);

        return view;
    }

    public void saveContact(View v){
    }
}