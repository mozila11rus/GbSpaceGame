package ru.geekbrains.aleksey.math;

import java.util.Random;

/**
 * Random number generator
 */

public class Rnd {
   private static final Random RANDOM  = new Random();

   public static float nextFloat (float min, float max) {
       return RANDOM.nextFloat() * (max - min) + min;
   }
}
