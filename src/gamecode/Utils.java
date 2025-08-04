package gamecode;

import processing.core.PVector;

public class Utils {

    public static float clamp(float v, float minVal, float maxVal) {
        return Math.max(minVal, Math.min(v, maxVal));
    }

    public static boolean circleCollide(PVector a, float ar, PVector b, float br) {
        return a.dist(b) < ar + br;
    }
}
