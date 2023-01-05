package org.termi.common.dto;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public record SelectOption<V>(
        V value,
        String label,
        boolean disabled
) {

    public static <V> SelectOption<V> of(V value, String label) {
        return new SelectOption<>(value, label, false);
    }

    public static <V> SelectOption<V> of(V value,
                                      String label,
                                      Collection<V> disables) {
        return new SelectOption<>(value, label, disables.contains(value));
    }

    public static <T, V> List<SelectOption<V>> listOf(Collection<T> list,
                                                      Function<T, V> getValueFunc,
                                                      Function<T, String> getLabelFunc) {
        return listOf(list, getValueFunc, getLabelFunc, List.of());
    }

    public static <T, V> List<SelectOption<V>> listOf(Collection<T> list,
                                                      Function<T, V> getValueFunc,
                                                      Function<T, String> getLabelFunc,
                                                      Collection<V> disables) {
        return list.stream()
                .map(t -> {
                            V optionValue = getValueFunc.apply(t);
                            return new SelectOption<>(
                                    optionValue,
                                    getLabelFunc.apply(t),
                                    disables.contains(optionValue)
                            );
                        }
                )
                .toList();
    }
}