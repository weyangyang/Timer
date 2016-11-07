package com.qsvxin.view.slideview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewDebug.ExportedProperty;
import android.widget.TextView;

public class RunTextView extends TextView{

	public RunTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	@ExportedProperty(category = "focus")
	public boolean isFocused() {
		return true;
	}
}
