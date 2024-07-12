package com.archclient.common.generic;

public interface Visitor<T> {
    void accept(T t);
}
