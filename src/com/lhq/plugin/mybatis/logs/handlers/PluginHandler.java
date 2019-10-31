package com.lhq.plugin.mybatis.logs.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.lhq.plugin.mybatis.logs.console.CurrentConsole;

public class PluginHandler extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        boolean status = CurrentConsole.startListener();
        String temp = status ? "started" : "stopped";
        StringBuilder buffer = new StringBuilder();
        buffer.append("------------------------------------------------------------------------\n");
        buffer.append("-------------------- mybatis logs plugin " + temp + " -----------------------\n");
        buffer.append("------------------------------------------------------------------------\n");
        CurrentConsole.showAndPrintln(buffer.toString());
        return null;
    }

}
