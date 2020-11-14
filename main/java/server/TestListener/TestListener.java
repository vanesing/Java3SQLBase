package server.TestListener;

import server.inter.Test;

import java.lang.reflect.Method;

public class TestListener {

    public static void start(Object o){
        // не совсем понял с @BeforeSuite и @AfterSuite, а точнее как и куда их встроить.

    }

    @Test(name = "name",nick = "nick",password = "password")

    public void hello (String[]args)
    {
        try {
            Method helloVoid =this.getClass().getDeclaredMethod("Добро пожаловать в чат");
            Test test =helloVoid.getAnnotation(Test.class);
            String s =test.name();
            System.out.println(helloVoid);

        }catch (NoSuchMethodException e){
            e.printStackTrace();
        }

    }

    @Test(name = "",nick = "",password = "")

    public void correctPassword (String s) throws NoSuchMethodException {
        Method correctPasswordVoid =this.getClass().getDeclaredMethod("Пароль верный");
        Test test =correctPasswordVoid.getAnnotation(Test.class);
        String s1 =test.password();
        System.out.println(correctPasswordVoid);
    }

    @Test(name = "",nick = "",password = "")

    public void nick (String s) throws NoSuchMethodException {
        Method unicleNick =this.getClass().getDeclaredMethod("Ник не занят");
        Test test =unicleNick.getAnnotation(Test.class);
        String s1 =test.nick();
        System.out.println(unicleNick);
    }
}
