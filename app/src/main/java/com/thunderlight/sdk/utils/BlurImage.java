package com.thunderlight.sdk.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by Choota on 1/9/17.
 */

public class BlurImage {
    private static float BITMAP_SCALE = 0.4f;
    private static float BLUR_RADIUS = 7.5f;

    private static Context thiscontext;
    private static BlurImage blurImage;

    private static Bitmap blurFillImage;


    public static BlurImage withContext(Context context) {
        thiscontext = context;
        return (blurImage = new BlurImage());
    }

    public static BlurImage setBlurRadius(float radius) {
        BLUR_RADIUS = radius;
        return blurImage;
    }

    public static BlurImage setBitmapScale(float scale) {
        BITMAP_SCALE = scale;
        return blurImage;
    }

    public static BlurImage blurFromResource(int resource) {
        Bitmap bitmap = BitmapFactory.decodeResource(thiscontext.getResources(), resource);
        blur(bitmap);
        return blurImage;
    }

    public static BlurImage blurFromUri(String imageUrl) {

//        URL url = null;
//        try {
//            url = new URL(imageUrl);
//            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Picasso.with(thiscontext)
                .load(imageUrl)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        BlurImage.blur(bitmap);
                        System.out.println("Test on load");
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        System.out.println("Test on fail");
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        System.out.println("Test on pre load");
                    }
                });

        return blurImage;
    }

    public static void into(ImageView imageView) {
        imageView.setBackground(new BitmapDrawable(thiscontext.getResources(), blurFillImage));
    }

    public static BlurImage blur(Bitmap image) {
        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript rs = RenderScript.create(thiscontext);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        blurFillImage = outputBitmap;

        return blurImage;
    }

    //*********************************************** 14020107
    public static Bitmap blur(Context context, View v, int src) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), src);

        return blur(context, getScreenshot(v, bitmap));
    }

    private static Bitmap blur(Context ctx, Bitmap image) {
        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript rs = RenderScript.create(ctx);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }

    private static Bitmap getScreenshot(View v, Bitmap bitmap) {
        Bitmap b = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }
//------------------------------------------------------------

    public static Bitmap blurBitmap(Context context, Bitmap bitmap) {

        //Let's create an empty bitmap with the same size of the bitmap we want to blur
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());

        //Instantiate a new Renderscript
        RenderScript rs = RenderScript.create(context.getApplicationContext());

        //Create an Intrinsic Blur Script using the Renderscript
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        //Create the in/out Allocations with the Renderscript and the in/out bitmaps
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

        //Set the radius of the blur
        blurScript.setRadius(15.f);

        //Perform the Renderscript
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);

        //Copy the final bitmap created by the out Allocation to the outBitmap
        allOut.copyTo(outBitmap);

        //recycle the original bitmap
        bitmap.recycle();

        //After finishing everything, we destroy the Renderscript.
        rs.destroy();

        return outBitmap;
    }

    public static Bitmap blurBitmap1(Context context, Bitmap bitmap) {

        //Let's create an empty bitmap with the same size of the bitmap we want to blur
        // Load the bitmap
        // Create a new bitmap with the same dimensions as the original
        Bitmap blurredBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());

        //Instantiate a new Renderscript
        RenderScript rs = RenderScript.create(context.getApplicationContext());

        //Create an Intrinsic Blur Script using the Renderscript
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        // Create a canvas to draw on the new bitmap
        Canvas canvas = new Canvas(blurredBitmap);

        // Draw the original bitmap on the canvas
        canvas.drawBitmap(bitmap, 0, 0, null);

        // Create a Paint object to define the blur effect
        Paint blurPaint = new Paint();
        blurPaint.setAntiAlias(true);
        blurPaint.setFilterBitmap(true);

        // Define the blur radius
        float blurRadius = 25f;

// Create a BitmapShader to use as the blur mask
        BitmapShader blurMask = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

// Create a new Paint object to use as the blur filter
        Paint blurFilter = new Paint();
        blurFilter.setShader(blurMask);
        blurFilter.setMaskFilter(new BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL));

// Define the center of the circle
        float centerX = bitmap.getWidth() / 2f;
        float centerY = bitmap.getHeight() / 2f;

// Define the radius of the circle
        float radius = Math.min(centerX, centerY) * 0.75f;

// Draw the blurred circle on the canvas
        canvas.drawRect(centerX, centerY, centerX, centerY, blurFilter);

// Create a new Paint object to draw the border
        Paint borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(20f);
        borderPaint.setColor(Color.WHITE);

// Draw the border around the circle
        canvas.drawRect(centerX, centerY, centerX, centerY, borderPaint);

// Set the blurred bitmap as the image for your ImageView

        return blurredBitmap;
    }

    public static Bitmap blurBitmap2(Context context, Bitmap bitmap) {
        //Let's create an empty bitmap with the same size of the bitmap we want to blur
// Create a new bitmap with the same dimensions as the original
        Bitmap blurredBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());

// Create a canvas to draw on the new bitmap
        Canvas canvas = new Canvas(blurredBitmap);

// Draw the original bitmap on the canvas
        canvas.drawBitmap(bitmap, 0, 0, null);

// Create a Paint object to define the blur effect
        Paint blurPaint = new Paint();
        blurPaint.setAntiAlias(true);
        blurPaint.setFilterBitmap(true);

// Define the blur radius
        float blurRadius = 25f;

// Create a BitmapShader to use as the blur mask
        BitmapShader blurMask = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

// Create a new Paint object to use as the blur filter
        Paint blurFilter = new Paint();
        blurFilter.setShader(blurMask);
        blurFilter.setMaskFilter(new BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL));

