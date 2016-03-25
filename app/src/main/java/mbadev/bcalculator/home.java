package mbadev.bcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class home extends AppCompatActivity {
/*
* todo: condition for Decimal.format
* */

    ArrayList<String> members = new ArrayList<>();

    String cString = ""; // current String (the value of the current button pressed
    String wholeNum = ""; // the current number (if the user want to add a number with more digits

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        clearCalculus();
    }

    public boolean isSymbol(String element) {
        if (element.equals("+") || element.equals("-") || element.equals("x") || element.equals("/"))  // if current String cString doesn't contain operators
            return true;
        return false;
    }

    public void addThisMember(View v) {
        TextView txtCalculation = (TextView) findViewById(R.id.txtCalculation);
        TextView txtResult = (TextView) findViewById(R.id.txtResult);

        Button cButton = (Button) v;
        cString = cButton.getText().toString();

        if (!isSymbol(cString)) { // if current String cString doesn't contain operators

            wholeNum = wholeNum + cString; // new number become old number and currentString

            if (members.size() > 0 && !isSymbol(members.get(members.size() - 1)))
                members.remove(members.size() - 1); // remove the last element from array

            members.add(wholeNum);//and add the new number created

        } else { // cString is symbol

            if (members.size() > 0) {
                if (isSymbol(members.get(members.size() - 1)))
                    members.remove(members.size() - 1);
                members.add(cString);
            } else if (cString.equals("-")) {
                members.add("0");
                members.add(cString);
            }
            wholeNum = "";
        }

        if (members.size() % 2 != 0)
            txtResult.setText(doCalculus(members, false));

        //txtCalculation.setText(txtCalculation.getText().toString()+cString);
        //txtCalculation.setText(members.toString());
        txtCalculation.setText(members.toString().replaceAll("\\[|\\]|,|\\s", " "));
    }

    public void doEqual(View v) {
        TextView txtCalculation = (TextView) findViewById(R.id.txtCalculation);
        TextView txtResult = (TextView) findViewById(R.id.txtResult);

        String result = doCalculus(members, true);

        if (Double.parseDouble(result) % 1 == 0)
            result = Integer.toString((int) Double.parseDouble(result));

        txtResult.setText(result);
        txtCalculation.setText(result);
    }

    public String doCalculus(ArrayList<String> realCalc, boolean finalResult) {
        ArrayList<String> myArr;

        if (finalResult)
            myArr = members;
        else
            myArr = new ArrayList<>(realCalc);

        double res = 0;
        int mSize = myArr.size();

        if (mSize == 1)
            res = Double.parseDouble(myArr.get(0));

        while (mSize != 1) {
            if (mSize > 3) {
                if (myArr.get(3).equals("x") || myArr.get(3).equals("/")) {

                    if (myArr.get(3).equals("x"))
                        res = Double.parseDouble(myArr.get(2)) * Double.parseDouble(myArr.get(4));
                    if (myArr.get(3).equals("/"))
                        res = Double.parseDouble(myArr.get(2)) / Double.parseDouble(myArr.get(4));

                    myArr.remove(2);
                    myArr.remove(2);
                    myArr.remove(2);
                    myArr.add(2, Double.toString(res));
                    mSize = myArr.size();
                } else {
                    if (myArr.get(1).equals("+"))
                        res = Double.parseDouble(myArr.get(0)) + Double.parseDouble(myArr.get(2));
                    if (myArr.get(1).equals("-"))
                        res = Double.parseDouble(myArr.get(0)) - Double.parseDouble(myArr.get(2));
                    if (myArr.get(1).equals("x"))
                        res = Double.parseDouble(myArr.get(0)) * Double.parseDouble(myArr.get(2));
                    if (myArr.get(1).equals("/"))
                        res = Double.parseDouble(myArr.get(0)) / Double.parseDouble(myArr.get(2));

                    myArr.remove(0);
                    myArr.remove(0);
                    myArr.remove(0);
                    myArr.add(0, Double.toString(res));
                    mSize = myArr.size();

                }
            } else {

                if (myArr.get(1).equals("+"))
                    res = Double.parseDouble(myArr.get(0)) + Double.parseDouble(myArr.get(2));
                if (myArr.get(1).equals("-"))
                    res = Double.parseDouble(myArr.get(0)) - Double.parseDouble(myArr.get(2));
                if (myArr.get(1).equals("x"))
                    res = Double.parseDouble(myArr.get(0)) * Double.parseDouble(myArr.get(2));
                if (myArr.get(1).equals("/"))
                    res = Double.parseDouble(myArr.get(0)) / Double.parseDouble(myArr.get(2));

                myArr.remove(0);
                myArr.remove(0);
                myArr.remove(0);
                myArr.add(0, Double.toString(res));
                mSize = myArr.size();
            }
        }

        return Double.toString(res);
    }

    public void clearCalculus() {
        Button ts = (Button) findViewById(R.id.btnDelete);

        ts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtCalculation = (TextView) findViewById(R.id.txtCalculation);
                TextView txtResult = (TextView) findViewById(R.id.txtResult);

                String txtCalc = txtCalculation.getText().toString();
                String last = "";

                if (members.size() > 0) {
                    last = members.get(members.size() - 1);
                    members.remove(members.size() - 1);
                    if (last.length() > 0) {
                        last = last.substring(0, last.length() - 1);
                        wholeNum = last;

                        if (last.length() > 0) {
                            members.add(last);
                            //doCalculus(v);
                        }
                    }
                } else
                    txtResult.setText("0");

                txtCalculation.setText(members.toString().replaceAll("\\[|\\]|,|\\s", " "));
            }
        });

        ts.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                TextView txtCalculation = (TextView) findViewById(R.id.txtCalculation);
                TextView txtResult = (TextView) findViewById(R.id.txtResult);

                Log.d("longpress", "L-ai apasat lung boss");
                cString = "0";
                wholeNum = "";
                members.clear();
                txtCalculation.setText("");
                txtResult.setText("0");
                return false;
            }
        });

    }

    private void nrValidation(String nr) {

    }


}
