package net.pixeleon.khpi.oop.labfour;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class BaseClass {
    public void hello() {
        System.out.println("i am the first class");
    }
}

class DerivedClass extends BaseClass {
    @Override
    public void hello() {
        System.out.println("i am the second class");
    }
}

public class GettingClassMethod {
    public static void main(String[] args) {
        try {
            Class<?> type = Class.forName("net.pixeleon.khpi.oop.labfour.DerivedClass");
            Object obj = type.newInstance();
            Method meth = type.getMethod("hello");
            meth.invoke(obj);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
