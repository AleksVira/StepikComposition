package ru.virarnd.stepcomposition.presentation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import java.lang.RuntimeException
import ru.virarnd.stepcomposition.databinding.FragmentGameFinishedBinding
import ru.virarnd.stepcomposition.domain.entity.GameResult
import ru.virarnd.stepcomposition.presentation.GameFragment.Companion.GAME_FRAGMENT_NAME

class GameFinishedFragment : Fragment() {

    companion object {

        private const val KEY_RESULT = "gameResult"

        fun newInstance(gameResult: GameResult) : GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_RESULT, gameResult)
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                retryGame()
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
        gameResult = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getSerializable(KEY_RESULT, GameResult::class.java)
        } else {
            arguments?.getSerializable(KEY_RESULT) as GameResult
        } ?: throw RuntimeException("GameResult is null")
    }


    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(GAME_FRAGMENT_NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

}
