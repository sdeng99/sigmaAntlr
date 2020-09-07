package com.articulate.sigma.parsing;

import com.articulate.sigma.KB;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class RowVar {

    public KB kb = null;
    public boolean debug = false;

    /** ***************************************************************
     */
    public RowVar(KB kbin) {

        if (!PredVarInst.predVarInstDone == true)
            System.out.println("Error! in RowVar() Predicate variable instantiation is required and has not been completed");
        kb = kbin;
    }

    /** ***************************************************************
     */
    public ArrayList<String> getVarSubStrings(String var) {

        ArrayList<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        String varName = var.substring(1);
        for (int i = 1; i <= 7; i++) {
            sb.append("?" + varName + i + " ");
            String varList = sb.toString();
            varList = varList.substring(0, varList.length() - 1);
            result.add(varList);
        }
        return result;
    }

    /** ***************************************************************
     */
    public HashSet<FormulaAST> expandVariableArityRowVar(HashSet<FormulaAST> flist, String var) {

        ArrayList<String> varLists = getVarSubStrings(var);
        HashSet<FormulaAST> result = new HashSet<>();
        for (FormulaAST f : flist) {
            ArrayList<FormulaAST> formulaList = new ArrayList<>();
            for (int i = 1; i <= 7; i++) {
                FormulaAST f2 = new FormulaAST(f);
                formulaList.add(f2);
            }
            if (debug) System.out.println("expandVariableArityRowVar(): row var structs " + f.rowVarStructs);
            for (FormulaAST.RowStruct rs : f.rowVarStructs.get(var)) {
                if (debug) System.out.println("expandVariableArityRowVar(): variable row struct " + rs);
                String literal = rs.literal;
                String pred = rs.pred;
                StringBuilder sb = new StringBuilder();
                String varName = var.substring(1);
                for (int i = 0; i <= 6; i++) {
                    FormulaAST fnew = formulaList.get(i);
                    String varList = varLists.get(i);
                    if (debug) System.out.println("expandVariableArityRowVar(): replace varname : @" +
                            varName + " with varlist: " + varList);
                    String newliteral = literal.replace("@" + varName, varList);
                    String fnSuffix = "Fn";
                    if (!pred.endsWith("Fn"))
                        fnSuffix = "";
                    int predArity = i-1;
                    if (debug) System.out.println("expandVariableArityRowVar(): literal arity: " + rs.arity);
                    if (debug) System.out.println("expandVariableArityRowVar(): i: " + i);
                    if (rs.arity > 1)
                        predArity = i + rs.arity;
                    else
                        predArity = i+1;
                    String newPredName = pred + "_" + predArity + fnSuffix;
                    if (!pred.equals("__quantList")) {
                        if (debug) System.out.println("expandVariableArityRowVar(): replace pred : " +
                                pred + " with new pred: " + newPredName + " in " + newliteral);
                        newliteral = newliteral.replace(pred, newPredName);
                    }
                    rs.literal = newliteral;
                    if (debug) System.out.println("expandVariableArityRowVar(): fnew before " + fnew.getFormula());
                    if (debug) System.out.println("expandVariableArityRowVar(): literal " + literal);
                    if (debug) System.out.println("expandVariableArityRowVar(): newliteral " + newliteral);
                    if (!pred.equals("__quantList"))
                        fnew.setFormula(fnew.getFormula().replace(literal, newliteral));
                    else {
                        if (debug) System.out.println("expandVariableArityRowVar(): replace (" + literal + ") with (" + newliteral + ")");
                        fnew.setFormula(fnew.getFormula().replace("(" + literal + ")", "(" + newliteral + ")"));
                    }
                    if (debug) System.out.println("expandVariableArityRowVar(): fnew after " + fnew.getFormula());
                }
                if (debug) System.out.println("expandVariableArityRowVar(): formulaList: " + formulaList);
            }
            result.addAll(formulaList);
        }
        if (debug) System.out.println("expandVariableArityRowVar():result: " + result);
        return result;
    }

    /** ***************************************************************
     * @return arities implied on every row var by their relations and arguments.
     * if the pred var is only an argument to variable arity relations, return -1
     *
     */
    public HashMap<String, Integer> findArities(FormulaAST f) {

        if (debug && f.getFormula().indexOf(" maxValue ") != -1) {
            System.out.println("findArities():" + f);
            f.printCaches();
        }
        HashMap<String, Integer> arities = new HashMap<>();
        for (HashSet<FormulaAST.RowStruct> rshs : f.rowVarStructs.values()) {
            for (FormulaAST.RowStruct rs : rshs) {
                if (debug) System.out.println("findArities(): variable " + rs.rowvar + " pred: " + rs.pred);
                if (KB.isVariable(rs.pred)) {
                    System.out.println("Error in findArities(): variable pred: " + rs.pred + " in "  + f);
                }
                else if (kb.kbCache.isInstanceOf(rs.pred,"VariableArityPredicate") && arities.get(rs.rowvar) == null )
                    arities.put(rs.rowvar,-1);
                else {
                    if (rs.pred.equals("__quantList"))
                        continue;
                    int predArity = kb.kbCache.getArity(rs.pred);
                    int rowVarArity = predArity;
                    if (rs.arity > 1)
                        rowVarArity = rowVarArity - (rs.arity - 1); // if there's more than one argument, var arity is reduced
                    if (predArity == -1)
                        rowVarArity = -1;
                    if (debug) System.out.println("findArities(): variable " + rs.rowvar + " pred: " +
                            rs.pred + " pred arity: " + predArity + " row arity: " + rowVarArity + " rs.arity: " + rs.arity + " in "  + f);
                    if (arities.get(rs.rowvar) == null || predArity != -1) {
                        arities.put(rs.rowvar, rowVarArity);
                        if (debug) System.out.println("findArities(): put " + rs.rowvar + " row arity: " + rowVarArity);
                    }
                }
            }
        }
        return arities;
    }

    /** ***************************************************************
     * @return a HashSet of FormulaAST.  If just one row variable is an
     * argument to a fixed arity relation, then there will be just one
     * element in the returned set.  But if there one or more row
     * variables are arguments to VariableArityRelations then there will
     * be multiple returned formulas
     */
    public HashSet<FormulaAST> expandRowVar(FormulaAST f) {

        if (debug) System.out.println("expandRowVar(): f: " + f);
        HashMap<String, Integer> varArities = findArities(f);
        HashSet<FormulaAST> result = new HashSet<>();
        if (debug) System.out.println("expandRowVar(): variable arity vars list " + varArities);
        HashSet<FormulaAST> flist = new HashSet<>();
        flist.add(f);
        for (String var : varArities.keySet()) {
            result = new HashSet<>();
            if (debug) System.out.println("expandRowVar(): expanding var: " + var);
            int arity = varArities.get(var);
            if (debug) System.out.println("expandRowVar(): var arity: " + arity);
            if (arity == -1)
                result.addAll(expandVariableArityRowVar(flist,var));
            else {
                String varName = var.substring(1);
                StringBuilder sb = new StringBuilder();
                for (int i = 1; i <= arity; i++)
                    sb.append("?" + varName + i + " ");
                sb.deleteCharAt(sb.length() - 1);

                for (FormulaAST.RowStruct rs : f.rowVarStructs.get(var)) {
                    if (debug) System.out.println("expandRowVar(): variable row struct " + rs);
                    String literal = rs.literal;
                    String pred = rs.pred;
                    if (kb.kbCache.valences.get(pred) == null && !pred.equals("__quantList")) {
                        System.out.println("expandRowVar(): null valence for " + pred + " in " + f);
                        continue;
                    }
                    if (kb.kbCache.valences.get(pred) == -1 && rs.rowvar.equals(var)) {
                        String newliteral = literal.replace("@" + varName, sb.toString());
                        // and we don't want a false match to a part of a var name
                        String fnSuffix = "Fn";
                        if (!pred.endsWith("Fn"))
                            fnSuffix = "";
                        String newPredName = pred;
                        if (!pred.equals("__quantList"))
                            newPredName = pred + "_" + Integer.toString(arity) + fnSuffix;
                        if (debug) System.out.println("expandRowVar(): replace pred : " +
                                pred + " with new pred: " + newPredName);
                        if (debug) System.out.println("expandRowVar(): in literal : " +
                                newliteral);
                        if (!pred.equals("__quantList"))
                            newliteral = newliteral.replace(pred, newPredName);
                        rs.literal = newliteral;
                        f.setFormula(f.getFormula().replace(literal, newliteral));
                    }
                }

                f.setFormula(f.getFormula().replace(var, sb.toString()));
                result.add(f);
            }
            flist = new HashSet<>();
            flist.addAll(result);
            if (debug) System.out.println("expandRowVar()  result for var: " + var);
            if (debug) {
                for (FormulaAST fp : flist) {
                    if (fp.getFormula().contains("@"))
                        System.out.println("RowVar.expandRowVar(): Error - row var in output -");
                    System.out.println(fp.getFormula() + "\n");
                }
            }
            result = new HashSet<>();
            result.addAll(flist);
        }
        return result;
    }

    /** ***************************************************************
     */
    public HashSet<FormulaAST> expandRowVar(HashSet<FormulaAST> rowvars) {

        HashSet<FormulaAST> result = new HashSet<>();
        for (FormulaAST f : rowvars)
            if (!f.higherOrder && !f.containsNumber)
            result.addAll(expandRowVar(f));
        return result;
    }
}
