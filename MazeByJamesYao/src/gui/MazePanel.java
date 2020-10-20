package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.awt.Font;

/**
 * Add functionality for double buffering to an AWT Panel class.
 * Used for drawing a maze.
 * 
 * @author Peter Kemper
 *
 */
public class MazePanel extends Panel implements P5Panel {
	private static final long serialVersionUID = 2787329533730973905L;
	/* Panel operates a double buffer see
	 * http://www.codeproject.com/Articles/2136/Double-buffer-in-standard-Java-AWT
	 * for details
	 */
	// bufferImage can only be initialized if the container is displayable,
	// uses a delayed initialization and relies on client class to call initBufferImage()
	// before first use
	private Image bufferImage;  
	private Graphics2D graphics; // obtained from bufferImage, 
	// graphics is stored to allow clients to draw on the same graphics object repeatedly
	// has benefits if color settings should be remembered for subsequent drawing operations
	
	/**
	 * Constructor. Object is not focusable.
	 */
	public MazePanel() {
		setFocusable(false);
		bufferImage = null; // bufferImage initialized separately and later
		graphics = null;	// same for graphics
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	/**
	 * Method to draw the buffer image on a graphics object that is
	 * obtained from the superclass. 
	 * Warning: do not override getGraphics() or drawing might fail. 
	 */
	public void update() {
		paint(getGraphics());
	}
	
	/**
	 * Draws the buffer image to the given graphics object.
	 * This method is called when this panel should redraw itself.
	 * The given graphics object is the one that actually shows 
	 * on the screen.
	 */
	@Override
	public void paint(Graphics g) {
		if (null == g) {
			System.out.println("MazePanel.paint: no graphics object, skipping drawImage operation");
		}
		else {
			g.drawImage(bufferImage,0,0,null);	
		}
	}

	/**
	 * Obtains a graphics object that can be used for drawing.
	 * This MazePanel object internally stores the graphics object 
	 * and will return the same graphics object over multiple method calls. 
	 * The graphics object acts like a notepad where all clients draw 
	 * on to store their contribution to the overall image that is to be
	 * delivered later.
	 * To make the drawing visible on screen, one needs to trigger 
	 * a call of the paint method, which happens 
	 * when calling the update method. 
	 * @return graphics object to draw on, null if impossible to obtain image
	 */
	public Graphics getBufferGraphics() {
		// if necessary instantiate and store a graphics object for later use
		if (null == graphics) { 
			if (null == bufferImage) {
				bufferImage = createImage(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
				if (null == bufferImage)
				{
					System.out.println("Error: creation of buffered image failed, presumedly container not displayable");
					return null; // still no buffer image, give up
				}		
			}
			graphics = (Graphics2D) bufferImage.getGraphics();
			if (null == graphics) {
				System.out.println("Error: creation of graphics for buffered image failed, presumedly container not displayable");
			}
			else {
				// System.out.println("MazePanel: Using Rendering Hint");
				// For drawing in FirstPersonDrawer, setting rendering hint
				// became necessary when lines of polygons 
				// that were not horizontal or vertical looked ragged
				setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			}
		}
		return graphics;
	}

	@Override
	public void commit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isOperational() {
		if (graphics != null) {
			return true;
		}
		return false;
	}

	private Color col;
	
	/**
	 * @param rgb value of color
	 */
	@Override
	public void setColor(int rgb) {
		col = new Color(rgb);
		graphics.setColor(col);
	}
	
	/**
	 * sets Color with rgba value
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setColor(float[] rgba) {
		col = new Color(rgba[0], rgba[1], rgba[2], rgba[3]);
		graphics.setColor(col);
	}
	
	/**
	 * @return rgb value of color
	 */
	@Override
	public int getColor() {
		return col.getRGB();
	}
	
	/**
	 * @param color to be decoded
	 * @return rgb value of decoded color
	 */
	public static int getColor(String decode) {
		return Color.decode(decode).getRGB();
	}
	
	public enum CommonColors {
		WHITE,
		GRAY,
		BLACK,
		RED,
		YELLOW
	}
	/**
	 * @param enumerated color
	 * @return
	 */
	public static int getColor(CommonColors color) {
		switch (color) {
		case WHITE:
			return Color.white.getRGB();
		case GRAY:
			return Color.gray.getRGB();
		case BLACK:
			return Color.black.getRGB();
		case RED:
			return Color.red.getRGB();
		case YELLOW:
			return Color.yellow.getRGB();
		}
		return 0;
	}
	
	/**
	 * 
	 * @param fontCode
	 * @return font name
	 */
	public static String getFontName(String fontCode) {
		return Font.decode(fontCode).getFontName();
	}
	
	/**
	 * 
	 * @param fontCode
	 * @return font style int
	 */
	public static int getFontStyle(String fontCode) {
		return Font.decode(fontCode).getStyle();
	}
	
	/**
	 * 
	 * @param fontCode
	 * @return font size
	 */
	public static int getFontSize(String fontCode) {
		return Font.decode(fontCode).getSize();
	}
	
	// private Font fields
	private String fontName;
	private int fontStyle;
	private int fontSize;
	
	public void setFontName(String fn) {
		fontName = fn;
	}
	
	public void setFontStyle(int fstyle) {
		fontStyle = fstyle;
	}
	
	public void setFontSize(int fsize) {
		fontSize = fsize;
	}

	/**
     * Default minimum value for RGB values.
     * For Wall class
     */
    private static final int RGB_DEF = 20;
    private static final int RGB_DEF_GREEN = 60;
    
	@Override
	public int getWallColor(int distance, int cc, int extensionX) {
		// TODO Auto-generated method stub
		// compute rgb value, depends on distance and x direction
        final int part1 = distance & 7;
        final int add = (extensionX != 0) ? 1 : 0;
        final int rgbValue = ((part1 + 2 + add) * 70) / 8 + 80;
        Color col;
        switch (((distance >> 3) ^ cc) % 6) {
        case 0:
            col = new Color(rgbValue, RGB_DEF, RGB_DEF);
            break;
        case 1:
            col = new Color(RGB_DEF, RGB_DEF_GREEN, RGB_DEF);
            break;
        case 2:
            col = new Color(RGB_DEF, RGB_DEF, rgbValue);
            break;
        case 3:
            col = new Color(rgbValue, RGB_DEF_GREEN, RGB_DEF);
            break;
        case 4:
            col = new Color(RGB_DEF, RGB_DEF_GREEN, rgbValue);
            break;
        case 5:
            col = new Color(rgbValue, RGB_DEF, rgbValue);
            break;
        default:
            col = new Color(RGB_DEF, RGB_DEF, RGB_DEF);
            break;
        }
		return col.getRGB();
	}
	
	/**
	 * Draws two solid rectangles to provide a background.
	 * Note that this also erases previous drawings of maze or map.
	 * The color setting adjusts to the distance to the exit to 
	 * provide an additional clue for the user.
	 * Colors transition from black to gold and from grey to green.
	 * @param percentToExit gives the distance to exit
	 */
	@Override
	public void addBackground(float percentToExit) {
		// TODO Auto-generated method stub
		// black rectangle in upper half of screen
		graphics.setColor(getBackgroundColor(percentToExit, true));
		graphics.fillRect(0, 0, Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT/2);
		// grey rectangle in lower half of screen
		graphics.setColor(getBackgroundColor(percentToExit, false));
		graphics.fillRect(0, Constants.VIEW_HEIGHT/2, Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT/2);
	}
	
	// colors for background
	static final Color greenWM = Color.decode("#115740");
	static final Color goldWM = Color.decode("#916f41");
	static final Color yellowWM = Color.decode("#FFFF99");
	/**
	 * Determine the background color for the top and bottom
	 * rectangle as a blend between starting color settings
	 * of black and grey towards gold and green as final
	 * color settings close to the exit
	 * @param percentToExit
	 * @param top is true for the top triangle, false for the bottom
	 * @return the color to use for the background rectangle
	 */
	private Color getBackgroundColor(float percentToExit, boolean top) {
		return top? blend(yellowWM, goldWM, percentToExit) : 
			blend(Color.lightGray, greenWM, percentToExit);
	}
	
	/**
	 * Calculates the weighted average of the two given colors
	 * @param c0 the one color
	 * @param c1 the other color
	 * @param weight0 of c0
	 * @return blend of colors c0 and c1 as weighted average
	 */
	private Color blend(Color c0, Color c1, double weight0) {
		if (weight0 < 0.1)
			return c1;
		if (weight0 > 0.95)
			return c0;
	    double r = weight0 * c0.getRed() + (1-weight0) * c1.getRed();
	    double g = weight0 * c0.getGreen() + (1-weight0) * c1.getGreen();
	    double b = weight0 * c0.getBlue() + (1-weight0) * c1.getBlue();
	    double a = Math.max(c0.getAlpha(), c1.getAlpha());

	    return new Color((int) r, (int) g, (int) b, (int) a);
	  }

	@Override
	public void addFilledRectangle(int x, int y, int width, int height) {
		graphics.fillRect(x, y, width, height);
	}

	@Override
	public void addFilledPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		graphics.fillPolygon(xPoints, yPoints, nPoints);
		
	}

	@Override
	public void addPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		graphics.drawPolygon(xPoints, yPoints, nPoints);
	}

