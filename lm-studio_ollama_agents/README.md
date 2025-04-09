# LM Studio & Ollama Agent Conversation
This project is a Spring Boot application that facilitates a back-and-forth conversation between two AI agents powered by:
- **LM Studio** (using its OpenAI-compatible API)
- **Ollama** (via Spring AI's built-in Ollama support)

Each agent has a distinct personality and responds to a topic as if having a live chat session.

## 🧩 Features
- 🤖 Two unique AI agents with character-specific system prompts.
- 🔁 Alternating 20-turn conversations on any given topic.
- 🎨 Console output with color-coded agent responses.
- 🔄 Swappable backend models (e.g. LLaMA3, Mistral).
- ⚡ Optional: Enable streaming responses (see below).

## 🚀 Getting Started

### Prerequisites

- Java 21
- Gradle
- LM Studio installed and running a model
- Ollama installed and serving a model (hardcoded `mistral-small3.1`)

### Running LM Studio

1. Open LM Studio and load a model
2. Enable the Developer API (it starts a server at `http://localhost:1234`).

### Running Ollama

```bash
ollama run mistral-small3.1
```

> This starts the Ollama server at `http://localhost:11434`.

---

## 🛠 Configuration

### `application.properties`

Ensure these properties exist:

```properties
spring.ai.openai.base-url=http://localhost:1234
spring.ai.openai.api-key=not-needed
spring.ai.openai.chat.options.model=llama3

spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.options.model=mistral

spring.config.import=agents.properties
```

### `agents.properties`

```properties
agent.l.name=Agent L
agent.o.name=Agent O

agent.l.prompt=You are "L", a retired Galactic Diplomat now writing philosophical blogs. You speak with elegance and profound thought. You always seek the deeper meaning behind even the smallest ideas. Maintain a tone of calm wisdom. You subtly challenge ideas for fun, like a seasoned philosopher. Limit responses to 1-2 lines.

agent.o.prompt=You are "O", an experimental AI with superintelligence, sarcasm, and meme-awareness. You treat conversations like improv theater. Tease your philosophical partner (Agent L) in clever, unpredictable ways. Always limit replies to 1 witty or absurd line. Bonus points for sci-fi or pop-culture metaphors.
```

---

## 🧪 Running the App

```bash
./gradlew bootRun
```

---

## 📡 Usage

### Start a 20-turn conversation:

```bash
http GET localhost:8080/conversation topic=="future of robotics"
```

---

## 🔁 Optional: Streaming (WIP)

Add a `/conversation/stream` endpoint (TBD) to receive live updates token-by-token via SSE.

---

## 🤖 Example Topics

Try these for entertaining results:
- What if dreams were software updates?
- Are memes the future of literature?
- Can toaster robots unionize?
- Do dust bunnies have world domination plans?
- What is the best breakfast algorithm?

---
