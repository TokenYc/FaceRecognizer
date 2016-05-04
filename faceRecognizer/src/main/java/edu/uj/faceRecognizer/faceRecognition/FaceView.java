package edu.uj.faceRecognizer.faceRecognition;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
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
    public static final int LOAD_OVER=1;
    private FaceRecognizer faceRecognizer;
    private ProgressDialog progressDialog;
    //    private JavaCVCamshift javaCVCamshift;
    private String name = "";
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOAD_OVER:
                    progressDialog.dismiss();
                    break;
            }
        }
    };


    public FaceView(Context context) {
        super(context);
        try {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("正在加载");
            progressDialog.show();
            this.faceRecognizer = new FaceRecognizer(context,handler);
//            javaCVCamshift = new JavaCVCamshift();
        } catch (IOException e) {
            Log.e("faceRecognizer", "Error during facerecognizer initialization", e);
            ToastHelper.notify(context, "Error during faceRecognizer initialization");
        }

    }


    public void onPreviewFrame(final byte[] data, final Camera camera) {
        try {
            Camera.Size size = camera.getParameters().getPreviewSize();
            name = faceRecognizer.processImage(data, size.width, size.height);
            if (faceRecognizer.getFaces()!=null&&faceRecognizer.getFaces().total()>0) {
                CvRect selection = new CvRect(cvGetSeqElem(faceRecognizer.getFaces(), 0));
//                javaCVCamshift.processImage(selection, data, size.width, size.height);
            }
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
        if (!name.equals("")) {
            String s = "识别出身份为:" + name;
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
                if (getWidth() - x * scaleX < getWidth() - (x + w) * scaleX) {
                    canvas.drawRect(getWidth() - x * scaleX, y * scaleY, getWidth() - (x + w) * scaleX, (y + h) * scaleY, paint);
                } else {
                    canvas.drawRect(getWidth() - (x + w) * scaleX, y * scaleY, getWidth() - x * scaleX, (y + h) * scaleY, paint);
                }
            }
        }
//        if (javaCVCamshift.getTrack_window() != null) {
//            CvRect r = javaCVCamshift.getTrack_window();
//            float scaleX = faceRecognizer.getScaleX(getWidth());
//            float scaleY = faceRecognizer.getScaleY(getHeight());
//            int x = r.x(), y = r.y(), w = r.width(), h = r.height();
//            if (getWidth() - x * scaleX < getWidth() - (x + w) * scaleX) {
//                canvas.drawRect(getWidth() - x * scaleX, y * scaleY, getWidth() - (x + w) * scaleX, (y + h) * scaleY, paint);
//            } else {
//                canvas.drawRect(getWidth() - (x + w) * scaleX, y * scaleY, getWidth() - x * scaleX, (y + h) * scaleY, paint);
//            }
//        }
    }
}