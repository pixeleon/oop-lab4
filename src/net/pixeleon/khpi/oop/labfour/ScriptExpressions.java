package net.pixeleon.khpi.oop.labfour;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Scanner;

public class ScriptExpressions {
    public static void main(String[] args) {
        try {
            ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("JavaScript");
            String expression = new Scanner(System.in).nextLine();
            System.out.println(scriptEngine.eval(expression));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
}
