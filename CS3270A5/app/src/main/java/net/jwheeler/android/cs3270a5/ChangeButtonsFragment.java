package net.jwheeler.android.cs3270a5;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.math.BigDecimal;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeButtonsFragment extends Fragment {

    View rootView;
    Button btn50,btn20,btn10,btn5,btn1,btn_50,btn_25,btn_10,btn_05,btn_01;


    public ChangeButtonsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_change_buttons, container, false);

        btn50 = (Button) rootView.findViewById(R.id.btn50);
        btn20 = (Button) rootView.findViewById(R.id.btn20);
        btn10 = (Button) rootView.findViewById(R.id.btn10);
        btn5 = (Button) rootView.findViewById(R.id.btn5);
        btn1 = (Button) rootView.findViewById(R.id.btn1);
        btn_50 = (Button) rootView.findViewById(R.id.btn_50);
        btn_25 = (Button) rootView.findViewById(R.id.btn_25);
        btn_10 = (Button) rootView.findViewById(R.id.btn_10);
        btn_05 = (Button) rootView.findViewById(R.id.btn_05);
        btn_01 = (Button) rootView.findViewById(R.id.btn_01);

        btn50.setOnClickListener(OnClick);
        btn20.setOnClickListener(OnClick);
        btn10.setOnClickListener(OnClick);
        btn5.setOnClickListener(OnClick);
        btn1.setOnClickListener(OnClick);
        btn_50.setOnClickListener(OnClick);
        btn_25.setOnClickListener(OnClick);
        btn_10.setOnClickListener(OnClick);
        btn_05.setOnClickListener(OnClick);
        btn_01.setOnClickListener(OnClick);


        return rootView;
    }

    public View.OnClickListener OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainActivity ma = (MainActivity) getActivity();
            if(ma._timer == null){
                ma.startTimer();
            }
            switch(v.getId()){
                case R.id.btn50:
                    ma.addMoney(new BigDecimal(50.00));
                    break;
                case R.id.btn20:
                    ma.addMoney(new BigDecimal(20.00));
                    break;
                case R.id.btn10:
                    ma.addMoney(new BigDecimal(10.00));
                    break;
                case R.id.btn5:
                    ma.addMoney(new BigDecimal(5.00));
                    break;
                case R.id.btn1:
                    ma.addMoney(new BigDecimal(1.00));
                    break;
                case R.id.btn_50:
                    ma.addMoney(new BigDecimal(.50));
                    break;
                case R.id.btn_25:
                    ma.addMoney(new BigDecimal(.25));
                    break;
                case R.id.btn_10:
                    ma.addMoney(new BigDecimal(.10));
                    break;
                case R.id.btn_05:
                    ma.addMoney(new BigDecimal(.05));
                    break;
                case R.id.btn_01:
                    ma.addMoney(new BigDecimal(.01));
                    break;
                default:
                    break;
            }

        }
    };
}
