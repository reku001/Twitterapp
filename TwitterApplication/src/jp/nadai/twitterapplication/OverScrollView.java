package jp.nadai.twitterapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
 
// カスタムリストビュークラス
public class OverScrollView extends ListView {
 
    // オーバースクロールするY軸方向の最大値
    private int maxOverScrollY = 0;
 
    public OverScrollView(Context context){
 	super(context);
    }
 
    public OverScrollView(Context context, AttributeSet attrs) {
	super(context, attrs);
        // XMLファイルから最大値を取得する
	this.maxOverScrollY = attrs.getAttributeIntValue(null, "maxOverScrollY", 0);
    }
 
    public OverScrollView(Context context, AttributeSet attrs, int defStyle){
	super(context, attrs, defStyle);
	// XMLファイルから最大値を取得する
	this.maxOverScrollY = attrs.getAttributeIntValue(null, "maxOverScrollY", 0);
    }
 
    @Override
    protected boolean overScrollBy(
	int deltaX,
	int deltaY,
	int scrollX,
	int scrollY,
	int scrollRangeX,
	int scrollRangeY,
	int maxOverScrollX,
	int maxOverScrollY,
	boolean isTouchEvent){
 
	return super.overScrollBy(
	    deltaX,
	    deltaY,
	    scrollX,
	    scrollY,
	    scrollRangeX,
	    scrollRangeY,
	    maxOverScrollX,
	    this.maxOverScrollY,
	    isTouchEvent
	);
    }
}