package co.gov.supernotariado.bachue.portalgeografico.model;

/**
 * Esta clase define el modelo de la tabla de administracion
 * @author datatools
 */
public class DataTable {
	
	/**
	 * Variable no
	 * int que contiene el numero de secuencia de la lista.
	 */
	private int no;
	/**
	 * Variable value
	 * String que contiene el valor de la lista en un grupo de listas.
	 */
	private String value;
	/**
	 * Variable state
	 * String que contiene el estado del valoren la lista.
	 */
	private String state;
	/**
	 * Variable list
	 * int que contiene el codigo del tipo de lista.
	 */
	private int list;
	/**
	 * Variable codBus
	 * String que contiene el codigo de la busqueda en el bus.
	 */
	private String codBus;
	/**
	 * Variable busquedaB
	 * String que contiene el tipo de la busqueda en el bus.
	 */
	private String busquedaB;
	
    /**
     * Constructor de la clase AddressSearch
     * @param int no E numero de la secuencia, String value El nombre del valor, String stateEl estado del valor, 
     * int list El codigo del grupo de la lista, String codBus El codigo de busqueda para el bus, String busquedaB El tipo de busqueda en el bus si tiene
     */
	public DataTable(int no, String value, String state, int list, String codBus, String busquedaB) {
		super();
		this.no = no;
		this.setValue(value);
		this.setState(state);
		this.setList(list);
		this.codBus = codBus;
		this.busquedaB = busquedaB;

	}
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCodBus() {
		return codBus;
	}

	public void setCodBus(String codBus) {
		this.codBus = codBus;
	}

	public String getBusquedaB() {
		return busquedaB;
	}

	public void setBusquedaB(String busquedaB) {
		this.busquedaB = busquedaB;
	}

	public int getList() {
		return list;
	}

	public void setList(int list) {
		this.list = list;
	}


}
