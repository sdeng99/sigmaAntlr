## What is sigmaAntlr?
==========================
A reimplementation of Sigma using the ANTLR parser and storing formulas in an abstract syntax tree

## Overview
==========================
The parser folder is generated from antlr and examples of usage of this project is down below. The .g4 files Suokif and Tptp are both implementation of Sigma to generate the parser folder.

## Installation
==========================

```
alias antlr='java -jar $PWD/antlr4-4.9.3.jar'
```

## Suokif.g4 Quick Start
==========================

We will be inputting a string and they will be parsed as tokens within antlr to create an Abstract Syntax Tree
```
cd /home/user/workspace/sigmaAntlr/src/java/com/articulate/sigma/parsing

java -Xmx500M -cp lib/antlr4-4.9.3.jar org.antlr.v4.Tool
  -package com.articulate.sigma.parsing /home/user/workspace/sigmaAntlr/Suokif.g4
```

If you want to compile the grammar manually rather than running ant
```
javac -cp ".:lib/antlr4-4.9.3.jar" Suokif*.java

java -cp ".:antlr4-4.9.3.jar" org.antlr.v4.gui.TestRig Suokif file -tokens -gui
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
