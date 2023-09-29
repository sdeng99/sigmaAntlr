## What is sigmaAntlr?
==========================
A reimplementation of Sigma using the ANTLR parser and storing formulas in an abstract syntax tree

## Overview
==========================
The parser folder is generated from antlr and examples of usage of this project is down below. The .g4 files Suokif and Tptp are both implementation of Sigma to generate the parser folder.

## Installation
==========================

Install homebrew (if not already and install wget from homebrew)
```
brew install wget
```

First we need to get the antlr jar and we will do this by using wget. We will also be setting the alias for antlr.
```
wget http://www.antlr.org/download/antlr-4.7-complete.jar

alias antlr='java -jar $PWD/antlr-4.7-complete.jar'
```

## Suokif.g4 Quick Start
==========================

We will be inputting a string and they will be parsed as tokens within antlr to be created an Abstract Syntax Tree
```
cd /home/user/workspace/sigmaAntlr/src/java/com/articulate/sigma/parsing

java -Xmx500M -cp antlr-4.8-complete.jar:$CLASSPATH org.antlr.v4.Tool
  -package com.articulate.sigma.parsing /home/user/workspace/sigmaAntlr/Suokif.g4
```

If you want to compile the grammar manually rather than running ant
```
javac -classpath ".:antlr-4.8-complete.jar" Suokif*.java

java -classpath ".:antlr-4.8-complete.jar" org.antlr.v4.gui.TestRig Suokif file -tokens -gui
```

## Tptp.g4 Quick Start
==========================
There's an ANTLR grammar for TPTP that also can be used
```
antlr4 -package tptp_parser Tptp.g4
```

# Test Inputs
[formula_test_1](testFormula.txt)
[row_pred_var_test_1](rowAndPredVarList.txt)

## Licensing
==========================
Apache 2.0 License
