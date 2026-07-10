plugins {
    id("org.jetbrains.kotlin.jvm") version "2.3.21"
    id("org.jetbrains.kotlin.plugin.allopen") version "2.3.21"
    id("com.google.devtools.ksp") version "2.3.7"
    id("io.micronaut.application") version "5.0.1"
    id("com.gradleup.shadow") version "9.4.1"
    id("io.micronaut.aot") version "5.0.1"
}

version = "0.1"
group = "javazone_demo"

val kotlinVersion = project.properties.get("kotlinVersion")

repositories {
    mavenCentral()
}

dependencies {
    ksp("io.micronaut:micronaut-http-validation")
    ksp("io.micronaut.serde:micronaut-serde-processor")
    ksp("io.micronaut.openapi:micronaut-openapi")
    ksp("io.micronaut.validation:micronaut-validation-processor")
    compileOnly("io.micronaut.openapi:micronaut-openapi-annotations")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut.validation:micronaut-validation")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    compileOnly("io.micronaut:micronaut-http-client")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("tools.jackson.module:jackson-module-kotlin")
    testImplementation("io.micronaut:micronaut-http-client")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}



application {
    mainClass = "javazone_demo.ApplicationKt"
}

java {
    sourceCompatibility = JavaVersion.toVersion("25")
}




graalvmNative.toolchainDetection = false
graalvmNative {
    binaries {
        all {
            buildArgs.add("-H:+SharedArenaSupport")
        }
    }
}




micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("javazone_demo.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }

}

tasks.named<io.micronaut.gradle.docker.MicronautDockerfile>("dockerfile") {

    baseImage = "eclipse-temurin:25-jre"
}







// https://docs.gradle.org/current/userguide/upgrading_major_version_9.html#test_task_fails_when_no_tests_are_discovered
tasks.withType<AbstractTestTask>().configureEach {
    failOnNoDiscoveredTests = false
}

val copyOpenApiSpec by tasks.registering(Copy::class) {
    dependsOn(tasks.named("kspKotlin"))
    from(layout.buildDirectory.file("generated/ksp/main/resources/META-INF/swagger/javazone-catalog-api-1.0.yml"))
    into(layout.buildDirectory.dir("openapi"))
    rename { "openapi.yaml" }
}
tasks.named("build") { dependsOn(copyOpenApiSpec) }

fun registerClientTask(name: String, generator: String, outDir: String, extra: List<String>) =
    tasks.register<Exec>(name) {
        group = "openapi client generation"
        dependsOn(copyOpenApiSpec)
        commandLine(listOf("docker", "run", "--rm",
            "-v", "$rootDir:/local",
            "openapitools/openapi-generator-cli:v7.23.0",
            "generate", "-i", "/local/build/openapi/openapi.yaml",
            "-g", generator, "-o", "/local/$outDir") + extra)
    }

val generatePythonClient = registerClientTask("generatePythonClient", "python",
    "clients/python/generated", listOf("--additional-properties=packageName=catalog_client,packageVersion=0.1.0"))
val generateTypeScriptClient = registerClientTask("generateTypeScriptClient", "typescript-axios",
    "clients/typescript/generated", listOf("--additional-properties=npmName=@javazone-2026/catalog-client,supportsES6=true"))
val generateGoClient = registerClientTask("generateGoClient", "go",
    "clients/go/generated", listOf("--additional-properties=packageName=catalogclient", "--git-user-id=javazone-2026", "--git-repo-id=catalog-client-go"))

tasks.register("generateAllClients") {
    group = "openapi client generation"
    dependsOn(generatePythonClient, generateTypeScriptClient, generateGoClient)
}
