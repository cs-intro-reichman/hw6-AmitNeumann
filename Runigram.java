import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	   
		//Color[][] image = read("tinypic.ppm"); // Replace with the path to your PPM file
		//print(image); // Print the image data
		
		
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		//Color[][] tinypic = read("tinypic.ppm");
		//print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		//Color[][] image;

		// Tests the horizontal flipping of an image:
		//image = flippedHorizontally(tinypic);
		//System.out.println();
		//print(image);

		//image = flippedVertically(tinypic);
		//System.out.println();
		//print(image);
		
		// Scale the image to 2x2
		//Color[][] scaledImage = scaled(image, 2, 2);
	
		//// Write here whatever code you need in order to test your work.
		//// You can continue using the image array.
	Color c1 = new Color(255, 0, 0); // Red
    Color c2 = new Color(0, 0, 255); // Blue
    double alpha = 0.5; // Equal blend

    Color blended = blend(c1, c2, alpha);

    System.out.println("Blended Color: (" + blended.getRed() + ", " 
                       + blended.getGreen() + ", " + blended.getBlue() + ")");

	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		// Reads the RGB values from the file into the image array. 
		// For each pixel (i,j), reads 3 values from the file,
		// creates from the 3 colors a new Color object, and 
		// makes pixel (i,j) refer to that object.
		for(int i=0;i<image.length;i++){
			for(int j=0;j<image[i].length;j++){
				int red=in.readInt();
				int green=in.readInt();
				int blue=in.readInt();
				image[i][j]=new Color(red,green,blue);
			}
		}
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
		//// Replace this comment with your code
		//// Notice that all you have to so is print every element (i,j) of the array using the print(Color) function.
		for(int i=0;i<image.length;i++){
			for(int j=0;j<image[i].length;j++){
				print(image[i][j]);
			}
			System.out.println();
		}
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		Color[][] flipped=new Color[image.length][image[0].length];
		int k;
		for(int i=0;i<flipped.length;i++){ // run on the rows
			int length=image[i].length-1;
			k=0;
			for(int j=length;j>=0;j--){ //runs from the end of the row
				flipped[i][k]=image[i][j];
				k++;
			}
		}
		return flipped;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){
		Color[][] flipped=new Color[image.length][image[0].length];
		int k=image.length-1;
		for(int i=0;i<image.length;i++){ // run on the rows
			for(int j=0;j<image[i].length;j++){ //run on the colums
				flipped[i][j]=image[k][j];
			}
			k--;
		}
		return flipped;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	private static Color luminance(Color pixel) {
		int r = pixel.getRed();
		int g = pixel.getGreen();
		int b = pixel.getBlue();
		int lum = (int) (0.299 * r + 0.587 * g + 0.114 * b);
		return new Color(lum, lum, lum);	
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		Color[][] grey=new Color[image.length][image[0].length];
		for(int i=0;i<image.length;i++){ // run on the rows
			for(int j=0;j<image[i].length;j++){ //run on the colums
				grey[i][j]=luminance(image[i][j]);
			}
		}
		return grey;
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		Color[][] resized=new Color[height][width];
		int imageH=image.length, imageW=image[0].length;
		for(int i=0;i<height;i++){ // run on the rows of target
			for(int j=0;j<width;j++){ //run on the colums og target
				int sourceRow=(int)(i*(imageH/(double)(height)));
				int sourceCol=(int)(j*(imageW/(double)(width)));
				resized[i][j]=image[sourceRow][sourceCol];
			}
		}

		return resized;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		if (alpha < 0 || alpha > 1) {
			throw new IllegalArgumentException("Alpha must be between 0 and 1.");
		} else {
						int r1,r2,g1,g2,b1,b2;
						r1=c1.getRed();
						r2=c2.getRed();
						g1=c1.getGreen();
						g2=c2.getGreen();
						b1=c1.getBlue();
						b2=c2.getBlue();
						int newR,newG,newB;
						newR=(int)((alpha*(double)(r1))+((1-alpha)*(double)(r2)));
						newG=(int)((alpha*(double)(g1))+((1-alpha)*(double)(g2)));
						newB=(int)((alpha*(double)(b1))+((1-alpha)*(double)(b2)));
						Color newColor=new Color(newR,newG,newB);
						return newColor;
		}
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		Color[][] blended=new Color[image1.length][image1[0].length];
		if (alpha < 0 || alpha > 1) {
            throw new IllegalArgumentException("Alpha must be between 0 and 1.");
        } else {
            for(int i=0;i<image1.length;i++){ // run on the rows
                for(int j=0;j<image1[i].length;j++){ //run on the colums
                    blended[i][j]=blend(image1[i][j], image2[i][j], alpha);
                }
            }
        }

		return blended;
	}


	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		if (n <= 0) {
			System.out.println("Error: Number of steps must be greater than 0.");
			;
		} else {
			Color[][] target1;
			if(source.length!=target.length||source[0].length!=target[0].length){ // doesnt have the same dimensions
				target1=scaled(target, source[0].length, source.length);
			} else {
				target1=target;
			}
			Color[][] blended=new Color[target1.length][target1[0].length];
			Runigram.setCanvas(source);
			Runigram.display(source);
			StdDraw.pause(500); 
			for(int k=n;k>=0;k--){ // runs on the n 
				blended=blend(source, target1, k/(double)(n));
				Runigram.setCanvas(blended);
				Runigram.display(blended);
				StdDraw.pause(500); 
				}

		}

	}
	
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

