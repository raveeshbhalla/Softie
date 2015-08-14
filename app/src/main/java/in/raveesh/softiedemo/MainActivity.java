package in.raveesh.softiedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import in.raveesh.softie.Softie;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Softie.attach(this)
                .setKeyboardShownListener(new Softie.KeyboardShownListener() {
                    @Override
                    public void shown() {
                        Toast.makeText(MainActivity.this, "Shown", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void hidden() {
                        Toast.makeText(MainActivity.this, "Hidden", Toast.LENGTH_SHORT). show();
                    }
                })
                .setHeightChangeListener(new Softie.HeightChangeListener() {
                    @Override
                    public void heightChanged(int height) {
                        Toast.makeText(MainActivity.this, "New height: " + height, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
