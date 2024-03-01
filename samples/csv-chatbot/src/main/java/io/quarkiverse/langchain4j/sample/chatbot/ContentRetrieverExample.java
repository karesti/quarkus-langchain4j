package io.quarkiverse.langchain4j.sample.chatbot;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.rag.query.Query;
import dev.langchain4j.store.embedding.EmbeddingStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class ContentRetrieverExample implements ContentRetriever {

    private final EmbeddingStoreContentRetriever contentRetriever;

    @Inject
    ContentRetrieverExample(EmbeddingStore store, EmbeddingModel model) {
        contentRetriever = EmbeddingStoreContentRetriever.builder()
              .embeddingStore(store)
              .embeddingModel(model)
              .maxResults(10)
              .build();
    }

    @Override
    public List<Content> retrieve(Query query) {
        return contentRetriever.retrieve(query);
    }
}
