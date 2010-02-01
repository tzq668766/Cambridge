package cambridge;

import cambridge.model.Fragment;
import cambridge.model.FragmentList;
import cambridge.model.TemplateDocument;
import cambridge.parser.TemplateParser;
import cambridge.parser.TemplateTokenizer;
import cambridge.runtime.TemplateProperties;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

/**
 * User: erdinc
 * Date: Oct 13, 2009
 * Time: 10:55:06 AM
 */
public class ParserTest {
   static TemplateProperties properties;

   @BeforeClass
   public static void init() {
      properties = new TemplateProperties();
      properties.put("var", "simple");
      properties.put("id", "test");
      properties.put("exp", "class=\"x\"");
      ArrayList<Integer> list = new ArrayList<Integer>();
      list.add(1);
      list.add(2);
      list.add(3);
      properties.put("list", list);
      properties.put("condition", true);
   }

   String full = "<!DOCTYPE html>\n" +
      "<html>\n" +
      "<body>\n" +
      "\n" +
      "<div>Simple expression simple</div>\n" +
      "\n" +
      "<div id=\"test2\">Complex</div>\n" +
      "\n" +
      "<div id=\"test\">Expression Attribute</div>\n" +
      "\n" +
      "<div class=\"x\">Expression inside tag</div>\n" +
      "\n" +
      "<div>Condition true</div>\n" +
      "\n" +
      "\n" +
      "   <div>1</div>\n" +
      "   <div>2</div>\n" +
      "   <div>3</div>\n" +
      "\n" +
      "<ul>\n" +
      "   <li>1</li>\n" +
      "   <li>2</li>\n" +
      "</ul>\n" +
      "\n" +
      "</body>\n" +
      "</html>";

   String before = "<!DOCTYPE html>\n" +
      "<html>\n" +
      "<body>\n" +
      "\n" +
      "<div>Simple expression simple</div>\n" +
      "\n" +
      "<div id=\"test2\">Complex</div>\n" +
      "\n" +
      "<div id=\"test\">Expression Attribute</div>\n" +
      "\n";

   String after = "\n" +
      "\n" +
      "<div>Condition true</div>\n" +
      "\n" +
      "\n" +
      "   <div>1</div>\n" +
      "   <div>2</div>\n" +
      "   <div>3</div>\n" +
      "\n" +
      "<ul>\n" +
      "   <li>1</li>\n" +
      "   <li>2</li>\n" +
      "</ul>\n" +
      "\n" +
      "</body>\n" +
      "</html>";

   String inside = "\n" +
      "\n" +
      "<div>Simple expression simple</div>\n" +
      "\n" +
      "<div id=\"test2\">Complex</div>\n" +
      "\n" +
      "<div id=\"test\">Expression Attribute</div>\n" +
      "\n" +
      "<div class=\"x\">Expression inside tag</div>\n" +
      "\n" +
      "<div>Condition true</div>\n" +
      "\n" +
      "\n" +
      "   <div>1</div>\n" +
      "   <div>2</div>\n" +
      "   <div>3</div>\n" +
      "\n" +
      "<ul>\n" +
      "   <li>1</li>\n" +
      "   <li>2</li>\n" +
      "</ul>\n" +
      "\n";

   @Test
   public void testFull() {
      try {
         TemplateTokenizer tokenizer = new TemplateTokenizer(ParserTest.class.getResourceAsStream("full.html"));
         TemplateParser parser = new TemplateParser(tokenizer);
         TemplateDocument t = parser.parse();
         assertNotNull(t);
         FragmentList fragments = t.normalize();


         StringBuilder builder = new StringBuilder();

         for (Fragment f : fragments) {
            f.eval(properties, builder);
         }

         assertEquals(full, builder.toString());

      } catch (IOException e) {
         e.printStackTrace();
      } catch (TemplateParsingException e) {
         e.printStackTrace();
      } catch (BehaviorInstantiationException e) {
         e.printStackTrace();
      } catch (TemplateEvaluationException e) {
         e.printStackTrace();
      }
   }

   @Test
   public void testSelectBefore() {
      try {
         TemplateTokenizer tokenizer = new TemplateTokenizer(ParserTest.class.getResourceAsStream("full.html"));
         TemplateParser parser = new TemplateParser(tokenizer);
         TemplateDocument t = parser.parse();
         FragmentList fragments = t.select("before /html/body/div[3]");

         StringBuilder builder = new StringBuilder();

         for (Fragment f : fragments) {
            f.eval(properties, builder);
         }

         assertEquals(before, builder.toString());

      } catch (IOException e) {
         e.printStackTrace();
      } catch (TemplateParsingException e) {
         e.printStackTrace();
      } catch (BehaviorInstantiationException e) {
         e.printStackTrace();
      } catch (TemplateEvaluationException e) {
         e.printStackTrace();
      }
   }

   @Test
   public void testSelectAfter() {
      try {
         TemplateTokenizer tokenizer = new TemplateTokenizer(ParserTest.class.getResourceAsStream("full.html"));
         TemplateParser parser = new TemplateParser(tokenizer);
         TemplateDocument t = parser.parse();
         FragmentList fragments = t.select("after /html/body/div[3]");

         StringBuilder builder = new StringBuilder();

         for (Fragment f : fragments) {
            f.eval(properties, builder);
         }

         assertEquals(after, builder.toString());

      } catch (IOException e) {
         e.printStackTrace();
      } catch (TemplateParsingException e) {
         e.printStackTrace();
      } catch (BehaviorInstantiationException e) {
         e.printStackTrace();
      } catch (TemplateEvaluationException e) {
         e.printStackTrace();
      }
   }

   @Test
   public void testSelectInside() {
      try {
         TemplateTokenizer tokenizer = new TemplateTokenizer(ParserTest.class.getResourceAsStream("full.html"));
         TemplateParser parser = new TemplateParser(tokenizer);
         TemplateDocument t = parser.parse();
         FragmentList fragments = t.select("inside /html/body");

         StringBuilder builder = new StringBuilder();

         for (Fragment f : fragments) {
            f.eval(properties, builder);
         }

         assertEquals(inside, builder.toString());

      } catch (IOException e) {
         e.printStackTrace();
      } catch (TemplateParsingException e) {
         e.printStackTrace();
      } catch (BehaviorInstantiationException e) {
         e.printStackTrace();
      } catch (TemplateEvaluationException e) {
         e.printStackTrace();
      }
   }
}