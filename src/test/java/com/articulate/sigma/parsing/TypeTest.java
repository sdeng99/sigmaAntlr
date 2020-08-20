package com.articulate.sigma.parsing;

import com.articulate.sigma.KB;
import com.articulate.sigma.KBmanager;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class TypeTest {

    public static KB kb = null;

    /***************************************************************
     * */
    @BeforeClass
    public static void setup()  {

        KBmanager.getMgr().initializeOnce();
        kb = KBmanager.getMgr().getKB(KBmanager.getMgr().getPref("sumokbname"));
        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();
    }

    /***************************************************************
     * */
    public static String process(String input, String expected) {

        System.out.println("Input: " + input);
        CodePointCharStream inputStream = CharStreams.fromString(input);
        SuokifLexer suokifLexer = new SuokifLexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(suokifLexer);
        SuokifParser suokifParser = new SuokifParser(commonTokenStream);
        SuokifParser.FileContext fileContext = suokifParser.file();
        SuokifVisitor visitor = new SuokifVisitor();
        HashMap<Integer,FormulaAST> hm = visitor.visitFile(fileContext);
        VarTypes vt = new VarTypes(hm.values(),kb);
        vt.findTypes();
        StringBuilder sb = new StringBuilder();
        FormulaAST f = hm.values().iterator().next();
        f.printCaches();
        String result = f.varTypes.toString().trim();
        System.out.println("Result: " + result);
        System.out.println("expected: " + expected);
        if (result.equals(expected))
            System.out.println("Success");
        else
            System.out.println("FAIL");
        return result;
    }

    /** ***************************************************************
     */
    @Test
    public void test1() {

        System.out.println("test1()");
        String input = "(=> (and (minValue ?R ?ARG ?N) (?R @ARGS) (equal ?VAL (ListOrderFn (ListFn @ARGS) ?ARG))) (greaterThan ?VAL ?N))";
        String expected = "{?R=[Predicate], ?ARG=[Integer, PositiveInteger], ?N=[Quantity], ?VAL=[Entity, Quantity]}";
        String result = process(input,expected);
        assertEquals(expected,result);
    }

    /** ***************************************************************
     */
    @Test
    public void test2() {

        System.out.println("test2()");
        String input = "(<=>\n" +
                "  (instance ?OBJ SelfConnectedObject)\n" +
                "  (forall (?PART1 ?PART2)\n" +
                "    (=>\n" +
                "      (equal ?OBJ (MereologicalSumFn ?PART1 ?PART2))\n" +
                "      (connected ?PART1 ?PART2))))";
        String expected = "{?OBJ=[SelfConnectedObject, Entity, Object], ?PART2=[Object], ?PART1=[Object]}";
        String result = process(input,expected);
        assertEquals(expected,result);
    }

    /** ***************************************************************
     */
    @Test
    public void test3() {

        System.out.println("test2()");
        String input = "(=>\n" +
                "  (and\n" +
                "    (maxValue ?REL ?ARG ?N)\n" +
                "    (?REL @ARGS)\n" +
                "    (equal ?VAL\n" +
                "      (ListOrderFn\n" +
                "        (ListFn @ARGS) ?ARG)))\n" +
                "  (greaterThan ?N ?VAL))\n";
        String expected = "{?OBJ=[SelfConnectedObject, Entity, Object], ?PART2=[Object], ?PART1=[Object]}";
        String result = process(input,expected);
        assertEquals(expected,result);
    }
}
