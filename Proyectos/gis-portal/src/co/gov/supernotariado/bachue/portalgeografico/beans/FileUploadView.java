package co.gov.supernotariado.bachue.portalgeografico.beans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;

import javax.ejb.EJB;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.google.gson.Gson;

import co.gov.supernotariado.bachue.portalgeografico.component.Constants;
import co.gov.supernotariado.bachue.portalgeografico.dao.interfaces.IAdministracionDao;
import co.gov.supernotariado.bachue.portalgeografico.dao.interfaces.IConsultationDao;
import co.gov.supernotariado.bachue.portalgeografico.model.DataTable;
import co.gov.supernotariado.bachue.portalgeografico.model.ListaValores;
import co.gov.supernotariado.bachue.portalgeografico.model.Puntos;

/**
 * Clase que define la carga de documentos de informacion en el portal gis.
 * @author datatools
 */
@ManagedBean
@ViewScoped
public class FileUploadView {

	/**
	 * Variable file
	 * objeto UploadedFile de primefaces para carga de documentos
	 */
	private UploadedFile file;
	/**
	 * Variable massiveConsul
	 * String que contiene el tipo de busqueda definida por el usuario.
	 */
	private String massiveConsul;
	/**
	 * Variable massiveConsuls
	 * Lista de ListaValores que contiene los tipos de busqueda masiva.
	 */
	private List<ListaValores> massiveConsuls;
	/**
	 * Variable cargarExportar
	 * String que contiene el valor que define la accion.
	 */
	private String cargarExportar;
	/**
	 * Variable cargarValor
	 * String que contiene el valor que define la accion.
	 */
	private String cargarValor;

	/**
	 * Variable mapBean
	 * Objeto MapBean que contiene los objetos de la clase MapBean.
	 */
	@ManagedProperty(value = "#{mapBean}")
	private MapBean mapBean;
	
	
	@EJB
	private IConsultationDao consInterest;

	@EJB
	private IAdministracionDao admon;
	
	
	/**
	 * Metodo que inicializa los valores de carga.
	 */	
	public FileUploadView() {
		this.cargarValor = "LOCAL";
		this.cargarExportar = "EXPORTAR";
	}

	/**
	 * Metodo constructor que inicializa la vista con los parametros por defecto definidos.
	 */	
	@PostConstruct
	public void init() {
		setMassiveConsuls(null);
		setMassiveConsuls(new ArrayList<ListaValores>());
		setMassiveConsuls(admon.tBusquedaMasivaA(5));
	}

