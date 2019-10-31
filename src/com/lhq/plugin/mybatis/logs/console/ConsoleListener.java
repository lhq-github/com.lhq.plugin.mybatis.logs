package com.lhq.plugin.mybatis.logs.console;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleListener;
import org.eclipse.ui.console.TextConsole;

/**
 * console监听器
 * 
 * @author lhq
 */
public class ConsoleListener implements IConsoleListener {

    @Override
    public void consolesAdded(IConsole[] consoles) {
        for (IConsole temp : consoles) {
            this.addListenerDocument(temp);
        }
    }

    @Override
    public void consolesRemoved(IConsole[] consoles) {
        for (IConsole temp : consoles) {
            this.removeListenerDocument(temp);
        }
    }

    private Map<String, IDocumentListener> docLisMap = new HashMap<>();

    /**
     * 监听console中document的变化
     * 
     * @param console
     */
    public void addListenerDocument(IConsole console) {
        if (CurrentConsole.CUR_CONSOLE_NAME.equals(console.getName())) {
            return;
        }
        if (console instanceof TextConsole) {
            TextConsole tConsole = (TextConsole) console;
            IDocument document = tConsole.getDocument();
            IDocumentListener documentListener = docLisMap.get(tConsole.getName());
            if (documentListener == null) {
                documentListener = new DocumentListener(tConsole.getName());
                docLisMap.put(tConsole.getName(), documentListener);
            }
            document.addDocumentListener(documentListener);
        }
    }

    /**
     * 移除监听
     * 
     * @param console
     */
    public void removeListenerDocument(IConsole console) {
        if (CurrentConsole.CUR_CONSOLE_NAME.equals(console.getName())) {
            return;
        }
        if (console instanceof TextConsole) {
            TextConsole tConsole = (TextConsole) console;
            IDocument document = tConsole.getDocument();

            IDocumentListener documentListener = docLisMap.get(tConsole.getName());
            if (documentListener != null) {
                document.removeDocumentListener(documentListener);
            }
        }
    }

}
