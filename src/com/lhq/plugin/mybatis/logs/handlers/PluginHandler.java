package com.lhq.plugin.mybatis.logs.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import com.lhq.plugin.mybatis.logs.toolkits.LogsToolkit;

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
                    LogsToolkit.parseText(text.getText());
                }
            }
        } catch (Exception e) {
        }
    }


}
