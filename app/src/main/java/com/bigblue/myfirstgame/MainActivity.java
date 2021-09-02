package com.bigblue.myfirstgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button buttonOK;
    private EditText editTextNumber;
    private TextView textViewIndication;
    private ProgressBar progressBarAttempt;
    private TextView textViewAttempt;
    private TextView textViewScore;
    private ListView listViewHistorique;
    private int magicNumber;
    private int counter;
    private int score;
    private List<String> listHistorique=new ArrayList<>();
    private int maxAttempt=6;
    private ArrayAdapter model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonOK=findViewById(R.id.buttonOK);
        editTextNumber=findViewById(R.id.editTextNumberUserInput);
        textViewIndication=findViewById(R.id.textViewMagicNumber);
        progressBarAttempt=findViewById(R.id.progressBarAttempt);
        textViewAttempt=findViewById(R.id.textViewAttempt);
        textViewIndication=findViewById(R.id.textViewIndication);
        listViewHistorique=findViewById(R.id.listHistorique);
        textViewScore=findViewById(R.id.textViewScore);

        model=new ArrayAdapter(this, android.R.layout.simple_list_item_1,listHistorique);
        listViewHistorique.setAdapter(model);

        initialisation();

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number=Integer.parseInt(editTextNumber.getText().toString());

                if(number>magicNumber){
                    textViewIndication.setText(R.string.str_ValSup);
                }else if(number<magicNumber){
                    textViewIndication.setText(R.string.str_ValInf);
                }else{
                    textViewIndication.setText(R.string.str_bravo);
                    score+=5;
                    textViewScore.setText(R.string.str_score);
                }
                listHistorique.add(counter+"=>"+number);
                model.notifyDataSetChanged();

                ++counter;
                textViewAttempt.setText(String.valueOf(counter));
                progressBarAttempt.setProgress(counter);

                if(counter>maxAttempt){
                    replay();
                }
            }
        });
    }

    private void replay() {

        Log.i("MyLog",getString(R.string.Rejouer));
        AlertDialog alertDialog=new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.RejouerQ));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.oui), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                initialisation();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.Quitter), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alertDialog.show();
    }


    private void initialisation() {
        magicNumber=1+((int)(Math.random()*100));
        //Log.i("MyLog","MagicNumber:"+magicNumber);
        counter=1;
        listHistorique.clear();model.notifyDataSetChanged();
        textViewAttempt.setText(String.valueOf(counter));
        progressBarAttempt.setProgress(counter);
        progressBarAttempt.setMax(maxAttempt);
        textViewScore.setText(String.valueOf(score));
    }
}