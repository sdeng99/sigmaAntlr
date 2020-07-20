package com.articulate.sigma.parsing;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import com.articulate.sigma.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SuokifVisitor extends AbstractParseTreeVisitor<String> {
    
    /** ***************************************************************
     * Merge arguments to a predicate, which may themselves be complex
     * formulas, with an existing formula.
     */
    public FormulaAST mergeFormulaAST(FormulaAST f1, FormulaAST f2) {

        f1.allVarsCache.addAll(f2.allVarsCache);
        f1.allVarsPairCache.addAll(f2.allVarsPairCache);
        f1.quantVarsCache.addAll(f2.quantVarsCache);
        f1.unquantVarsCache.addAll(f2.unquantVarsCache);
        f1.existVarsCache.addAll(f2.existVarsCache);
        f1.univVarsCache.addAll(f2.univVarsCache);
        f1.termCache.addAll(f2.termCache);
        if (f1.rowVarCache == null)
            f1.rowVarCache = new HashSet<>();
        if (f2.rowVarCache == null)
            f2.rowVarCache = new HashSet<>();
        f1.rowVarCache.addAll(f2.rowVarCache);
        if (f1.predVarCache == null)
            f1.predVarCache = new HashSet<>();
        if (f2.predVarCache == null)
            f2.predVarCache = new HashSet<>();
        f1.predVarCache.addAll(f2.predVarCache);
        return f1;
    }

    /** ***************************************************************
     * Merge arguments to a predicate, which may themselves be complex
     * formulas, with an existing formula.
     */
    public FormulaAST mergeFormulaAST(FormulaAST f1, ArrayList<FormulaAST> ar) {

        if (f1.predVarCache == null)
            f1.predVarCache = new HashSet<>();
        if (f1.rowVarCache == null)
            f1.rowVarCache = new HashSet<>();
        for (FormulaAST arf : ar) {
            f1.allVarsCache.addAll(arf.allVarsCache);
            f1.allVarsPairCache.addAll(arf.allVarsPairCache);
            f1.quantVarsCache.addAll(arf.quantVarsCache);
            f1.unquantVarsCache.addAll(arf.unquantVarsCache);
            f1.existVarsCache.addAll(arf.existVarsCache);
            f1.univVarsCache.addAll(arf.univVarsCache);
            f1.termCache.addAll(arf.termCache);
            if (arf.rowVarCache == null)
                arf.rowVarCache = new HashSet<>();
            f1.rowVarCache.addAll(arf.rowVarCache);
            if (arf.predVarCache == null)
                arf.predVarCache = new HashSet<>();
            f1.predVarCache.addAll(arf.predVarCache);
        }
        return f1;
    }

    /** ***************************************************************
     * file : (sentence | comment)+ EOF ;
     */
    public HashMap<Integer,FormulaAST> visitFile(SuokifParser.FileContext context) {

        HashMap<Integer,FormulaAST> result = new HashMap<>();
        System.out.println("Visiting file: " + context);
        System.out.println("# children: " + context.children.size());
        System.out.println("text: " + context.getText());
        for (ParseTree c : context.children) {
            System.out.println("child: " + c.getClass().getName());
            FormulaAST f = null;
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$SentenceContext")) {
                f = visitSentence((SuokifParser.SentenceContext) c);
                result.put(f.startLine,f);
            }
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$CommentContext")) {
                f = visitComment((SuokifParser.CommentContext) c);
                result.put(f.startLine,f);
            }
        }
        System.out.println("return file: " + result);
        return result;
    }
    
    /** ***************************************************************
     * sentence : (relsent | logsent | quantsent | variable) ;
     */
    public FormulaAST visitSentence(SuokifParser.SentenceContext context) {
       
        System.out.println("Visiting sentence: " + context);
        System.out.println("# children: " + context.children.size());
        System.out.println("text: " + context.getText());
        FormulaAST f = null;
        for (ParseTree c : context.children) {
            f = null;
            System.out.println("child: " + c.getClass().getName());
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$RelsentContext"))
                f = visitRelsent((SuokifParser.RelsentContext) c);
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$LogsentContext"))
                f = visitLogsent((SuokifParser.LogsentContext) c);
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$QuantsentContext"))
                f = visitQuantsent((SuokifParser.QuantsentContext) c);
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$VariableContext"))
                f = visitVariable((SuokifParser.VariableContext) c);
            if (f != null) {
                f.startLine = ((ParserRuleContext) c).getStart().getLine();
            }
        }
        System.out.println("return sentence: " + f);
        f.unquantVarsCache = f.allVarsCache;
        f.unquantVarsCache.removeAll(f.quantVarsCache);
        return f;
    }

    /** ***************************************************************
     */
    public FormulaAST visitComment(SuokifParser.CommentContext context) {
       
        System.out.println("Visiting comment: " + context);
        System.out.println(context.COMMENT().getText() + "\n");
        System.out.println("# children: " + context.children.size());
        System.out.println("text: " + context.getText());
        FormulaAST f = FormulaAST.createComment(context.getText());
        return f;
    }

    /** ***************************************************************
     * relsent : ('(' IDENTIFIER argument+ ')') | ('(' variable argument+ ')')  ;
     */
    public FormulaAST visitRelsent(SuokifParser.RelsentContext context) {

        FormulaAST result = new FormulaAST();
        result.predVarCache = new HashSet<>();
        System.out.println("Visiting relsent: " + context);
        System.out.println("# children: " + context.children.size());
        System.out.println("text: " + context.getText());
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        String pred = null;
        int argnum = 1;
        HashMap<Integer,HashSet<SuokifParser.ArgumentContext>> args = new HashMap<>();
        if (context.IDENTIFIER() != null) {
            pred = context.IDENTIFIER().toString();
            sb.append(pred + " ");
            result.termCache.add(pred);
            System.out.println("identifier: " + pred);
        }
        for (ParseTree c : context.children) {
            System.out.println("child: " + c.getClass().getName());
            FormulaAST f = null;
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$VariableContext")) {
                f = visitVariable((SuokifParser.VariableContext) c);
                mergeFormulaAST(result,f);
                pred = f.getFormula();
                result.predVarCache.add(f.getFormula());
                result.allVarsCache.add(f.getFormula());
                sb.append(f.getFormula() + " ");
            }
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$ArgumentContext")) {
                f = visitArgument((SuokifParser.ArgumentContext) c);
                HashSet<SuokifParser.ArgumentContext> argAt = args.get(argnum);
                if (argAt == null)
                    argAt = new HashSet<>();
                argAt.add((SuokifParser.ArgumentContext) c);
                args.put(argnum,argAt);
                argnum++;
                sb.append(f.getFormula() + " ");
                mergeFormulaAST(result,f);
            }
        }
        result.argMap.put(pred,args);
        sb.deleteCharAt(sb.length()-1);  // delete trailing space
        sb.append(")");
        result.setFormula(sb.toString());
        System.out.println("return relsent: " + result);
        return result;
    }

    /** ***************************************************************
     * argument : (sentence | term) ;
     */
    public FormulaAST visitArgument(SuokifParser.ArgumentContext context) {
       
        System.out.println("Visiting argument: " + context);
        System.out.println("# children: " + context.children.size());
        System.out.println("text: " + context.getText());
        FormulaAST f = null;
        for (ParseTree c : context.children) {
            System.out.println("child: " + c.getClass().getName());
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$SentenceContext"))
                f = visitSentence((SuokifParser.SentenceContext) c);
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$TermContext"))
                f = visitTerm((SuokifParser.TermContext) c);
        }
        return f;
    }

    /** ***************************************************************
     * logsent :  (notsent | andsent | orsent | implies | iff | eqsent) ;
     */
    public FormulaAST visitLogsent(SuokifParser.LogsentContext context) {
       
        System.out.println("Visiting logsent: " + context);
        System.out.println("# children: " + context.children.size());
        System.out.println("text: " + context.getText());
        FormulaAST f = null;
         for (ParseTree c : context.children) {
             System.out.println("child: " + c.getClass().getName());
             if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$NotsentContext"))
                 f = visitNotsent((SuokifParser.NotsentContext) c);
             if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$AndsentContext"))
                 f = visitAndsent((SuokifParser.AndsentContext) c);
             if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$OrsentContext"))
                 f = visitOrsent((SuokifParser.OrsentContext) c);
             if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$ImpliesContext"))
                 f = visitImplies((SuokifParser.ImpliesContext) c);
             if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$IffContext"))
                 f = visitIff((SuokifParser.IffContext) c);
             if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$EqsentContext"))
                 f = visitEqsent((SuokifParser.EqsentContext) c);
         }
         return f;
    }

    /** ***************************************************************
     * notsent : '(' 'not' sentence ')' ;
     */
    public FormulaAST visitNotsent(SuokifParser.NotsentContext context) {

        FormulaAST f = null;
        System.out.println("Visiting Notsent: " + context);
        System.out.println("# children: " + context.children.size());
        if (context.children.size() != 1)
            System.out.println("error wrong number of arguments in Notsent: ");
        System.out.println("text: " + context.getText());
        for (ParseTree c : context.children) {
             System.out.println("child: " + c.getClass().getName());
             if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$SentenceContext"))
                 f = visitSentence((SuokifParser.SentenceContext) c);
        }
        f.setFormula("(not " + f.getFormula() + ")");
        return f;
    }

    /** ***************************************************************
     * andsent : '(' 'and' sentence sentence+ ')' ;
     */
    public FormulaAST visitAndsent(SuokifParser.AndsentContext context) {
       
        System.out.println("Visiting Andsent: " + context);
        System.out.println("# children: " + context.children.size());
        if (context.children.size() < 2)
            System.out.println("error wrong number of arguments in Andsent: ");
        System.out.println("text: " + context.getText());
        ArrayList<FormulaAST> ar = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("(and ");
        for (ParseTree c : context.children) {
             System.out.println("child: " + c.getClass().getName());
             if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$SentenceContext")) {
                 FormulaAST f = visitSentence((SuokifParser.SentenceContext) c);
                 ar.add(f);
                 sb.append(f.getFormula() + " ");
             }
        }
        sb.delete(sb.length()-1,sb.length());
        sb.append(")");
        FormulaAST f = new FormulaAST();
        f.setFormula(sb.toString());
        mergeFormulaAST(f,ar);
        return f;
    }

    /** ***************************************************************
     * orsent : '(' 'or' sentence sentence+ ')' ;
     */
    public FormulaAST visitOrsent(SuokifParser.OrsentContext context) {
       
        System.out.println("Visiting Orsent: " + context);
        System.out.println("# children: " + context.children.size());
        if (context.children.size() < 2)
            System.out.println("error wrong number of arguments in Orsent: ");
        System.out.println("text: " + context.getText());
        ArrayList<FormulaAST> ar = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("(and ");
        for (ParseTree c : context.children) {
             System.out.println("child: " + c.getClass().getName());
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$SentenceContext")) {
                FormulaAST f = visitSentence((SuokifParser.SentenceContext) c);
                ar.add(f);
                sb.append(f.getFormula() + " ");
            }
        }
        sb.delete(sb.length()-1,sb.length());
        sb.append(")");
        FormulaAST f = new FormulaAST();
        f.setFormula(sb.toString());
        mergeFormulaAST(f,ar);
        return f;
    }

    /** ***************************************************************
     * implies :  '(' '=>' sentence sentence ')' ;
     */
    public FormulaAST visitImplies(SuokifParser.ImpliesContext context) {
       
        System.out.println("Visiting Implies: " + context);
        System.out.println("# children: " + context.children.size());
        if (context.children.size() != 5)
            System.out.println("error wrong number of arguments in Implies: ");
        System.out.println("text: " + context.getText());
        FormulaAST f1 = null;
        FormulaAST f2 = null;
        for (ParseTree c : context.children) {
             System.out.println("child: " + c.getClass().getName());
             if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$SentenceContext")) {
                 if (f1 == null)
                     f1 = visitSentence((SuokifParser.SentenceContext) c);
                 else
                     f2 = visitSentence((SuokifParser.SentenceContext) c);
             }
        }
        f1.setFormula("(=> " + f1.getFormula() + " " + f2.getFormula() + ")");
        f1 = mergeFormulaAST(f1,f2);
        return f1;
    }

    /** ***************************************************************
     * iff : '(' '<=>' sentence sentence ')' ;
     */
    public FormulaAST visitIff(SuokifParser.IffContext context) {
       
        System.out.println("Visiting Iff: " + context);
        System.out.println("# children: " + context.children.size());
        if (context.children.size() != 5)
            System.out.println("error wrong number of arguments in Iff: ");
        System.out.println("text: " + context.getText());
        FormulaAST f1 = null;
        FormulaAST f2 = null;
        for (ParseTree c : context.children) {
             System.out.println("child: " + c.getClass().getName());
             if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$SentenceContext")) {
                 if (f1 == null)
                     f1 = visitSentence((SuokifParser.SentenceContext) c);
                 else
                     f2 = visitSentence((SuokifParser.SentenceContext) c);
             }
        }
        f1.setFormula("(<=> " + f1.getFormula() + " " + f2.getFormula() + ")");
        f1 = mergeFormulaAST(f1,f2);
        return f1;
    }

    /** ***************************************************************
     * eqsent : '(' 'equal' term term ')' ;
     */
    public FormulaAST visitEqsent(SuokifParser.EqsentContext context) {
       
        System.out.println("Visiting Eqsent: " + context);
        System.out.println("# children: " + context.children.size());
        if (context.children.size() != 2)
            System.out.println("error wrong number of arguments in Eqsent: ");
        System.out.println("text: " + context.getText());
        FormulaAST f1 = null;
        FormulaAST f2 = null;
        for (ParseTree c : context.children) {
             System.out.println("child: " + c.getClass().getName());
             if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$TermContext")) {
                 if (f1 == null)
                     f1 = visitTerm((SuokifParser.TermContext) c);
                 else
                     f2 = visitTerm((SuokifParser.TermContext) c);
             }
        }
        f1.setFormula("(equal " + f1.getFormula() + " " + f2.getFormula() + ")");
        f1 = mergeFormulaAST(f1,f2);
        return f1;
    }

    /** ***************************************************************
     * quantsent : (forall | exists) ;
     */
    public FormulaAST visitQuantsent(SuokifParser.QuantsentContext context) {
       
        System.out.println("Visiting quantsent: " + context);
        System.out.println("# children: " + context.children.size());
        System.out.println("text: " + context.getText());
        FormulaAST f = null;
        for (ParseTree c : context.children) {
            System.out.println("child: " + c.getClass().getName());
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$ForallContext"))
               f = visitForall((SuokifParser.ForallContext) c);
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$ExistsContext"))
                f = visitExists((SuokifParser.ExistsContext) c);
        }
        return f;
    }

    /** ***************************************************************
     * forall : '(' 'forall' '(' variable+ ')' sentence ')' ;
     */
    public FormulaAST visitForall(SuokifParser.ForallContext context) {
       
        System.out.println("Visiting Forall: " + context);
        System.out.println("# children: " + context.children.size());
        if (context.children.size() != 2)
            System.out.println("error wrong number of arguments in Forall: ");
        StringBuilder varlist = new StringBuilder();
        HashSet<String> quant = new HashSet<>();
        varlist.append("(");
        StringBuilder fstring = new StringBuilder();
        FormulaAST f = null;
        String body = null;
        System.out.println("text: " + context.getText());
        for (ParseTree c : context.children) {
            System.out.println("child: " + c.getClass().getName());
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$VariableContext")) {
                FormulaAST farg = visitVariable((SuokifParser.VariableContext) c);
                quant.add(farg.getFormula());
                varlist.append(farg.getFormula() + " ");
            }
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$SentenceContext")) {
                f = visitSentence((SuokifParser.SentenceContext) c);
                body = f.getFormula();
            }
        }
        varlist.delete(varlist.length()-1,varlist.length());
        f.univVarsCache.addAll(quant);
        f.allVarsCache.addAll(quant);
        f.setFormula("(forall (" + varlist + ") " + body + ")");
        return f;
    }

    /** ***************************************************************
     * exists : '(' 'exists' '(' variable+ ')' sentence ')' ;
     */
    public FormulaAST visitExists(SuokifParser.ExistsContext context) {
       
        System.out.println("Visiting Exists: " + context);
        System.out.println("# children: " + context.children.size());
        if (context.children.size() != 2)
            System.out.println("error wrong number of arguments in Exists: ");
        System.out.println("text: " + context.getText());
        StringBuilder varlist = new StringBuilder();
        HashSet<String> quant = new HashSet<>();
        varlist.append("(");
        StringBuilder fstring = new StringBuilder();
        FormulaAST f = null;
        String body = null;
        System.out.println("text: " + context.getText());
        for (ParseTree c : context.children) {
            System.out.println("child: " + c.getClass().getName());
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$VariableContext")) {
                FormulaAST farg = visitVariable((SuokifParser.VariableContext) c);
                quant.add(farg.getFormula());
                varlist.append(farg.getFormula() + " ");
            }
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$SentenceContext")) {
                f = visitSentence((SuokifParser.SentenceContext) c);
                body = f.getFormula();
            }
        }
        varlist.delete(varlist.length()-1,varlist.length());
        f.existVarsCache.addAll(quant);
        f.allVarsCache.addAll(quant);
        f.setFormula("(exists (" + varlist + ") " + body + ")");
        return f;
    }

    /** ***************************************************************
     * variable : (REGVAR | ROWVAR) ;
     */
    public FormulaAST visitVariable(SuokifParser.VariableContext context) {

        FormulaAST f = new FormulaAST();
        System.out.println("Visiting variable: " + context);
        System.out.println("# children: " + context.children.size());
        System.out.println("text: " + context.getText());
        if (context.REGVAR() != null) {
            String regvar = context.REGVAR().toString();
            f.setFormula(regvar);
            f.allVarsCache.add(regvar);
            System.out.println("regvar: " + f);
        }
        if (context.ROWVAR() != null) {
            String row = context.ROWVAR().toString();
            f.setFormula(row);
            f.rowVarCache = new HashSet<>();
            f.rowVarCache.add(row);
            System.out.println("rowv: " + row);
        }
        return f;
    }

    /** ***************************************************************
     * term : (funterm | variable | string | number | FUNWORD | IDENTIFIER ) ;
     */
    public FormulaAST visitTerm(SuokifParser.TermContext context) {

        FormulaAST f = new FormulaAST();
        System.out.println("Visiting Term: " + context);
        System.out.println("# children: " + context.children.size());
        System.out.println("text: " + context.getText());
        if (context.IDENTIFIER() != null) {
            String ident = context.IDENTIFIER().toString();
            System.out.println("identifier: " + ident);
            f.termCache.add(ident);
            f.setFormula(ident);
        }
        if (context.FUNWORD() != null) {
            String funword = context.FUNWORD().toString();
            System.out.println("funword: " + funword);
            f.termCache.add(funword);
            f.setFormula(funword);
        }
        for (ParseTree c : context.children) { // there should be only one child
            System.out.println("child: " + c.getClass().getName());
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$FuntermContext"))
                f = visitFunterm((SuokifParser.FuntermContext) c);
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$VariableContext")) {
                f = visitVariable((SuokifParser.VariableContext) c);
                f.allVarsCache.add(f.getFormula());
            }
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$StringContext"))
                f = visitString((SuokifParser.StringContext) c);
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$NumberContext"))
                f = visitNumber((SuokifParser.NumberContext) c);
        }
        return f;
    }

    /** ***************************************************************
     * funterm : '(' FUNWORD argument+ ')' ;
     */
    public FormulaAST visitFunterm(SuokifParser.FuntermContext context) {
       
        System.out.println("Visiting funterm: " + context);
        System.out.println("# children: " + context.children.size());
        System.out.println("text: " + context.getText());
        StringBuilder sb = new StringBuilder();
        FormulaAST f = new FormulaAST();
        if (context.FUNWORD() != null) {
            String funword = context.FUNWORD().toString();
            System.out.println("funword: " + funword);
            f.termCache.add(funword);
            sb.append("(" + funword + " ");
        }
        ArrayList<FormulaAST> arf = new ArrayList<>();
        for (ParseTree c : context.children) {
            System.out.println("child: " + c.getClass().getName());
            FormulaAST farg = null;
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$ArgumentContext")) {
                farg = visitArgument((SuokifParser.ArgumentContext) c);
                arf.add(farg);
                sb.append(farg.getFormula() + " ");
            }
        }
        sb.delete(sb.length()-1,sb.length());
        sb.append(")");
        f.setFormula(sb.toString());
        mergeFormulaAST(f,arf);
        return f;
    }

    /** ***************************************************************
     */
    public FormulaAST visitString(SuokifParser.StringContext context) {
       
        System.out.println("Visiting string: " + context);
        System.out.println("# children: " + context.children.size());
        System.out.println("text: " + context.getText());
        FormulaAST f = new FormulaAST();
        f.setFormula(context.getText());
        return f;
    }

    /** ***************************************************************
     */
    public FormulaAST visitNumber(SuokifParser.NumberContext context) {
       
        System.out.println("Visiting number: " + context);
        System.out.println("# children: " + context.children.size());
        System.out.println("text: " + context.getText());
        FormulaAST f = new FormulaAST();
        f.setFormula(context.getText());
        return f;
    }
}

