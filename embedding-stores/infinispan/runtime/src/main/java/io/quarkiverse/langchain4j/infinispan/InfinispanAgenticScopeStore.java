package io.quarkiverse.langchain4j.infinispan;

import java.util.Optional;
import java.util.Set;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;

import dev.langchain4j.agentic.scope.AgenticScopeKey;
import dev.langchain4j.agentic.scope.AgenticScopePersister;
import dev.langchain4j.agentic.scope.AgenticScopeSerializer;
import dev.langchain4j.agentic.scope.AgenticScopeStore;
import dev.langchain4j.agentic.scope.DefaultAgenticScope;
import io.quarkus.runtime.Startup;

@Singleton
@Startup
public class InfinispanAgenticScopeStore implements AgenticScopeStore {

    private final RemoteCache<AgenticScopeKey, String> scopes;

    @Inject
    public InfinispanAgenticScopeStore(RemoteCacheManager remoteCacheManager) {
        this.scopes = remoteCacheManager.getCache("agentic-scope-store");
    }

    @PostConstruct
    void init() {
        AgenticScopePersister.setStore(this);
    }

    @Override
    public boolean save(AgenticScopeKey key, DefaultAgenticScope agenticScope) {
        scopes.put(key, AgenticScopeSerializer.toJson(agenticScope));
        return true;
    }

    @Override
    public Optional<DefaultAgenticScope> load(AgenticScopeKey key) {
        return Optional.ofNullable(scopes.get(key))
                .map(s -> AgenticScopeSerializer.fromJson(s));
    }

    @Override
    public boolean delete(AgenticScopeKey key) {
        return scopes.remove(key) != null;
    }

    @Override
    public Set<AgenticScopeKey> getAllKeys() {
        return scopes.keySet();
    }
}
