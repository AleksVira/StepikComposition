package ru.virarnd.stepcomposition.presentation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import java.lang.RuntimeException
import ru.virarnd.stepcomposition.R
import ru.virarnd.stepcomposition.databinding.FragmentGameBinding
import ru.virarnd.stepcomposition.domain.entity.GameResult
import ru.virarnd.stepcomposition.domain.entity.GameSettings
import ru.virarnd.stepcomposition.domain.entity.Level

class GameFragment : Fragment() {

    companion object {

        const val GAME_FRAGMENT_NAME = "GameFragment"
        private const val KEY_LEVEL = "level"

        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_LEVEL, level)
                }
            }
        }
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    private lateinit var level: Level

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            tvOption1.setOnClickListener {
                launchGameFinishedFragment(GameResult(
                    winner = true,
                    countOfRightAnswers = 12,
                    countOfQuestions = 15,
                    gameSettings = GameSettings(
                        maxSumValue = 152,
                        minCountOfRightAnswers = 10,
                        minPercentOfRightAnswers = 80,
                        gameTimeInSeconds = 8128
                    )
                ))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
        level = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getSerializable(KEY_LEVEL, Level::class.java)
        } else {
            arguments?.getSerializable(KEY_LEVEL) as Level
        } ?: throw RuntimeException("Level is null")
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

}
