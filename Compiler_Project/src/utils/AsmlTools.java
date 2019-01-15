package utils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;

import ast.Exp;
import type.Type;
import visitor.ASMLVisitor;
import visitor.TypeCheckVisitor;


/**
 * La classe permettant soit d'afficher le code ASML d'un AST, soit l'enregistrer.
 *
 */
public class AsmlTools {

    /**
     * Affiche le code ASML d'un AST.
     *
     * @param expression l'expression du haut de l'arbre.
    *
     */
	public static void print(Exp expression)  {
		System.out.println("\n------ ASML GENERATED ---- \n");
        TypeCheckVisitor typeCheckVisitor = new TypeCheckVisitor();
        expression.accept(typeCheckVisitor);
        HashMap<String,Type> env = typeCheckVisitor.getEnvironement().getCurentEnvironement().getGho();
        ASMLVisitor asmlv = new ASMLVisitor(env);
        expression.accept(asmlv);
        String x2 = "";
        for(String x:asmlv.float_code) {
            if(x.contains("?")) {
                x2 = x.replace("?","new_varid_");
            } else {
                x2 = x;
            }
            System.out.print(x2);
        }
        for(String x:asmlv.fun_code) {
            if(x.contains("?")) {
                x2 = x.replace("?","new_varid_");
            } else {
                x2 = x;
            }
            System.out.print(x2);

        }

        for(String x:asmlv.code) {
            if(x.contains("?")) {
                x2 = x.replace("?","new_varid_");
            } else {
                x2 = x;
            }
            System.out.print(x2);
        }

	}


    /**
     * Enregistre le code ASML d'un AST
     *
     * @param expression l'expression du haut de l'arbre.
     * @param output le nom du fichier de sortie.
    *
     */
	public static void save(Exp expression,String output) throws FileNotFoundException {
        TypeCheckVisitor typeCheckVisitor = new TypeCheckVisitor();
        expression.accept(typeCheckVisitor);
        HashMap<String,Type> env = typeCheckVisitor.getEnvironement().getCurentEnvironement().getGho();
        PrintWriter out = new PrintWriter(output); // l'output de l'asml
        ASMLVisitor asmlv = new ASMLVisitor(env);
        expression.accept(asmlv);
        String x2 = "";
        for(String x:asmlv.float_code) {
            if(x.contains("?")) {
                x2 = x.replace("?","new_varid_");
            } else {
                x2 = x;
            }
            out.print(x2);
        }
        for(String x:asmlv.fun_code) {
            if(x.contains("?")) {
                x2 = x.replace("?","new_varid_");
            } else {
                x2 = x;
            }
            out.print(x2);

        }

        for(String x:asmlv.code) {
            if(x.contains("?")) {
                x2 = x.replace("?","new_varid_");
            } else {
                x2 = x;
            }
            out.print(x2);
        }
        out.close();

        System.out.println(output+" Successfuly written");
	}


}
