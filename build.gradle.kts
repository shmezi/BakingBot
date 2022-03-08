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
    mainClass.set("me.alexirving.bot.BakersBot")
}

dependencies {
    implementation("ch.qos.logback:logback-classic:1.2.10")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.0")
    implementation("net.dv8tion:JDA:5.0.0-alpha.9")
    implementation("com.google.zxing:core:3.4.1")
    implementation("com.google.zxing:javase:3.4.1")
    implementation("org.xerial:sqlite-jdbc:3.36.0.3")
    implementation("com.zaxxer:HikariCP:5.0.1")


}