package com.example.taewoo.viewpager_test;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.PermissionChecker;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.yongbeam.y_photopicker.util.photopicker.PhotoPickerActivity;
import com.yongbeam.y_photopicker.util.photopicker.utils.YPhotoPickerIntent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    int PICK_IMAGE_MULTIPLE = 1;

    int PICK_IMAGE_SAMSUNG = 2;
    final int REQUEST_TAKE_ALBUM = 1;
    final int REQUEST_CODE = 1;
    ImageView image_preview;
    ImageButton button_choice;
    Button button_upload;
    EditText title;
    EditText content;
    ViewPager pager;
    CustomAdapter adapter;
    ArrayList imageList;
    ArrayList<Uri> uris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //image_preview = (ImageView) findViewById(R.id.preview);            //미리보기
        button_choice = (ImageButton) findViewById(R.id.camera_connect);  //사진선택
        button_upload = (Button) findViewById(R.id.bt_upload);            //이거 클릭시 데이터 베이스에 업로드
        title = (EditText) findViewById(R.id.EditText_title);
        content = (EditText) findViewById(R.id.EditText_content);
        pager = (ViewPager) findViewById(R.id.pager);
        //ViewPager에 설정할 Adapter 객체 생성
        //ListView에서 사용하는 Adapter와 같은 역할.
        //다만. ViewPager로 스크롤 될 수 있도록 되어 있다는 것이 다름
        //PagerAdapter를 상속받은 CustomAdapter 객체 생성
        //CustomAdapter에게 LayoutInflater 객체 전달
        //adapter = new CustomAdapter(getLayoutInflater());
        //ViewPager에 Adapter 설정
        //pager.setAdapter(adapter);

        button_choice.setOnClickListener(new View.OnClickListener() { //버튼 클릭했을시 사진첩 접근
            @Override
            public void onClick(View view) {
                getAlbum();
            }
        });
    }


    public void getAlbum() {
        //앨범호출
        Intent intent = new Intent("android.intent.action.MULTIPLE_PICK");//("Intent.ACTION_GET_CONTENT);
        // intent.addCategory(Intent.CATEGORY_OPENABLE); // 생략해도 됨 - 삼성 갤럭시S3 테스트
        intent.setType("image/*"); // 생략하면 아래 검사 무의미 > else { ... 구문으로 넘어감
        // Check to see if it can be handled...
        PackageManager manager = getApplicationContext().getPackageManager();
        List<ResolveInfo> infos = manager.queryIntentActivities(intent, 0);
        if (infos.size() > 0) {
            Log.e("FAT=","삼성폰");
            // intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); // 삼성폰 - 생략해도 됨
            // createChooser 실행해도 Intent가 1개 뿐이면 통과 > 이미지 리스트가 바로 열림
            startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_SAMSUNG);
        }
        else {
            Log.e("FAT=", "일반폰");
            // intent.addCategory(Intent.CATEGORY_OPENABLE); // 없어도 됨 - 엘지 G2 테스트
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); // 일반폰 - 반드시 있어야 다중선택 가능
            intent.setAction(Intent.ACTION_PICK); // ACTION_GET_CONTENT 사용불가 - 엘지 G2 테스트
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 출처 : http://coder-jeff.blogspot.kr/2016/05/how-to-pick-multiple-files-from.html
        // 수정 : NullPointerException 해결 - (1) 작업취소 (2) 삼성폰 확인
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        // if (data.getAction().equals("android.intent.action.MULTIPLE_PICK")) { // NullPointerException
        if (requestCode == PICK_IMAGE_SAMSUNG) {
            final Bundle extras = data.getExtras();
            int count = extras.getInt("selectedCount");
            Object items = extras.getStringArrayList("selectedItems");
            // do somthing
            adapter = new CustomAdapter(getLayoutInflater(), items);
            pager.setAdapter(adapter);
            Log.e("FAT=", "삼성폰태우 : "+items.toString());
        }
        else {
            if (data != null && data.getData() != null) {
                Uri uri = data.getData();
                // do somthing
                Log.e("FAT=", "일반폰/단일 : "+uri.toString());
            }
            else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ClipData clipData = data.getClipData();
                    if (clipData != null) {
                        uris = new ArrayList<>();   //여기에 Uri들이 담긴다!!
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            ClipData.Item item = clipData.getItemAt(i);
                            Uri uri = item.getUri();
                            Log.e("FAT=", "일반폰/다중 : "+uri.toString());
                            uris.add(uri);
                        }
                        // Do someting
                        //Intent intent = new Intent(this, ImagesView.class);
                        //intent.putExtra("list", uris);
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
