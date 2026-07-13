# chore/05-processor - Procesamiento de Items con ItemProcessor

## Descripción
Esta branch corresponde al sexto video de la playlist **Spring Batch desde 0: Procesamiento por Lotes con Spring Boot 3.5**.

En este capítulo se explica:
- ItemProcessor para transformación y validación de datos
- Filtrado de items retornando null
- Validación de datos y lanzamiento de excepciones
- Transformación de datos (mayúsculas/minúsculas)
- Integración del Processor en el flujo de Chunks

## Recursos
- **Video**: [Transforma y valida datos con ItemProcessor](https://youtu.be/_nIhecLXQic?si=_WwnWKt7EF6Xq5GQ)
- **Playlist completa**: [Spring Batch desde 0](https://www.youtube.com/playlist?list=PLETWWcGiZcBs)
- **Branch principal**: [main](https://github.com/ferclager/batch-usuarios/tree/main)
- **Branch actual**: [chore/05-processor](https://github.com/ferclager/batch-usuarios/tree/chore/05-processor)

## Stack Tecnológico
- Spring Boot 3.5
- Spring Batch 5
- Java 21
- Gradle
- H2 (Base de datos en memoria)

## Implementación
Esta branch contiene:
- UsuarioProcessor que implementa ItemProcessor<Usuario, Usuario>
- Filtrado de usuarios menores de 18 años (return null)
- Validación de formato de email con lanzamiento de excepciones
- Transformación de datos: nombre a mayúsculas, email a minúsculas
- Integración del processor en el Step con chunk processing

## Capítulo Anterior
- [Lectura de Archivos CSV con FlatFileItemReader](https://github.com/ferclager/batch-usuarios/tree/chore/04-csv) - [chore/04-csv](https://github.com/ferclager/batch-usuarios/tree/chore/04-csv)

## Capítulo Siguiente
- [](https://github.com/ferclager/batch-usuarios/tree/chore/06-jdbcbiw) - [chore/06-jdbcbiw](https://github.com/ferclager/batch-usuarios/tree/chore/06-jdbcbiw)

