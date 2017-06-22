package net.jwheeler.android.cs3270mi;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActionFragment extends Fragment {
    View rootView;
    Button btnCalculate;
    EditText txtEditWeight, txtEditInches, txtEditAge;
    RadioGroup rgGender;
    String _gender;

    public ActionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_action, container, false);
        btnCalculate = (Button) rootView.findViewById(R.id.btnCalc);
        txtEditWeight = (EditText) rootView.findViewById(R.id.txtEditWeight);
        txtEditAge = (EditText) rootView.findViewById(R.id.txtEditAge);
        txtEditInches = (EditText) rootView.findViewById(R.id.txtEditInches);
        rgGender = (RadioGroup) rootView.findViewById(R.id.rgGender);

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId == R.id.radio_Female){
                    _gender = "F";
                }
                if(checkedId == R.id.radio_Male){
                    _gender = "M";
                }
            }
        });

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double weight = Double.parseDouble("0" + txtEditWeight.getText().toString());
                double inches = Double.parseDouble("0" + txtEditInches.getText().toString());
                double age = Double.parseDouble("0" + txtEditAge.getText().toString());

                MainActivity ma = (MainActivity) getActivity();

                Log.d("Test", "ActionFrag - onClick(Age: " + age+ " Weight: " + weight
                        + " Inches " + inches + " Gender: " + _gender);

                ma.Calculate(weight,inches,age,_gender);
            }
        });


        return rootView;
    }

    public void restoreState(Double weight, Double inches, Double age, String gender){
        Log.d("Test", "ActionFrag - restoreState(Weight: " + weight + " Inches: " + inches + " Age: " + age + " Gender: " + gender + ")");
        txtEditAge.setText(age.toString());
        txtEditInches.setText(inches.toString());
        txtEditWeight.setText(weight.toString());

        if(gender.equals("M")){
            Log.d("Test", "Gender is Male");
            ((RadioButton) rgGender.getChildAt(1)).setChecked(true);
        }

        if(gender.equals("F")){
            ((RadioButton) rgGender.getChildAt(0)).setChecked(true);
        }

        MainActivity ma = (MainActivity) getActivity();
        ma.Calculate(weight,inches,age,gender);
    }

}
