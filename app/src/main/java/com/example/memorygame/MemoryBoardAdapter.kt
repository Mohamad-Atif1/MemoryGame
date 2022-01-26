package com.example.memorygame

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.memorygame.models.BoardSize
import com.example.memorygame.utils.Card
import com.google.android.material.snackbar.Snackbar
import java.util.ArrayList
import kotlin.math.min

class MemoryBoardAdapter(
    private val context: Context,
    private val boardSize: BoardSize,
    private val randomImages: ArrayList<Card>,
    private val tvNumMoves:TextView,
    private val tvNumPairs:TextView
):RecyclerView.Adapter<MemoryBoardAdapter.ViewHolder>() {
    private var count = 0
    private var savePreviewsPosition = -1
    private var numPairs = 0
    private var numMoves = 0
    // static
    companion object{
        private const val MARGIN_SIZE = 10


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardWidth = parent.width / boardSize.getWidth() -(MARGIN_SIZE*2)
        val cardHeight = parent.height / boardSize.getHeight() -(MARGIN_SIZE*2)
        val cardSlideLength = min(cardWidth,cardHeight)
        val view = LayoutInflater.from(context).inflate(R.layout.card_list_item , parent , false)
        //LayoutParams are used by views to tell their parents how they want to be laid out.
        // to determine the size of the view
        // "as" is for casting , MarginLayoutParams is for setting margin for layoutParams
        val layoutParams = view.findViewById<CardView>(R.id.cardViewPa).layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.height = cardSlideLength
        layoutParams.width = cardSlideLength
        layoutParams.setMargins(MARGIN_SIZE,MARGIN_SIZE,MARGIN_SIZE,MARGIN_SIZE)
        return ViewHolder(view)
    }
    //done // اول شي لازم المستخدم مايقدر يقفل البطاقة بعد ما يفتحها
    // ثاني شي نحدد اذا الكرتين متشابهين او لا , وممكن نستخدم متغير كاونت لمن يوصل ل2 يشيك وبعدين يصير 0
    // اذا كانوا الاثنين متطابقين ياخذ نقطة و تبقى الكرتين مفتوحة
    // اذا ما كانت متطابقتين ترجع تتقفل
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // holder.imageButton.setImageResource(randomImages.get(position).id)
        if(!randomImages.get(position).isFaceUp){
            holder.imageButton.setImageResource(R.drawable.ic_launcher_background)
        }
        else{
            holder.imageButton.setImageResource(randomImages.get(position).id)
        }

        if(randomImages.get(position).isMatched && randomImages.get(position).isFaceUp){
         ViewCompat.setBackgroundTintList(holder .imageButton,ContextCompat.getColorStateList(context,
             R.color.gray
         ))
           holder.imageButton.alpha = 0.5f
        }
        else{
            ViewCompat.setBackgroundTintList(holder.imageButton,ContextCompat.getColorStateList(context,R.color.white))
            holder.imageButton.alpha = 1.0f
        }


        holder.imageButton.setOnClickListener {



            if(!randomImages.get(position).isFaceUp){
                tvNumMoves.text = "Moves:" + (++numMoves).toString()

                if (count == 0)
                    notifyDataSetChanged()

                randomImages.get(position).isFaceUp = !(randomImages.get(position).isFaceUp)

                if(count !=1) {
                    savePreviewsPosition = position
                    count++
                }
                else{
                    holder.imageButton.setImageResource(randomImages.get(position).id)
                    count = 0
                    if(randomImages.get(savePreviewsPosition).id== randomImages.get(position).id){
                       tvNumPairs.text = "Pairs:" +  (++numPairs).toString() + " / ${boardSize.getNumPairs()}"
                        Toast.makeText(context,"it actually works!!  $numPairs"   ,Toast.LENGTH_SHORT).show()
                        randomImages.get(position).isMatched = true
                        randomImages.get(savePreviewsPosition).isMatched = true
                        savePreviewsPosition=-1

                        if(win()){
                            Toast.makeText(context,"YOU WIN!!",Toast.LENGTH_LONG).show()
                            notifyDataSetChanged()
                        }

                    }

                    else{

                        randomImages.get(savePreviewsPosition).isFaceUp = !randomImages.get(savePreviewsPosition).isFaceUp
                        randomImages.get(position).isFaceUp = !randomImages.get(position).isFaceUp

                        savePreviewsPosition=-1

                    }
                }


            }

        }



    }



    override fun getItemCount(): Int {
        return boardSize.numCards
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
         val imageButton:ImageButton = itemView.findViewById(R.id.imageButton)

    }
    private fun win(): Boolean {
        return numPairs == boardSize.getNumPairs()
    }
}

