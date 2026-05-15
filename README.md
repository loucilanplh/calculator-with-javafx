# Calculator with JavaFX

Simple calculator application built with JavaFX and Maven.

## Features
- Basic arithmetic operations: +, -, ×, ÷
- Clear (C) and Clear Entry (CE) functions
- Decimal point support
- Stores calculation history
- Displays history in a separate window

## Prerequisites
- Java 17 or higher
- Maven

## Build and Run

To build and run the application, use the following Maven commands:

### Build
```bash
mvn clean package
```

### Run
```bash
mvn clean javafx:run
```

## Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── calculator/
│   │       ├── CalculatorApp.java
│   │       ├── CalculatorController.java
│   │       └── CalculatorModel.java
│   └── resources/
│       └── calculator.fxml
└── test/
    └── java/
        └── calculator/
            └── CalculatorModelTest.java
```

## Configuration

The application uses a simple text file `calculator_history.txt` to store calculation history. This file is automatically created in the project root directory if it doesn't exist.

## License

[MIT License](LICENSE)
