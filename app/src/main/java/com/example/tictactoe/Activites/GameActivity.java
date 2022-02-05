package com.example.tictactoe.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tictactoe.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

public class GameActivity extends AppCompatActivity {
    TextView text1,text2,text3,text4,text5,text6,text7,text8,text9, showOTP, showChance;
    LinearLayout gameBow;
    Boolean X = false;
    int check3 = 0;
    private String otp = "";
    private final HashMap<Integer, String> map = new HashMap<>();
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        otp = intent.getStringExtra("otp");
        init();
        onClick();
        getFromDatabase();
        showOTP.setText(" OTP: "+ otp);
        gameBow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
                System.out.println(map);
            }
        });

    }

    public void init() {
        text1 = findViewById(R.id.first1);
        text2 = findViewById(R.id.second1);
        text3 = findViewById(R.id.thrird1);

        text4 = findViewById(R.id.first2);
        text5 = findViewById(R.id.second2);
        text6 = findViewById(R.id.thrird2);

        text7 = findViewById(R.id.first3);
        text8 = findViewById(R.id.second3);
        text9 = findViewById(R.id.thrird3);

        showChance = findViewById(R.id.chance);
        showOTP = findViewById(R.id.otp);

        gameBow = findViewById(R.id.gameBox);
    }

    public void getFromDatabase()
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child(otp).child("game");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (int i=0; i< snapshot.getChildrenCount(); i++)
                {
                    Log.d("@@changes: ", snapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void check() {
        // int[][] winningPos = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {1, 4, 7}, {2, 5, 8}, {3, 6, 9}, {1, 5, 9}, {3, 5, 7}};

        if (check3 < 2)
        {
            check3++;
            return;
        }

        for (int a = 0; a < 8; a++) {
            String line = "";

            switch (a) {
                case 0:
                    line = map.get(1) + map.get(2) + map.get(3);
                    System.out.println("line1: "+line);
                    break;
                case 1:
                    line = map.get(4) + map.get(5) + map.get(6);
                    System.out.println("line2: "+line);
                    break;
                case 2:
                    line =  map.get(7) + map.get(8) + map.get(9);
                    System.out.println("line3: "+line);
                    break;
                case 3:
                    line = map.get(1) + map.get(4) + map.get(7);
                    System.out.println("line4: "+line);
                    break;
                case 4:
                    line = map.get(2) + map.get(5) + map.get(8);
                    System.out.println("line5: "+line);
                    break;
                case 5:
                    line = map.get(3) + map.get(6) + map.get(9);
                    System.out.println("line6: "+line);
                    break;
                case 6:
                    line = map.get(1) + map.get(5) + map.get(9);
                    System.out.println("line7: "+line);
                    break;
                case 7:
                    line = map.get(3) + map.get(5) + map.get(7);
                    System.out.println("line8: "+line);
                    break;
            }

            if (line.equals("XXX")) {
//                text3.setBackgroundResource(R.drawable.coloured_background);
//                text1.setBackgroundResource(R.drawable.coloured_background);
//                text2.setBackgroundResource(R.drawable.coloured_background);
                System.out.println("finalLine: "+ line );
                gameBow.setEnabled(false);
                new AlertDialog.Builder(GameActivity.this)
                        .setTitle("WINNER")
                        .setMessage("X Wins!!")

                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                finish();
                                startActivity(getIntent());
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.ic_baseline_beenhere_24)
                        .show();
            }
            else if (line.equals("OOO")) {
                gameBow.setEnabled(false);
                new AlertDialog.Builder(GameActivity.this)
                        .setTitle("WINNER")
                        .setMessage("O Wins!!")
                        .setIcon(R.drawable.ic_baseline_beenhere_24)
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                finish();
                                startActivity(getIntent());

                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.ic_baseline_beenhere_24)
                        .show();
            }
        }

    }

    public void onClick() {

        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (X)
                {
                    text1.setText("O");
                    X = false;

                }
                else
                {
                    text1.setText("X");
                    X = true;
                }
                text1.setEnabled(false);
                map.put(1, text1.getText().toString());
                databaseReference = FirebaseDatabase.getInstance().getReference().child(otp).child("game").child("text1");
                databaseReference.setValue( text1.getText().toString());
                System.out.println(map);
                check();
            }
        });

        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (X)
                {
                    text2.setText("O");
                    X = false;
                }
                else
                {
                    text2.setText("X");
                    X = true;
                }
                text2.setEnabled(false);
                map.put(2, text2.getText().toString());
                databaseReference = FirebaseDatabase.getInstance().getReference().child(otp).child("game").child("text2");
                databaseReference.setValue(text2.getText().toString());
                System.out.println(map);
                check();
            }
        });

        text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (X)
                {
                    text3.setText("O");
                    X = false;
                }
                else
                {
                    text3.setText("X");
                    X = true;
                }
                text3.setEnabled(false);
                map.put(3, text3.getText().toString());
                databaseReference = FirebaseDatabase.getInstance().getReference().child(otp).child("game").child("text3");
                databaseReference.setValue( text3.getText().toString());
                check();
                System.out.println(map);
            }
        });

        text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (X)
                {
                    text4.setText("O");
                    X = false;
                }
                else
                {
                    text4.setText("X");
                    X = true;
                }
                text4.setEnabled(false);
                map.put(4, text4.getText().toString());
                databaseReference = FirebaseDatabase.getInstance().getReference().child(otp).child("game").child("text4");
                databaseReference.setValue(text4.getText().toString());
                check();
                System.out.println(map);
            }
        });

        text5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (X)
                {
                    text5.setText("O");
                    X = false;
                }
                else
                {
                    text5.setText("X");
                    X = true;
                }
                text5.setEnabled(false);
                map.put(5, text5.getText().toString());
                databaseReference = FirebaseDatabase.getInstance().getReference().child(otp).child("game").child("text5");
                databaseReference.setValue(text5.getText().toString());
                check();
                System.out.println(map);
            }
        });

        text6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (X)
                {
                    text6.setText("O");
                    X = false;
                }
                else
                {
                    text6.setText("X");
                    X = true;
                }
                text6.setEnabled(false);
                map.put(6, text6.getText().toString());
                databaseReference = FirebaseDatabase.getInstance().getReference().child(otp).child("game").child("text6");
                databaseReference.setValue(text6.getText().toString());
                check();
                System.out.println(map);
            }
        });


        text7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (X)
                {
                    text7.setText("O");
                    X = false;
                }
                else
                {
                    text7.setText("X");
                    X = true;
                }
                text7.setEnabled(false);
                map.put(7, text7.getText().toString());
                databaseReference = FirebaseDatabase.getInstance().getReference().child(otp).child("game").child("text7");
                databaseReference.setValue(text7.getText().toString());
                check();
                System.out.println(map);
            }
        });

        text8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (X)
                {
                    text8.setText("O");
                    X = false;
                }
                else
                {
                    text8.setText("X");
                    X = true;
                }
                text8.setEnabled(false);
                map.put(8, text8.getText().toString());
                databaseReference = FirebaseDatabase.getInstance().getReference().child(otp).child("game").child("text8");
                databaseReference.setValue(text8.getText().toString());
                check();
                System.out.println(map);
            }
        });

        text9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (X)
                {
                    text9.setText("O");
                    X = false;
                }
                else
                {
                    text9.setText("X");
                    X = true;
                }
                text9.setEnabled(false);
                map.put(9, text9.getText().toString());
                databaseReference = FirebaseDatabase.getInstance().getReference().child(otp).child("game").child("text9");
                databaseReference.setValue(text9.getText().toString());
                check();
                System.out.println(map);
            }
        });


    }



    }