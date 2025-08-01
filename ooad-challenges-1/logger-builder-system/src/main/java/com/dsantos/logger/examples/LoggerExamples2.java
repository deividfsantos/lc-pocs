//package com.dsantos.logger.examples;
//
//import com.dsantos.logger.builder.LoggerBuilder;
//import com.dsantos.logger.core.Logger;
//import com.dsantos.logger.model.LogLevel;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Exemplos de uso do sistema de logging
// */
//public class LoggerExamples2 {
//
//    public static void main(String[] args) {
//        // Exemplo 1: Logger simples para console
//        simpleConsoleExample();
//
//        // Exemplo 2: Logger para arquivo
//        fileLoggerExample();
//
//        // Exemplo 3: Logger assíncrono
//        asyncLoggerExample();
//
//        // Exemplo 4: Logger com múltiplos destinos
//        multiAppenderExample();
//
//        // Exemplo 5: Logger para Elasticsearch
//        elasticsearchExample();
//
//        // Exemplo 6: Logger completo com todos os destinos
//        fullLoggerExample();
//
//        // Aguardar um pouco para processamento assíncrono
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
//
//    private static void simpleConsoleExample() {
//        System.out.println("=== Exemplo 1: Logger Simples Console ===");
//
//        Logger logger = LoggerBuilder.simple("SimpleLogger");
//
//        logger.info("Aplicação iniciada");
//        logger.debug("Debug message - não aparecerá (nível padrão é INFO)");
//        logger.warn("Aviso importante");
//        logger.error("Erro simulado");
//
//        logger.close();
//    }
//
//    private static void fileLoggerExample() {
//        System.out.println("\n=== Exemplo 2: Logger para Arquivo ===");
//
//        Logger logger = LoggerBuilder.create("FileLogger")
//            .level(LogLevel.DEBUG)
//            .file("logs/application.log", "5MB", 3)
//            .build();
//
//        logger.debug("Debug message salva no arquivo");
//        logger.info("Informação salva no arquivo");
//        logger.error("Erro salvo no arquivo");
//
//        logger.flush();
//        logger.close();
//    }
//
//    private static void asyncLoggerExample() {
//        System.out.println("\n=== Exemplo 3: Logger Assíncrono ===");
//
//        Logger logger = LoggerBuilder.create("AsyncLogger")
//            .async()
//            .asyncBufferSize(500)
//            .asyncFlushInterval(1000)
//            .console()
//            .file("logs/async.log")
//            .build();
//
//        // Simular múltiplas mensagens rapidamente
//        for (int i = 0; i < 10; i++) {
//            logger.info("Mensagem assíncrona número %d", i);
//        }
//
//        logger.flush();
//        logger.close();
//    }
//
//    private static void multiAppenderExample() {
//        System.out.println("\n=== Exemplo 4: Múltiplos Destinos ===");
//
//        Logger logger = LoggerBuilder.create("MultiLogger")
//            .level(LogLevel.DEBUG)
//            .console(LogLevel.WARN)  // Console apenas para warnings e errors
//            .file("logs/all.log")    // Arquivo para todos os logs
//            .file("logs/errors.log", LogLevel.ERROR) // Arquivo só para errors
//            .build();
//
//        logger.debug("Debug - só no arquivo all.log");
//        logger.info("Info - só no arquivo all.log");
//        logger.warn("Warning - console e arquivo all.log");
//        logger.error("Error - console, all.log e errors.log");
//
//        logger.flush();
//        logger.close();
//    }
//
//    private static void elasticsearchExample() {
//        System.out.println("\n=== Exemplo 5: Elasticsearch ===");
//
//        Logger logger = LoggerBuilder.create("ElkLogger")
//            .elasticsearch("localhost", 9200, "app-logs-2025")
//            .console() // Também no console para ver o que está sendo enviado
//            .build();
//
//        // Log com metadata
//        Map<String, Object> metadata = new HashMap<>();
//        metadata.put("userId", "12345");
//        metadata.put("action", "login");
//        metadata.put("ip", "192.168.1.100");
//
//        logger.info("Usuário fez login", metadata);
//        logger.error("Falha na autenticação", metadata);
//
//        // Log com exceção
//        try {
//            throw new RuntimeException("Erro simulado");
//        } catch (Exception e) {
//            logger.error("Exceção capturada", e);
//        }
//
//        logger.flush();
//        logger.close();
//    }
//
//    private static void fullLoggerExample() {
//        System.out.println("\n=== Exemplo 6: Logger Completo ===");
//
//        Logger logger = LoggerBuilder.create("FullLogger")
//            .level(LogLevel.DEBUG)
//            .async()
//            .pattern("[%timestamp] [%level] %logger - %message")
//            .property("app.name", "LoggerDemo")
//            .property("app.version", "1.0.0")
//            .console(LogLevel.WARN)
//            .file("logs/full.log", "10MB", 5)
//            .file("logs/errors-only.log", LogLevel.ERROR)
//            .elasticsearch("localhost", 9200, "full-logs", LogLevel.INFO, 10)
//            .build();
//
//        logger.debug("Aplicação inicializando...");
//        logger.info("Configuração carregada");
//        logger.warn("Configuração de cache não encontrada, usando padrão");
//        logger.error("Falha ao conectar com banco de dados");
//
//        // Log estruturado
//        Map<String, Object> context = new HashMap<>();
//        context.put("operation", "user_registration");
//        context.put("duration_ms", 150);
//        context.put("success", true);
//
//        logger.info("Operação concluída", context);
//
//        logger.flush();
//        logger.close();
//    }
//}
