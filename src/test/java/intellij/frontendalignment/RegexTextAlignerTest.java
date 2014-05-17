package intellij.frontendalignment;

import intellij.frontendalignement.RegexTextAligner;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class RegexTextAlignerTest {
  @Test
  public void alignEqualsSign() {
    String textIn =
      "  int i = 123;\n" +
      "  int j  = 456;";
    String textOut =
      "  int i  = 123;\n" +
      "  int j  = 456;";
    RegexTextAligner aligner = new RegexTextAligner(textIn, "=");
    assertEquals(textOut, aligner.alignText());
  }

  @Test
  public void alignWithDifferingLeadingSpacing() {
    String textIn =
      " int i = 123;\n" +
      "  int j   = 456;";
    String textOut =
      " int i    = 123;\n" +
      "  int j   = 456;";
    RegexTextAligner aligner = new RegexTextAligner(textIn, "=");
    assertEquals(textOut, aligner.alignText());
  }

  @Test
  public void alignHashRocket() {
    String textIn =
      "  { woot => 123,\n" +
      "    foobario => 'stringage',\n" +
      "    yeppers => 23.23 }";
    String textOut =
      "  { woot     => 123,\n" +
      "    foobario => 'stringage',\n" +
      "    yeppers  => 23.23 }";
    RegexTextAligner aligner = new RegexTextAligner(textIn, "=>");
    assertEquals(textOut, aligner.alignText());
  }

  @Test
  public void alignWithSomeLinesNotMatchingRegex() {
    String textIn =
      "  someFunction();\n"+
      "  int i = 123;\n" +
      "  int j   = 456;\n" +
      "  someFunction();";
    String textOut =
      "  someFunction();\n"+
      "  int i   = 123;\n" +
      "  int j   = 456;\n" +
      "  someFunction();";
    RegexTextAligner aligner = new RegexTextAligner(textIn, "=");
    assertEquals(textOut, aligner.alignText());
  }

  @Test
  public void alignWithPatternRegex() {
    String textIn =
      "abc 13  ba sdflkj\n" +
      "abc   13  ba sdflkj";
    String textOut =
      "abc   13  ba sdflkj\n" +
      "abc   13  ba sdflkj";
    RegexTextAligner aligner = new RegexTextAligner(textIn, "\\d\\d\\s+\\w");
    assertEquals(textOut, aligner.alignText());
  }
}
