package com.archclient.util;

import java.util.*;

public class Utils {

    public static <A, B> List<B> convertListType(List<A> toConvert) {
        List<B> toReturn = new ArrayList<>();

        for (A item : toConvert) {
            toReturn.add((B) item);
        }

        return toReturn;
    }

    public static <A> List<A> toList(A[] images) {
        return new ArrayList<>(Arrays.asList(images));
    }

    public static <A, B> Iterator<B> convertIterationType(Iterator<A> iterator) {
        List<B> toReturn = new ArrayList<>();
        while (iterator.hasNext()) {
            toReturn.add((B) iterator.next());
        }
        return toReturn.iterator();
    }

    public static <A> Iterable<A> iteratorToIterable(Iterator<A> iterator) {
        return () -> iterator;
    }

    public static <A, B> Collection<B> convertCollectionType(Collection<A> collection) {
        List<B> toReturn = new ArrayList<>();

        for (A item : collection) {
            toReturn.add((B) item);
        }

        return toReturn;
    }
}
