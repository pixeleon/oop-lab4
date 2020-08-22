package net.pixeleon.khpi.oop.labfour;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Scanner;

public class FieldsAndMethodsInfo {
    public static void main(String[] args) {
        String className = new Scanner(System.in).next();
        className = "java.lang.Math";
        try {
            Class<?> type = Class.forName(className);
            System.out.println("Class name: " + type.getCanonicalName());
            System.out.println("\nClass fields:");
            for(Field f : type.getDeclaredFields()) {
                f.setAccessible(true);
                System.out.printf("Name: %s, type: %s%n", f.getName(), f.getType().getCanonicalName());
            }
            System.out.println("\nClass methods:");
            for(Method m : type.getDeclaredMethods()) {
                m.setAccessible(true);
                System.out.printf("Name: %s, return type: %s%nParameter types: [\t",
                        m.getName(), m.getReturnType().getCanonicalName());
                for(Class<?> t : m.getParameterTypes()) {
                    System.out.print(t.getCanonicalName() + "\t");
                }
                System.out.println("]\n");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found!");
        }
    }
}
