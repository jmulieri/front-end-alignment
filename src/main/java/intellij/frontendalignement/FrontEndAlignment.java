package intellij.frontendalignement;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.popup.ComponentPopupBuilder;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.util.TextRange;

import javax.swing.*;

public class FrontEndAlignment implements ApplicationComponent {
  public FrontEndAlignment() {
  }
  public void initComponent() {}

  public void disposeComponent() {}

  public String getComponentName() {
    return "FrontEndAlignement";
  }

  public void alignText(Editor editor) {
    String regex = Messages.showInputDialog(editor.getContentComponent(), "Enter regex to align with:", "Front End Alignment", null);
    SelectionModel selectionModel = editor.getSelectionModel();
    if(selectionModel.getSelectedText() != null) {
      Document document      = editor.getDocument();
      final int startOffset  = document.getLineStartOffset(document.getLineNumber(selectionModel.getSelectionStart()));
      final int endOffset    = document.getLineEndOffset(document.getLineNumber(selectionModel.getSelectionEnd()));
      final String text      = document.getText(new TextRange(startOffset, endOffset));
      final Editor editorArg = editor;

      final String newText   = (new RegexTextAligner(text, regex)).alignText();

      ApplicationManager.getApplication().runWriteAction(new Runnable() {
        @Override
        public void run() {
          editorArg.getDocument().replaceString(startOffset, endOffset, newText);
        }
      });
    }
  }
}
