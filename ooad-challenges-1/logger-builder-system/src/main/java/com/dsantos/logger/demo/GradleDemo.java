package com.dsantos.logger.demo;

import com.dsantos.logger.builder.LoggerBuilder;
import com.dsantos.logger.core.Logger;
import com.dsantos.logger.model.LogLevel;

/**
 * Demonstração simples do sistema de logging apenas com Console e File (sem ELK)
 */
public class GradleDemo {

    public static void main(String[] args) {
        System.out.println("=== DEMONSTRAÇÃO COM GRADLE KOTLIN DSL ===\n");

        // 1. Logger básico para console
        System.out.println("1. Logger básico para console:");
        Logger consoleLogger = LoggerBuilder.simple("GradleConsoleDemo");
        consoleLogger.info("Sistema iniciado com Gradle + Kotlin DSL!");
        consoleLogger.warn("Configuração padrão sendo usada");
        consoleLogger.error("Erro simulado para demonstração");
        consoleLogger.close();

        // 2. Logger para arquivo
        System.out.println("\n2. Logger para arquivo:");
        Logger fileLogger = LoggerBuilder.create("GradleFileDemo")
                .level(LogLevel.DEBUG)
                .file("gradle-demo.log", "1MB", 3)
                .build();

        fileLogger.debug("Log de debug salvo no arquivo via Gradle");
        fileLogger.info("Operação realizada com sucesso usando Gradle build");
        fileLogger.flush();
        fileLogger.close();

        // 3. Logger assíncrono
        System.out.println("\n3. Logger assíncrono:");
        Logger asyncLogger = LoggerBuilder.create("GradleAsyncDemo")
                .async()
                .asyncBufferSize(100)
                .console()
                .build();

        for (int i = 1; i <= 5; i++) {
            asyncLogger.info("Processando item %d de 5 com Gradle", i);
        }
        asyncLogger.flush();
        asyncLogger.close();

        // 4. Logger com múltiplos destinos
        System.out.println("\n4. Logger com múltiplos destinos:");
        Logger multiLogger = LoggerBuilder.create("GradleMultiDemo")
                .level(LogLevel.DEBUG)
                .console(LogLevel.WARN)  // Console só para warnings+
                .file("gradle-all.log")   // Arquivo para todos
                .file("gradle-errors.log", LogLevel.ERROR) // Arquivo só para errors
                .build();

        multiLogger.debug("Debug - só vai para gradle-all.log");
        multiLogger.info("Info - só vai para gradle-all.log");
        multiLogger.warn("Warning - vai para console e gradle-all.log");
        multiLogger.error("Error - vai para console, gradle-all.log e gradle-errors.log");
        multiLogger.flush();
        multiLogger.close();

        System.out.println("\n=== GRADLE KOTLIN DSL FUNCIONANDO PERFEITAMENTE! ===");
        System.out.println("Arquivos criados: gradle-demo.log, gradle-all.log, gradle-errors.log");

        // Aguardar processamento assíncrono
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
