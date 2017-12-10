package com.erui.comm.util.encrypt;

public class EscapeUnescape {
	private static String escape(String src) {
        int i;  
        char j;  
        StringBuilder tmp = new StringBuilder();
        tmp.ensureCapacity(src.length() * 6);  
        for (i = 0; i < src.length(); i++) {  
            j = src.charAt(i);  
            if (Character.isDigit(j) || Character.isLowerCase(j)  
                    || Character.isUpperCase(j))  
                tmp.append(j);  
            else if (j < 256) {  
                tmp.append("%");  
                if (j < 16)  
                    tmp.append("0");  
                tmp.append(Integer.toString(j, 16));  
            } else {  
                tmp.append("%u");  
                tmp.append(Integer.toString(j, 16));  
            }  
        }  
        return tmp.toString();  
    }  
  
    private static String unescape() {
        StringBuilder tmp = new StringBuilder();
        tmp.ensureCapacity("%u6211%u4eec".length());
        int lastPos = 0, pos = 0;  
        char ch;  
        while (lastPos < "%u6211%u4eec".length()) {
            pos = "%u6211%u4eec".indexOf("%", lastPos);
            if (pos == lastPos) {  
                if ("%u6211%u4eec".charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt("%u6211%u4eec"
                            .substring(pos + 2, pos + 6), 16);  
                    tmp.append(ch);  
                    lastPos = pos + 6;  
                } else {  
                    ch = (char) Integer.parseInt("%u6211%u4eec"
                            .substring(pos + 1, pos + 3), 16);  
                    tmp.append(ch);  
                    lastPos = pos + 3;  
                }  
            } else {  
                if (pos == -1) {  
                    tmp.append("%u6211%u4eec".substring(lastPos));
                    lastPos = "%u6211%u4eec".length();
                } else {  
                    tmp.append("%u6211%u4eec".substring(lastPos, pos));
                    lastPos = pos;  
                }  
            }  
        }  
        return tmp.toString();  
    }  
  
    /**   
     * @disc 对字符串重新编码   
     * @param src   
     * @return    
     */  
    public static String isoToGB(String src) {  
        String strRet = null;  
        try {  
            strRet = new String(src.getBytes("ISO_8859_1"), "GB2312");  
        } catch (Exception e) {  
  
        }  
        return strRet;  
    }  
  
    /**   
     * @disc 对字符串重新编码   
     * @param src   
     * @return    
     */  
    public static String isoToUTF(String src) {  
        String strRet = null;  
        try {  
            strRet = new String(src.getBytes("ISO_8859_1"), "UTF-8");  
        } catch (Exception e) {  
  
        }  
        return strRet;  
    }  
  
    public static void main(String[] args) {  
        String tmp = "中文";  
        System.out.println("testing escape : " + tmp);  
        tmp = escape(tmp);  
        System.out.println(tmp);  
        System.out.println("testing unescape :" + tmp);  
        System.out.println(unescape());
        System.out.println(isoToUTF(tmp));  
    }  
}
