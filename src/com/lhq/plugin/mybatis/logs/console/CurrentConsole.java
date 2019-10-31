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
    public static final String CUR_CONSOLE_NAME = "mybatis log console";
    // 是否已经开始监听
    private static boolean isListened = false;

    private static IConsoleManager consoleManager = null;
    private static MessageConsole messageConsole = null;
    private static MessageConsoleStream messageConsoleStream = null;
    private static ConsoleListener consoleListener = new ConsoleListener();

    static {
        consoleManager = ConsolePlugin.getDefault().getConsoleManager();
        IConsole[] consoles = consoleManager.getConsoles();
        if (consoles.length > 0) {
            for (IConsole temp : consoles) {
                if (CUR_CONSOLE_NAME.equals(temp.getName())) {
                    messageConsole = (MessageConsole) temp;
                    break;
                }
            }
        }

        if (messageConsole == null) {
            messageConsole = new MessageConsole(CUR_CONSOLE_NAME, null);
            consoleManager.addConsoles(new IConsole[] { messageConsole });
        }
        consoleManager.showConsoleView(messageConsole);
        messageConsoleStream = messageConsole.newMessageStream();
    }

    private CurrentConsole() {
    }

    public static synchronized boolean startListener() {
        IConsole[] consoles = consoleManager.getConsoles();
        for (IConsole console : consoles) {
            if (isListened) {
                consoleListener.removeListenerDocument(console);
            } else {
                consoleListener.addListenerDocument(console);
            }
        }
        if (isListened) {
            isListened = false;
            consoleManager.removeConsoleListener(consoleListener);
        } else {
            isListened = true;
            consoleManager.addConsoleListener(consoleListener);
        }
        return isListened;
    }

    public static void showAndPrintln(String message) {
        if (message != null) {
            consoleManager.showConsoleView(messageConsole);
            messageConsoleStream.println(message);
        }
    }
    public static void print(String message) {
        if (message != null) {
            messageConsoleStream.print(message);
        }
    }

    public static void println(String message) {
        if (message != null) {
            messageConsoleStream.println(message);
        }
    }

}
