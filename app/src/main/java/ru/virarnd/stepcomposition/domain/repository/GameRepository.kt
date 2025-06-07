package ru.virarnd.stepcomposition.domain.repository

import ru.virarnd.stepcomposition.domain.entity.GameSettings
import ru.virarnd.stepcomposition.domain.entity.Level
import ru.virarnd.stepcomposition.domain.entity.Question

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question

    fun generateGameSettings(level: Level): GameSettings
}