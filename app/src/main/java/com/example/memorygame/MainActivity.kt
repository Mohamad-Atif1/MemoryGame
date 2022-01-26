package com.example.memorygame

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memorygame.models.BoardSize
import com.example.memorygame.utils.Card
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    private lateinit var tvNumMoves:TextView
    private lateinit var tvNumPairs:TextView
    private lateinit var rvBoard:RecyclerView
    private var boardSize = BoardSize.EASY
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvNumMoves = findViewById(R.id.tvNumMoves)
        tvNumPairs = findViewById(R.id.tvNumPairs)
        rvBoard = findViewById(R.id.rvBoard)
        setupBoard()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
        R.id.refreash ->{
            val builder = AlertDialog.Builder(this)
            builder.setMessage(
                "Are you sure you want to refresh"
            )
            builder.setPositiveButton(
                "Yes"
            ) { dialog, which ->
                setupBoard()
            }
            builder.setNegativeButton(
                "Cancel"
            ) { dialog: DialogInterface?, which: Int -> }
            builder.create().show()

        }
        R.id.newSize ->{
            showNewSizeDialog()
            return true
        }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun setupBoard(){
        var cards:ArrayList<Card> = ArrayList()
        cards.add(Card(R.drawable.ic_bell,false,false) )
        cards.add(Card( R.drawable.ic_clock,false,false) )
        cards.add(Card( R.drawable.ic_eye,false,false) )
        cards.add(Card(R.drawable.ic_face,false,false) )
        cards.add(Card(R.drawable.ic_fan,false,false) )
        cards.add(Card(R.drawable.ic_flag,false,false) )
        cards.add(Card( R.drawable.ic_flower,false,false) )
        cards.add(Card(R.drawable.ic_home,false,false) )
        cards.add(Card(R.drawable.ic_like,false,false) )
        cards.add(Card(R.drawable.ic_music,false,false) )
        cards.add(Card(R.drawable.ic_star,false,false) )
        cards.add(Card(R.drawable.ic_tv,false,false) )

        cards.shuffle()
        val randomImages = Card.takeCard(boardSize.getNumPairs(),cards)

        val randomImage = Card.repeatCards(randomImages)
        randomImage.shuffle() //Collections.shuffle(randomImages)

        tvNumPairs.text = "Pairs:0/${boardSize.getNumPairs()}"
        tvNumMoves.text = "Moves:0"

        rvBoard.adapter = MemoryBoardAdapter(this,boardSize,randomImage,tvNumMoves,tvNumPairs)
        rvBoard.setHasFixedSize(true)
        rvBoard.layoutManager =  GridLayoutManager(this,boardSize.getWidth())
    }

    private fun showNewSizeDialog(){
        val boardSizeView = LayoutInflater.from(this).inflate(R.layout.dialog_board_size,null)
        val rgSizeBoard = boardSizeView.findViewById<RadioGroup>(R.id.rgBoardSize)
        when(boardSize){
            BoardSize.EASY -> rgSizeBoard.check(R.id.rbEasy)
            BoardSize.MEDIUM -> rgSizeBoard.check(R.id.rbMedium)
            BoardSize.HARD -> rgSizeBoard.check(R.id.rbMedium)
        }

        AlertDialog.Builder(this)
            .setTitle("Choose new size")
            .setView(boardSizeView)
            .setNegativeButton("Cancel",null)
            .setPositiveButton("Ok") { dialog, which ->
                boardSize= when(rgSizeBoard.checkedRadioButtonId){
                    R.id.rbEasy->{BoardSize.EASY}
                    R.id.rbMedium->{BoardSize.MEDIUM}
                    else->{BoardSize.HARD}
                }
              setupBoard()
            }
            .create().show()
    }
}