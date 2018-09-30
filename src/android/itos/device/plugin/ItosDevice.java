/**
 * PhoneGap is available under *either* the terms of the modified BSD license *or* the
 * MIT License (2008). See http://opensource.org/licenses/alphabetical for full text.
 *
 * Copyright (c) Matt Kane 2010
 * Copyright (c) 2011, IBM Corporation
 * Copyright (c) 2013, Maciej Nux Jaros
 */
package com.phonegap.plugins.itosdevice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.content.pm.PackageManager;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PermissionHelper;

import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.encode.EncodeActivity;
import com.google.zxing.client.android.Intents;

/**
 * This calls out to the ZXing barcode reader and returns the result.
 *
 * @sa https://github.com/apache/cordova-android/blob/master/framework/src/org/apache/cordova/CordovaPlugin.java
 */
public class ItosDevice extends CordovaPlugin {
    public static final int REQUEST_CODE = 0x0ba7c0de;

    private static final String PRINT = "print";
    private static final String ENCODE = "encode";
    private static final String CANCELLED = "cancelled";
    private static final String FORMAT = "format";
    private static final String TEXT = "text";
    private static final String DATA = "data";
    private static final String TYPE = "type";
    private static final String PREFER_FRONTCAMERA = "preferFrontCamera";
    private static final String ORIENTATION = "orientation";
    private static final String SHOW_FLIP_CAMERA_BUTTON = "showFlipCameraButton";
    private static final String RESULTDISPLAY_DURATION = "resultDisplayDuration";
    private static final String SHOW_TORCH_BUTTON = "showTorchButton";
    private static final String TORCH_ON = "torchOn";
    private static final String FORMATS = "formats";
    private static final String PROMPT = "prompt";
    private static final String TEXT_TYPE = "TEXT_TYPE";
    private static final String EMAIL_TYPE = "EMAIL_TYPE";
    private static final String PHONE_TYPE = "PHONE_TYPE";
    private static final String SMS_TYPE = "SMS_TYPE";

    private static final String LOG_TAG = "ItosDevice";

    private String [] permissions = { Manifest.permission.CAMERA };

    private JSONArray requestArgs;
    private CallbackContext callbackContext;

    private final int FONT_SIZE_NORMAL = 24;

     private Printer mPrinter;

    /**
     * Constructor.
     */
    public ItosDevice() {
    }

    /**
     * Executes the request.
     *
     * This method is called from the WebView thread. To do a non-trivial amount of work, use:
     *     cordova.getThreadPool().execute(runnable);
     *
     * To run on the UI thread, use:
     *     cordova.getActivity().runOnUiThread(runnable);
     *
     * @param action          The action to execute.
     * @param args            The exec() arguments.
     * @param callbackContext The callback context used when calling back into JavaScript.
     * @return                Whether the action was valid.
     *
     * @sa https://github.com/apache/cordova-android/blob/master/framework/src/org/apache/cordova/CordovaPlugin.java
     */
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {

        this.callbackContext = callbackContext;
        this.requestArgs = args;

        callbackContext.error("User did not specify data to encode");
        return true;
        if (action.equals(PRINT)) {
            Log.i("Hola");

            //JSONObject obj = args.optJSONObject(0);
            
            /*String type = obj.optString(TYPE);
            String data = obj.optString(DATA);

            // If the type is null then force the type to text
            if (type == null) {
                type = TEXT_TYPE;
            }

            if (data == null) {
                callbackContext.error("User did not specify data to encode");
                return true;
            }

            encode(type, data);*/
            mPrinter = new Printer(this.cordova.getActivity().getBaseContext());
            mPrinter.initPrinter();

            mPrinter.appendStr( "Business Name: Application Test", FONT_SIZE_NORMAL, Align.LEFT, false );
            mPrinter.appendStr( "Business number:123456789012345", FONT_SIZE_NORMAL, Align.LEFT, false );
            mPrinter.appendStr( "---------------------------", FONT_SIZE_NORMAL, Align.LEFT, false );
            mPrinter.appendStr( "Cardholder's signature:", FONT_SIZE_NORMAL, Align.LEFT, false );
            mPrinter.appendStr( "\n", FONT_SIZE_NORMAL, Align.LEFT, false );
            mPrinter.appendStr( "\n", FONT_SIZE_NORMAL, Align.LEFT, false );
            mPrinter.appendStr( "\n", FONT_SIZE_NORMAL, Align.LEFT, false );
            mPrinter.appendStr( "---------------------------", FONT_SIZE_NORMAL, Align.LEFT, false );
            mPrinter.appendStr( "Business", FONT_SIZE_NORMAL, Align.RIGHT, false );
            mPrinter.appendStr( "\n", FONT_SIZE_NORMAL, Align.LEFT, false );
            mPrinter.appendStr( "\n", FONT_SIZE_NORMAL, Align.LEFT, false );

            int retCode = mPrinter.startPrint( true, new PrinterCallbacks() {

                @Override
                public void onPrintResult( int retCode ) {
                    PluginResult result = new PluginResult(PluginResult.Status.RESULT_OK);
                    this.callbackContext.sendPluginResult(result);
                    //showMessage( String.format( "PrintResult: %d", retCode ) );
                }
            } );

        } else if (action.equals(SCAN)) {

           return true;
        } else {
            return false;
        }
        return true;
    }

}