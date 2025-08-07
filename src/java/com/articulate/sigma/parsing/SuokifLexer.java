// Generated from Suokif.g4 by ANTLR 4.9.3
package com.articulate.sigma.parsing;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SuokifLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, FUNWORD=12, IDENTIFIER=13, NUMBER=14, WORDCHAR=15, 
		STRING=16, COMMENT=17, REGVAR=18, ROWVAR=19, EXPONENT=20, LETTER=21, DIGIT=22, 
		WHITESPACE=23;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "FUNWORD", "IDENTIFIER", "NUMBER", "WORDCHAR", "STRING", 
			"COMMENT", "REGVAR", "ROWVAR", "EXPONENT", "LETTER", "DIGIT", "WHITESPACE"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "'exists'", "')'", "'forall'", "'<=>'", "'=>'", "'and'", 
			"'or'", "'xor'", "'not'", "'equal'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			"FUNWORD", "IDENTIFIER", "NUMBER", "WORDCHAR", "STRING", "COMMENT", "REGVAR", 
			"ROWVAR", "EXPONENT", "LETTER", "DIGIT", "WHITESPACE"
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


	public SuokifLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Suokif.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\31\u00b9\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\3\2"+
		"\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\6\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\n"+
		"\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\7\rb\n\r\f\r\16\r"+
		"e\13\r\3\r\3\r\3\r\3\16\3\16\7\16l\n\16\f\16\16\16o\13\16\3\17\5\17r\n"+
		"\17\3\17\6\17u\n\17\r\17\16\17v\3\17\3\17\6\17{\n\17\r\17\16\17|\5\17"+
		"\177\n\17\3\17\5\17\u0082\n\17\3\20\3\20\3\20\5\20\u0087\n\20\3\21\3\21"+
		"\6\21\u008b\n\21\r\21\16\21\u008c\3\21\3\21\3\22\3\22\7\22\u0093\n\22"+
		"\f\22\16\22\u0096\13\22\3\22\3\22\3\23\3\23\6\23\u009c\n\23\r\23\16\23"+
		"\u009d\3\24\3\24\6\24\u00a2\n\24\r\24\16\24\u00a3\3\25\3\25\5\25\u00a8"+
		"\n\25\3\25\6\25\u00ab\n\25\r\25\16\25\u00ac\3\26\3\26\3\27\3\27\3\30\6"+
		"\30\u00b4\n\30\r\30\16\30\u00b5\3\30\3\30\2\2\31\3\3\5\4\7\5\t\6\13\7"+
		"\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25"+
		")\26+\27-\30/\31\3\2\t\4\2..\60\60\4\2//aa\3\2$$\4\2\f\f\17\17\4\2C\\"+
		"c|\3\2\62;\5\2\13\f\17\17\"\"\2\u00c8\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2"+
		"\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2"+
		"\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3"+
		"\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3"+
		"\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\3\61\3\2\2\2\5\63\3\2\2\2\7:\3"+
		"\2\2\2\t<\3\2\2\2\13C\3\2\2\2\rG\3\2\2\2\17J\3\2\2\2\21N\3\2\2\2\23Q\3"+
		"\2\2\2\25U\3\2\2\2\27Y\3\2\2\2\31_\3\2\2\2\33i\3\2\2\2\35q\3\2\2\2\37"+
		"\u0086\3\2\2\2!\u0088\3\2\2\2#\u0090\3\2\2\2%\u0099\3\2\2\2\'\u009f\3"+
		"\2\2\2)\u00a5\3\2\2\2+\u00ae\3\2\2\2-\u00b0\3\2\2\2/\u00b3\3\2\2\2\61"+
		"\62\7*\2\2\62\4\3\2\2\2\63\64\7g\2\2\64\65\7z\2\2\65\66\7k\2\2\66\67\7"+
		"u\2\2\678\7v\2\289\7u\2\29\6\3\2\2\2:;\7+\2\2;\b\3\2\2\2<=\7h\2\2=>\7"+
		"q\2\2>?\7t\2\2?@\7c\2\2@A\7n\2\2AB\7n\2\2B\n\3\2\2\2CD\7>\2\2DE\7?\2\2"+
		"EF\7@\2\2F\f\3\2\2\2GH\7?\2\2HI\7@\2\2I\16\3\2\2\2JK\7c\2\2KL\7p\2\2L"+
		"M\7f\2\2M\20\3\2\2\2NO\7q\2\2OP\7t\2\2P\22\3\2\2\2QR\7z\2\2RS\7q\2\2S"+
		"T\7t\2\2T\24\3\2\2\2UV\7p\2\2VW\7q\2\2WX\7v\2\2X\26\3\2\2\2YZ\7g\2\2Z"+
		"[\7s\2\2[\\\7w\2\2\\]\7c\2\2]^\7n\2\2^\30\3\2\2\2_c\5+\26\2`b\5\37\20"+
		"\2a`\3\2\2\2be\3\2\2\2ca\3\2\2\2cd\3\2\2\2df\3\2\2\2ec\3\2\2\2fg\7H\2"+
		"\2gh\7p\2\2h\32\3\2\2\2im\5+\26\2jl\5\37\20\2kj\3\2\2\2lo\3\2\2\2mk\3"+
		"\2\2\2mn\3\2\2\2n\34\3\2\2\2om\3\2\2\2pr\7/\2\2qp\3\2\2\2qr\3\2\2\2rt"+
		"\3\2\2\2su\5-\27\2ts\3\2\2\2uv\3\2\2\2vt\3\2\2\2vw\3\2\2\2w~\3\2\2\2x"+
		"z\t\2\2\2y{\5-\27\2zy\3\2\2\2{|\3\2\2\2|z\3\2\2\2|}\3\2\2\2}\177\3\2\2"+
		"\2~x\3\2\2\2~\177\3\2\2\2\177\u0081\3\2\2\2\u0080\u0082\5)\25\2\u0081"+
		"\u0080\3\2\2\2\u0081\u0082\3\2\2\2\u0082\36\3\2\2\2\u0083\u0087\5+\26"+
		"\2\u0084\u0087\5-\27\2\u0085\u0087\t\3\2\2\u0086\u0083\3\2\2\2\u0086\u0084"+
		"\3\2\2\2\u0086\u0085\3\2\2\2\u0087 \3\2\2\2\u0088\u008a\7$\2\2\u0089\u008b"+
		"\n\4\2\2\u008a\u0089\3\2\2\2\u008b\u008c\3\2\2\2\u008c\u008a\3\2\2\2\u008c"+
		"\u008d\3\2\2\2\u008d\u008e\3\2\2\2\u008e\u008f\7$\2\2\u008f\"\3\2\2\2"+
		"\u0090\u0094\7=\2\2\u0091\u0093\n\5\2\2\u0092\u0091\3\2\2\2\u0093\u0096"+
		"\3\2\2\2\u0094\u0092\3\2\2\2\u0094\u0095\3\2\2\2\u0095\u0097\3\2\2\2\u0096"+
		"\u0094\3\2\2\2\u0097\u0098\b\22\2\2\u0098$\3\2\2\2\u0099\u009b\7A\2\2"+
		"\u009a\u009c\5\37\20\2\u009b\u009a\3\2\2\2\u009c\u009d\3\2\2\2\u009d\u009b"+
		"\3\2\2\2\u009d\u009e\3\2\2\2\u009e&\3\2\2\2\u009f\u00a1\7B\2\2\u00a0\u00a2"+
		"\5\37\20\2\u00a1\u00a0\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a1\3\2\2\2"+
		"\u00a3\u00a4\3\2\2\2\u00a4(\3\2\2\2\u00a5\u00a7\7g\2\2\u00a6\u00a8\7/"+
		"\2\2\u00a7\u00a6\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\u00aa\3\2\2\2\u00a9"+
		"\u00ab\5-\27\2\u00aa\u00a9\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\u00aa\3\2"+
		"\2\2\u00ac\u00ad\3\2\2\2\u00ad*\3\2\2\2\u00ae\u00af\t\6\2\2\u00af,\3\2"+
		"\2\2\u00b0\u00b1\t\7\2\2\u00b1.\3\2\2\2\u00b2\u00b4\t\b\2\2\u00b3\u00b2"+
		"\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b5\u00b3\3\2\2\2\u00b5\u00b6\3\2\2\2\u00b6"+
		"\u00b7\3\2\2\2\u00b7\u00b8\b\30\2\2\u00b8\60\3\2\2\2\22\2cmqv|~\u0081"+
		"\u0086\u008c\u0094\u009d\u00a3\u00a7\u00ac\u00b5\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}