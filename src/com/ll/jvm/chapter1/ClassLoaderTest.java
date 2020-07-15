package com.ll.jvm.chapter1;

import sun.misc.Launcher;

import java.net.URL;

/**
 * created by ll
 * 2020/07/13
 */
public class ClassLoaderTest {

    public static void main(String[] args) {
        System.out.println(String.class.getClassLoader());  // C++ 类生成的对象，也就是引导类加载器
        System.out.println(com.sun.crypto.provider.DESKeyFactory.class.getClassLoader()); // 扩展类加载器
        System.out.println(ClassLoaderTest.class.getClassLoader()); // 应用类加载器

        System.out.println("----------------------------------------------");
        ClassLoader appClassLoader = ClassLoader.getSystemClassLoader();
        ClassLoader extClassLoader = appClassLoader.getParent();
        ClassLoader bootstrapClassLoader = extClassLoader.getParent();
        System.out.println("the appClassLoader:  " + appClassLoader);
        System.out.println("the extClassLoader:  " + extClassLoader);
        System.out.println("the bootstrapClassLoader: " + bootstrapClassLoader);


        System.out.println("----------------------------------------------");
        System.out.println("bootstrapLoader 加载以下文件: ");
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL url : urLs) {
            System.out.println(url);
        }

        System.out.println("----------------------------------------------");
        System.out.println("extClassLoader 加载以下文件: ");
        System.out.println(System.getProperty("java.ext.dirs"));

        System.out.println("----------------------------------------------");
        System.out.println("appClassLoader 加载以下文件: ");
        System.out.println(System.getProperty("java.class.path"));

    }


}
