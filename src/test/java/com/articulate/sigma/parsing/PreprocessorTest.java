package com.articulate.sigma.parsing;

import com.articulate.sigma.KBmanager;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.HashSet;

public class PreprocessorTest {

    /** ***************************************************************
     */
    @Before
    public void init() {

        KBmanager.getMgr().initializeOnce();
    }

    /** ***************************************************************
     */
    @Test
    public void test1() {

        SuokifVisitor sv = new SuokifVisitor();
        sv.parseFile(System.getenv("SIGMA_HOME") + File.separator + "KBs" + File.separator + "Merge.kif");
        Preprocessor pre = new Preprocessor(KBmanager.getMgr().getKB(KBmanager.getMgr().getPref("sumokbname")));
        HashSet<FormulaAST> rules = pre.preprocess(sv.hasPredVar,sv.hasRowVar,sv.rules);
        HashSet<FormulaAST> result = pre.reparse(rules);
        System.out.println(result);
    }

    /** ***************************************************************
     */
    @Ignore
    @Test
    public void test2() {

        String input = "(=>\n" +
                "  (and\n" +
                "    (maxValue ?REL ?ARG ?N)\n" +
                "    (?REL @ARGS)\n" +
                "    (equal ?VAL\n" +
                "      (ListOrderFn\n" +
                "        (ListFn @ARGS) ?ARG)))\n" +
                "  (greaterThan ?N ?VAL))";
        SuokifVisitor sv = SuokifVisitor.parseString(input);
        System.out.println("PreprocessorTest.test2(): # rules: " + sv.rules.size());
        Preprocessor pre = new Preprocessor(KBmanager.getMgr().getKB(KBmanager.getMgr().getPref("sumokbname")));
        System.out.println("PreprocessorTest.test2(): # before preprocess: " + sv.rules.size());
        HashSet<FormulaAST> rules = pre.preprocess(sv.hasPredVar,sv.hasRowVar,sv.rules);
        System.out.println("PreprocessorTest.test2(): # after preprocess: " + rules.size());
        HashSet<FormulaAST> result = pre.reparse(rules);
        System.out.println(result);
    }
}
