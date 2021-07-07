package com.example.puzzle

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatTextView
import com.example.puzzle.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
    private var mCurrentNum = 0

    private var sizevalueFloat = 0f
    private var sizevalueInt = 0

    private var density = 0f
    private var changeSize: Float? = null
    private var size_x = 0
    private var size_y = 0
    private var nullX = 0f
    private var nullY = 0f

    private var what1: Boolean = true

    private var arraylist = ArrayList<Int>()
    private var arraylist2 = ArrayList<Int>()
    private var examplelist = ArrayList<Int>()
    private var imgList = ArrayList<Bitmap>()

    private var UP = 0
    private var DOWN = 1
    private var RIGHT = 2
    private var LEFT = 3

    private var gameNum = 0
    private var clickPos = 0
    private var nullNum = 0
    private var difficulty = true

    private var numquestLayout = ArrayList<GameLayout>()
    private lateinit var nullGameLayout: GameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.getSize()
        changeSize()

        if (savedInstanceState == null) {
            initGame(intent.getStringExtra("level")!!, intent.getStringExtra("difficulty")!!)
        }
    }

    fun initGame(GmLevel: String, Gmdifficulty: String) {
        when (GmLevel) {

            "3x3" -> {
                sizevalueFloat = 3f
                sizevalueInt = 3
                gameNum = 9
                for (i in 1..8) {
                    arraylist.add(i)
                }
                arraylist.add(0)
                for (i in 1..9) {
                    examplelist.add(i)
                }
                for (i in 1..8) {
                    arraylist2.add(i)
                }
                arraylist2.add(0)
            }
            "4x4" -> {
                sizevalueFloat = 4f
                sizevalueInt = 4
                gameNum = 16
                for (i in 1..15) {
                    arraylist.add(i)
                }
                arraylist.add(0)
                for (i in 1..16) {
                    examplelist.add(i)
                }
                for (i in 1..15) {
                    arraylist2.add(i)
                }
                arraylist2.add(0)

            }
            "5x5" -> {
                sizevalueFloat = 5f
                sizevalueInt = 5
                gameNum = 25
                for (i in 1..24) {
                    arraylist.add(i)
                }
                arraylist.add(0)
                for (i in 1..25) {
                    examplelist.add(i)
                }
                for (i in 1..24) {
                    arraylist2.add(i)
                }
                arraylist2.add(0)
            }

        }
        difficulty = when (Gmdifficulty) {
            "easy" -> true
            else -> false
        }


        CoroutineScope(mainDispatcher).launch {
            delay(1000L)
            genImg()
            genExample()
            genRandom()
            genQuest()


            Log.e("rest", "${1 % 2}")
            Log.e("rest", "${4 % 2}")
            Log.e("rest", "${7 % 2}")
            Log.e("rest", "${2 % 2}")
            Log.e("rest", "${5 % 2}")
            Log.e("rest", "${8 % 2}")
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

    private fun genImg() {
        val originImgView = BitmapFactory.decodeResource(resources, R.drawable.pricess)
        //imgList.add(originImgView)
        for (i in 0 until gameNum) {
            val cropImgView = Bitmap.createBitmap(
                originImgView,
                originImgView.width / sizevalueInt * (i % sizevalueInt),
                originImgView.height / sizevalueInt * (i / sizevalueInt),
                originImgView.width / sizevalueInt,
                originImgView.height / sizevalueInt
            )
            Log.e("xx", "${i % sizevalueInt}")
            Log.e("width", "${originImgView.width }")
            Log.e("height", "${originImgView.height}")
            imgList.add(cropImgView)
        }

    }


    private fun genRandom() {

        for (i in 0..1000) {
            nullNum = arraylist.indexOf(0)
            Log.e("nullNum", "${nullNum}")
            Log.e("imgList", "${imgList}")
            val nullNumImg = imgList[nullNum]
            Log.e("nullNum", "${nullNumImg}")
            when (Random().nextInt(4)) {

                UP -> {
                    if (nullNum - sizevalueInt >= 0) {
                        Log.e("up", "up")
                        arraylist[nullNum] = arraylist[nullNum - sizevalueInt]
                        arraylist[nullNum - sizevalueInt] = 0
                        imgList[nullNum] = imgList[nullNum - sizevalueInt]
                        imgList[nullNum - sizevalueInt] = nullNumImg
                    } else {
                        Log.e("up", "down")
                        arraylist[nullNum] = arraylist[nullNum + sizevalueInt]
                        arraylist[nullNum + sizevalueInt] = 0
                        imgList[nullNum] = imgList[nullNum + sizevalueInt]
                        imgList[nullNum + sizevalueInt] = nullNumImg
                    }


                }
                DOWN -> {
                    if (nullNum + sizevalueInt < gameNum) {
                        Log.e("down", "down")
                        arraylist[nullNum] = arraylist[nullNum + sizevalueInt]
                        arraylist[nullNum + sizevalueInt] = 0
                        imgList[nullNum] = imgList[nullNum + sizevalueInt]
                        imgList[nullNum + sizevalueInt] = nullNumImg
                    } else {
                        Log.e("down", "up")
                        arraylist[nullNum] = arraylist[nullNum - sizevalueInt]
                        arraylist[nullNum - sizevalueInt] = 0
                        imgList[nullNum] = imgList[nullNum - sizevalueInt]
                        imgList[nullNum - sizevalueInt] = nullNumImg
                    }

                }
                LEFT -> {
                    Log.e("leftnum", "${nullNum % sizevalueInt}")
                    if (nullNum % sizevalueInt != 0) {
                        Log.e("left", "left")
                        Log.e("check", "${nullNum % sizevalueInt}")
                        arraylist[nullNum] = arraylist[nullNum - 1]
                        arraylist[nullNum - 1] = 0
                        imgList[nullNum] = imgList[nullNum - 1]
                        imgList[nullNum - 1] = nullNumImg
                    } else {
                        Log.e("left", "right")
                        arraylist[nullNum] = arraylist[nullNum + 1]
                        arraylist[nullNum + 1] = 0
                        imgList[nullNum] = imgList[nullNum + 1]
                        imgList[nullNum + 1] = nullNumImg
                    }
                }
                RIGHT -> {
                    if (nullNum % sizevalueInt != sizevalueInt - 1) {
                        Log.e("true1", "True")
                        Log.e("dd", "${nullNum % sizevalueInt}")
                        arraylist[nullNum] = arraylist[nullNum + 1]
                        arraylist[nullNum + 1] = 0
                        imgList[nullNum] = imgList[nullNum + 1]
                        imgList[nullNum + 1] = nullNumImg
                    } else {
                        Log.e("true2", "True")
                        Log.e("dd1", "${nullNum % sizevalueInt}")
                        arraylist[nullNum] = arraylist[nullNum - 1]
                        arraylist[nullNum - 1] = 0
                        imgList[nullNum] = imgList[nullNum - 1]
                        imgList[nullNum - 1] = nullNumImg
                    }

                }
            }


        }

    }


    private fun genExample() {
        changeSize = binding.exampleLayout.width / sizevalueFloat

        for (count in 0 until examplelist.size) {
            val questLayout = GameLayout(this)
            questLayout.setQuestNum(examplelist[count], difficulty).setQuestImg(imgList[count], difficulty)
                .setXY(changeSize!! * (count % sizevalueInt), changeSize!! * (count / sizevalueInt))

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

        for (count in 0 until arraylist.size) {
            val questLayout = GameLayout(this)
            Log.e("array", "${arraylist}")

            questLayout.setQuestNum(arraylist[count], difficulty).setQuestImg(imgList[count], difficulty)
                .setXY(changeSize!! * (count % sizevalueInt), changeSize!! * (count / sizevalueInt))

            questLayout.setFinally()
            questLayout.cardVisible()
            binding.questAreaLayout.addView(questLayout)
            questLayout.setTxtSize(changeSize!! / 2.8f)
            questLayout.layoutParams.width = changeSize!!.toInt()
            questLayout.layoutParams.height = changeSize!!.toInt()

        }




        for (j in 0 until arraylist.size) {
            val questLayout = binding.questAreaLayout.getChildAt(j) as GameLayout
            binding.showNumberTxtView.text = mCurrentNum.toString()
            numquestLayout.add(questLayout)
            questLayout.tag = arraylist[j]


            questLayout.setOnClickListener {
                nullNum = arraylist.indexOf(0)
                nullGameLayout = numquestLayout[nullNum]
                clickPos = arraylist.indexOf(it.tag.toString().toInt())
                nullX = numquestLayout[nullNum].x
                nullY = numquestLayout[nullNum].y
                Log.e("nullX0", "${nullX}")
                Log.e("nullY0", "${nullY}")
                Log.e("clickPos", "${clickPos}")
                Log.e("nullNum", "${nullNum}")
                Log.e("ss", "${(clickPos + 1) % sizevalueInt}")
                //왼쪽
                if (nullNum == clickPos - 1 && clickPos % sizevalueInt != 0) {
                    arraylist[nullNum] = arraylist[clickPos]
                    arraylist[clickPos] = 0
                    initAnim(numquestLayout[nullNum], numquestLayout[clickPos].x, true, true)
                    initAnim(numquestLayout[clickPos], nullX, true, false)
                    //오른쪽
                } else if (nullNum == clickPos + 1 && (clickPos + 1) % sizevalueInt != 0) {
                    // clickPos에 1을 더해줘야 2,5,8 숫자 안바뀜
                    arraylist[nullNum] = arraylist[clickPos]
                    arraylist[clickPos] = 0
                    initAnim(numquestLayout[nullNum], numquestLayout[clickPos].x, true, true)
                    initAnim(numquestLayout[clickPos], nullX, true, false)
                    //위
                } else if (clickPos - sizevalueInt == nullNum) {
                    arraylist[nullNum] = arraylist[clickPos]
                    arraylist[clickPos] = 0
                    initAnim(numquestLayout[nullNum], numquestLayout[clickPos].y, false, true)
                    initAnim(numquestLayout[clickPos], nullY, false, false)
                    //아래
                } else if (clickPos + sizevalueInt == nullNum) {
                    arraylist[nullNum] = arraylist[clickPos]
                    arraylist[clickPos] = 0
                    initAnim(numquestLayout[nullNum], numquestLayout[clickPos].y, false, true)
                    initAnim(numquestLayout[clickPos], nullY, false, false)
                }


            }
        }

    }

    private fun initAnim(moveLayout: View, Value: Float, xAxis: Boolean, what: Boolean) {
        val propertyName = if (xAxis) {
            "translationX"
        } else {
            "translationY"
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        Log.e("mcurrentNum", "${mCurrentNum}")
        ObjectAnimator.ofFloat(moveLayout, propertyName, Value)
            .apply {
                duration = 300
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        if (!what) {
                            numquestLayout[nullNum] = numquestLayout[clickPos]
                            numquestLayout[clickPos] = nullGameLayout
                            mCurrentNum++
                            binding.showNumberTxtView.text = mCurrentNum.toString()
                            Log.e("what1", "${what1}")
                            if (arraylist == arraylist2) {
                                binding.moveTxtView.text = "끝"
                                for (j in 0 until arraylist.size) {
                                    val questLayout =
                                        binding.questAreaLayout.getChildAt(j) as GameLayout
                                    questLayout.setOnClickListener(null)
                                }
                            }
                        }
                        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    }
                })
                start()
            }


    }

}