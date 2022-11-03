package test;

import lexer.*;
import parser.*;
import java.io.*;

public class Tester {
	public static void main(String[] args) {
		if(args.length<=0){}
		else if("lexer".equalsIgnoreCase(args[0])){
			testLexer();
			return;
		}
		else if("parser".equalsIgnoreCase(args[0])){
			testParser();
			return;
		}

		//Default
		testParser();
	}

	private static void testParser(){
		Lexer lex = new Lexer();
		BufferedReader br=null;
		try {
			br = new BufferedReader(new FileReader(getPath("input-parser.txt")));
			Parser parser = new Parser(lex, br);
			parser.start();
			System.out.println("Input OK");
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private static void testLexer(){
		Lexer lex = new Lexer();
		BufferedReader br=null;
		try {
			br = new BufferedReader(new FileReader(getPath("input-lexer.txt")));

			Token tok;
			do {
				tok = lex.lexical_scan(br);
				if (tok == null)
					return;
				System.out.print(tok + " ");
			} while (tok.tag != Tag.EOF);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private static String getPath(String relativeFilename){
		return "test/"+relativeFilename;
	}
}
