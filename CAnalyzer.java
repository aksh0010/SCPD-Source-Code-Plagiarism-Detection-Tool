import java.io.*;
import java.util.*;
import org.anarres.cpp.*;

public class CAnalyzer {

  public static void main(String[] args) throws Exception {
    String code =
      "int main() {\n" +
      "    int x = 42;\n" +
      "    printf(\"The answer is %d\\n\", x);\n" +
      "    return 0;\n" +
      "}";
    CppReader reader = new CppReader(new StringReader(code), "test.c");
    Token[] tokens = reader.readTokens();
    CppParser parser = new CppParser(tokens, "test.c", new CppOptions());
    TranslationUnit tu = parser.parse();
    List<Declaration> decls = tu.getDeclarations();
    for (Declaration decl : decls) {
      if (decl instanceof FunctionDefinition) {
        FunctionDefinition funcDef = (FunctionDefinition) decl;
        if (funcDef.getDeclarator().getName().equals("main")) {
          System.out.println("Found main function:");
          List<Statement> stmts = funcDef.getBody().getStatements();
          for (Statement stmt : stmts) {
            if (stmt instanceof ExpressionStatement) {
              ExpressionStatement exprStmt = (ExpressionStatement) stmt;
              Expression expr = exprStmt.getExpression();
              if (expr instanceof FunctionCall) {
                FunctionCall funcCall = (FunctionCall) expr;
                String funcName = funcCall.getFunction().getName();
                if (funcName.equals("printf")) {
                  System.out.println("Found printf call:");
                  List<Expression> args = funcCall.getArguments();
                  for (Expression arg : args) {
                    System.out.println(arg.getText());
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
