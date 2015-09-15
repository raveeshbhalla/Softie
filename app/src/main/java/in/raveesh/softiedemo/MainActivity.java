package in.raveesh.softiedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import in.raveesh.softie.Softie;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView keyboardState = (TextView) findViewById(R.id.keyboardState);
        final TextView keyboardHeight = (TextView) findViewById(R.id.keyboardHeight);
        final FrameLayout bottomTray = (FrameLayout) findViewById(R.id.bottomTray);

        keyboardHeight.setText("Keyboard height: " + Softie.getHeight(this) + "px");
        updateBottomTrayHeight(bottomTray, Softie.getHeight(this));

        Softie.attach(this)
                .setKeyboardShownListener(new Softie.KeyboardShownListener() {
                    @Override
                    public void shown() {
                        keyboardState.setText("Keyboard Shown");
                        updateBottomTrayHeight(bottomTray, 0);
                    }

                    @Override
                    public void hidden() {
                        keyboardState.setText("Keyboard Hidden");
                        updateBottomTrayHeight(bottomTray, Softie.getHeight(MainActivity.this));
                    }
                })
                .setHeightChangeListener(new Softie.HeightChangeListener() {
                    @Override
                    public void heightChanged(int height) {
                        keyboardHeight.setText("Keyboard height: " + height + "px");
                        updateBottomTrayHeight(bottomTray, height);
                    }
                });

    }

    private void updateBottomTrayHeight(FrameLayout tray, int height) {
        ViewGroup.LayoutParams params = tray.getLayoutParams();
        if (height != -1) {
            params.height = height;
        } else {
            params.height = dpToPx(250);
        }
        tray.setLayoutParams(params);
    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}
