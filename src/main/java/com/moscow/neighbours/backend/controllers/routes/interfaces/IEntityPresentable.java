package com.moscow.neighbours.backend.controllers.routes.interfaces;

public interface IEntityPresentable<T> {
    T toDBModel();
}
