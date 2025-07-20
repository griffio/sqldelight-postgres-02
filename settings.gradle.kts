pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "sqldelight-postgres-02"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            val vSqlDelight = "2.1.0"
            plugin("kotlin", "org.jetbrains.kotlin.jvm").version("2.0.20")
            plugin("sqldelight", "app.cash.sqldelight").version(vSqlDelight)
            plugin("liquibase", "org.liquibase.gradle").version("2.2.0")
            library("sqldelight-jdbc-driver", "app.cash.sqldelight:jdbc-driver:$vSqlDelight")
            library("sqldelight-postgresql-dialect", "app.cash.sqldelight:postgresql-dialect:$vSqlDelight")
            library("postgresql-jdbc-driver", "org.postgresql:postgresql:42.5.4")
            library("liquibase-core", "org.liquibase:liquibase-core:4.24.0")
            library("info-picocli", "info.picocli:picocli:4.7.5")
            library("snakeyaml", "org.yaml:snakeyaml:1.33")
            library("logback-core", "ch.qos.logback:logback-core:1.4.14")
            library("logback-classic", "ch.qos.logback:logback-classic:1.4.14")
        }
    }
}
