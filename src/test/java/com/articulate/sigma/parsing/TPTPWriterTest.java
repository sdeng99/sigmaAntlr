package com.articulate.sigma.parsing;

import com.articulate.sigma.KBmanager;
import com.articulate.sigma.utils.FileUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class TPTPWriterTest {

    /** ***************************************************************
     */
    @Before
    public void init() {

        long start = System.currentTimeMillis();
        KBmanager.getMgr().initializeOnce();
        long end = (System.currentTimeMillis()-start)/1000;
        System.out.println("TPTPWriterTest.init(): total init time: " + end + " seconds");
    }

    /** ***************************************************************
     */
    @Test
    public void test1() {

        long start = System.currentTimeMillis();
        SuokifVisitor sv = new SuokifVisitor();
        sv.parseFile(System.getenv("SIGMA_HOME") + File.separator + "KBs" + File.separator + "Merge.kif");
        Preprocessor pre = new Preprocessor(KBmanager.getMgr().getKB(KBmanager.getMgr().getPref("sumokbname")));

        pre.removeMultiplePredVar(sv); // remove explosive rules with multiple predicate variables

        System.out.println("TPTPWriterTest.test1(): sourceFile after parsing: " + sv.rules.iterator().next().sourceFile);
        Collection<FormulaAST> rules = pre.preprocess(sv.hasPredVar,sv.hasRowVar,sv.rules);
        System.out.println("TPTPWriterTest.test1(): sourceFile after preprocessing:" + rules.iterator().next().sourceFile);
        // HashSet<FormulaAST> result = pre.reparse(rules); done already in preprocess
        if (rules.size() < 100)
            System.out.println("TPTPWriterTest.test1(): " + rules);
        else
            System.out.println("TPTPWriterTest.test1() results too large to show");
        long end = System.currentTimeMillis();
        System.out.println("TPTPWriterTest.test1(): total preprocess time: " + ((end-start)/1000) + " seconds");
        TPTPWriter tptpW = new TPTPWriter();
        for (FormulaAST f : rules) {
            System.out.println("fof(kb_" + FileUtil.noExt(FileUtil.noPath(f.sourceFile)) + "_" + f.startLine + ",axiom," + tptpW.visitSentence(f.parsedFormula) + ").");
        }
        long end2 = System.currentTimeMillis();
        System.out.println("TPTPWriterTest.test1(): total write time: " + ((end2-end)/1000)  + " seconds");
    }

    /** ***************************************************************
     */
    @Test
    public void test2() {

        String s = "(=>\n" +
                "    (equal\n" +
                "        (MinFn ?NUMBER1 ?NUMBER2) ?NUMBER)\n" +
                "    (or\n" +
                "        (and\n" +
                "            (equal ?NUMBER ?NUMBER1)\n" +
                "            (lessThan ?NUMBER1 ?NUMBER2))\n" +
                "        (and\n" +
                "            (equal ?NUMBER ?NUMBER2)\n" +
                "            (lessThan ?NUMBER2 ?NUMBER1))\n" +
                "        (and\n" +
                "            (equal ?NUMBER ?NUMBER1)\n" +
                "            (equal ?NUMBER ?NUMBER2))))";
        SuokifVisitor sv = SuokifVisitor.parseString(s);
        Preprocessor pre = new Preprocessor(KBmanager.getMgr().getKB(KBmanager.getMgr().getPref("sumokbname")));
        sv.hasPredVar.removeAll(sv.multiplePredVar); // remove explosive rules with multiple predicate variables
        sv.rules.removeAll(sv.multiplePredVar);
        sv.hasRowVar.removeAll(sv.multiplePredVar);
        System.out.println("TPTPWriterTest.test2(): sourceFile after parsing: " + sv.rules.iterator().next().sourceFile);
        Collection<FormulaAST> rules = pre.preprocess(sv.hasPredVar,sv.hasRowVar,sv.rules);
        System.out.println("TPTPWriterTest.test2(): sourceFile after preprocessing:" + rules.iterator().next().sourceFile);
        // HashSet<FormulaAST> result = pre.reparse(rules); done already in preprocess
        if (rules.size() < 100)
            System.out.println("TPTPWriterTest.test2(): " + rules);
        else
            System.out.println("TPTPWriterTest.test2() results too large to show");
        TPTPWriter tptpW = new TPTPWriter();
        for (FormulaAST f : rules) {
            System.out.println("fof(kb_" + FileUtil.noExt(FileUtil.noPath(f.sourceFile)) + "_" + f.startLine + ",axiom," + tptpW.visitSentence(f.parsedFormula) + ").");
        }
    }
}
