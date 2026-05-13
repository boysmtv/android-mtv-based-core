pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        google()

        /* disable for using jitpack*/
        mavenLocal()
//        maven("https://jitpack.io")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()

        /* disable for using jitpack*/
        mavenLocal()
        maven {
            url = uri("https://maven.pkg.github.com/boysmtv/android-mtv-based-uicomponent")
            credentials {
                username = System.getenv("PACKAGES_USER")
                    ?: System.getenv("GITHUB_ACTOR")
                password = System.getenv("PACKAGES_TOKEN")
                    ?: System.getenv("GITHUB_TOKEN")
            }
        }
//        maven("https://jitpack.io")
    }
}

rootProject.name = "App Core"
include(":app")
include(":network")
include(":provider")
