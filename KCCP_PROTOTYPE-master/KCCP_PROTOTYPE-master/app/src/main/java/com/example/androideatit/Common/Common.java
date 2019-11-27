package com.example.androideatit.Common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.example.androideatit.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Common {

    public static void setMyId(String myId) {
        MY_ID = myId;
    }

    private Common() {
    }

    public final static String CHAT_INFOMAION = "chat_contents";
    public final static String ROOM = "Room";
    public final static String SCRAP = "Scrap";

    public static ArrayList<String> downloadUrl = new ArrayList<>();

    private static final StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://kccp-a4bd9.appspot.com");

    private static String USER_NAME;

    private static String MY_ID;

    private static final int[] townImages = {R.id.dobong_image, R.id.nowon_image, R.id.dongdaemoon_image, R.id.enpyeong_image,
            R.id.joong_image, R.id.jongro_image, R.id.seodaemoon_image, R.id.seongbook_image,
            R.id.seongdong_image, R.id.guangjin_image, R.id.gangnam_image, R.id.gangbook_image,
            R.id.gangdong_image, R.id.gangseo_image, R.id.guanak_image, R.id.guro_image,
            R.id.geumcheon_image, R.id.dongjak_image, R.id.joonglang_image, R.id.mapo_image,
            R.id.seocho_image, R.id.songpa_image, R.id.yangcheon_image, R.id.yeongdeungpo_image,
            R.id.yongsan_image};

    private static final String[] townNames = {"도봉구", "노원구", "동대문구", "은평구", "중구", "종로구",
            "서대문구", "성북구", "성동구", "광진구", "강남구", "강북구", "강동구",
            "강서구", "관악구", "구로구", "금천구", "동작구", "중랑구", "마포구",
            "서초구", "송파구", "양천구", "영등포구", "용산구"};

    public static int[] getTownImages() {
        return townImages;
    }

    public static String[] getTownNames() {
        return townNames;
    }

    public static String getUserName() {
        return USER_NAME;
    }

    public static void setUserName(String userName) {
        USER_NAME = userName;
    }

    public static String getMyId() {
        return MY_ID;
    }

    public static DatabaseReference getDatabase() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public static DatabaseReference getDatabase(String ref) {
        return FirebaseDatabase.getInstance().getReference(ref);
    }

    // 전체 내용, 구별 될 내용, 구별자 / null 이면 해당 채팅방 아님
    public static String getOther(String all, String host, String distinction){
        String[] user = all.split(distinction);
        if(host.equals(user[0]) || host.equals(user[1]))
            return host.equals(user[0]) ? user[1] : user[0];
        return null;
    }


    // 두개의 스트링을 합치기
    public static String integrate(String hostName, String username) {
        return hostName.compareTo(username) > 0 ? hostName + ", " + username : username + ", " + hostName;
    }

    public static StorageReference getStorageRef() {
        return storageRef;
    }

    public static StorageReference getStorageRef(String child) {
        return storageRef.child(child);
    }

    public static String timeStamp(String user, String type) {
        return user + "_" + timeStamp() + type;
    }

    public static String timeStamp() {
        return new SimpleDateFormat("yyyyMMHH_mmss").format(new Date());
    }

    public static String chatTimeStamp() {
        return new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date(System.currentTimeMillis()));
    }






}
