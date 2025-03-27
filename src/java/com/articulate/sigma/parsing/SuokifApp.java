package com.articulate.sigma.parsing;

import com.articulate.sigma.Formula;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/** Example App showing 3 various ways to parse SUO-KIF and show the results */
public class SuokifApp {

    /**
     * Process the given SUO-KIF
     * @param input the SUO-KIF to process
     * @return and instance of the SuokifVisitor
     */
    public static SuokifVisitor process(String input) {
        return SuokifVisitor.parseString(input);
    }

    /**
     * Process the given SUO-KIF
     * @param fast the SUO-KIF formula to process
     * @return and instance of the SuokifVisitor
     */
    public static SuokifVisitor process(Formula fast) {
        return SuokifVisitor.parseFormula(fast);
    }

    /**
     * Process the given SUO-KIF
     * @param file the SUO-KIF file to process
     * @return and instance of the SuokifVisitor
     */
    public static SuokifVisitor process(File file) {
        return SuokifVisitor.parseFile(file);
    }

    private static void showResults() {

        Map<Integer, FormulaAST> hm = SuokifVisitor.result;
        StringBuilder sb = new StringBuilder();
        for (FormulaAST f : hm.values())
            sb.append(f.getFormula()).append("\n");
        System.out.println("result: " + sb);
    }

    /** ***************************************************************
     */
    public static void showHelp() {

        System.out.println("SuokifApp class");
        System.out.println("  options:");
        System.out.println("  -h - show this help screen");
        System.out.println("  -d - demo 3 various inputs to the parser");
        System.out.println("       process(input)   - parse a string input");
        System.out.println("       process(formula) - parse a formula");
        System.out.println("       process(fname)   - parse a file");
    }

    /** Command line entry point for the APP
     *
     * @param args common line arguments if any
     * @throws IOException if SUO-KIF reading goes south
     */
    public static void main(String[] args) throws IOException {

        System.out.println("INFO in SuokifApp.main()");

        if (args != null && args.length > 0 && args[0].equals("-h"))
            showHelp();
        else if (args != null && args.length == 1 && args[0].equals("-d")) {
            System.out.println();
            String input = "(likes John Mary)\n; and here's a comment\n";
            process(input);
            System.out.println("String input:\n" + input);
            showResults();
            System.out.println();

            input = "(=> (and (minValue ?R ?ARG ?N) (?R @ARGS) (equal ?VAL (ListOrderFn (ListFn @ARGS) ?ARG))) (greaterThan ?VAL ?N))";
            FormulaAST fast = new FormulaAST();
            fast.setFormula(input);
            process(fast);
            System.out.println("Formula, or FormulaAST input:\n " + fast);
            showResults();
            System.out.println();

            File file = new File("testFormula.txt");
            Path path = Paths.get(file.toURI());
            process(path.toFile());
            System.out.println("File input: " + path);
            showResults();
        }
        else
            showHelp();
    }
}
