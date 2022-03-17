package com.esmaeel.challenge.domain.usecases

interface UseCase<ReturnType, Input> {
    suspend fun invoke(input: Input): ReturnType
}