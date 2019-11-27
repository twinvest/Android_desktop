package com.example.androideatit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androideatit.Common.Common;
import com.example.androideatit.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.Format;


public class InfoChangeFragment extends Fragment {

    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_change, container, false);

        // ID 보여줌
        TextView textViewUserID = view.findViewById(R.id.userID);
        textViewUserID.setText(Common.getMyId());

        // edit text 연결
        final EditText editName, editPhoneNumber, editBirth, editPassword, editPasswordCheck;
        editName = (EditText) view.findViewById(R.id.name);
        editPhoneNumber = (EditText) view.findViewById(R.id.phone_number);
        editBirth = (EditText) view.findViewById(R.id.birth);
        editPassword = (EditText) view.findViewById(R.id.password);
        editPasswordCheck = (EditText) view.findViewById(R.id.password_check);

        final ImageView passwordCheckView = (ImageView) view.findViewById(R.id.check_icon);

        // db 연결
        final DatabaseReference MY_USER_DB = Common.getDatabase("User").child(Common.getMyId());

        // user 정보를 edit 에 넣음
        MY_USER_DB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                editName.setHint(" " + Common.getUserName());
                editPhoneNumber.setHint(" " + user.getPhone());
                editBirth.setHint(" " + user.getBirthDate());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("data 인출 에러 - ", databaseError.getMessage());
            }
        });


        editPasswordCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editPassword.getText().toString().equals(editPasswordCheck.getText().toString())) {
                    passwordCheckView.setImageResource(R.drawable.fui_ic_check_circle_black_128dp);
                } else {
                    passwordCheckView.setImageResource(R.drawable.ic_close_black_24dp);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


//         사용자 변경 내용 저장
        view.findViewById(R.id.change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!editName.getText().toString().equals(""))
                    user.setName(editName.getText().toString());
                if (!editPhoneNumber.getText().toString().equals(""))
                    user.setPhone(editPhoneNumber.getText().toString());
                // 생일
                // 비밀번호

                // 변경 여부 확인
                changeAlert(MY_USER_DB, user);


            }
        });

        return view;
    }


    // 변경 알람
    private void changeAlert(final DatabaseReference userDB, final User user) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("개인정보 수정");
        builder.setMessage("이대로 수정하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        userDB.setValue(user);
                        Toast.makeText(getContext(), "수정되었습니다!", Toast.LENGTH_LONG).show();
                        getFragmentManager().beginTransaction().replace(R.id.home_fragment, new SettingFragment()).commit();
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "취소", Toast.LENGTH_LONG).show();
                    }
                });

        builder.show();
    }


}
