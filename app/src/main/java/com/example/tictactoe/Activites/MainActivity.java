package com.example.tictactoe.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tictactoe.Network.ApiClient;
import com.example.tictactoe.Network.ApiInterface;
import com.example.tictactoe.Network.ResponseOTP;
import com.example.tictactoe.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    ResponseOTP otpResponse;
    int otp = 0;
    TextView text1, text2, text3;
    LinearLayout linearLayout,linearLayout1;
    Button joinExistingGame,createNewGame;
    EditText enterOTP, user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = findViewById(R.id.myAcc);
        text2 = findViewById(R.id.searchAcc);
        text3 = findViewById(R.id.otp);
        linearLayout = findViewById(R.id.linearLayout);
        linearLayout1 = findViewById(R.id.linearLayout1);
        joinExistingGame = findViewById(R.id.searchBtn);
        createNewGame = findViewById(R.id.joinBtn);
        enterOTP = findViewById(R.id.username);
        user = findViewById(R.id.user);

        joinExistingGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                DatabaseReference myRef = database.child(enterOTP.getText().toString());
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Intent intent = new Intent(MainActivity.this, GameActivity.class);
                        intent.putExtra("otp",enterOTP.getText().toString());
                        if(snapshot.hasChild("portalStatus"))
                        {
                            myRef.child("OpponentStatus").setValue(true);
                            startActivity(intent);
                        }
                        else Toast.makeText(MainActivity.this, "OTP might be incorrect! Check again!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] array = new byte[6]; // length is bounded by 7
                new Random().nextBytes(array);
                String portalID = new String(array, StandardCharsets.UTF_8);
                int otpGenerated = new Random().nextInt(999999);
                String convert = ""+otpGenerated;
                createPortal(convert, portalID, true, user.getText().toString(), false);
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("otp", convert);
                startActivity(intent);
            }
        });

        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.VISIBLE);

            }
        });
    }

    public void createPortal(String otp, String portalId, boolean portalStatus, String ownerUsername, boolean opponentStatus )
    {
        DatabaseReference portal = FirebaseDatabase.getInstance().getReference().child(otp);


        portal.child("OTP").setValue(otp);
        portal.child("PortalID").setValue(portalId);
        portal.child("portalStatus").setValue(portalStatus);
        portal.child("OwnerName").setValue(ownerUsername);
        portal.child("OpponentStatus").setValue(opponentStatus);
//        portal.child("1-1").setValue("");
//        portal.child("1-2").setValue("");
//        portal.child("1-3").setValue("");
//        portal.child("2-1").setValue("");
//        portal.child("2-2").setValue("");
//        portal.child("2-3").setValue("");
//        portal.child("3-1").setValue("");
//        portal.child("3-2").setValue("");
//        portal.child("3-3").setValue("");


    }

    public void getMyOtp()
    {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseOTP> call = apiInterface.getOTP();
        call.enqueue(new Callback<ResponseOTP>() {
            @Override
            public void onResponse(Call<ResponseOTP> call, Response<ResponseOTP> response) {
                otpResponse = response.body();
               otp =  otpResponse.getOtp();
               String test = otpResponse.getPortal();
                Log.d("@@Portal: ", test);
                Log.d("@@otp: ",  (String.valueOf(otp)));
            }

            @Override
            public void onFailure(Call<ResponseOTP> call, Throwable t) {
                Log.d("@@failed: ", t.getMessage());
                System.out.println("@@message: "+ t.getLocalizedMessage());

                otp = 0;
            }
        });
    }

}