package com.warehouse.manage.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Tool {

    /**
     * 获取订单数字
     * @return 订单数字
     */
    public static String getCurrentOrderId(int length){
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmSS");
        String timeStr = ft.format(dNow);

        Random rand = new Random();
        StringBuffer sb=new StringBuffer();
        for (int i=0;i<length;i++){
            int randNum = rand.nextInt(9)+1;
            String num=randNum+"";
            sb=sb.append(num);
        }
        String random=String.valueOf(sb);

        return timeStr + random;
    }
}
