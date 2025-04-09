
# Java LMStudio QnA RAG

This project demonstrates how to use LM Studio, a local language model, to perform Question and Answering (QnA) and Retrieval-Augmented Generation (RAG) tasks. It is built using Spring Boot and integrates with LM Studio via the `spring-ai` library.

---

## Table of Contents
- [Overview](#overview)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [Dependencies](#dependencies)
- [How to Start LM Studio](#how-to-start-lm-studio)
- [Running the Application](#running-the-application)
- [Endpoints](#endpoints)

---

## Overview

LM Studio is a local language model that allows you to run AI tasks without relying on external APIs. This project integrates LM Studio with Spring Boot using the `spring-ai` library to enable QnA and RAG functionalities.

The application includes:
- A REST API to interact with the language model.
- Configuration for connecting to LM Studio.
- Example usage of chat, speech, and image models.

---

## Project Structure

- **`JavaLmstudioQnaRagApplication.java`**: The main entry point of the Spring Boot application.
- **`AiController.java`**: A REST controller that exposes an endpoint to interact with the language model.
- **`LmStudioConfig.java`**: Configuration for connecting to LM Studio using the `spring-ai` library.
- **`application.properties`**: Contains application-specific configurations such as the LM Studio base URL and API key.

---

## Configuration

The `LmStudioConfig.java` file is responsible for setting up the connection to LM Studio. It uses the `spring-ai` library to configure the `ChatClient` with the following details:
- **Base URL**: `http://localhost:1234` (default LM Studio endpoint).
- **API Key**: A placeholder key (`no-key-needed`) since LM Studio does not require authentication.
- **HTTP Client**: Configured with a timeout of 30 seconds.

This configuration ensures seamless communication between the Spring Boot application and LM Studio.

---

## Dependencies

The `pom.xml` file includes the following key dependencies:

1. **Spring Boot Starter Web**:
   - Provides the necessary libraries to build a REST API.
   - Artifact: `spring-boot-starter-web`.

2. **Spring AI OpenAI Starter**:
   - Integrates the `spring-ai` library for AI functionalities.
   - Artifact: `spring-ai-openai-spring-boot-starter`.

3. **Spring AI Tika Document Reader**:
   - Enables document reading capabilities for RAG tasks.
   - Artifact: `spring-ai-tika-document-reader`.

4. **Spring Boot Starter Test**:
   - Provides testing utilities for the application.
   - Artifact: `spring-boot-starter-test`.

The `spring-ai-bom` is used for dependency management, ensuring compatibility between the `spring-ai` modules.

---

## How to Start LM Studio

1. **Download LM Studio**:
   - Visit the [LM Studio GitHub Repository](https://github.com/your-lmstudio-repo) to download the latest version.

2. **Run LM Studio**:
   - Extract the downloaded files and navigate to the LM Studio directory.
   - Start LM Studio by running the following command:
     ```bash
     ./lmstudio --model gemma-3-1b-instruct
     ```
   - This will start LM Studio on `http://localhost:1234`.

3. **Verify LM Studio**:
   - Open a browser and navigate to `http://localhost:1234` to ensure LM Studio is running.

---

## Running the Application

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-repo/java-lmstudio-qna-rag.git
   cd java-lmstudio-qna-rag
   ```

2. **Build the Application**:
   - Ensure you have Java 21 and Maven installed.
   - Run the following command to build the project:
     ```bash
     mvn clean install
     ```

3. **Run the Application**:
   - Start the Spring Boot application:
     ```bash
     mvn spring-boot:run
     ```

4. **Verify the Application**:
   - The application will start on `http://localhost:8080`.

---

## Endpoints

### `GET /`
- **Description**: Sends a message to the language model and retrieves a response.
- **Parameters**:
  - `message` (optional): The input message to send to the model. Defaults to `"What is the meaning of life?"`.
- **Example**:
  ```bash
  curl "http://localhost:8080/?message=Hello"
  ```

---

## Notes

- Ensure LM Studio is running before starting the Spring Boot application.
- The default model used is `gemma-3-1b-instruct`. You can change this in the `application.properties` file.

---
