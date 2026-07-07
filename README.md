# chore/03-chunks - Procesamiento con Chunks en Spring Batch

## Descripción
Esta branch corresponde al cuarto video de la playlist **Spring Batch desde 0: Procesamiento por Lotes con Spring Boot 3.5**.

En este capítulo se explica:
- Concepto de Chunks en Spring Batch
- ItemReader, ItemProcessor y ItemWriter
- Procesamiento por lotes con Chunks
- Configuración de chunk size y manejo de transacciones
- Ventajas del procesamiento con Chunks vs Tasklets

## Recursos
- **Playlist completa**: [Spring Batch desde 0](https://www.youtube.com/playlist?list=PLETWWcGiZcBs)
- **Branch principal**: [main](https://github.com/ferclager/batch-usuarios/tree/main)
- **Branch actual**: [chore/03-chunks](https://github.com/ferclager/batch-usuarios/tree/chore/03-chunks)

## Stack Tecnológico
- Spring Boot 3.5
- Spring Batch 5
- Java 21
- Gradle
- H2 (Base de datos en memoria)

## Implementación
Esta branch contiene:
- Configuración de Job con Chunk-oriented processing
- Implementación de ItemReader para lectura de datos
- Implementación de ItemProcessor para transformación de datos
- Implementación de ItemWriter para escritura de datos
- Configuración de chunk size y transacciones

## Capítulo Anterior
- [Tu primer Job de Spring Batch: Tasklet y metadata](https://youtu.be/bh48zlM7alk) - [chore/02-tasklet](https://github.com/ferclager/batch-usuarios/tree/chore/02-tasklet)