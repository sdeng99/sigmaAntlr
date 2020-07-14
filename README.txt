Intro
==========================
A reimplementation of Sigma using the ANTLR parser and storing formulas in an abstract syntax tree

Grammar
==========================
java -Xmx500M -cp antlr-4.8-complete.jar:$CLASSPATH org.antlr.v4.Tool -package com.articulate.sigma.parsing Suokif.g4

if you want to compile the grammar manually rather than running ant -

javac -classpath ".:antlr-4.8-complete.jar" Suokif*.java

java -classpath ".:antlr-4.8-complete.jar" org.antlr.v4.gui.TestRig Suokif file -tokens -gui

TPTP
==========================

There's an ANTLR grammar for TPTP that also can be used

antlr4 -package tptp_parser Tptp.g4
