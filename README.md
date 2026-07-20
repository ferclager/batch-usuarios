# chore/06-jdbcbiw - JDBC Batch Item Writer

## Descripción
Esta branch corresponde al séptimo video de la playlist **Spring Batch desde 0: Procesamiento por Lotes con Spring Boot 3.5**.

En este capítulo se explica:
- JdbcBatchItemWriter para escritura en base de datos
- Configuración de DataSource y SQL personalizado
- Esquema de base de datos con script SQL
- Integración del Writer en el flujo de Chunks
- Procesamiento completo: Reader → Processor → Writer (BD)

## Notas:
- **`JobExecutionListener`**: `beforeJob` / `afterJob`. Ideal para logs de inicio/fin y resumen.
- **`StepExecutionListener`**: `beforeStep` / `afterStep`. Acceso a contadores del step.
- **`ChunkListener`**: alrededor de cada lote (`beforeChunk`, `afterChunk`, `afterChunkError`).
- **`ItemReadListener` / `ItemProcessListener` / `ItemWriteListener`**: por item (incluido `onXxxError`).

## Recursos
- **Video**: [Escribe en Base de Datos con JdbcBatchItemWriter](https://youtu.be/acNaHaqTu_Y?si=OhBc0KDVX4H-2qrW)
- **Playlist completa**: [Spring Batch desde 0](https://www.youtube.com/playlist?list=PLETWWcGiZcBs)
- **Branch principal**: [main](https://github.com/ferclager/batch-usuarios/tree/main)
- **Branch actual**: [chore/06-jdbcbiw](https://github.com/ferclager/batch-usuarios/tree/chore/06-jdbcbiw)

## Stack Tecnológico
- Spring Boot 3.5
- Spring Batch 5
- Java 21
- Gradle
- H2 (Base de datos en memoria)

## Implementación
Esta branch contiene:
- JdbcBatchItemWriter configurado con DataSource y SQL personalizado
- Script SQL schema-usuario.sql para crear la tabla usuario
- UsuarioProcessor que implementa ItemProcessor<Usuario, Usuario>
- Filtrado de usuarios menores de 18 años (return null)
- Validación de formato de email con lanzamiento de excepciones
- Transformación de datos: nombre a mayúsculas, email a minúsculas
- Integración completa del Writer en el Step con chunk processing

## Capítulo Anterior
- [Procesamiento de Items con ItemProcessor](https://github.com/ferclager/batch-usuarios/tree/chore/05-processor) - [chore/05-processor](https://github.com/ferclager/batch-usuarios/tree/chore/05-processor)