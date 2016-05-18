package mbadev.bcalculator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Boobo on 5/11/2016.
 */
public class Fragment1 extends Fragment {
    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag1,container,false);
        clearCalculus();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void clearCalculus() {
        Button ts = (Button) rootView.findViewById(R.id.btnDelete);

        ts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtCalculation = (TextView) getActivity().findViewById(R.id.txtCalculation);
                String txtCalc = txtCalculation.getText().toString();

                if( txtCalc.length() > 0)
                    txtCalc = txtCalc.substring(0,txtCalc.length()-1);

                txtCalculation.setText(txtCalc);
                Log.d("delete","ai apasat pe delete");
            }
        });

        ts.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                TextView txtCalculation = (TextView) getActivity().findViewById(R.id.txtCalculation);
                TextView txtResult = (TextView) getActivity().findViewById(R.id.txtResult);

                txtCalculation.setText("");
                txtResult.setText("0");

                Log.d("delete","ai apasat lung pe delete");
                return false;
            }
        });

    }
}