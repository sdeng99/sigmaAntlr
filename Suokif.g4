grammar Suokif ;

file : (sentence | comment)+ EOF ;

sentence : (relsent | logsent | quantsent | variable) ;

quantsent : (forall | exists) ;
exists : '(' 'exists' '(' variable+ ')' sentence ')' ;
forall : '(' 'forall' '(' variable+ ')' sentence ')' ;

logsent :  (notsent | andsent | orsent | xorsent | implies | iff | eqsent) ;
iff : '(' '<=>' sentence sentence ')' ;
implies :  '(' '=>' sentence sentence ')' ;
andsent : '(' 'and' sentence sentence+ ')' ;
orsent : '(' 'or' sentence sentence+ ')' ;
xorsent : '(' 'xor' sentence sentence+ ')' ;
notsent : '(' 'not' sentence ')' ;
eqsent : '(' 'equal' term term ')' ;

funterm : '(' FUNWORD argument+ ')' ;
FUNWORD : LETTER WORDCHAR* 'Fn';

relsent : ('(' IDENTIFIER argument+ ')') | ('(' variable argument+ ')')  ;

argument : (sentence | term) ;
term : (funterm | variable | string | number | FUNWORD | IDENTIFIER) ;
IDENTIFIER : LETTER WORDCHAR* ;

NUMBER : '-'? DIGIT+ ([.,] DIGIT+)? EXPONENT? ;
number : NUMBER ;

WORDCHAR : (LETTER | DIGIT | '-' | '_') ;

STRING : '"' ~('"')+ '"' ;
string : STRING ;

//COMMENT : ';' ~('\r' | '\n')* ;
COMMENT : ';' ~('\r' | '\n')* -> skip ;
comment : COMMENT ;

//REGVAR : '?' [a-zA-Z0-9]+ ;
REGVAR : '?' WORDCHAR+ ;
//ROWVAR : '@' [a-zA-Z0-9]+ ;
ROWVAR : '@' WORDCHAR+ ;
variable : (REGVAR | ROWVAR) ;

EXPONENT : 'e' '-'? DIGIT+ ;
LETTER : [A-Za-z] ;
DIGIT : [0-9] ;
WHITESPACE : [ \n\t\r]+ -> skip ;
