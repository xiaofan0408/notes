# Java字节码相关分享

## 介绍

## 字节码

- [JVM指令码表](http://xiaofan0408.github.io/jvm.html)

## 查看字节码的方式

1. javap

    查看字节码可以用jdk自带的工具 javap，他自带的使用说明如下

    ```text

    用法: javap <options> <classes>
    其中, 可能的选项包括:
    -help  --help  -?        输出此用法消息
    -version                 版本信息
    -v  -verbose             输出附加信息
    -l                       输出行号和本地变量表
    -public                  仅显示公共类和成员
    -protected               显示受保护的/公共类和成员
    -package                 显示程序包/受保护的/公共类
                            和成员 (默认)
    -p  -private             显示所有类和成员
    -c                       对代码进行反汇编
    -s                       输出内部类型签名
    -sysinfo                 显示正在处理的类的
                            系统信息 (路径, 大小, 日期, MD5 散列)
    -constants               显示最终常量
    -classpath <path>        指定查找用户类文件的位置
    -cp <path>               指定查找用户类文件的位置
    -bootclasspath <path>    覆盖引导类文件的位置
    ```

2. Java Decompiler

    Java Decompiler是一个开源的java反编译工具，一般比较少直接使用jd-core这个库用代码进行反编译，而是使用jd-gui去反编译

    1. github地址: <https://github.com/java-decompiler>
    2. jd-gui地址<https://github.com/java-decompiler/jd-gui>

3. idea 插件

## 实例

## java操作字节码

1. ASM

2. cglib

## 简单例子

## 相关资料

1. [oracle官方文档](https://docs.oracle.com/javase/specs/index.html)

2. [Java虚拟机规范(Java SE 8版)](https://book.douban.com/subject/26418340/)

3. [深入理解Java虚拟机：JVM高级特性与最佳实践](https://book.douban.com/subject/24722612/)

4. [JVM 字节码从入门到精通](https://juejin.im/book/5c25811a6fb9a049ec6b23ee)
