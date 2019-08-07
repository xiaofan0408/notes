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

使用一个最简单的java程序看下他的字节码，其源代码如下

```java
package bytecode;

public class bytecodeTest {

    public static void main(String[] args) {
        System.out.println("Hello world");
    }

}

```

在他的class文件处使用javap输出字节码，命令为 javap -p -s -c -v byecodeTest.class
输出的字节码如下：

```text
Classfile /D:/Code/testCode/target/classes/bytecode/bytecodeTest.class
  Last modified 2019-8-7; size 557 bytes
  MD5 checksum 3fee8392ac69e00f21959f4208a77bb9
  Compiled from "bytecodeTest.java"
public class bytecode.bytecodeTest
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #6.#20         // java/lang/Object."<init>":()V
   #2 = Fieldref           #21.#22        // java/lang/System.out:Ljava/io/PrintStream;
   #3 = String             #23            // Hello world
   #4 = Methodref          #24.#25        // java/io/PrintStream.println:(Ljava/lang/String;)V
   #5 = Class              #26            // bytecode/bytecodeTest
   #6 = Class              #27            // java/lang/Object
   #7 = Utf8               <init>
   #8 = Utf8               ()V
   #9 = Utf8               Code
  #10 = Utf8               LineNumberTable
  #11 = Utf8               LocalVariableTable
  #12 = Utf8               this
  #13 = Utf8               Lbytecode/bytecodeTest;
  #14 = Utf8               main
  #15 = Utf8               ([Ljava/lang/String;)V
  #16 = Utf8               args
  #17 = Utf8               [Ljava/lang/String;
  #18 = Utf8               SourceFile
  #19 = Utf8               bytecodeTest.java
  #20 = NameAndType        #7:#8          // "<init>":()V
  #21 = Class              #28            // java/lang/System
  #22 = NameAndType        #29:#30        // out:Ljava/io/PrintStream;
  #23 = Utf8               Hello world
  #24 = Class              #31            // java/io/PrintStream
  #25 = NameAndType        #32:#33        // println:(Ljava/lang/String;)V
  #26 = Utf8               bytecode/bytecodeTest
  #27 = Utf8               java/lang/Object
  #28 = Utf8               java/lang/System
  #29 = Utf8               out
  #30 = Utf8               Ljava/io/PrintStream;
  #31 = Utf8               java/io/PrintStream
  #32 = Utf8               println
  #33 = Utf8               (Ljava/lang/String;)V
{
  public bytecode.bytecodeTest();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 6: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lbytecode/bytecodeTest;

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=1, args_size=1
         0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
         3: ldc           #3                  // String Hello world
         5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
         8: return
      LineNumberTable:
        line 9: 0
        line 10: 8
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       9     0  args   [Ljava/lang/String;
}
SourceFile: "bytecodeTest.java"

```

## java操作字节码

1. javaagent

    - 由于对字节码修改功能的巨大需求，JDK 从 JDK5 版本开始引入了java.lang.instrument 包。基本的思路是在 JVM 启动的时候添加一个代理，每个代理是一个 jar 包，其 MANIFEST.MF 文件里指定了代理类，这个代理类包含一个 premain 方法。JVM 在类加载时候会先执行代理类的 premain 方法，再执行 Java 程序本身的 main 方法，这就是 premain 名字的来源。在 premain 方法中可以对加载前的 class 文件进行修改。JDK6 还允许 JVM 在启动之后动态添加代理。

2. ASM

    - ASM 库是一款基于 Java 字节码层面的代码分析和修改工具。ASM 可以直接生产二进制的 class 文件，也可以在类被加载入 JVM 之前动态修改类行为
    - [ASM官网](https://asm.ow2.io/)
    - [ASM文档](https://asm.ow2.io/developer-guide.html)

## 简单例子

## 字节码修改技术的应用

1. Aop

2. 代码生成

3. 无侵入APM全链路监控

4. 协程

## 相关资料

1. [oracle官方文档](https://docs.oracle.com/javase/specs/index.html)

2. [Java虚拟机规范(Java SE 8版)](https://book.douban.com/subject/26418340/)

3. [深入理解Java虚拟机：JVM高级特性与最佳实践](https://book.douban.com/subject/24722612/)

4. [JVM 字节码从入门到精通](https://juejin.im/book/5c25811a6fb9a049ec6b23ee)
