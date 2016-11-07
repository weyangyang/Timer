package com.qsvxin.view.slideview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.qsvxin.bean.BaseListViewItem;

public class ListViewCompat extends ListView {
    private SlideView mFocusedItemView;

    public ListViewCompat(Context context) {
        super(context);
    }

    public ListViewCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewCompat(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void shrinkListItem(int position) {
        View item = getChildAt(position);

        if (item != null) {
            try {
                ((SlideView) item).shrink();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    private int downX;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
	        case MotionEvent.ACTION_DOWN: {
	            int x = (int) event.getX();
	            int y = (int) event.getY();
	            downX = x;
	            int position = pointToPosition(x, y);
	            if (position != INVALID_POSITION) {
	            	BaseListViewItem data = (BaseListViewItem) getItemAtPosition(position);
	                mFocusedItemView = data.slideView;
	            }
	        }
        }

        if (mFocusedItemView != null) {
        	int position = pointToPosition((int)event.getX(), (int)event.getY());
            if (position != INVALID_POSITION) {
            	mFocusedItemView.onRequireTouchEvent(event);
            }
        }
        
        if (event.getAction() == MotionEvent.ACTION_UP) {
        	if (event.getX() - downX > 40 ||
        			event.getX() - downX < -40) {
//        		return false;
        	}
        }

        return super.onTouchEvent(event);
    }

}