// Define the center of the circle
        float centerX = bitmap.getWidth() / 2f;
        float centerY = bitmap.getHeight() / 2f;

// Define the radius of the circle
        float radius = Math.min(centerX, centerY) * 0.75f;

// Draw the blurred circle on the canvas
        canvas.drawCircle(centerX, centerY, radius, blurFilter);

// Create a new Paint object to apply a color filter to the blurred circle
        Paint colorPaint = new Paint();
        colorPaint.setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY));

// Draw the white color on top of the blurred circle
        canvas.drawCircle(centerX, centerY, radius, colorPaint);

// Set the blurred bitmap as the image for your ImageView

        return blurredBitmap;
    }

    public static Bitmap blurBitmap3(Bitmap bitmap) {

// Create a new bitmap with the same dimensions as the original
        Bitmap blurredBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());

// Create a canvas to draw on the new bitmap
        Canvas canvas = new Canvas(blurredBitmap);

// Draw the original bitmap on the canvas
        canvas.drawBitmap(bitmap, 0, 0, null);

// Define the center of the circle
        float centerX = bitmap.getWidth() / 2f;
        float centerY = bitmap.getHeight() / 2f;

// Define the radius of the circle
        float radius = Math.min(centerX, centerY) * 0.75f;

// Create a new Paint object to apply a white color with 20% opacity
        Paint colorPaint = new Paint();
        colorPaint.setColor(Color.parseColor("#80FFFFFF")); // Set color with 20% opacity
        colorPaint.setStyle(Paint.Style.FILL);

// Draw the white color with 20% opacity on the canvas
        canvas.drawCircle(centerX, centerY, radius, colorPaint);

// Create a Paint object to define the blur effect
        Paint blurPaint = new Paint();
        blurPaint.setAntiAlias(true);
        blurPaint.setFilterBitmap(true);

// Define the blur radius
        float blurRadius = 25f;

// Create a BitmapShader to use as the blur mask
        BitmapShader blurMask = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

// Create a new Paint object to use as the blur filter
        Paint blurFilter = new Paint();
        blurFilter.setShader(blurMask);
        blurFilter.setMaskFilter(new BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL));

// Draw the blurred circle on the canvas, including the white color with 20% opacity
        canvas.drawCircle(centerX, centerY, radius, blurFilter);

// Create a new Paint object to draw the border
        Paint borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(1f);
        borderPaint.setColor(Color.WHITE);

// Draw the border around the circle
        canvas.drawCircle(centerX, centerY, radius, borderPaint);
        return blurredBitmap;

    }

    public static Bitmap blurBitmap4(Bitmap bitmap) {

// Create a new bitmap with the same dimensions as the original
        Bitmap blurredBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());

// Create a canvas to draw on the new bitmap
        Canvas canvas = new Canvas(blurredBitmap);

// Draw the original bitmap on the canvas
        canvas.drawBitmap(bitmap, 0, 0, null);

// Create a Paint object to define the blur effect
        Paint blurPaint = new Paint();
        blurPaint.setAntiAlias(true);
        blurPaint.setFilterBitmap(true);

// Define the blur radius
        float blurRadius = 15f;

// Create a BitmapShader to use as the blur mask
        BitmapShader blurMask = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

// Create a new Paint object to use as the blur filter
        Paint blurFilter = new Paint();
        blurFilter.setShader(blurMask);
        blurFilter.setMaskFilter(new BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL));

// Define the center of the circle
        float centerX = bitmap.getWidth() / 2f;
        float centerY = bitmap.getHeight() / 2f;

// Define the radius of the circle
        float radius = Math.min(centerX, centerY) * 0.75f;

// Create a new Paint object to apply a white color with 20% opacity
        Paint colorPaint = new Paint();
        colorPaint.setColor(Color.parseColor("#33FFFFFF")); // Set color with 20% opacity
        colorPaint.setStyle(Paint.Style.FILL);

// Draw the white color with 20% opacity on the canvas
        canvas.drawCircle(centerX, centerY, radius, colorPaint);

// Draw the blurred circle on the canvas, including the white color with 20% opacity
        canvas.drawCircle(centerX, centerY, radius, blurFilter);

// Create a new Paint object to draw the border
        Paint borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(5f);
        borderPaint.setColor(Color.WHITE);

// Draw the border around the circle
        canvas.drawCircle(centerX, centerY, radius, borderPaint);

        return blurredBitmap;
    }


    public static Bitmap mosiRounded(Bitmap bitmap) {
        Bitmap blurredBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());

// Create a canvas to draw on the new bitmap
        Canvas canvas = new Canvas(blurredBitmap);

// Draw the original bitmap on the canvas
        canvas.drawBitmap(bitmap, 0, 0, null);


        // Create a Paint object to define the blur effect
        Paint blurPaint = new Paint();
        blurPaint.setAntiAlias(true);
        blurPaint.setFilterBitmap(true);

        drawRoundRect(25, 100, 100, 100, 100, blurPaint, canvas);
        return null;
    }

    private static void drawRoundRect(float radius, float left, float top, float right, float bottom, Paint paint, Canvas canvas) {
        Path path = new Path();
        path.moveTo(left, top);
        path.lineTo(right, top);
        path.lineTo(right, bottom);
        path.lineTo(left + radius, bottom);
        path.quadTo(left, bottom, left, bottom - radius);
        path.lineTo(left, top + radius);
        path.quadTo(left, top, left + radius, top);
        canvas.drawPath(path, paint);
    }
}
