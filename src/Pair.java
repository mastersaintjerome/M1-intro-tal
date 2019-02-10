/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gaëtan
 */
public class Pair <V, U>{
    private final V first;
    private final U second;

    public Pair(V first, U second) {
        this.first = first;
        this.second = second;
    }

    public V getFirst() { return first; }
    public U getSecond() { return second; }
}
