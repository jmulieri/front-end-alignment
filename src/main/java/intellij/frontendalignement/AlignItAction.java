package intellij.frontendalignement;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;

public class AlignItAction extends EditorAction {
  public AlignItAction() {
    super(new EditorActionHandler() {
      @Override
      public void execute(Editor editor, DataContext dataContext) {
        Application application = ApplicationManager.getApplication();
        FrontEndAlignment alignment = application.getComponent(FrontEndAlignment.class);
        alignment.alignText(editor);
      }
    });
  }
}
