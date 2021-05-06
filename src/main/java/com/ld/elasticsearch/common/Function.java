package com.ld.elasticsearch.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Function {
    public static String execCurl(String[] cmds) {
        ProcessBuilder process = new ProcessBuilder(cmds);
        Process p;
        try {
            p = process.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            return builder.toString();

        } catch (IOException e) {
            System.out.print("error");
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 将字符串ids处理成数字集合
     * @param ids
     * @return
     */
    public static List<Integer> idsRes(String ids){
        String[] idList = ids.split(",");
        // 将字符串数组转为List<Intger> 类型
        List<Integer> idIntList = new ArrayList<Integer>();
        for(String str : idList){
            idIntList.add(new Integer(str));
        }
        return idIntList;
    }
}
