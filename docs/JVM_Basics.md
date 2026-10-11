# JVM Basics — LearnTrack

## 1. Introduction

Java is a high-level, class-based programming language. One of its important features is platform independence, which allows compiled Java programs to run on different operating systems when a compatible Java runtime is available.

Understanding the JDK, JVM, bytecode, and Java compilation process helps explain how LearnTrack is compiled and executed.

## 2. JDK, JRE, and JVM

### Java Development Kit (JDK)

The JDK provides the tools required to develop Java applications. These include the Java compiler (`javac`) and the tools needed to run and diagnose Java programs.

During LearnTrack development, the JDK is used to compile the source code and prepare the application for execution.

### Java Runtime Environment (JRE)

The JRE is traditionally described as the environment containing the JVM and supporting libraries required to run Java applications.

Modern Java distributions do not always provide a separate JRE installation. A JDK includes the components needed to run Java applications as well as development tools.

### Java Virtual Machine (JVM)

The JVM executes Java bytecode. It manages runtime operations such as class loading, bytecode verification, and memory management. JVM implementations may use interpretation and Just-In-Time (JIT) compilation to execute program instructions.

### Summary

| Component | Primary purpose |
|---|---|
| JDK | Develop, compile, and run Java applications |
| JRE | Traditional term for the Java runtime environment |
| JVM | Executes Java bytecode |

## 3. Java Compilation Process

Java source code is not normally compiled directly into operating-system-specific machine code by the Java compiler. Instead, the compiler converts source code into bytecode.

The LearnTrack execution process can be summarized as follows:

```text
Java Source Code
      |
      | javac
      v
Java Bytecode (.class files)
      |
      | JVM loads and executes
      v
Running Application
```

For LearnTrack:

1. Source files such as `Main.java`, `Student.java`, and `Course.java` contain Java code.
2. The `javac` compiler compiles the source files into `.class` files.
3. The JVM loads the required classes and executes their bytecode.
4. The application displays its menu and processes user input.

## 4. Java Bytecode

Bytecode is the intermediate instruction format produced by the Java compiler. Compiled Java classes usually use the `.class` file extension.

Bytecode is designed for execution by a compatible JVM rather than being tied directly to a particular operating system's native instruction set.

This separation between source compilation and execution contributes to Java's portability.

## 5. Platform Independence

Java is commonly associated with the principle:

**Write Once, Run Anywhere (WORA).**

A Java program can often run on different operating systems without changing its source code, provided a compatible JVM and the required dependencies are available.

For example, LearnTrack can be developed on Windows and run on another operating system with a compatible Java environment. The application may still require environment-specific adjustments if its setup or dependencies rely on operating-system features.

## 6. Class Loading and Execution

When a Java application starts, the JVM loads the classes required by the program. The runtime verifies bytecode and prepares classes for execution.

The JVM may interpret bytecode or compile frequently executed code into native machine instructions using a JIT compiler. This can improve performance during execution.

For LearnTrack, the JVM loads `com.airtribe.learntrack.Main` when the application is launched using its fully qualified class name.

## 7. Memory Management

Java provides automatic memory management through garbage collection.

Objects created during LearnTrack execution can become eligible for garbage collection when they are no longer reachable through the program's references.

For example, student and course objects are stored in repository collections. While those collections hold references to the objects, the objects remain reachable. Removing an object from a collection does not necessarily make it immediately eligible for garbage collection if other references still exist.

Garbage collection timing is managed by the JVM and should not be assumed to happen immediately.

## 8. Key Takeaways

- The JDK provides Java development tools, including `javac`.
- The JVM executes Java bytecode.
- Java source code is compiled into bytecode stored in `.class` files.
- A compatible JVM allows Java bytecode to run across different platforms.
- The JVM manages class loading and runtime execution.
- Garbage collection helps manage memory by reclaiming memory from eligible objects.

