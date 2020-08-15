package com.articulate.sigma.parsing;

import com.articulate.sigma.KB;

import java.util.HashMap;
import java.util.HashSet;

// Add type guards to formulas
public class Sortals {

    private KB kb;

    public boolean debug = false;

    /** ***************************************************************
     */
    public Sortals(KB kbin) {
        kb = kbin;
    }

    /** ***************************************************************
     * Add type guards to a formula by making it the consequent of a rule
     * and making type tests into a new antecedent
     */
    public String addSortals(FormulaAST f, HashMap<String,String> types) {

        if (debug) System.out.println("Sortals.addSortals(): types: " + types);
        StringBuilder result = new StringBuilder();
        if (types.keySet().size() > 0)
            result.append("(=> ");
        if (types.keySet().size() > 1)
            result.append("(and ");
        for (String k : types.keySet()) {
            String v = types.get(k);
            if (v.endsWith("+"))
                result.append("(subclass " + k + " " + v.substring(0,v.length()-1) + ") ");
            else
                result.append("(instance " + k + " " + v + ") ");
        }
        if (types.keySet().size() > 0)
            result.deleteCharAt(result.length()-1);
        if (types.keySet().size() > 1)
            result.append(") ");
        result.append(f.getFormula());
        result.append(")");
        if (debug) System.out.println("Sortals.addSortals(): result: " + result);
        return result.toString();
    }

    /** ***************************************************************
     * Find the most specific type in a list of types.  This assumes that
     * the list has already been tested for disjointness
     */
    public String mostSpecificType(HashSet<String> types) {

        if (types.size() == 1)
            return types.iterator().next();
        if (kb.kbCache.checkDisjoint(kb,types)) {
            System.out.println("Error in Sortals.mostSpecificType(): disjoint type spec: " + types);
            return "";
        }
        return kb.kbCache.getCommonChild(types);
    }

    /** ***************************************************************
     * if variables in a formula has several possible type constraints,
     * based on their being arguments to relations, find the most
     * specific type for each
     */
    public HashMap<String, String> mostSpecificTypes(HashMap<String, HashSet<String>> vmap) {

        HashMap<String, String> themap = new HashMap<>();
        for (String var : vmap.keySet()) {
            themap.put(var,mostSpecificType(vmap.get(var)));
        }
        return themap;
    }

    /** ***************************************************************
     * If a type is already specified for a variable in a rule with an
     * instance or subclass statement, remove it from the type list so
     * that it won't be added as a type guard
     */
    public HashMap<String, String> removeExplicitTypes(HashMap<String,String> types,
                                                       HashMap<String, HashSet<String>> explicit) {

        HashMap<String, String> result = new HashMap<>();
        for (String var : types.keySet()) {
            HashSet<String> expType = explicit.get(var);
            String type = types.get(var);
            if (expType == null)
                result.put(var,type);
            else {
                for (String t : expType)
                    if (kb.compareTermDepth(type, t) > 0)
                        result.put(var, type);
            }
        }
        return result;
    }

    /** ***************************************************************
     * Find the most specific type constraint for each variable
     */
    public void winnowAllTypes(FormulaAST f) {

        f.specvarmap = mostSpecificTypes(f.varTypes);
    }

    /** ***************************************************************
     * Find the most specific type constraint for each variable and
     * create a new formula with type guards
     */
    public String addSortals(FormulaAST f) {

        if (debug) System.out.println("Sortals.addSortals():types: " + f.specvarmap);
        f.specvarmap = removeExplicitTypes(f.specvarmap,f.explicitTypes);
        if (debug) System.out.println("Sortals.addSortals():after removeExplicitTypes: " + f.specvarmap);
        String result = addSortals(f,f.specvarmap);
        return result;
    }
}
