package edu.uj.faceRecognizer.faceRecognition.email;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import edu.uj.faceRecognizer.faceRecognition.MainActivity;

/**
 * User: piotrplaneta
 * Date: 15.01.2013
 * Time: 22:34
 */
public class SendEmailTask extends AsyncTask<Mail, Void, Void> {
    private Context context;
    private boolean result;

    @Override
    protected Void doInBackground(Mail... mails) {

        try {
            result = mails[0].send();
        } catch(Exception e) {
            result = false;
            Log.e("MailApp", "Could not send email", e);
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(result) {
            Toast.makeText(context, "Email was sent", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Email was not sent", Toast.LENGTH_LONG).show();
        }
        ((MainActivity) context).startPreview();
    }

    public SendEmailTask(Context context) {

        this.context = context;

    }
}
