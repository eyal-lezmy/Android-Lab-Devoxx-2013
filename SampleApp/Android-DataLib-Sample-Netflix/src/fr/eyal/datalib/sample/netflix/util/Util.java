package fr.eyal.datalib.sample.netflix.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Util {

	public static Bitmap scaleBitmap(Bitmap unscaledBitmap, float dstWidth, float dstHeight, ScalingLogic scalingLogic) {
	    Rect srcRect = calculateSrcRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstWidth, dstHeight, scalingLogic);
	    Rect dstRect = calculateDstRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstWidth, dstHeight, scalingLogic);
	    Bitmap scaledBitmap = Bitmap.createBitmap(dstRect.width(), dstRect.height(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(scaledBitmap);
	    canvas.drawBitmap(unscaledBitmap, srcRect, dstRect, new Paint(Paint.FILTER_BITMAP_FLAG));

	    return scaledBitmap;
	}
	
	public static Rect calculateSrcRect(int srcWidth, int srcHeight, float dstWidth, float dstHeight, ScalingLogic scalingLogic) {
	    if (scalingLogic == ScalingLogic.CROP) {
	        final float srcAspect = (float)srcWidth / (float)srcHeight;
	        final float dstAspect = dstWidth / dstHeight;

	        if (srcAspect > dstAspect) {
	            final int srcRectWidth = (int)(srcHeight * dstAspect);
	            final int srcRectLeft = (srcWidth - srcRectWidth) / 2;
	            return new Rect(srcRectLeft, 0, srcRectLeft + srcRectWidth, srcHeight);
	        } else {
	            final int srcRectHeight = (int)(srcWidth / dstAspect);
	            final int scrRectTop = (int)(srcHeight - srcRectHeight) / 2;
	            return new Rect(0, scrRectTop, srcWidth, scrRectTop + srcRectHeight);
	        }
	    } else {
	        return new Rect(0, 0, srcWidth, srcHeight);
	    }
	}

	public static Rect calculateDstRect(int srcWidth, int srcHeight, float dstWidth, float dstHeight, ScalingLogic scalingLogic) {
	    if (scalingLogic == ScalingLogic.FIT) {
	        final float srcAspect = (float)srcWidth / (float)srcHeight;
	        final float dstAspect = dstWidth / dstHeight;

	        if (srcAspect > dstAspect) {
	            return new Rect(0, 0, (int)dstWidth, (int)(dstWidth / srcAspect));
	        } else {
	            return new Rect(0, 0, (int)(dstHeight * srcAspect), (int)dstHeight);
	        }
	    } else {
	        return new Rect(0, 0, (int)dstWidth, (int)dstHeight);
	    }
	}
	

	public enum ScalingLogic {
		CROP, FIT
	}

}
