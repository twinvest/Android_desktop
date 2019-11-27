package com.example.androideatit;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.androideatit.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Calendar;

import info.hoang8f.widget.FButton;

public class SignUp extends AppCompatActivity {

    private MaterialEditText edtId, edtPhone, edtName, edtPassword, edtPassword2;
    private MaterialAutoCompleteTextView edtBirth;
    private DatePickerDialog.OnDateSetListener mDataSetListener;
    private ImageView setImage;
    private FButton btnBirthChoose, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtId = (MaterialEditText) findViewById(R.id.edtId);
        edtName = (MaterialEditText) findViewById(R.id.edtName);

        edtPhone = (MaterialEditText) findViewById(R.id.edtPhone);
        edtPhone.setText(getIntent().getStringExtra("edtPhone"));

        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword);
        edtPassword2 = (MaterialEditText) findViewById(R.id.edtPassword2);

        btnBirthChoose = (FButton) findViewById(R.id.btnBirthChoose);

        edtBirth = (MaterialAutoCompleteTextView) findViewById(R.id.edtBirth);
        edtBirth.setEnabled(false);

        setImage = findViewById(R.id.setImage);
        edtPassword2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtPassword.getText().toString().equals(edtPassword2.getText().toString())) {
                    setImage.setImageResource(R.drawable.ic_check_black_24dp);
                } else {
                    setImage.setImageResource(R.drawable.ic_close_black_24dp);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnBirthChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                mDataSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String birth;
                        if (month < 10)
                            birth = Integer.toString(year) + "0" + Integer.toString(month + 1);
                        else birth = Integer.toString(year) + Integer.toString(month + 1);
                        if (day < 10) birth += "0" + Integer.toString(day);
                        else birth += Integer.toString(day);
                        edtBirth.setText(birth);
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(
                        SignUp.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDataSetListener,
                        year, month, day);
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }

        });
        btnSignUp = findViewById(R.id.btnSignUp);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please waiting....");
                mDialog.show();
                // 아이디, 이름, 번호 다 입력했는지 체크
                table_user.addValueEventListener(new ValueEventListener() {
                    boolean tf = false;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Check if already user phone
                        if (dataSnapshot.child(edtId.getText().toString()).exists() && !tf) {
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "Phone Number has already been registered", Toast.LENGTH_SHORT).show();
                        } else {
                            mDialog.dismiss();
                            User user = new User(edtPhone.getText().toString(), edtName.getText().toString(), edtPassword.getText().toString(), edtBirth.getText().toString());
                            table_user.child(edtId.getText().toString()).setValue(user);
                            tf = true;
                            Toast.makeText(SignUp.this, "Sign up successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUp.this, StartActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
