import ast.Exp;
import utils.AsmlTools;
import utils.Lexer;
import utils.Parser;
import visitor.KNormVisitor;
import visitor.LetExpressionsVisitor;
import visitor.PrintVisitor;

import java.io.FileReader;

public class _RandomTest {
    static public void main(String argv[]) {
        try {

            // Normalement pas d'erreur ici car syntaxe OK
            Parser p = new Parser(new Lexer(new FileReader(argv[0])));
            Exp expression = (Exp) p.parse().value;
            assert (expression != null);

            try {
                expression.accept(new PrintVisitor());
                expression = expression.accept(new KNormVisitor());
                System.out.println("=================");
                expression.accept(new PrintVisitor());

                //expression = expression.accept(new AlphaConvVisitor());
                expression = expression.accept(new LetExpressionsVisitor());
                System.out.println("=================");
                expression.accept(new PrintVisitor());
                //expression = expression.accept(new ClosureConversion());

                // On écrit l'asml dans un fichier
                //AsmlTools.save(expression, "test.asml");

                AsmlTools.print(expression);

            } catch (Exception e) {
                // Erreur, on retourne 1
                System.exit(1);
            }
            // Type correct, on retourne 0
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            // Autre erreur, on retourne 2
            System.exit(2);
        }
    }
}
