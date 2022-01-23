package com.tbse.wnsw.domain.di

/**
 * Created by toddsmith on 1/6/22.
 */
interface ModelMapper<I, O> {
    operator fun invoke(input: I): O
}