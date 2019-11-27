package com.example.taewoo.taewoojinwoo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SimpleDB {
    private static Map<String, ArticleVO> db = new HashMap<String, ArticleVO>();

    public  static void addArticle(String index, ArticleVO articleVO) {   //이 함수의 기능은 DB에 데이터를 집어 넣는 것이다.
        db.put(index, articleVO);
    }

    public  static ArticleVO getArticle(String index) {                   //이 함수의 기능은 DB에서 데이터를 가지고 오는 것이다.
        return db.get(index);
    }

    public static List<String> getIndexes(){
        Iterator<String> keys = db.keySet().iterator();  //db.keySet()을 함으로써 키의 집합들이 반환되고 그것에 대해 iterator메소드를 호출하면 해당 iterator를 사용할 수 있게끔 객체가 생성 및 초기화.

        List<String> keyList = new ArrayList<String>();
        String key="";
        while(keys.hasNext())  //다음 키가 존재한다면
        {
            key = keys.next(); //키를 저장.
            keyList.add(key); //저장한 키를 키리스트(어레이리스트)에 추가.
        }
        return  keyList;  //그리고 다 채워진 키 리스트를 반환함.
    }
}
