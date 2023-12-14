plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.liquibase)
    application
}

group = "griffio"
version = "1.0-SNAPSHOT"

repositories {
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
    mavenCentral()
    google()
}

dependencies {
    implementation(libs.sqldelight.jdbc.driver)
    api(libs.sqldelight.postgresql.dialect)
    implementation(libs.postgresql.jdbc.driver)
    testImplementation(kotlin("test"))

    // required by liquibase plugin
    liquibaseRuntime(libs.postgresql.jdbc.driver)
    liquibaseRuntime(libs.liquibase.core)
    liquibaseRuntime(libs.info.picocli)
    liquibaseRuntime(libs.snakeyaml)
    liquibaseRuntime(libs.logback.core)
    liquibaseRuntime(libs.logback.classic)
}

sqldelight {
    databases {
        create("Sample") {
            deriveSchemaFromMigrations.set(true)
            migrationOutputDirectory = file("$buildDir/generated/migrations")
            migrationOutputFileFormat = ".sql" // Defaults to .sql
            packageName.set("griffio.queries")
            dialect(libs.sqldelight.postgresql.dialect)
        }
    }
}

liquibase {
    activities {
        register("changelogs") {
            arguments = mapOf(
                "logLevel" to "info",
                "changelogFile" to "src/main/liquibase/changelog/db.changelog.yaml",
                "url" to "jdbc:postgresql://localhost:5432/postgres",
                "username" to "postgres",
                "password" to "postgres",
                "driver" to "org.postgresql.Driver"
            )
        }
    }
}

tasks {
    ///sqldelight task generateMainSampleMigrations will output your .sqm files as valid SQL
    // in the output directory, with the output format.
    // Create a dependency from compileKotlin where liquibase will have the files available on the classpath
    compileKotlin.configure {
        dependsOn("generateMainSampleMigrations")
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("griffio.MainKt")
}
