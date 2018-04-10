package com.purple.ams.ssm.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
/**
 * @ClassName: MacAddressApi 
 * @Description: 获取Mac地址和IP地址工具类
 * @author: PurpleSoft@一禅
 * @date: 2018年4月3日 上午9:54:49
 */
public class MacAddressApi {
    /**
    * 获取当前操作系统名称. return 操作系统名称 例如:windows xp,linux 等.
    */
    public static String getOSName() {
        return System.getProperty("os.name").toLowerCase();
    }

    /**
    * 获取unix网卡的mac地址. 非windows的系统默认调用本方法获取. 如果有特殊系统请继续扩充新的取mac地址方法.
    *
    * @return mac地址
    */
    public static String getUnixMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;
        try {
            // linux下的命令，一般取eth0作为本地主网卡
            process = Runtime.getRuntime().exec("ifconfig eth0");
            // 显示信息中包含有mac地址信息
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                // 寻找标示字符串[hwaddr]
                index = line.toLowerCase().indexOf("hwaddr");
                if (index >= 0) {// 找到了
                    // 取出mac地址并去除2边空格
                    mac = line.substring(index + "hwaddr".length() + 1).trim();
                    break;
                }
            }
        }
        catch (IOException e) {
            System.out.println("unix/linux方式未获取到网卡地址");
        }
        finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
            bufferedReader = null;
            process = null;
        }
        return mac;
    }

    /**
    * 获取widnows网卡的mac地址.
    *
    * @return mac地址
    */
    public static String getWindowsMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;
        try {
            // windows下的命令，显示信息中包含有mac地址信息
            process = Runtime.getRuntime().exec("ipconfig /all");
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                // 寻找标示字符串[physical
                index = line.toLowerCase().indexOf("physical address");
                if (index >= 0) {// 找到了
                    index = line.indexOf(":");// 寻找":"的位置
                    if (index >= 0) {
                        // 取出mac地址并去除2边空格
                        mac = line.substring(index + 1).trim();
                    }
                    break;
                }
            }
        }
        catch (IOException e) {
            System.out.println("widnows方式未获取到网卡地址");
        }
        finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
            bufferedReader = null;
            process = null;
        }
        return mac;
    }

    /**
    * windows 7 专用 获取MAC地址
    *
    * @return
    * @throws Exception
    */
    public static String getWindows7MACAddress() {
        StringBuffer sb = new StringBuffer();
        try {
            // 获取本地IP对象
            InetAddress ia = InetAddress.getLocalHost();
            // 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
            byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
            // 下面代码是把mac地址拼装成String
            for (int i = 0; i < mac.length; i++) {
                // mac[i] & 0xFF 是为了把byte转化为正整数
                String s = Integer.toHexString(mac[i] & 0xFF);
                sb.append(s.length() == 1 ? 0 + s : s);
            }
        }
        catch (Exception ex) {
            System.out.println("windows 7方式未获取到网卡地址");
        }
        return sb.toString();
    }

    /**
    * 获取MAC地址
    *
    * @param argc
    *            运行参数.
    * @throws Exception
    */
    public static String getMACAddress() {
        // windows
        String mac = getWindowsMACAddress();
        // windows7
        if (isNull(mac)) {
            mac = getWindows7MACAddress();
        }
        // unix
        if (isNull(mac)) {
            mac = getUnixMACAddress();
        }

        if (!isNull(mac)) {
            mac = mac.replace("-", "");
        }
        else {
            mac = "ABCDEFGHIJ";
        }
        return mac.toLowerCase();
    }

    public static boolean isNull(Object strData) {
        if (strData == null || String.valueOf(strData).trim().equals("")) {
            return true;
        }
        return false;
    }
    
    /**
    * @Title: getIpAddr  
    * @Description: 获取ip地址  
    * @param @param request
    * @param @return    设定文件  
    * @return String    返回类型  
    * @throws
     */
    public static String getIpAddr(HttpServletRequest request) {  
        String ip = request.getHeader("x-forwarded-for");  
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    } 
    
    public static String getClientIp(HttpServletRequest request){
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }
    
    public static InetAddress getLocalHostLANAddress() throws Exception {
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            return jdkSuppliedAddress;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
