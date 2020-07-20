// Generated from Suokif.g4 by ANTLR 4.8
package com.articulate.sigma.parsing;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SuokifParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, FUNWORD=11, IDENTIFIER=12, NUMBER=13, WORDCHAR=14, STRING=15, 
		COMMENT=16, REGVAR=17, ROWVAR=18, EXPONENT=19, LETTER=20, DIGIT=21, WHITESPACE=22;
	public static final int
		RULE_file = 0, RULE_sentence = 1, RULE_quantsent = 2, RULE_exists = 3, 
		RULE_forall = 4, RULE_logsent = 5, RULE_iff = 6, RULE_implies = 7, RULE_andsent = 8, 
		RULE_orsent = 9, RULE_notsent = 10, RULE_eqsent = 11, RULE_funterm = 12, 
		RULE_relsent = 13, RULE_argument = 14, RULE_term = 15, RULE_number = 16, 
		RULE_string = 17, RULE_comment = 18, RULE_variable = 19;
	private static String[] makeRuleNames() {
		return new String[] {
			"file", "sentence", "quantsent", "exists", "forall", "logsent", "iff", 
			"implies", "andsent", "orsent", "notsent", "eqsent", "funterm", "relsent", 
			"argument", "term", "number", "string", "comment", "variable"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "'exists'", "')'", "'forall'", "'<=>'", "'=>'", "'and'", 
			"'or'", "'not'", "'equal'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, "FUNWORD", 
			"IDENTIFIER", "NUMBER", "WORDCHAR", "STRING", "COMMENT", "REGVAR", "ROWVAR", 
			"EXPONENT", "LETTER", "DIGIT", "WHITESPACE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Suokif.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SuokifParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class FileContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(SuokifParser.EOF, 0); }
		public List<SentenceContext> sentence() {
			return getRuleContexts(SentenceContext.class);
		}
		public SentenceContext sentence(int i) {
			return getRuleContext(SentenceContext.class,i);
		}
		public List<CommentContext> comment() {
			return getRuleContexts(CommentContext.class);
		}
		public CommentContext comment(int i) {
			return getRuleContext(CommentContext.class,i);
		}
		public FileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_file; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).enterFile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).exitFile(this);
		}
	}

	public final FileContext file() throws RecognitionException {
		FileContext _localctx = new FileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_file);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(42); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(42);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__0:
				case REGVAR:
				case ROWVAR:
					{
					setState(40);
					sentence();
					}
					break;
				case COMMENT:
					{
					setState(41);
					comment();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(44); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << COMMENT) | (1L << REGVAR) | (1L << ROWVAR))) != 0) );
			setState(46);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SentenceContext extends ParserRuleContext {
		public RelsentContext relsent() {
			return getRuleContext(RelsentContext.class,0);
		}
		public LogsentContext logsent() {
			return getRuleContext(LogsentContext.class,0);
		}
		public QuantsentContext quantsent() {
			return getRuleContext(QuantsentContext.class,0);
		}
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public SentenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sentence; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).enterSentence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).exitSentence(this);
		}
	}

	public final SentenceContext sentence() throws RecognitionException {
		SentenceContext _localctx = new SentenceContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_sentence);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				{
				setState(48);
				relsent();
				}
				break;
			case 2:
				{
				setState(49);
				logsent();
				}
				break;
			case 3:
				{
				setState(50);
				quantsent();
				}
				break;
			case 4:
				{
				setState(51);
				variable();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QuantsentContext extends ParserRuleContext {
		public ForallContext forall() {
			return getRuleContext(ForallContext.class,0);
		}
		public ExistsContext exists() {
			return getRuleContext(ExistsContext.class,0);
		}
		public QuantsentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quantsent; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).enterQuantsent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).exitQuantsent(this);
		}
	}

	public final QuantsentContext quantsent() throws RecognitionException {
		QuantsentContext _localctx = new QuantsentContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_quantsent);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				setState(54);
				forall();
				}
				break;
			case 2:
				{
				setState(55);
				exists();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExistsContext extends ParserRuleContext {
		public SentenceContext sentence() {
			return getRuleContext(SentenceContext.class,0);
		}
		public List<VariableContext> variable() {
			return getRuleContexts(VariableContext.class);
		}
		public VariableContext variable(int i) {
			return getRuleContext(VariableContext.class,i);
		}
		public ExistsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exists; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).enterExists(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).exitExists(this);
		}
	}

	public final ExistsContext exists() throws RecognitionException {
		ExistsContext _localctx = new ExistsContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_exists);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			match(T__0);
			setState(59);
			match(T__1);
			setState(60);
			match(T__0);
			setState(62); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(61);
				variable();
				}
				}
				setState(64); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==REGVAR || _la==ROWVAR );
			setState(66);
			match(T__2);
			setState(67);
			sentence();
			setState(68);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForallContext extends ParserRuleContext {
		public SentenceContext sentence() {
			return getRuleContext(SentenceContext.class,0);
		}
		public List<VariableContext> variable() {
			return getRuleContexts(VariableContext.class);
		}
		public VariableContext variable(int i) {
			return getRuleContext(VariableContext.class,i);
		}
		public ForallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).enterForall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).exitForall(this);
		}
	}

	public final ForallContext forall() throws RecognitionException {
		ForallContext _localctx = new ForallContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_forall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			match(T__0);
			setState(71);
			match(T__3);
			setState(72);
			match(T__0);
			setState(74); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(73);
				variable();
				}
				}
				setState(76); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==REGVAR || _la==ROWVAR );
			setState(78);
			match(T__2);
			setState(79);
			sentence();
			setState(80);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LogsentContext extends ParserRuleContext {
		public NotsentContext notsent() {
			return getRuleContext(NotsentContext.class,0);
		}
		public AndsentContext andsent() {
			return getRuleContext(AndsentContext.class,0);
		}
		public OrsentContext orsent() {
			return getRuleContext(OrsentContext.class,0);
		}
		public ImpliesContext implies() {
			return getRuleContext(ImpliesContext.class,0);
		}
		public IffContext iff() {
			return getRuleContext(IffContext.class,0);
		}
		public EqsentContext eqsent() {
			return getRuleContext(EqsentContext.class,0);
		}
		public LogsentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logsent; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).enterLogsent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).exitLogsent(this);
		}
	}

	public final LogsentContext logsent() throws RecognitionException {
		LogsentContext _localctx = new LogsentContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_logsent);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				{
				setState(82);
				notsent();
				}
				break;
			case 2:
				{
				setState(83);
				andsent();
				}
				break;
			case 3:
				{
				setState(84);
				orsent();
				}
				break;
			case 4:
				{
				setState(85);
				implies();
				}
				break;
			case 5:
				{
				setState(86);
				iff();
				}
				break;
			case 6:
				{
				setState(87);
				eqsent();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IffContext extends ParserRuleContext {
		public List<SentenceContext> sentence() {
			return getRuleContexts(SentenceContext.class);
		}
		public SentenceContext sentence(int i) {
			return getRuleContext(SentenceContext.class,i);
		}
		public IffContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iff; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).enterIff(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).exitIff(this);
		}
	}

	public final IffContext iff() throws RecognitionException {
		IffContext _localctx = new IffContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_iff);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			match(T__0);
			setState(91);
			match(T__4);
			setState(92);
			sentence();
			setState(93);
			sentence();
			setState(94);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ImpliesContext extends ParserRuleContext {
		public List<SentenceContext> sentence() {
			return getRuleContexts(SentenceContext.class);
		}
		public SentenceContext sentence(int i) {
			return getRuleContext(SentenceContext.class,i);
		}
		public ImpliesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_implies; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).enterImplies(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).exitImplies(this);
		}
	}

	public final ImpliesContext implies() throws RecognitionException {
		ImpliesContext _localctx = new ImpliesContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_implies);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			match(T__0);
			setState(97);
			match(T__5);
			setState(98);
			sentence();
			setState(99);
			sentence();
			setState(100);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AndsentContext extends ParserRuleContext {
		public List<SentenceContext> sentence() {
			return getRuleContexts(SentenceContext.class);
		}
		public SentenceContext sentence(int i) {
			return getRuleContext(SentenceContext.class,i);
		}
		public AndsentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_andsent; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).enterAndsent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).exitAndsent(this);
		}
	}

	public final AndsentContext andsent() throws RecognitionException {
		AndsentContext _localctx = new AndsentContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_andsent);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			match(T__0);
			setState(103);
			match(T__6);
			setState(104);
			sentence();
			setState(106); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(105);
				sentence();
				}
				}
				setState(108); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << REGVAR) | (1L << ROWVAR))) != 0) );
			setState(110);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrsentContext extends ParserRuleContext {
		public List<SentenceContext> sentence() {
			return getRuleContexts(SentenceContext.class);
		}
		public SentenceContext sentence(int i) {
			return getRuleContext(SentenceContext.class,i);
		}
		public OrsentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orsent; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).enterOrsent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).exitOrsent(this);
		}
	}

	public final OrsentContext orsent() throws RecognitionException {
		OrsentContext _localctx = new OrsentContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_orsent);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(112);
			match(T__0);
			setState(113);
			match(T__7);
			setState(114);
			sentence();
			setState(116); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(115);
				sentence();
				}
				}
				setState(118); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << REGVAR) | (1L << ROWVAR))) != 0) );
			setState(120);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NotsentContext extends ParserRuleContext {
		public SentenceContext sentence() {
			return getRuleContext(SentenceContext.class,0);
		}
		public NotsentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_notsent; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).enterNotsent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).exitNotsent(this);
		}
	}

	public final NotsentContext notsent() throws RecognitionException {
		NotsentContext _localctx = new NotsentContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_notsent);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			match(T__0);
			setState(123);
			match(T__8);
			setState(124);
			sentence();
			setState(125);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EqsentContext extends ParserRuleContext {
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public EqsentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eqsent; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).enterEqsent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).exitEqsent(this);
		}
	}

	public final EqsentContext eqsent() throws RecognitionException {
		EqsentContext _localctx = new EqsentContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_eqsent);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(127);
			match(T__0);
			setState(128);
			match(T__9);
			setState(129);
			term();
			setState(130);
			term();
			setState(131);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FuntermContext extends ParserRuleContext {
		public TerminalNode FUNWORD() { return getToken(SuokifParser.FUNWORD, 0); }
		public List<ArgumentContext> argument() {
			return getRuleContexts(ArgumentContext.class);
		}
		public ArgumentContext argument(int i) {
			return getRuleContext(ArgumentContext.class,i);
		}
		public FuntermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funterm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).enterFunterm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).exitFunterm(this);
		}
	}

	public final FuntermContext funterm() throws RecognitionException {
		FuntermContext _localctx = new FuntermContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_funterm);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(133);
			match(T__0);
			setState(134);
			match(FUNWORD);
			setState(136); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(135);
				argument();
				}
				}
				setState(138); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << FUNWORD) | (1L << IDENTIFIER) | (1L << NUMBER) | (1L << STRING) | (1L << REGVAR) | (1L << ROWVAR))) != 0) );
			setState(140);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RelsentContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(SuokifParser.IDENTIFIER, 0); }
		public List<ArgumentContext> argument() {
			return getRuleContexts(ArgumentContext.class);
		}
		public ArgumentContext argument(int i) {
			return getRuleContext(ArgumentContext.class,i);
		}
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public RelsentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relsent; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).enterRelsent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).exitRelsent(this);
		}
	}

	public final RelsentContext relsent() throws RecognitionException {
		RelsentContext _localctx = new RelsentContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_relsent);
		int _la;
		try {
			setState(160);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(142);
				match(T__0);
				setState(143);
				match(IDENTIFIER);
				setState(145); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(144);
					argument();
					}
					}
					setState(147); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << FUNWORD) | (1L << IDENTIFIER) | (1L << NUMBER) | (1L << STRING) | (1L << REGVAR) | (1L << ROWVAR))) != 0) );
				setState(149);
				match(T__2);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(151);
				match(T__0);
				setState(152);
				variable();
				setState(154); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(153);
					argument();
					}
					}
					setState(156); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << FUNWORD) | (1L << IDENTIFIER) | (1L << NUMBER) | (1L << STRING) | (1L << REGVAR) | (1L << ROWVAR))) != 0) );
				setState(158);
				match(T__2);
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgumentContext extends ParserRuleContext {
		public SentenceContext sentence() {
			return getRuleContext(SentenceContext.class,0);
		}
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public ArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).enterArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).exitArgument(this);
		}
	}

	public final ArgumentContext argument() throws RecognitionException {
		ArgumentContext _localctx = new ArgumentContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_argument);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(164);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				setState(162);
				sentence();
				}
				break;
			case 2:
				{
				setState(163);
				term();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TermContext extends ParserRuleContext {
		public FuntermContext funterm() {
			return getRuleContext(FuntermContext.class,0);
		}
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public TerminalNode FUNWORD() { return getToken(SuokifParser.FUNWORD, 0); }
		public TerminalNode IDENTIFIER() { return getToken(SuokifParser.IDENTIFIER, 0); }
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).enterTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).exitTerm(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_term);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				{
				setState(166);
				funterm();
				}
				break;
			case REGVAR:
			case ROWVAR:
				{
				setState(167);
				variable();
				}
				break;
			case STRING:
				{
				setState(168);
				string();
				}
				break;
			case NUMBER:
				{
				setState(169);
				number();
				}
				break;
			case FUNWORD:
				{
				setState(170);
				match(FUNWORD);
				}
				break;
			case IDENTIFIER:
				{
				setState(171);
				match(IDENTIFIER);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumberContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(SuokifParser.NUMBER, 0); }
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).exitNumber(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_number);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(174);
			match(NUMBER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StringContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(SuokifParser.STRING, 0); }
		public StringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).enterString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).exitString(this);
		}
	}

	public final StringContext string() throws RecognitionException {
		StringContext _localctx = new StringContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_string);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(176);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CommentContext extends ParserRuleContext {
		public TerminalNode COMMENT() { return getToken(SuokifParser.COMMENT, 0); }
		public CommentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).enterComment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).exitComment(this);
		}
	}

	public final CommentContext comment() throws RecognitionException {
		CommentContext _localctx = new CommentContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_comment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(178);
			match(COMMENT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableContext extends ParserRuleContext {
		public TerminalNode REGVAR() { return getToken(SuokifParser.REGVAR, 0); }
		public TerminalNode ROWVAR() { return getToken(SuokifParser.ROWVAR, 0); }
		public VariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).enterVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuokifListener ) ((SuokifListener)listener).exitVariable(this);
		}
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_variable);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(180);
			_la = _input.LA(1);
			if ( !(_la==REGVAR || _la==ROWVAR) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\30\u00b9\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\3\2\3\2\6\2-\n\2\r\2\16\2.\3\2\3\2\3\3"+
		"\3\3\3\3\3\3\5\3\67\n\3\3\4\3\4\5\4;\n\4\3\5\3\5\3\5\3\5\6\5A\n\5\r\5"+
		"\16\5B\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\6\6M\n\6\r\6\16\6N\3\6\3\6\3\6"+
		"\3\6\3\7\3\7\3\7\3\7\3\7\3\7\5\7[\n\7\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\6\nm\n\n\r\n\16\nn\3\n\3\n\3\13\3\13"+
		"\3\13\3\13\6\13w\n\13\r\13\16\13x\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\6\16\u008b\n\16\r\16\16\16\u008c\3\16"+
		"\3\16\3\17\3\17\3\17\6\17\u0094\n\17\r\17\16\17\u0095\3\17\3\17\3\17\3"+
		"\17\3\17\6\17\u009d\n\17\r\17\16\17\u009e\3\17\3\17\5\17\u00a3\n\17\3"+
		"\20\3\20\5\20\u00a7\n\20\3\21\3\21\3\21\3\21\3\21\3\21\5\21\u00af\n\21"+
		"\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\25\2\2\26\2\4\6\b\n\f\16\20"+
		"\22\24\26\30\32\34\36 \"$&(\2\3\3\2\23\24\2\u00bd\2,\3\2\2\2\4\66\3\2"+
		"\2\2\6:\3\2\2\2\b<\3\2\2\2\nH\3\2\2\2\fZ\3\2\2\2\16\\\3\2\2\2\20b\3\2"+
		"\2\2\22h\3\2\2\2\24r\3\2\2\2\26|\3\2\2\2\30\u0081\3\2\2\2\32\u0087\3\2"+
		"\2\2\34\u00a2\3\2\2\2\36\u00a6\3\2\2\2 \u00ae\3\2\2\2\"\u00b0\3\2\2\2"+
		"$\u00b2\3\2\2\2&\u00b4\3\2\2\2(\u00b6\3\2\2\2*-\5\4\3\2+-\5&\24\2,*\3"+
		"\2\2\2,+\3\2\2\2-.\3\2\2\2.,\3\2\2\2./\3\2\2\2/\60\3\2\2\2\60\61\7\2\2"+
		"\3\61\3\3\2\2\2\62\67\5\34\17\2\63\67\5\f\7\2\64\67\5\6\4\2\65\67\5(\25"+
		"\2\66\62\3\2\2\2\66\63\3\2\2\2\66\64\3\2\2\2\66\65\3\2\2\2\67\5\3\2\2"+
		"\28;\5\n\6\29;\5\b\5\2:8\3\2\2\2:9\3\2\2\2;\7\3\2\2\2<=\7\3\2\2=>\7\4"+
		"\2\2>@\7\3\2\2?A\5(\25\2@?\3\2\2\2AB\3\2\2\2B@\3\2\2\2BC\3\2\2\2CD\3\2"+
		"\2\2DE\7\5\2\2EF\5\4\3\2FG\7\5\2\2G\t\3\2\2\2HI\7\3\2\2IJ\7\6\2\2JL\7"+
		"\3\2\2KM\5(\25\2LK\3\2\2\2MN\3\2\2\2NL\3\2\2\2NO\3\2\2\2OP\3\2\2\2PQ\7"+
		"\5\2\2QR\5\4\3\2RS\7\5\2\2S\13\3\2\2\2T[\5\26\f\2U[\5\22\n\2V[\5\24\13"+
		"\2W[\5\20\t\2X[\5\16\b\2Y[\5\30\r\2ZT\3\2\2\2ZU\3\2\2\2ZV\3\2\2\2ZW\3"+
		"\2\2\2ZX\3\2\2\2ZY\3\2\2\2[\r\3\2\2\2\\]\7\3\2\2]^\7\7\2\2^_\5\4\3\2_"+
		"`\5\4\3\2`a\7\5\2\2a\17\3\2\2\2bc\7\3\2\2cd\7\b\2\2de\5\4\3\2ef\5\4\3"+
		"\2fg\7\5\2\2g\21\3\2\2\2hi\7\3\2\2ij\7\t\2\2jl\5\4\3\2km\5\4\3\2lk\3\2"+
		"\2\2mn\3\2\2\2nl\3\2\2\2no\3\2\2\2op\3\2\2\2pq\7\5\2\2q\23\3\2\2\2rs\7"+
		"\3\2\2st\7\n\2\2tv\5\4\3\2uw\5\4\3\2vu\3\2\2\2wx\3\2\2\2xv\3\2\2\2xy\3"+
		"\2\2\2yz\3\2\2\2z{\7\5\2\2{\25\3\2\2\2|}\7\3\2\2}~\7\13\2\2~\177\5\4\3"+
		"\2\177\u0080\7\5\2\2\u0080\27\3\2\2\2\u0081\u0082\7\3\2\2\u0082\u0083"+
		"\7\f\2\2\u0083\u0084\5 \21\2\u0084\u0085\5 \21\2\u0085\u0086\7\5\2\2\u0086"+
		"\31\3\2\2\2\u0087\u0088\7\3\2\2\u0088\u008a\7\r\2\2\u0089\u008b\5\36\20"+
		"\2\u008a\u0089\3\2\2\2\u008b\u008c\3\2\2\2\u008c\u008a\3\2\2\2\u008c\u008d"+
		"\3\2\2\2\u008d\u008e\3\2\2\2\u008e\u008f\7\5\2\2\u008f\33\3\2\2\2\u0090"+
		"\u0091\7\3\2\2\u0091\u0093\7\16\2\2\u0092\u0094\5\36\20\2\u0093\u0092"+
		"\3\2\2\2\u0094\u0095\3\2\2\2\u0095\u0093\3\2\2\2\u0095\u0096\3\2\2\2\u0096"+
		"\u0097\3\2\2\2\u0097\u0098\7\5\2\2\u0098\u00a3\3\2\2\2\u0099\u009a\7\3"+
		"\2\2\u009a\u009c\5(\25\2\u009b\u009d\5\36\20\2\u009c\u009b\3\2\2\2\u009d"+
		"\u009e\3\2\2\2\u009e\u009c\3\2\2\2\u009e\u009f\3\2\2\2\u009f\u00a0\3\2"+
		"\2\2\u00a0\u00a1\7\5\2\2\u00a1\u00a3\3\2\2\2\u00a2\u0090\3\2\2\2\u00a2"+
		"\u0099\3\2\2\2\u00a3\35\3\2\2\2\u00a4\u00a7\5\4\3\2\u00a5\u00a7\5 \21"+
		"\2\u00a6\u00a4\3\2\2\2\u00a6\u00a5\3\2\2\2\u00a7\37\3\2\2\2\u00a8\u00af"+
		"\5\32\16\2\u00a9\u00af\5(\25\2\u00aa\u00af\5$\23\2\u00ab\u00af\5\"\22"+
		"\2\u00ac\u00af\7\r\2\2\u00ad\u00af\7\16\2\2\u00ae\u00a8\3\2\2\2\u00ae"+
		"\u00a9\3\2\2\2\u00ae\u00aa\3\2\2\2\u00ae\u00ab\3\2\2\2\u00ae\u00ac\3\2"+
		"\2\2\u00ae\u00ad\3\2\2\2\u00af!\3\2\2\2\u00b0\u00b1\7\17\2\2\u00b1#\3"+
		"\2\2\2\u00b2\u00b3\7\21\2\2\u00b3%\3\2\2\2\u00b4\u00b5\7\22\2\2\u00b5"+
		"\'\3\2\2\2\u00b6\u00b7\t\2\2\2\u00b7)\3\2\2\2\21,.\66:BNZnx\u008c\u0095"+
		"\u009e\u00a2\u00a6\u00ae";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}