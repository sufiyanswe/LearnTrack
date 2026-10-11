# LearnTrack — Setup Instructions

## 1. Overview

This document explains how to configure the Java development environment and run the LearnTrack Student & Course Management System.

## 2. Prerequisites

Before running the application, ensure that you have:

- A Java Development Kit (JDK) installed.
- IntelliJ IDEA or another Java-compatible IDE, or a terminal.
- The LearnTrack source code available locally.

## 3. Install and Configure Java

### Step 1: Verify Java Installation

Open Command Prompt or a terminal and execute:

```bash
java -version
```

This displays the installed Java runtime version.

Next, execute:

```bash
javac -version
```

This verifies that the Java compiler is available.

If either command is not recognized, install a JDK and configure your system's environment variables if necessary.

### Step 2: Configure the Project SDK

In IntelliJ IDEA:

1. Open the LearnTrack project.
2. Navigate to **File → Project Structure → Project**.
3. Select an installed JDK as the Project SDK.
4. Apply the changes.

The configured JDK should be compatible with the source code.

## 4. Open the Project

1. Open IntelliJ IDEA.
2. Select **Open** and choose the LearnTrack project directory.
3. Ensure that the `src` directory is recognized as the source directory.
4. Verify that the Java packages follow the `com.airtribe.learntrack` package structure.
5. Wait for the IDE to finish indexing the project.

## 5. Run the Application Using IntelliJ IDEA

1. Navigate to `src/com/airtribe/learntrack/Main.java`.
2. Open `Main.java`.
3. Locate the `main()` method.
4. Click the Run icon beside the method or use the IDE's Run command.
5. Follow the displayed console menu to manage students, courses, and enrollments.

## 6. Compile and Run from the Command Line

Run these commands from the project root directory.

### Compile

```bash
javac -d out -sourcepath src src/com/airtribe/learntrack/Main.java
```

The `-sourcepath src` option tells the compiler where to find source files referenced by the entry point. The `-d out` option places compiled class files in the `out` directory.

### Run

```bash
java -cp out com.airtribe.learntrack.Main
```

The `-cp out` option tells Java where to find the compiled classes. The fully qualified class name identifies the application entry point.

## 7. Troubleshooting

### `java` or `javac` Is Not Recognized

Verify that the JDK is installed and that the required executable directory is configured in your system's `PATH`.

### Main Class Cannot Be Found

Check that compilation completed successfully and that the package declaration in `Main.java` matches its directory structure.

### Compilation Errors

Read the first compiler error carefully. Check package names, imports, method signatures, and references to classes or enum values.

### Application Does Not Accept Input

Ensure that the application is running in a console that supports standard input.

## 8. Data Storage

LearnTrack uses in-memory collections for storage. Data created during an execution is not retained after the application terminates.

## 9. Environment Verification

Record the output from your own development environment before submitting the project:

- **Java runtime version:** Run `java -version`.
- **Java compiler version:** Run `javac -version`.
- **IDE:** Record the IDE and version, if applicable.
- **Execution result:** Confirm whether `Main.java` starts successfully.

Do not report a successful compilation or execution until you have verified it locally.
