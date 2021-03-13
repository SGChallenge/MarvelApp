package com.example.marvelchallenge.domain.model

data class ObjectContainer<T>(val data: Data<T>)

data class Data<T>(val results: List<T>)
