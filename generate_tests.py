from langchain_community.embeddings import HuggingFaceEmbeddings
from langchain_community.vectorstores import Chroma
from langchain_openai import ChatOpenAI
from langchain.prompts import PromptTemplate
from langchain.chains.combine_documents import create_stuff_documents_chain

# === Shared setup ===
embedding_model = HuggingFaceEmbeddings(model_name="sentence-transformers/all-MiniLM-L6-v2")
vector_store = Chroma(persist_directory="chroma_db", embedding_function=embedding_model)
retriever = vector_store.as_retriever(search_kwargs={"k": 6})
llm = ChatOpenAI()

# === Test prompt template ===
test_prompt_template = """
You are a senior test automation engineer. Use the following context to write test cases.

Preferred Framework: {framework}

Context:
{context}

{prior_tests_block}

User Request:
{question}

Only return code in proper {framework} format:
- For Python: use {framework}.
- For Java: use JUnit 5 with assert statements.
- For config/infrastructure files: describe what to test logically, no code.

Ensure your code is clean, concise, and follows best practices.
"""

# === Detect framework preference ===
def detect_framework(question: str) -> str:
    q = question.lower()
    if "unittest" in q or "unit test" in q:
        return "unittest"
    elif "pytest" in q:
        return "pytest"
    elif "java" in q or "junit" in q:
        return "junit"
    return "pytest"  # fallback

# === Extract prior test cases ===
def extract_prior_tests(documents):
    test_case_texts = []
    for doc in documents:
        if "test" in doc.metadata.get("source", "").lower():
            content = doc.page_content.strip()
            if "def test_" in content or "@pytest" in content:
                test_case_texts.append(content[:800])
    return test_case_texts

# === Main callable ===
def generate_test_cases(question: str):
    framework = detect_framework(question)
    relevant_docs = retriever.invoke(question)
    prior_tests = extract_prior_tests(relevant_docs)

    prior_tests_block = ""
    if prior_tests:
        joined_tests = "\n\n".join(prior_tests)
        prior_tests_block = f"The following test cases have already been written. Include these patterns or extend them:\n\n{joined_tests}"

    prompt = PromptTemplate.from_template(test_prompt_template)
    test_chain = create_stuff_documents_chain(llm=llm, prompt=prompt)

    result = test_chain.invoke({
    "context": relevant_docs,  # <-- this fixes the KeyError
    "framework": framework,
    "question": question,
    "prior_tests_block": prior_tests_block
})


    return {
        "result": result,
        "sources": sorted({doc.metadata.get("source") for doc in relevant_docs}),
        "chunks": relevant_docs[:3],
        "framework": framework,
        "prior_tests": prior_tests_block or None
    }
