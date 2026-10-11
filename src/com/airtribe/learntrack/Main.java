package com.airtribe.learntrack;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.service.StudentService;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.util.IdGenerator;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentRepository enrollmentRepository = new EnrollmentRepository();

        StudentService studentService =
                new StudentService(studentRepository);

        CourseService courseService =
                new CourseService(courseRepository);

        EnrollmentService enrollmentService =
                new EnrollmentService(
                        enrollmentRepository,
                        courseRepository,
                        studentRepository
                );

        int choice = -1;

        do {
            System.out.println("\n========== LEARNTRACK ==========");
            System.out.println("1. Manage Students");
            System.out.println("2. Manage Courses");
            System.out.println("3. Manage Enrollments");
            System.out.println("0. Exit");
            System.out.println("================================");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    studentMenu(scanner, studentService);
                    break;

                case 2:
                    courseMenu(scanner, courseService);
                    break;

                case 3:
                    enrollmentMenu(scanner, enrollmentService);
                    break;

                case 0:
                    System.out.println("Thank you for using LearnTrack!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 0);

        scanner.close();
    }

    // ==================== STUDENT MANAGEMENT ====================

    private static void studentMenu(
            Scanner scanner,
            StudentService studentService
    ) {
        int choice = -1;

        do {
            System.out.println("\n====== STUDENT MANAGEMENT ======");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Find Student by ID");
            System.out.println("4. Update Student");
            System.out.println("5. Deactivate Student");
            System.out.println("0. Return to Main Menu");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        addStudent(scanner, studentService);
                        break;
                    case 2:
                        viewAllStudents(studentService);
                        break;
                    case 3:
                        findStudent(scanner, studentService);
                        break;
                    case 4:
                        updateStudent(scanner, studentService);
                        break;
                    case 5:
                        deactivateStudent(scanner, studentService);
                        break;
                    case 0:
                        System.out.println("Returning to main menu.");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (choice != 0);
    }

    private static void addStudent(
            Scanner scanner,
            StudentService studentService
    ) {
        System.out.println("\n--- Add Student ---");

        System.out.print("First name: ");
        String firstName = scanner.nextLine().trim();

        System.out.print("Last name: ");
        String lastName = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Batch: ");
        String batch = scanner.nextLine().trim();

        String id = IdGenerator.generateStudentId();

        Student student = new Student(
                id, firstName, lastName, email, batch, true
        );

        studentService.addStudent(student);

        System.out.println("Student added successfully. ID: " + id);
    }

    private static void viewAllStudents(StudentService studentService) {
        List<Student> students = studentService.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Student student : students) {
            displayStudent(student);
        }
    }

    private static void findStudent(
            Scanner scanner,
            StudentService studentService
    ) {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine().trim();

        displayStudent(studentService.findStudentById(id));
    }

    private static void updateStudent(
            Scanner scanner,
            StudentService studentService
    ) {
        System.out.print("Enter student ID to update: ");
        String id = scanner.nextLine().trim();

        Student existing = studentService.findStudentById(id);

        System.out.print("First name: ");
        String firstName = scanner.nextLine().trim();

        System.out.print("Last name: ");
        String lastName = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Batch: ");
        String batch = scanner.nextLine().trim();

        Student updated = new Student(
                id, firstName, lastName, email, batch,
                existing.isActive()
        );

        studentService.updateStudent(updated);
        System.out.println("Student updated successfully.");
    }

    private static void deactivateStudent(
            Scanner scanner,
            StudentService studentService
    ) {
        System.out.print("Enter student ID to deactivate: ");
        String id = scanner.nextLine().trim();

        studentService.deactivateStudent(id);
        System.out.println("Student deactivated successfully.");
    }

    private static void displayStudent(Student student) {
        System.out.println("--------------------------------");
        System.out.println("ID     : " + student.getId());
        System.out.println("Name   : " + student.getDisplayName());
        System.out.println("Email  : " + student.getEmail());
        System.out.println("Batch  : " + student.getBatch());
        System.out.println("Active : " + student.isActive());
        System.out.println("--------------------------------");
    }

    // ==================== COURSE MANAGEMENT ====================

    private static void courseMenu(
            Scanner scanner,
            CourseService courseService
    ) {
        int choice = -1;

        do {
            System.out.println("\n====== COURSE MANAGEMENT ======");
            System.out.println("1. Add Course");
            System.out.println("2. View All Courses");
            System.out.println("3. Find Course by ID");
            System.out.println("4. Update Course");
            System.out.println("5. Deactivate Course");
            System.out.println("6. Activate Course");
            System.out.println("0. Return to Main Menu");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        addCourse(scanner, courseService);
                        break;
                    case 2:
                        viewAllCourses(courseService);
                        break;
                    case 3:
                        findCourse(scanner, courseService);
                        break;
                    case 4:
                        updateCourse(scanner, courseService);
                        break;
                    case 5:
                        changeCourseStatus(scanner, courseService, false);
                        break;
                    case 6:
                        changeCourseStatus(scanner, courseService, true);
                        break;
                    case 0:
                        System.out.println("Returning to main menu.");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (choice != 0);
    }

    private static void addCourse(
            Scanner scanner,
            CourseService courseService
    ) {
        System.out.print("Course name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Description: ");
        String description = scanner.nextLine().trim();

        System.out.print("Duration in weeks: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Duration must be a number.");
            scanner.nextLine();
            return;
        }

        int duration = scanner.nextInt();
        scanner.nextLine();

        String id = IdGenerator.generateCourseId();

        Course course = new Course(
                id, name, description, duration, true
        );

        courseService.addCourse(course);
        System.out.println("Course added successfully. ID: " + id);
    }

    private static void viewAllCourses(CourseService courseService) {
        List<Course> courses = courseService.getAllCourses();

        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }

        for (Course course : courses) {
            displayCourse(course);
        }
    }

    private static void findCourse(
            Scanner scanner,
            CourseService courseService
    ) {
        System.out.print("Enter course ID: ");
        String id = scanner.nextLine().trim();

        displayCourse(courseService.findCourseById(id));
    }

    private static void updateCourse(
            Scanner scanner,
            CourseService courseService
    ) {
        System.out.print("Enter course ID to update: ");
        String id = scanner.nextLine().trim();

        Course existing = courseService.findCourseById(id);

        System.out.print("New course name: ");
        String name = scanner.nextLine().trim();

        System.out.print("New description: ");
        String description = scanner.nextLine().trim();

        System.out.print("New duration in weeks: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Duration must be a number.");
            scanner.nextLine();
            return;
        }

        int duration = scanner.nextInt();
        scanner.nextLine();

        Course updated = new Course(
                id, name, description, duration, existing.isActive()
        );

        courseService.updateCourse(updated);
        System.out.println("Course updated successfully.");
    }

    private static void changeCourseStatus(
            Scanner scanner,
            CourseService courseService,
            boolean activate
    ) {
        System.out.print("Enter course ID: ");
        String id = scanner.nextLine().trim();

        if (activate) {
            courseService.activateCourse(id);
            System.out.println("Course activated successfully.");
        } else {
            courseService.deactivateCourse(id);
            System.out.println("Course deactivated successfully.");
        }
    }

    private static void displayCourse(Course course) {
        System.out.println("--------------------------------");
        System.out.println("ID          : " + course.getId());
        System.out.println("Name        : " + course.getCourseName());
        System.out.println("Description : " + course.getDescription());
        System.out.println("Duration    : " + course.getDurationInWeeks()
                + " weeks");
        System.out.println("Active      : " + course.isActive());
        System.out.println("--------------------------------");
    }

    // ==================== ENROLLMENT MANAGEMENT ====================

    private static void enrollmentMenu(
            Scanner scanner,
            EnrollmentService enrollmentService
    ) {
        int choice = -1;

        do {
            System.out.println("\n===== ENROLLMENT MANAGEMENT =====");
            System.out.println("1. Enroll Student in Course");
            System.out.println("2. View All Enrollments");
            System.out.println("3. View Enrollments for a Student");
            System.out.println("4. Mark Enrollment as Completed");
            System.out.println("5. Cancel Enrollment");
            System.out.println("0. Return to Main Menu");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        enrollStudent(scanner, enrollmentService);
                        break;
                    case 2:
                        viewAllEnrollments(enrollmentService);
                        break;
                    case 3:
                        viewStudentEnrollments(scanner, enrollmentService);
                        break;
                    case 4:
                        changeEnrollmentStatus(
                                scanner,
                                enrollmentService,
                                EnrollmentStatus.COMPLETED
                        );
                        break;
                    case 5:
                        changeEnrollmentStatus(
                                scanner,
                                enrollmentService,
                                EnrollmentStatus.CANCELLED
                        );
                        break;
                    case 0:
                        System.out.println("Returning to main menu.");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (choice != 0);
    }

    private static void enrollStudent(
            Scanner scanner,
            EnrollmentService enrollmentService
    ) {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine().trim();

        System.out.print("Enter course ID: ");
        String courseId = scanner.nextLine().trim();

        enrollmentService.enrollStudent(studentId, courseId);

        System.out.println("Student enrolled successfully.");
    }

    private static void viewAllEnrollments(
            EnrollmentService enrollmentService
    ) {
        List<Enrollment> enrollments =
                enrollmentService.getAllEnrollments();

        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found.");
            return;
        }

        for (Enrollment enrollment : enrollments) {
            displayEnrollment(enrollment);
        }
    }

    private static void viewStudentEnrollments(
            Scanner scanner,
            EnrollmentService enrollmentService
    ) {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine().trim();

        List<Enrollment> enrollments =
                enrollmentService.getEnrollmentsByStudentId(studentId);

        if (enrollments.isEmpty()) {
            System.out.println(
                    "No enrollments found for student: " + studentId
            );
            return;
        }

        for (Enrollment enrollment : enrollments) {
            displayEnrollment(enrollment);
        }
    }

    private static void changeEnrollmentStatus(
            Scanner scanner,
            EnrollmentService enrollmentService,
            EnrollmentStatus status
    ) {
        System.out.print("Enter enrollment ID: ");
        String enrollmentId = scanner.nextLine().trim();

        enrollmentService.updateEnrollmentStatus(enrollmentId, status);

        System.out.println("Enrollment status updated to " + status + ".");
    }

    private static void displayEnrollment(Enrollment enrollment) {
        System.out.println("--------------------------------");
        System.out.println("Enrollment ID : " + enrollment.getId());
        System.out.println("Student ID    : " + enrollment.getStudentId());
        System.out.println("Course ID     : " + enrollment.getCourseId());
        System.out.println("Date          : " + enrollment.getEnrollmentDate());
        System.out.println("Status        : " + enrollment.getEnrollmentStatus());
        System.out.println("--------------------------------");
    }
}
