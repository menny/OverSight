package net.evendanan.oversight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 */
public class OverSight {

    public static interface OnOverSightVisibilityChanged {
        void onOverSightVisibilityChanged(OverSight overSight, boolean shown);
    }

    private PopupWindow mPopup;
    private Context mContext;

    private View mAnchor;
    private OnOverSightVisibilityChanged mListener;

    public OverSight(Context context, int style) {
        mContext = context;
        mPopup = new PopupWindow(context, null, style);
        mPopup.setFocusable(false);
    }

    public OverSight(Context context) {
        this(context, R.style.OverSight_ToolTip);
    }

    public void attach(View anchor, View clickable, OnOverSightVisibilityChanged visibilityListener) {
        if (anchor == null) {
            throw new IllegalArgumentException("anchor argument can not be null!");
        }
        mAnchor = anchor;
        mListener = visibilityListener;

        if (clickable == null)
            clickable = anchor;//no clickable view was provided, so I'll use the anchor

        clickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopup.isShowing())
                    hide();
                else
                    show();
            }
        });
    }

    public void setContentView(int layoutResourceId) {
        View content = LayoutInflater.from(mContext).inflate(layoutResourceId, null);
        setContentView(content);
    }

    public void setContentView(View contentView) {
        mPopup.setContentView(contentView);
    }

    public void show() {
        if (mAnchor == null)
            throw new NullPointerException("No anchor was specified! First call attach.");

        if (mPopup.isShowing()) {
            mPopup.dismiss();
        }

        mPopup.showAsDropDown(mAnchor, 0, 0);
        mPopup.update(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if (mListener != null)
            mListener.onOverSightVisibilityChanged(this, true);
    }

    public void hide() {
        if (mPopup.isShowing()) {
            mPopup.dismiss();
            if (mListener != null)
                mListener.onOverSightVisibilityChanged(this, false);
        }
    }

    public boolean isShowing() {
        return mPopup.isShowing();
    }

    public void close() {
        mPopup.dismiss();
        //just making sure we are not holding anything.
        mPopup = null;
        mContext = null;
        mListener = null;
        mAnchor = null;
    }
}