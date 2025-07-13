package ru.virarnd.stepcomposition.presentation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.virarnd.stepcomposition.R
import ru.virarnd.stepcomposition.databinding.FragmentGameFinishedBinding
import ru.virarnd.stepcomposition.domain.entity.GameResult
import ru.virarnd.stepcomposition.presentation.GameFragment.Companion.GAME_FRAGMENT_NAME

class GameFinishedFragment : Fragment() {

    companion object {

        private const val KEY_GAME_RESULT = "gameResult"

        fun newInstance(gameResult: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT, gameResult)
                }
            }
        }
    }

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    private lateinit var gameResult: GameResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        bindViews()
    }

    private fun setupClickListeners() {
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                retryGame()
            }
        })
    }

    private fun bindViews() {
        with(binding) {
            emojiResult.setImageResource(getSmileResId())
            tvRequiredAnswers.text = String.format(
                getString(R.string.required_score),
                gameResult.gameSettings.minCountOfRightAnswers.toString()
            )
            tvScoreAnswers.text = String.format(
                getString(R.string.score_answers),
                gameResult.countOfRightAnswers.toString()
            )
            tvRequiredPercentage.text = String.format(
                getString(R.string.required_percentage),
                gameResult.gameSettings.minPercentOfRightAnswers.toString()
            )
            tvScorePercentage.text = String.format(
                getString(R.string.score_percentage),
                getPercentOfRightQuestions()
            )
        }
    }

    private fun getPercentOfRightQuestions(): Float = with(gameResult) {
        if (countOfRightAnswers == 0) {
            0f
        } else {
            (gameResult.countOfRightAnswers.toFloat() / gameResult.countOfQuestions * 100)
        }
    }

    private fun getSmileResId(): Int = if (gameResult.winner) {
        R.drawable.ic_smile
    } else {
        R.drawable.ic_sad
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
        gameResult = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(KEY_GAME_RESULT, GameResult::class.java)
        } else {
            arguments?.getParcelable<GameResult>(KEY_GAME_RESULT) as GameResult
        } ?: throw RuntimeException("GameResult is null")
    }


    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(GAME_FRAGMENT_NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

}
