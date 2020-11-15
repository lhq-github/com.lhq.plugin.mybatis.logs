package com.lhq.plugin.mybatis.logs.toolkits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.lhq.plugin.mybatis.logs.console.CurrentConsole;

public class LogsToolkit {

    private LogsToolkit() {
    }

    // MyBatis
    private static final String preparing1 = " ==>  Preparing: ";
    private static final String parameters1 = " ==> Parameters: ";
    // iBatis
    private static final String preparing2 = " Executing Statement: ";
    private static final String parameters2 = " Parameters: ";

    private static Map<String, String> cache = new HashMap<String, String>();

    public static void parseText(String text) {
        if (text == null || "".equals(text)) {
            return;
        }
        cache.clear();
        String[] texts = text.split(System.getProperty("line.separator"));
        for (String line : texts) {
            if (line == null || "".equals(line)) {
                continue;
            }
            // MyBatis
            if (line.contains(preparing1)) {
                cache.put(preparing1, line.split(preparing1)[1].trim());
                continue;
            }
            if (line.contains(parameters1)) {
                String[] params = line.split(parameters1);
                if (params.length > 1) {
                    cache.put(parameters1, line.split(parameters1)[1].trim());
                } else {
                    cache.put(parameters1, "");
                }
                printMyBatisSql();
                continue;
            }

            // iBatis
            if (line.contains(preparing2)) {
                cache.put(preparing2, line.split(preparing2)[1].trim());
                continue;
            }
            if (line.contains(parameters2)) {
                if (line.split(parameters2).length > 1) {
                    cache.put(parameters2, line.split(parameters2)[1].trim());
                }
                printIBatisSql();
                continue;
            }
        }
    }

    /**
     * 输出MyBatis SQL
     */
    private static void printMyBatisSql() {
        if (cache.get(preparing1) != null && cache.get(parameters1) != null) {
            String parameters = cache.get(parameters1);
            if (parameters.endsWith(")")) {
                parameters += ", ";
            }
            List<String> params = new ArrayList<String>(Arrays.asList(parameters.split("\\(\\w+\\), ")));

            Iterator<String> it = params.iterator();
            while (it.hasNext()) {
                String param = it.next();
                if (cache.get(preparing1).contains("?")) {
                    cache.put(preparing1, cache.get(preparing1).replaceFirst("\\?", "'" + param + "'"));
                    it.remove();
                }
            }
            StringBuilder buffer = new StringBuilder();
            buffer.setLength(0);
            buffer.append("   SQL:  " + cache.get(preparing1).replaceAll(" +", " ") + "\n");
            buffer.append("PARAMS:  " + cache.get(parameters1) + "\n");
            if (!params.isEmpty()) {
                buffer.append("注意：由于SQL中某一项参数中包含“(\\w+), ”使其被解析成多个参数，导致最终多出部分参数: " + params.toString() + "\n");
            }
            buffer.append("------------------------------------------------------------------------\n");
            // System.out.println(buffer.toString());
            CurrentConsole.println(buffer.toString());
            cache.clear();
        }
    }

    /**
     * 输出iBatis SQL
     */
    private static void printIBatisSql() {
        if (cache.get(preparing2) != null && cache.get(parameters2) != null) {
            String parameters = cache.get(parameters2);
            parameters = parameters.substring(1, parameters.length() - 1);
            List<String> params = new ArrayList<String>(Arrays.asList(parameters.split(", ")));

            Iterator<String> it = params.iterator();
            while (it.hasNext()) {
                String param = it.next();
                if (cache.get(preparing2).contains("?")) {
                    cache.put(preparing2, cache.get(preparing2).replaceFirst("\\?", "'" + param + "'"));
                    it.remove();
                }
            }
            StringBuilder buffer = new StringBuilder();
            buffer.setLength(0);
            buffer.append("   SQL:  " + cache.get(preparing2).replaceAll(" +", " ") + "\n");
            buffer.append("PARAMS:  " + cache.get(parameters2) + "\n");
            if (!params.isEmpty()) {
                buffer.append("注意：由于SQL中某一项参数中包含“, ”使其被解析成多个参数，导致最终多出部分参数: " + params.toString() + "\n");
            }
            buffer.append("------------------------------------------------------------------------\n");
            // System.out.println(buffer.toString());
            CurrentConsole.println(buffer.toString());
            cache.clear();
        }
    }
}
