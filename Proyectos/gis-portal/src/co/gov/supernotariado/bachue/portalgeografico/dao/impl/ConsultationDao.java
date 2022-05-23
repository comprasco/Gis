package co.gov.supernotariado.bachue.portalgeografico.dao.impl;

import java.io.IOException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bachue.snr.servicios.fabrica.IServicioWeb;
import com.bachue.snr.servicios.fabrica.ServicioWebFactory;
import com.bachue.snr.servicios.util.EnumOperacionesSoap;
import com.bachue.snr.servicios.vo.AnotacionVO;
import com.bachue.snr.servicios.vo.ClienteIntegracionVO;
import com.bachue.snr.servicios.vo.ConsultarDatosRegistralesEntradaVO;
import com.bachue.snr.servicios.vo.ConsultarDatosRegistralesSalidaVO;
import com.bachue.snr.servicios.vo.IntervinienteVO;
import com.bachue.snr.servicios.vo.MatriculaDatosRegistralesVO;
import com.bachue.snr.servicios.vo.TitularVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import co.gov.supernotariado.bachue.portalgeografico.component.Constants;
import co.gov.supernotariado.bachue.portalgeografico.dao.impl.ConsultationDao;
import co.gov.supernotariado.bachue.portalgeografico.dao.interfaces.IAdministracionDao;
import co.gov.supernotariado.bachue.portalgeografico.dao.interfaces.IConsultationDao;
import co.gov.supernotariado.bachue.portalgeografico.dao.interfaces.IGisParametrosDao;
import co.gov.supernotariado.bachue.portalgeografico.model.AddressSearch;
import co.gov.supernotariado.bachue.portalgeografico.model.AnotacionesDataTable;
import co.gov.supernotariado.bachue.portalgeografico.model.AttributeDataTable;
import co.gov.supernotariado.bachue.portalgeografico.model.DatosJuridicoDataTable;
import co.gov.supernotariado.bachue.portalgeografico.model.DatosPredioDataTable;
import co.gov.supernotariado.bachue.portalgeografico.model.NameSearch;

/**
 * Clase que ejecuta los CRUD de las consultas tanto de base de datos del portal
 * gis como el consumo del servicio de datos registrales.
 * 
 * @author datatools
 */
@Stateless
@LocalBean
public class ConsultationDao implements IConsultationDao {
	private static final Logger log = LoggerFactory.getLogger(ConsultationDao.class);
	/**
	 * Variable service String que contiene la url del servidor arcgis.
	 */
	private String service;
	/**
	 * Variable gisCodes Lista de String que contiene las cedulas catastrales de los
	 * predios consultados.
	 */
	List<String> gisCodes;
	/**
	 * Variable territorioD String el departamento donde esta ubicado el predio.
	 */
	private String territorioD;
	/**
	 * Variable territorioD String el municipio donde esta ubicado el predio.
	 */
	private String territorioM;
	/**
	 * Variable datosPredioM Lista de DatosPredioDataTable que contiene la
	 * informacion de los predios consultado por poup.
	 */
	private List<DatosPredioDataTable> datosPredioM;
	/**
	 * Variable datosJuridicoM Lista de DatosJuridicoDataTable que contiene las
	 * anotaciones del predio por poup.
	 */
	private List<DatosJuridicoDataTable> datosJuridicoM;
	/**
	 * Variable datosAnotacionesM Lista de AnotacionesDataTable que contiene los
	 * intervinientes de las anotaciones del predio por poup.
	 */
	private List<AnotacionesDataTable> datosAnotacionesM;

	@EJB
	private IGisParametrosDao gisParametrosDao;

	@EJB
	private IAdministracionDao admon;

	/**
	 * Metodo que obtiene los registros por numero de la propiedad
	 * 
	 * @param Search, value El parámetro Search define un String que contiene el
	 *                tipo de busqueda y El parámetro value define un String con el
	 *                numero de la propiedad
	 * @return Una lista de AttributeDataTable que contiene los valores de los
	 *         predios asociados al numero.
	 */
	@Override
	public List<AttributeDataTable> propertyNumber(String Search, String value) {
		List<AttributeDataTable> dataTable;
		dataTable = new ArrayList<AttributeDataTable>();
		dataTable = consumeSoapList(Search, value);
		String gisServiceU = this.service + Constants.ZONA_URBANA;
		String gisServiceR = this.service + Constants.ZONA_RURAL;
		PrimeFaces.current().executeScript(
				"queryCodes('" + gisServiceU + "','" + gisServiceR + "','" + new Gson().toJson(gisCodes) + "')");
		return dataTable;
	}

