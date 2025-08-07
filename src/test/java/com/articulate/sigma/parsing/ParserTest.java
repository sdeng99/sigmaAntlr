package com.articulate.sigma.parsing;

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author <a href="mailto:tdnorbra@nps.edu?subject=com.articulate.sigma.parsing.ParserTest">Terry Norbraten, NPS MOVES</a>
 */
public class ParserTest {

    @Test
    public void testParser() throws IOException {

        CharStream stream = CharStreams.fromStream(this.getClass().getClassLoader().getResourceAsStream("testFormula.txt"));
        SuokifLexer lexer = new SuokifLexer(stream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        SuokifParser parser = new SuokifParser(tokenStream);
        parser.removeErrorListeners();

        // Custom listener that stops parsing on first syntax error
        parser.addErrorListener(new SuokifParserErrorListener());
        SuokifParser.FileContext fileContext = parser.file();
        assertTrue(!fileContext.children.isEmpty());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testForError() throws IOException {

        String input = "(=>\n" +
"  (and\n" +
"    (instance ?REL Predicate)\n" +
"    (instance ?ARG PositiveInteger)\n" +
"    (instance ?N Quantity)\n" +
"    (instance ?VAL Quantity))\n" +
"  (=>\n" +
"    (and\n" +
"      (maxValue subclass ?ARG ?N)\n" +
"      (subclass @ARGS)\n" +
"      (equal (?VAL)\n" + // introduce error
"        (ListOrderFn\n" +
"          (ListFn @ARGS)\n" +
"          ?ARG)))\n" +
"    (greaterThan ?N ?VAL)))";

        CharStream stream = CharStreams.fromString(input);
        SuokifLexer lexer = new SuokifLexer(stream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        SuokifParser parser = new SuokifParser(tokenStream);
        parser.removeErrorListeners();

        // Custom listener that stops parsing on first syntax error
        parser.addErrorListener(new SuokifParserErrorListener());
        SuokifParser.FileContext fileContext = parser.file();

        ParseTreeWalker walker = new ParseTreeWalker();
        ParseTreeListener listener = new SuokifBaseListener();
        for (ParseTree c : fileContext.children) {

            walker.walk(listener, c);
        }
    }

} // end class file ParserTest.java