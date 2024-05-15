package com.CelebTrivia.FamousFacesChallenge.famousfaceschallenge.view
import com.squareup.picasso.Picasso
import android.graphics.drawable.GradientDrawable
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.CelebTrivia.FamousFacesChallenge.famousfaceschallenge.R
import com.CelebTrivia.FamousFacesChallenge.famousfaceschallenge.database.ActorDao
import com.CelebTrivia.FamousFacesChallenge.famousfaceschallenge.database.DatabaseCopyHelper
import com.CelebTrivia.FamousFacesChallenge.famousfaceschallenge.databinding.FragmentQuizBinding
import com.CelebTrivia.FamousFacesChallenge.famousfaceschallenge.model.ActorModel


class FragmentQuiz : Fragment() {
    lateinit var framentQuizBinding: FragmentQuizBinding
    var actorList = ArrayList<ActorModel>()
    var correctNumber = 0
    var wrongNumber = 0
    var emptyNumber = 0
    var questionNumber = 0

    lateinit var correctActor : ActorModel
    var wrongActor = ArrayList<ActorModel>()

    val dao = ActorDao()

    var optionControl = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        framentQuizBinding = FragmentQuizBinding.inflate(inflater, container, false)
        // Creăm un obiect GradientDrawable
        val gradientDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            colors = intArrayOf(0xFF512DA8.toInt(), 0xFFC2185B.toInt()) // Culorile de start și de sfârșit
            cornerRadius = resources.getDimensionPixelSize(R.dimen.corner_radius).toFloat() // Raza colțurilor
            // Dacă vrei un unghi specific pentru gradient, poți seta asta:
            // gradientDrawable.orientation = GradientDrawable.Orientation.TL_BR
        }
        // Setăm forma ca fundal pentru view-ul nostru
        framentQuizBinding.root.background = gradientDrawable

        actorList = dao.getRandomTenRecords(DatabaseCopyHelper(requireActivity()))

        for (actor in actorList) {

            Log.d("actor", actor.id.toString())
            Log.d("actor", actor.actorsName)
            Log.d("actor", actor.actorName)
            Log.d("actor", actor.actorsGender)
            Log.d("actor", "**************************")

        }

        showData()

        framentQuizBinding.buttonA.setOnClickListener() {
            answerControl(framentQuizBinding.buttonA)

        }
        framentQuizBinding.buttonB.setOnClickListener() {
            answerControl(framentQuizBinding.buttonB)

        }
        framentQuizBinding.buttonC.setOnClickListener() {
            answerControl(framentQuizBinding.buttonC)

        }
        framentQuizBinding.buttonD.setOnClickListener() {
            answerControl(framentQuizBinding.buttonD)

        }
        framentQuizBinding.buttonNext.setOnClickListener {

            questionNumber++

            if (questionNumber > 14) {

                if (!optionControl) {
                    emptyNumber++
                }

                val direction = FragmentQuizDirections.actionFragmentQuizToFragmentResult().apply {

                    correct = correctNumber
                    wrong = wrongNumber
                    empty = emptyNumber

                }

                this.findNavController().apply {
                    navigate(direction)
                    popBackStack(R.id.fragmentResult, false)
                }


                //Toast.makeText(requireActivity(),"The quiz is finished",Toast.LENGTH_SHORT).show()

            } else {

                showData()

                if (!optionControl) {
                    emptyNumber++
                    framentQuizBinding.textViewEmpty.text = emptyNumber.toString()
                } else {
                    setButtonToInitialProperties()
                }

            }

            optionControl = false


        }

        // Inflate the layout for this fragment
        return framentQuizBinding.root
    }

    private fun showData() {



        framentQuizBinding.textViewQuestion.text =
            resources.getString(R.string.question_number).plus(" ").plus(questionNumber + 1)

        correctActor = actorList[questionNumber]



        Picasso.get()
            .load(resources.getIdentifier(correctActor.actorName, "drawable", requireActivity().packageName))
            .resize(300, 250)  // redimensionare la 300x250
            .into(framentQuizBinding.imageViewActor)


        wrongActor =
            dao.getRandomThreeRecords(DatabaseCopyHelper(requireActivity()), correctActor.id, correctActor.actorsGender)

        val mixOptions = HashSet<ActorModel>()
        mixOptions.clear()

        mixOptions.add(correctActor)
        mixOptions.add(wrongActor[0])
        mixOptions.add(wrongActor[1])
        mixOptions.add(wrongActor[2])

        val options = ArrayList<ActorModel>()
        options.clear()

        for (eachActor in mixOptions) {

            options.add(eachActor)

        }

        framentQuizBinding.buttonA.text = options[0].actorsName
        framentQuizBinding.buttonB.text = options[1].actorsName
        framentQuizBinding.buttonC.text = options[2].actorsName
        framentQuizBinding.buttonD.text = options[3].actorsName
    }

    private fun answerControl(button: Button) {

        val clickedOption = button.text.toString()
        val correctAnswer = correctActor.actorsName

        if (clickedOption == correctAnswer) {

            correctNumber++
            framentQuizBinding.textViewCorrect.text = correctNumber.toString()
            button.setBackgroundColor(Color.GREEN)

        } else {

            wrongNumber++
            framentQuizBinding.textViewWrong.text = wrongNumber.toString()
            button.setBackgroundColor(Color.RED)
            button.setTextColor(Color.parseColor("#ADD8E6"))

            when (correctAnswer) {

                framentQuizBinding.buttonA.text -> framentQuizBinding.buttonA.setBackgroundColor(
                    Color.GREEN
                )

                framentQuizBinding.buttonB.text -> framentQuizBinding.buttonB.setBackgroundColor(
                    Color.GREEN
                )

                framentQuizBinding.buttonC.text -> framentQuizBinding.buttonC.setBackgroundColor(
                    Color.GREEN
                )

                framentQuizBinding.buttonD.text -> framentQuizBinding.buttonD.setBackgroundColor(
                    Color.GREEN
                )

            }

        }

        framentQuizBinding.buttonA.isClickable = false
        framentQuizBinding.buttonB.isClickable = false
        framentQuizBinding.buttonC.isClickable = false
        framentQuizBinding.buttonD.isClickable = false

        optionControl = true

    }

    private fun setButtonToInitialProperties() {

        framentQuizBinding.buttonA.apply {
            setBackgroundColor(Color.parseColor("#ADD8E6"))
            setTextColor(resources.getColor(R.color.pink, requireActivity().theme))
            isClickable = true
        }
        framentQuizBinding.buttonB.apply {
            setBackgroundColor(Color.parseColor("#ADD8E6"))
            setTextColor(resources.getColor(R.color.pink, requireActivity().theme))
            isClickable = true
        }
        framentQuizBinding.buttonC.apply {
            setBackgroundColor(Color.parseColor("#ADD8E6"))
            setTextColor(resources.getColor(R.color.pink, requireActivity().theme))
            isClickable = true
        }
        framentQuizBinding.buttonD.apply {
            setBackgroundColor(Color.parseColor("#ADD8E6"))
            setTextColor(resources.getColor(R.color.pink, requireActivity().theme))
            isClickable = true
        }
    }


}
