package com.example.default1.utils;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CollectionUtils {
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> void ifEmpty(Collection<T> collection, Consumer<Collection<T>> action) {
        if(isEmpty(collection)) action.accept(collection);
    }

    public static <T> List<T> setToList(Set<T> set) {
        return new ArrayList<>(set);
    }

    public static <T> Set<T> listToSet(List<T> list) {
        return new HashSet<>(list);
    }

    @SafeVarargs
    public static <T> Collection<T> merge(Collection<T>... collections) {
        if(collections == null) {
            throw new RuntimeException("Collections cannot be Null.");
        }
        Collection<T> mergedCollection = new ArrayList<>();
        for (Collection<T> collection : collections) {
            mergedCollection.addAll(collection);
        }
        return mergedCollection;
    }

    public static <T> Collection<T> splice(Collection<T> collection, int start, int end) {
        if(isEmpty(collection)) return collection;
        List<T> ret = new ArrayList<>();
        for(int i = start; i < end; i++) {
            ret.add(((List<T>) collection).get(i));
        }
        return ret;
    }

    public static <T> List<List<T>> separationList(Collection<T> collection, int size) {
        if(isEmpty(collection)) return null;
        List<List<T>> ret = new ArrayList<>();
        Iterator<T> iterator = collection.iterator();
        while (iterator.hasNext()) {
            List<T> subList = new ArrayList<>();
            for (int i = 0; i < size && iterator.hasNext(); i++) {
                subList.add(iterator.next());
            }
            ret.add(subList);
        }
        return ret;
    }

    public static <T> List<List<T>> separationList(Collection<T> collection, Predicate<T> predicate) {
        if(isEmpty(collection)) return null;
        List<T> trueList = new ArrayList<>();
        List<T> falseList = new ArrayList<>();
        for (T item : collection) {
            if (predicate.test(item)) {
                trueList.add(item);
            } else {
                falseList.add(item);
            }
        }
        return Arrays.asList(trueList, falseList);
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
        if(isEmpty(list)) return list;
        return new ArrayList<>(new LinkedHashSet<>(list));
    }

    public static <T, R> List<R> map(List<T> list, Function<T, R> func) {
        return list.stream()
                .map(func)
                .collect(Collectors.toList());
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        if(isEmpty(list)) return list;
        return list.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
}
