package com.dsantos.logger.demo;

import com.dsantos.logger.builder.LoggerBuilder;
import com.dsantos.logger.core.Logger;
import com.dsantos.logger.model.LogLevel;

import java.util.HashMap;
import java.util.Map;

/**
 * Demonstração simples do sistema de logging
 */
public class SimpleDemo {

    public static void main(String[] args) {
        System.out.println("=== DEMONSTRAÇÃO DO SISTEMA DE LOGGER BUILDER ROUTER ===\n");

        // 1. Logger básico para console
        System.out.println("1. Logger básico para console:");
        Logger consoleLogger = LoggerBuilder.simple("ConsoleDemo");
        consoleLogger.info("Sistema iniciado com sucesso!");
        consoleLogger.warn("Atenção: Configuração padrão sendo usada");
        consoleLogger.error("Erro simulado para demonstração");
        consoleLogger.close();

        // 2. Logger para arquivo com rotação
        System.out.println("\n2. Logger para arquivo:");
        Logger fileLogger = LoggerBuilder.create("FileDemo")
            .level(LogLevel.DEBUG)
            .file("demo.log", "1MB", 3)
            .build();
        
        fileLogger.debug("Log de debug salvo no arquivo");
        fileLogger.info("Operação realizada com sucesso");
        fileLogger.flush();
        fileLogger.close();

        // 3. Logger assíncrono para alta performance
        System.out.println("\n3. Logger assíncrono:");
        Logger asyncLogger = LoggerBuilder.create("AsyncDemo")
            .async()
            .asyncBufferSize(100)
            .console()
            .build();

        for (int i = 1; i <= 5; i++) {
            asyncLogger.info("Processando item %d de 5", i);
        }
        asyncLogger.flush();
        asyncLogger.close();

        // 4. Logger com múltiplos destinos e filtros
        System.out.println("\n4. Logger com múltiplos destinos:");
        Logger multiLogger = LoggerBuilder.create("MultiDemo")
            .level(LogLevel.DEBUG)
            .console(LogLevel.WARN)  // Console só para warnings+
            .file("multi-all.log")   // Arquivo para todos
            .file("multi-errors.log", LogLevel.ERROR) // Arquivo só para errors
            .build();

        multiLogger.debug("Debug - só vai para multi-all.log");
        multiLogger.info("Info - só vai para multi-all.log");
        multiLogger.warn("Warning - vai para console e multi-all.log");
        multiLogger.error("Error - vai para console, multi-all.log e multi-errors.log");
        multiLogger.flush();
        multiLogger.close();

        // 5. Logger com metadata estruturada
        System.out.println("\n5. Logger com metadata:");
        Logger metadataLogger = LoggerBuilder.create("MetadataDemo")
            .pattern("[%timestamp] [%level] %logger - %message")
            .console()
            .build();

        Map<String, Object> userContext = new HashMap<>();
        userContext.put("userId", "12345");
        userContext.put("sessionId", "abc-def-ghi");
        userContext.put("action", "user_login");

        metadataLogger.info("Usuário fez login", userContext);
        
        // Exemplo com exceção
        try {
            throw new RuntimeException("Erro de exemplo");
        } catch (Exception e) {
            metadataLogger.error("Falha na operação", e);
        }
        
        metadataLogger.close();

        System.out.println("\n=== DEMONSTRAÇÃO CONCLUÍDA ===");
        System.out.println("Arquivos de log criados: demo.log, multi-all.log, multi-errors.log");
        
        // Aguardar processamento assíncrono
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
