# TheNewItemLibrary
A library used by The New Economy for item-based operations, including calculations, removal, comparisons, and serialization of any items,
including potions, shulker boxes, and other "special" items. This library serializes the items into JSON-valid Strings. This library has also
been rewritten to be platform agnostic, and will include sponge support shortly.

## Maven
TheNewItemLibrary uses maven for dependency management.


Repository:
```
<repository>
    <id>codemc-releases</id>
    <url>https://repo.codemc.io/repository/maven-public/</url>
</repository>
```

Core Dependency:
```
<dependency>
    <groupId>net.tnemc</groupId>
    <artifactId>TNIL-Core</artifactId>
    <version>0.1.5.0</version>
    <scope>compile</scope>
</dependency>
```

Bukkit Dependency:
```
<dependency>
    <groupId>net.tnemc</groupId>
    <artifactId>TNIL-Bukkit</artifactId>
    <version>0.1.5.0</version>
    <scope>compile</scope>
</dependency>
```

Sponge Dependency:

*Coming Soon*
