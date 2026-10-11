# LearnTrack — Design Notes

## 1. Overview

LearnTrack is organized into separate packages for entities, repositories, services, utilities, exceptions, and enums. This structure separates responsibilities and makes the application easier to understand, maintain, and extend.

The application uses Core Java and in-memory collections rather than a database.

## 2. Why Does LearnTrack Use ArrayList?

LearnTrack uses Java's `ArrayList` to store students, courses, and enrollments in memory.

An `ArrayList` is a resizable array that implements the `List` interface. Unlike a regular array, it can grow or shrink as elements are added or removed.

### Reasons for Using ArrayList

- **Dynamic size:** The collection can accommodate changing numbers of students, courses, and enrollments.
- **Ordered elements:** Elements retain their list order unless an operation changes that order.
- **Index-based access:** Elements can be accessed by their positions.
- **Simple iteration:** The application can traverse collections to find, display, or update records.
- **Suitable for this assignment:** It demonstrates Java Collections Framework fundamentals without requiring database integration.

### Trade-offs

An `ArrayList` is not automatically synchronized for concurrent modifications. Searching for an element by ID generally requires a linear scan when no separate index is maintained. Adding an element at the end is amortized constant time, while inserting or removing elements in the middle can require shifting subsequent elements.

For this assignment's in-memory storage requirements, these trade-offs are acceptable.

### Data Persistence Limitation

Repository data exists only in memory. When the application terminates, the stored records are lost.

A future version could use a database or another persistent storage mechanism, but that is outside the current project's scope.

## 3. Why Are Static Members Used?

A static member belongs to a class rather than to an individual object.

LearnTrack uses static functionality where behavior or data should be associated with the class itself rather than with a particular instance.

### ID Generation

The `IdGenerator` utility class provides ID-generation methods that can be called through the class without creating an `IdGenerator` object.

For example, a call such as `IdGenerator.generateEnrollmentId()` invokes a static method if that method is declared static in the implementation.

This avoids creating a separate utility object for each ID-generation request.

### Static Versus Instance Members

| Static member | Instance member |
|---|---|
| Associated with the class | Associated with an object |
| Accessed through the class name when accessible | Accessed through an object reference when accessible |
| Shared at the class level | Each object has its own instance state |
| Suitable for stateless utility operations | Suitable for object-specific data and behavior |

Student names, email addresses, batch information, and active status belong to individual student objects. They should therefore be represented as instance state rather than shared static fields.

## 4. Why Is Inheritance Used?

Inheritance allows one class to acquire accessible fields and methods from another class.

LearnTrack uses a shared `Person` base class for common personal information.

The `Student` and `Trainer` classes extend `Person`, allowing them to reuse common fields such as:

- ID
- First name
- Last name
- Email

Each subclass can also define its own additional fields. For example, a student has batch information, while a trainer has a specialization.

### Benefits of Inheritance

- **Code reuse:** Common person-related state and behavior are defined once.
- **Reduced duplication:** Subclasses do not need to independently redefine all shared person attributes.
- **Clearer domain model:** Students and trainers are represented as specialized types of people.
- **Extensibility:** Additional person-related types could reuse the base class in a future version.

### The Role of `super`

In a subclass constructor, `super(...)` can invoke a constructor of the parent class to initialize inherited state.

This allows `Student` and `Trainer` to reuse `Person`'s initialization logic rather than duplicating it.

## 5. Encapsulation

Encapsulation combines an object's state and behavior while controlling direct access to its internal data.

LearnTrack entity classes use fields and accessor methods to represent and expose object state.

Where fields are private, callers cannot access them directly and must use the methods provided by the class.

This supports controlled access and makes it easier to maintain consistent object state.

## 6. Separation of Responsibilities

LearnTrack separates its functionality into several layers.

### Entity Layer

Represents the application's domain objects, including students, courses, and enrollments.

### Repository Layer

Stores and retrieves objects using in-memory collections. Repository classes provide the data-access operations used by services.

### Service Layer

Contains application logic and validation. For example, enrollment logic checks whether a student and course exist and whether they are active before creating an enrollment.

### User Interface Layer

The `Main` class provides the menu-driven console interface. It reads user input and delegates application operations to services.

### Utility, Exception, and Enum Layers

- **Utility:** Provides reusable functionality such as ID generation.
- **Exception:** Represents application-specific error conditions.
- **Enum:** Defines a fixed set of enrollment statuses.

Separating these responsibilities makes it easier to change one part of the application without unnecessarily changing every other part.

## 7. Exception Handling

LearnTrack uses custom exceptions to communicate application errors.

For example:

- `EntityNotFoundException` represents an attempt to access an entity that does not exist.
- `InvalidInputException` represents invalid input or an operation that violates application rules.

These exceptions allow the service layer to communicate failures to the user interface, where they can be handled and presented as understandable messages.

## 8. Design Limitations and Future Improvements

The current design is intentionally limited to the assignment's Core Java requirements.

Possible future improvements include:

- Persistent database storage.
- Automated unit tests.
- More comprehensive input validation.
- Improved lookup performance for larger collections.
- A graphical or web-based user interface.

These improvements should be considered separately from the requirements of the current assignment.

## 9. Summary

LearnTrack uses `ArrayList` for straightforward in-memory storage, static utility methods for shared operations such as ID generation, and inheritance to reuse common person-related state and behavior. Its layered structure separates the user interface, business logic, and data access, making the project easier to maintain and understand.

