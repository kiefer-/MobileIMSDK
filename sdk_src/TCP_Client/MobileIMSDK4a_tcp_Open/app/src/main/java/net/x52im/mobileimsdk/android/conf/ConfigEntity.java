/*
 * Copyright (C) 2021  即时通讯网(52im.net) & Jack Jiang.
 * The MobileIMSDK_TCP (MobileIMSDK v6.x TCP版) Project. 
 * All rights reserved.
 * 
 * > Github地址：https://github.com/JackJiang2011/MobileIMSDK
 * > 文档地址：  http://www.52im.net/forum-89-1.html
 * > 技术社区：  http://www.52im.net/
 * > 技术交流群：215477170 (http://www.52im.net/topic-qqgroup.html)
 * > 作者公众号：“即时通讯技术圈】”，欢迎关注！
 * > 联系作者：  http://www.52im.net/thread-2792-1-1.html
 *  
 * "即时通讯网(52im.net) - 即时通讯开发者社区!" 推荐开源工程。
 * 
 * ConfigEntity.java at 2021-7-1 15:08:17, code by Jack Jiang.
 */
package net.x52im.mobileimsdk.android.conf;

import net.x52im.mobileimsdk.android.core.KeepAliveDaemon;

public class ConfigEntity {
    public static String appKey = null;
    public static String serverIP = "192.168.0.190";//"rbcore.52im.net";
    public static int serverPort = 8901;
    public static int localPort = 0;//7801;

    public static void setSenseMode(SenseMode mode) {
        int keepAliveInterval = 0;
        int networkConnectionTimeout = 0;
        switch (mode) {
            case MODE_3S: {
                keepAliveInterval = 3000;// 3s
                networkConnectionTimeout = keepAliveInterval * 1 + 2000;// 5s
                break;
            }
            case MODE_5S: {
                keepAliveInterval = 5000;// 3s
                networkConnectionTimeout = keepAliveInterval * 1 + 3000;// 8s
                break;
            }
            case MODE_10S:
                keepAliveInterval = 10000;// 10s
                networkConnectionTimeout = keepAliveInterval * 1 + 5000;// 15s
                break;
            case MODE_15S:
                keepAliveInterval = 15000;// 15s
                networkConnectionTimeout = keepAliveInterval * 1 + 5000;// 20s
                break;
            case MODE_30S:
                keepAliveInterval = 30000;// 30s
                networkConnectionTimeout = keepAliveInterval * 1 + 5000;// 35s
                break;
            case MODE_60S:
                keepAliveInterval = 60000;// 60s
                networkConnectionTimeout = keepAliveInterval * 1 + 5000;// 65s
                break;
            case MODE_120S:
                keepAliveInterval = 120000;// 120s
                networkConnectionTimeout = keepAliveInterval * 1 + 5000;// 125s
                break;
        }

        if (keepAliveInterval > 0) {
            KeepAliveDaemon.KEEP_ALIVE_INTERVAL = keepAliveInterval;
        }
        if (networkConnectionTimeout > 0) {
            KeepAliveDaemon.NETWORK_CONNECTION_TIME_OUT = networkConnectionTimeout;
        }
    }

    /**
     * MobileIMSDK即时通讯核心框架预设的敏感度模式.
     *
     * <p>
     * 对于客户端而言，此模式决定了用户与服务端网络会话的健康模式，原则上超敏感客户端的体验越好。
     *
     * <p>
     * <b>重要说明：</b><u>客户端本模式的设定必须要与服务端的模式设制保持一致</u>，否则可能因参数的不一致而导致
     * IM算法的不匹配，进而出现不可预知的问题。
     *
     * @author Jack Jiang, 2015-09-07
     * @version 2.1
     */
    public enum SenseMode {
        /**
         * 此模式下：<br>
         * * KeepAlive心跳问隔为3秒；<br>
         * * 5秒后未收到服务端心跳反馈即认为连接已断开（相当于连续2个心跳间隔(即算法最大6秒延迟后)后仍未收到服务端反馈）。
         */
        MODE_3S,

        /**
         * 此模式下：<br>
         * * KeepAlive心跳问隔为5秒；<br>
         * * 8秒后未收到服务端心跳反馈即认为连接已断开（相当于连续1个心跳间隔+3秒链路延迟容忍时间后仍未收到服务端反馈）。
         */
        MODE_5S,

        /**
         * 此模式下：<br>
         * * KeepAlive心跳问隔为10秒；<br>
         * * 15秒后未收到服务端心跳反馈即认为连接已断开（相当于连续2个心跳间隔(即算法最大20秒延迟后)后仍未收到服务端反馈）。
         */
        MODE_10S,

        /**
         * 此模式下：<br>
         * * KeepAlive心跳问隔为15秒；<br>
         * * 20秒后未收到服务端心跳反馈即认为连接已断开（相当于连续2个心跳间隔(即算法最大30秒延迟后)后仍未收到服务端反馈）。
         *
         * @since 5.0
         */
        MODE_15S,

        /**
         * 此模式下：<br>
         * * KeepAlive心跳问隔为30秒；<br>
         * * 35秒后未收到服务端心跳反馈即认为连接已断开（相当于连续2个心跳间隔(即算法最大60秒延迟后)后仍未收到服务端反馈）。
         */
        MODE_30S,

        /**
         * 此模式下：<br>
         * * KeepAlive心跳问隔为60秒；<br>
         * * 65秒后未收到服务端心跳反馈即认为连接已断开（相当于连续2个心跳间隔(即算法最大120秒延迟后)后仍未收到服务端反馈）。
         */
        MODE_60S,

        /**
         * 此模式下：<br>
         * * KeepAlive心跳问隔为120秒；<br>
         * * 125秒后未收到服务端心跳反馈即认为连接已断开（相当于连续2个心跳间隔(即算法最大240秒延迟后)后仍未收到服务端反馈）。
         */
        MODE_120S
    }
}