	/**
	 * Metodo que obtiene los registros de predios por su rango de area
	 * 
	 * @param Search, value El parámetro Search define un String que contiene el
	 *                tipo de busqueda y El parámetro value define un String con el
	 *                numero de la propiedad
	 * @return Una lista de AttributeDataTable que contiene los valores de los
	 *         predios asociados al numero.
	 */
	@Override
	public List<AttributeDataTable> propertyAreaRange(String Search, String value) {
		List<AttributeDataTable> dataTable;
		dataTable = new ArrayList<AttributeDataTable>();
		dataTable = consumeSoapList(Search, value);
		return dataTable;
	}

	/**
	 * Metodo que obtiene los registros por numero de propietario
	 * 
	 * @param Search, value El parámetro Search define un String que contiene el
	 *                tipo de busqueda y El parámetro value define un String con el
	 *                numero del propietario
	 * @return Una lista de AttributeDataTable que contiene los valores de los
	 *         predios asociados al numero.
	 */
	@Override
	public List<AttributeDataTable> ownerId(String identification, String value) {
		List<AttributeDataTable> dataTable;
		dataTable = new ArrayList<AttributeDataTable>();
		dataTable = consumeSoapList(identification, value);
		String gisServiceU = this.service + Constants.ZONA_URBANA;
		String gisServiceR = this.service + Constants.ZONA_RURAL;
		PrimeFaces.current().executeScript(
				"queryCodes('" + gisServiceU + "','" + gisServiceR + "','" + new Gson().toJson(gisCodes) + "')");
		return dataTable;
	}

	/**
	 * Metodo que obtiene los registros por la direccion de la propiedad
	 * 
	 * @param Search, value El parámetro Search define un String que contiene el
	 *                tipo de busqueda y El parámetro value define un String con la
	 *                direccion de la propiedad
	 * @return Una lista de AttributeDataTable que contiene los valores de los
	 *         predios asociados a la direccion.
	 */
	@Override
	public List<AttributeDataTable> ownerBasicDataDir(String Search, String value) throws IOException {
		List<AttributeDataTable> dataTable;
		dataTable = new ArrayList<AttributeDataTable>();
		final AddressSearch json = new AddressSearch("", "", "", "", "", "", "", "", "", "", "", "", "", value);
		final Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
		final String valueJ = prettyGson.toJson(json);
		System.out.println(valueJ);
		dataTable = consumeSoapList(Search, valueJ);
		String gisServiceU = this.service + Constants.ZONA_URBANA;
		String gisServiceR = this.service + Constants.ZONA_RURAL;
		PrimeFaces.current().executeScript(
				"queryCodes('" + gisServiceU + "','" + gisServiceR + "','" + new Gson().toJson(gisCodes) + "')");
		return dataTable;
	}

	/**
	 * Metodo que obtiene los registros por el nombre del propietario
	 * 
	 * @param Search, value,value2 El parámetro Search define un String que contiene
	 *                el tipo de busqueda, El parámetro value define un String con
	 *                los nombres del propietario y El parámetro value2 define un
	 *                String que contiene los apellidos del propietario
	 * @return Una lista de AttributeDataTable que contiene los valores de los
	 *         predios asociados al nombre del propietario.
	 */
	@Override
	public List<AttributeDataTable> ownerBasicDataNom(String Search, String value, String value2) throws IOException {
		String name1 = "";
		String name2 = "";
		String apell1 = "";
		String apell2 = "";
		List<AttributeDataTable> dataTable;
		dataTable = new ArrayList<AttributeDataTable>();
		StringTokenizer tokens = new StringTokenizer(value);
		while (tokens.hasMoreTokens()) {
			if (name1.equals("")) {
				name1 = tokens.nextToken();
			} else {
				if (tokens.countTokens() == 1) {
					name2 = name2 + tokens.nextToken();
				} else {
					name2 = name2 + tokens.nextToken() + " ";
				}
			}
		}
		tokens = new StringTokenizer(value2);
		while (tokens.hasMoreTokens()) {
			if (apell1.equals("")) {
				apell1 = tokens.nextToken();
			} else {
				if (tokens.countTokens() == 1) {
					apell2 = apell2 + tokens.nextToken();
				} else {
					apell2 = apell2 + tokens.nextToken() + " ";
				}
			}
		}
		final NameSearch json = new NameSearch(name1, name2, apell1, apell2, "");
		final Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
		final String valueJ = prettyGson.toJson(json);
		System.out.println(valueJ);
		dataTable = consumeSoapList(Search, valueJ);
		return dataTable;
	}

