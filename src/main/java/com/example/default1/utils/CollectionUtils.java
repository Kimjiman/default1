package com.example.default1.utils;

import java.util.*;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CollectionUtils {
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> List<T> setToList(Set<T> set) {
        return new ArrayList<>(set);
    }

    public static <T> Set<T> listToSet(List<T> list) {
        return new HashSet<>(list);
    }

    public static <T> T[] listToArray(List<T> list, IntFunction<T[]> generator) {
        return list.toArray(generator.apply(list.size()));
    }

    public static <T> List<T> arrayToList(T[] arr) {
        return Arrays.asList(arr);
    }

    public static <T> List<T> removeDuplicates(List<T> list) {
        return new ArrayList<>(new LinkedHashSet<>(list));
    }

    public static <T, R> List<R> map(List<T> list, Function<T, R> func) {
        return list.stream()
                .map(func)
                .collect(Collectors.toList());
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        return list.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public static <T, R> R find(List<T> list, Predicate<T> predicate, Function<T, R> func) {
        return list.stream()
                .filter(predicate)
                .findFirst()
                .map(func)
                .orElse(null);
    }
}
