## Overview

This project demonstrates how to integrate [LM Studio](https://lmstudio.ai/) with a Spring Boot application using [Spring AI](https://docs.spring.io/spring-ai/). It uses LM Studio as a local LLM server via its OpenAI-compatible API on `http://localhost:1234`, and calls it using Spring AI's OpenAI starter with custom HTTP/1.1 configuration to avoid HTTP/2 issues.

## How it works

- LM Studio runs locally and serves OpenAI-compatible APIs (chat, completion, etc.).
- Spring AI is configured to connect to LM Studio using `OpenAiApi` with `baseUrl=http://localhost:1234`.
- The app uses a custom `@Configuration` class to enforce HTTP/1.1 via Java's `HttpClient`.
- Chat requests are handled via a simple REST controller that uses `ChatClient`.

## Setup
- Open LM Studio and start a model
- Ensure the server is listening on port 1234.

## Run the Server

Run the app and access:
```bash
./mvnw spring-boot:run
```

## Example request

Run the app and access:
```bash
http GET localhost:8080 message=="Tell me a joke"