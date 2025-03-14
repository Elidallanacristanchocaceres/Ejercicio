package com.ferreapp.infrastructure.adapter.ui;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.ferreapp.application.usecase.EpsUseCase;
import com.ferreapp.domain.entities.Eps;
import com.ferreapp.domain.repositories.EpsRepository;
import com.ferreapp.infrastructure.database.ConnectMysqlFactory;
import com.ferreapp.infrastructure.persistence.EpsRepositoryImpl;

public class EpsUI {
    EpsRepository repository = new EpsRepositoryImpl(ConnectMysqlFactory.crearConexion());
    EpsUseCase  useCase = new EpsUseCase(repository);

    public void CreateEps() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Ingrese el nombre de la EPS");
            String name = sc.nextLine();
            useCase.save(name);
        }
    }
    public void UpdateEps() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Ingrese el id de la EPS");
            int id = sc.nextInt();
            System.out.println("Ingrese el nombre de la EPS");
            String name = sc.nextLine();
            useCase.update(id, name);
        }
    }
    public void DeleteEps() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Ingrese el id de la EPS");
            int id = sc.nextInt();
            useCase.delete(id);
        }
    }
    public void FindEps() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Ingrese el id de la EPS");
            int id = sc.nextInt();
            Eps eps = useCase.findById(id);
            System.out.println("Id: " + eps.getId() + " Nombre: " + eps.getName());
        }
    }
    public void FindEpsByName() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Ingrese el nombre de la EPS");
            String name = sc.nextLine();
            List<Eps> epsConSalud = useCase.buscarPorNombreParcial(name);
            epsConSalud.forEach(eps -> System.out.println(eps.getName()));
        }
    }
        
    public void FindEpsByNameExact() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Ingrese el nombre de la EPS");
            String name = sc.nextLine();
            Optional<Eps> nuevaEps = useCase.buscarPorNombreExacto(name);
            nuevaEps.ifPresent(eps -> System.out.println("Encontrada: " + eps.getName()));
        }
    }

    public void FindEpsByIds() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Ingrese los IDs de las EPS");
            String ids = sc.nextLine();
            List<Integer> idsList = Arrays.stream(ids.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
            Map<Integer, Eps> epsPorIds = useCase.obtenerEpsPorIds(idsList);
            epsPorIds.forEach((id, eps) -> 
                System.out.println("ID: " + id + ", Nombre: " + eps.getName()));
        }
    }

    public void FindEpsByPrefix() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Ingrese el prefijo de la EPS");
            String prefix = sc.nextLine();
            List<Eps> epsConSan = useCase.buscarPorPrefijo(prefix);
            epsConSan.forEach(eps -> System.out.println(eps.getName()));
        }
    }
    public Map<Integer, Eps> FindAllEps() {
        return  useCase.findAllAsMap();
        //epsMap.forEach((id, eps) -> System.out.println("ID: " + id + ", Nombre: " + eps.getName()));
    }

}
