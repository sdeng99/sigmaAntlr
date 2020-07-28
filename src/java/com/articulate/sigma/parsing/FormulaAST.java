package com.articulate.sigma.parsing;

import com.articulate.sigma.Formula;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class FormulaAST extends Formula {

    // arguments to relations in order to find the types of arg in a second pass
    // first key is a relation name, interior key is argument number starting at 1
    public HashMap<String, HashMap<Integer, HashSet<SuokifParser.ArgumentContext>>> argMap = new HashMap<>();

    // all the equality statements in a formula.  The interior ArrayList must have
    // only two elements, one for each side of the equation
    public ArrayList<ArrayList<SuokifParser.TermContext>> eqList = new ArrayList<>();

    // a map of all variables that have an explicit type declaration
    public HashMap<String,String> explicitTypes = new HashMap<>();

    // a map of variables and all their inferred types
    public HashMap<String,HashSet<String>> varmap = new HashMap<>();

    // a map of variables and their most specific types
    public HashMap<String,String> specvarmap = new HashMap<>();

    public HashSet<ParserRuleContext> rowvarLiterals = new HashSet<>(); // this can have a RelsentContext or FuntermContext

    public HashMap<String,String> predVarSub = new HashMap<>();

    public static boolean isRule = false;

    /** ***************************************************************
     */
    public FormulaAST() {

    }

    /** ***************************************************************
     */
    public FormulaAST(FormulaAST f) {

        this.endLine = f.endLine;
        this.startLine = f.startLine;
        this.sourceFile = f.sourceFile;
        this.setFormula(f.getFormula());
        this.allVarsPairCache.addAll(f.allVarsPairCache);
        this.quantVarsCache.addAll(f.quantVarsCache);
        this.unquantVarsCache.addAll(f.unquantVarsCache);
        this.existVarsCache.addAll(f.existVarsCache);
        this.univVarsCache.addAll(f.univVarsCache);
        this.termCache.addAll(f.termCache);
        if (f.predVarCache != null) {
            this.predVarCache = new HashSet<>();
            this.predVarCache.addAll(f.predVarCache);
        }
        if (f.rowVarCache != null) {
            this.rowVarCache = new HashSet<>();
            this.rowVarCache.addAll(f.rowVarCache);
        }
        this.varTypeCache.putAll(f.varTypeCache);
        this.argMap.putAll(f.argMap);
        this.eqList.addAll(f.eqList);
        this.explicitTypes.putAll(f.explicitTypes);
        this.varmap.putAll(f.varmap);
        this.specvarmap.putAll(f.specvarmap);
        this.isRule = f.isRule;
        this.rowvarLiterals.addAll(f.rowvarLiterals);
    }

    /** ***************************************************************
     * Merge arguments to a predicate, which may themselves be complex
     * formulas, with an existing formula.
     */
    public FormulaAST mergeFormulaAST(FormulaAST f2) {

        this.allVarsCache.addAll(f2.allVarsCache);
        this.allVarsPairCache.addAll(f2.allVarsPairCache);
        this.quantVarsCache.addAll(f2.quantVarsCache);
        this.unquantVarsCache.addAll(f2.unquantVarsCache);
        this.existVarsCache.addAll(f2.existVarsCache);
        this.univVarsCache.addAll(f2.univVarsCache);
        this.termCache.addAll(f2.termCache);
        if (this.rowVarCache == null)
            this.rowVarCache = new HashSet<>();
        if (f2.rowVarCache == null)
            f2.rowVarCache = new HashSet<>();
        this.rowVarCache.addAll(f2.rowVarCache);
        if (this.predVarCache == null)
            this.predVarCache = new HashSet<>();
        if (f2.predVarCache == null)
            f2.predVarCache = new HashSet<>();
        this.predVarCache.addAll(f2.predVarCache);
        this.argMap.putAll(f2.argMap);
        this.varmap.putAll(f2.varmap);
        this.eqList.addAll(f2.eqList);
        this.explicitTypes.putAll(f2.explicitTypes);
        this.specvarmap.putAll(f2.specvarmap);
        this.isRule = this.isRule || f2.isRule;
        this.rowvarLiterals.addAll(f2.rowvarLiterals);
        return this;
    }

    /** ***************************************************************
     * Merge arguments to a predicate, which may themselves be complex
     * formulas, with an existing formula.
     */
    public FormulaAST mergeFormulaAST(ArrayList<FormulaAST> ar) {

        if (this.predVarCache == null)
            this.predVarCache = new HashSet<>();
        if (this.rowVarCache == null)
            this.rowVarCache = new HashSet<>();
        for (FormulaAST arf : ar) {
            this.allVarsCache.addAll(arf.allVarsCache);
            this.allVarsPairCache.addAll(arf.allVarsPairCache);
            this.quantVarsCache.addAll(arf.quantVarsCache);
            this.unquantVarsCache.addAll(arf.unquantVarsCache);
            this.existVarsCache.addAll(arf.existVarsCache);
            this.univVarsCache.addAll(arf.univVarsCache);
            this.termCache.addAll(arf.termCache);
            if (arf.rowVarCache == null)
                arf.rowVarCache = new HashSet<>();
            this.rowVarCache.addAll(arf.rowVarCache);
            if (arf.predVarCache == null)
                arf.predVarCache = new HashSet<>();
            this.predVarCache.addAll(arf.predVarCache);
            this.argMap.putAll(arf.argMap);
            this.varmap.putAll(arf.varmap);
            this.eqList.addAll(arf.eqList);
            this.explicitTypes.putAll(arf.explicitTypes);
            this.specvarmap.putAll(arf.specvarmap);
            this.isRule = this.isRule || arf.isRule;
            this.rowvarLiterals.addAll(arf.rowvarLiterals);
        }
        return this;
    }

    /** *****************************************************************
     * the textual version of the formula
     */
    public static FormulaAST createComment(String input) {

        FormulaAST f = new FormulaAST();
        f.setFormula(input);
        f.comment = true;
        return f;
    }

    /** *****************************************************************
     * the textual version of the formula
     */
    public void printCaches() {

        super.printCaches();
        System.out.println("argMap: ");
        for (String pred : argMap.keySet()) {
            System.out.print("\t" + pred + "\t");
            for (Integer i : argMap.get(pred).keySet()) {
                System.out.print(i + ": ");
                for (SuokifParser.ArgumentContext c : argMap.get(pred).get(i)) {
                    System.out.print(c.getText() + ", ");
                }
            }
            System.out.println();
        }
        System.out.println("varmap: " + varmap);
        System.out.println("explicitTypes: " + explicitTypes);
        System.out.println("eqlist: ");
        for (ArrayList<SuokifParser.TermContext> al : eqList) {
            System.out.println(al.get(0).getText() + " = " + al.get(1).getText());
        }
        System.out.println("row var literal: ");
        for (ParserRuleContext lit : rowvarLiterals) {
            System.out.println(lit.getText());
        }
        System.out.println();
    }
}
