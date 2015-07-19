package coffee.chris.gopherstudios.dontpanic;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class title extends Fragment {
    Button panicButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_title, container, false);

        panicButton = (Button) view.findViewById(R.id.panicButton);
        panicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = ((main) getActivity()).getPhone();
                String text = ((main) getActivity()).getMessage();
                if(number != null && text != null) {
                    ((main) getActivity()).sendText(number, text);
                }else{
                    Toast.makeText(((main) getActivity()).getApplicationContext(), "Make sure you set up your emergency contact information",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }
}