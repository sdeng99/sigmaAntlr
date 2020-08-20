package com.articulate.sigma.parsing;

import java.util.HashSet;

public class TPTPWriter {

    public HashSet<FormulaAST> formulas = null;

    public TPTPWriter(HashSet<FormulaAST> fs) {
        formulas = fs;
    }

    public void generateTPTP() {

        for (FormulaAST f : formulas) {
            
        }
    }
}
