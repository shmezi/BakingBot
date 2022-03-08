plugins {
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    application
}

version = "1.0"

repositories {
    mavenCentral()

}

application{
    mainClass.set("me.alexirving.bot.Bot")
}

dependencies {
    implementation("ch.qos.logback:logback-classic:1.2.8")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.0")
    implementation("net.dv8tion:JDA:5.0.0-alpha.9")

}