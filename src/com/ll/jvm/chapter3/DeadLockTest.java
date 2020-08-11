package com.ll.jvm.chapter3;


// jstack java进程id
public class DeadLockTest {

    private static Object lock1 = new Object();
    private static Object lock2 = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
           synchronized (lock1) {
               try {
                   System.out.println("Thread1 begin. ");
                   Thread.sleep(5000);
               } catch (InterruptedException e){

               }

               synchronized (lock2) {
                   System.out.println("Thread1 end. ");
               }
           }
        }).start();
        new Thread(() -> {
           synchronized (lock2) {
               try {
                   System.out.println("Thread2 begin. ");
                   Thread.sleep(5000);
               } catch (InterruptedException e){

               }

               synchronized (lock1) {
                   System.out.println("Thread2 end. ");
               }
           }
        }).start();

        System.out.println("main thread end. ");
    }
}
