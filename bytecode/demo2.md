# 实例2

```java
package bytecode;


public class BytecodeTest2 {

    private int a;

    private int b;

    BytecodeTest2(int a, int b){
        this.a = a;
        this.b = b;
    }

    public int sum(){
        return a + b;
    }

    public static void main(String[] args) {
        BytecodeTest2 bytecodeTest2 = new BytecodeTest2(2,3);
        System.out.println(bytecodeTest2.sum());
    }

}

```

- 字节码

```text
Classfile /D:/Code/testCode/target/classes/bytecode/BytecodeTest2.class
  Last modified 2019-8-7; size 764 bytes
  MD5 checksum df24c67da3a4a0cd34a36f6ff58f9ccb
  Compiled from "BytecodeTest2.java"
public class bytecode.BytecodeTest2
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #9.#29         // java/lang/Object."<init>":()V
   #2 = Fieldref           #4.#30         // bytecode/BytecodeTest2.a:I
   #3 = Fieldref           #4.#31         // bytecode/BytecodeTest2.b:I
   #4 = Class              #32            // bytecode/BytecodeTest2
   #5 = Methodref          #4.#33         // bytecode/BytecodeTest2."<init>":(II)V
   #6 = Fieldref           #34.#35        // java/lang/System.out:Ljava/io/PrintStream;
   #7 = Methodref          #4.#36         // bytecode/BytecodeTest2.sum:()I
   #8 = Methodref          #37.#38        // java/io/PrintStream.println:(I)V
   #9 = Class              #39            // java/lang/Object
  #10 = Utf8               a
  #11 = Utf8               I
  #12 = Utf8               b
  #13 = Utf8               <init>
  #14 = Utf8               (II)V
  #15 = Utf8               Code
  #16 = Utf8               LineNumberTable
  #17 = Utf8               LocalVariableTable
  #18 = Utf8               this
  #19 = Utf8               Lbytecode/BytecodeTest2;
  #20 = Utf8               sum
  #21 = Utf8               ()I
  #22 = Utf8               main
  #23 = Utf8               ([Ljava/lang/String;)V
  #24 = Utf8               args
  #25 = Utf8               [Ljava/lang/String;
  #26 = Utf8               bytecodeTest2
  #27 = Utf8               SourceFile
  #28 = Utf8               BytecodeTest2.java
  #29 = NameAndType        #13:#40        // "<init>":()V
  #30 = NameAndType        #10:#11        // a:I
  #31 = NameAndType        #12:#11        // b:I
  #32 = Utf8               bytecode/BytecodeTest2
  #33 = NameAndType        #13:#14        // "<init>":(II)V
  #34 = Class              #41            // java/lang/System
  #35 = NameAndType        #42:#43        // out:Ljava/io/PrintStream;
  #36 = NameAndType        #20:#21        // sum:()I
  #37 = Class              #44            // java/io/PrintStream
  #38 = NameAndType        #45:#46        // println:(I)V
  #39 = Utf8               java/lang/Object
  #40 = Utf8               ()V
  #41 = Utf8               java/lang/System
  #42 = Utf8               out
  #43 = Utf8               Ljava/io/PrintStream;
  #44 = Utf8               java/io/PrintStream
  #45 = Utf8               println
  #46 = Utf8               (I)V
{
  private int a;
    descriptor: I
    flags: ACC_PRIVATE

  private int b;
    descriptor: I
    flags: ACC_PRIVATE

  bytecode.BytecodeTest2(int, int);
    descriptor: (II)V
    flags:
    Code:
      stack=2, locals=3, args_size=3
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: aload_0
         5: iload_1
         6: putfield      #2                  // Field a:I
         9: aload_0
        10: iload_2
        11: putfield      #3                  // Field b:I
        14: return
      LineNumberTable:
        line 12: 0
        line 13: 4
        line 14: 9
        line 15: 14
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      15     0  this   Lbytecode/BytecodeTest2;
            0      15     1     a   I
            0      15     2     b   I

  public int sum();
    descriptor: ()I
    flags: ACC_PUBLIC
    Code:
      stack=2, locals=1, args_size=1
         0: aload_0
         1: getfield      #2                  // Field a:I
         4: aload_0
         5: getfield      #3                  // Field b:I
         8: iadd
         9: ireturn
      LineNumberTable:
        line 18: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      10     0  this   Lbytecode/BytecodeTest2;

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=4, locals=2, args_size=1
         0: new           #4                  // class bytecode/BytecodeTest2
         3: dup
         4: iconst_2
         5: iconst_3
         6: invokespecial #5                  // Method "<init>":(II)V
         9: astore_1
        10: getstatic     #6                  // Field java/lang/System.out:Ljava/io/PrintStream;
        13: aload_1
        14: invokevirtual #7                  // Method sum:()I
        17: invokevirtual #8                  // Method java/io/PrintStream.println:(I)V
        20: return
      LineNumberTable:
        line 22: 0
        line 23: 10
        line 24: 20
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      21     0  args   [Ljava/lang/String;
           10      11     1 bytecodeTest2   Lbytecode/BytecodeTest2;
}
SourceFile: "BytecodeTest2.java"

```
