package io.github.ferclager.batchusuarios.partitioner;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

// ============================================================================
// RANGOPARTITIONER — OPCIÓN B: PARTITIONING POR RANGOS DE IDs
// ============================================================================
// Implementación de Partitioner que divide un rango de IDs en N particiones
// equitativas para procesamiento paralelo.
//
// Ejemplo de uso con 700 registros y gridSize=4:
// - Partición 0: minId=1,   maxId=175
// - Partición 1: minId=176, maxId=350
// - Partición 2: minId=351, maxId=525
// - Partición 3: minId=526, maxId=700
//
// Cada partición recibe su propio ExecutionContext con minId/maxId.
// El reader del worker (debe ser @StepScope) lee estos valores con:
// @Value("#{stepExecutionContext['minId']}") y @Value("#{stepExecutionContext['maxId']}")
// ============================================================================

public class RangoPartitioner implements Partitioner {

    private final long minId;  // ID inicial del rango total
    private final long maxId;  // ID final del rango total

    /**
     * Constructor: define el rango total de IDs a particionar.
     *
     * @param minId ID inicial (inclusive)
     * @param maxId ID final (inclusive)
     */
    public RangoPartitioner(long minId, long maxId) {
        this.minId = minId;
        this.maxId = maxId;
    }

    /**
     * Divide el rango total en N particiones equitativas.
     *
     * @param gridSize número de particiones deseadas (ej: 4 para 4 workers en paralelo)
     * @return Map donde la clave es el nombre de la partición y el valor es su ExecutionContext
     */
    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> particiones = new HashMap<>();

        // Calcular tamaño total del rango
        long total = maxId - minId + 1;

        // Calcular tamaño de cada partición (redondeo hacia arriba para repartir el resto)
        // Fórmula: (total + gridSize - 1) / gridSize
        // Ejemplo: (700 + 4 - 1) / 4 = 175
        long tamano = (total + gridSize - 1) / gridSize;

        // Iterar creando particiones
        long desde = minId;  // ID inicial de la partición actual
        for (int i = 0; i < gridSize && desde <= maxId; i++) {
            // Calcular ID final de esta partición
            // Math.min asegura que no nos pasemos del maxId en la última partición
            long hasta = Math.min(desde + tamano - 1, maxId);

            // Crear ExecutionContext para esta partición
            ExecutionContext ctx = new ExecutionContext();
            ctx.putLong("minId", desde);  // ID inicial de esta partición
            ctx.putLong("maxId", hasta);  // ID final de esta partición

            // Añadir la partición al map con un nombre único
            particiones.put("particion-" + i, ctx);

            // Avanzar al siguiente rango (el siguiente ID después del actual maxId)
            desde = hasta + 1;
        }

        return particiones;
    }
}

// ============================================================================
// EJEMPLO DE CÓMO USAR ESTE PARTITIONER EN UN READER @StepScope
// ============================================================================
//
// @Bean
// @StepScope  // IMPORTANTE: permite acceder al ExecutionContext de la partición
// public ItemReader<Usuario> workerReader(
//         @Value("#{stepExecutionContext['minId']}") long minId,
//         @Value("#{stepExecutionContext['maxId']}") long maxId) {
//
//     // Ejemplo con JdbcPagingItemReader (ideal para BBDD):
//     JdbcPagingItemReader<Usuario> reader = new JdbcPagingItemReader<>();
//     reader.setDataSource(dataSource);
//     reader.setSql("SELECT * FROM usuarios WHERE id BETWEEN :minId AND :maxId");
//
//     // Configurar parámetros
//     Map<String, Object> params = new HashMap<>();
//     params.put("minId", minId);
//     params.put("maxId", maxId);
//     reader.setParameterValues(params);
//
//     // ... más configuración del reader
//     return reader;
// }
//
// ============================================================================
// ESTRATEGIAS PARA CSV FILES
// ============================================================================
//
// Para archivos CSV (como usuarios.csv), tienes varias opciones:
//
// 1. DIVIDIR EL ARCHIVO: Crear N archivos más pequeños (uno por partición)
//    - Partición 0: usuarios_part0.csv (líneas 1-175)
//    - Partición 1: usuarios_part1.csv (líneas 176-350)
//    - etc.
//    - El Partitioner asignaría el nombre del archivo en lugar de minId/maxId
//
// 2. SALTAR LÍNEAS: Usar LineNumberReader para saltar a la línea inicial
//    - Cada worker salta N líneas antes de empezar a leer
//    - Requiere contar líneas totales primero
//
// 3. FILTRAR POR ÍNDICE: Si los usuarios tienen un ID numérico en el CSV
//    - Leer todo el archivo pero filtrar por rango de IDs
//    - Menos eficiente pero más simple
//
// La estrategia recomendada depende de tu caso de uso específico.
// ============================================================================
