package com.lhq.plugin.mybatis.logs.console;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;

/**
 * @author 钱多多--李海青
 *
 */
public class DocumentListener implements IDocumentListener {
    private static final String preparing  = " ==>  Preparing: ";
    private static final String parameters = " ==> Parameters: ";
    private static final String total      = " <==      Total: ";

    private Map<String, String> cache = new HashMap<>();
    private StringBuilder buffer = new StringBuilder();
    private String consoleName;


    public DocumentListener(String consoleName) {
        super();
        this.consoleName = consoleName;
    }

    @Override
    public void documentChanged(DocumentEvent event) {
        String text = event.fText;
        if (text == null || "".equals(text)) {
            return;
        }
        String[] texts = text.split(System.getProperty("line.separator"));
        for (String line : texts) {
            if (line == null || "".equals(line)) {
                continue;
            }
            if (line.contains(preparing)) {
                cache.put(preparing, line.split(preparing)[1].trim());
            } else if (line.contains(parameters)) {
                String[] params = line.split(parameters);
                if (params.length > 1) {
                    cache.put(parameters, line.split(parameters)[1].trim());
                } else {
                    cache.put(parameters, "");
                }
            } else if (line.contains(total)) {
                cache.put(total, line.split(total)[1].trim());
                this.printSql();
            }
        }
    }

    /**
     * 输出SQL
     */
    private void printSql() {
        if (cache.get(preparing) != null && cache.get(parameters) != null && cache.get(total) != null) {
            List<String> params = Arrays.asList(cache.get(parameters).split(","));

            for (String param : params) {
                param = param.trim().replaceAll("\\(\\w+\\)", "");
                cache.put(preparing, cache.get(preparing).replaceFirst("\\?", "'" + param + "'"));
            }
            buffer.setLength(0);
            buffer.append(this.consoleName + "\n");
            buffer.append("   SQL:  " + cache.get(preparing) + "\n");
            buffer.append("PARAMS:  " + cache.get(parameters) + "\n");
            buffer.append(" TOTAL:  " + cache.get(total) + "\n");
            buffer.append("------------------------------------------------------------------------\n");
            CurrentConsole.println(buffer.toString());
            cache.clear();
        }
    }

    @Override
    public void documentAboutToBeChanged(DocumentEvent arg0) {

    }

    public String getConsoleName() {
        return consoleName;
    }

    public void setConsoleName(String consoleName) {
        this.consoleName = consoleName;
    }

}
