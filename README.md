# Library Management System

A desktop application for managing a library's catalog, users, loans, and statistics. Built in **Java** with a Swing-based graphical user interface, this project is fully integrated and ready to run in **BlueJ**.

## 📋 Table of Contents
- [Features](#-features)
- [Project Structure](#-project-structure)
- [Prerequisites](#-prerequisites)
- [How to Run](#-how-to-run)
- [Testing](#-testing)

---

## ✨ Features

- **Catalog Management:** Add and organize physical books and digital books (`LibroDigital`).
- **User & Employee Roles:** Administer members (`Usuario`) and staff (`Empleado`) details.
- **Loan System:** Interactive dialogue to issue, return, and track book loans (`Prestamo`), automatically calculating overdue fines (`DialogoMulta`).
- **Data Visualization & Charts:** Interactive graphs displaying library statistics via monthly line charts and pie charts.
- **JUnit Tests:** Full suite of test cases ensuring robustness for key models (Loans, Users, Books).

---

## 📂 Project Structure

The project consists of the following core modules:
* `Main.java`: The main entry point of the desktop application.
* `BibliotecaGUI.java`: Coordinates the Swing window panels and main dashboard.
* **Dialogs:** Dialog windows (`DialogoNuevoUsuario`, `DialogoNuevoLibro`, `DialogoNuevoPrestamo`, etc.) for clean data input.
* **Models:** `Libro`, `LibroDigital`, `Usuario`, `Empleado`, `Prestamo` representing domain entities.
* **Charts:** custom GUI classes (`GraficoPastel`, `GraficoLineal`, `GraficoEstadisticas`) to draw charts dynamically.

---

## ⚙️ Prerequisites

- **Java Development Kit (JDK):** Version 8 or higher.
- **BlueJ (Optional):** Recommended IDE for visualization and execution.

---

## 🚀 How to Run

### Using BlueJ:
1. Open BlueJ.
2. Select **Project** -> **Open Project...** and choose the `Lab9` directory.
3. Right-click on the `Main` class and select `void main(String[] args)`.

### Using Command Line:
Navigate to the `Lab9` source folder and compile/run via terminal:
```bash
javac *.java
java Main
```

---

## 🧪 Testing

This project includes unit tests for the core logic. To run the tests in BlueJ:
* Right-click any test class (e.g., `LibroTest`, `PrestamoTest`, `UsuarioTest`) and select **Test All** or click **Run Tests** on the side panel.
