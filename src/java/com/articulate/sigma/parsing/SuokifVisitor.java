package com.articulate.sigma.parsing;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import com.articulate.sigma.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SuokifVisitor extends AbstractParseTreeVisitor<String> {

    public HashSet<FormulaAST> hasRowVar = new HashSet<>();
    public HashSet<FormulaAST> hasPredVar = new HashSet<>();

    /** ***************************************************************
     * file : (sentence | comment)+ EOF ;
     * @return a map that presents each statement or comment in the same order
     * as the file.  Note that comments at the end of a SUO-KIF line that include
     * a statement are recorded as occurring on the next line
     */
    public HashMap<Integer,FormulaAST> visitFile(SuokifParser.FileContext context) {

        HashMap<Integer,FormulaAST> result = new HashMap<>();
        System.out.println("Visiting file: " + context);
        System.out.println("# children: " + context.children.size());
        System.out.println("text: " + context.getText());
        int counter = 0;
        for (ParseTree c : context.children) {
            System.out.println("child: " + c.getClass().getName());
            FormulaAST f = null;
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$SentenceContext")) {
                f = visitSentence((SuokifParser.SentenceContext) c);
                result.put(counter++,f);
                if (f.predVarCache.size() > 0)
                    hasPredVar.add(f);
                if (f.rowVarCache.size() > 0)
                    hasRowVar.add(f);
            }
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$CommentContext")) {
                f = visitComment((SuokifParser.CommentContext) c);
                result.put(counter++,f);
            }
        }
        System.out.println("return file: " + result);
        System.out.println();
        System.out.println("has pred var: " + hasPredVar);
        System.out.println();
        System.out.println("has row var: " + hasRowVar);
        System.out.println();
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
        f.quantVarsCache.addAll(f.existVarsCache);
        f.quantVarsCache.addAll(f.univVarsCache);
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
     * Set the types of any variables that appear in an instance or subclass
     * declaration
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
        ArrayList<String> argList = new ArrayList<>();
        HashMap<Integer,HashSet<SuokifParser.ArgumentContext>> args = new HashMap<>();
        if (context.IDENTIFIER() != null) {
            pred = context.IDENTIFIER().toString();
            sb.append(pred + " ");
            result.termCache.add(pred);
            result.relation = pred;
            System.out.println("identifier: " + pred);
        }
        for (ParseTree c : context.children) {
            System.out.println("child: " + c.getClass().getName());
            FormulaAST f = null;
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$VariableContext")) {
                f = visitVariable((SuokifParser.VariableContext) c);
                result.mergeFormulaAST(f);
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
                argList.add(f.getFormula());
                result.mergeFormulaAST(f);
            }
        }
        if (pred.equals("instance") && Formula.isVariable(argList.get(0)) && Formula.isTerm(argList.get(1))) {
            FormulaPreprocessor.addToMap(result.varmap, argList.get(0), argList.get(1));
            result.explicitTypes.put(argList.get(0), argList.get(1));
        }
        if (pred.equals("subclass") && Formula.isVariable(argList.get(0)) && Formula.isTerm(argList.get(1))) {
            FormulaPreprocessor.addToMap(result.varmap, argList.get(0), argList.get(1) + "+");
            result.explicitTypes.put(argList.get(0), argList.get(1) + "+");
        }
        result.argMap.put(pred,args);
        sb.deleteCharAt(sb.length()-1);  // delete trailing space
        sb.append(")");
        result.setFormula(sb.toString());
        System.out.println("pred: " + pred);
        if (argList != null && argList.size() > 1)
            System.out.println("arg 1: " + argList.get(0));
        if (argList != null && argList.size() > 2)
            System.out.println("arg 2: " + argList.get(1));
        System.out.println("return relsent: " + result);
        System.out.println("varmap: " + result.varmap);
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
        f.mergeFormulaAST(ar);
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
        f.mergeFormulaAST(ar);
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
        f1.mergeFormulaAST(f2);
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
        f1.mergeFormulaAST(f2);
        return f1;
    }

    /** ***************************************************************
     * eqsent : '(' 'equal' term term ')' ;
     * argument : (sentence | term) ;
     * term : (funterm | variable | string | number | FUNWORD | IDENTIFIER ) ;
     */
    public FormulaAST visitEqsent(SuokifParser.EqsentContext context) {
       
        System.out.println("Visiting Eqsent: " + context);
        System.out.println("# children: " + context.children.size());
        if (context.children.size() != 2)
            System.out.println("error wrong number of arguments in Eqsent: ");
        System.out.println("text: " + context.getText());
        FormulaAST f1 = null;
        FormulaAST f2 = null;
        SuokifParser.TermContext c1 = null;
        SuokifParser.TermContext c2 = null;
        for (ParseTree c : context.children) {
             System.out.println("child: " + c.getClass().getName());
             if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$TermContext")) {
                 if (f1 == null) {
                     f1 = visitTerm((SuokifParser.TermContext) c);
                     c1 = (SuokifParser.TermContext) c;
                 }
                 else {
                     f2 = visitTerm((SuokifParser.TermContext) c);
                     c2 = (SuokifParser.TermContext) c;
                 }
             }
        }
        ArrayList<SuokifParser.TermContext> oneEq = new ArrayList<>();
        oneEq.add(c1);
        oneEq.add(c2);
        f1.eqList.add(oneEq);
        f1.setFormula("(equal " + f1.getFormula() + " " + f2.getFormula() + ")");
        f1.mergeFormulaAST(f2);
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
        FormulaAST result = new FormulaAST();
        String funword = null;
        if (context.FUNWORD() != null) {
            funword = context.FUNWORD().toString();
            System.out.println("funword: " + funword);
            result.termCache.add(funword);
            result.relation = funword;
            sb.append("(" + funword + " ");
        }
        int argnum = 1;
        HashMap<Integer,HashSet<SuokifParser.ArgumentContext>> args = new HashMap<>();
        ArrayList<FormulaAST> arf = new ArrayList<>();
        for (ParseTree c : context.children) {
            System.out.println("child: " + c.getClass().getName());
            FormulaAST farg = null;
            if (c.getClass().getName().equals("com.articulate.sigma.parsing.SuokifParser$ArgumentContext")) {
                farg = visitArgument((SuokifParser.ArgumentContext) c);
                HashSet<SuokifParser.ArgumentContext> argAt = args.get(argnum);
                if (argAt == null)
                    argAt = new HashSet<>();
                argAt.add((SuokifParser.ArgumentContext) c);
                args.put(argnum,argAt);
                argnum++;
                arf.add(farg);
                sb.append(farg.getFormula() + " ");
                result.mergeFormulaAST(farg);
            }
        }
        result.argMap.put(funword,args);
        sb.delete(sb.length()-1,sb.length());
        sb.append(")");
        result.setFormula(sb.toString());
        result.mergeFormulaAST(arf);
        result.isFunctional = true;
        return result;
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

    /** ***************************************************************
     */
    public static void showHelp() {

        System.out.println("SuokifVisitor class");
        System.out.println("  options:");
        System.out.println("  -h - show this help screen");
        System.out.println("  -f <fname> - parse the file");
    }

    /** ***************************************************************
     */
    public static void main(String[] args) throws IOException {

        System.out.println("INFO in KB.main()");
        if (args != null && args.length > 0 && args[0].equals("-h"))
            showHelp();
        else {
            /* KBmanager.getMgr().initializeOnce();
            String kbName = KBmanager.getMgr().getPref("sumokbname");
            KB kb = KBmanager.getMgr().getKB(kbName); */
            if (args != null && args.length > 1 && args[0].equals("-f")) {
                CodePointCharStream inputStream = (CodePointCharStream) CharStreams.fromFileName(args[1]);
                SuokifLexer suokifLexer = new SuokifLexer(inputStream);
                CommonTokenStream commonTokenStream = new CommonTokenStream(suokifLexer);
                SuokifParser suokifParser = new SuokifParser(commonTokenStream);
                SuokifParser.FileContext fileContext = suokifParser.file();
                SuokifVisitor visitor = new SuokifVisitor();
                HashMap<Integer,FormulaAST> hm = visitor.visitFile(fileContext);
                System.out.println("row var axioms: " + visitor.hasRowVar);
                System.out.println("row var count: " + visitor.hasRowVar.size());
                System.out.println("pred var axioms: " + visitor.hasPredVar);
                System.out.println("pred var count: " + visitor.hasPredVar.size());
            }
            else
                showHelp();
        }
    }
}

