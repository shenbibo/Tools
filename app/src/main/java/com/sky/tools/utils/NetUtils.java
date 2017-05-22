package com.sky.tools.utils;

/**
 * [网络工具类]
 * [detail]
 * Created by Sky on 2017/5/22.
 */
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class NetUtils {
    public static final int NET_TYPE_NONE = -1;
    public static final int NET_TYPE_MOBILE = 0;
    public static final int NET_TYPE_WIFI = 1;
    public static final int NET_TYPE_2G = 2;
    public static final int NET_TYPE_3G = 3;
    public static final int NET_TYPE_4G = 4;
    public static final int NET_TYPE_UNKNOWN = 100;


    /**
     * copy from {@link android.telephony.TelephonyManager}version 7.1.1
     * */
    /** Current network is GPRS */
    public static final int NETWORK_TYPE_GPRS = 1;
    /** Current network is EDGE */
    public static final int NETWORK_TYPE_EDGE = 2;
    /** Current network is UMTS */
    public static final int NETWORK_TYPE_UMTS = 3;
    /** Current network is CDMA: Either IS95A or IS95B */
    public static final int NETWORK_TYPE_CDMA = 4;
    /** Current network is EVDO revision 0 */
    public static final int NETWORK_TYPE_EVDO_0 = 5;
    /** Current network is EVDO revision A */
    public static final int NETWORK_TYPE_EVDO_A = 6;
    /** Current network is 1xRTT */
    public static final int NETWORK_TYPE_1xRTT = 7;
    /** Current network is HSDPA */
    public static final int NETWORK_TYPE_HSDPA = 8;
    /** Current network is HSUPA */
    public static final int NETWORK_TYPE_HSUPA = 9;
    /** Current network is HSPA */
    public static final int NETWORK_TYPE_HSPA = 10;
    /** Current network is iDen */
    public static final int NETWORK_TYPE_IDEN = 11;
    /** Current network is EVDO revision B */
    public static final int NETWORK_TYPE_EVDO_B = 12;
    /** Current network is LTE */
    public static final int NETWORK_TYPE_LTE = 13;
    /** Current network is eHRPD */
    public static final int NETWORK_TYPE_EHRPD = 14;
    /** Current network is HSPA+ */
    public static final int NETWORK_TYPE_HSPAP = 15;
    /** Current network is GSM */
    public static final int NETWORK_TYPE_GSM = 16;
    /** Current network is TD_SCDMA */
    public static final int NETWORK_TYPE_TD_SCDMA = 17;
    /** Current network is IWLAN */
    public static final int NETWORK_TYPE_IWLAN = 18;


    /**
     * <br>获取当前手机数据网络的类型 2/3/4G, 其他为未知网络
     * <br>GPRS       2G(2.5) General Packet Radia Service 114kbps<br>
     * <br>EDGE       2G(2.75G) Enhanced Data Rate for GSM Evolution 384kbps
     * <br>UMTS      3G WCDMA 联通3G Universal Mobile Telecommunication System 完整的3G移动通信技术标准
     * <br>CDMA     2G 电信 Code Division Multiple Access 码分多址
     * <br>EVDO_0   3G (EVDO 全程 CDMA2000 1xEV-DO) Evolution - Data Only (Data Optimized) 153.6kps - 2.4mbps 属于3G
     * <br>EVDO_A  3G 1.8mbps - 3.1mbps 属于3G过渡，3.5G
     * <br>1xRTT      2G CDMA2000 1xRTT (RTT - 无线电传输技术) 144kbps 2G的过渡,
     * <br>HSDPA    3.5G 高速下行分组接入 3.5G WCDMA High Speed Downlink Packet Access 14.4mbps
     * <br>HSUPA    3.5G High Speed Uplink Packet Access 高速上行链路分组接入 1.4 - 5.8 mbps
     * <br>HSPA      3G (分HSDPA,HSUPA) High Speed Packet Access
     * <br>IDEN      2G Integrated Dispatch Enhanced Networks 集成数字增强型网络 （属于2G，来自维基百科）
     * <br>EVDO_B 3G EV-DO Rev.B 14.7Mbps 下行 3.5G
     * <br>LTE        4G Long Term Evolution FDD-LTE 和 TDD-LTE , 3G过渡，升级版 LTE Advanced 才是4G
     * <br>EHRPD  3G CDMA2000向LTE 4G的中间产物 Evolved High Rate Packet Data HRPD的升级
     * <br>HSPAP  3G HSPAP 比 HSDPA 快些
     */
    public static int getMobileNetType(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        switch (telephonyManager.getNetworkType()) {
            case NETWORK_TYPE_GPRS:
            case NETWORK_TYPE_EDGE:
            case NETWORK_TYPE_CDMA:    //CDMA2000是3G
            case NETWORK_TYPE_1xRTT:
            case NETWORK_TYPE_IDEN:    //集成数字增强型网络 （属于2G，来自维基百科）
            case NETWORK_TYPE_GSM:
                return NET_TYPE_2G;

            case NETWORK_TYPE_UMTS:
            case NETWORK_TYPE_EVDO_0:   //CDMA2000
            case NETWORK_TYPE_EVDO_A:
            case NETWORK_TYPE_HSDPA:
            case NETWORK_TYPE_HSUPA:
            case NETWORK_TYPE_HSPA:
            case NETWORK_TYPE_EVDO_B:
            case NETWORK_TYPE_EHRPD:
            case NETWORK_TYPE_HSPAP:
            case NETWORK_TYPE_TD_SCDMA:
                return NET_TYPE_3G;

            case NETWORK_TYPE_IWLAN:    // 为什么是4G网络还未查到资料
            case NETWORK_TYPE_LTE:
                return NET_TYPE_4G;

            default:
                NetworkInfo info = getActiveNetworkInfo(context);
                if (isNetAvailable(info)) {
                    String subTypeName = info.getSubtypeName();
                    if (subTypeName.equalsIgnoreCase("WCDMA") || subTypeName.equalsIgnoreCase("CDMA2000")) {
                        return NET_TYPE_3G;
                    }
                }
        }

        return NET_TYPE_UNKNOWN;
    }

    /**
     * 获取当前网络类型
     * {@link #NET_TYPE_NONE}无网络
     * {@link #NET_TYPE_2G}
     * {@link #NET_TYPE_3G}
     * {@link #NET_TYPE_4G}
     * {@link #NET_TYPE_WIFI}
     * {@link #NET_TYPE_UNKNOWN}未知网络，如有线网络，蓝牙等
     */
    public static int getNetState(Context context) {
        int netState = NET_TYPE_NONE;
        NetworkInfo info = getActiveNetworkInfo(context);
        if (isNetAvailable(info)) {
            int type = info.getType();
            if (type == ConnectivityManager.TYPE_WIFI) {
                netState = NET_TYPE_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                netState = getMobileNetType(context);
            } else {
                netState = NET_TYPE_UNKNOWN;
            }
        }

        return netState;
    }

    /**
     * 获取当前的网络类型(WIFI,2G,3G,4G)
     * <p>依赖上面的方法</p>
     *
     * @param context 上下文
     * @return 网络类型名称
     * <ul>
     * <li>NETWORK_WIFI   </li>
     * <li>NETWORK_4G     </li>
     * <li>NETWORK_3G     </li>
     * <li>NETWORK_2G     </li>
     * <li>NETWORK_UNKNOWN</li>
     * <li>NETWORK_NO     </li>
     * </ul>
     */
    public static String getNetWorkStateName(Context context) {
        switch (getNetState(context)) {
            case NET_TYPE_WIFI:
                return "NETWORK_WIFI";
            case NET_TYPE_4G:
                return "NETWORK_4G";
            case NET_TYPE_3G:
                return "NETWORK_3G";
            case NET_TYPE_2G:
                return "NETWORK_2G";
            case NET_TYPE_NONE:
                return "NETWORK_NONE";
            default:
                return "NETWORK_UNKNOWN";
        }
    }

    /**
     * 是否是wifi网络
     */
    public static boolean isWifiNet(Context context) {
        return getNetState(context) == NET_TYPE_WIFI;
    }

    /**
     * 是否是数据网络(2/3/4)
     */
    public static boolean isMobileNet(Context context) {
        NetworkInfo info = getActiveNetworkInfo(context);
        return isNetAvailable(info) && info.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * 检测当前网络是否可用
     *
     * @param context
     * @return true 可用，false 不可用
     */
    public static boolean isNetAvailable(Context context) {
        return isNetAvailable(getActiveNetworkInfo(context));
    }

    private static boolean isNetAvailable(NetworkInfo networkInfo) {
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    private static NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo();
    }
}

