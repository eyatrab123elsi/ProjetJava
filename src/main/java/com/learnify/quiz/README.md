# Plateforme E-Learning - Module Quiz

**Objectif du cette Push Request :** Implémenter la fonctionnalité backend pour la création et le stockage de questions à choix multiples (QCM) dans une base de données MySQL.

---

### **Fonctionnalités implémentées dans cette commit :**

- **Création de questions QCM :**
  - Possibilité de créer des questions avec :
    - Texte de la question
    - Quatre options (A, B, C, D)
    - Index de la réponse correcte (0 pour A, 1 pour B, 2 pour C, 3 pour D)

- **Stockage en base de données :**
  - Les questions sont sauvegardées dans une base de données MySQL (`learning_platform`, table `question`).
  - Création automatique de la base et des tables : Si la base de données `learning_platform` ou les tables `quiz` et `question` n'existent pas, elles seront créées automatiquement lors de la première exécution de l'application.

- **Logique backend :**
  - Implémentation selon une architecture Modele-DAO-Service-Controleur.
  - Utilisation de JDBC pour interagir avec la base de données.

---

### **Technologies utilisées (Push Request 1) :**

- Java (JDK 17 - comme spécifié dans le `pom.xml`)
- MySQL (via XAMPP)
- JDBC (MySQL Connector/J)
- Maven (gestion des dépendances et du projet)

---

### **Prochaines étapes (Push Request 2) :**

- Implémenter la fonctionnalité backend pour la création et la gestion des quiz.
- Commencer le développement de l'interface utilisateur JavaFX pour la création des questions et des quiz.