package co.gov.supernotariado.bachue.portalgeografico.model.map;

import java.io.Serializable;

/**
 * 
 * Clase que almacena el tamaño de la pantalla
 *
 */
public class Screen implements Serializable {
	private static final long serialVersionUID = 6593280787822002388L;
	
	/**
	 * Variable height
	 * int que contiene la altura.
	 */
	private int height;
	/**
	 * Variable width
	 * int que contiene la anchura.
	 */
	private int width;
	/**
	 * Variable x
	 * int que contiene el valor de la posicion en x.
	 */
	private int x;
	/**
	 * Variable width
	 * int que contiene el valor de la posicion en y.
	 */
	private int y;

	
	/**
	 * Constructor por defecto de la clase Screen
	 * 
	 */
	public Screen() {
	}

	/**
	 * Constructor con el tamaño actual de la pantalla
	 * 
	 * @param height la altura
	 * @param width la anchura
	 */
	public Screen(int height, int width) {
		this.height = height;
		this.width = width;
	}

	/**
	 * Constructor con el tamaño actual de la pantalla y posición actual del cursos
	 * 
	 * @param height la altura
	 * @param width la anchura
	 * @param x posicion en x
	 * @param y posicion en y
	 */
	public Screen(int height, int width, int x, int y) {
		this.height = height;
		this.width = width;
		this.x = x;
		this.y = y;
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
