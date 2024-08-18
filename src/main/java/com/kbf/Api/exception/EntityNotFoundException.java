package com.kbf.Api.exception;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class EntityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -5889554920928861295L;

    /**
     * Constructor to create an EntityNotFoundException.
     *
     * @param clazz The entity class that was not found.
     * @param searchParamsMap A variable number of strings representing search parameters.
     */
    public EntityNotFoundException(Class<?> clazz, String... searchParamsMap) {
        super(EntityNotFoundException.generateMessage(clazz.getSimpleName(), toMap(String.class, String.class, searchParamsMap)));
    }

    /**
     * Generates a detailed message for the exception.
     *
     * @param entity The name of the entity.
     * @param searchParams The map of search parameters.
     * @return A formatted error message.
     */
    private static String generateMessage(String entity, Map<String, String> searchParams) {
        return StringUtils.capitalize(entity) +
                " was not found for parameters " +
                searchParams;
    }

    /**
     * Converts an array of objects into a map. The array should contain pairs of key-value elements.
     *
     * @param keyType The class type of the keys.
     * @param valueType The class type of the values.
     * @param entries The array of key-value pairs.
     * @return A map containing the key-value pairs.
     * @throws IllegalArgumentException If the entries array length is odd.
     */
    private static <K, V> Map<K, V> toMap(Class<K> keyType, Class<V> valueType, Object... entries) {
        if (entries.length % 2 == 1)
            throw new IllegalArgumentException("Invalid entries. The array length should be even, representing key-value pairs.");

        return IntStream.range(0, entries.length / 2).map(i -> i * 2)
                .collect(HashMap::new,
                        (m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])),
                        Map::putAll);
    }
}
