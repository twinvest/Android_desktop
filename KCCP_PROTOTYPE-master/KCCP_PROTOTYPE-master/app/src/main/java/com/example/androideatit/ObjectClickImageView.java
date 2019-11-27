package com.example.androideatit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ObjectClickImageView extends AppCompatImageView { // or ImageView

    private OnTouchListener mOnTouchListener;
    private OnClickListener mOnObjectClickListener;

    public ObjectClickImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        super.setOnTouchListener(mOnObjectTouchListener);
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        mOnTouchListener = l;
    }

    public void setOnObjectClickListener(@Nullable OnClickListener l) {
        mOnObjectClickListener = l;
    }

    private final OnTouchListener mOnObjectTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            boolean consumed = false;

            if (mOnTouchListener != null)
                consumed = mOnTouchListener.onTouch(v, event);

            if (consumed)
                return true;

            if (mOnObjectClickListener != null) {
                int x = (int)event.getX();
                int y = (int)event.getY();

                switch(event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        if (isOnObject(x, y))
                            return true;

                        break;

                    case MotionEvent.ACTION_DOWN:
                        if (isOnObject(x, y)) {
                            mOnObjectClickListener.onClick(v);
                            return true;
                        }

                        break;
                }
            }

            return false;
        }
    };

    private boolean isOnObject(int x, int y) {
        setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(getDrawingCache());
        setDrawingCacheEnabled(false);

        if (bitmap == null)
            return false;

        if (Color.alpha(bitmap.getPixel(x, y)) > 0)
            return true;

        return false;
    }
}
