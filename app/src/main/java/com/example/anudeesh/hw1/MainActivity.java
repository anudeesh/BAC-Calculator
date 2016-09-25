package com.example.anudeesh.hw1;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText weight;
    private Switch sw;
    private double gender,bac;
    private SeekBar sb;
    private TextView sb_val,bac_val,stat_val;
    private RadioGroup rg;
    private int oz,count;
    private double wt,ap;
    private boolean flag=false;
    private ProgressBar pb;
    private Button save,add,reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        weight = (EditText) findViewById(R.id.weightValue);
        sw = (Switch) findViewById(R.id.switchGender);
        sb = (SeekBar) findViewById(R.id.seekBarAlcohol);
        sb_val = (TextView) findViewById(R.id.labelSliderValue);
        rg = (RadioGroup) findViewById(R.id.radioGroupDrinks);
        bac_val = (TextView) findViewById(R.id.bacValue);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        stat_val = (TextView) findViewById(R.id.statusValue);
        save = (Button) findViewById(R.id.buttonSaveWeight);
        add = (Button) findViewById(R.id.buttonAddDrink);
        reset = (Button) findViewById(R.id.buttonReset);
        oz = 1;
        ap=5;
        gender = 0.55;
        bac=0.00;
        count=0;
        pb.setProgress(0);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) findViewById(checkedId);
                String str = rb.getText().toString();
                oz = Integer.parseInt(str.substring(0,str.length()-3));
                //Log.d("demo","radio changed to "+rb.getText().toString() +" " +str.substring(0,str.length()-3) );
            }
        });

        weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                flag = false;
            }
        });

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                gender = isChecked ? 0.68 : 0.55;
                flag = false;
            }
        });

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress/5;
                progress = progress*5;
                sb_val.setText(String.valueOf(progress)+"%");
                ap=Double.parseDouble(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        save.setOnClickListener(this);
        add.setOnClickListener(this);
        reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.buttonSaveWeight) {
            if(weight.getText().toString().isEmpty()) {
                weight.setError("Enter the weight in lbs");
            }
            else {
                //if(!flag) {
                    //if(count==1) {
                        double wt = Double.parseDouble(weight.getText().toString());
                        bac = ((oz * ap * 6.24) / (wt * gender * 100));
                        if (bac >= 0.25) {

                            pb.setProgress(25);
                        } else {
                            int p = (int) (bac * 100);
                            pb.setProgress(p);
                        }
                        DecimalFormat precision = new DecimalFormat("0.00");
                        ((TextView) findViewById(R.id.bacValue)).setText(precision.format(bac));
                        if (bac <= 0.08) {
                            stat_val.setText("You're safe");
                            stat_val.setBackgroundColor(Color.parseColor("#3fd924"));
                        } else if (bac > 0.08 && bac < 0.20) {
                            stat_val.setText("Be careful...");
                            stat_val.setBackgroundColor(Color.parseColor("#f9c030"));
                        } else {
                            stat_val.setText("Over the limit!");
                            stat_val.setBackgroundColor(Color.parseColor("#fa0f1f"));

                        }
                        if (bac >= 0.25) {
                            save.setClickable(false);
                            save.setEnabled(false);
                            add.setClickable(false);
                            add.setEnabled(false);
                            Toast.makeText(getApplicationContext(), "No more drinks for you", Toast.LENGTH_LONG).show();
                        }
                    //}
                //}
                flag = true;
            }
        }
        else if(v.getId() == R.id.buttonAddDrink) {
            if(weight.getText().toString().isEmpty()) {
                weight.setError("Enter the weight in lbs");
            }
            else {
                double wt = Double.parseDouble(weight.getText().toString());
                //double ap = Double.parseDouble(sb_val.getText().toString());
                if(flag) {
                    bac = bac + ((oz*ap*6.24)/(wt*gender*100));
                    if(bac>=0.25) {

                        pb.setProgress(25);
                    }
                    else {
                        int p = (int)(bac*100);
                        pb.setProgress(p);
                    }
                    //DecimalFormat precision = new DecimalFormat("0.00");
                    bac_val.setText(Double.toString(bac).substring(0,4));
                    if(bac<=0.08) {
                        stat_val.setText("You're safe");
                        stat_val.setBackgroundColor(Color.parseColor("#3fd924"));
                    }
                    else if (bac>0.08 && bac<0.20) {
                        stat_val.setText("Be careful...");
                        stat_val.setBackgroundColor(Color.parseColor("#f9c030"));
                    }
                    else {
                        stat_val.setText("Over the limit!");
                        stat_val.setBackgroundColor(Color.parseColor("#fa0f1f"));
                    }
                    if (bac >= 0.25) {
                        save.setClickable(false);
                        save.setEnabled(false);
                        add.setClickable(false);
                        add.setEnabled(false);
                        Toast.makeText(getApplicationContext(),"No more drinks for you",Toast.LENGTH_LONG).show();
                    }
                    //Log.d("demo",Double.toString(bac));
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please save the changes to continue",Toast.LENGTH_LONG).show();
                }
            }
        }
        else if(v.getId() == R.id.buttonReset) {
            ((RadioButton) findViewById(R.id.radioButtonOneOz)).setChecked(true);
            sb.setProgress(5);
            weight.setText("");
            sw.setChecked(false);
            bac_val.setText("0.00");
            save.setClickable(true);
            save.setEnabled(true);
            add.setClickable(true);
            add.setEnabled(true);
            pb.setProgress(0);
            stat_val.setText("You're safe");
            stat_val.setBackgroundColor(Color.parseColor("#3fd924"));
        }
    }
}
