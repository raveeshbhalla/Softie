package in.raveesh.softie;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewTreeObserver;

public class Softie{
    private static final String TAG = "Softie";

    private HeightChangeListener mSoftieHeightListener;
    private Activity activity;
    private SharedPreferences mKeyboardHeightPreferences;

    public static Softie attach(Activity activity){
        return new Softie(activity);
    }

    private Softie(Activity activity) {
        this.activity = activity;
        final View contentView = activity.findViewById(android.R.id.content);
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private int mPreviousHeight;
            @Override
            public void onGlobalLayout() {
                int newHeight = contentView.getHeight();
                if (mPreviousHeight != 0) {
                    if (mPreviousHeight > newHeight) {
                        if (keyboardShownListener != null){
                            keyboardShownListener.shown();
                        }
                        if (mSoftieHeightListener != null) {
                            int keyboardHeight = mPreviousHeight - newHeight;
                            int storedKeyboardHeight = mKeyboardHeightPreferences.getInt("height", 0);
                            if (keyboardHeight != storedKeyboardHeight) {
                                mSoftieHeightListener.heightChanged(keyboardHeight);
                                mKeyboardHeightPreferences.edit().putInt("height", keyboardHeight).apply();
                            }
                        }

                    } else if (mPreviousHeight < newHeight) {
                        if (keyboardShownListener != null){
                            keyboardShownListener.hidden();
                        }
                    } else {
                        // No change
                    }
                }
                mPreviousHeight = newHeight;
            }
        });
    }

    KeyboardShownListener keyboardShownListener;

    public Softie setKeyboardShownListener(KeyboardShownListener listener){
        keyboardShownListener = listener;
        return this;
    }

    public interface KeyboardShownListener{
        void shown();
        void hidden();
    }

    public interface HeightChangeListener {
        void heightChanged(int height);
    }

    public Softie setHeightChangeListener(HeightChangeListener listener){
        mSoftieHeightListener = listener;
        mKeyboardHeightPreferences = activity.getSharedPreferences("softie", activity.MODE_PRIVATE);
        return this;
    }

}