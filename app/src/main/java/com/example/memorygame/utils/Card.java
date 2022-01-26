package com.example.memorygame.utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Card {
    private int id;
    private boolean isFaceUp,isMatched;


    public Card(int id, boolean isFaceUp, boolean isMatched) {
        this.id = id;
        this.isFaceUp = isFaceUp;
        this.isMatched = isMatched;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFaceUp() {
        return isFaceUp;
    }

    public void setFaceUp(boolean faceUp) {
        isFaceUp = faceUp;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }
    @NotNull
    public static ArrayList<Card> takeCard(int num, ArrayList<Card> cards){
        ArrayList<Card> takenCards = new ArrayList<>();
        for(int i = 0; i<num;i++){
            takenCards.add(new Card(cards.get(i).id,cards.get(i).isFaceUp,cards.get(i).isMatched));
        }
        return takenCards;
    }
    public static ArrayList<Card> repeatCards(ArrayList<Card>cards){
        ArrayList<Card> tempCards = new ArrayList<>();
        for(int i =0;i<cards.size();i++){
            tempCards.add(new Card(cards.get(i).id,cards.get(i).isFaceUp,cards.get(i).isMatched));
        }
        for(int i =0;i<cards.size();i++){
            tempCards.add(new Card(cards.get(i).id,cards.get(i).isFaceUp,cards.get(i).isMatched));
        }
//        for(int i =0;i<cards.size();i++){
//            cards.add(new Card(cards.get(i).id,cards.get(i).isFaceUp,cards.get(i).isMatched));
//        }
            return tempCards;
            }


}
