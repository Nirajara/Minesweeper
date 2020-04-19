package minesweeper;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Icons {

    public static class Flag {

        private static Flag instance = new Flag();

        private ImageIcon flag;
        private int flagDimension;

        private Flag() {
            scaleImage(20);
        }

        public static Flag getInstance() {
            return instance;
        }

        public ImageIcon getFlagImage(int dimension) {
            scaleImage(dimension);
            return getFlagImage();
        }

        public ImageIcon getFlagImage() {
            return flag;
        }

        private void scaleImage(int newDimension) {

            if (flagDimension != newDimension) {
                
                loadImageFile();
                flagDimension = newDimension;
                flag = new ImageIcon(flag.getImage().getScaledInstance(flagDimension, flagDimension, Image.SCALE_SMOOTH));

            }

        }

        private void loadImageFile() {

            try {
                flag = new ImageIcon(ImageIO.read(new File("images\\flag.png")));
            } catch (Exception e) {
                System.out.println("Flag Image Error: " + e);
            }

        }
        
    }

    public static class Mine {

        private static Mine instance = new Mine();

        private ImageIcon mine;
        private int mineDimension;

        private Mine() {
            scaleImage(20);
        }

        public static Mine getInstance() {
            return instance;
        }

        public ImageIcon getMineImage(int dimension) {
            scaleImage(dimension);
            return getMineImage();
        }

        public ImageIcon getMineImage() {
            return mine;
        }

        private void scaleImage(int newDimension) {

            if (mineDimension != newDimension) {

                loadImageFile();
                mineDimension = newDimension;
                mine = new ImageIcon(mine.getImage().getScaledInstance(mineDimension, mineDimension, Image.SCALE_SMOOTH));

            }

        }

        private void loadImageFile() {

            try {
                mine = new ImageIcon(ImageIO.read(new File("images\\mine.png")));
            } catch (Exception e) {
                System.out.println("Mine Image Error: " + e);
            }

        }

    }

}