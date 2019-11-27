package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.capstone.R;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class PhoneAuthActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 7117; // Any number
    List<AuthUI .IdpConfig> providers;
    Button phoneAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);

        phoneAuth = (Button)findViewById(R.id.phoneAuth);
        phoneAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Init providers
                providers = Arrays.asList(
                        new AuthUI.IdpConfig.PhoneBuilder().build()
                );
                startActivityForResult(
                        AuthUI.getInstance().createSignInIntentBuilder()
                                .setAvailableProviders(providers).build(), MY_REQUEST_CODE
                );
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String phoneNumber = user.getPhoneNumber();
                Intent intent = new Intent(PhoneAuthActivity.this, SignUp.class);
                intent.putExtra("edtPhone", phoneNumber);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "인증번호 또는 핸드폰 번호가 잘못되었습니다.", Toast.LENGTH_LONG).show();
            }
        }
    }
}