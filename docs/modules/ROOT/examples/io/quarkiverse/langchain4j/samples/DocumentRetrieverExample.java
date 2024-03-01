package io.quarkiverse.langchain4j.samples;

import java.util.List;

import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.rag.query.Query;
import jakarta.enterprise.context.ApplicationScoped;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.retriever.EmbeddingStoreRetriever;
import dev.langchain4j.retriever.Retriever;
import io.quarkiverse.langchain4j.redis.RedisEmbeddingStore;
import jakarta.inject.Inject;

@ApplicationScoped
public class DocumentRetrieverExample implements ContentRetriever {

    private final EmbeddingStoreContentRetriever contentRetriever;

    @Inject
    DocumentRetrieverExample(RedisEmbeddingStore store, EmbeddingModel model) {
        contentRetriever = EmbeddingStoreContentRetriever.builder().embeddingStore(store).embeddingModel(model).maxResults(20).build();
    }

    @Override
    public List<Content> retrieve(Query query) {
        return contentRetriever.retrieve(query);
    }
}
