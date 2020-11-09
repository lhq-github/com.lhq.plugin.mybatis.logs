package com.lhq.plugin.mybatis.logs.handlers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import com.lhq.plugin.mybatis.logs.console.CurrentConsole;

public class PluginHandler extends AbstractHandler {
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        this.getSelectedText();
        return null;
    }

    private void getSelectedText() {
        try {
            IWorkbenchPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();
            IViewPart view = part.getSite().getPage().findView("org.eclipse.ui.console.ConsoleView");
            if (view != null) {
                ISelection selection = view.getViewSite().getSelectionProvider().getSelection();
                if (selection instanceof TextSelection) {
                    TextSelection text = (TextSelection) selection;
                    this.parseText(text.getText());
                }
            }
        } catch (Exception e) {
        }
    }

    // MyBatis
    private static final String preparing1 = " ==>  Preparing: ";
    private static final String parameters1 = " ==> Parameters: ";
    private static final String total = " <==      Total: ";
    // iBatis
    private static final String preparing2 = " Executing Statement: ";
    private static final String parameters2 = " Parameters: ";
    private static final String types = " Types: ";

    private Map<String, String> cache = new HashMap<String, String>();
    private StringBuilder buffer = new StringBuilder();

    private void parseText(String text) {
        if (text == null || "".equals(text)) {
            return;
        }
        cache.clear();
        buffer.setLength(0);
        String[] texts = text.split(System.getProperty("line.separator"));
        for (String line : texts) {
            if (line == null || "".equals(line)) {
                continue;
            }
            // MyBatis
            if (line.contains(preparing1)) {
                cache.put(preparing1, line.split(preparing1)[1].trim());
            } else if (line.contains(parameters1)) {
                String[] params = line.split(parameters1);
                if (params.length > 1) {
                    cache.put(parameters1, line.split(parameters1)[1].trim());
                } else {
                    cache.put(parameters1, "");
                }
            } else if (line.contains(total)) {
                cache.put(total, line.split(total)[1].trim());
                this.printMyBatisSql();
                continue;
            }
            // iBatis
            if (line.contains(preparing2)) {
                cache.put(preparing2, line.split(preparing2)[1].trim());
            } else if (line.contains(parameters2)) {
                if (line.split(parameters2).length > 1) {
                    cache.put(parameters2, line.split(parameters2)[1].trim());
                }
            } else if (line.contains(types)) {
                this.printIBatisSql();
                continue;
            }
        }
    }

    /**
     * 输出MyBatis SQL
     */
    private void printMyBatisSql() {
        if (cache.get(preparing1) != null && cache.get(parameters1) != null && cache.get(total) != null) {
            List<String> params = Arrays.asList(cache.get(parameters1).split(", "));

            for (String param : params) {
                param = param.trim().replaceAll("\\(\\w+\\)", "");
                cache.put(preparing1, cache.get(preparing1).replaceFirst("\\?", "'" + param + "'"));
            }
            buffer.setLength(0);
            buffer.append("   SQL:  " + cache.get(preparing1).replaceAll(" +", " ") + "\n");
            buffer.append("PARAMS:  " + cache.get(parameters1) + "\n");
            buffer.append(" TOTAL:  " + cache.get(total) + "\n");
            buffer.append("------------------------------------------------------------------------\n");
            CurrentConsole.println(buffer.toString());
            cache.clear();
        }
    }

    /**
     * 输出iBatis SQL
     */
    private void printIBatisSql() {
        if (cache.get(preparing2) != null && cache.get(parameters2) != null) {
            String parameters = cache.get(parameters2);
            parameters = parameters.substring(1, parameters.length() - 1);
            List<String> params = Arrays.asList(parameters.split(", "));

            for (String param : params) {
                param = param.trim().replaceAll("\\(\\w+\\)", "");
                cache.put(preparing2, cache.get(preparing2).replaceFirst("\\?", "'" + param + "'"));
            }
            buffer.setLength(0);
            buffer.append("   SQL:  " + cache.get(preparing2).replaceAll(" +", " ") + "\n");
            buffer.append("PARAMS:  " + cache.get(parameters2) + "\n");
            buffer.append(" TOTAL:  \n");
            buffer.append("------------------------------------------------------------------------\n");
            CurrentConsole.println(buffer.toString());
            cache.clear();
        }
    }

}
