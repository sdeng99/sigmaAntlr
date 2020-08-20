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
    public HashMap<String,HashSet<String>> explicitTypes = new HashMap<>();

    // a map of variables and all their inferred types
    public HashMap<String,HashSet<String>> varTypes = new HashMap<>();

    public HashSet<ParserRuleContext> rowvarLiterals = new HashSet<>(); // this can have a RelsentContext or FuntermContext

    public HashMap<String,ArgStruct> constants = new HashMap<>(); // constants as arguments and their enclosing literal

    public HashMap<String,HashSet<RowStruct>> rowVarStructs = new HashMap<>(); // row var keys

    //public HashMap<String,String> predVarSub = new HashMap<>();

    public SuokifParser.SentenceContext parsedFormula = null;

    public boolean isRule = false;

    public HashSet<String> antecedentTerms = new HashSet<>();
    public HashSet<String> consequentTerms = new HashSet<>();

    public String strForm = null; // a String version of this modified formula

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

        for (String pred : f.argMap.keySet()) {
            HashMap<Integer, HashSet<SuokifParser.ArgumentContext>> argnummap = f.argMap.get(pred);
            HashMap<Integer, HashSet<SuokifParser.ArgumentContext>> newargnummap = new HashMap<>();
            for (Integer argnum : argnummap.keySet()) {
                HashSet<SuokifParser.ArgumentContext> args = argnummap.get(argnum);
                HashSet<SuokifParser.ArgumentContext> newargs = new HashSet<>();
                newargs.addAll(args);
                newargnummap.put(argnum, newargs);
            }
            this.argMap.put(pred, newargnummap);
        }

        this.eqList.addAll(f.eqList);

        for (String var : f.explicitTypes.keySet()) {
            HashSet<String> newtypes = f.explicitTypes.get(var);
            if (explicitTypes.containsKey(var))
                explicitTypes.get(var).addAll(newtypes);
            else {
                HashSet<String> existingTypes = new HashSet<>();
                explicitTypes.put(var,existingTypes);
                existingTypes.addAll(newtypes);
            }
        }

        for (String var : f.varTypes.keySet()) {
            HashSet<String> newtypes = f.varTypes.get(var);
            if (varTypes.containsKey(var))
                varTypes.get(var).addAll(newtypes);
            else {
                HashSet<String> existingTypes = new HashSet<>();
                varTypes.put(var,existingTypes);
                existingTypes.addAll(newtypes);
            }
        }

        this.isRule = f.isRule;
        this.rowvarLiterals.addAll(f.rowvarLiterals);
        this.constants.putAll(f.constants);
        for (String var : f.rowVarStructs.keySet()) {
            HashSet<RowStruct> hsrs = f.rowVarStructs.get(var);
            if (debug) System.out.println("merge from rowVarStructs: " + hsrs);
            for (RowStruct rs : hsrs)
                this.addRowVarStruct(var, new RowStruct(rs));
        }
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
        for (String pred : f2.argMap.keySet()) {
            HashMap<Integer, HashSet<SuokifParser.ArgumentContext>> argnummap = f2.argMap.get(pred);
            HashMap<Integer, HashSet<SuokifParser.ArgumentContext>> newargnummap = new HashMap<>();
            for (Integer argnum : argnummap.keySet()) {
                HashSet<SuokifParser.ArgumentContext> args = argnummap.get(argnum);
                HashSet<SuokifParser.ArgumentContext> newargs = new HashSet<>();
                newargs.addAll(args);
                newargnummap.put(argnum,newargs);
            }
            this.argMap.put(pred,newargnummap);
        }
        for (String var : f2.explicitTypes.keySet()) {
            HashSet<String> newtypes = f2.explicitTypes.get(var);
            if (explicitTypes.containsKey(var))
                explicitTypes.get(var).addAll(newtypes);
            else {
                HashSet<String> existingTypes = new HashSet<>();
                explicitTypes.put(var,existingTypes);
                existingTypes.addAll(newtypes);
            }
        }

        for (String var : f2.varTypes.keySet()) {
            HashSet<String> newtypes = f2.varTypes.get(var);
            if (varTypes.containsKey(var))
                varTypes.get(var).addAll(newtypes);
            else {
                HashSet<String> existingTypes = new HashSet<>();
                varTypes.put(var,existingTypes);
                existingTypes.addAll(newtypes);
            }
        }

        this.eqList.addAll(f2.eqList);
        this.isRule = this.isRule || f2.isRule;
        this.rowvarLiterals.addAll(f2.rowvarLiterals);
        this.constants.putAll(f2.constants);
        for (String var : f2.rowVarStructs.keySet()) {
            HashSet<RowStruct> hsrs = f2.rowVarStructs.get(var);
            if (debug) System.out.println("merge from rowVarStructs: " + hsrs);
            for (RowStruct rs : hsrs)
                this.addRowVarStruct(var, new RowStruct(rs));
        }
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
            for (String pred : arf.argMap.keySet()) {
                HashMap<Integer, HashSet<SuokifParser.ArgumentContext>> argnummap = arf.argMap.get(pred);
                HashMap<Integer, HashSet<SuokifParser.ArgumentContext>> newargnummap = new HashMap<>();
                for (Integer argnum : argnummap.keySet()) {
                    HashSet<SuokifParser.ArgumentContext> args = argnummap.get(argnum);
                    HashSet<SuokifParser.ArgumentContext> newargs = new HashSet<>();
                    newargs.addAll(args);
                    newargnummap.put(argnum,newargs);
                }
                this.argMap.put(pred,newargnummap);
            }

            this.eqList.addAll(arf.eqList);
            for (String var : arf.explicitTypes.keySet()) {
                HashSet<String> newtypes = arf.explicitTypes.get(var);
                if (explicitTypes.containsKey(var))
                    explicitTypes.get(var).addAll(newtypes);
                else {
                    HashSet<String> existingTypes = new HashSet<>();
                    explicitTypes.put(var,existingTypes);
                    existingTypes.addAll(newtypes);
                }
            }

            for (String var : arf.varTypes.keySet()) {
                HashSet<String> newtypes = arf.varTypes.get(var);
                if (varTypes.containsKey(var))
                    varTypes.get(var).addAll(newtypes);
                else {
                    HashSet<String> existingTypes = new HashSet<>();
                    varTypes.put(var,existingTypes);
                    existingTypes.addAll(newtypes);
                }
            }
            this.isRule = this.isRule || arf.isRule;
            this.rowvarLiterals.addAll(arf.rowvarLiterals);
            this.constants.putAll(arf.constants);
            for (String var : arf.rowVarStructs.keySet()) {
                HashSet<RowStruct> hsrs = arf.rowVarStructs.get(var);
                if (debug) System.out.println("merge from rowVarStructs: " + hsrs);
                for (RowStruct rs : hsrs)
                    this.addRowVarStruct(var, new RowStruct(rs));
            }
        }
        return this;
    }

    /** *****************************************************************
     * A class for holding information about constants (non-variables) and the literal
     * in which they appear
     */
    public class ArgStruct {
        public String pred = "";
        public String literal = "";
        public String constant = "";
        public int argPos = -1;

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(pred + ":" + constant + ":" + ":" + argPos + ":" + literal);
            return sb.toString();
        }
    }

    /** *****************************************************************
     * A class for holding information about row variables and the literal
     * in which they appear
     */
    public class RowStruct {
        public RowStruct() {}
        public RowStruct(RowStruct rs) {
            this.rowvar = rs.rowvar;
            this.pred = rs.pred;
            this.literal = rs.literal;
            this.arity = rs.arity;
        }
        public String rowvar = "";
        public String pred = "";
        public String literal = "";
        public int arity = 0; // number of actual arguments in the literal

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(pred + ":" + rowvar + ":" + literal);
            return sb.toString();
        }
    }

    /** *****************************************************************
     */
    public void addRowVarStruct(String var, RowStruct rs) {

        HashSet<RowStruct> hrs = null;
        if (!rowVarStructs.containsKey(var)) {
            hrs = new HashSet<>();
            rowVarStructs.put(var,hrs);
        }
        else
            hrs = rowVarStructs.get(var);
        hrs.add(rs);
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
        System.out.println("varTypes: " + varTypes);
        System.out.println("predVarCache: " + predVarCache);
        System.out.println("explicitTypes: " + explicitTypes);

        System.out.println("eqlist: ");
        for (ArrayList<SuokifParser.TermContext> al : eqList) {
            System.out.println(al.get(0).getText() + " = " + al.get(1).getText());
        }
        System.out.println("row var literal: ");
        for (ParserRuleContext lit : rowvarLiterals) {
            System.out.println(lit.getText());
        }

        System.out.println("constants: ");
        for (String c : constants.keySet()) {
            ArgStruct as = constants.get(c);
            String lit = as.literal;
            System.out.println(c + " : " + lit);
        }

        System.out.println("row var struct: ");
        for (String var : rowVarStructs.keySet()) {
            HashSet<RowStruct> rvs = rowVarStructs.get(var);
            System.out.print(var + ":");
            for (RowStruct rv : rvs)
                System.out.print(rv.toString() + ", ");
            System.out.println();
        }
        System.out.println();
    }
}
