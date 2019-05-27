package com.weshare.umg.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.util.IPAddressUtil;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class IpUtils {

    /**增加日志*/
    private final static Logger logger = LoggerFactory.getLogger(IpUtils.class);
    private static final String LOCAL_IPV4 = "127.0.0.1";
    private static final String LOCAL_IPV6 = "0:0:0:0:0:0:0:1";
    private static final String IP_UNKNOWN = "unknown";
    public static final String IPV4 = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";
    private static String      ip         = null;

    /**获取本地IP*/
    public static String getLocalIp() {
        if (ip == null) {
            synchronized (IpUtils.class) {
                if (ip == null) {
                    ip = localIp();
                }
            }
        }
        return ip;
    }

    /**
     * @Description 判断IP是否是内网
     * @return true:内网 false:公网
     **/
    public static boolean internalIp(String ip) {
        byte[] addr = IPAddressUtil.textToNumericFormatV4(ip);
        return internalIp(addr);
    }
    public static boolean internalIp(byte[] addr) {
        final byte b0 = addr[0];
        final byte b1 = addr[1];
        final byte b2 = addr[2];
        final byte b3 = addr[3];
        //10.x.x.x/8
        final byte SECTION_1 = 0x0A;
        //172.16.x.x/12
        final byte SECTION_2 = (byte) 0xAC;
        final byte SECTION_3 = (byte) 0x10;
        final byte SECTION_4 = (byte) 0x1F;
        //192.168.x.x/16
        final byte SECTION_5 = (byte) 0xC0;
        final byte SECTION_6 = (byte) 0xA8;
        //127.0.0.1
        final byte SECTION_7 = (byte) 0x7F;
        final byte SECTION_8 = (byte) 0x00;
        final byte SECTION_9 = (byte) 0x01;

        switch (b0) {
            case SECTION_1:
                return true;
            case SECTION_2:
                if (b1 >= SECTION_3 && b1 <= SECTION_4) {
                    return true;
                }
            case SECTION_5:
                switch (b1) {
                    case SECTION_6:
                        return true;
                }
            case SECTION_7:
                if( b1 == b2 && b2 == SECTION_8 && b3 == SECTION_9){
                    return true;
                }
            default:
                return false;
        }
    }

    /**
     *  获取本地ip字符串
     * @return 本地的ip字符
     */
    private static String localIp() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            NetworkInterface network;
            Enumeration<InetAddress> inetAddresses;
            while (networkInterfaces.hasMoreElements()) {
                network = networkInterfaces.nextElement();
                if (network.getName().startsWith("eth") && !network.isLoopback()
                        && !network.isVirtual()) {
                    inetAddresses = network.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddress = inetAddresses.nextElement();
                        String hostAddress = inetAddress.getHostAddress();
                        if (!hostAddress.contains(":")){
                            return hostAddress;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("localIp error!",ex);
        }
        return LOCAL_IPV4;
    }

    /***
     * 获取客户端IP地址;if通过了Nginx获取;X-Real-IP,
     * @param request 客户端请求的对象
     * @return 返回客户端ip字符串
     */
    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");

        // 先获取代理机传过来的客户端IP by trentluo
        if (StringUtils.isBlank(ip) || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            String forwardIp = request.getHeader("X-Forwarded-For");
            if (StringUtils.isNoneBlank(forwardIp)) {
                String[] ipLists = forwardIp.split(",");
                ip = ipLists[0];
            }
        }
        if (StringUtils.isBlank(ip) || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (LOCAL_IPV4.equals(ip) || LOCAL_IPV6.equals(ip)) {
            //获取本机真实IP
            ip = getLocalIp();
        }

        return ip;
    }

    /**
     * 根据ip地址计算出long型的数据
     * @param strIP IP V4 地址
     * @return long值
     */
    public static long ipv4ToLong(String strIP) {
        if(isIpv4(strIP)){
            long[] ip = new long[4];
            // 先找到IP地址字符串中.的位置
            int position1 = strIP.indexOf(".");
            int position2 = strIP.indexOf(".", position1 + 1);
            int position3 = strIP.indexOf(".", position2 + 1);
            // 将每个.之间的字符串转换成整型
            ip[0] = Long.parseLong(strIP.substring(0, position1));
            ip[1] = Long.parseLong(strIP.substring(position1 + 1, position2));
            ip[2] = Long.parseLong(strIP.substring(position2 + 1, position3));
            ip[3] = Long.parseLong(strIP.substring(position3 + 1));
            return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
        }
        return 0;
    }

    public static boolean isIpv4(String ip) {
        if(StringUtils.isBlank(ip)){
            return false;
        }
        Pattern p = Pattern.compile(IPV4);
        Matcher m = p.matcher(ip);
        return m.matches();
    }

    /**
     * 根据long值获取ip v4地址
     *
     * @param longIP IP的long表示形式
     * @return IP V4 地址
     */
    public static String longToIpv4(long longIP) {
        StringBuffer sb = new StringBuffer();
        // 直接右移24位
        sb.append(String.valueOf(longIP >>> 24));
        sb.append(".");
        // 将高8位置0，然后右移16位
        sb.append(String.valueOf((longIP & 0x00FFFFFF) >>> 16));
        sb.append(".");
        sb.append(String.valueOf((longIP & 0x0000FFFF) >>> 8));
        sb.append(".");
        sb.append(String.valueOf(longIP & 0x000000FF));
        return sb.toString();
    }
}
