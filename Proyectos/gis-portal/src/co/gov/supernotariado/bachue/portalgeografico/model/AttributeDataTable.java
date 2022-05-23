package co.gov.supernotariado.bachue.portalgeografico.model;

/**
 * Esta clase define el modelo de la tabla de atributos
 * @author datatools
 */
public class AttributeDataTable {
	
	/**
	 * Variable number
	 * String que contiene el numero de secuencia de la lista de registros.
	 */
	private String number;
	/**
	 * Variable nupre
	 * String que contiene el nupre del predio.
	 */
	private String nupre;
	/**
	 * Variable enrollment
	 * String que contiene la matricula del predio.
	 */
	private String enrollment;
	/**
	 * Variable depto
	 * String que contiene el nombre del departamento del predio.
	 */
	private String depto;
	/**
	 * Variable municipality
	 * String que contiene el nombre del municipio del predio.
	 */
	private String municipality;
	/**
	 * Variable group
	 * String que contiene el nombre del grupo del predio.
	 */
	private String group;
	/**
	 * Variable address
	 * String que contiene la direccion del predio.
	 */
	private String address;
	/**
	 * Variable id
	 * String que contiene el tipo de documento del propietario del predio.
	 */
	private String id;
	/**
	 * Variable identification
	 * String que contiene el numero de documento del propietario del predio.
	 */
	private String identification;
	/**
	 * Variable name
	 * String que contiene elnombre del propietario del predio.
	 */
	private String name;
	/**
	 * Variable code
	 * String que contiene la cedula y chip del predio.
	 */
	private String code;
	/**
	 * Variable layer
	 * String que contiene el layer del predio.
	 */
	private String layer;
	
    /**
     * Constructor de la clase AddressSearch
     * @param String number El numero de la secuencia,String nupre, String enrollment El numero de matricula, 
     * String code El numero de la cedula catastral, String depto El departamento, String municipality El municipio, 
     * String group El grupo, String address La direccion, String id El tipo de identificacion, String identification El numero de identificacion, 
     * String name El nombre del propietario, String layer La capa del terreno
     */
	public AttributeDataTable(String number,String nupre, String enrollment, String code, String depto, String municipality,
			String group, String address, String id, String identification, String name, String layer) {
		super();
		this.number = number;
		this.nupre = nupre;
		this.enrollment = enrollment;
		this.depto = depto;
		this.municipality = municipality;
		this.group = group;
		this.address = address;
		this.id = id;
		this.identification = identification;
		this.name = name;
		this.code = code;
		this.layer = layer;
	}
	
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getEnrollment() {
		return enrollment;
	}
	public void setEnrollment(String enrollment) {
		this.enrollment = enrollment;
	}
	
	public String getDepto() {
		return depto;
	}
	public void setDepto(String depto) {
		this.depto = depto;
	}
	public String getMunicipality() {
		return municipality;
	}
	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdentification() {
		return identification;
	}
	public void setIdentification(String identification) {
		this.identification = identification;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getLayer() {
		return layer;
	}


	public void setLayer(String layer) {
		this.layer = layer;
	}


	public String getNupre() {
		return nupre;
	}


	public void setNupre(String nupre) {
		this.nupre = nupre;
	}

}

