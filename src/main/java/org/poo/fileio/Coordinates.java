package org.poo.fileio;


public final class Coordinates {
   private int x, y;

   public Coordinates() { };

   public int getX() {
      return x;
   }

   public void setX(final int x) {
      this.x = x;
   }

   public int getY() {
      return y;
   }

   public void setY(final int y) {
      this.y = y;
   }

   /**
    *
    * @param xNew
    */
   public void addX(final int xNew) {
      this.x += xNew;
   }

   @Override
   public String toString() {
      return "Coordinates{"
              + "x="
              + x
              + ", y="
              + y
              + '}';
   }
}
