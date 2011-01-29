package cambridge.parser;

import cambridge.ExpressionEvaluationException;
import cambridge.TemplateEvaluationException;
import cambridge.model.ExtensionNode;
import cambridge.parser.expressions.Expression;
import cambridge.parser.expressions.Expressions;
import cambridge.parser.expressions.ListExpression;
import play.i18n.Messages;
import java.io.IOException;
import java.util.Map;

/**
 * @author Erdinc YILMAZEL
 * @since 1/28/11
 */
public class PlayMessagesExtensionNode extends ExtensionNode {
   Expression expression;
   String expr;

   public PlayMessagesExtensionNode(String expr) {
      expression = Expressions.parse(expr);
      this.expr = expr;
   }

   public void eval(Map<String, Object> bindings, Appendable out) throws IOException, TemplateEvaluationException {
      try {
         if (expression instanceof ListExpression) {
            ListExpression e = (ListExpression) expression;

            if (e.size() > 1) {
               Object message = e.get(0).eval(bindings);
               Object[] params = new Object[e.size() - 1];
               for (int i = 0; i < params.length; i++) {
                  params[i] = e.get(i + 1).eval(bindings);
               }

               out.append(Messages.get(message, params));
            }
         }
      } catch (ExpressionEvaluationException e) {
         throw new TemplateEvaluationException("Could not execute the expression: " + e.getMessage(), getBeginLine(), getBeginColumn(), expr);
      }
   }
}