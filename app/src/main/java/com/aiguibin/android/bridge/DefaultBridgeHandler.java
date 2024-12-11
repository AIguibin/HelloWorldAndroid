package com.aiguibin.android.bridge;

import android.util.Log;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;

import org.json.JSONObject;

public class DefaultBridgeHandler implements BridgeHandler {

    @Override
    public void handler(String data, CallBackFunction function){

        try {
            Log.e("DefaultBridgeHandler","接收H5页面传递过来的数据:\r\n"+data);
            JSONObject jsonObject = new JSONObject(data);
            String action = jsonObject.optString("action", "").toUpperCase();
            MessageType messageType = MessageType.valueOf(action);

            BridgeHandler specificHandler = BridgeHandlerDispatch.getHandler(messageType);
            if (specificHandler != null) {
                specificHandler.handler(data, function);
            } else {
                Log.d("DefaultBridgeHandler","Not matched BridgeHandler.");
            }
        } catch (Exception e) {
            Log.e("DefaultBridgeHandler", String.valueOf(e));
        }
    };
}
