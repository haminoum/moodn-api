pluginManagement {
    repositories {
        maven { url = uri("https://repo.spring.io/release") }
        gradlePluginPortal()
    }
}
rootProject.name = "moodn"

plugins {
    id("com.gradle.enterprise") version("3.9")
}

gradleEnterprise {
    if (System.getenv("CI") != null) {
        buildScan {
            publishAlways()
            termsOfServiceUrl = "https://gradle.com/terms-of-service"
            termsOfServiceAgree = "yes"
        }
    }
}
