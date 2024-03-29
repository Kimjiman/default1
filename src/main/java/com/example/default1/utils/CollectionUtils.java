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

    @SafeVarargs
    public static <T> Collection<T> merge(Collection<T>... collections) {
        Collection<T> mergedCollection = new ArrayList<>();
        for (Collection<T> collection : collections) {
            mergedCollection.addAll(collection);
        }
        return mergedCollection;
    }

    public static <T> Collection<T> splice(Collection<T> collection, int start, int end) {
        List<T> ret = new ArrayList<>();
        for(int i = start; i < end; i++) {
            ret.add(((List<T>) collection).get(i));
        }
        return ret;
    }

    /**
     *  Integer[] array = listToArray(list, size -> new Integer[size]);
     */
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
}
