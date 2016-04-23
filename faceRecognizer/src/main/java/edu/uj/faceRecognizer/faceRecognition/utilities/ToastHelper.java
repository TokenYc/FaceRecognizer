package edu.uj.faceRecognizer.faceRecognition.utilities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * User: piotrplaneta
 * Date: 21.01.2013
 * Time: 22:15
 */
public class ToastHelper {
    private static String lastMessage="";

    private static int sameMessageCounter = 0;
    private static int differentMessageCounter = 0;
    private static Toast lastToast = null;

    public static void notify(Context context, String message) {
        if(message != lastMessage) {
            if (differentMessageCounter % 5 == 0) {
                lastToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                //lastToast.show();
            }
        } else {
            if(sameMessageCounter++ % 10 == 0) {
                lastToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                //lastToast.show();
            }
        }

        Log.i("faceRecognizer", message);
        lastMessage = message;
    }


}
