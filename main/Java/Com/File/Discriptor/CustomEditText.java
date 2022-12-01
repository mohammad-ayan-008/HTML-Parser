package com.File.Discriptor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatEditText;

public class CustomEditText extends AppCompatEditText {
   private Rect rect;
    private Paint paint;
	
  public CustomEditText(Context c,AttributeSet abs){
	  super(c,abs);
	    rect = new Rect();
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTextSize(20);
        setHorizontallyScrolling(true);
        setMovementMethod(new ScrollingMovementMethod());		
        setLongClickable(true);
		setTextIsSelectable(true);
		setTextColor(Color.WHITE);
		
  }
  
 @Override
 protected void onDraw(Canvas arg0) {
	
	super.onDraw(arg0);
	int baseline = getBaseline();
        for (int i = 0; i < getLineCount(); i++) {
		     arg0.drawText(""+(i+1),rect.left,baseline,paint);
            baseline += getLineHeight();
        }
 }
	
}