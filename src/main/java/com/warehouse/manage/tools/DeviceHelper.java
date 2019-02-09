package com.warehouse.manage.tools;

import javax.servlet.http.HttpServletRequest;

public class DeviceHelper {

    /**
     * 判断设备请求的类型
     * @param request 请求
     * @return 1：微信 2：安卓 3：ios 4：pc
     */
    public static int checkDeviceType(HttpServletRequest request){
        int type = 0;
        String agent = request.getHeader("user-agent").toLowerCase();
        if (agent.contains("micromessenger")) {
            type = 1;
        } else  if (agent.contains("android")) {
            type = 2;
        } else if (agent.contains("iphone") || agent.contains("ipad") || agent.contains("ipod")){
            type = 3;
        }  else {
            type = 4;
        }
        return type;
    }
}
