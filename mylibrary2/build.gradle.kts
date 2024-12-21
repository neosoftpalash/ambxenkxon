plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("maven-publish")
    id("signing")
}

android {
    namespace = "com.example.mylibrary2"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}




dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

// Publishing to Maven Central setup
publishing {
    publications {
        // Define the release publication
        register<MavenPublication>("release") {
            // from(components["release"]) // Use the release build variant

            groupId = "com.example" // Replace with your groupId
            artifactId = "mylibrary2" // Replace with your artifactId
            version = "1.0.0" // Replace with your library version


            afterEvaluate {
                from(components["release"])
            }

            pom {
                name.set("My Library")
                description.set("A simple Android library")
                url.set("https://github.com/Neosoft123422/mylibrary.git") // Your project URL

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/Neosoft123422/mylibrary.git")
                    developerConnection.set("scm:git:https://github.com/Neosoft123422/mylibrary.git")
                    url.set("https://github.com/Neosoft123422/mylibrary")
                }

                developers {
                    developer {
                        id.set("Neosoft123422")
                        name.set("Palash")
                        email.set("palash.gour@neosoft.com")
                    }
                }
            }

        }
    }


    // Repositories for publishing
    repositories {
        maven {
            name = "MavenCentral"
            url =
                uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/") // Sonatype's Maven Central repository URL

            credentials {
                username =findProperty("sonatypeUsername")?.toString() ?: System.getenv("OSSRH_USERNAME")
                password =findProperty("sonatypePassword")?.toString() ?: System.getenv("OSSRH_PASSWORD")
            }

        }
    }
    signing {
       useGpgCmd()
//        useInMemoryPgpKeys(
//            findProperty("signing.keyId") as String?,
//            findProperty("signing.secretKeyRingFile") as String?,
//            findProperty("signing.password") as String?
//        )
        sign(publishing.publications["release"])
    }
}