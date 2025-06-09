# Laser Maze Project

**Course:** 02160-f25  
**University:** Technical University of Denmark


**Group Members:**
- Adal Tanach Yaman (s234545)
- Balint Regoczi (s234566)
- Frederik Walther Liberoth Christoffersen (s234644)
- Markus Anker (s234658)
- Seyed Soheil Ghoreishi (s230880)

---

## 1. Project Overview

LazerMaze is a single-player puzzle game where the player is given an initial configuration of obstacles in a grid and has the goal of displacing all the obstacles from a second set such that a laser beam hits the target. Examples of obstacles are a "blocker" that does not let the laser pass or a "mirror" that bends the beam by 90 degrees or a "splitter" that splits the beam into two. The exact set of obstacles can be read online (see https://www.nordicgames.is/wp-content/uploads/2015/05/Lazer_Maze_Rules_EN.pdf, however the set of initial configurations (that in the original game are in increasing level of difficulty) are left open.

---

## 2. Project Description

This project is developed following a Behavior-Driven Development (BDD) approach. The main focus is to ensure that all functionalities—both mandatory and optional—are clearly defined, implemented, and tested.

### Mandatory Functionalities

- **M1: Game and rules**  
  The system should capture the essence of the game into an object-oriented system, thus giving the opportunity to interact with the system and play the game according to the expected rules (i.e., the game should not allow for wrong behavior). The rules are the following: The laser must be triggered when the laser button is pushed, and it must move through the board and interact with all obstacles presented in the manual as expected.


- **M2: Graphical User Interface (GUI)**  
  It should be possible to interact with the game via a graphical user interface. The graphics should not be advanced.

### Optional Functionalities (at least four)
To be filled in later.

---

## 3. Development Process

### Behavior-Driven Development (BDD)
- **Identification and Prioritization:** All functionalities have been identified and prioritized based on project requirements.
- **User Stories and Scrum Board:** User stories are defined and tracked on a Scrum board in Jira and used in Cucumber tests within the code base. Their creation are according to our rules managed by the product owners.
- **Implementation of User Stories:** 
    - The user stories are (before any real implementation) first written in .feature files in Gherkin syntax with corresponding Cucumber steps.
    - Core classes are then implemented to make tests pass.
    - Continuous refactoring and unit testing are then performed to ensure code quality.
- **GUI Testing:** Manual integration tests are used for GUI functionality.

### Version Control
- The project is tracked using Git. All team members are supposed to contribute equally, and the repository is hosted on [GitLab](https://gitlab.gbar.dtu.dk/02160-f25/group-14-re).

---

## 4. Installation and Setup

### Prerequisites
- **Java Development Kit (JDK)** - Version 8 or higher.
- **Git** - For version control.
- **Gradle** - For dependency management and building the project.
- **Cucumber** - For running BDD tests.

### Installation Steps
1. **Clone the Repository:**  
     ```bash
     git clone https://gitlab.gbar.dtu.dk/02160-f25/group-14-re.git
     ```
     ```bash
    cd group-14-re
     ```
2. **Build the Project:**  
     Using Gradle Wrapper:
     ```bash
     gradlew clean build
     ```
3. **Launch the Game:**  
     Execute the main class (e.g., GameLauncher.java) from your IDE or via command line:
     ```bash
     java -jar app/build/libs/laser.jar
     ```
---

## 5. Game Usage

### Controls
To be filled in later.

### Gameplay Overview
To be filled in later.

---

## 6. Project Structure
To be filled in later.

---

## 7. Project Milestones & Key Dates

- **Group Contract Deadline:** Jun 6, 2025, 12:00
- **Sprint 1** Takes place from Jun 9, 12:00 until Jun 12, 12:00
- **Sprint 2** Takes place from Jun 13, 12:00 until Jun 17, 12:00
- **Sprint 3** Takes place from Jun 18, 12:00 until Jun 23, 12:00

- **Final Submission:** Jun 24, 2025, 13:00
- **Final Presentation:** Jun 25, 2025, 9:00

---

## 8. Contributing

All team members are encouraged to contribute to the project. Please follow these guidelines:
- **Commit Messages:** Write clear and concise commit messages with reference to Jira tasks!
- **Code Reviews:** Ensure your code meets our project standards before merging. Ie., developed with BDD and SOLID principles. All code should be reviewed by AT LEAST one other team member.
- **Workload Tracking:** Document each member’s contributions on Jira and in the project report.

---

## 9. Additional Resources

- [Laser Maze by ThinkFun](https://www.thinkfun.com/en-US/products/single-player-logic-games/laser-maze-44001014)
- [Laser Maze GUI Example (GitHub)](https://github.com/terzicaglar/LaserArena)
- Cucumber Testing Documentation
- Java Swing Tutorials

---

## 10. Acknowledgements

Special thanks to the course professor for giving us another chance and to the hardest working team members for their dedication to this project. Responsibility is to be taken. Not just given.

This README file is a living document and will be updated as new features are implemented and the project evolves.
