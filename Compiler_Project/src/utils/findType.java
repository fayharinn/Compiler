package utils;

import ast.*;
import type.Type;
import type.*;

public class findType {
	
	public static Type find(Exp e) {
		switch (e.getClass().getName()) {
		case "ast.Int":
			return new TInt();
		case "ast.Bool":
			return new TBool();
		case "ast.Float":
			return new TFloat();
		
		default:
			break;
		}

		return null;
		
	}
}
