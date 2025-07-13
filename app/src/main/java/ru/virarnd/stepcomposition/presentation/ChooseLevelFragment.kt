package ru.virarnd.stepcomposition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.lang.RuntimeException
import ru.virarnd.stepcomposition.R
import ru.virarnd.stepcomposition.databinding.FragmentChooseLevelBinding
import ru.virarnd.stepcomposition.domain.entity.Level

class ChooseLevelFragment : Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding == null")

    private val levelButtonClickListener: View.OnClickListener = View.OnClickListener {
        val gameLevel = when(it.id) {
            R.id.button_level_easy -> Level.EASY
            R.id.button_level_normal -> Level.NORMAL
            R.id.button_level_hard -> Level.HARD
            else -> Level.TEST
        }
        launchGameFragment(gameLevel)
     }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            buttonLevelTest.setOnClickListener(levelButtonClickListener)
            buttonLevelEasy.setOnClickListener(levelButtonClickListener)
            buttonLevelNormal.setOnClickListener(levelButtonClickListener)
            buttonLevelHard.setOnClickListener(levelButtonClickListener)
        }
    }

    private fun launchGameFragment(level: Level) {
        findNavController().navigate(
            ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(level)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
