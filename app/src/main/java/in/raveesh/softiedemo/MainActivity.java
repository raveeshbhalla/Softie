package in.raveesh.softiedemo;

import android.app.Service;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import in.raveesh.softie.Softie;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RelativeLayout root = (RelativeLayout) findViewById(R.id.root);
        InputMethodManager im = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        final Softie softie = new Softie(root, im);
        softie.setSoftKeyboardCallback(new Softie.SoftKeyboardChanged() {
            @Override
            public void onSoftKeyboardHide() {
                Log.d(TAG, "Keyboard hidden");
                Snackbar.make(root, "Keyboard hidden", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Show", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                softie.show();
                            }
                        }).show();

            }

            @Override
            public void onSoftKeyboardShow() {
                Log.d(TAG, "Keyboard shown");
                Snackbar.make(root, "Keyboard shown", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Hide", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                softie.hide();
                            }
                        }).show();
            }
        });

        softie.setHeightObtainedListener(this, new Softie.SoftieHeightListener() {
            @Override
            public void heightChanged(int height) {
                Log.d(TAG, "Height obtained: "+height);
            }
        });
    }
}
