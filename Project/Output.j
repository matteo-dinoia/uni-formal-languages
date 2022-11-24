.class public Output 
.super java/lang/Object

.method public <init>()V
 aload_0
 invokenonvirtual java/lang/Object/<init>()V
 return
.end method

.method public static print(I)V
 .limit stack 2
 getstatic java/lang/System/out Ljava/io/PrintStream;
 iload_0 
 invokestatic java/lang/Integer/toString(I)Ljava/lang/String;
 invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
 return
.end method

.method public static read()I
 .limit stack 3
 new java/util/Scanner
 dup
 getstatic java/lang/System/in Ljava/io/InputStream;
 invokespecial java/util/Scanner/<init>(Ljava/io/InputStream;)V
 invokevirtual java/util/Scanner/next()Ljava/lang/String;
 invokestatic java/lang/Integer.parseInt(Ljava/lang/String;)I
 ireturn
.end method

.method public static run()V
 .limit stack 1024
 .limit locals 256
 invokestatic Output/read()I
 istore 0
 invokestatic Output/read()I
 istore 1
 invokestatic Output/read()I
 istore 2
 iload 0
 iload 2
 if_icmple L2
 goto L1
L2:
 iload 0
 istore 3
 iload 2
 istore 0
 iload 3
 istore 2
 goto L0
L1:
 ldc 11
 invokestatic Output/print(I)V
L0:
 iload 1
 iload 2
 if_icmple L5
 goto L4
L5:
 iload 2
 istore 3
 iload 1
 istore 2
 iload 3
 istore 1
 goto L3
L4:
 ldc 22
 invokestatic Output/print(I)V
L3:
 iload 0
 iload 1
 if_icmple L8
 goto L7
L8:
 iload 0
 istore 3
 iload 1
 istore 0
 iload 3
 istore 1
 goto L6
L7:
 ldc 33
 invokestatic Output/print(I)V
L6:
 iload 0
 invokestatic Output/print(I)V
 iload 1
 invokestatic Output/print(I)V
 iload 2
 invokestatic Output/print(I)V
 return
.end method
.method public static main([Ljava/lang/String;)V
 invokestatic Output/run()V
 return
.end method

