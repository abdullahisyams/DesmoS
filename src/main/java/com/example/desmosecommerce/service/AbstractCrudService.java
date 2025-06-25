package com.example.desmosecommerce.service;

import java.util.List;

public abstract class AbstractCrudService<T, ID> {
    
    protected abstract List<T> findAll();
    protected abstract T findById(ID id);
    protected abstract void save(T entity);
    protected abstract void update(T entity);
    protected abstract void delete(ID id);
    
    // Template method for common operations
    public final void processEntity(T entity, String operation) {
        validateEntity(entity);
        switch (operation.toLowerCase()) {
            case "create":
                save(entity);
                break;
            case "update":
                update(entity);
                break;
            case "delete":
                delete(getEntityId(entity));
                break;
            default:
                throw new IllegalArgumentException("Invalid operation: " + operation);
        }
        postProcess(entity, operation);
    }
    
    // Hook methods that can be overridden by subclasses
    protected void validateEntity(T entity) {
        // Default implementation does nothing
    }
    
    protected void postProcess(T entity, String operation) {
        // Default implementation does nothing
    }
    
    protected abstract ID getEntityId(T entity);
} 