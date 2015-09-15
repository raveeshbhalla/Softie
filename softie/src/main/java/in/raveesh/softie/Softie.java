package in.raveesh.softie;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewTreeObserver;

public class Softie{
    private static final String TAG = "Softie";

    private HeightChangeListener heightChangeListener;
    private KeyboardShownListener keyboardShownListener;
    private static SharedPreferences sharedPreferences;

    public static Softie attach(Activity activity){
        return new Softie(activity);
    }

    private Softie(Activity activity) {
        sharedPreferences = activity.getSharedPreferences("softie", activity.MODE_PRIVATE);
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
                        if (heightChangeListener != null) {
                            int keyboardHeight = mPreviousHeight - newHeight;
                            int storedKeyboardHeight = sharedPreferences.getInt("height", 0);
                            if (keyboardHeight != storedKeyboardHeight) {
                                heightChangeListener.heightChanged(keyboardHeight);
                                sharedPreferences.edit().putInt("height", keyboardHeight).apply();
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
        heightChangeListener = listener;
        return this;
    }

    /**
     * Returns the height of the software keyboard
     * @param activity Activity in which we're looking
     * @return Height of keyboard (in px) or -1, if not already available
     */
    public static int getHeight(Activity activity){
        if (sharedPreferences == null){
            sharedPreferences = activity.getSharedPreferences("softie", activity.MODE_PRIVATE);
        }
        return sharedPreferences.getInt("height", -1);
    }

}