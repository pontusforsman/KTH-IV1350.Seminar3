# KTH IV1350 Seminar 3 – Point-of-Sale System

This project is developed as part of the [Object-Oriented Design, IV1350](https://www.kth.se/student/kurser/kurs/IV1350) course at KTH Royal Institute of Technology.

## Course Context

This project was developed for Seminar 3: Implementation in the course [Object-Oriented Design, IV1350](https://www.kth.se/student/kurser/kurs/IV1350) at KTH Royal Institute of Technology.

Seminar 3 builds upon the work from previous seminars:
- **Seminar 1:** Analysis – requirements and use case analysis
- **Seminar 2:** Design – system and class design
  
The implementation in Seminar 3 is based on the analysis and design artifacts produced in those earlier seminars.

### Project Goal
- Practice translating a design model to source code.
- Practice writing unit tests.
- Apply and discuss established guidelines for object-oriented programming.

### Assignment Summary
- Implement a POS (Point-of-Sale) system following the MVC (Model-View-Controller) and layered architecture.
- Cover the basic flow and alternative flow 3-4b as specified in the seminar [requirements](REQUIREMENTS.md).
- Replace the user interface with a single class (`View`) containing hard-coded calls to the controller.
- Simulate external systems (inventory, accounting) within the integration layer.
- Follow the [object-oriented and unit testing guidelines](#guidelines) as summarized below.
- Write unit tests for all classes in `controller`, `model`, and `integration`.

## Guidelines

This project follows the object-oriented programming and unit testing guidelines from chapters 6 and 7 of *A First Course in Object-Oriented Development: A Hands-On Approach* by Leif Lindbäck:

### Object-Oriented Design
- **High Cohesion:** Each class has a single, well-defined responsibility.
- **Low Coupling:** Classes and packages are designed to minimize dependencies.
- **Encapsulation:** Attributes are kept private and accessed via public methods.
- **Well-Defined Public Interfaces:** Only necessary methods are exposed.
- **Clear Naming:** Classes, methods, and variables have descriptive names.
- **Documentation:** All public classes and methods are documented.
- **Short, Focused Methods:** Methods are concise and focused on a single task.
- **Favor Composition:** Composition is used over inheritance where appropriate.
- **Immutability:** Value objects (e.g., `Amount`, DTOs) are immutable.

### Unit Testing
- **Test Important Classes:** Unit tests are written for all logic-heavy classes.
- **Test Public Methods:** All public methods with logic are tested.
- **Test Edge Cases:** Both typical and edge cases are covered.
- **Isolated Tests:** Each test is independent.
- **Automated Testing:** Tests are automated using JUnit and Maven.
- **Readable Tests:** Test methods are clearly named and easy to understand.

For more details, see [Course literature](https://e.pcloud.link/publink/show?code=XZa6eKZI6J9qagkziJ7TiyNrMf9qQhMDrC7) and the [course example code repository on GitHub](https://github.com/oodbook/code).

---

## Project Structure

- `controller/` – Application logic and coordination
- `model/` – Business logic (sales, items, payments, receipts)
- `integration/` – Simulated external systems (inventory, accounting)
- `view/` – User interface (console-based)
- `startup/` – Application entry point

---

## Restrictions

- Limited use of design patterns (as per course instructions)
- No use of exceptions (as per course instructions)

---

## Requirements

See [REQUIREMENTS.md](REQUIREMENTS.md) for detailed functional requirements and business rules.

---

## License

This project is licensed under the MIT License. See [LICENSE.md](LICENSE.md) for details.

This project is for educational purposes as part of the course Object-Oriented Design IV1350 at KTH Royal Institute of Technology.
