/*
 * Copyright (c) 2012-2014 Arne Schwabe
 * Distributed under the GNU GPL v2 with additional terms. For full terms see the file doc/LICENSE.txt
 */

package de.blinkt.openvpn.core;

import android.text.TextUtils;

import java.io.Serializable;

public class Connection implements Serializable, Cloneable {
    public String mServerName = "openvpn.blinkt.de";
    public String mServerPort = "1194";
    public boolean mUseUdp = true;
    public String mCustomConfiguration="";
    public boolean mUseCustomConfig=false;
    public boolean mEnabled=true;
    public int mConnectTimeout = 0;

    private static final long serialVersionUID = 92031902903829089L;


    public String getConnectionBlock() {
        String cfg="";

        // Server Address
        cfg += "remote ";
        cfg += mServerName;
        cfg += " ";
        cfg += mServerPort;
        if (mUseUdp)
            cfg += " udp\n";
        else
            cfg += " tcp-client\n";

        if (mConnectTimeout!=0)
            cfg += String.format(" connect-timeout  %d\n" , mConnectTimeout);


        if (!TextUtils.isEmpty(mCustomConfiguration) && mUseCustomConfig) {
            cfg += mCustomConfiguration;
            cfg += "\n";
        }
        return cfg;
    }

    @Override
    public Connection clone() throws CloneNotSupportedException {
        return (Connection) super.clone();
    }

    public boolean isOnlyRemote() {
        return TextUtils.isEmpty(mCustomConfiguration) || !mUseCustomConfig;
    }
}
