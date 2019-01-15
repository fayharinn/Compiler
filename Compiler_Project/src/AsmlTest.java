import ast.Exp;
import utils.AsmlTools;
import utils.Lexer;
import utils.Parser;
import visitor.TypeCheckVisitor;

import java.io.FileReader;

public class AsmlTest {
    static public void main(String argv[]) {
        try {

            // Normalement pas d'erreur ici car syntaxe OK
            Parser p = new Parser(new Lexer(new FileReader(argv[0])));
            Exp expression = (Exp) p.parse().value;
            assert (expression != null);

            try {
                // On check le type
                AsmlTools.save(expression, "lol.asml");

            } catch (Exception e) {
                // Erreur de durant le check du type, on retourne 1
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
