package com.example.taewoo.taewoojinwoo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //액티비티가 처음 생성될 때 호출
        setContentView(R.layout.activity_main); // 화면을 표시해주는 메서드 setContentView. 이 메서드가 기본적으로 작성되어 있는 것을 볼 수 있다. 그래서 activity_main.xml 이 표시되는 것.

        prepareSimpleDB(); // 태우가 짠 함수 밑에 정의 해놓음.

        LinearLayout li = (LinearLayout)findViewById(R.id.itemList); // li라는 참조변수에 itemList를 찾아서 저장.

        for(int i=0; i<SimpleDB.getIndexes().size(); i++){          //즉, 여기서 SimpleDB.getIndexes().size()는 키리스트의 사이즈를 반환함.
            Button button = new AppCompatButton(this);     //버튼 생성
            button.setText(SimpleDB.getIndexes().get(i));            //키리스트를 받아와서 get(i)메소드를 통해 반환 되는 글을 버튼텍스트 부분에 넣는다.
            button.setOnClickListener(new View.OnClickListener() {     //클릭했을 때 Detail을 위해 추가한 부분
                public void onClick(View v) {                      //버튼 리스너 작동
                    Intent intent = new Intent(v.getContext(), DetailActivity.class); //버튼 눌렸을 때 DetailActivity.class실행
                    String buttonText = (String)((Button) v).getText();                // 버튼에 텍스트를 얻어와 buttonText에 저장.
                    intent.putExtra("key", buttonText);                    //액티비티 이동과 동시에 이전 액티비티에서 이동하는 액티비티로 어떤 값을 넘기고 싶다면 Intent 안에 있는 putExtra 함수를 호출
                    startActivity(intent);                                         // 두번째 액티비티로 실제 넘어감!!
                }
                });
            li.addView(button);
        }
    }
    private void prepareSimpleDB(){   //이 함수의 기능은 SimpleDB클래스의 addArticle함수를 50번 호출한다. addArticle에 대한 설명은 SimpleDB로 이동해서 볼 것!!
        for(int i=1; i<50; i++) {
           SimpleDB.addArticle(i+"번글", new ArticleVO(i, i+"번글 제목", i+"번글 내용", "김태우"));
        }
    }
}
