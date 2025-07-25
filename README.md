# TheNewItemLibrary [![Codacy Badge](https://api.codacy.com/project/badge/Grade/a71ad7ab19c14a7190fdb7a4ffe0e947)](https://app.codacy.com/gh/TheNewEconomy/TheNewItemLibrary?utm_source=github.com&utm_medium=referral&utm_content=TheNewEconomy/TheNewItemLibrary&utm_campaign=Badge_Grade_Settings)[![Javadoc](https://img.shields.io/badge/JavaDoc-Online-green)](https://theneweconomy.github.io/TheNewItemLibrary/javadoc/)

<p align="center">
    <img src="logo.png" width="500"  alt="tnil logo"/>
</p>

A library used by The New Economy for item-based operations, including calculations, removal,
comparisons, and serialization of any items,
including potions, shulker boxes, and other "special" items. This library serializes the items into
JSON-valid Strings. This library has also
been rewritten to be platform-agnostic, and will include sponge support shortly.

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

## Contributing

Contributions to TheNewEconomy are welcome and encouraged! Whether you're fixing a bug, adding a new
feature, or improving documentation, we would love your help.

However, to ensure the project stays consistent and manageable, we ask that you follow
our [contributing guidelines](.contributing/contributing.md) before submitting a pull request.

Please make sure to:

- Sign the Contributor License Agreement (CLA) if this is your first contribution when it appears in
  the Pull Request.
- Follow the coding standards and branch naming conventions outlined in the guidelines.
- Use the required IntelliJ plugin **Final Obsession** for code quality and consistency.

Thank you for your contributions!
