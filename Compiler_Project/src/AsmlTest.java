import ast.Exp;
import utils.AsmlTools;
import utils.Lexer;
import utils.Parser;
import visitor.*;

import java.io.FileReader;

public class AsmlTest {
    static public void main(String argv[]) {
        try {
            // On lit le fichier et on crée l'arbre
            Parser p = new Parser(new Lexer(new FileReader(argv[0])));
            Exp expression = (Exp) p.parse().value;
            assert (expression != null);
            expression = expression.accept(new KNormVisitor());
            expression = expression.accept(new AlphaConvVisitor());
            expression = expression.accept(new LetExpressionsVisitor());
            expression = expression.accept(new ClosureConversion());

            // On écrit l'asml dans un fichier
            AsmlTools.save(expression, argv[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
