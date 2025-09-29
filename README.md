# Template Rendering System

## Overview

The Template Rendering System is a flexible and extensible system for rendering data templates into multiple output formats. The project allows converting structured data into different formats such as HTML, CSV, and PDF through an architecture based on design patterns.

## Features

- Template rendering in multiple formats (HTML, CSV, PDF)
- Support for structured data using Map<String, Object>
- Extensible architecture for adding new formats
- Simple and intuitive interface for usage
 
## How It Works

The system works through three main components:

1. **Template**: Record that encapsulates the template name and data to be rendered
2. **TemplateEngine**: Central class that manages different renderers
3. **Renderers**: Specific implementations for each output format

### Execution Flow

1. Creation of a `Template` object with name and data
2. Instantiation of the `TemplateEngine`
3. Call to the `render()` method specifying the desired format
4. The engine selects the appropriate renderer and processes the template
5. Returns the formatted string in the requested format

## Design Patterns Used

### 1. Strategy Pattern

**Location**: `TemplateRenderer` interface and its implementations (`HtmlRenderer`, `CsvRenderer`, `PdfRenderer`)

**Purpose**: Allows switching between different rendering algorithms without modifying client code. Each renderer implements a specific strategy to convert template data into a particular format.

**Benefits**:
- Facilitates adding new rendering formats
- Separates responsibilities for each type of rendering
- Allows runtime behavior changes

### 2. Factory Pattern (implicit)

**Location**: `TemplateEngine` class with the renderers map

**Purpose**: The TemplateEngine acts as a factory that creates/selects the appropriate renderer based on the requested format. Although not a classic factory, it follows the principle of abstracting object creation.

**Benefits**:
- Centralizes renderer selection logic
- Facilitates system maintenance and extension
- Abstracts the complexity of renderer creation

### 3. Template Method Pattern (partial)

**Location**: `TemplateRenderer` interface defines the common contract

**Purpose**: Defines the basic structure for rendering through the `render()` and `getFileExtension()` methods, allowing subclasses to implement specific details.

## Project Structure

```
src/main/java/com/dsantos/
├── Main.java                 # Main class with usage example
├── Template.java             # Record representing a template
├── TemplateEngine.java       # Main rendering engine
└── renderer/
    ├── TemplateRenderer.java # Common interface for renderers
    ├── HtmlRenderer.java     # Implementation for HTML format
    ├── CsvRenderer.java      # Implementation for CSV format
    └── PdfRenderer.java      # Implementation for PDF format
```

## How to Run

1. Clone the repository
2. Execute the command: `./gradlew run`
3. The system will execute a demonstration rendering a customer data template in all available formats

