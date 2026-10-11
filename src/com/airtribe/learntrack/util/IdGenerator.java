package com.airtribe.learntrack.util;

public class IdGenerator {

    private static int studentCounter = 0;
    private static int courseCounter = 0;
    private static int enrollmentCounter = 0;

    public static String generateStudentId() {
        return "S" + String.format("%03d", ++studentCounter);
    }

    public static String generateCourseId() {
        return "C" + String.format("%03d", ++courseCounter);
    }

    public static String generateEnrollmentId() {
        return "E" + String.format("%03d", ++enrollmentCounter);
    }
}