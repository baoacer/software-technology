package com.example.jwt.services;

import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jwt.entities.ProductEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class SearchIndexService {
    @PersistenceContext
    private EntityManager entityManager;

    @EventListener(ContextRefreshedEvent.class) // run after application startup
    @Transactional
    public void indexExsitingData() throws InterruptedException {
        // Get a Hibernate Search session, called SearchSession,
        // from the EntityManager
        SearchSession searchSession = Search.session(entityManager);

        // create a MassIndexer, passing the entity types we want to index. In this
        // case, ProductEntity
        searchSession
                .massIndexer(ProductEntity.class)
                // set the number of threads to be used
                .threadsToLoadObjects(7)
                .start()
                .thenRun(() -> System.out.println("Indexing finished"))
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return null;
                });

    }
}
