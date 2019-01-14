import ast.Exp;
import utils.*;

import java.io.FileReader;

public class SyntaxTest {
    static public void main(String argv[]) {
        try {
            Parser p = new Parser(new Lexer(new FileReader(argv[0])));

            Exp expression = null;
            try {
                expression = (Exp) p.parse().value;
            } catch (Exception e) {
                // Erreur de syntax, on retourne 1
                System.exit(1);
            }
            // Syntax correct, on retourne 0
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            // Autre erreur, on retourne 2
            System.exit(2);
        }
    }
}
