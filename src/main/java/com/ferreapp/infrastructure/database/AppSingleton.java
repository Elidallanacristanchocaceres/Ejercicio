package com.ferreapp.infrastructure.database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum AppSingleton {
    INSTANCIA;

    private final Properties propiedades = new Properties();

    AppSingleton() {
        cargarConfiguraciones();
    }

    private void cargarConfiguraciones() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("configmysql.properties")) {
            if (input == null) {
                System.err.println("❌ No se pudo encontrar el archivo configmysql.properties");
                return;
            }
            propiedades.load(input);
        } catch (IOException e) {
            System.err.println("❌ Error cargando configuración: " + e.getMessage());
        }
    }

    public String get(String clave) {
        return propiedades.getProperty(clave, "No encontrado");
    }
}