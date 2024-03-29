package hu.festivalplum.utils;

/**
 * Created by viktor on 2015.03.29..
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SectionIndexer;

public class SideBar extends View {
    private char[] l;
    private SectionIndexer sectionIndexter = null;
    private AdapterView view;
    private int m_nItemHeight = 29;
    private int textSize = 20;
    public SideBar(Context context) {
        super(context);
        init();
        calculate();
    }
    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        calculate();
    }
    private void init() {
        //l = new char[] { 'A', 'Á', 'B', 'C', 'D', 'E', 'É', 'F', 'G', 'H', 'I', 'Í', 'J', 'K', 'L', 'M', 'N', 'O', 'Ó', 'Ö', 'Ó', 'P', 'Q', 'R', 'S', 'T', 'U', 'Ú', 'Ü', 'Ű', 'V', 'W', 'X', 'Y', 'Z' };
        l = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
        setBackgroundColor(0x44FFFFFF);
    }

    private void calculate(){
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        Double h = metrics.heightPixels * 0.7 / l.length;
        m_nItemHeight = h.intValue();
        textSize = m_nItemHeight - 10;
    }

    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        calculate();
    }

    public void setView(AdapterView view) {
        this.view = view;
        sectionIndexter = (SectionIndexer) view.getAdapter();
    }

    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int i = (int) event.getY();
        int idx = i / m_nItemHeight;
        if (idx >= l.length) {
            idx = l.length - 1;
        } else if (idx < 0) {
            idx = 0;
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            if (sectionIndexter == null) {
               sectionIndexter = (SectionIndexer) view.getAdapter();

            }
            int position = sectionIndexter.getPositionForSection(l[idx]);
            if (position == -1) {
                return true;
            }
            view.setSelection(position);
        }
        return true;
    }

    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(0xFFA6A9AA);
        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.CENTER);
        float widthCenter = getMeasuredWidth() / 2;
        for (int i = 0; i < l.length; i++) {
            canvas.drawText(String.valueOf(l[i]), widthCenter, m_nItemHeight + (i * m_nItemHeight), paint);
        }
        super.onDraw(canvas);
    }
}
