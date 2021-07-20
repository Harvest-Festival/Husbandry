<img src="https://harvestfestivalwiki.com/images/e/ea/Husbandry_Logo.svg" width="50%">

[![Discord](https://img.shields.io/discord/227497118498029569?style=plastic&colorB=7289DA&logo=discord&logoColor=white)](http://discord.gg/0vVjLvWg5kyQwnHG) &nbsp; ![GitHub](https://img.shields.io/github/license/Harvest-Festival/Husbandry?color=%23990000&style=plastic) &nbsp; ![Jenkins](https://img.shields.io/jenkins/build?jobUrl=https%3A%2F%2Fjenkins.joshiejack.uk%2Fjob%2FHusbandry%2F&style=plastic) &nbsp; ![Maven metadata URL](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fmaven.joshiejack.uk%2Fuk%2Fjoshiejack%2Fhusbandry%2FHusbandry%2Fmaven-metadata.xml&style=plastic) &nbsp; [![Curseforge](http://cf.way2muchnoise.eu/full_husbandry_downloads.svg)](https://www.curseforge.com/minecraft/mc-mods/husbandry)

Husbandry is the animal care element of Harvest Festival. It adds the requirement to look after your animals to make them happy. It gives animals a limited lifespan and has mammals be pregnant for several minecraft days. Also adds nests for chickens to lay their eggs and an incubator for you to hatch them. Pigs will "find" truffles randomly throughout their day. Treats will help level up animals so that they produce more products for you.

More information about Husbandry and downloads can be found on //TODO

If you have any questions, feel free to join the [Harvest Festival Discord](https://discord.gg/MRZAyze)

Adding Husbandry to your buildscript
---
Add to your build.gradle:
```gradle
repositories {
  maven {
    url 'https://maven.joshiejack.uk/'
  }
}

dependencies {
    compileOnly fg.deobf("uk.joshiejack.penguinlib:Penguin-Lib:${minecraft_version}-${penguinlib_version}")
    compileOnly fg.deobf("uk.joshiejack.husbandry:Husbandry:${minecraft_version}-${husbandry_version}")
}
```

`${$penguinlib_version}` can be found [here](https://maven.joshiejack.uk/uk/joshiejack/penguinlib/Penguin-Lib/)
`${husbandry_version}` can be found [here](https://maven.joshiejack.uk/uk/joshiejack/husbandry/Husbandry/)
