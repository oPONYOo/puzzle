package com.example.puzzle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import com.example.puzzle.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding

    private var level = ""
    private var levelSelect = true
    private var difficulty = ""
    private var difficultySelect = true
    val btnArray = ArrayList<AppCompatButton>()
    val btnArray2 = ArrayList<AppCompatButton>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnArray.add(binding.firstBtn)
        btnArray.add(binding.secondBtn)
        btnArray.add(binding.thirdBtn)


        btnArray2.add(binding.easyBtn)
        btnArray2.add(binding.normalBtn)

        binding.firstBtn.text = "3x3"
        binding.secondBtn.text= "4x4"
        binding.thirdBtn.text = "5x5"
        binding.easyBtn.text = "easy"
        binding.normalBtn.text = "hard"

        binding.firstBtn.tag = "3x3"
        binding.secondBtn.tag= "4x4"
        binding.thirdBtn.tag = "5x5"
        binding.easyBtn.tag = "easy"
        binding.normalBtn.tag = "hard"


        for (i in 0 until btnArray.size){
            btnArray[i].setOnClickListener(onClickListener)
        }

        for (i in 0 until btnArray2.size){
            btnArray2[i].setOnClickListener(difficultyClickListener)
        }

        binding.startBtn.setOnClickListener{
            if (levelSelect && difficultySelect){
                val nextIntent = Intent(this, MainActivity::class.java)
                nextIntent.putExtra("level", level)
                nextIntent.putExtra("difficulty", difficulty)
                startActivity(nextIntent)

            }
        }

    }

    private var onClickListener = View.OnClickListener { view ->
        level = view.tag.toString()
        Log.e("tag", level)
        levelSelect = true
    }

    private var difficultyClickListener = View.OnClickListener { view ->
        difficulty = view.tag.toString()
        Log.e("tag2", difficulty)
        difficultySelect = true
    }


}