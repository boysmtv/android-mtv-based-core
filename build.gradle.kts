// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.signing)
    alias(libs.plugins.android.library) apply false
}

val signingKeyFile: String? by project
val signingPassword: String? by project

subprojects {

    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlin") {
                useVersion("1.9.24")
                because("Force Kotlin 1.9.24 to avoid metadata 2.x crash with KAPT")
            }
        }
    }

    afterEvaluate {
        if (plugins.hasPlugin("com.android.library") || plugins.hasPlugin("java-library")) {

            publishing {
                publications {
                    create<MavenPublication>("release") {
                        afterEvaluate {
                            from(components["release"])
                        }
                        groupId = "com.mtv.based.core"
                        artifactId = project.name
                        version = "1.0.0"
                    }
                }

                // Disable for Jitpack
                repositories {
                    mavenLocal()
                }
            }

            if (!signingKeyFile.isNullOrBlank() && !signingPassword.isNullOrBlank()) {
                signing {
                    // Disable for Jitpack
                    setRequired { false }
                    useInMemoryPgpKeys(
                        file(signingKeyFile!!).readText(),
                        signingPassword!!
                    )
                    sign(publishing.publications)
                }
            }
        }
    }
}

tasks.register("publishAllModulesToMavenLocal") {
    group = "publishing"
    description = "Publish all modules to Maven Local"

    subprojects.forEach { sub ->
        val publishTask = sub.tasks.findByName("publishToMavenLocal")
        if (publishTask != null) {
            dependsOn(publishTask)
        }
    }
}