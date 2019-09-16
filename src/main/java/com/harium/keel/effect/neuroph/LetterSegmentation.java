package com.harium.keel.effect.neuroph;

import com.harium.etyl.commons.math.Vector2i;
import com.harium.keel.core.Modifier;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Mihailo Stupar
 * <p>
 * Code from: https://github.com/neuroph/neuroph/blob/master/neuroph-2.9/ImageRec/src/main/java/org/neuroph/imgrec/filter/impl/LetterSegmentationFilter.java
 */
public class LetterSegmentation implements Modifier<ImageSource, List<ImageSource>> {

    int letterWidth = 80;
    int letterHeight = 80;
    private boolean[][] visited;

    @Override
    public List<ImageSource> apply(ImageSource input) {
        List<ImageSource> output = new ArrayList<>();

        ImageSource originalImage = input;
        int width = input.getWidth();
        int height = input.getHeight();
        visited = new boolean[width][height];

        int letterIndex = 1;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                int color = originalImage.getGray(i, j);
                if (color == 255) {
                    visited[i][j] = true;
                } else {
                    // Why?
                    if (letterIndex > 3000) {
                        return output;
                    }
                    ImageSource letter = BFS(i, j, originalImage);
                    output.add(letter);
                    letterIndex++;
                }

            }
        }

        return output;
    }

    /**
     * Breadth first search
     */
    public ImageSource BFS(int startI, int startJ, /*String imageName*/ImageSource originalImage) {
        LinkedList<Vector2i> queue = new LinkedList<>();

        //=============================================================================

        int gapX = 30;
        int gapY = 30;

        MatrixSource letter = new MatrixSource(letterWidth, letterHeight);

        //BufferedImage letter = new BufferedImage(letterWidth, letterHeight, BufferedImage.TYPE_BYTE_BINARY);
        int alpha = originalImage.getA(startI, startJ);
        int white = ColorHelper.getARGB(255, 255, 255, alpha);
        int black = ColorHelper.getARGB(0, 0, 0, alpha);

        for (int j = 0; j < letterHeight; j++) {
            for (int i = 0; i < letterWidth; i++) {
                letter.setRGB(i, j, white);
            }
        }
        //=============================================================================

        int count = 0;
        Vector2i positions = new Vector2i(startI, startJ);
        visited[startI][startJ] = true;
        queue.addLast(positions);

        while (!queue.isEmpty()) {
            Vector2i pos = queue.removeFirst();

            int x = pos.x;
            int y = pos.y;
            visited[x][y] = true;

            //set black pixel to letter image===================================
            int posX = startI - x + gapX;
            int posY = startJ - y + gapY;

            count++;

            if(posX>=originalImage.getWidth() || posY>=originalImage.getHeight()) {
                continue;
            } else {
                letter.setRGB(posX, posY, black);
                /*try {
                    letter.setRGB(posX, posY, black);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("posX " + posX);
                    System.out.println("posY " + posY);
                    System.out.println("letterWidth " + letter.getWidth());
                    System.out.println("letterHeight " + letter.getHeight());
                    throw e;
                }*/
            }

            //==================================================================
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if (i >= 0 && j >= 0 && i < originalImage.getWidth() && j < originalImage.getHeight()) {
                        if (!visited[i][j]) {
                            int color = originalImage.getGray(i, j);
                            if (color < 10) {
                                visited[i][j] = true;
                                Vector2i tmpPos = new Vector2i(i, j);
                                queue.addLast(tmpPos);
                            }
                        }
                    }
                } //i
            } //j
        }

        System.out.println("count = " + count);
        //save letter===========================================================
        if (count < 3) {
            return letter;
        }
        /*try {
            saveToFile(letter, imageName);
            //
        } catch (IOException ex) {
            ex.printStackTrace();
        }*/

        return letter;
    }

    /*public void saveToFile(BufferedImage img, String name) throws FileNotFoundException, IOException {

        File outputfile = new File("C:/Users/Mihailo/Documents/NetBeansProjects/ImagePreprocessing/Segmented_letters/" + name + ".jpg");
        ImageIO.write(img, "jpg", outputfile);
    }*/

}

