package com.example.puzzle

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatTextView
import com.example.puzzle.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
    private var mnextNum = 1
    private var mCurrentNum = 0
    private var pos = 0
    private var count = 0

    var finish = false
    private var sizevalueFloat = 0f
    private var sizevalueInt = 0

    private var density = 0f
    private var changeSize: Float? = null
    private var size_x = 0
    private var size_y = 0
    private var nullX = 0f
    private var nullY = 0f
    private var x = 0f
    private var y = 0f

    private var xaxis: Boolean = true

    private var directionList = ArrayList<String>()
    private var arraylist = ArrayList<Int>()
    private var arraylist2 = ArrayList<Int>()
    private var examplelist = ArrayList<Int>()
    private var imgList = Vector<Int>()

    private var UP = 1
    private var DOWN = 2
    private var RIGHT = 3
    private var LEFT = 4

    private var gameNum = 0

    private lateinit var mrandList: ArrayList<Int>
    private var numquestLayout = ArrayList<GameLayout>()
    private var GameLayout = ArrayList<GameLayout>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.getSize()
        changeSize()

        if (savedInstanceState == null) {
            initGame("3x3", "easy")
        }
    }

    @SuppressLint("Recycle")
    fun initGame(GmLevel: String, difficulty: String) {
        when (GmLevel) {

            "3x3" -> {
                sizevalueFloat = 3f
                sizevalueInt = 3
                gameNum = 9
                when(difficulty){
                    "easy" -> {
                        for (i in 0..8) {
                            arraylist.add(i)
                        }
                        for (i in 1..9) {
                            examplelist.add(i)
                        }
                    }
                    "hard" -> {
                        val imageList1 = resources.obtainTypedArray(R.array.threebythree)
                        for (i in 0 until  imageList1.length()) {
                            val drawable = imageList1.getDrawable(i)
                            Log.e("drawable", "${drawable}")
                        }

                    }
                }


            }
            "4x4" -> {
                sizevalueFloat = 4f
                sizevalueInt = 4
                gameNum = 16
                when(difficulty){
                    "easy" -> {
                        for (i in 0..15) {
                            arraylist.add(i)
                        }
                        for (i in 1..16) {
                            examplelist.add(i)
                        }
                    }

                }

            }
            "5x5" -> {
                sizevalueFloat = 5f
                sizevalueInt = 5
                gameNum = 25
                when(difficulty){
                    "easy" -> {
                        for (i in 0..24) {
                            arraylist.add(i)
                        }
                        for (i in 1..25) {
                            examplelist.add(i)
                        }
                    }

                }
            }

        }


        count = 0
        CoroutineScope(mainDispatcher).launch {
            delay(1000L)
            mrandList = genRandom()
            count = 1
            genQuest()
            genExample()
            Log.e("rest", "${1%2}")
            Log.e("rest", "${4%2}")
            Log.e("rest", "${7%2}")
            Log.e("rest", "${2%2}")
            Log.e("rest", "${5%2}")
            Log.e("rest", "${8%2}")
        }
    }
    fun getSize(): Point {
        val display = windowManager.defaultDisplay // in case of Activity
        /* val display = activity!!.windowManaver.defaultDisplay */ // in case of Fragment
        val size = Point()
        display.getSize(size) // or getSize(size)
        Log.e("size", "${size}")
        return size
    }

    fun changeSize() {
        val displaySize: Point = this@MainActivity.getSize()
        density = resources.displayMetrics.density
        /* px, dp간의 비율 */
        Log.e("density", "${density}")
        size_x = (displaySize.x / density).toInt()
        size_y = (displaySize.y / density).toInt()
        Log.e("size_X", "${size_x}")
        Log.e("size_Y", "${size_y}")

    }

    private fun genRandom(): ArrayList<Int> {

        for (i in 0..1000){
            val nullNum = arraylist.indexOf(0)
            Log.e("nullNum", "${nullNum}")
            when(Random().nextInt(4)){

                 UP -> {
                     if (nullNum - sizevalueInt >= 0){
                         arraylist[nullNum] = arraylist[nullNum - sizevalueInt]
                         arraylist[nullNum - sizevalueInt] = 0
                     }else{
                         arraylist[nullNum] = arraylist[nullNum + sizevalueInt]
                         arraylist[nullNum + sizevalueInt] = 0
                     }


                }
                DOWN -> {
                    if (nullNum + sizevalueInt < gameNum){
                        arraylist[nullNum] = arraylist[nullNum + sizevalueInt]
                        arraylist[nullNum + sizevalueInt] = 0
                    }else{
                        arraylist[nullNum] = arraylist[nullNum - sizevalueInt]
                        arraylist[nullNum - sizevalueInt] = 0
                    }

                }
                RIGHT -> {
                    if (nullNum % sizevalueInt != sizevalueInt-1){
                        Log.e("true1", "True")
                        Log.e("dd", "${nullNum % sizevalueInt}")
                        Log.e("vv", "${sizevalueInt-1}")
                        arraylist[nullNum] = arraylist[nullNum + 1]
                        arraylist[nullNum + 1] = 0
                    }else{
                        Log.e("true2", "True")
                        Log.e("dd1", "${nullNum % sizevalueInt}")
                        Log.e("vv1", "${sizevalueInt-1}")
                        arraylist[nullNum] = arraylist[nullNum - 1]
                        arraylist[nullNum - 1] = 0
                    }

                }
                LEFT -> {
                    if (nullNum % sizevalueInt != 0){
                        arraylist[nullNum] = arraylist[nullNum - 1]
                        arraylist[nullNum - 1] = 0
                    }
                    else {
                        arraylist[nullNum] = arraylist[nullNum + 1]
                        arraylist[nullNum + 1] = 0
                    }
                }
            }


        }




        val idx = ArrayList<Int>()
        for (i in 0 until arraylist.size) {
            idx.add(arraylist[i])

        }
        return idx
    }



    private fun genExample(){
        changeSize = binding.exampleLayout.width / sizevalueFloat

        for (count in 0 until examplelist.size) {
            val questLayout = GameLayout(this)
            questLayout.setQuestNum(examplelist[count])
                .setXY(changeSize!! * (count % sizevalueInt), changeSize!! * (count / sizevalueInt))
                .setVisible(true)

            questLayout.setFinally()
            questLayout.cardVisible()
            binding.exampleLayout.addView(questLayout)
            questLayout.setTxtSize(changeSize!! / 2.8f)
            questLayout.layoutParams.width = changeSize!!.toInt()
            questLayout.layoutParams.height = changeSize!!.toInt()

        }
    }

    private fun genQuest() {
        changeSize = binding.questAreaLayout.width / sizevalueFloat

        Log.e("mrandList", "${mrandList}")

        for (count in 0 until mrandList.size) {
            val questLayout = GameLayout(this)
            questLayout.setQuestNum(mrandList[count])
                .setXY(changeSize!! * (count % sizevalueInt), changeSize!! * (count / sizevalueInt))
                .setVisible(true)

            questLayout.setFinally()
            questLayout.cardVisible()
            binding.questAreaLayout.addView(questLayout)
            questLayout.setTxtSize(changeSize!! / 2.8f)
            questLayout.layoutParams.width = changeSize!!.toInt()
            questLayout.layoutParams.height = changeSize!!.toInt()
            if (mrandList[count] == 0 ){

                nullX = questLayout.x
                nullY = questLayout.y
                Log.e("nullX", "${nullX}")
                Log.e("nullY", "${nullY}")

            }

            initAnim(nullX, 0f, true)
        }

        for (j in 0 until mrandList.size) {
            val questLayout = binding.questAreaLayout.getChildAt(j) as GameLayout
            questLayout.setVisible(true)
            val questTxtView: AppCompatTextView = questLayout.findViewById(R.id.numberTxtView)
            binding.showNumberTxtView.text = mCurrentNum.toString()
            numquestLayout.add(questLayout)
            questLayout.setOnClickListener {
                mCurrentNum++
                binding.showNumberTxtView.text = mCurrentNum.toString()
                Log.e("mcurrentNum", "${mCurrentNum}")
                val prevX = questLayout.x
                val prevY = questLayout.y

                when(xaxis){
                    true -> {
                        ObjectAnimator.ofFloat(questLayout, "translationX",nullX )
                            .apply {
                                duration = 400
                                addListener(object : AnimatorListenerAdapter() {
                                    override fun onAnimationEnd(animation: Animator) {

//                                        Log.e("0", "${mrandList.indexOf(0)}")
//                                        if (questTxtView.text.toString().toInt() ==0){
//                                            questTxtView.text= 0.toString()
//                                            mrandList.set(mrandList.indexOf(0), questTxtView.text.toString().toInt())
//                                        }else{
//                                            mrandList.set(mrandList.indexOf(0), questTxtView.text.toString().toInt())
//                                        }
//
//                                        mrandList.set(mrandList.indexOf(questTxtView.text.toString().toInt()), 0)
//                                        Log.e("questTxtView", "${questTxtView.text.toString().toInt()}")
//                                        Log.e("mrandList2", "${mrandList}")
//                                        for (i in 0 until mrandList.size){
//
//
//                                        }
                                        Log.e("mranList11", "$mrandList")
                                        val nullNum = mrandList.indexOf(0)
//                                        if (nullNum % sizevalueInt != sizevalueInt-1){
//                                            arraylist[nullNum] = arraylist[nullNum + 1]
//                                            arraylist[nullNum + 1] = 0
//                                        }else{
//                                            arraylist[nullNum] = arraylist[nullNum - 1]
//                                            arraylist[nullNum - 1] = 0
//                                        }


                                    }
                                })
                                start()
                            }
                    }
                    else -> {
                        ObjectAnimator.ofFloat(questLayout, "translationY",nullY )
                            .apply {
                                duration = 400
                                addListener(object : AnimatorListenerAdapter() {
                                    override fun onAnimationEnd(animation: Animator) {


                                    }
                                })
                                start()
                            }
                    }
                }

                if (finish) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    binding.showNumberTxtView.text = "끝"
                }

            }
        }

    }



    private fun initAnim(xValue: Float, yValue: Float, xAxis: Boolean) {
        x = xValue
        y = yValue
        xaxis = xAxis
    }

}