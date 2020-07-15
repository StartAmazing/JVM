package com.ll.jvm.chapter1;

import java.io.FileInputStream;
import java.lang.reflect.Method;

public class MyClassLoaderTest {
    static class MyClassLoader extends ClassLoader {
        private String classPath;

        public MyClassLoader(String classPath) {
            this.classPath = classPath;
        }

        private byte[] loadByte(String name) throws Exception {
            name = name.replaceAll("\\.", "/");
            FileInputStream fis = new FileInputStream(classPath + "/" + name + ".class");
            int len = fis.available();
            byte[] data = new byte[len];
            fis.read(data);
            fis.close();
            return data;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] data = loadByte(name);

                // defineClass将一个字节数组转为class对象，这个字节数组是class文件读取之后最终的字节数组
                return defineClass(name, data, 0, data.length);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ClassNotFoundException();
            }
        }

        @Override
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            synchronized (getClassLoadingLock(name)) {
                //First, check if the class has already been loaded
                Class<?> c = findLoadedClass(name);

                //if still not found, then invoke findClass in order to find the class
                long t1 = System.nanoTime();
                if (c == null) {
                    c = findClass(name);
                }

                // this is the defining class loader; record the stats
                sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                sun.misc.PerfCounter.getFindClasses().increment();
                if (resolve) {
                    resolveClass(c);
                }

                return c;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // 初始化类加载器，先初始化父类ClassLoader， 其中会把自定义类加载棋的父加载器设置为应用程序类加载器
        MyClassLoader classLoader = new MyClassLoader("D:/test");
        // D盘创建 test/com/ll/jvm层级目录，将某个类的复制xx.class丢入该目录下
        Class clazz = classLoader.findClass("com.ll.jvm.xx");
        Object obj = clazz.newInstance();
        Method xxx = clazz.getDeclaredMethod("xxx", null);
        xxx.invoke(obj, null);
        System.out.println(clazz.getClassLoader().getClass().getName());
    }


}
