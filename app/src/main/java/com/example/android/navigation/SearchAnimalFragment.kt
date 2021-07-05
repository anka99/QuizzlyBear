package com.example.android.navigation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentSearchAnimalBinding

class SearchAnimalFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentSearchAnimalBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_search_animal, container, false)

        val questionNumber = arguments?.getInt("questionNumber")?.plus(1) ?: 0

        val numQuestions = arguments?.getInt("numQuestions") ?: 1

        val isCorrect = arguments?.getBoolean("isCorrect") ?: false

        val animalImage = arguments?.getInt("animalImage") // null should never happen

        val animal = arguments?.getString("animal") ?: ""


        binding.nextQuestionButton.setOnClickListener{view: View ->
            if (questionNumber >= numQuestions) {
                view.findNavController().navigate(R.id.action_searchAnimalFragment_to_gameEndFragment)
            }
            else {
                val bundle = bundleOf("questionNumber" to questionNumber)
                view.findNavController().navigate(R.id.action_searchAnimalFragment_to_gameFragment, bundle)
            }
        }

        binding.searchButton.setOnClickListener{
            val queryUrl: Uri = Uri.parse("${SEARCH_PREFIX}${animal}")
            val intent = Intent(Intent.ACTION_VIEW, queryUrl)
            startActivity(intent)
        }

        if (animalImage != null) {
            binding.tryAgainImage.setImageResource(animalImage)
        }

        binding.isCorrectText.text = "Correct! This is a " + animal + "."

        if (!isCorrect) {
            binding.isCorrectText.text = "Incorrect! This is a " + animal + "."
        }

        return binding.root
    }

    companion object {
        const val SEARCH_PREFIX = "https://www.google.com/search?q="
    }
}
