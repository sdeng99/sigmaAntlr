// Generated from Suokif.g4 by ANTLR 4.8
package com.articulate.sigma.parsing;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SuokifParser}.
 */
public interface SuokifListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SuokifParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(SuokifParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuokifParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(SuokifParser.FileContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuokifParser#sentence}.
	 * @param ctx the parse tree
	 */
	void enterSentence(SuokifParser.SentenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuokifParser#sentence}.
	 * @param ctx the parse tree
	 */
	void exitSentence(SuokifParser.SentenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuokifParser#quantsent}.
	 * @param ctx the parse tree
	 */
	void enterQuantsent(SuokifParser.QuantsentContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuokifParser#quantsent}.
	 * @param ctx the parse tree
	 */
	void exitQuantsent(SuokifParser.QuantsentContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuokifParser#exists}.
	 * @param ctx the parse tree
	 */
	void enterExists(SuokifParser.ExistsContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuokifParser#exists}.
	 * @param ctx the parse tree
	 */
	void exitExists(SuokifParser.ExistsContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuokifParser#forall}.
	 * @param ctx the parse tree
	 */
	void enterForall(SuokifParser.ForallContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuokifParser#forall}.
	 * @param ctx the parse tree
	 */
	void exitForall(SuokifParser.ForallContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuokifParser#logsent}.
	 * @param ctx the parse tree
	 */
	void enterLogsent(SuokifParser.LogsentContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuokifParser#logsent}.
	 * @param ctx the parse tree
	 */
	void exitLogsent(SuokifParser.LogsentContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuokifParser#iff}.
	 * @param ctx the parse tree
	 */
	void enterIff(SuokifParser.IffContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuokifParser#iff}.
	 * @param ctx the parse tree
	 */
	void exitIff(SuokifParser.IffContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuokifParser#implies}.
	 * @param ctx the parse tree
	 */
	void enterImplies(SuokifParser.ImpliesContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuokifParser#implies}.
	 * @param ctx the parse tree
	 */
	void exitImplies(SuokifParser.ImpliesContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuokifParser#andsent}.
	 * @param ctx the parse tree
	 */
	void enterAndsent(SuokifParser.AndsentContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuokifParser#andsent}.
	 * @param ctx the parse tree
	 */
	void exitAndsent(SuokifParser.AndsentContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuokifParser#orsent}.
	 * @param ctx the parse tree
	 */
	void enterOrsent(SuokifParser.OrsentContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuokifParser#orsent}.
	 * @param ctx the parse tree
	 */
	void exitOrsent(SuokifParser.OrsentContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuokifParser#notsent}.
	 * @param ctx the parse tree
	 */
	void enterNotsent(SuokifParser.NotsentContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuokifParser#notsent}.
	 * @param ctx the parse tree
	 */
	void exitNotsent(SuokifParser.NotsentContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuokifParser#eqsent}.
	 * @param ctx the parse tree
	 */
	void enterEqsent(SuokifParser.EqsentContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuokifParser#eqsent}.
	 * @param ctx the parse tree
	 */
	void exitEqsent(SuokifParser.EqsentContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuokifParser#funterm}.
	 * @param ctx the parse tree
	 */
	void enterFunterm(SuokifParser.FuntermContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuokifParser#funterm}.
	 * @param ctx the parse tree
	 */
	void exitFunterm(SuokifParser.FuntermContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuokifParser#relsent}.
	 * @param ctx the parse tree
	 */
	void enterRelsent(SuokifParser.RelsentContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuokifParser#relsent}.
	 * @param ctx the parse tree
	 */
	void exitRelsent(SuokifParser.RelsentContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuokifParser#argument}.
	 * @param ctx the parse tree
	 */
	void enterArgument(SuokifParser.ArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuokifParser#argument}.
	 * @param ctx the parse tree
	 */
	void exitArgument(SuokifParser.ArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuokifParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(SuokifParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuokifParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(SuokifParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuokifParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(SuokifParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuokifParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(SuokifParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuokifParser#string}.
	 * @param ctx the parse tree
	 */
	void enterString(SuokifParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuokifParser#string}.
	 * @param ctx the parse tree
	 */
	void exitString(SuokifParser.StringContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuokifParser#comment}.
	 * @param ctx the parse tree
	 */
	void enterComment(SuokifParser.CommentContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuokifParser#comment}.
	 * @param ctx the parse tree
	 */
	void exitComment(SuokifParser.CommentContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuokifParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(SuokifParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuokifParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(SuokifParser.VariableContext ctx);
}