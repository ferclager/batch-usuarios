# chore/04-csv - Lectura de Archivos CSV con FlatFileItemReader

## Descripción
Esta branch corresponde al quinto video de la playlist **Spring Batch desde 0: Procesamiento por Lotes con Spring Boot 3.5**.

En este capítulo se explica:
- FlatFileItemReaderBuilder para lectura de archivos CSV
- Mapeo de campos del CSV a objetos Java con RecordFieldSetMapper
- Configuración de delimitadores y nombres de campos
- Saltar líneas de encabezado en archivos CSV
- Integración de archivos CSV con procesamiento por Chunks

## Recursos
- **Video**: [Lectura de Archivos CSV con FlatFileItemReader](https://youtu.be/f3c9PuQ6b_I?list=PLETWWcGiZcBs)
- **Playlist completa**: [Spring Batch desde 0](https://www.youtube.com/playlist?list=PLETWWcGiZcBs)
- **Branch principal**: [main](https://github.com/ferclager/batch-usuarios/tree/main)
- **Branch actual**: [chore/04-csv](https://github.com/ferclager/batch-usuarios/tree/chore/04-csv)

## Stack Tecnológico
- Spring Boot 3.5
- Spring Batch 5
- Java 21
- Gradle
- H2 (Base de datos en memoria)

## Implementación
Esta branch contiene:
- Configuración de FlatFileItemReaderBuilder para leer archivos CSV
- Archivo de datos de ejemplo (usuarios.csv) en src/main/resources/data/
- Mapeo automático de campos CSV a record Usuario con RecordFieldSetMapper
- Configuración de delimitadores y saltado de línea de encabezado
- Procesamiento por Chunks con datos leídos desde CSV

## Capítulo Anterior
- [Procesamiento con Chunks en Spring Batch](https://github.com/ferclager/batch-usuarios/tree/chore/03-chunks) - [chore/03-chunks](https://github.com/ferclager/batch-usuarios/tree/chore/03-chunks)