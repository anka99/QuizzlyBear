package com.example.android.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    data class Question(
        var answers: List<String> = emptyList(),
        val img: Int,
        val answerIds : List<Int> = emptyList())

    private val questions: MutableList<Question> = mutableListOf(
            Question(
                img = R.drawable.white_nose_coati,
                answerIds = listOf((R.string.q1a1), (R.string.q1a2), (R.string.q1a3), (R.string.q1a4))),
            Question(
                answerIds = listOf((R.string.q2a1), (R.string.q2a2), (R.string.q2a3), (R.string.q2a4)),
                img = R.drawable.puched_frog),
            Question(
                answerIds = listOf((R.string.q3a1), (R.string.q3a2), (R.string.q3a3), (R.string.q3a4)),
                img = R.drawable.snek),
            Question(
                answerIds = listOf((R.string.q4a1), (R.string.q4a2), (R.string.q4a3), (R.string.q4a4)),
                img = R.drawable.blandings_turtle),
            Question(
                answerIds = listOf((R.string.q5a1), (R.string.q5a2), (R.string.q5a3), (R.string.q5a4)),
                img = R.drawable.mandarin_dragonet),
            Question(
                answerIds = listOf((R.string.q6a1), (R.string.q6a2), (R.string.q6a3), (R.string.q6a4)),
                img = R.drawable.tacua_speciosa),
            Question(
                answerIds = listOf((R.string.q7a1), (R.string.q7a2), (R.string.q7a3), (R.string.q7a4)),
                img = R.drawable.klipspringer),
    )


    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = arguments?.getInt("questionNumber") ?: 0
    private val numQuestions = questions.size

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
                inflater, R.layout.fragment_game, container, false)

        setQuestion()

        binding.game = this

        questionIndex = arguments?.getInt("questionNumber") ?: 0

        // Set the onClickListener for the submitButton
        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            // Do nothing if nothing is checked (id == -1)
            if (-1 != checkedId) {
                var answerIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                    R.id.fourthAnswerRadioButton -> answerIndex = 3
                }

                binding.invalidateAll()

                val isCorrect : Boolean = answers[answerIndex] == currentQuestion.answers[0]

                // After the answer is given, navigate to "search animal screen"
                val bundle = bundleOf(
                    "isCorrect" to isCorrect,
                    "animal" to currentQuestion.answers[0],
                    "questionNumber" to questionIndex,
                    "numQuestions" to numQuestions,
                    "animalImage" to currentQuestion.img
                )

                view.findNavController()
                    .navigate(R.id.action_gameFragment_to_searchAnimalFragment, bundle)
            }
        }

        binding.questionImage.setImageResource(currentQuestion.img)
        return binding.root
    }

    private fun setQuestion() {
        questionIndex = arguments?.getInt("questionNumber") ?: 0

        currentQuestion = questions[questionIndex]
        currentQuestion.answers = currentQuestion.answerIds.map { getString(it) }
        // randomize the answers into a copy of the array
        answers = currentQuestion.answers.toMutableList()
        // and shuffle them
        answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_android_trivia_question, questionIndex + 1, numQuestions)
    }


}
