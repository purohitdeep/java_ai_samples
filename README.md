# 🤖 Java AI Samples

A collection of sample Java projects that demonstrate how to integrate AI language models locally using **Spring Boot**, **Spring AI**, **LM Studio**, and **Ollama**.

---

## 📁 Projects in This Repository

### 1. [`java-lmstudio-qna-rag`](./java-lmstudio-qna-rag)

> A question-answering and RAG-style app that connects to LM Studio using its OpenAI-compatible API.

- 🔧 Uses: `spring-ai-openai-spring-boot-starter`
- 📚 Pattern: Ask a question, receive an LLM-generated answer.
- 🎯 Purpose: Experiment with direct LM Studio integration.
- 🗂 Tech: Spring Boot, Java 23, LM Studio
- 🔗 [Project README](./java-lmstudio-qna-rag/README.md)

---

### 2. [`lm-studio_ollama_agents`](./lm-studio_ollama_agents)

> Two AI agents (LM Studio + Ollama) engage in a 20-turn character-driven conversation on any topic.

- 🎭 Features:
  - Distinct AI personalities (custom prompts from `agents.properties`)
  - Alternating dialogue with color-coded console output
  - Configurable models via Spring AI
- 🔧 Uses: `spring-ai-openai-spring-boot-starter` + `spring-ai-ollama-spring-boot-starter`
- 🎯 Purpose: Explore creativity and context persistence in agent simulations.
- 📡 (Optional) SSE/Streaming support coming soon.
- 🔗 [Project README](./lm-studio_ollama_agents/README.md)

---

## 🚀 Getting Started

1. Clone the repo:
   ```bash
   git clone https://github.com/purohitdeep/java_ai_samples.git
   cd java_ai_samples
    ```
	2.	Follow the individual project README files to run them:
	•	LMStudio QnA RAG
	•	LM Studio + Ollama Agents

⸻

🧰 Prerequisites
	- Java 21+
	- LM Studio (https://lmstudio.ai/)
	- Ollama (https://ollama.com/)


