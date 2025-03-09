package com.articulate.sigma.parsing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/** Example App showing 3 various ways to parse SUO-KIF and show the results */
public class SuokifApp {

    public static void process(String input) {
        SuokifVisitor.parseString(input);
    }

    public static void process(FormulaAST fast) {
        SuokifVisitor.parseFormula(fast);
    }

    public static void process(File file) {
        new SuokifVisitor().parseFile(file);
    }

    private static void showResults() {

        Map<Integer, FormulaAST> hm = SuokifVisitor.result;
        StringBuilder sb = new StringBuilder();
        for (FormulaAST f : hm.values())
            sb.append(f.getFormula()).append("\n");
        System.out.println("result: " + sb);
    }

    public static void main(String[] args) throws IOException {

        System.out.println();
        String input = "(likes John Mary)\n; and here's a comment\n";
        process(input);
        showResults();
        System.out.println();

        input = "(=> (and (minValue ?R ?ARG ?N) (?R @ARGS) (equal ?VAL (ListOrderFn (ListFn @ARGS) ?ARG))) (greaterThan ?VAL ?N))";
        FormulaAST fast = new FormulaAST();
        fast.setFormula(input);
        process(fast);
        showResults();
        System.out.println();

        File file = new File("testFormula.txt");
        Path path = Paths.get(file.toURI());
        process(path.toFile());
        showResults();
    }
}
