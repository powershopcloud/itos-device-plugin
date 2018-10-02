/**
 * PhoneGap is available under *either* the terms of the modified BSD license *or* the
 * MIT License (2008). See http://opensource.org/licenses/alphabetical for full text.
 *
 * Copyright (c) Matt Kane 2010
 * Copyright (c) 2011, IBM Corporation
 * Copyright (c) 2013, Maciej Nux Jaros
 */
package com.phonegap.plugins.itosdevice;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.Manifest;


import com.itos.sdk.cm5.deviceLibrary.Beeper.Beeper;
import com.itos.sdk.cm5.deviceLibrary.DeviceResult;
import com.itos.sdk.cm5.deviceLibrary.Led.Led;
import com.itos.sdk.cm5.deviceLibrary.Led.LightMode;
import com.itos.sdk.cm5.deviceLibrary.Printer.Align;
import com.itos.sdk.cm5.deviceLibrary.Printer.Printer;
import com.itos.sdk.cm5.deviceLibrary.Printer.PrinterCallbacks;
import com.itos.sdk.cm5.deviceLibrary.scanner.Scanner;
import com.itos.sdk.cm5.deviceLibrary.scanner.ScannerCallbacks;

/**
 * This calls out to the ZXing barcode reader and returns the result.
 *
 * @sa https://github.com/apache/cordova-android/blob/master/framework/src/org/apache/cordova/CordovaPlugin.java
 */
public class ItosDevice extends CordovaPlugin {
    public static final int REQUEST_CODE = 0x0ba7c0de;

    private static final String PRINT = "print";
    private static final String BEEP = "beep";
    private static final String LIGHT = "light";
    private static final String DATA = "data";


    private static final String LOG_TAG = "ItosDevice";


    private JSONArray requestArgs;
    private CallbackContext callbackContext;

    private final int FONT_SIZE_NORMAL = 24;

    private Printer mPrinter;
    private Led mLed;
    private Beeper mBeeper;

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
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) {

        this.callbackContext = callbackContext;
        this.requestArgs = args;

        if (action.equals(PRINT)) {
            JSONObject obj = args.optJSONObject(0);


            JSONArray data = obj.optJSONArray(DATA);

            if (data == null) {
                callbackContext.error("User did not specify data to print");
                return true;
            }

            mPrinter = new Printer(this.cordova.getActivity().getBaseContext());
            mPrinter.initPrinter();

            for(int i=0; i<data.length(); i++){
                try{mPrinter.appendStr( data.getString(i), FONT_SIZE_NORMAL, Align.LEFT, false );}catch(JSONException e){}
            }

            int retCode = mPrinter.startPrint( true, new PrinterCallbacks() {

                @Override
                public void onPrintResult( int retCode ) {
                    mPrinter.cutPaper();
                    PluginResult result = new PluginResult(PluginResult.Status.OK);
                    callbackContext.sendPluginResult(result);
                }

            } );

        }else if(action.equals(BEEP)){
            JSONObject obj = args.optJSONObject(0);
            int time = Integer.parseInt(obj.optString("seconds"));
            mBeeper = new Beeper( this.cordova.getActivity().getBaseContext() );
            mBeeper.beep( time );
            PluginResult result = new PluginResult(PluginResult.Status.OK);
            callbackContext.sendPluginResult(result);
        } else if (action.equals(LIGHT)) {
            JSONObject obj = args.optJSONObject(0);
            String color = obj.optString("color");
            boolean isOn = obj.optBoolean("isOn");
            if(mLed == null){
                mLed = new Led(this.cordova.getActivity().getBaseContext());
            }
            LightMode lightMode;
            if(color.equals("BLUE")){
                lightMode = LightMode.BLUE;
            }else if(color.equals("RED")){
                lightMode =  LightMode.RED;
            }else if(color.equals("GREEN")){
                lightMode = LightMode.GREEN;
            }else{
                lightMode = LightMode.YELLOW;
            }
            mLed.setLed(lightMode, isOn);
            PluginResult result = new PluginResult(PluginResult.Status.OK);
            callbackContext.sendPluginResult(result);
        } else {
            return false;
        }
        return true;
    }

}
