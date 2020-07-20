package com.articulate.sigma.parsing;

import com.articulate.sigma.Formula;

import java.util.HashMap;
import java.util.HashSet;

public class FormulaAST extends Formula {

    // arguments to relations in order to find the types of arg in a second pass
    // first key is a relation name, interior key is argument number starting at 1
    public HashMap<String, HashMap<Integer, HashSet<SuokifParser.ArgumentContext>>> argMap = new HashMap<>();

    /** *****************************************************************
     * the textual version of the formula
     */
    public static FormulaAST createComment(String input) {

        FormulaAST f = new FormulaAST();
        f.setFormula(input);
        f.comment = true;
        return f;
    }
}
