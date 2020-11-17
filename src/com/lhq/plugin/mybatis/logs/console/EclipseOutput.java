package com.lhq.plugin.mybatis.logs.console;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

/**
 * 当前插件自己的控制台，主要用于打印输出
 * 
 * @author lhq
 */
public class EclipseOutput implements Output {
    // 当前插件控制台名称
    public static final String CUR_CONSOLE_NAME = "Mybatis Logs";

    private IConsoleManager consoleManager;
    private MessageConsole messageConsole;
    private MessageConsoleStream messageConsoleStream;

    private EclipseOutput() {
        this.messageConsole = new MessageConsole(CUR_CONSOLE_NAME, null);
        this.consoleManager = ConsolePlugin.getDefault().getConsoleManager();
        this.consoleManager.addConsoles(new IConsole[] { this.messageConsole });
        this.messageConsoleStream = this.messageConsole.newMessageStream();
    }

    public static Output output = new EclipseOutput();

    @Override
    public void print(String message) {
        if (message != null) {
            this.consoleManager.showConsoleView(messageConsole);
            this.messageConsoleStream.print(message);
        }

    }

    @Override
    public void println(String message) {
        if (message != null) {
            this.consoleManager.showConsoleView(messageConsole);
            this.messageConsoleStream.println(message);
        }
    }

}
