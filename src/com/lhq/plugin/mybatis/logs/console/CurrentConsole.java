package com.lhq.plugin.mybatis.logs.console;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

/**
 * 当前插件自己的控制台
 * 主要用于打印输出
 * 
 * @author lhq
 */
public class CurrentConsole {
    // 当前插件控制台名称
    public static final String CUR_CONSOLE_NAME = "Mybatis Logs";

    private static IConsoleManager consoleManager = null;
    private static MessageConsole messageConsole = null;
    private static MessageConsoleStream messageConsoleStream = null;

    static {
        consoleManager = ConsolePlugin.getDefault().getConsoleManager();
        messageConsole = new MessageConsole(CUR_CONSOLE_NAME, null);
        consoleManager.addConsoles(new IConsole[] { messageConsole });
        messageConsoleStream = messageConsole.newMessageStream();
    }

    private CurrentConsole() {
    }

    public static void println(String message) {
        if (message != null) {
            consoleManager.showConsoleView(messageConsole);
            messageConsoleStream.println(message);
        }
    }

}
