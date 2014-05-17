package intellij.frontendalignement;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTextAligner {
  String       text;
  Pattern      pattern;
  List<String> lines;
  int          maxIdx;

  public RegexTextAligner(String text, String regex) {
    this.text = text;
    this.pattern = Pattern.compile(regex);
    this.lines = Arrays.asList(text.split("\n", -1));
  }

  public String alignText() {
    return StringUtils.join(alignLines(buildLinesWithIndices()), "\n");
  }

  private List<String> alignLines(List<LineWithIndex> linesWithIndices) {
    for(int i = 0; i < linesWithIndices.size(); i++) {
      LineWithIndex lineWithIndex = linesWithIndices.get(i);
      if(lineWithIndex.idx < maxIdx) {
        lines.set(lineWithIndex.lineIdx, lineWithIndex.injectSpaces(maxIdx));
      }
    }
    return lines;
  }

  private List<LineWithIndex> buildLinesWithIndices() {
    maxIdx = 0;
    List<LineWithIndex> linesWithIndices = new ArrayList<LineWithIndex>();
    for(int i = 0; i < lines.size(); i++) {
      String line = lines.get(i);
      Matcher matcher = pattern.matcher(line);
      if(matcher.find()) {
        LineWithIndex lineWithIndex = new LineWithIndex(line, i, matcher.start());
        if(lineWithIndex.idx > maxIdx) {
          maxIdx = lineWithIndex.idx;
        }
        linesWithIndices.add(lineWithIndex);
      }
    }
    return linesWithIndices;
  }

  class LineWithIndex {
    String line;
    int    lineIdx;
    int    idx;

    LineWithIndex(String line, int lineIdx, int idx) {
      this.line    = line;
      this.lineIdx = lineIdx;
      this.idx     = idx;
    }

    String injectSpaces(int maxIdx) {
      line = line.substring(0, idx) + StringUtils.leftPad("", maxIdx - idx) + line.substring(idx);
      return line;
    }
  }
}
