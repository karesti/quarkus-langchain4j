package io.quarkiverse.langchain4j.sample.chatbot;

import java.util.List;

import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.rag.query.Query;
import jakarta.enterprise.context.ApplicationScoped;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;

@ApplicationScoped
public class ContentRetrieverExample implements ContentRetriever {

    private final EmbeddingStoreContentRetriever contentRetriever;

    ContentRetrieverExample(EmbeddingStore store, EmbeddingModel model) {
        contentRetriever = EmbeddingStoreContentRetriever.builder()
              .embeddingStore(store)
              .embeddingModel(model)
              .maxResults(20)
              .build();
    }

    @Override
    public List<Content> retrieve(Query query) {
        return contentRetriever.retrieve(query);
    }
}
