![](src/main/resources/assets/harvestfestival/logo.png)

Husbandry is the fishing element of the Harvest Festival Collection. It adds a bunch of new fish to catch in different biomes as well as entity versions for them all! Also included are the hatchery where you can duplicate your fish and the fish trap which will catch fish for you, although it must be seeded with bait!

More information about Husbandry and downloads can be found on //TODO

If you have any questions, feel free to join the [Harvest Festival Discord](https://discord.gg/MRZAyze)

Adding Husbandry to your buildscript
---
Add to your build.gradle:
```gradle
repositories {
  maven {
    // url of the maven that hosts husbandry files
    url //TODO
  }
}

dependencies {
  // compile against Husbandry
  deobfCompile "uk.joshiejack.husbandry:Husbandry:${minecraft_version}-${husbandry_version}"
}
```

`${minecraft_version}` & `${husbandry_version}` can be found //TODO, check the file name of the version you want.