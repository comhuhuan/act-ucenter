/**   
 * @Title: IpUtils.java 
 * @Package com.act.web.util 
 * @Description: (关于IP操作公用类) 
 * @author   fmj
 * @date 2017-2-8 下午8:32:25 
 * @version V1.0   
 */
package com.act.ucenter.common.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class IpUtil {
	
	/**
	 * 根据 请求获取ip地址
	 */
	public static String getIpAddrByRequest(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	
	//获取本机IP	
	public static String getRealIp() throws SocketException {
		String localip = null;// 本地IP，如果没有配置外网IP则返回它
		String netip = null;// 外网IP
		Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = null;
		boolean finded = false;// 是否找到外网IP
		while (netInterfaces.hasMoreElements() && !finded) {
			NetworkInterface ni = netInterfaces.nextElement();
			Enumeration<InetAddress> address = ni.getInetAddresses();
			while (address.hasMoreElements()) {
				ip = address.nextElement();
				if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
					netip = ip.getHostAddress();
					finded = true;
					break;
				} else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
					localip = ip.getHostAddress();
				}
			}
		}
		if (netip != null && !"".equals(netip)) {
			return netip;
		} else {
			return localip;
		}
	}
	
	
	//ip转换为long
	public static long ipToLong(String ipString){
		long result = 0;
	    java.util.StringTokenizer token = new java.util.StringTokenizer(ipString,".");
	    result += Long.parseLong(token.nextToken())<<24;
	    result += Long.parseLong(token.nextToken())<<16;
	    result += Long.parseLong(token.nextToken())<<8;
	    result += Long.parseLong(token.nextToken());
	    return result;
	}
	
}
