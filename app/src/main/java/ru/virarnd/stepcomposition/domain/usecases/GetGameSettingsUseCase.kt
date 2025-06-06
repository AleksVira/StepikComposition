package ru.virarnd.stepcomposition.domain.usecases

import ru.virarnd.stepcomposition.domain.entity.GameSettings
import ru.virarnd.stepcomposition.domain.entity.Level
import ru.virarnd.stepcomposition.domain.repository.GameRepository

class GetGameSettingsUseCase(
    private val repository: GameRepository
) {

    operator fun invoke(level: Level): GameSettings {
        return repository.generateGameSettings(level)

    }
}