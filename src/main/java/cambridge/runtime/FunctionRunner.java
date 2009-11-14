package cambridge.runtime;

import cambridge.ExpressionEvaluationException;
import cambridge.parser.expressions.Expression;

import java.util.HashMap;

/**
 * User: erdinc
 * Date: Nov 9, 2009
 * Time: 11:53:59 PM
 */
public abstract class FunctionRunner {
   public static HashMap<String, FunctionRunner> functions = new HashMap<String, FunctionRunner>();

   static {
      functions.put("text", new ResourceBundleFunction());
   }

   public static FunctionRunner getInstance(String name) {
      return functions.get(name);
   }

   public abstract Object eval(TemplateProperties p, Expression[] params) throws ExpressionEvaluationException;
}