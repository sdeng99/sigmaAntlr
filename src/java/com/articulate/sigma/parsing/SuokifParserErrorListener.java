package com.articulate.sigma.parsing;

import org.antlr.v4.runtime.*;

/** Listens for and rethrows an IllegalArgumentException on the first syntax
 * error encountered while parsing SUO-KIF which will stop further parsing
 *
 * @author <a href="mailto:tdnorbra@nps.edu?subject=com.articulate.sigma.parsing.SuokifParserErrorListener">Terry Norbraten, NPS MOVES</a>
 */
public class SuokifParserErrorListener extends BaseErrorListener {

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e)
    {
        String errStr = "Parse error at line:charposn " + line + ":" + charPositionInLine + ": -> " + msg;
//        System.err.println(errStr);

        // You can throw an exception here if you want to stop parsing on the first error
         throw new IllegalArgumentException(errStr, e);
    }

} // end class file SuokifParserErrorListener.java
