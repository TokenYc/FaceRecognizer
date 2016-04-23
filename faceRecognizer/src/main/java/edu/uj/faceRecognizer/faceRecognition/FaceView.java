package edu.uj.faceRecognizer.faceRecognition;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import edu.uj.faceRecognizer.faceRecognition.utilities.ToastHelper;
import com.googlecode.javacpp.Pointer;
import com.googlecode.javacv.cpp.opencv_highgui.CvCapture;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;
import static com.googlecode.javacv.cpp.opencv_video.*;

import java.io.IOException;

import static com.googlecode.javacv.cpp.opencv_core.*;

class FaceView extends View implements Camera.PreviewCallback {
    private FaceRecognizer faceRecognizer;
    private String name="";


    public FaceView(Context context) {
        super(context);
        try {
            this.faceRecognizer = new FaceRecognizer(context);
        } catch (IOException e) {
            Log.e("faceRecognizer", "Error during facerecognizer initialization", e);
            ToastHelper.notify(context, "Error during faceRecognizer initialization");
        }

    }



    public void onPreviewFrame(final byte[] data, final Camera camera) {
        try {
            Camera.Size size = camera.getParameters().getPreviewSize();
            name=faceRecognizer.processImage(data, size.width, size.height);
            camera.addCallbackBuffer(data);
            postInvalidate();


        } catch (RuntimeException e) {
            // The camera has probably just been released, ignore.
        }
    }



    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(40);
        if (!name.equals("")){
            String s = "识别出身份为:"+name;
            float textWidth = paint.measureText(s);
            canvas.drawText(s, (getWidth() - textWidth) / 2, 60, paint);
        }
        if (faceRecognizer.getFaces() != null) {
            paint.setStrokeWidth(2);
            paint.setStyle(Paint.Style.STROKE);
            float scaleX = faceRecognizer.getScaleX(getWidth());
            float scaleY = faceRecognizer.getScaleY(getHeight());
            int total = faceRecognizer.getFaces().total();
            for (int i = 0; i < total; i++) {
                CvRect r = new CvRect(cvGetSeqElem(faceRecognizer.getFaces(), i));
                int x = r.x(), y = r.y(), w = r.width(), h = r.height();
                if(getWidth()-x*scaleX < getWidth()-(x+w)*scaleX) {
                    canvas.drawRect(getWidth()-x*scaleX, y*scaleY, getWidth()-(x+w)*scaleX, (y+h)*scaleY, paint);
                } else {
                    canvas.drawRect(getWidth()-(x+w)*scaleX, y*scaleY, getWidth()-x*scaleX, (y+h)*scaleY, paint);
                }
            }
        }
    }
}