	@Override
	public void addLine(int startX, int startY, int endX, int endY) {
		graphics.drawLine(startX, startY, endX, endY);
	}

	@Override
	public void addFilledOval(int x, int y, int width, int height) {
		graphics.fillOval(x, y, width, height);
	}

	@Override
	public void addArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
		graphics.drawArc(x, y, width, height, startAngle, arcAngle);
	}

	@Override
	public void addMarker(float x, float y, String str) {
		Font f = new Font(fontName, fontStyle, fontSize);
		GlyphVector gv = f.createGlyphVector(graphics.getFontRenderContext(), str);
        Rectangle2D rect = gv.getVisualBounds();
        
        x -= rect.getWidth() / 2;
        y += rect.getHeight() / 2;
        
        graphics.drawGlyphVector(gv, x, y);
		
	}

	/**
	 * uses the java.awt.RenderingHints keys and values that correspond to the enumerated values
	 * @param hintKey an enumerated RenderingHints key
	 * @param hintValue an enumerated RenderingHints value
	 */
	@Override
	public void setRenderingHint(RenderingHints hintKey, RenderingHints hintValue) {
		switch (hintKey) {
		case KEY_RENDERING:
			if (hintValue == RenderingHints.VALUE_ANTIALIAS_ON) {
				graphics.setRenderingHint(java.awt.RenderingHints.KEY_RENDERING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
			}
			else if (hintValue == RenderingHints.VALUE_INTERPOLATION_BILINEAR) {
				graphics.setRenderingHint(java.awt.RenderingHints.KEY_RENDERING, java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			}
			else {
				graphics.setRenderingHint(java.awt.RenderingHints.KEY_RENDERING, java.awt.RenderingHints.VALUE_RENDER_QUALITY);
			}
			break;
		case KEY_INTERPOLATION:
			if (hintValue == RenderingHints.VALUE_ANTIALIAS_ON) {
				graphics.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
			}
			else if (hintValue == RenderingHints.VALUE_INTERPOLATION_BILINEAR) {
				graphics.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION, java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			}
			else {
				graphics.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION, java.awt.RenderingHints.VALUE_RENDER_QUALITY);
			}
			break;
		case KEY_ANTIALIASING:
			if (hintValue == RenderingHints.VALUE_ANTIALIAS_ON) {
				graphics.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
			}
			else if (hintValue == RenderingHints.VALUE_INTERPOLATION_BILINEAR) {
				graphics.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			}
			else {
				graphics.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_RENDER_QUALITY);
			}
			break;
		default:
			break;
		}
		
	}

}
