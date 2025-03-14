package com.ferreapp.application.usecase;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ferreapp.domain.entities.Eps;
import com.ferreapp.domain.repositories.EpsRepository;

public class EpsUseCase {
    private final EpsRepository epsRepository;

    public EpsUseCase(EpsRepository epsRepository) {
        this.epsRepository = epsRepository;
    }

    public void save(String name) {
        Eps eps = new Eps(name);
        epsRepository.save(eps);
    }

    public Eps findById(int id) {
        return epsRepository.findById(id);
    }
    
    public List<Eps> findAll() {
        return epsRepository.findAll();
    }
    
    public void update(int id, String name) {
        Eps eps = new Eps(name);
        eps.setId(id);
        epsRepository.update(eps);
    }

    public Map<Integer, Eps> findAllAsMap() {
        return epsRepository.findAllAsMap();
    }

    public void delete(int id) {
        epsRepository.delete(id);
    }   

    public List<Eps> buscarPorNombreParcial(String termino) {
        return epsRepository.findByNameContaining(termino);
    }
    
    public Optional<Eps> buscarPorNombreExacto(String nombre) {
        return epsRepository.findFirstByName(nombre);
    }
    
    public Map<Integer, Eps> obtenerEpsPorIds(List<Integer> ids) {
        return epsRepository.findByIds(ids);
    }
    
    public List<Eps> buscarPorPrefijo(String prefijo) {
        return epsRepository.findByNameStartingWith(prefijo);
    }
}
