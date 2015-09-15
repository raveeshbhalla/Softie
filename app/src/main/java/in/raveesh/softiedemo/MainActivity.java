package in.raveesh.softiedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import in.raveesh.softie.Softie;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView keyboardState = (TextView)findViewById(R.id.keyboardState);
        final TextView keyboardHeight = (TextView)findViewById(R.id.keyboardHeight);

        keyboardHeight.setText("Keyboard height: "+Softie.getHeight(this)+"px");

        Softie.attach(this)
                .setKeyboardShownListener(new Softie.KeyboardShownListener() {
                    @Override
                    public void shown() {
                        keyboardState.setText("Keyboard Shown");
                    }

                    @Override
                    public void hidden() {
                        keyboardState.setText("Keyboard Hidden");
                    }
                })
                .setHeightChangeListener(new Softie.HeightChangeListener() {
                    @Override
                    public void heightChanged(int height) {
                        keyboardHeight.setText("Keyboard height: "+height+"px");
                    }
                });
    }
}
