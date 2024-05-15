package com.CelebTrivia.FamousFacesChallenge.famousfaceschallenge.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.CelebTrivia.FamousFacesChallenge.famousfaceschallenge.R
import com.CelebTrivia.FamousFacesChallenge.famousfaceschallenge.database.DatabaseCopyHelper
import com.CelebTrivia.FamousFacesChallenge.famousfaceschallenge.databinding.FragmentHomeBinding



class FragmentHome : Fragment() {

    lateinit var fragmentHomeBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

        createAndOpenDatabase()

        fragmentHomeBinding.buttonStart.setOnClickListener() {

            val direction = FragmentHomeDirections.actionFragmentHomeToFragmentQuiz()
            this.findNavController().navigate(direction)
            //it.findNavController()
            //requireActivity().findNavController()


        }
        return fragmentHomeBinding.root
    }

    private fun createAndOpenDatabase(){

        //try-catch
        try {

            val helper = DatabaseCopyHelper(requireActivity())
            helper.createDataBase()
            helper.openDataBase()

        }catch (e : Exception){

            e.printStackTrace()

        }

    }


}