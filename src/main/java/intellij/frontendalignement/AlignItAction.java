package intellij.frontendalignement;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.TextRange;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;

public class AlignItAction extends AnAction {
  @Override
  public void update(@NotNull final AnActionEvent e) {
    final Project project = e.getProject();
    final Editor  editor  = e.getData(CommonDataKeys.EDITOR);

    // Set visibility only in case of existing project and editor and if a selection exists
    e.getPresentation().setEnabledAndVisible( project != null
                                              && editor != null
                                              && editor.getSelectionModel().hasSelection());
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent event) {
    final Editor   editor         = Objects.requireNonNull(event.getData(CommonDataKeys.EDITOR));
    String         regex          = Messages.showInputDialog(editor.getContentComponent(), "Enter regex to align with:", "Front End Alignment", Messages.getQuestionIcon());
    SelectionModel selectionModel = editor.getSelectionModel();

    if(selectionModel.getSelectedText() != null) {
      Document     document    = editor.getDocument();
      final int    startOffset = document.getLineStartOffset(document.getLineNumber(selectionModel.getSelectionStart()));
      final int    endOffset   = document.getLineEndOffset(document.getLineNumber(selectionModel.getSelectionEnd()));
      final String text        = document.getText(new TextRange(startOffset, endOffset));

      final String newText = (new RegexTextAligner(text, regex)).alignText();

      WriteCommandAction.runWriteCommandAction(editor.getProject(), () -> editor.getDocument().replaceString(startOffset, endOffset, newText));
    }
  }
}
