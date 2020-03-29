package ru.geekbrains.aleksey.math;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

/**
 * Rectangle
 */

public class Rect {

    public final Vector2 POS = new Vector2();
    protected float halfWidth;
    protected float halfHeight;

    public Rect() {

    }

    public Rect(float x, float y, float halfWidth, float halfHeight) {
        POS.set(x, y);
        this.halfWidth = halfWidth;
        this.halfHeight= halfHeight;
    }

    public Rect (Rect from) {
        this(from.POS.x, from.POS.y, from.getHalfWidth(), from.getHalfHeight());
    }

    public float getLeft() {
        return POS.x - halfWidth;
    }

    public float getRight() {
        return POS.x + halfWidth;
    }

    public float getTop() {
        return POS.y + halfHeight;
    }

    public float getBottom() {
        return POS.y - halfHeight;
    }


    public float getHalfWidth() {
        return halfWidth;
    }

    public float getHalfHeight() {
        return halfHeight;
    }

    public float getWidth() {
        return halfWidth * 2f;
    }

    public float getHeight() {
        return halfHeight * 2f;
    }

    public void set (Rect from) {
        POS.set(from.POS);
        halfWidth = from.halfWidth;
        halfHeight = from.halfHeight;
    }

    public void setLeft (float left) {
        POS.x = left + halfWidth;
    }
    public void setRight (float right) {
        POS.x = right - halfWidth;
    }
    public void setTop (float top) {
        POS.y = top - halfHeight;
    }
    public void setBottom (float bottom) {
        POS.y = bottom + halfHeight;
    }
    public void setWidth (float width) {
        this.halfWidth = width / 2f;
    }
    public void setHeight (float height) {
        this.halfHeight = height / 2f;
    }

    public void setSize (float width, float height) {
        this.halfWidth = width / 2f;
        this.halfHeight = height / 2f;
    }

    public boolean isMe (Vector2 touch) {
        return touch.x >= getLeft() && touch.x <= getRight()
                && touch.y >= getBottom() && touch.y <= getTop();
    }

    public boolean isOutside (Rect other) {
        return getLeft() > other.getRight() || getRight() < other.getLeft()
                || getBottom() > other.getTop() || getTop() < other.getBottom();
    }

    @Override
    public String toString() {
        return "Rectangle: pos" + POS + " size(" + getWidth() + ", " + getHeight() + ")";
    }

}
