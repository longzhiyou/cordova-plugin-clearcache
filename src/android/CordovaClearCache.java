package com.jerym.clearCache;

import android.util.Log;
import java.io.File;

import org.apache.cordova.*;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class CordovaClearCache extends CordovaPlugin {

    private static final String TAG = "CordovaClearCache";

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            return true;
        }
        if (action.equals("clearCacheAndExternalCache")) {
            /* clear externalCacheDir and cacheDir */
            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    int result = clearCacheFolder(cordova.getActivity().getExternalCacheDir());
                    result += clearCacheFolder(cordova.getActivity().getCacheDir());
                    callbackContext.success(result); // Thread-safe.
                }
            });
            return true;
        }
        if(action.equals("webViewClearCache")){
            /* execute clearCache of this webView. */
            /* Clears the resource cache. Note that the cache is per-application, so this will clear the cache for all WebViews used */
             cordova.getActivity().runOnUiThread( new Runnable() {
                public void run() {
                    try {
                         boolean b = args.getBoolean(0);
                            if (b){
                                webView.clearCache(true);
                            }else {
                                webView.clearCache();
                            }

                        // send success result to cordova
                        callbackContext.success("success");
                    } catch ( Exception e ) {
                        String msg = "Error while clearing webview cache.";
                        Log.e(TAG, msg );

                        // return error answer to cordova
                        callbackContext.error(msg);
                    }
                }
            });
            
          
            return true;
        }

        if ( action.equals("clearCacheOnly") ) {
            Log.i(TAG, "Cordova Android Cache.clear() called.");
            /* clear cacheDir from cordova.getActivity().getCacheDir() */

            cordova.getActivity().runOnUiThread( new Runnable() {
                public void run() {
                    try {
                        // clear the cache
//                        self.webView.clearCache(true);

                        // clear the data
                        clearApplicationData();

                        // send success result to cordova
                        callbackContext.success("success");
                    } catch ( Exception e ) {
                        String msg = "Error while clearing webview cache.";
                        Log.e(TAG, msg );

                        // return error answer to cordova
                        callbackContext.error(msg);
                    }
                }
            });
            return true;
        }
        return false;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    private int clearCacheFolder(final File dir) {
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        clearCacheFolder(child);
                    }

                    Log.i(TAG, String.format("开始移除文件： %s", child.getAbsolutePath()));
                    child.delete();
                }
            } catch (Exception e) {
                Log.e(TAG, String.format("Failed to clean the cache, error %s", e.getMessage()));

                return 1;
            }
        }
        return 0;
    }

    // http://www.hrupin.com/2011/11/how-to-clear-user-data-in-your-android-application-programmatically
    private void clearApplicationData() {
        File cache = this.cordova.getActivity().getCacheDir();
        File appDir = new File(cache.getParent());
        Log.i(TAG, "Absolute path: " + appDir.getAbsolutePath());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    Log.i(TAG, "File /data/data/APP_PACKAGE/" + s + " DELETED");
                }
            }
        }
    }

    private boolean deleteDir(File dir) {
        Log.i(TAG, "Deleting: " + dir.getAbsolutePath());
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }
}
