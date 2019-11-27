package com.example.androideatit.WantRoom;

public class TimeString {
    private static final int SEC = 60;
    private static final int MIN = 60;
    private static final int HOUR = 24;
    private static final int DAY = 30;
    private static final int MONTH = 12;

    public static String formatTimeString(long regTime){
        long curTime = System.currentTimeMillis();
        long diffTime = (curTime - regTime)/1000;
        String msg = null;

        if(diffTime < TimeString.SEC) msg = "방금";
        else if((diffTime /= TimeString.SEC) < TimeString.MIN) msg = diffTime +"분 전";
        else if((diffTime /= TimeString.MIN) < TimeString.HOUR) msg = diffTime +"시간 전";
        else if((diffTime /= TimeString.HOUR) < TimeString.DAY) msg = diffTime +"일 전";
        else if((diffTime /= TimeString.DAY) < TimeString.MONTH) msg = diffTime +"달 전";
        else msg = diffTime +"년 전";

        return msg;
    }
}