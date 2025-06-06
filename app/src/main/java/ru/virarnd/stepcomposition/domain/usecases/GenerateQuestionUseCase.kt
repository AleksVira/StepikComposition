package ru.virarnd.stepcomposition.domain.usecases

import ru.virarnd.stepcomposition.domain.entity.GameSettings
import ru.virarnd.stepcomposition.domain.entity.Question
import ru.virarnd.stepcomposition.domain.repository.GameRepository

class GenerateQuestionUseCase(
    private val repository: GameRepository
)  {

    private companion object {
        private const val COUNT_OF_OPTIONS = 6
    }

    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

}