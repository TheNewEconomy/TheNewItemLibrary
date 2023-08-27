# TheNewItemLibrary [![Codacy Badge](https://api.codacy.com/project/badge/Grade/a71ad7ab19c14a7190fdb7a4ffe0e947)](https://app.codacy.com/gh/TheNewEconomy/TheNewItemLibrary?utm_source=github.com&utm_medium=referral&utm_content=TheNewEconomy/TheNewItemLibrary&utm_campaign=Badge_Grade_Settings)

A library used by The New Economy for item-based operations, including calculations, removal, comparisons, and serialization of any items,
including potions, shulker boxes, and other "special" items. This library serializes the items into JSON-valid Strings. This library has also
been rewritten to be platform agnostic, and will include sponge support shortly.

## Maven
TheNewItemLibrary uses maven for dependency management.

Repository:
```XML
<repository>
    <id>codemc-releases</id>
    <url>https://repo.codemc.io/repository/maven-public/</url>
</repository>
```

Core Dependency:
```XML
<dependency>
    <groupId>net.tnemc</groupId>
    <artifactId>TNIL-Core</artifactId>
    <version>0.1.7.4-Pre-17</version>
    <scope>compile</scope>
</dependency>
```

Bukkit Dependency:
```XML
<dependency>
    <groupId>net.tnemc</groupId>
    <artifactId>TNIL-Bukkit</artifactId>
    <version>0.1.7.4-Pre-17</version>
    <scope>compile</scope>
</dependency>
```

Sponge 8 Dependency:
```XML
<dependency>
    <groupId>net.tnemc</groupId>
    <artifactId>TNIL-Sponge</artifactId>
    <version>0.1.7.4-Pre-17</version>
    <scope>compile</scope>
</dependency>
```

Sponge 7 Dependency:

*coming soon*
