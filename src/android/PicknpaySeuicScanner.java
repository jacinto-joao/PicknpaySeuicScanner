package com.seuic.scanner.api;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.apache.cordova.CordovaArgs;
import org.apache.cordova.PluginResult;

import com.seuic.scanner.DecodeInfo;
import com.seuic.scanner.DecodeInfoCallBack;
import com.seuic.scanner.Scanner;
import com.seuic.scanner.ScannerFactory;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class PicknpaySeuicScanner extends CordovaPlugin {

	private Context mContext;

	private Scanner mScanner;

	private CallbackContext mCallbackContext;

	private OnDecodeInfoCallback mDecodeInfoCallback = new OnDecodeInfoCallback();

	@Override
	public boolean execute(String action, CordovaArgs args,
			CallbackContext callbackContext) throws JSONException {

		this.initScanner();

		if(action.equals("startScan")){
			this.startScan();
			return true;
		}else if(action.equals("stopScan")){
			this.stopScan();
			return true;
		}else if(action.equals("closeScanner")){
			this.closeScanner();
			return true;
		}else if(action.equals("openScanner")){
			this.openScanner();
			return true;
		}else if(action.equals("setDecodeCallback")){
			this.setDecodeCallback(callbackContext);
			return true;
		}

		return false;
	}

	private void startScan(){
		try {
			cordova.getThreadPool().execute(new Runnable() {

			@Override
			public void run() {
				mScanner.startScan();
				mScanner.setParams(0x104, 1);
			}
		});
		} catch (Exception e) {
			//TODO: handle exception
		}
	}

	private void stopScan(){
		cordova.getThreadPool().execute(new Runnable() {

			@Override
			public void run() {
				mScanner.stopScan();
			}
		});
	}

	private void closeScanner(){
		cordova.getThreadPool().execute(new Runnable() {

			@Override
			public void run() {
				mScanner.close();
			}
		});
	}

	private void openScanner(){

		cordova.getThreadPool().execute(new Runnable() {

			@Override
			public void run() {
				boolean isOpened = mScanner.open();
				Log.i("ScannerAPIPlugin", isOpened +"");
			}
		});
	}

	private void setDecodeCallback(final CallbackContext callbackContext){
		cordova.getThreadPool().execute(new Runnable() {

			@Override
			public void run() {
				mCallbackContext = callbackContext;

				mScanner.setDecodeInfoCallBack(mDecodeInfoCallback);
			}
		});
	}

	private void initScanner(){
		if(mContext == null){
			mContext = this.cordova.getActivity().getApplicationContext();
		}

		if(mScanner == null){
			mScanner = ScannerFactory.getScanner(mContext);
		}
	}

	private class OnDecodeInfoCallback implements DecodeInfoCallBack{

		@Override
		public void onDecodeComplete(DecodeInfo decodeInfo) {
			PluginResult result = new PluginResult(PluginResult.Status.OK, decodeInfo.barcode);
			result.setKeepCallback(true);
			mCallbackContext.sendPluginResult(result);
		}

	}

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }


}
