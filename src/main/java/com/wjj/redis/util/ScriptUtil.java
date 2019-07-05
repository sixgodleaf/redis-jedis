package com.wjj.redis.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

/**
 * @Author: wangjunjie 2019/7/3 14:58
 * @Description: 读取redis lua脚本
 * @Version: 1.0.0
 * @Modified By: xxx 2019/7/3 14:58
 */
public class ScriptUtil {

    public static String getScript(String path) throws IOException {
        StringBuilder script = new StringBuilder();
        InputStream inputStream = ScriptUtil.class.getResourceAsStream(path);

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String str;
        while ((str = br.readLine()) != null) {
            script.append(str);
        }

        return script.toString();
    }
}
