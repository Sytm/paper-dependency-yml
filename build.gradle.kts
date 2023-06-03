plugins {
  alias(libs.plugins.spotless)
  `java-library`
  `maven-publish`
}

repositories {
  mavenCentral()

  maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
  api(libs.paper)
}

java {
  toolchain.languageVersion.set(JavaLanguageVersion.of(libs.versions.jvmToolchain.get().toInt()))
}

spotless {
  java {
    palantirJavaFormat().style("GOOGLE")
    formatAnnotations()
  }
}

publishing {
  repositories {
    maven {
      name = "md5lukasReposilite"

      url =
          uri(
              "https://repo.md5lukas.de/${
            if (version.toString().endsWith("-SNAPSHOT")) {
              "snapshots"
            } else {
              "releases"
            }
          }")

      credentials(PasswordCredentials::class)
      authentication { create<BasicAuthentication>("basic") }
    }
  }
  publications { create<MavenPublication>("maven") { from(components["java"]) } }
}