	/**
	 * Metodo que obtiene los registros por la razon social
	 * 
	 * @param Search, value El parámetro Search define un String que contiene el
	 *                tipo de busqueda y El parámetro value define un String con la
	 *                razon social
	 * @return Una lista de AttributeDataTable que contiene los valores de los
	 *         predios asociados a la razon social.
	 */
	@Override
	public List<AttributeDataTable> ownerBasicDataNomNit(String Search, String value) throws IOException {
		List<AttributeDataTable> dataTable;
		dataTable = new ArrayList<AttributeDataTable>();
		final NameSearch json = new NameSearch("", "", "", "", value);
		final Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
		final String valueJ = prettyGson.toJson(json);
		System.out.println(valueJ);
		dataTable = consumeSoapList(Search, valueJ);
		String gisServiceU = this.service + Constants.ZONA_URBANA;
		String gisServiceR = this.service + Constants.ZONA_RURAL;
		PrimeFaces.current().executeScript(
				"queryCodes('" + gisServiceU + "','" + gisServiceR + "','" + new Gson().toJson(gisCodes) + "')");
		return dataTable;
	}

	/**
	 * Metodo que obtiene los registros por los numeros de las propiedades
	 * 
	 * @param Search, value El parámetro Search define una lista de String que
	 *                contiene el tipo de busqueda y El parámetro value define una
	 *                lista de String con los numeros de las propiedades
	 * @return Una lista de AttributeDataTable que contiene los valores de los
	 *         predios asociados a los numeros.
	 */
	@Override
	public List<AttributeDataTable> propertyNumber(ArrayList<String> Search, ArrayList<String> value) {
		List<AttributeDataTable> dataTable;
		dataTable = new ArrayList<AttributeDataTable>();
		dataTable = consumeSoapListMult(Search, value);
		String gisServiceU = this.service + Constants.ZONA_URBANA;
		String gisServiceR = this.service + Constants.ZONA_RURAL;
		PrimeFaces.current().executeScript(
				"queryCodes('" + gisServiceU + "','" + gisServiceR + "','" + new Gson().toJson(gisCodes) + "')");
		return dataTable;
	}

	/**
	 * Metodo que obtiene los registros por la lista de numeros de propietarios
	 * 
	 * @param identification, value El parámetro identification define una lista de
	 *                        String que contiene el tipo de busqueda y El parámetro
	 *                        value define una lista de String con los numero de los
	 *                        propietarios
	 * @return Una lista de AttributeDataTable que contiene los valores de los
	 *         predios asociados a los numeros.
	 */
	@Override
	public List<AttributeDataTable> ownerId(ArrayList<String> identification, ArrayList<String> value) {
		List<AttributeDataTable> dataTable;
		dataTable = new ArrayList<AttributeDataTable>();
		dataTable = consumeSoapListMult(identification, value);
		String gisServiceU = this.service + Constants.ZONA_URBANA;
		String gisServiceR = this.service + Constants.ZONA_RURAL;
		PrimeFaces.current().executeScript(
				"queryCodes('" + gisServiceU + "','" + gisServiceR + "','" + new Gson().toJson(gisCodes) + "')");
		return dataTable;
	}

	/**
	 * Metodo que obtiene los registros por la identificacion del predio
	 * 
	 * @param identification, value El parámetro identification define un String que
	 *                        contiene el tipo de busqueda y El parámetro value
	 *                        define un String con el numero de la propiedad
	 * @return Una lista de String que contiene los valores del predio asociado al
	 *         numero.
	 */
	@Override
	public List<String> datosPredio(String identification, String value) {
		List<String> dataTable;
		dataTable = new ArrayList<String>();
		dataTable = consumeSoapListPopup(identification, value);
		return dataTable;
	}

	/**
	 * Metodo que obtiene una lista de los datos del predio consultados por poup
	 * 
	 * @return Una lista de DatosPredioDataTable que contiene los valores del predio
	 *         consultados por poup.
	 */
	@Override
	public List<DatosPredioDataTable> datosPredioTable() {
		return this.datosPredioM;
	}

