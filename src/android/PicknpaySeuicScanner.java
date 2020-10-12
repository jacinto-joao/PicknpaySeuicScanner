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
				
				mScanner.setParams(0x101, 0);
				mScanner.setParams(0x102, 0);
				mScanner.setParams(0x103, 0);
				mScanner.setParams(0x104, 0);
				mScanner.setParams(0x105, 1);
				mScanner.setParams(0x106, 0);
				mScanner.setParams(0x107, 0);
				mScanner.setParams(0x108, 0);
				mScanner.setParams(0x109, 0);
				mScanner.setParams(0x10a, 1);
				mScanner.setParams(0x10b, 1);
				mScanner.setParams(0x10c, 0);
				mScanner.setParams(0x10d, 0);
				mScanner.setParams(0x10e, 0);
				mScanner.setParams(0x10f, 0);

				mScanner.setParams(0x110, 0);
				mScanner.setParams(0x111, 1);
				mScanner.setParams(0x112, 0);
				mScanner.setParams(0x113, 0);
				mScanner.setParams(0x114, 0);
				mScanner.setParams(0x115, 0);

				mScanner.setParams(0x116, 0);
				mScanner.setParams(0x117, 0);
				mScanner.setParams(0x118, 1);
				mScanner.setParams(0x119, 0);

				mScanner.setParams(0x11a, 0);
				mScanner.setParams(0x11b, 0);

				mScanner.setParams(0x121, 0);
				mScanner.setParams(0x122, 0);
				mScanner.setParams(0x123, 0);
				mScanner.setParams(0x124, 0);
				mScanner.setParams(0x125, 0);
				mScanner.setParams(0x126, 0);


				mScanner.setParams(0x131, 0);
				mScanner.setParams(0x132, 0);
				mScanner.setParams(0x133, 0);
				mScanner.setParams(0x134, 0);
				mScanner.setParams(0x135, 0);
				mScanner.setParams(0x136, 0);
				mScanner.setParams(0x137, 0);
				mScanner.setParams(0x138, 0);
				mScanner.setParams(0x139, 0);


				mScanner.setParams(0x13a, 0);
				mScanner.setParams(0x13b, 0);
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
