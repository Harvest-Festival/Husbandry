![](src/main/resources/assets/harvestfestival/logo.png)

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