	/**
	 * Metodo que obtiene una lista de los datos juridicos del predio consultados
	 * por poup
	 * 
	 * @return Una lista de DatosJuridicoDataTable que contiene los valores
	 *         juridicos del predio consultados por poup.
	 */
	@Override
	public List<DatosJuridicoDataTable> datosJuridicoTable() {
		return this.datosJuridicoM;
	}

	/**
	 * Metodo que obtiene una lista de las anotaciones del predio consultados por
	 * poup
	 * 
	 * @return Una lista de AnotacionesDataTable que contiene las anotaciones del
	 *         predio consultados por poup.
	 */
	@Override
	public List<AnotacionesDataTable> datosAnotacionesTable() {
		return this.datosAnotacionesM;
	}

	/**
	 * Metodo que obtiene el departamento del predio consultado por poup
	 * 
	 * @return Un String que contiene el departamento del predio consultado por
	 *         poup.
	 */
	@Override
	public String territorioD() {
		return this.territorioD;
	}

	/**
	 * Metodo que obtiene el municipio del predio consultado por poup
	 * 
	 * @return Un String que contiene el municipio del predio consultado por poup.
	 */
	@Override
	public String territorioM() {
		return this.territorioM;
	}

	/**
	 * Metodo que obtiene los registros por lista de los nombres de los propietarios
	 * 
	 * @param Search, value,value2 El parámetro Search define una lista de String
	 *                que contiene el tipo de busqueda, El parámetro value define
	 *                una lista String con los nombres de los propietarios y El
	 *                parámetro value2 define una lista de String que contiene los
	 *                apellidos de los propietarios
	 * @return Una lista de AttributeDataTable que contiene los valores de los
	 *         predios asociados a los nombres de los propietarios.
	 */
	@Override
	public List<AttributeDataTable> ownerBasicDataNom(ArrayList<String> Search, ArrayList<String> value,
			ArrayList<String> value2) throws IOException {

		StringTokenizer tokens;
		List<AttributeDataTable> dataTable;
		dataTable = new ArrayList<AttributeDataTable>();
		ArrayList<String> valor = new ArrayList<String>();
		String name1;
		String name2;
		String apell1;
		String apell2;

		for (int x = 0; x < value.size(); x++) {
			name1 = "";
			name2 = "";
			apell1 = "";
			apell2 = "";
			tokens = new StringTokenizer(value.get(x));
			while (tokens.hasMoreTokens()) {
				if (name1.equals("")) {
					name1 = tokens.nextToken();
				} else {
					if (tokens.countTokens() == 1) {
						name2 = name2 + tokens.nextToken();
					} else {
						name2 = name2 + tokens.nextToken() + " ";
					}
				}
			}
			tokens = new StringTokenizer(value2.get(x));
			while (tokens.hasMoreTokens()) {
				if (apell1.equals("")) {
					apell1 = tokens.nextToken();
				} else {
					if (tokens.countTokens() == 1) {
						apell2 = apell2 + tokens.nextToken();
					} else {
						apell2 = apell2 + tokens.nextToken() + " ";
					}
				}
			}
			final NameSearch json = new NameSearch(name1, name2, apell1, apell2, "");
			final Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
			final String valueJ = prettyGson.toJson(json);
			valor.add(valueJ);
			System.out.println(valueJ);
		}
		dataTable = consumeSoapListMult(Search, valor);
		String gisServiceU = this.service + Constants.ZONA_URBANA;
		String gisServiceR = this.service + Constants.ZONA_RURAL;
		PrimeFaces.current().executeScript(
				"queryCodes('" + gisServiceU + "','" + gisServiceR + "','" + new Gson().toJson(gisCodes) + "')");
		return dataTable;
	}

