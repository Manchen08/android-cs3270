package net.jwheeler.android.cs3270a5;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeMaxFragment extends Fragment {

    View rootView;
    Button btnSaveMax;
    EditText txtEditMaxAmount;
    public ChangeMaxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_change_max, container, false);
        btnSaveMax = (Button) rootView.findViewById(R.id.btnSaveMax);
        txtEditMaxAmount = (EditText) rootView.findViewById(R.id.txtEditMaxAmount);

        btnSaveMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double amount = Double.parseDouble("0" + txtEditMaxAmount.getText().toString());
                MainActivity ma = (MainActivity) getActivity();
                if(amount < .01){
                    ma.alertMaxAmount();
                }else{
                    ma.saveMaxAmount(amount);
                }

            }
        });
        return rootView;
    }

}
