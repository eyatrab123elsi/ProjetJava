# Learnify - E-Learning Platform with Quiz Module

**Project Description:** This project aims to create a robust e-learning platform with a focus on providing an interactive quiz module. It allows teachers to create, edit, and manage quizzes, and students to take quizzes and view results.

---

### **Current Features:**

-   **User Management:**
    -   Users can log in as either "Teacher" or "Student."
-   **Quiz Management (Backend):**
    -   Teachers can create quizzes.
    -   Teachers can delete quizzes.
    -   Teachers can edit quizzes.
-   **Question Management (Backend & UI):**
    -   Teachers can create multiple-choice questions with:
        -   Question text.
        -   Four options (A, B, C, D).
        -   The correct answer index (0 for A, 1 for B, 2 for C, 3 for D).
    -   Teachers can edit and update the created questions.
    -   Teachers can delete questions.
    -   Teachers can view a list of all questions.
    -   Users can see the question text as the options in the combobox to chose a question for editing.
-  **User Interface (JavaFX):**
    - Login UI to chose the type of user (Teacher or Student).
    - Teacher dashboard to Create, Edit and Delete Questions, and see the Quiz list.
    - Student dashboard to see the Quiz list, start a Quiz (currently a placeholder), and view Results (currently a placeholder).
    - Create Question UI.
    - Edit Question UI, where we can choose a question using a combobox and edit its fields.
    - Delete Question UI, where we can choose a question using a combobox and then delete it.
    - Quiz List UI, to list all quizzes.
    - Student Quiz and Results UIs (currently placeholders).
-   **Database Storage:**
    -   Questions and quizzes are stored in a MySQL database (`learning_platform`).
    -   The application automatically creates the database and tables (`quiz`, `question`, `user`, `result`) if they don't exist.
-   **Backend Architecture:**
    -   Implemented using a Model-DAO-Service-Controller architecture.
    -   Uses JDBC for database interactions.

### **Technologies Used:**

-   Java (JDK 21 - as specified in the `pom.xml`)
-   MySQL (via XAMPP or similar)
-   JDBC (MySQL Connector/J)
-   JavaFX (for the user interface)
-   Maven (for project management and dependency handling)
-   SceneBuilder for creating the UI.

---

### **Next Steps:**

-  Implement functionality for students to take his result as PDF.
- Add styling with css to improve the user experience.
- Write unit tests to increase project stability.
- Merge my part with my friends part.