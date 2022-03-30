package org.apache.cordova.plugin.Broadcaster;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.Objects;

/**
 * This class echoes a string called from JavaScript.
 */
public class BroadcasterPlugin extends CordovaPlugin {

  private CallbackContext receiverCallbackContext;

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

    if (action.equals("receiver")) {
      receiverCallbackContext= callbackContext;
      final IntentFilter filter = new IntentFilter();
      filter.addAction(Intent.ACTION_SCREEN_OFF);
      filter.addAction(Intent.ACTION_SHUTDOWN);
      cordova.getActivity().registerReceiver(mReceiver, filter);
      return true;
    }
    return false;
  }

  @Override
  public void onDestroy() {

    super.onDestroy();
    cordova.getActivity().unregisterReceiver(mReceiver);
  }

  private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

    public void onReceive(Context ctx, Intent intent) {

      if (Objects.equals(intent.getAction(), Intent.ACTION_SCREEN_OFF)) {
        PluginResult result = new PluginResult(PluginResult.Status.OK, 0);
        result.setKeepCallback(true);
        receiverCallbackContext.sendPluginResult(result);

      } else if (Objects.equals(intent.getAction(), Intent.ACTION_SHUTDOWN)) {
        PluginResult result = new PluginResult(PluginResult.Status.OK, 1);
        result.setKeepCallback(true);
        receiverCallbackContext.sendPluginResult(result);
      }
    }
  };
}
