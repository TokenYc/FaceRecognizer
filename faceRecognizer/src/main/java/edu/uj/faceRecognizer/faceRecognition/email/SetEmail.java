package edu.uj.faceRecognizer.faceRecognition.email;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import edu.uj.faceRecognizer.faceRecognition.utilities.AppPreferences;
import edu.uj.faceRecognizer.faceRecognition.R;

import java.util.regex.Pattern;

/**
 * User: piotrplaneta
 * Date: 16.01.2013
 * Time: 00:00
 */
public class SetEmail extends Activity {
    private static final String APP_SHARED_PREFS = "edu.uj.pmd.locationalarm_preferences";
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_email_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppPreferences prefs = new AppPreferences(this);
        if (prefs.getEmail() != "none") {
            ((EditText) findViewById(R.id.emailEditText)).setText(prefs.getEmail());
        }
    }

    private static final Pattern rfc2822 = Pattern.compile(
            "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$"
    );

    public void save(View view) {
        String email = ((EditText) findViewById(R.id.emailEditText)).getText().toString();
        if(!rfc2822.matcher(email).matches()) {
            Toast.makeText(this, "Insert proper email!", Toast.LENGTH_LONG).show();
        } else {
            AppPreferences prefs = new AppPreferences(this);
            prefs.setEmail(email);
            this.finish();
        }
    }
}