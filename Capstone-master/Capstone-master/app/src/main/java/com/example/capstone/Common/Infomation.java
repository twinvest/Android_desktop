package com.example.capstone.Common;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Infomation {

    public static final String ALBUM = "Album";
    public static final String storageUrl = "gs://capstone-843d1.appspot.com";

    // firebase 객체 생성
    private static final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private static final StorageReference storage = FirebaseStorage.getInstance().getReferenceFromUrl(storageUrl);

    // 스토리지 시작 위치 반환
    public static StorageReference getStorageRef() {
        return storage;
    }

    // 스토리지의 원하는 자식 위치를 반환
    public static StorageReference getStorageRef(String child) {
        return storage.child(child);
    }

    // 스토리지의 전체 앨범 위치를 반환
    public static StorageReference getAlbum(){
        return storage.child(ALBUM);
    }

    // 스토리지의 특정 인의 앨범 위치를 반환
    public static StorageReference getAlbum(String child){
        return storage.child(ALBUM).child(child);
    }

    public static DatabaseReference getAlbumData(){
        return database.child(ALBUM);
    }

    public static DatabaseReference getAlbumData(String child){
        return database.child(ALBUM).child(child);
    }

    public static void setMyId(String myId) {
        MY_ID = myId;
    }

    public final static String CHAT_INFOMAION = "chat_contents";

    public static ArrayList<String> downloadUrl = new ArrayList<>();

    private static String USER_NAME;

    private static String MY_ID;

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
