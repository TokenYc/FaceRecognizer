package edu.uj.faceRecognizer.faceRecognition;

import android.*;
import android.R;
import com.googlecode.javacv.cpp.opencv_core;

/**
 * User: piotrplaneta
 * Date: 20.01.2013
 * Time: 20:55
 */
public class ImageUtilities {

    public static void getResizedFragment(opencv_core.CvRect rect) {
        float aspectRatio = (float) (4.0 / 3.0);

        float h = rect.width() / aspectRatio;
        if (rect.height() > h) {
            float w = rect.height() * aspectRatio;
            float diff = w - rect.width();

            rect.width(Math.round(w));
            rect.x(rect.x() - Math.round(diff/2));
        } else {
            float diff = h - rect.height();
            rect.height(Math.round(h));
            rect.y(rect.y() - Math.round(diff/2));
        }
    }

    public static boolean isRegionValid(opencv_core.CvRect region, int imageWidth, int imageHeight) {
        if (region.x() < 0 || region.y() < 0) {
            return false;
        }

        if (region.x() + region.width() > imageWidth || region.y() + region.height() > imageHeight) {
            return false;
        }
        return true;
    }
}
