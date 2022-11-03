#GENERAL
all:
	javac **/*.java
clean:
	rm -f **/*.class

#NOT ASSOSIATED WITH FILE
.PHONY: all clean lexer parser

#LEXER
lexer/Lexer.class:
	javac lexer/*.java test/*.java
lexer: lexer/Lexer.class
	java test.Tester "lexer"

#PARSER
parser/Parser.class:
	javac parser/*.java test/*.java
parser: parser/Parser.class
	java test.Tester "parser"