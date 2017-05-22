package com.sky.tools.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * NetWork Utils
 * <ul>
 * <strong>Attentions</strong>
 * <li>You should add <strong>android.permission.ACCESS_NETWORK_STATE</strong> in manifest, to get network status.</li>
 * </ul>
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-11-03
 */
public class NetWorkUtils {

    public static final String NETWORK_TYPE_WIFI       = "wifi";
    public static final String NETWORK_TYPE_3G         = "eg";
    public static final String NETWORK_TYPE_2G         = "2g";
    public static final String NETWORK_TYPE_WAP        = "wap";
    public static final String NETWORK_TYPE_UNKNOWN    = "unknown";
    public static final String NETWORK_TYPE_DISCONNECT = "disconnect";

    /**
     * Get network type
     * 
     * @param context
     * @return
     */
    public static int getNetworkType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager == null ? null : connectivityManager.getActiveNetworkInfo();
        return networkInfo == null ? -1 : networkInfo.getType();
    }

    /**
     * Get network type name
     * 
     * @param context
     * @return
     */
    public static String getNetworkTypeName(Context context) {
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;
        String type = NETWORK_TYPE_DISCONNECT;
        if (manager == null || (networkInfo = manager.getActiveNetworkInfo()) == null) {
            return type;
        }

        if (networkInfo.isConnected()) {
            String typeName = networkInfo.getTypeName();
            if ("WIFI".equalsIgnoreCase(typeName)) {
                type = NETWORK_TYPE_WIFI;
            } else if ("MOBILE".equalsIgnoreCase(typeName)) {
                String proxyHost = android.net.Proxy.getDefaultHost();
                type = TextUtils.isEmpty(proxyHost) ? (isFastMobileNetwork(context) ? NETWORK_TYPE_3G : NETWORK_TYPE_2G)
                        : NETWORK_TYPE_WAP;
            } else {
                type = NETWORK_TYPE_UNKNOWN;
            }
        }
        return type;
    }

    /**
     * Whether is fast mobile network
     * 数据网络下，当是3G网络（或以上）都认为是快速网络
     * @param context
     * @return
     */
    private static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager == null) {
            return false;
        }

        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true;

            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_IDEN:
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }

    /**
     * 检测当前网络是否可用
     * @param context
     * @return true 可用，false 不可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }
}
