import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.scala.ScalaCompile

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    kotlin("jvm") version "1.2.31"
}

repositories {
    mavenCentral()
}

dependencies {
    compile("io.vavr:vavr:0.9.0")
    compile("com.google.guava:guava:19.0")
    compile(kotlin("stdlib"))

    testCompile("org.hamcrest:hamcrest-all:1.3")
    testCompile("junit:junit:4.12")
}


val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}