	/**
	 * Metodo que consume el servicio de consultar datos registrales
	 * 
	 * @param criterio, busqueda El parámetro criterio define un String que contiene
	 *                  el tipo de busqueda y El parámetro busqueda define un String
	 *                  con el valor de la busqueda
	 * @return Un objeto de ConsultarDatosRegistralesSalidaVO que contiene los
	 *         valores de los predios asociados a la busqueda.
	 */
	public ConsultarDatosRegistralesSalidaVO Soap(String criterio, String busqueda) {
		String endPonint, modulo, espacioNombre, metodo;
		ConsultarDatosRegistralesSalidaVO consultarDatosRegistralesSalidaVO = null;

		try {
			ServicioWebFactory servicioWebFactory = new ServicioWebFactory();

			endPonint = gisParametrosDao.getValue(Constants.ENDPOINT_BUS_ARCGIS);
			modulo = gisParametrosDao.getValue(Constants.MODULO_ARCGIS);
			espacioNombre = gisParametrosDao.getValue(Constants.ESPACIO_NOMBRE_ARCGIS);
			metodo = gisParametrosDao.getValue(Constants.METODO_ARCGIS);
			this.service = gisParametrosDao.getValue(Constants.URL_ARCGIS);

			System.out.println(endPonint + " " + modulo + " " + espacioNombre + " " + metodo);

			ConsultarDatosRegistralesEntradaVO cdre_consultarDatosRegistralesEntradaVO = new ConsultarDatosRegistralesEntradaVO();
			cdre_consultarDatosRegistralesEntradaVO.setIs_modulo(modulo);
			cdre_consultarDatosRegistralesEntradaVO.setIs_criterioBusqueda(criterio);
			cdre_consultarDatosRegistralesEntradaVO.setIs_valorCriterioBusqueda(busqueda);

			cdre_consultarDatosRegistralesEntradaVO.setUrl(endPonint);
			cdre_consultarDatosRegistralesEntradaVO.setEspacioNombre(espacioNombre);
			cdre_consultarDatosRegistralesEntradaVO.setNombreServicioSoap(metodo);

			IServicioWeb i_iServicioWeb = servicioWebFactory.getServicioWeb(cdre_consultarDatosRegistralesEntradaVO);
			ClienteIntegracionVO ci_clienteIntegracionVO = i_iServicioWeb.ejecutar(
					cdre_consultarDatosRegistralesEntradaVO,
					EnumOperacionesSoap.CONSULTAR_DATOS_REGISTRALES_CONSULTAR.getIs_nombreServicio());

			consultarDatosRegistralesSalidaVO = (ConsultarDatosRegistralesSalidaVO) ci_clienteIntegracionVO;
			// ---------------------------------------------------------------------------------------------------------------------

//			cdre_consultarDatosRegistralesEntradaVO.setIs_modulo("GIS");
//			cdre_consultarDatosRegistralesEntradaVO.setIs_criterioBusqueda("CEDULA_CATASTRAL");
//			cdre_consultarDatosRegistralesEntradaVO.setIs_valorCriterioBusqueda("187530003000000040013000000000");
//
//			cdre_consultarDatosRegistralesEntradaVO
//					.setUrl("http://10.0.0.11:7004/services/ci/consultaDatosRegistrales/v1?WSDL");
//			cdre_consultarDatosRegistralesEntradaVO.setEspacioNombre(
//					"https://www.supernotariado.gov.co/services/bachue/ci/consultadatosregistrales/v1");
//			cdre_consultarDatosRegistralesEntradaVO.setNombreServicioSoap("SDI_CI_ConsultaDatosRegistrales");
//
//			IServicioWeb i_iServicioWeb = servicioWebFactory.getServicioWeb(cdre_consultarDatosRegistralesEntradaVO);
//			ClienteIntegracionVO ci_clienteIntegracionVO = i_iServicioWeb.ejecutar(
//					cdre_consultarDatosRegistralesEntradaVO,
//					EnumOperacionesSoap.CONSULTAR_DATOS_REGISTRALES_CONSULTAR.getIs_nombreServicio());
//
//			consultarDatosRegistralesSalidaVO = (ConsultarDatosRegistralesSalidaVO) ci_clienteIntegracionVO;

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return consultarDatosRegistralesSalidaVO;
	}

	/**
	 * Metodo que obtiene los valores del servicio de consultar datos registrales
	 * 
	 * @param criterio, busqueda El parámetro criterio define un String que contiene
	 *                  el tipo de busqueda y El parámetro busqueda define un String
	 *                  con el valor de la busqueda
	 * @return Una lista de AttributeDataTable que contiene los valores de los
	 *         predios asociados a la busqueda.
	 */
	public List<AttributeDataTable> consumeSoapList(String criterio, String busqueda) {

		List<AttributeDataTable> dataTable = null;
		this.gisCodes = null;
		this.gisCodes = new LinkedList<>();
		int cantidadMax = admon.tBusquedaARegistro(Constants.CONSULTA_INTERESADO);
		int num = 0;

		try {
			System.out.println("SOAP:      " + criterio + "------" + busqueda);
			ConsultarDatosRegistralesSalidaVO consultarDatosRegistralesSalidaVO = Soap(criterio, busqueda);
			System.out.println(consultarDatosRegistralesSalidaVO.getIs_cantidadRegistros());
			System.out.println(consultarDatosRegistralesSalidaVO.getIs_descripcionMensaje());
			System.out.println(consultarDatosRegistralesSalidaVO.getIs_codigoMensaje());

			dataTable = new ArrayList<AttributeDataTable>();
			List<MatriculaDatosRegistralesVO> list = new ArrayList<MatriculaDatosRegistralesVO>();
			list = consultarDatosRegistralesSalidaVO.getIlmdr_matriculas();
			for (int i = 0; i < list.size(); i++) {
				String codId = "", identi = "", name = "", n1 = "", a1 = "", a2 = "", ccatastral = "", numero = "",
						nupre = "", matricula = "", chip = "", depto = "", mun = "", grupo = "", dir = "";
				List<TitularVO> list2 = new ArrayList<TitularVO>();
				list2 = list.get(i).getIlt_titulares();

				String headline = "";
				if (list2 != null) {
					if (list2.size() <= 2) {
						headline = Constants.OTROS;
					}
					codId = codId + list2.get(0).getIs_tipoIdentificacionTitular();
					identi = identi + list2.get(0).getIs_identificacionTitular();
					if (list2.get(0).getIs_primerApellidoTitular() != null) {
						a1 = list2.get(0).getIs_primerApellidoTitular();
					}
					if (list2.get(0).getIs_segundoApellidoTitular() != null) {
						a2 = list2.get(0).getIs_segundoApellidoTitular();
					}
					if (list2.get(0).getIs_primerNombreTitular() != null) {
						n1 = list2.get(0).getIs_primerNombreTitular();
					}

					name = name + a1 + " " + a2 + " " + n1 + headline;
				}

				if (list.get(i).getIs_chip().isEmpty()) {
					ccatastral = list.get(i).getIs_cedulaCatastral() + ccatastral + Constants.DELIMITADOR_GUION_ESPACIO
							+ chip;
				} else {
					ccatastral = list.get(i).getIs_cedulaCatastral() + ccatastral + Constants.DELIMITADOR_GUION_ESPACIO
							+ chip + list.get(i).getIs_chip();
				}
				num = i + 1;
				numero = String.valueOf(num);
				// numero = numero + list.get(i).getIs_numero();
				nupre = nupre + list.get(i).getIs_nupre();
				matricula = matricula + list.get(i).getIs_matricula();
				depto = depto + list.get(i).getIs_departamento();
				mun = mun + list.get(i).getIs_municipio();
				grupo = grupo + list.get(i).getIs_grupo();
				dir = dir + list.get(i).getIs_direccionPredio();
//				for (TitularVO obj2 : list2) {
//					codId = obj2.getIs_tipoIdentificacionTitular();
//					identi = obj2.getIs_identificacionTitular();
//					name = obj2.getIs_primerApellidoTitular();
//				}
				System.out.println(
						numero + nupre + matricula + ccatastral + depto + mun + grupo + dir + codId + identi + name);
				dataTable.add(new AttributeDataTable(numero, nupre, matricula, ccatastral, depto, mun, grupo, dir,
						codId, identi, name, Constants.LAYER2_6));
				this.gisCodes.add(list.get(i).getIs_cedulaCatastral());

				if (i == cantidadMax) {
					i = list.size();
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			System.out.println(e);
		}
		return dataTable;
	}

	/**
	 * Metodo que obtiene los valores del servicio de consultar datos registrales
	 * para la consulta masiva
	 * 
	 * @param criterio, busqueda El parámetro criterio define una lista de String
	 *                  que contiene el tipo de busqueda y El parámetro busqueda
	 *                  define una lista String con el valor de las busquedas
	 * @return Una lista de AttributeDataTable que contiene los valores de los
	 *         predios asociados a la busqueda.
	 */
	public List<AttributeDataTable> consumeSoapListMult(ArrayList<String> criterio, ArrayList<String> busqueda) {
		List<AttributeDataTable> dataTable = null;
		dataTable = new ArrayList<AttributeDataTable>();
		this.gisCodes = null;
		this.gisCodes = new LinkedList<>();
		int cantidadMax = admon.tBusquedaARegistro(Constants.TABLA_ATRIBUTOS);
		String numero;
		int num = 0;

		for (int x = 0; x < criterio.size(); x++) {
			String codId = "", identi = "", name = "", n1 = "", a1 = "", a2 = "", ccatastral = "", nupre = "",
					matricula = "", chip = "", depto = "", mun = "", grupo = "", dir = "";
			try {
				System.out.println("SOAP:      " + criterio.get(x) + "------" + busqueda.get(x));
				ConsultarDatosRegistralesSalidaVO consultarDatosRegistralesSalidaVO = Soap(criterio.get(x),
						busqueda.get(x));
				System.out.println(consultarDatosRegistralesSalidaVO.getIs_cantidadRegistros());
				System.out.println(consultarDatosRegistralesSalidaVO.getIs_descripcionMensaje());
				System.out.println(consultarDatosRegistralesSalidaVO.getIs_codigoMensaje());

				List<MatriculaDatosRegistralesVO> list = new ArrayList<MatriculaDatosRegistralesVO>();
				list = consultarDatosRegistralesSalidaVO.getIlmdr_matriculas();
				for (int i = 0; i < list.size(); i++) {

					System.out.println(list.get(i).getIs_matricula());
					System.out.println(list.get(i).getIs_chip());
					List<TitularVO> list2 = new ArrayList<TitularVO>();
					list2 = list.get(i).getIlt_titulares();
					String headline = "";
					if (list2 != null) {
						if (list2.size() <= 2) {
							headline = Constants.OTROS;
						}
						codId = codId + list2.get(0).getIs_tipoIdentificacionTitular();
						identi = identi + list2.get(0).getIs_identificacionTitular();
						if (list2.get(0).getIs_primerApellidoTitular() != null) {
							a1 = list2.get(0).getIs_primerApellidoTitular();
						}
						if (list2.get(0).getIs_segundoApellidoTitular() != null) {
							a2 = list2.get(0).getIs_segundoApellidoTitular();
						}
						if (list2.get(0).getIs_primerNombreTitular() != null) {
							n1 = list2.get(0).getIs_primerNombreTitular();
						}

						name = name + a1 + " " + a2 + " " + n1 + headline;
					}

					if (list.get(i).getIs_chip().isEmpty()) {
						ccatastral = list.get(i).getIs_cedulaCatastral() + ccatastral
								+ Constants.DELIMITADOR_GUION_ESPACIO + chip;
					} else {
						ccatastral = list.get(i).getIs_cedulaCatastral() + ccatastral
								+ Constants.DELIMITADOR_GUION_ESPACIO + chip + list.get(i).getIs_chip();
					}

					nupre = nupre + list.get(i).getIs_nupre();
					matricula = matricula + list.get(i).getIs_matricula();
					depto = depto + list.get(i).getIs_departamento();
					mun = mun + list.get(i).getIs_municipio();
					grupo = grupo + list.get(i).getIs_grupo();
					dir = dir + list.get(i).getIs_direccionPredio();
					num = num + 1;
					numero = num + "";
					dataTable.add(new AttributeDataTable(numero, nupre, matricula, ccatastral, depto, mun, grupo, dir,
							codId, identi, name, Constants.LAYER2_6));
					this.gisCodes.add(list.get(i).getIs_cedulaCatastral());

					if (i == cantidadMax) {
						i = list.size();
						x = criterio.size();
					}
				}

			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		return dataTable;
	}

	/**
	 * Metodo que obtiene los valores del servicio de consultar datos registrales
	 * para el poup
	 * 
	 * @param criterio, busqueda El parámetro criterio define un String que contiene
	 *                  el tipo de busqueda y El parámetro busqueda define un String
	 *                  con el valor de la busqueda
	 * @return Una lista de String que contiene los valores de un predio asociado a
	 *         la busqueda.
	 */
	public List<String> consumeSoapListPopup(String criterio, String busqueda) {
		List<String> dataTable = null;
		dataTable = new ArrayList<String>();
		datosPredioM = new ArrayList<DatosPredioDataTable>();
		datosJuridicoM = new ArrayList<DatosJuridicoDataTable>();
		datosAnotacionesM = new ArrayList<AnotacionesDataTable>();
		this.gisCodes = null;
		this.gisCodes = new LinkedList<>();
		String name = "", n1 = "", n2 = "", a1 = "", a2 = "", matricula = "", depto = "", mun = "";

		try {
			System.out.println("SOAP:      " + criterio + "------" + busqueda);
			ConsultarDatosRegistralesSalidaVO consultarDatosRegistralesSalidaVO = Soap(criterio, busqueda);
			System.out.println(consultarDatosRegistralesSalidaVO.getIs_cantidadRegistros());
			System.out.println(consultarDatosRegistralesSalidaVO.getIs_descripcionMensaje());
			System.out.println(consultarDatosRegistralesSalidaVO.getIs_codigoMensaje());

			dataTable = new ArrayList<String>();
			List<MatriculaDatosRegistralesVO> list = new ArrayList<MatriculaDatosRegistralesVO>();
			list = consultarDatosRegistralesSalidaVO.getIlmdr_matriculas();

			System.out.println(list.get(0).getIs_matricula());

			List<TitularVO> list2 = new ArrayList<TitularVO>();
			List<AnotacionVO> list3 = new ArrayList<AnotacionVO>();
			List<IntervinienteVO> list4 = new ArrayList<IntervinienteVO>();

			this.territorioD = depto + list.get(0).getIs_departamento();
			this.territorioM = mun + list.get(0).getIs_municipio();

			System.out.println(territorioD);
			System.out.println(territorioM);

			for (int i = 0; i < list.size(); i++) {
				matricula = matricula + list.get(i).getIs_matricula();
				dataTable.add(matricula);
				System.out.println("lista de matriculas-------");
				System.out.println(list.size());
				System.out.println(list.get(i).getIs_matricula());

				list2 = list.get(i).getIlt_titulares();
				if (list2 != null) {
					for (int j = 0; j < list2.size(); j++) {
						if (list2.get(j).getIs_primerApellidoTitular() != null) {
							a1 = list2.get(j).getIs_primerApellidoTitular();
						}
						if (list2.get(j).getIs_segundoApellidoTitular() != null) {
							a2 = list2.get(j).getIs_segundoApellidoTitular();
						}
						if (list2.get(j).getIs_primerNombreTitular() != null) {
							n1 = list2.get(j).getIs_primerNombreTitular();
						}
						if (list2.get(j).getIs_segundoNombreTitular() != null) {
							n2 = list2.get(j).getIs_segundoNombreTitular();
						}
						name = a1 + " " + a2 + " " + n1 + " " + n2;

						this.datosPredioM.add(new DatosPredioDataTable(list.get(i).getIs_matricula(),
								list2.get(j).getIs_identificacionTitular(), name, list.get(i).getIs_direccionPredio(),
								list2.get(j).getIs_porcentajeParticipacion()));
					}
				}
				list3 = list.get(i).getIlt_anotaciones();

				if (list3 != null) {
					for (int j = 0; j < list2.size(); j++) {

						list4 = list3.get(j).getIli_intervinientes();
						this.datosJuridicoM.add(new DatosJuridicoDataTable(list.get(i).getIs_matricula(),
								list3.get(j).getIs_consecutivoAnotacion(), list3.get(j).getIs_tipoDocumentoAnotacion(),
								list3.get(j).getIs_actoJuridico(), list3.get(j).getIs_drr(),
								list3.get(j).getIs_fechaAnotacion(), list3.get(j).getIs_valorActo()));
						if (list4 != null) {
							for (int k = 0; k < list4.size(); k++) {
								this.datosAnotacionesM.add(new AnotacionesDataTable(list.get(i).getIs_matricula(),
										list3.get(j).getIs_consecutivoAnotacion(),
										list4.get(k).getIs_identificacionInterviniente(),
										list4.get(k).getIs_primerNombreInterviniente() + " "
												+ list4.get(k).getIs_primerApellidoInterviniente(),
										list4.get(k).getIs_rol()));
							}
						}
					}
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return dataTable;
	}

	public List<DatosPredioDataTable> getDatosPredioM() {
		return datosPredioM;
	}

	public void setDatosPredioM(List<DatosPredioDataTable> datosPredioM) {
		this.datosPredioM = datosPredioM;
	}

	public List<DatosJuridicoDataTable> getDatosJuridicoM() {
		return datosJuridicoM;
	}

	public void setDatosJuridicoM(List<DatosJuridicoDataTable> datosJuridicoM) {
		this.datosJuridicoM = datosJuridicoM;
	}

	public List<AnotacionesDataTable> getDatosAnotacionesM() {
		return datosAnotacionesM;
	}

	public void setDatosAnotacionesM(List<AnotacionesDataTable> datosAnotacionesM) {
		this.datosAnotacionesM = datosAnotacionesM;
	}

	public String getTerritorioD() {
		return territorioD;
	}

	public void setTerritorioD(String territorioD) {
		this.territorioD = territorioD;
	}

	public String getTerritorioM() {
		return territorioM;
	}

	public void setTerritorioM(String territorioM) {
		this.territorioM = territorioM;
	}

}
