package com.Alatheer.marmy;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Delta on 15/02/2018.
 */

public class NetworkConnection {

    Context context;

    public NetworkConnection(Context context) {
        this.context = context;
    }

    public boolean getConnection() {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) {
            if (networkInfo.isConnected() || networkInfo.isAvailable()) {
                return true;
            }

        }
        return false;
    }
}
