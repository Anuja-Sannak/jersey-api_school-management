package com.api.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EMFProvider {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("schoolPU");

    private EMFProvider() {
        // private constructor to prevent instantiation
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void closeFactory() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}