	/**
	 * Metodo por defecto de la carga de un archivo en primefaces.
	 */	
	public void upload() {
		if (file != null) {
			FacesMessage message = new FacesMessage(Constants.MENSAJE_UPLOAD_1, file.getFileName() + Constants.MENSAJE_UPLOAD_2);
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	/**
	 * Metodo que carga el archivo txt en consulta masiva
	 * @param event El parámetro event define un objeto de FileUploadEvent que contiene los datos del documento cargado por las caracteristicas de primefaces.
	 */	
	public void handleFileUpload(FileUploadEvent event) throws IOException {
		System.out.println(massiveConsul);
		ArrayList<String> seleccion = new ArrayList<String>();
		ArrayList<String> valor = new ArrayList<String>();
		ArrayList<String> valor2 = new ArrayList<String>();
		List<ListaValores> mc = new ArrayList<ListaValores>();
		List<DataTable> mc2 = new ArrayList<DataTable>();

		String linea = "";
		String value;
		int cantidadL;
		int cantidadMax = admon.tBusquedaARegistro(Constants.ARCHIVO_TXT);
		boolean vacio = true;
		boolean error = false;

		mc = admon.tBusquedaA(5, this.massiveConsul);

		InputStream inputStream = event.getFile().getInputstream();
		InputStream inputStream2 = event.getFile().getInputstream();
		InputStreamReader reader = new InputStreamReader(inputStream, Constants.UTF8);
		InputStreamReader reader2 = new InputStreamReader(inputStream2, Constants.UTF8);
		BufferedReader br = new BufferedReader(reader);
		BufferedReader br2 = new BufferedReader(reader2);

		FacesMessage msg = new FacesMessage(Constants.MENSAJE_UPLOAD_3, event.getFile().getFileName() + Constants.MENSAJE_UPLOAD_4 + linea);

		if (br2.lines().count() > cantidadMax) {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, Constants.MENSAJE_UPLOAD_5, event.getFile().getFileName()
					+ Constants.MENSAJE_UPLOAD_6 + cantidadMax + Constants.DELIMITADOR_PUNTO);
			PrimeFaces.current().executeScript(Constants.FUNCION_LIMPIAR_JS);
			error = true;
		} else {

			while ((linea = br.readLine()) != null) {
				vacio = false;
				System.out.println(linea);
				StringTokenizer tokens = new StringTokenizer(linea);
				cantidadL = tokens.countTokens();
				if (mc.get(0).getCOD_BUSQUEDA_BUS() == 1) {
					if (cantidadL != 1) {
						msg = new FacesMessage(FacesMessage.SEVERITY_WARN, Constants.MENSAJE_UPLOAD_5, event.getFile().getFileName()
								+ Constants.MENSAJE_UPLOAD_7);
						PrimeFaces.current().executeScript(Constants.FUNCION_LIMPIAR_JS);
						error = true;
						break;
					}
					seleccion.add(mc.get(0).getCOD_BUS());
					valor.add(linea);

				} else if (mc.get(0).getCOD_BUSQUEDA_BUS() == 5) {
					if (cantidadL != 2) {
						msg = new FacesMessage(FacesMessage.SEVERITY_WARN, Constants.MENSAJE_UPLOAD_5, event.getFile().getFileName()
								+ Constants.MENSAJE_UPLOAD_7);
						PrimeFaces.current().executeScript(Constants.FUNCION_LIMPIAR_JS);
						error = true;
						break;
					}
					value = tokens.nextToken();
					mc2 = admon.tBusquedaIdeBus(2, value);
					seleccion.add(mc2.get(0).getCodBus());
					valor.add(tokens.nextToken());
				} else if (mc.get(0).getCOD_BUSQUEDA_BUS() == 2) {
					seleccion.add(mc.get(0).getCOD_BUS());
					StringTokenizer tokens2 = new StringTokenizer(linea, ";");
					valor.add(tokens2.nextToken());
					valor2.add(tokens2.nextToken());
				}

			}
			if (vacio) {
				msg = new FacesMessage(FacesMessage.SEVERITY_WARN, Constants.MENSAJE_UPLOAD_5,
						event.getFile().getFileName() + Constants.MENSAJE_UPLOAD_8);
				PrimeFaces.current().executeScript(Constants.FUNCION_LIMPIAR_JS);
				error = true;
			}
		}

		if (error == false) {

			if (mc.get(0).getCOD_BUSQUEDA_BUS() == 5) {

				mapBean.setDataTable(consInterest.ownerId(seleccion, valor));
			} else if (mc.get(0).getCOD_BUSQUEDA_BUS() == 2) {
				mapBean.setDataTable(consInterest.ownerBasicDataNom(seleccion, valor, valor2));
			} else if (mc.get(0).getCOD_BUSQUEDA_BUS() == 1) {
				mapBean.setDataTable(consInterest.propertyNumber(seleccion, valor));
			}
		}

		FacesContext.getCurrentInstance().addMessage(null, msg);
		br.close();
	}

	/**
	 * Metodo que carga el archivo csv en agregar archivos csv
	 * @param event El parámetro event define un objeto de FileUploadEvent que contiene los datos del documento cargado por las caracteristicas de primefaces.
	 */
	public void handleFileUpload2(FileUploadEvent event) throws IOException {
		if (this.cargarValor.equals(Constants.EXTENCION_CSV)) {
			int index = 0;
			String linea = "";
			int cantidadL;
			int cantidadMax = admon.tBusquedaARegistro(Constants.ARCHIVO_TXT);
			boolean vacio = true;
			boolean error = false;
			List<Puntos> result = new LinkedList<>();

			InputStream inputStream = event.getFile().getInputstream();
			InputStreamReader reader = new InputStreamReader(inputStream, Constants.UTF8);
			BufferedReader br = new BufferedReader(reader);
			FacesMessage msg = null;

			while ((linea = br.readLine()) != null) {
				vacio = false;
				System.out.println(linea);
				StringTokenizer tokens = new StringTokenizer(linea, Constants.DELIMITADOR_COMA);
				cantidadL = tokens.countTokens();
				if (cantidadL != 3) {
					msg = new FacesMessage(FacesMessage.SEVERITY_WARN, Constants.MENSAJE_UPLOAD_5,
							event.getFile().getFileName() + Constants.MENSAJE_UPLOAD_9);
					error = true;
					break;
				}

				try {
					if (index > 0) {
						Long consecutivo = new Long(tokens.nextToken());
						Double longitud = new Double(tokens.nextToken());
						Double latitud = new Double(tokens.nextToken());
						if ((longitud >= -180 && longitud <= 180) && (latitud >= -90 && latitud <= 90)) {
							result.add(new Puntos(consecutivo, longitud, latitud));
						} else {
							msg = new FacesMessage(FacesMessage.SEVERITY_WARN, Constants.MENSAJE_UPLOAD_5,
									event.getFile().getFileName() + Constants.MENSAJE_UPLOAD_10);
							error = true;
							break;
						}

					} else {
						String consecutivo = tokens.nextToken();
						String longitud = tokens.nextToken();
						String latitud = tokens.nextToken();
						if (!consecutivo.equalsIgnoreCase(Constants.CONSECUTIVO_UPLOAD) || !longitud.equalsIgnoreCase(Constants.LONGITUD_UPLOAD)
								|| !latitud.equalsIgnoreCase(Constants.LATITUD_UPLOAD)) {
							msg = new FacesMessage(FacesMessage.SEVERITY_WARN, Constants.MENSAJE_UPLOAD_5,
									event.getFile().getFileName()
											+ Constants.MENSAJE_UPLOAD_11);
							error = true;
							break;
						}
					}
				} catch (Exception ex) {
					msg = new FacesMessage(FacesMessage.SEVERITY_WARN, Constants.MENSAJE_UPLOAD_5,
							event.getFile().getFileName() + Constants.MENSAJE_UPLOAD_9);
					error = true;
					break;
				}

				index++;
				if (index >= cantidadMax) {
					msg = new FacesMessage(FacesMessage.SEVERITY_WARN, Constants.MENSAJE_UPLOAD_5, event.getFile().getFileName()
							+ Constants.MENSAJE_UPLOAD_6 + cantidadMax + ".");
					error = true;
				}
			}

			if (vacio) {
				msg = new FacesMessage(FacesMessage.SEVERITY_WARN, Constants.MENSAJE_UPLOAD_5,
						event.getFile().getFileName() + Constants.MENSAJE_UPLOAD_8);
				error = true;
			}

			if (error == false) {
				PrimeFaces.current().executeScript("addCSVPoints(" + new Gson().toJson(result) + ");");
				msg = new FacesMessage(Constants.MENSAJE_UPLOAD_3, event.getFile().getFileName() + Constants.MENSAJE_UPLOAD_4);
			}

			FacesContext.getCurrentInstance().addMessage(null, msg);
			br.close();
		} else {
			PrimeFaces.current().executeScript("generateFeatureCollection('" + event.getFile().getFileName() + "');");
		}
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public MapBean getMapBean() {
		return mapBean;
	}

	public void setMapBean(MapBean mapBean) {
		this.mapBean = mapBean;
	}

	public String getCargarExportar() {
		return cargarExportar;
	}

	public void setCargarExportar(String cargarExportar) {
		this.cargarExportar = cargarExportar;
	}

	public String getCargarValor() {
		return cargarValor;
	}

	public void setCargarValor(String cargarValor) {
		this.cargarValor = cargarValor;
	}

	public List<ListaValores> getMassiveConsuls() {
		return massiveConsuls;
	}

	public void setMassiveConsuls(List<ListaValores> massiveConsuls) {
		this.massiveConsuls = massiveConsuls;
	}

	public String getMassiveConsul() {
		return massiveConsul;
	}

	public void setMassiveConsul(String massiveConsul) {
		this.massiveConsul = massiveConsul;
	}
}
