package com.articulate.sigma.parsing;

import com.articulate.sigma.KB;
import com.articulate.sigma.StringUtil;

import java.util.HashMap;
import java.util.HashSet;

// Add type guards to formulas
public class Sortals {

    private KB kb;

    public boolean debug = false;
    public long disjointTime = 0;

    /** ***************************************************************
     */
    public Sortals(KB kbin) {
        kb = kbin;
    }

    /** ***************************************************************
     * Add type guards to a formula by making it the consequent of a rule
     * and making type tests into a new antecedent
     */
    public String addSortals(FormulaAST f, HashMap<String,HashSet<String>> types) {

        if (types.keySet().size() == 0) return f.getFormula();
        if (debug) System.out.println("Sortals.addSortals(): types: " + types);
        StringBuilder result = new StringBuilder();
        if (types.keySet().size() > 0)
            result.append("(=> ");
        if (types.keySet().size() > 1)
            result.append("(and ");
        for (String k : types.keySet()) {
            HashSet<String> v = types.get(k);
            for (String t : v) {
                if (t.endsWith("+"))
                    result.append("(subclass " + k + " " + t.substring(0, t.length() - 1) + ") ");
                else
                    result.append("(instance " + k + " " + t + ") ");
            }
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
        long start = System.currentTimeMillis();
        if (kb.kbCache.checkDisjoint(kb,types)) {
            System.out.println("Error in Sortals.mostSpecificType(): disjoint type spec: " + types);
            return "";
        }
        long end = (System.currentTimeMillis()-start);
        disjointTime = disjointTime + end;
        return kb.kbCache.getCommonChild(types);
    }

    /** ***************************************************************
     * if variables in a formula has several possible type constraints,
     * based on their being arguments to relations, find the most
     * specific type for each

    public HashMap<String, String> mostSpecificTypes(HashMap<String, HashSet<String>> vmap) {

        HashMap<String, String> themap = new HashMap<>();
        for (String var : vmap.keySet()) {
            themap.put(var,mostSpecificType(vmap.get(var)));
        }
        return themap;
    }
*/
    /** ***************************************************************
     * If a type is already specified for a variable in a rule with an
     * instance or subclass statement, remove it from the type list so
     * that it won't be added as a type guard
     */
    public HashMap<String, HashSet<String>> removeExplicitTypes(HashMap<String,HashSet<String>> typesMap,
                                                       HashMap<String, HashSet<String>> explicit) {

        HashMap<String, HashSet<String>> result = new HashMap<>();
        for (String var : typesMap.keySet()) {
            HashSet<String> expTypes = explicit.get(var);
            HashSet<String> types = typesMap.get(var);
            HashSet<String> newtypes = new HashSet<>();
            if (expTypes == null)
                newtypes.addAll(types);
            else {
                for (String t : types) {
                    if (!expTypes.contains(t))
                        newtypes.add(t);
                }
            }
            if (newtypes.size() > 0)
                result.put(var, newtypes);
        }
        return result;
    }

    /** ***************************************************************
     * Eliminate more general types in favor of their more specific
     * subclasses (if any)
     */
    public void elimSubsumedTypes(FormulaAST f) {

        for (String var : f.varTypes.keySet()) {
            HashSet<String> types = f.varTypes.get(var);
            HashSet<String> remove = new HashSet<>();
            for (String type1 : types) {
                for (String type2 : types) {
                    if (!StringUtil.emptyString(type1) && !StringUtil.emptyString(type2) && !type1.equals(type2)) {
                        if (kb.kbCache.subclassOf(type1, type2))
                            remove.add(type2);
                        if (kb.kbCache.subclassOf(type2,type1))
                            remove.add(type1);
                    }
                }
            }
            types.removeAll(remove);
        }
    }

    /** ***************************************************************
     * Find the most specific type constraint for each variable
     */
    public void winnowAllTypes(FormulaAST f) {

        if (debug) System.out.println("Sortals.winnowAllTypes():input: " + f.varTypes);
        elimSubsumedTypes(f);
        if (debug) System.out.println("Sortals.winnowAllTypes():output: " + f.varTypes);
    }

    /** ***************************************************************
     * Find the most specific type constraint for each variable and
     * create a new String of the formula with type guards
     */
    public String addSortals(FormulaAST f) {

        if (debug) System.out.println("Sortals.addSortals():types: " + f.varTypes);
        HashMap<String,HashSet<String>> types = removeExplicitTypes(f.varTypes,f.explicitTypes);
        if (debug) System.out.println("Sortals.addSortals():after removeExplicitTypes: " + f.varTypes);
        String result = addSortals(f,types);
        f.setFormula(result);
        return result;
    }
}
