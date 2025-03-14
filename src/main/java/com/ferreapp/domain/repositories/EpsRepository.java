package com.ferreapp.domain.repositories;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ferreapp.domain.entities.Eps;


public interface EpsRepository {
    void save(Eps eps);
    Eps findById(int id);
    List<Eps> findAll();
    void update(Eps eps);
    void delete(int id);
    Map<Integer, Eps> findAllAsMap();
    
    /**
     * Busca EPS que contengan el término de búsqueda en su nombre
     */
    List<Eps> findByNameContaining(String searchTerm);
    
    /**
     * Encuentra la primera EPS que coincida exactamente con el nombre
     */
    Optional<Eps> findFirstByName(String name);
    
    /**
     * Busca múltiples EPS por sus IDs
     */
    Map<Integer, Eps> findByIds(List<Integer> ids);
    
    /**
     * Encuentra todas las EPS cuyo nombre comience con el prefijo dado
     */
    List<Eps> findByNameStartingWith(String prefix);
}
