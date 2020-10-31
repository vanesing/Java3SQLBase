package Other;


//1. Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз (порядок – ABСABСABС). Используйте wait/notify/notifyAll.

//2. На серверной стороне сетевого чата реализовать управление потоками через ExecutorService.

//У меня на серверной по факту один поток в Обработчике клиента "ClientHandler", и как то я по строчкам прошелся, и не нашел куда еще можно было прикрутить поток.
// Не потому что лень, а просто архетикрурно не понимаю. Есть еще один поток, но он в клентской части, и то демон.
// Связывать их в Executor... мне показалось логической ошибкой.
// Но в целом из урока я понял  назначение  ExecutorService, как гораздо более удобное средство уравления потоками.
// За счет переопределяемой логики итераций защелки, и ограничения пула потоков в самом ExecutorService. Короче более гибкая штука чем wait/notify/notifyAll.

public class Main {
    private static final Object c = new Object();
    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (c){
                    for (int i = 0; i < 4; i++) {
                        System.out.println(Thread.currentThread().getName());
                        try {
                            wait(1000);
                            notify();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }notifyAll();
                    System.out.println("Конец потока");
                }
            }
        });

        Thread A = new Thread();
        Thread B = new Thread();
        Thread C =new Thread();

        A.start();
        B.start();
        C.start();
    }
}
