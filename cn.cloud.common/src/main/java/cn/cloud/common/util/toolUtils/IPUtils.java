package cn.cloud.common.util.toolUtils;



public class IPUtils {
	
	public static Long getIpNum(final String IP) {
		Long iPNum = 0L;
		
		final String iPString = IP.trim();
		if (iPString != null  && iPString.length() !=0) {
			
			
			final String[] subIp = iPString.split("\\.");
			
			for (String string : subIp) {
				iPNum = iPNum << 8;
				iPNum += Integer.parseInt(string);
			}
		}
		return iPNum;
	}
	
	public static String getIpString(final Long num) {
		final Long[] andNumbers = {0xff000000L, 0x00ff0000L, 0x0000ff00L, 0x000000ffL};
		final StringBuilder ipBuilder = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			ipBuilder.append(String.valueOf((num & andNumbers[i]) >> 8*(3-i)));
			if (i != 3) {
				ipBuilder.append(".");
			}
		}
		return ipBuilder.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(getIpNum("192.168.25.182"));
		
		System.out.println(getIpString(getIpNum("192.168.25.182")));
	}

}
