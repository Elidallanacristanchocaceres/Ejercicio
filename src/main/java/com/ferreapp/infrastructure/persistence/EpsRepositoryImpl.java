package com.ferreapp.infrastructure.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ferreapp.domain.entities.Eps;
import com.ferreapp.domain.repositories.EpsRepository;
import com.ferreapp.infrastructure.database.ConnectionDb;

public class EpsRepositoryImpl implements EpsRepository {
    private final ConnectionDb connectionDb;

    public EpsRepositoryImpl(ConnectionDb connectionDb) {
        this.connectionDb = connectionDb;
    }

    @Override
    public void save(Eps eps) {
        String sqlInsert = "INSERT INTO eps (name) VALUES (?)";
        try (Connection conn = connectionDb.getConexion();
                PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
            ps.setString(1, eps.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Eps findById(int id) {
        String sqlSelect = "SELECT id,name FROM eps WHERE id = ?";
        try (Connection conn = connectionDb.getConexion();
                PreparedStatement ps = conn.prepareStatement(sqlSelect)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Eps(id, rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Eps> findAll() {
        List<Eps> epsList = new ArrayList<>();
        String sqlSelect = "SELECT id, name FROM eps";
        try (Connection conn = connectionDb.getConexion();
                PreparedStatement ps = conn.prepareStatement(sqlSelect);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                epsList.add(new Eps(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return epsList;
    }

    @Override
    public void update(Eps eps) {
        String sqlUpdate = "UPDATE eps SET name = ? WHERE id = ?";
        try (Connection conn = connectionDb.getConexion();
                PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
            ps.setString(1, eps.getName());
            ps.setInt(2, eps.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sqlDelete = "DELETE FROM eps WHERE id = ?";
        try (Connection conn = connectionDb.getConexion();
                PreparedStatement ps = conn.prepareStatement(sqlDelete)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
public Map<Integer, Eps> findAllAsMap() {
    Map<Integer, Eps> epsMap = new HashMap<>();
    String sqlSelect = "SELECT id, name FROM eps";
    try (Connection conn = connectionDb.getConexion();
         PreparedStatement ps = conn.prepareStatement(sqlSelect);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            epsMap.put(id, new Eps(id, name));
        }
    } catch (SQLException e) {
        System.err.println("❌ Error al recuperar datos de la base de datos: " + e.getMessage());
    }
    return epsMap;
}

    @Override
    public List<Eps> findByNameContaining(String searchTerm) {
        return findAllAsMap().values().stream()
                .filter(eps -> eps.getName().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
public Optional<Eps> findFirstByName(String name) {
    String sqlSelect = "SELECT id, name FROM eps WHERE name = ?";
    try (Connection conn = connectionDb.getConexion();
         PreparedStatement ps = conn.prepareStatement(sqlSelect)) {
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int id = rs.getInt("id");
            String epsName = rs.getString("name");
            return Optional.of(new Eps(id, epsName));
        }
    } catch (SQLException e) {
        System.err.println("❌ Error al buscar EPS por nombre: " + e.getMessage());
    }
    
    return Optional.empty();
}

    @Override
    public Map<Integer, Eps> findByIds(List<Integer> ids) {
        return findAllAsMap().entrySet().stream()
                .filter(entry -> ids.contains(entry.getKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue));
    }

    @Override
    public List<Eps> findByNameStartingWith(String prefix) {
        return findAllAsMap().values().stream()
                .filter(eps -> eps.getName().toLowerCase().startsWith(prefix.toLowerCase()))
                .sorted(Comparator.comparing(Eps::getName))
                .collect(Collectors.toList());
    }
}