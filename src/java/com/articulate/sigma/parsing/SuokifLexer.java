// Generated from Suokif.g4 by ANTLR 4.8
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
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, FUNWORD=11, IDENTIFIER=12, NUMBER=13, WORDCHAR=14, STRING=15, 
		COMMENT=16, REGVAR=17, ROWVAR=18, EXPONENT=19, LETTER=20, DIGIT=21, WHITESPACE=22;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "FUNWORD", "IDENTIFIER", "NUMBER", "WORDCHAR", "STRING", "COMMENT", 
			"REGVAR", "ROWVAR", "EXPONENT", "LETTER", "DIGIT", "WHITESPACE"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\30\u00b2\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\3\2\3\2\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6"+
		"\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\f\3\f\7\f\\\n\f\f\f\16\f_\13\f\3\f\3\f\3\f\3\r"+
		"\3\r\3\r\7\rg\n\r\f\r\16\rj\13\r\3\16\5\16m\n\16\3\16\6\16p\n\16\r\16"+
		"\16\16q\3\16\3\16\6\16v\n\16\r\16\16\16w\5\16z\n\16\3\16\5\16}\n\16\3"+
		"\17\3\17\3\17\5\17\u0082\n\17\3\20\3\20\6\20\u0086\n\20\r\20\16\20\u0087"+
		"\3\20\3\20\3\21\3\21\7\21\u008e\n\21\f\21\16\21\u0091\13\21\3\22\3\22"+
		"\6\22\u0095\n\22\r\22\16\22\u0096\3\23\3\23\6\23\u009b\n\23\r\23\16\23"+
		"\u009c\3\24\3\24\5\24\u00a1\n\24\3\24\6\24\u00a4\n\24\r\24\16\24\u00a5"+
		"\3\25\3\25\3\26\3\26\3\27\6\27\u00ad\n\27\r\27\16\27\u00ae\3\27\3\27\2"+
		"\2\30\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35"+
		"\20\37\21!\22#\23%\24\'\25)\26+\27-\30\3\2\n\4\2..\60\60\4\2//aa\3\2$"+
		"$\4\2\f\f\17\17\5\2\62;C\\c|\4\2C\\c|\3\2\62;\5\2\13\f\17\17\"\"\2\u00c2"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\3/\3\2\2"+
		"\2\5\61\3\2\2\2\78\3\2\2\2\t:\3\2\2\2\13A\3\2\2\2\rE\3\2\2\2\17H\3\2\2"+
		"\2\21L\3\2\2\2\23O\3\2\2\2\25S\3\2\2\2\27Y\3\2\2\2\31c\3\2\2\2\33l\3\2"+
		"\2\2\35\u0081\3\2\2\2\37\u0083\3\2\2\2!\u008b\3\2\2\2#\u0092\3\2\2\2%"+
		"\u0098\3\2\2\2\'\u009e\3\2\2\2)\u00a7\3\2\2\2+\u00a9\3\2\2\2-\u00ac\3"+
		"\2\2\2/\60\7*\2\2\60\4\3\2\2\2\61\62\7g\2\2\62\63\7z\2\2\63\64\7k\2\2"+
		"\64\65\7u\2\2\65\66\7v\2\2\66\67\7u\2\2\67\6\3\2\2\289\7+\2\29\b\3\2\2"+
		"\2:;\7h\2\2;<\7q\2\2<=\7t\2\2=>\7c\2\2>?\7n\2\2?@\7n\2\2@\n\3\2\2\2AB"+
		"\7>\2\2BC\7?\2\2CD\7@\2\2D\f\3\2\2\2EF\7?\2\2FG\7@\2\2G\16\3\2\2\2HI\7"+
		"c\2\2IJ\7p\2\2JK\7f\2\2K\20\3\2\2\2LM\7q\2\2MN\7t\2\2N\22\3\2\2\2OP\7"+
		"p\2\2PQ\7q\2\2QR\7v\2\2R\24\3\2\2\2ST\7g\2\2TU\7s\2\2UV\7w\2\2VW\7c\2"+
		"\2WX\7n\2\2X\26\3\2\2\2Y]\5)\25\2Z\\\5\35\17\2[Z\3\2\2\2\\_\3\2\2\2]["+
		"\3\2\2\2]^\3\2\2\2^`\3\2\2\2_]\3\2\2\2`a\7H\2\2ab\7p\2\2b\30\3\2\2\2c"+
		"h\5)\25\2dg\5)\25\2eg\5+\26\2fd\3\2\2\2fe\3\2\2\2gj\3\2\2\2hf\3\2\2\2"+
		"hi\3\2\2\2i\32\3\2\2\2jh\3\2\2\2km\7/\2\2lk\3\2\2\2lm\3\2\2\2mo\3\2\2"+
		"\2np\5+\26\2on\3\2\2\2pq\3\2\2\2qo\3\2\2\2qr\3\2\2\2ry\3\2\2\2su\t\2\2"+
		"\2tv\5+\26\2ut\3\2\2\2vw\3\2\2\2wu\3\2\2\2wx\3\2\2\2xz\3\2\2\2ys\3\2\2"+
		"\2yz\3\2\2\2z|\3\2\2\2{}\5\'\24\2|{\3\2\2\2|}\3\2\2\2}\34\3\2\2\2~\u0082"+
		"\5)\25\2\177\u0082\5+\26\2\u0080\u0082\t\3\2\2\u0081~\3\2\2\2\u0081\177"+
		"\3\2\2\2\u0081\u0080\3\2\2\2\u0082\36\3\2\2\2\u0083\u0085\7$\2\2\u0084"+
		"\u0086\n\4\2\2\u0085\u0084\3\2\2\2\u0086\u0087\3\2\2\2\u0087\u0085\3\2"+
		"\2\2\u0087\u0088\3\2\2\2\u0088\u0089\3\2\2\2\u0089\u008a\7$\2\2\u008a"+
		" \3\2\2\2\u008b\u008f\7=\2\2\u008c\u008e\n\5\2\2\u008d\u008c\3\2\2\2\u008e"+
		"\u0091\3\2\2\2\u008f\u008d\3\2\2\2\u008f\u0090\3\2\2\2\u0090\"\3\2\2\2"+
		"\u0091\u008f\3\2\2\2\u0092\u0094\7A\2\2\u0093\u0095\t\6\2\2\u0094\u0093"+
		"\3\2\2\2\u0095\u0096\3\2\2\2\u0096\u0094\3\2\2\2\u0096\u0097\3\2\2\2\u0097"+
		"$\3\2\2\2\u0098\u009a\7B\2\2\u0099\u009b\t\6\2\2\u009a\u0099\3\2\2\2\u009b"+
		"\u009c\3\2\2\2\u009c\u009a\3\2\2\2\u009c\u009d\3\2\2\2\u009d&\3\2\2\2"+
		"\u009e\u00a0\7g\2\2\u009f\u00a1\7/\2\2\u00a0\u009f\3\2\2\2\u00a0\u00a1"+
		"\3\2\2\2\u00a1\u00a3\3\2\2\2\u00a2\u00a4\5+\26\2\u00a3\u00a2\3\2\2\2\u00a4"+
		"\u00a5\3\2\2\2\u00a5\u00a3\3\2\2\2\u00a5\u00a6\3\2\2\2\u00a6(\3\2\2\2"+
		"\u00a7\u00a8\t\7\2\2\u00a8*\3\2\2\2\u00a9\u00aa\t\b\2\2\u00aa,\3\2\2\2"+
		"\u00ab\u00ad\t\t\2\2\u00ac\u00ab\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae\u00ac"+
		"\3\2\2\2\u00ae\u00af\3\2\2\2\u00af\u00b0\3\2\2\2\u00b0\u00b1\b\27\2\2"+
		"\u00b1.\3\2\2\2\23\2]fhlqwy|\u0081\u0087\u008f\u0096\u009c\u00a0\u00a5"+
		"\u00ae\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}