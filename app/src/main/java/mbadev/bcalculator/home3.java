package mbadev.bcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;
import java.util.*;

public class home3 extends AppCompatActivity {

    ArrayList<String> members = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        clearCalculus();
    }

    public void addThisMember(View v) {
        TextView txtCalculation = (TextView) findViewById(R.id.txtCalculation);
        TextView txtResult = (TextView) findViewById(R.id.txtResult);

        Button cButton = (Button) v;
        String cString = cButton.getText().toString();


        txtCalculation.setText(txtCalculation.getText().toString()+cString);
    }

    public void doEqual(View v) {
        TextView txtCalculation = (TextView) findViewById(R.id.txtCalculation);
        TextView txtResult = (TextView) findViewById(R.id.txtResult);

        String result = evaluate(txtCalculation.getText().toString());

        txtResult.setText(result);
        txtCalculation.setText(result);
    }

    public static String evaluate(String line) {
        //String[] data = line.split("()+-*/");
        StringTokenizer data = new StringTokenizer(line, "()+-*/", true);

        Log.d("blaaaa",data.toString());

        Stack<String> symbols = new Stack<String>();
        Stack<Double> values = new Stack<Double>();
        boolean error = false;
        while (!error && data.hasMoreTokens()) {

            String next = data.nextToken();
            if (next.equals(")")) {
                if (symbols.size() < 2 || symbols.peek().equals("(")) {
                    error = true;
                } else {
                    String operator = symbols.pop();
                    if (!symbols.peek().equals("(")) {
                        error = true;
                    } else {
                        symbols.pop(); // to remove the "("
                        double oper2 = values.pop();
                        double oper1 = values.pop();
                        double value = evaluate(operator, oper1, oper2);
                        values.push(value);
                    }
                }
            } else if ("(+-*/^".indexOf(next) != -1) {
                symbols.push(next);
            } else {  // it should be a number
                Log.d("parseDouble",Double.parseDouble(next)+"");
                values.push(Double.parseDouble(next));
            }
            //System.out.println(next + "\t\t" + symbols + "\t" + values);
        }
        if (error || values.size() != 1 || !symbols.isEmpty()) {
            //System.out.println("illegal expression");
            return "illegal expression";
        } else {
            //System.out.println(values.pop());
            return values.pop().toString();
        }
    }

    public static double evaluate(String operator, double operand1, double operand2) {
        if (operator.equals("+")) {
            return operand1 + operand2;
        } else if (operator.equals("-")) {
            return operand1 - operand2;
        } else if (operator.equals("*")) {
            return operand1 * operand2;
        } else if (operator.equals("/")) {
            return operand1 / operand2;
        } else if (operator.equals("^")) {
            return Math.pow(operand1, operand2);
        } else {
            throw new RuntimeException("illegal operator " + operator);
        }
    }

    public void clearCalculus() {
        Button ts = (Button) findViewById(R.id.btnDelete);

        ts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtCalculation = (TextView) findViewById(R.id.txtCalculation);
                TextView txtResult = (TextView) findViewById(R.id.txtResult);

                String txtCalc = txtCalculation.getText().toString();

                if(txtCalc.length() > 0)
                    txtCalc = txtCalc.substring(0,txtCalc.length()-1);

                txtCalculation.setText(txtCalc);
            }
        });

        ts.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                TextView txtCalculation = (TextView) findViewById(R.id.txtCalculation);
                TextView txtResult = (TextView) findViewById(R.id.txtResult);

                members.clear();
                txtCalculation.setText("");
                txtResult.setText("0");
                return false;
            }
        });

    }


}
