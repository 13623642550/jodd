// Copyright (c) 2003-2014, Jodd Team (jodd.org). All Rights Reserved.

/* The following code was generated by JFlex 1.4.3-fixes on 12/9/13 7:44 PM */

package jodd.csselly;

import java.util.ArrayList;
import java.nio.CharBuffer;

/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3-fixes
 * on 12/9/13 7:44 PM from the specification file
 * <tt>csselly.flex</tt>
 */
final class CSSellyLexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 4096;

  /** lexical states */
  public static final int COMBINATOR = 6;
  public static final int YYINITIAL = 0;
  public static final int ATTR = 4;
  public static final int SELECTOR = 2;
  public static final int PSEUDO_FN = 8;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1,  1,  2,  2,  3,  3,  4, 4
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\2\1\10\1\7\1\2\1\11\1\6\22\2\1\10\1\2\1\13"+
    "\1\17\1\24\2\2\1\14\1\21\1\30\1\15\1\27\1\2\1\1"+
    "\1\12\1\2\12\5\1\20\2\2\1\22\1\26\2\2\32\0\1\16"+
    "\1\3\1\25\1\24\1\0\1\2\6\4\24\0\1\2\1\24\1\2"+
    "\1\23\1\2\uff80\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\3\0\1\1\1\0\1\2\3\3\1\4\1\5\1\6"+
    "\1\7\1\6\1\10\2\6\1\11\3\7\1\12\1\13"+
    "\1\1\1\14\1\15\1\16\6\7\1\0\1\2\1\17"+
    "\2\0\1\20\1\0\1\21\4\0\1\11\2\0\1\11"+
    "\7\0\1\22\3\0\2\2\1\17\1\20\1\23\1\21"+
    "\1\11\4\0\2\11\6\0\1\2\2\17\2\20\2\21"+
    "\2\11\2\0\1\11\4\0\1\22\3\0\1\22\1\2"+
    "\1\17\1\20\1\21\2\11\2\0\1\11\2\0\2\11"+
    "\7\0\1\2\1\17\1\20\1\21\1\11\2\0\1\11"+
    "\11\0\1\2\1\17\1\20\1\21\1\11\2\0\1\11"+
    "\5\0\1\22\2\0\1\17\1\20\1\21\1\11\11\0"+
    "\1\11\21\0";

  private static int [] zzUnpackAction() {
    int [] result = new int[184];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\31\0\62\0\113\0\144\0\175\0\226\0\257"+
    "\0\310\0\341\0\257\0\257\0\257\0\372\0\257\0\u0113"+
    "\0\u012c\0\u0145\0\u015e\0\u0177\0\u0190\0\257\0\257\0\u01a9"+
    "\0\u01c2\0\u01db\0\u01f4\0\u020d\0\u0226\0\u023f\0\u0258\0\u0271"+
    "\0\u028a\0\310\0\u02a3\0\u02bc\0\u02d5\0\u02ee\0\u0113\0\u0307"+
    "\0\u0320\0\u0339\0\u0352\0\u036b\0\u0177\0\u0384\0\u039d\0\u03b6"+
    "\0\u03cf\0\u015e\0\u0190\0\u020d\0\u0226\0\u0258\0\u0271\0\u028a"+
    "\0\257\0\u023f\0\u03e8\0\u0401\0\u041a\0\u0433\0\u044c\0\u0465"+
    "\0\257\0\u047e\0\u0497\0\u04b0\0\u04c9\0\u04e2\0\u04fb\0\u0514"+
    "\0\u052d\0\u0546\0\u055f\0\u0578\0\u0591\0\u05aa\0\u05c3\0\u05dc"+
    "\0\u05f5\0\u060e\0\u0627\0\u0640\0\u0659\0\u0672\0\u068b\0\u06a4"+
    "\0\u06bd\0\u06d6\0\u06ef\0\u0708\0\u0721\0\u073a\0\u0753\0\u0271"+
    "\0\u076c\0\u0785\0\u079e\0\u028a\0\u07b7\0\u07d0\0\u07e9\0\u0802"+
    "\0\u081b\0\u0834\0\u084d\0\u0866\0\u087f\0\u0898\0\u08b1\0\u08ca"+
    "\0\u08e3\0\u08fc\0\u0915\0\u092e\0\u0947\0\u0960\0\u0979\0\u0992"+
    "\0\u09ab\0\u09c4\0\u09dd\0\u09f6\0\u0a0f\0\u0a28\0\u0a41\0\u0a5a"+
    "\0\u0a73\0\u0a8c\0\u0aa5\0\u0abe\0\u0ad7\0\u0af0\0\u0b09\0\u0b22"+
    "\0\u0b3b\0\u0b54\0\u0b6d\0\u0b86\0\u0b9f\0\u0bb8\0\u0bd1\0\u0bea"+
    "\0\u0c03\0\u0c1c\0\u0c35\0\u0c4e\0\u0c67\0\u0c80\0\u0753\0\u0c99"+
    "\0\u0cb2\0\u0ccb\0\u0ce4\0\u0cfd\0\u0d16\0\u0d2f\0\u0d48\0\u0d61"+
    "\0\u0d7a\0\u0d93\0\u0dac\0\u0dc5\0\u0dde\0\u0df7\0\u0e10\0\u0e29"+
    "\0\u0e42\0\u0e5b\0\u0e74\0\u0e8d\0\u0ea6\0\u0ebf\0\u0ed8\0\u0ef1"+
    "\0\u0f0a\0\u0f23\0\u0f3c\0\u0f55\0\u0f6e\0\u0f87\0\u0fa0\0\u0fb9";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[184];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int ZZ_TRANS [] = {
    5, 6, 7, 8, 5, 7, 9, 9, 9, 9, 
    7, 7, 7, 10, 7, 7, 7, 7, 7, 7, 
    7, 7, 7, 7, 7, 11, 11, 11, 11, 11, 
    11, 11, 12, 11, 11, 13, 11, 11, 11, 14, 
    15, 16, 11, 11, 11, 11, 11, 11, 11, 11, 
    17, 18, 12, 19, 17, 12, 20, 20, 20, 20, 
    12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 
    12, 21, 12, 12, 12, 22, 22, 22, 22, 22, 
    22, 23, 23, 23, 23, 22, 22, 22, 22, 22, 
    22, 22, 22, 22, 24, 22, 22, 25, 26, 22, 
    27, 27, 12, 28, 27, 27, 29, 29, 29, 29, 
    30, 31, 32, 12, 12, 12, 12, 12, 12, 12, 
    12, 12, 12, 27, 12, 5, 5, -1, 33, 5, 
    5, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    5, -1, -1, 33, 5, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    5, 5, 5, 5, 34, 34, -1, -1, 5, -1, 
    5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 
    5, 5, 5, 5, 5, -1, -1, -1, -1, -1, 
    -1, 9, 9, 9, 9, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    35, 36, -1, 37, 35, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, 38, 38, -1, 39, 38, 
    38, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    40, 41, -1, 42, 40, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, 43, -1, -1, -1, 
    -1, -1, -1, -1, -1, 17, 17, -1, 44, 17, 
    17, 45, 45, 45, 45, -1, -1, -1, 46, -1, 
    -1, -1, -1, 47, 46, 46, -1, -1, -1, -1, 
    17, -1, -1, 44, 17, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, 17, 17, 17, 17, 48, 
    48, -1, -1, 17, -1, 17, 17, 17, 17, 17, 
    17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 
    17, 49, -1, 44, 17, -1, 50, 50, 50, 50, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, 23, 23, 23, 23, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, 24, -1, -1, 25, 26, -1, 
    -1, -1, -1, -1, -1, -1, 24, 24, 24, 24, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, 25, 25, 25, 25, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, 26, 26, 26, 26, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, 51, 51, -1, 52, 51, 
    51, 51, 51, 51, 51, 53, 54, 55, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, 51, 56, 
    51, 51, 51, 51, 51, 51, -1, -1, 51, -1, 
    51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 
    51, 51, 51, 51, 51, 51, 51, -1, 52, 51, 
    51, 57, 57, 57, 57, 53, 54, 55, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, 51, -1, 
    -1, -1, -1, -1, -1, 51, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, 54, 54, 54, 58, 54, 
    54, -1, -1, 54, -1, 54, 51, 54, 54, 54, 
    54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 
    55, 55, 55, 59, 55, 55, -1, -1, 55, -1, 
    55, 55, 51, 55, 55, 55, 55, 55, 55, 55, 
    55, 55, 55, 55, 55, 5, 5, -1, 33, 60, 
    60, 61, 5, 5, 5, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    35, 35, -1, 37, 35, 35, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, 35, -1, -1, 37, 35, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    35, 35, 35, 35, 62, 62, -1, -1, 35, -1, 
    35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 
    35, 35, 35, 35, 35, 38, 38, 38, 38, 63, 
    63, -1, -1, 38, -1, 38, 38, 38, 38, 38, 
    38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 
    40, 40, -1, 42, 40, 40, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, 64, -1, -1, 
    -1, -1, -1, -1, -1, 40, -1, -1, 42, 40, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    40, 40, 40, 40, 65, 65, -1, -1, 40, -1, 
    40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 
    40, 40, 40, 40, 40, 40, 41, -1, 42, 40, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, 45, 45, 45, 45, 
    -1, -1, -1, 46, -1, -1, -1, -1, 47, 46, 
    46, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, 47, -1, -1, -1, -1, -1, -1, 
    66, 67, -1, 68, 66, -1, 47, 47, 47, 47, 
    -1, 69, 70, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, 17, 17, -1, 44, 71, 
    71, 72, 17, 17, 17, -1, -1, -1, 46, -1, 
    -1, -1, -1, 47, 46, 46, -1, -1, -1, -1, 
    54, 54, 54, 58, 73, 73, 74, 54, 54, 54, 
    54, 75, 54, 54, 54, 54, 54, 54, 54, 54, 
    54, 54, 54, 54, 54, 55, 55, 55, 59, 76, 
    76, 77, 55, 55, 55, 55, 55, 78, 55, 55, 
    55, 55, 55, 55, 55, 55, 55, 55, 55, 55, 
    5, 5, -1, 33, 79, 79, 61, 5, 5, 5, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, 5, 5, -1, 33, 5, 
    5, -1, 5, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    35, 35, -1, 37, 80, 80, 81, 35, 35, 35, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, 38, 38, -1, 39, 82, 
    82, 83, 38, 38, 38, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    40, 40, -1, 42, 84, 84, 85, 40, 40, 40, 
    -1, -1, -1, -1, -1, -1, -1, 64, -1, -1, 
    -1, -1, -1, -1, -1, 66, 66, -1, 68, 66, 
    66, 86, 86, 86, 86, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    66, -1, -1, 68, 66, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, 66, 66, 66, 66, 87, 
    87, -1, -1, 66, -1, 66, 66, 66, 66, 66, 
    66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 
    69, 69, 69, 88, 69, 69, -1, -1, 69, -1, 
    69, 86, 69, 69, 69, 69, 69, 69, 69, 69, 
    69, 69, 69, 69, 69, 70, 70, 70, 89, 70, 
    70, -1, -1, 70, -1, 70, 70, 86, 70, 70, 
    70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 
    17, 17, -1, 44, 90, 90, 72, 17, 17, 17, 
    -1, -1, -1, 46, -1, -1, -1, -1, 47, 46, 
    46, -1, -1, -1, -1, 17, 17, -1, 44, 17, 
    17, 45, 17, 45, 45, -1, -1, -1, 46, -1, 
    -1, -1, -1, 47, 46, 46, -1, -1, -1, -1, 
    54, 54, 54, 58, 91, 91, 74, 54, 54, 54, 
    54, 51, 54, 54, 54, 54, 54, 54, 54, 54, 
    54, 54, 54, 54, 54, 54, 54, 54, 58, 54, 
    54, -1, 54, 54, -1, 54, 51, 54, 54, 54, 
    54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 
    75, 75, 54, 92, 75, 75, 51, 51, 75, 51, 
    93, 75, 94, 54, 54, 54, 54, 54, 54, 54, 
    54, 54, 54, 75, 95, 55, 55, 55, 59, 96, 
    96, 77, 55, 55, 55, 55, 55, 51, 55, 55, 
    55, 55, 55, 55, 55, 55, 55, 55, 55, 55, 
    55, 55, 55, 59, 55, 55, -1, 55, 55, -1, 
    55, 55, 51, 55, 55, 55, 55, 55, 55, 55, 
    55, 55, 55, 55, 55, 78, 78, 55, 97, 78, 
    78, 51, 51, 78, 51, 98, 94, 78, 55, 55, 
    55, 55, 55, 55, 55, 55, 55, 55, 78, 99, 
    5, 5, -1, 33, 100, 100, 61, 5, 5, 5, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, 35, 35, -1, 37, 101, 
    101, 81, 35, 35, 35, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    35, 35, -1, 37, 35, 35, -1, 35, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, 38, 38, -1, 39, 102, 
    102, 83, 38, 38, 38, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    38, 38, -1, 39, 38, 38, -1, 38, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, 40, 40, -1, 42, 103, 
    103, 85, 40, 40, 40, -1, -1, -1, -1, -1, 
    -1, -1, 64, -1, -1, -1, -1, -1, -1, -1, 
    40, 40, -1, 42, 40, 40, -1, 40, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, 64, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, 86, 86, 86, 86, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    66, 66, -1, 68, 104, 104, 105, 66, 66, 66, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, 69, 69, 69, 88, 106, 
    106, 107, 69, 69, 69, 69, 108, 69, 69, 69, 
    69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 
    70, 70, 70, 89, 109, 109, 110, 70, 70, 70, 
    70, 70, 111, 70, 70, 70, 70, 70, 70, 70, 
    70, 70, 70, 70, 70, 17, 17, -1, 44, 112, 
    112, 72, 17, 17, 17, -1, -1, -1, 46, -1, 
    -1, -1, -1, 47, 46, 46, -1, -1, -1, -1, 
    54, 54, 54, 58, 113, 113, 74, 54, 54, 54, 
    54, 51, 54, 54, 54, 54, 54, 54, 54, 54, 
    54, 54, 54, 54, 54, 75, 75, 75, 114, 115, 
    115, 74, 54, 75, 54, 75, 75, 75, 75, 75, 
    75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 
    54, 54, 54, 58, 54, 75, -1, -1, 54, -1, 
    54, 51, 54, 54, 54, 54, 54, 54, 54, 54, 
    54, 54, 54, 54, 54, 94, 94, 94, 116, 94, 
    94, -1, -1, 94, -1, 94, 78, 75, 94, 94, 
    94, 94, 94, 94, 94, 94, 94, 94, 94, 94, 
    55, 55, 55, 59, 117, 117, 77, 55, 55, 55, 
    55, 55, 51, 55, 55, 55, 55, 55, 55, 55, 
    55, 55, 55, 55, 55, 78, 78, 78, 118, 119, 
    119, 77, 55, 78, 55, 78, 78, 78, 78, 78, 
    78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 
    55, 55, 55, 59, 55, 78, -1, -1, 55, -1, 
    55, 55, 51, 55, 55, 55, 55, 55, 55, 55, 
    55, 55, 55, 55, 55, 5, 5, -1, 33, 120, 
    120, 61, 5, 5, 5, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    35, 35, -1, 37, 121, 121, 81, 35, 35, 35, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, 38, 38, -1, 39, 122, 
    122, 83, 38, 38, 38, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    40, 40, -1, 42, 123, 123, 85, 40, 40, 40, 
    -1, -1, -1, -1, -1, -1, -1, 64, -1, -1, 
    -1, -1, -1, -1, -1, 66, 66, -1, 68, 124, 
    124, 105, 66, 66, 66, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    66, 66, -1, 68, 66, 66, 86, 66, 86, 86, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, 69, 69, 69, 88, 125, 
    125, 107, 69, 69, 69, 69, 86, 69, 69, 69, 
    69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 
    69, 69, 69, 88, 69, 69, -1, 69, 69, -1, 
    69, 86, 69, 69, 69, 69, 69, 69, 69, 69, 
    69, 69, 69, 69, 69, 69, 69, 69, 88, 69, 
    69, 86, 86, 108, 86, 69, 86, 69, 69, 69, 
    69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 
    70, 70, 70, 89, 126, 126, 110, 70, 70, 70, 
    70, 70, 86, 70, 70, 70, 70, 70, 70, 70, 
    70, 70, 70, 70, 70, 70, 70, 70, 89, 70, 
    70, -1, 70, 70, -1, 70, 70, 86, 70, 70, 
    70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 
    70, 70, 70, 89, 70, 70, 86, 86, 111, 86, 
    70, 70, 86, 70, 70, 70, 70, 70, 70, 70, 
    70, 70, 70, 70, 70, 17, 17, -1, 44, 127, 
    127, 72, 17, 17, 17, -1, -1, -1, 46, -1, 
    -1, -1, -1, 47, 46, 46, -1, -1, -1, -1, 
    54, 54, 54, 58, 128, 128, 74, 54, 54, 54, 
    54, 51, 54, 54, 54, 54, 54, 54, 54, 54, 
    54, 54, 54, 54, 54, 75, 75, 54, 92, 115, 
    115, 129, 75, 75, 75, 93, 75, 94, 54, 54, 
    54, 54, 54, 54, 54, 54, 54, 54, 75, 95, 
    75, 75, 54, 92, 130, 130, 129, 75, 75, 75, 
    93, 75, 94, 54, 54, 54, 54, 54, 54, 54, 
    54, 54, 54, 75, 95, 94, 94, 94, 116, 131, 
    131, 132, 94, 94, 94, 94, 133, 133, 94, 94, 
    94, 94, 94, 94, 94, 94, 94, 94, 94, 94, 
    55, 55, 55, 59, 134, 134, 77, 55, 55, 55, 
    55, 55, 51, 55, 55, 55, 55, 55, 55, 55, 
    55, 55, 55, 55, 55, 78, 78, 55, 97, 119, 
    119, 135, 78, 78, 78, 98, 94, 78, 55, 55, 
    55, 55, 55, 55, 55, 55, 55, 55, 78, 99, 
    78, 78, 55, 97, 136, 136, 135, 78, 78, 78, 
    98, 94, 78, 55, 55, 55, 55, 55, 55, 55, 
    55, 55, 55, 78, 99, 5, 5, -1, 33, 137, 
    137, 61, 5, 5, 5, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    35, 35, -1, 37, 138, 138, 81, 35, 35, 35, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, 38, 38, -1, 39, 139, 
    139, 83, 38, 38, 38, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    40, 40, -1, 42, 140, 140, 85, 40, 40, 40, 
    -1, -1, -1, -1, -1, -1, -1, 64, -1, -1, 
    -1, -1, -1, -1, -1, 66, 66, -1, 68, 141, 
    141, 105, 66, 66, 66, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    69, 69, 69, 88, 142, 142, 107, 69, 69, 69, 
    69, 86, 69, 69, 69, 69, 69, 69, 69, 69, 
    69, 69, 69, 69, 69, 70, 70, 70, 89, 143, 
    143, 110, 70, 70, 70, 70, 70, 86, 70, 70, 
    70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 
    17, 17, -1, 44, 144, 144, 72, 17, 17, 17, 
    -1, -1, -1, 46, -1, -1, -1, -1, 47, 46, 
    46, -1, -1, -1, -1, 54, 54, 54, 58, 145, 
    145, 74, 54, 54, 54, 54, 51, 54, 54, 54, 
    54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 
    75, 75, 54, 92, 75, 75, 51, 75, 75, 51, 
    93, 75, 94, 54, 54, 54, 54, 54, 54, 54, 
    54, 54, 54, 75, 95, 75, 75, 54, 92, 146, 
    146, 129, 75, 75, 75, 93, 75, 94, 54, 54, 
    54, 54, 54, 54, 54, 54, 54, 54, 75, 95, 
    94, 94, 94, 116, 147, 147, 132, 94, 94, 94, 
    94, 78, 75, 94, 94, 94, 94, 94, 94, 94, 
    94, 94, 94, 94, 94, 94, 94, 94, 116, 94, 
    94, -1, 94, 94, -1, 94, 78, 75, 94, 94, 
    94, 94, 94, 94, 94, 94, 94, 94, 94, 94, 
    133, 133, 94, 148, 133, 133, 51, 51, 133, 51, 
    149, 133, 133, 94, 94, 94, 94, 94, 94, 94, 
    94, 94, 94, 133, 150, 55, 55, 55, 59, 151, 
    151, 77, 55, 55, 55, 55, 55, 51, 55, 55, 
    55, 55, 55, 55, 55, 55, 55, 55, 55, 55, 
    78, 78, 55, 97, 78, 78, 51, 78, 78, 51, 
    98, 94, 78, 55, 55, 55, 55, 55, 55, 55, 
    55, 55, 55, 78, 99, 78, 78, 55, 97, 152, 
    152, 135, 78, 78, 78, 98, 94, 78, 55, 55, 
    55, 55, 55, 55, 55, 55, 55, 55, 78, 99, 
    5, 5, -1, 33, 5, 5, 61, 5, 5, 5, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, 35, 35, -1, 37, 153, 
    153, 81, 35, 35, 35, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    38, 38, -1, 39, 154, 154, 83, 38, 38, 38, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, 40, 40, -1, 42, 155, 
    155, 85, 40, 40, 40, -1, -1, -1, -1, -1, 
    -1, -1, 64, -1, -1, -1, -1, -1, -1, -1, 
    66, 66, -1, 68, 156, 156, 105, 66, 66, 66, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, 69, 69, 69, 88, 157, 
    157, 107, 69, 69, 69, 69, 86, 69, 69, 69, 
    69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 
    70, 70, 70, 89, 158, 158, 110, 70, 70, 70, 
    70, 70, 86, 70, 70, 70, 70, 70, 70, 70, 
    70, 70, 70, 70, 70, 17, 17, -1, 44, 17, 
    17, 72, 17, 17, 17, -1, -1, -1, 46, -1, 
    -1, -1, -1, 47, 46, 46, -1, -1, -1, -1, 
    54, 54, 54, 58, 159, 159, 74, 54, 54, 54, 
    54, 51, 54, 54, 54, 54, 54, 54, 54, 54, 
    54, 54, 54, 54, 54, 75, 75, 54, 92, 160, 
    160, 129, 75, 75, 75, 93, 75, 94, 54, 54, 
    54, 54, 54, 54, 54, 54, 54, 54, 75, 95, 
    94, 94, 94, 116, 161, 161, 132, 94, 94, 94, 
    94, 78, 75, 94, 94, 94, 94, 94, 94, 94, 
    94, 94, 94, 94, 94, 133, 133, 133, 162, 163, 
    163, 132, 94, 133, 94, 133, 133, 133, 133, 133, 
    133, 133, 133, 133, 133, 133, 133, 133, 133, 133, 
    94, 94, 94, 116, 94, 133, -1, -1, 94, -1, 
    94, 78, 75, 94, 94, 94, 94, 94, 94, 94, 
    94, 94, 94, 94, 94, 55, 55, 55, 59, 164, 
    164, 77, 55, 55, 55, 55, 55, 51, 55, 55, 
    55, 55, 55, 55, 55, 55, 55, 55, 55, 55, 
    78, 78, 55, 97, 165, 165, 135, 78, 78, 78, 
    98, 94, 78, 55, 55, 55, 55, 55, 55, 55, 
    55, 55, 55, 78, 99, 35, 35, -1, 37, 35, 
    35, 81, 35, 35, 35, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    38, 38, -1, 39, 38, 38, 83, 38, 38, 38, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, 40, 40, -1, 42, 40, 
    40, 85, 40, 40, 40, -1, -1, -1, -1, -1, 
    -1, -1, 64, -1, -1, -1, -1, -1, -1, -1, 
    66, 66, -1, 68, 166, 166, 105, 66, 66, 66, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, 69, 69, 69, 88, 167, 
    167, 107, 69, 69, 69, 69, 86, 69, 69, 69, 
    69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 
    70, 70, 70, 89, 168, 168, 110, 70, 70, 70, 
    70, 70, 86, 70, 70, 70, 70, 70, 70, 70, 
    70, 70, 70, 70, 70, 54, 54, 54, 58, 54, 
    54, 74, 54, 54, 54, 54, 51, 54, 54, 54, 
    54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 
    75, 75, 54, 92, 169, 169, 129, 75, 75, 75, 
    93, 75, 94, 54, 54, 54, 54, 54, 54, 54, 
    54, 54, 54, 75, 95, 94, 94, 94, 116, 170, 
    170, 132, 94, 94, 94, 94, 78, 75, 94, 94, 
    94, 94, 94, 94, 94, 94, 94, 94, 94, 94, 
    133, 133, 94, 148, 163, 163, 171, 133, 133, 133, 
    149, 133, 133, 94, 94, 94, 94, 94, 94, 94, 
    94, 94, 94, 133, 150, 133, 133, 94, 148, 172, 
    172, 171, 133, 133, 133, 149, 133, 133, 94, 94, 
    94, 94, 94, 94, 94, 94, 94, 94, 133, 150, 
    55, 55, 55, 59, 55, 55, 77, 55, 55, 55, 
    55, 55, 51, 55, 55, 55, 55, 55, 55, 55, 
    55, 55, 55, 55, 55, 78, 78, 55, 97, 173, 
    173, 135, 78, 78, 78, 98, 94, 78, 55, 55, 
    55, 55, 55, 55, 55, 55, 55, 55, 78, 99, 
    66, 66, -1, 68, 66, 66, 105, 66, 66, 66, 
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    -1, -1, -1, -1, -1, 69, 69, 69, 88, 174, 
    174, 107, 69, 69, 69, 69, 86, 69, 69, 69, 
    69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 
    70, 70, 70, 89, 175, 175, 110, 70, 70, 70, 
    70, 70, 86, 70, 70, 70, 70, 70, 70, 70, 
    70, 70, 70, 70, 70, 75, 75, 54, 92, 176, 
    176, 129, 75, 75, 75, 93, 75, 94, 54, 54, 
    54, 54, 54, 54, 54, 54, 54, 54, 75, 95, 
    94, 94, 94, 116, 177, 177, 132, 94, 94, 94, 
    94, 78, 75, 94, 94, 94, 94, 94, 94, 94, 
    94, 94, 94, 94, 94, 133, 133, 94, 148, 133, 
    133, 51, 133, 133, 51, 149, 133, 133, 94, 94, 
    94, 94, 94, 94, 94, 94, 94, 94, 133, 150, 
    133, 133, 94, 148, 178, 178, 171, 133, 133, 133, 
    149, 133, 133, 94, 94, 94, 94, 94, 94, 94, 
    94, 94, 94, 133, 150, 78, 78, 55, 97, 179, 
    179, 135, 78, 78, 78, 98, 94, 78, 55, 55, 
    55, 55, 55, 55, 55, 55, 55, 55, 78, 99, 
    69, 69, 69, 88, 69, 69, 107, 69, 69, 69, 
    69, 86, 69, 69, 69, 69, 69, 69, 69, 69, 
    69, 69, 69, 69, 69, 70, 70, 70, 89, 70, 
    70, 110, 70, 70, 70, 70, 70, 86, 70, 70, 
    70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 
    75, 75, 54, 92, 75, 75, 129, 75, 75, 75, 
    93, 75, 94, 54, 54, 54, 54, 54, 54, 54, 
    54, 54, 54, 75, 95, 94, 94, 94, 116, 180, 
    180, 132, 94, 94, 94, 94, 78, 75, 94, 94, 
    94, 94, 94, 94, 94, 94, 94, 94, 94, 94, 
    133, 133, 94, 148, 181, 181, 171, 133, 133, 133, 
    149, 133, 133, 94, 94, 94, 94, 94, 94, 94, 
    94, 94, 94, 133, 150, 78, 78, 55, 97, 78, 
    78, 135, 78, 78, 78, 98, 94, 78, 55, 55, 
    55, 55, 55, 55, 55, 55, 55, 55, 78, 99, 
    94, 94, 94, 116, 94, 94, 132, 94, 94, 94, 
    94, 78, 75, 94, 94, 94, 94, 94, 94, 94, 
    94, 94, 94, 94, 94, 133, 133, 94, 148, 182, 
    182, 171, 133, 133, 133, 149, 133, 133, 94, 94, 
    94, 94, 94, 94, 94, 94, 94, 94, 133, 150, 
    133, 133, 94, 148, 183, 183, 171, 133, 133, 133, 
    149, 133, 133, 94, 94, 94, 94, 94, 94, 94, 
    94, 94, 94, 133, 150, 133, 133, 94, 148, 133, 
    133, 171, 133, 133, 133, 149, 133, 133, 94, 94, 
    94, 94, 94, 94, 94, 94, 94, 94, 133, 150
  };

  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\3\0\1\1\1\0\2\1\1\11\2\1\3\11\1\1"+
    "\1\11\6\1\2\11\12\1\1\0\2\1\2\0\1\1"+
    "\1\0\1\1\4\0\1\1\2\0\1\1\7\0\1\11"+
    "\3\0\4\1\1\11\2\1\4\0\2\1\6\0\11\1"+
    "\2\0\1\1\4\0\1\1\3\0\7\1\2\0\1\1"+
    "\2\0\2\1\7\0\5\1\2\0\1\1\11\0\5\1"+
    "\2\0\1\1\5\0\1\1\2\0\4\1\11\0\1\1"+
    "\21\0";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[184];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private char[] zzChars;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
	// position methods
	public int position() { return yychar; }
	public int length()   { return yylength(); }
	public int line()     { return -1; /*yyline;*/ }   	// for debugging
	public int column()   { return -1; /*yycolumn;*/ } 	// for debugging

	// state methods
	public void stateReset() 		{ yybegin(YYINITIAL); }
	public void stateSelector() 	{ yybegin(SELECTOR); }
	public void stateAttr()			{ yybegin(ATTR); }
	public void stateCombinator()	{ yybegin(COMBINATOR); }
	public void statePseudoFn()		{ yybegin(PSEUDO_FN); }

	// fast methods
	public final CharSequence xxtext() {
		return CharBuffer.wrap(zzBuffer, zzStartRead, zzMarkedPos - zzStartRead);
	}

	public final String yytext(int startIndex) {
		startIndex += zzStartRead;
		return new String(zzBuffer, startIndex, zzMarkedPos - startIndex);
	}
	public final String yytext(int startIndex, int endIndexOffset) {
		startIndex += zzStartRead;
		return new String(zzBuffer, startIndex, zzMarkedPos - endIndexOffset - startIndex);
	}

	ArrayList<CssSelector> selectors = new ArrayList<CssSelector>();
	CssSelector cssSelector;
	String pseudoFnName;


  /**
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 86) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Creates a new scanner.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  CSSellyLexer(char[] input) {
    this.zzChars = input;
  }

  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   */
  private boolean zzRefill() {
    if (zzBuffer == null) {
        zzBuffer = zzChars;
        zzEndRead += zzChars.length;
        return false;
    }
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public int yylex() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      yychar+= zzMarkedPosL-zzStartRead;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 9: 
          { cssSelector.addAttributeSelector(yytext());
          }
        case 20: break;
        case 14: 
          { cssSelector.setCombinator(Combinator.ADJACENT_SIBLING); stateReset();
          }
        case 21: break;
        case 5: 
          { cssSelector = new CssSelector(); selectors.add(cssSelector); stateSelector();
          }
        case 22: break;
        case 4: 
          { /* ignore whitespaces */
          }
        case 23: break;
        case 12: 
          { cssSelector.setCombinator(Combinator.GENERAL_SIBLING); stateReset();
          }
        case 24: break;
        case 10: 
          { stateSelector();
          }
        case 25: break;
        case 18: 
          { cssSelector.addPseudoFunctionSelector(pseudoFnName, yytext(0, 1)); stateSelector();
          }
        case 26: break;
        case 8: 
          { stateAttr();
          }
        case 27: break;
        case 7: 
          { throw new CSSellyException("Illegal character <"+ yytext() +">.", yystate(), line(), column());
          }
        case 28: break;
        case 6: 
          { yypushback(1); stateCombinator();
          }
        case 29: break;
        case 2: 
          { cssSelector = new CssSelector(yytext()); selectors.add(cssSelector); stateSelector();
          }
        case 30: break;
        case 3: 
          { cssSelector = new CssSelector(); selectors.add(cssSelector); yypushback(1); stateSelector();
          }
        case 31: break;
        case 1: 
          { cssSelector.setCombinator(Combinator.DESCENDANT); stateReset();
          }
        case 32: break;
        case 19: 
          { pseudoFnName = yytext(yycharat(1) == ':' ? 2 : 1,1); statePseudoFn();
          }
        case 33: break;
        case 16: 
          { cssSelector.addIdSelector(yytext(1));
          }
        case 34: break;
        case 15: 
          { cssSelector.addClassSelector(yytext(1));
          }
        case 35: break;
        case 17: 
          { cssSelector.addPseudoClassSelector(yytext( yycharat(1) == ':' ? 2 : 1 ));
          }
        case 36: break;
        case 11: 
          { throw new CSSellyException("Invalid combinator <"+ yytext() +">.", yystate(), line(), column());
          }
        case 37: break;
        case 13: 
          { cssSelector.setCombinator(Combinator.CHILD); stateReset();
          }
        case 38: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
              { return 0; }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
