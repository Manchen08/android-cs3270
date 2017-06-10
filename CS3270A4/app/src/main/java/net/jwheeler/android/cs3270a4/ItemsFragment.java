package net.jwheeler.android.cs3270a4;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemsFragment extends Fragment {

    View rootView;
    EditText edt1,edt2,edt3,edt4;

    public ItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Test","ItemsFrag onCreateView");
        // Inflate the layout for this fragment
        rootView = (View) inflater.inflate(R.layout.fragment_items, container, false);
        edt1 = (EditText) rootView.findViewById(R.id.editItem1);
        edt2 = (EditText) rootView.findViewById(R.id.editItem2);
        edt3 = (EditText) rootView.findViewById(R.id.editItem3);
        edt4 = (EditText) rootView.findViewById(R.id.editItem4);

        edt1.addTextChangedListener(amountChanged);
        edt2.addTextChangedListener(amountChanged);
        edt3.addTextChangedListener(amountChanged);
        edt4.addTextChangedListener(amountChanged);

        return rootView;
    }

    private void calcTotal(){
        Log.d("Test","ItemsFrag calcTotal");
        double totalAmount = 0.0;
        totalAmount += Double.parseDouble("0" + edt1.getText().toString());
        totalAmount += Double.parseDouble("0" + edt2.getText().toString());
        totalAmount += Double.parseDouble("0" + edt3.getText().toString());
        totalAmount += Double.parseDouble("0" + edt4.getText().toString());
        MainActivity ma = (MainActivity) getActivity();
        ma.setTotal(totalAmount, edt1.getText().toString(), edt2.getText().toString(), edt3.getText().toString(), edt4.getText().toString());
    }

    public void restoreState(String amt1, String amt2, String amt3, String amt4){
        edt1.setText(amt1);
        edt2.setText(amt2);
        edt3.setText(amt3);
        edt4.setText(amt4);
    }

    public TextWatcher amountChanged = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            calcTotal();
        }
    };
}
