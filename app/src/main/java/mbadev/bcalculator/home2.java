package mbadev.bcalculator;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Console;
import java.io.IOError;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;
import java.util.*;

public class home2 extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new CustomAdapter(getSupportFragmentManager(),getApplicationContext()));

    }

    public void doEqual(View v) {
        TextView txtCalculation = (TextView) findViewById(R.id.txtCalculation);
        TextView txtResult = (TextView) findViewById(R.id.txtResult);

        Double result = eval(txtCalculation.getText().toString());

        txtResult.setText(result.toString());
        txtCalculation.setText(result.toString());
    }

    /*public void clearCalculus() {
        Button ts = (Button) findViewById(R.id.btnDelete);

        ts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtCalculation = (TextView) findViewById(R.id.txtCalculation);
                String txtCalc = txtCalculation.getText().toString();

                if( txtCalc.length() > 0)
                    txtCalc = txtCalc.substring(0,txtCalc.length()-1);

                txtCalculation.setText(txtCalc);
            }
        });

        ts.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                TextView txtCalculation = (TextView) findViewById(R.id.txtCalculation);
                TextView txtResult = (TextView) findViewById(R.id.txtResult);

                txtCalculation.setText("");
                txtResult.setText("0");
                return false;
            }
        });

    }*/

    public void addThisMember(View v) {
        TextView txtCalculation = (TextView) findViewById(R.id.txtCalculation);
        TextView txtResult = (TextView) findViewById(R.id.txtResult);

        Button cButton = (Button) v;
        String cString = cButton.getText().toString();

        txtCalculation.setText(txtCalculation.getText().toString() + cString);
    }

/*
* evaluation function
* */

    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                //ch = (++pos < str.length()) ? str.charAt(pos) : -1;

                if (++pos < str.length())
                    ch = str.charAt(pos);
                else
                    ch = -1;
            }

            boolean checkChar(int charToCheck) {
                while (ch == ' ') // while current char is space skip to the next char
                    nextChar();

                if (ch == charToCheck) { //if after those space I found the wanted char skip to the next and return true
                    nextChar();
                    return true;
                }
                return false;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parse() { // this is the first function called by this object
                nextChar();// for now I am on the -1 position in string, so I will go to the first char on position 0 MUHAHA

                double x = parseExpression(); // so I arrived here for expression examination
                //after the last execution of function parseExpression I have to be on the str.lenth() position (Last position in string)
                if (pos < str.length()) //if this will not happen
                    throw new RuntimeException("Unexpected: " + (char) ch); //should be an error

                return x;//in other case VICTORY, return the x
            }

            double parseExpression() {
                double x = parseTerm(); // maybe my expression is not simple so I have to threat that case
                                        // I have to check if my expression have a complex syntax with more than +,- signs

                // in the other case I will execut this
                // i have to check after my + or - sign exists something with more priority
                for (;;) {
                    if (checkChar('+'))
                        x += parseTerm(); // addition
                    else if (checkChar('-'))
                        x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor(); // this function will make all the calculus
                for (;;) {
                    if (checkChar('x'))
                        x *= parseFactor(); // multiplication
                    else if (checkChar('/'))
                        x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (checkChar('+'))//because I treated this in parseExpression function
                    return parseFactor(); // unary plus
                if (checkChar('-'))//same case for this
                    return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;

                if (checkChar('(')) { // parentheses
                                            //if I found a ( char then
                    x = parseExpression(); //I will treat another expression
                    checkChar(')');//serch for the end tag
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    //while my char is a number I will go to check next
                    while ((ch >= '0' && ch <= '9') || ch == '.')
                        nextChar();
                    //after that I will store the whole value in x var
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    //if my char is alpha then I will have a function
                    while (ch >= 'a' && ch <= 'z')
                        nextChar();
                    //so I get the function name in func var
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();//and I will treat the string after function for new expression evaluation

                    //here I treat the function
                    if (func.equals("sqrt"))
                        x = Math.sqrt(x);
                    else if (func.equals("sin"))
                        x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos"))
                        x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan"))
                        x = Math.tan(Math.toRadians(x));
                    else if (func.equals("cot"))
                        x = 1.0 / (Math.tan(Math.toRadians(x)));
                    else
                        throw new RuntimeException("Unknown function: " + func);//if here are something else than known function I return an error
                } else
                    throw new RuntimeException("Unexpected: " + (char) ch);

                if (checkChar('^')) // if my char is ^ I will treat this case so
                    x = Math.pow(x, parseFactor()); // exponentiation

                return x;//IEEEI I'm the king of the world and this expression was to easy for me
                        //have you something hard for me?!
            }
        }.parse();
    }


    private class CustomAdapter extends FragmentPagerAdapter {
        private String fragments[] = {"Fragment 1","Fragment 2"};

        public CustomAdapter(FragmentManager supportFragmentManager, Context applicationContext) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Fragment1();
                case 1:
                    return new Fragment2();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments[position];
        }
    }
}
