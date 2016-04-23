package edu.uj.faceRecognizer.faceRecognition;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PersonActivity extends Activity {

    private static int counter = -1;
    private static String personName;
    private static final int CAMERA_PIC_REQUEST = 1337;
    private static final int CROP_REQ = 1333;
    private File storageDir;
    private static File[] takenPhotoFiles = new File[3];
    private static int restore = 0;
    private static Button back;
    private static Button save;
    private static EditText field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_activity);

        back = (Button)findViewById(R.id.buttonBack);
        back.setEnabled(false);
        save = (Button)findViewById(R.id.buttonTwo);

        field = (EditText)findViewById(R.id.personEditText);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //setContentView(R.layout.person_activity2);


        storageDir = new File(
                Environment.getExternalStorageDirectory(), "faceRecognizer"
        );

    }

    public void smile() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        try {

            counter = counter + 1;


            takenPhotoFiles[counter] = createImageFile();


            System.out.println("Counter ====> " + counter);
            Log.i("faceRecognizer", String.valueOf(counter));
            Log.i("faceRecognizer", takenPhotoFiles[counter].getAbsolutePath());
            for (File f : takenPhotoFiles) {
                System.out.println("FILE ---> " + f);
            }
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(takenPhotoFiles[counter]));
            cameraIntent.putExtra("idx", counter);

            startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

            System.out.println("Photo Taken!!!");

        } catch (IOException e) {
            System.out.println("Epic fail!");
            e.printStackTrace();
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {
            if(resultCode == Activity.RESULT_OK) {
                System.out.println("CHECKED!");

                //System.out.println("EXTRA! " + data.getStringExtra("idx"));

                Log.i("faceRecognizer", takenPhotoFiles.toString());
                Intent intent = new Intent("com.android.camera.action.CROP");
                //System.out.println(.getAbsolutePath());
                intent.setDataAndType(Uri.fromFile(takenPhotoFiles[restore]), "image/*");
                intent.putExtra("crop", "true");
                // this defines the aspect ration
                intent.putExtra("aspectX", 4);
                intent.putExtra("aspectY", 3);
                // this defines the output bitmap size
                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 150);
                // true to return a Bitmap, false to directly save the cropped iamge
                intent.putExtra("return-data", false);
                //save output image in uri
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(takenPhotoFiles[restore]));
                startActivity(intent);
                restore += 1;

                System.out.println("[RESTORE] " + restore);
                if (restore > 2) {
                    back.setEnabled(true);
                    save.setEnabled(false);
                    field.setEnabled(false);
                    counter = -1;
                    takenPhotoFiles = new File[3];
                    restore = 0;
                }

            } else {
                takenPhotoFiles[counter].delete();
                counter = -1;
                takenPhotoFiles = new File[3];
                restore = 0;
            }
        } else {

            System.out.println("Hello:)");
        }
    }

    public void savePerson(View view) {
        EditText editText = (EditText) findViewById(R.id.personEditText);
        String message = editText.getText().toString();
        personName = message;

        for (int i=0; i<3; i++) {
            smile();
        }
    }

    public void backToMenu(View view) {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("faceRecognizer", "ondestoroy");
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = personName + "-" + timeStamp;
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        String mCurrentPhotoPath = image.getAbsolutePath();
        System.out.println("path: " + mCurrentPhotoPath);
        return image;
    }
}