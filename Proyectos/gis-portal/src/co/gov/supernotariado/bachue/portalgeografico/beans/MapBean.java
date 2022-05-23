package co.gov.supernotariado.bachue.portalgeografico.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import co.gov.supernotariado.bachue.portalgeografico.component.Constants;
import co.gov.supernotariado.bachue.portalgeografico.dao.impl.VeredaDao;
import co.gov.supernotariado.bachue.portalgeografico.dao.interfaces.IAdministracionDao;
import co.gov.supernotariado.bachue.portalgeografico.dao.interfaces.IConsultationDao;
import co.gov.supernotariado.bachue.portalgeografico.dao.interfaces.IGestionUsuarioDao;
import co.gov.supernotariado.bachue.portalgeografico.dao.interfaces.IGisParametrosDao;
import co.gov.supernotariado.bachue.portalgeografico.dao.interfaces.IResourceBD;
import co.gov.supernotariado.bachue.portalgeografico.event.MapClickEvent;
import co.gov.supernotariado.bachue.portalgeografico.event.MapExtentEvent;
import co.gov.supernotariado.bachue.portalgeografico.event.MapGeoLocationEvent;
import co.gov.supernotariado.bachue.portalgeografico.event.MapGraphicActionEvent;
import co.gov.supernotariado.bachue.portalgeografico.event.MapGraphicDragEvent;
import co.gov.supernotariado.bachue.portalgeografico.event.MapGraphicViewEvent;
import co.gov.supernotariado.bachue.portalgeografico.model.AnotacionesDataTable;
import co.gov.supernotariado.bachue.portalgeografico.model.AttributeDataTable;
import co.gov.supernotariado.bachue.portalgeografico.model.BusquedaBus;
import co.gov.supernotariado.bachue.portalgeografico.model.Capa;
import co.gov.supernotariado.bachue.portalgeografico.model.DataTable;
import co.gov.supernotariado.bachue.portalgeografico.model.DatosJuridicoDataTable;
import co.gov.supernotariado.bachue.portalgeografico.model.DatosPredioDataTable;
import co.gov.supernotariado.bachue.portalgeografico.model.Departamento;
import co.gov.supernotariado.bachue.portalgeografico.model.ListaValores;
import co.gov.supernotariado.bachue.portalgeografico.model.Municipios;
import co.gov.supernotariado.bachue.portalgeografico.model.graphic.CircleGraphic;
import co.gov.supernotariado.bachue.portalgeografico.model.graphic.GraphicsModel;
import co.gov.supernotariado.bachue.portalgeografico.model.graphic.LineStyle;
import co.gov.supernotariado.bachue.portalgeografico.model.graphic.MeasurementUnit;
import co.gov.supernotariado.bachue.portalgeografico.model.graphic.PictureMarkerGraphic;
import co.gov.supernotariado.bachue.portalgeografico.model.graphic.PolygonGraphic;
import co.gov.supernotariado.bachue.portalgeografico.model.graphic.PolylineGraphic;
import co.gov.supernotariado.bachue.portalgeografico.model.graphic.SvgMarkerGraphic;
import co.gov.supernotariado.bachue.portalgeografico.model.graphic.TextGraphic;
import co.gov.supernotariado.bachue.portalgeografico.model.graphic.TextHorizontalAlignment;
import co.gov.supernotariado.bachue.portalgeografico.model.map.Background;
import co.gov.supernotariado.bachue.portalgeografico.model.map.Coordinate;
import co.gov.supernotariado.bachue.portalgeografico.model.service.ServiceDirectoryMetadata;
import co.gov.supernotariado.bachue.portalgeografico.model.service.ServiceDirectoryMetadataBuilder;
import co.gov.supernotariado.bachue.portalgeografico.model.service.ServiceLayerMetadata;
import co.gov.supernotariado.bachue.portalgeografico.model.service.ServiceMetadata;
import co.gov.supernotariado.bachue.portalgeografico.utilities.GISUtilities;
import co.gov.supernotariado.bachue.portalgeografico.utilities.JSFUtilities;
import co.gov.supernotariado.bachue.portalgeografico.utilities.StringUtilities;

/**
 * Clase para el manejo del negocio de caracteristicas del mapa del portal
 *
 * @author datatools
 *
 */

@ManagedBean
@ViewScoped
public class MapBean implements Serializable, Comparable<MapBean> {
	private static final Logger log = LoggerFactory.getLogger(MapBean.class);
	private static final long serialVersionUID = 848357454974222535L;

	/**
	 * Atributo msg Objeto de FacesMessage que contiene el valor de los mensajes
	 * para el usuario.
	 */
	FacesMessage msg;
	/**
	 * Variable radioBtn String que contiene el la busqueda de interesado elegida
	 * por el usuario.
	 */
	private String radioBtn;
	/**
	 * Variable sessionId String que contiene el id de la sesion actual.
	 */
	private String sessionId;
	/**
	 * Variable folioM String que contiene el folio de matricula seleccionado por el
	 * usuario en la lista del poup.
	 */
	private String folioM;
	/**
	 * Variable search String que contiene el tipo de busqueda elegida por el
	 * usuario.
	 */
	private String search;
	/**
	 * Variable searchs Lista de String que contiene los tipos de busqueda para
	 * mostrarlos en la lista.
	 */
	private List<String> searchs;
	/**
	 * Variable fMatriculas Lista de String que contiene que contiene la informacion
	 * de los predios consultado por poup.
	 */
	private List<String> fMatriculas;
	/**
	 * Variable territorioD String el departamento donde esta ubicado el predio
	 * seleccionado en el poup.
	 */
	private String territorioD;
	/**
	 * Variable territorioM String el municipio donde esta ubicado el predio
	 * seleccionado en el poup.
	 */
	private String territorioM;
	/**
	 * Variable search2 String que especifica tipo de busqueda elegida por el
	 * usuario.
	 */
	private String search2;
	/**
	 * Variable searchs2 Lista de String que especifica el tipo de busqueda para
	 * mostrarlos en la lista.
	 */
	private List<String> searchs2;
	/**
	 * Variable stateAdmon String que especifica el estado de la administracion.
	 */
	private String stateAdmon;
	/**
	 * Variable statesAdmon Lista de String que especifica los tipos de estados de
	 * los valores de listas.
	 */
	private List<String> statesAdmon;
	/**
	 * Variable admonList int que especifica el numero del grupo de lista elegida
	 * por el usuario.
	 */
	private int admonList;
	/**
	 * Atributo selectedAdmon Objeto DataTable que contiene la informacion de un
	 * valor de lista seleccionado por el usuario en la grilla de la tabla admon.
	 */
	private DataTable selectedAdmon;
	/**
	 * Atributo selectedPredio Objeto DatosPredioDataTable que contiene la
	 * informacion seleccionada por el usuario en la grilla de la tabla datos
	 * predio.
	 */
	private DatosPredioDataTable selectedPredio;
	/**
	 * Atributo selectedJuridico Objeto DatosJuridicoDataTable que contiene la
	 * informacion seleccionada por el usuario en la grilla de la tabla datos
	 * juridicos.
	 */
	private DatosJuridicoDataTable selectedJuridico;
	/**
	 * Atributo selectedConsult Objeto AttributeDataTable que contiene la
	 * informacion seleccionada por el usuario en la grilla de la tabla de
	 * consultas.
	 */
	private AttributeDataTable selectedConsult;
	/**
	 * Atributo selectedTable Lista de AttributeDataTable que contiene la
	 * informacion seleccionada por el usuario en la grilla de la tabla de
	 * atributos.
	 */
	private List<AttributeDataTable> selectedTable;
	/**
	 * Variable checkAdmon boolean que contiene el estado del valor de la lista.
	 */
	private boolean checkAdmon;
	/**
	 * Variable inputAdmon String que contiene el valor ingresado por el usuario del
	 * nuevo valor de una lista.
	 */
	private String inputAdmon;
	/**
	 * Variable checkAdmonBus boolean que activa la casilla de ingreso de codigo del
	 * bus para el usuario.
	 */
	private boolean checkAdmonBus;
	/**
	 * Variable inputAdmonBus String que contiene el valor ingresado por el usuario
	 * del codigo del bus del valor si aplica.
	 */
	private String inputAdmonBus;
	/**
	 * Variable inputAdmon2 int que contiene la cantidad de registro de consultapor
	 * interesado a modificar.
	 */
	private int inputAdmon2;
	/**
	 * Variable inputAdmon3 int que contiene la cantidad de registro de consulta csv
	 * a modificar.
	 */
	private int inputAdmon3;
	/**
	 * Variable inputAdmon4 int que contiene la cantidad de registro de consulta txt
	 * a modificar.
	 */
	private int inputAdmon4;
	/**
	 * Variable inputAdmon5 int que contiene la cantidad de registro de consulta de
	 * atributos a modificar.
	 */
	private int inputAdmon5;

	/**
	 * Variable name String que contiene el nombre del usuario activo.
	 */
	private String name;
	/**
	 * Variable queryVal String que contiene el primer valor digitado por el usuario
	 * para la consulta.
	 */
	private String queryVal;
	/**
	 * Variable queryVal2 String que contiene el segundo valor digitado por el
	 * usuario para la consulta.
	 */
	private String queryVal2;
	/**
	 * Variable queryValMin String que contiene el valor minimo digitado por el
	 * usuario para la consulta.
	 */
	private String queryValMin;
	/**
	 * Variable queryValMax String que contiene el valor maximo digitado por el
	 * usuario para la consulta.
	 */
	private String queryValMax;
	/**
	 * Variable typeName String que contiene el tipo de nombre.
	 */
	private String typeName;
	/**
	 * Variable btnAdmdEst boolean que contiene el estado del boton modificar en
	 * administracion.
	 */
	private boolean btnAdmdEst = true;
	/**
	 * Variable inputAdmdEst boolean que contiene el estado de la casilla del codigo
	 * asociado al bus en administracion.
	 */
	private boolean inputAdmdEst = true;

	private List<AttributeDataTable> dataTable;
	/**
	 * Variable admonTable Lista de DataTable que contiene los datos de la tabla en
	 * administracion.
	 */
	private List<DataTable> admonTable;
	/**
	 * Variable datosPredio Lista de DatosPredioDataTable que contiene los datos de
	 * la tabla predio en el poup.
	 */
	private List<DatosPredioDataTable> datosPredio;
	/**
	 * Variable datosPredioM Lista de DatosPredioDataTable que contiene los datos
	 * del predio.
	 */
	private List<DatosPredioDataTable> datosPredioM;
	/**
	 * Variable datosJuridico Lista de DatosJuridicoDataTable que contiene los datos
	 * de la tabla juridico en el poup.
	 */
	private List<DatosJuridicoDataTable> datosJuridico;
	/**
	 * Variable datosJuridicoM Lista de DatosJuridicoDataTable que contiene los
	 * datos juridicos.
	 */
	private List<DatosJuridicoDataTable> datosJuridicoM;
	/**
	 * Variable datosAnotaciones Lista de AnotacionesDataTable que contiene los
	 * datos de la tabla anotaciones en el poup.
	 */
	private List<AnotacionesDataTable> datosAnotaciones;
	/**
	 * Variable datosAnotacionesM Lista de AnotacionesDataTable que contiene los
	 * datos de las anotaciones.
	 */
	private List<AnotacionesDataTable> datosAnotacionesM;
	/**
	 * Variable list Lista de DataTable que contiene los datos de la lista de
	 * busqueda.
	 */
	private List<DataTable> list;
	/**
	 * Variable list2 Lista de DataTable que contiene los datos de la lista de
	 * busqueda.
	 */
	private List<ListaValores> list2;
	/**
	 * Variable busqsBus Lista de BusquedaBus que contiene los datos de la lista de
	 * busqueda de bus en administracion.
	 */
	private List<BusquedaBus> busqsBus;
	/**
	 * Variable busqBus String que contiene el tipo de busqueda seleccionado por el
	 * usuario.
	 */
	private String busqBus;
	/**
	 * Variable dpto String que contiene el departamento seleccionado por el
	 * usuario.
	 */
	private String dpto;
	/**
	 * Variable dptos Lista de Departamento que contiene los departamentos.
	 */
	private List<Departamento> dptos;
	/**
	 * Variable mpio String que contiene el municipio seleccionado por el usuario.
	 */
	private String mpio;
	/**
	 * Variable mpios Lista de Municipios que contiene los municipios.
	 */
	private List<Municipios> mpios;
	/**
	 * Variable departments Lista de String que contiene los departamentos.
	 */
	private List<String> departments;

	/**
	 * Variable background String que contiene el background del mapa.
	 */
	private String background;
	/**
	 * Variable latitude String que contiene la latitude del mapa.
	 */
	private double latitude;
	/**
	 * Variable longitude String que contiene la longitude del mapa.
	 */
	private double longitude;
	/**
	 * Variable opacity Double que contiene el valor de la opacidad del mapa
	 * seleccionada por el usuario.
	 */
	private double opacity;
	/**
	 * Variable opacityCal Double que contiene el valor calculado de la opacidad del
	 * mapa conforme a arcgis.
	 */
	private double opacityCal;
	/**
	 * Variable opacityCal2 Double que contiene el valor calculado de la opacidad
	 * del mapa conforme a arcgis.
	 */
	private double opacityCal2;
	/**
	 * Variable opacity2 Double que contiene el valor de la opacidad del mapa
	 * seleccionada por el usuario.
	 */
	private double opacity2;
	/**
	 * Variable zoom int que contiene el valor del acercamiento en el mapa.
	 */
	private int zoom;
	/**
	 * Variable layers Arreglo de boolean que contiene el estado de las capas.
	 */
	private boolean[] layers = new boolean[10];
	/**
	 * Variable layers2 Arreglo de boolean que contiene el estado de las capas.
	 */
	private boolean[] layers2 = new boolean[10];
	/**
	 * Variable visible boolean que contiene el estado de visibilidad de un
	 * componente en el mapa.
	 */
	private boolean visible;
	/**
	 * Variable moveUpM1 boolean que contiene el estado de la accion del menu 1
	 * subir capa.
	 */
	private boolean moveUpM1 = true;
	/**
	 * Variable moveDownM1 boolean que contiene el estado de la accion del menu 1
	 * bajar capa.
	 */
	private boolean moveDownM1 = false;
	/**
	 * Variable moveUpM2 boolean que contiene el estado de la accion del menu 2
	 * subir capa.
	 */
	private boolean moveUpM2 = false;
	/**
	 * Variable moveDownM2 boolean que contiene el estado de la accion del menu 2
	 * bajar capa.
	 */
	private boolean moveDownM2 = true;
	/**
	 * Variable where String que contiene un condicional de busqueda.
	 */
	private String where;
	/**
	 * Variable extent String que contiene una seleccion de parametro en el mapa.
	 */
	private String extent;
	/**
	 * Variable airports boolean que contiene el estado de la visualizacion de los
	 * aeropuertos.
	 */
	private boolean airports;
	/**
	 * Variable radar boolean que contiene el estado del radar.
	 */
	private boolean radar;
	/**
	 * Variable geoip String que la ip de la ubicacion geografica.
	 */
	private String geoip;
	/**
	 * Atributo geoipGraphicsModel Objeto GraphicsModel que contiene la informacion
	 * del modelo de graficos.
	 */
	private GraphicsModel geoipGraphicsModel;
	/**
	 * Atributo graphicsModel Objeto GraphicsModel que contiene la informacion del
	 * modelo de graficos para el mapa.
	 */
	private GraphicsModel graphicsModel;
	/**
	 * Atributo starbucksGraphicsModel Objeto GraphicsModel que contiene la
	 * informacion del modelo de graficos.
	 */
	private GraphicsModel starbucksGraphicsModel;
	/**
	 * Variable urlService String que contiene la url del servicio arcgis para el
	 * cargue del servicio 1.
	 */
	private String urlService;
	/**
	 * Variable urlService String que contiene la url del servicio arcgis para el
	 * cargue del servicio 2.
	 */
	private String urlService2;
	/**
	 * Variable url String que contiene la url del servicio arcgis para el cargue
	 * del servicio 2.
	 */
	private String url;
	/**
	 * Variable url2 String que contiene la url del servicio arcgis para el cargue
	 * del servicio 2.
	 */
	private String url2;
	/**
	 * Variable layerVisualize String que contiene el estado de la accion del menu 1
	 * activar o desactivar capas.
	 */
	private String layerVisualize;
	/**
	 * Variable layerVisualize2 String que contiene el estado de la accion del menu
	 * 2 activar o desactivar capas.
	 */
	private String layerVisualize2;
	/**
	 * Variable urlGisServiceU String que contiene el la url del servicio arcgis con
	 * la capa urbana.
	 */
	private String urlGisServiceU;
	/**
	 * Variable urlGisServiceR String que contiene el la url del servicio arcgis con
	 * la capa rural.
	 */
	private String urlGisServiceR;
	/**
	 * Variable featureLayers Lista de String que contiene las capas del serrvicio.
	 */
	private List<String> featureLayers;
	/**
	 * Variable featureLayers2 Lista de String que contiene las capas del serrvicio
	 * 2.
	 */
	private List<String> featureLayers2;
	/**
	 * Atributo treeNodeServices Objeto de TreeNode que contiene los nodos de las
	 * capas.
	 */
	private TreeNode treeNodeServices;
	/**
	 * Atributo treeNodeServices2 Objeto de TreeNode que contiene los nodos de las
	 * capas.
	 */
	private TreeNode treeNodeServices2;
	/**
	 * Atributo selectedNode Arreglo de TreeNode que contiene los nodos de las capas
	 * seleccionadas.
	 */
	private TreeNode[] selectedNode;
	/**
	 * Atributo selectedNode2 Arreglo de TreeNode que contiene los nodos de las
	 * capas seleccionadas.
	 */
	private TreeNode[] selectedNode2;
	/**
	 * Atributo treeNodeOGCServices Objeto de TreeNode que contiene los nodos de las
	 * capas OGC.
	 */
	private TreeNode treeNodeOGCServices;
	/**
	 * Variable initialValues HashMap de String que contiene los valores iniciales
	 * de la aplicacion.
	 */
	private HashMap<String, String> initialValues;

	/**
	 * Variable urlList ArrayList de String que contiene las urls del servicio de
	 * arcgis.
	 */
	private ArrayList<String> urlList;
	/**
	 * Variable codeListString String que contiene la cedula catastarl que se
	 * comparte entre jsf y js.
	 */
	private String codeListString;
	/**
	 * Variable tipoConsultaLocalizacion String que contiene el tipo de consulta en
	 * localizacion.
	 */
	private String tipoConsultaLocalizacion;
	/**
	 * Variable capasLocalesString String que contiene las capas locales que se
	 * comparten entre jsf y js.
	 */
	private String capasLocalesString;
	/**
	 * Variable capasLocalesItems Lista de SelectItem que contiene los items de las
	 * capas locales.
	 */
	private List<SelectItem> capasLocalesItems = new ArrayList<>();
	/**
	 * Variable capasLocalesSelected String que contiene la seleccion por el usuario
	 * de capas locales.
	 */
	private String capasLocalesSelected;
	/**
	 * Variable capasOGCString String que contiene las capas ogc que se comparten
	 * entre jsf y js.
	 */
	private String capasOGCString;
	/**
	 * Variable capasOGCItems Lista de SelectItem que contiene los items de las
	 * capas ogc.
	 */
	private List<SelectItem> capasOGCItems = new ArrayList<>();
	/**
	 * Variable capasOGCMap Mapa de String y Capa que contiene las capas ogc.
	 */
	private Map<String, Capa> capasOGCMap;
	/**
	 * Variable capasOGCSelected String que contiene la capa ogc seleccionada por el
	 * usuario.
	 */
	private String capasOGCSelected;
	/**
	 * Variable exportarValor String que contiene el tipo de exportacion del mapa.
	 */
	private String exportarValor;
	/**
	 * Variable imprimirNombre String que contiene el nombre del archivo a exportar.
	 */
	private String imprimirNombre;
	/**
	 * Variable imprimirFormato String que contiene el formato del archivo a
	 * exportar.
	 */
	private String imprimirFormato;
	/**
	 * Variable imprimirDiseno String que contiene el tamaño del diseño a exportar.
	 */
	private String imprimirDiseno;
	/**
	 * Variable imprimirScalebarUnit String que contiene la unidad de la escala en
	 * el diseño a exportar.
	 */
	private String imprimirScalebarUnit = "Kilometers";
	/**
	 * Variable imprimirWidth String que contiene el ancho del diseño a exportar.
	 */
	private int imprimirWidth = 0;
	/**
	 * Variable imprimirHeight String que contiene el largo del diseño a exportar.
	 */
	private int imprimirHeight = 0;
	/**
	 * Variable imprimirDPI String que contiene los dpi del diseño a exportar.
	 */
	private int imprimirDPI = 256;
	/**
	 * Variable imprimirLegend boolean que contiene el estado de la inclusion de
	 * convenciones en el diseño a exportar.
	 */
	private boolean imprimirLegend = true;
	/**
	 * Variable exportarCapa String que contiene la capa a exportar.
	 */
	private String exportarCapa;
	/**
	 * Variable exportarFormato String que contiene el formato del archivo de datos
	 * a exportar.
	 */
	private String exportarFormato;
	/**
	 * Variable servicioTipo String que contiene el tipo de servicio a importar.
	 */
	private String servicioTipo;
	/**
	 * Variable servicioURL String que contiene la url del servicio a importar.
	 */
	private String servicioURL;

	@EJB
	private VeredaDao veredaDao;

	@EJB
	private IGisParametrosDao gisParametrosDao;

	@EJB
	private IConsultationDao consInterest;

	@EJB
	private IResourceBD resourceBD;

	@EJB
	private IAdministracionDao admon;

	@EJB
	private IGestionUsuarioDao gesUser;

	/**
	 * Metodo constructor que inicializa la vista con los parametros por defecto
	 * definiddos.
	 */
	public MapBean() {
		super();
		log.info(Constants.MENSAJE_MAPBEAN_1);
	}

	/**
	 * Método para iniciar parámetros del portal GIS
	 */
	@PostConstruct
	public void init() {
		log.info(Constants.MENSAJE_MAPBEAN_2);
		setupInitialValues();
//		List<ConstantesBachue> parameters = gisParametrosDao.getAll();
//		for (ConstantesBachue p : parameters) {
//			log.info(p.getNombre() + " :: " + p.getValor());
//			if (initialValues.containsKey(p.getNombre())) {
//				log.info("Setting :: " + p.getNombre() + " to :: " + p.getValor());
//				initialValues.put(p.getNombre(), p.getValor());
//			}
//		}
	    //this.name = "bachue02";
		//boolean estado = true;
		
		this.name = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
		boolean estado = true;//gesUser.accesoUsuario(this.name);
		

		if (estado) {
			urlList = new ArrayList<String>();

			urlList.add(initialValues.get(Constants.URL_SERVICE));
			urlList.add(initialValues.get(Constants.UNIFORMRL));
			urlList.add(initialValues.get(Constants.URL_SERVICE));
			urlList.add(initialValues.get(Constants.UNIFORMRL));

			this.latitude = Double.parseDouble(initialValues.get(Constants.LATITUD));
			this.longitude = Double.parseDouble(initialValues.get(Constants.LONGITUD));
			log.info(Constants.LATITUD + "::  " + String
					.valueOf(this.getLatitude() + Constants.LONGITUD + "::  " + String.valueOf(this.getLongitude())));
			this.background = initialValues.get(Constants.BACKGR);
			busqsBus = null;
			busqsBus = new ArrayList<BusquedaBus>(admon.BusquedaB());
			this.urlGisServiceU = gisParametrosDao.getValue(Constants.URL_ARCGIS) + Constants.ZONA_URBANA;
			this.urlGisServiceR = gisParametrosDao.getValue(Constants.URL_ARCGIS) + Constants.ZONA_RURAL;
			List<AttributeDataTable> dt = new ArrayList<AttributeDataTable>();
			dt.add(new AttributeDataTable(Constants.MENSAJE_MAPBEAN_3, Constants.MENSAJE_MAPBEAN_3,
					Constants.MENSAJE_MAPBEAN_3, Constants.MENSAJE_MAPBEAN_3, Constants.MENSAJE_MAPBEAN_3,
					Constants.MENSAJE_MAPBEAN_3, Constants.MENSAJE_MAPBEAN_3, Constants.MENSAJE_MAPBEAN_3,
					Constants.MENSAJE_MAPBEAN_3, Constants.MENSAJE_MAPBEAN_3, Constants.MENSAJE_MAPBEAN_3,
					Constants.MENSAJE_MAPBEAN_3));
			this.setDataTable(dt);
			reset();
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			Object session = externalContext.getSession(false);
			HttpSession httpSession = (HttpSession) session;
			httpSession.invalidate();
			String url = Constants.PAGINA_ERROR;
			FacesContext fc = FacesContext.getCurrentInstance();
			try {
				fc.getExternalContext().redirect(url);
			} catch (IOException e) {
				log.warn(Constants.MENSAJE_SESION_2 + e);
			}

		}
	}

	/**
	 * Método para asignar Valores iniciales
	 */
	private void setupInitialValues() {

		log.info("assingInitialValues..");
//		initialValues = new HashMap<String, String>();
//		initialValues.put(URL_SERVICE,"http://192.168.36.18:6080/arcgis/rest/services/san_vicente/San_Vicente/MapServer");
//		initialValues.put(UNIFORMRL, "http://192.168.36.18:6080/arcgis/rest/services/san_vicente/");
//		initialValues.put(LATITUD, "4.628175");
//		initialValues.put(LONGITUD, "-74.55");
//		initialValues.put(BACKGR, "streets");

		initialValues = new HashMap<String, String>();
		initialValues.put(Constants.URL_SERVICE, gisParametrosDao.getValue(Constants.URL_ARCGIS));
		initialValues.put(Constants.UNIFORMRL, gisParametrosDao.getValue(Constants.URL_FORM_ARCGIS));
		initialValues.put(Constants.LATITUD, gisParametrosDao.getValue(Constants.LATITUD_ARGIS));
		initialValues.put(Constants.LONGITUD, gisParametrosDao.getValue(Constants.LONGITUD_ARCGIS));
		initialValues.put(Constants.BACKGR, gisParametrosDao.getValue(Constants.BACKGR_ARCGIS));
		this.tipoConsultaLocalizacion = Constants.CAPA;
		this.exportarFormato = Constants.FORMATO_SHP;
	}

	/**
	 * Método para Reiniciar valores iniciales
	 */
	public void reset() {
		log.info(Constants.MENSAJE_MAPBEAN_4);
		this.zoom = 4;
		this.opacityCal = 100;
		this.opacityCal2 = 0;
		this.opacity = 1;
		this.opacity2 = 0;
		Arrays.fill(layers, Boolean.TRUE);
		Arrays.fill(layers2, Boolean.TRUE);
		this.layerVisualize = Constants.LAYERS_OFF;
		this.layerVisualize2 = Constants.LAYERS_OFF;
		this.visible = true;
		this.where = Constants.WHERE_MAGNITUDE;
		this.extent = Constants.EXTENT_MAGNITUDE;
		this.airports = true;
		this.radar = false;
		this.geoip = null;
		this.geoipGraphicsModel = null;
		this.graphicsModel = null;
		this.starbucksGraphicsModel = null;
		this.geoipGraphicsModel = new GraphicsModel();
		this.geoipGraphicsModel.setName(Constants.GEOCODED_IP);

		this.buildGraphicsModel();

		this.featureLayers = null;
		this.featureLayers2 = null;

		this.selectedNode = null;
		this.selectedNode2 = null;

		this.urlService = urlList.get(0);
		this.urlService2 = urlList.get(2);

		ServiceDirectoryMetadata serviceDirectory = null;
		ServiceDirectoryMetadata serviceDirectory2 = null;
		try {
			serviceDirectory = null;
			serviceDirectory2 = null;
			ServiceDirectoryMetadataBuilder builder = new ServiceDirectoryMetadataBuilder();
			ServiceDirectoryMetadataBuilder builder2 = new ServiceDirectoryMetadataBuilder();
			serviceDirectory = builder.build(urlList.get(1));
			serviceDirectory2 = builder2.build(urlList.get(3));

		} catch (Exception e) {
			JSFUtilities.addErrorMessage(e.getMessage());
			log.error(e.getMessage());
		}
		treeNodeServices = null;
		treeNodeServices2 = null;
		//treeNodeServices = createTreeNode(serviceDirectory);
		//treeNodeServices2 = createTreeNode2(serviceDirectory2);

		statesAdmon = null;
		statesAdmon = new ArrayList<String>();
		statesAdmon.add(Constants.ESTADO_ACTIVO_ADMON);
		statesAdmon.add(Constants.ESTADO_INACTIVO_ADMON);
	}

	/**
	 * Metodo que obtiene la ip del usuario
	 * 
	 * @param request El parámetro request define un objeto HttpServletRequest que
	 *                contiene los datos de la peticion
	 * @return Un String que contiene la ip del usuario.
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ip;

		final String[] HEADER = { Constants.HEADER_LIST_1, Constants.HEADER_LIST_1_1, Constants.HEADER_LIST_1_2 };
		for (String h : HEADER) {
			ip = request.getHeader(h);
			if (ip != null) {
				String[] lsa_forwarded = ip.trim().split(" ");
				if (lsa_forwarded.length > 0 && lsa_forwarded[0].trim().length() > 0) {
					ip = lsa_forwarded[0];
					return ip;
				}
			}
		}
		final String[] HEADER_LIST = { Constants.HEADER_LIST_2, Constants.HEADER_LIST_3, Constants.HEADER_LIST_4,
				Constants.HEADER_LIST_5, Constants.HEADER_LIST_6, Constants.HEADER_LIST_7, Constants.HEADER_LIST_8,
				Constants.HEADER_LIST_9, Constants.HEADER_LIST_10, Constants.HEADER_LIST_11 };

		for (String header : HEADER_LIST) {
			ip = request.getHeader(header);
			if (ip != null && ip.length() != 0 && !Constants.UNKNOWN.equalsIgnoreCase(ip)) {
				return ip;
			}
		}

		return request.getRemoteAddr();
	}

	public void logout() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		
		ExternalContext externalContext = context.getExternalContext();
		Object session = externalContext.getSession(false);
		HttpSession httpSession = (HttpSession) session;
		httpSession.invalidate();
		context.getExternalContext().invalidateSession();
		
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		request.getSession().invalidate();		
		weblogic.servlet.security.ServletAuthentication.logout(request);
		weblogic.servlet.security.ServletAuthentication.invalidateAll(request);
		weblogic.servlet.security.ServletAuthentication.done(request);

		String url = Constants.PAGINA_ERROR;
		try {
			context.getExternalContext().redirect(url);
		} catch (IOException e) {
			log.warn(Constants.MENSAJE_SESION_2 + e);
		}
	}

	/**
	 * Método para retornar la lista de mapas base
	 * 
	 * @return la lista de mapas base
	 */
	public List<SelectItem> getBackgrounds() {
		return Background.getSelectItems();
	}

	/**
	 * 
	 * Método para escuchar los eventos del mapa al dar clic
	 * 
	 * @param event evento
	 */
	public void doMapClickListener(AjaxBehaviorEvent event) {
		MapClickEvent e = (MapClickEvent) event;

		String summary = "Map Click Event";
		String detail = String.format(
				"Latitude='%s', Longitude='%s', Zoom='%s', Scale='%s', XMin='%s', YMin='%s', XMax='%s', YMax='%s', Height='%s', Width='%s', X='%s', Y='%s'",
				e.getLatitude(), e.getLongitude(), e.getZoom(), e.getScale(), e.getExtent().getXmin(),
				e.getExtent().getYmin(), e.getExtent().getXmax(), e.getExtent().getYmax(), e.getScreen().getHeight(),
				e.getScreen().getWidth(), e.getScreen().getX(), e.getScreen().getY());

		System.out.println(String.format("%s: %s", summary, detail));
		JSFUtilities.addInfoMessage(summary, detail);
	}

	/**
	 * 
	 * Método para escuchar el evento al cambiar el extent del mapa
	 * 
	 * @param event evento
	 */
	public void doMapExtentListener(AjaxBehaviorEvent event) {
		MapExtentEvent e = (MapExtentEvent) event;

		String summary = "Map Extent Update Event";
		String detail = String.format(
				"Latitude='%s', Longitude='%s', Zoom='%s', Scale='%s', XMin='%s', YMin='%s', XMax='%s', YMax='%s', Height='%s', Width='%s'",
				e.getLatitude(), e.getLongitude(), e.getZoom(), e.getScale(), e.getExtent().getXmin(),
				e.getExtent().getYmin(), e.getExtent().getXmax(), e.getExtent().getYmax(), e.getScreen().getHeight(),
				e.getScreen().getWidth());

		// System.out.println(String.format("%s: %s", summary, detail));
		log.info(String.format("%s: %s", summary, detail));
		JSFUtilities.addInfoMessage(summary, detail);
	}

	/**
	 * Método para escuchar el evento al hacer clic en un grafico
	 * 
	 * @param event evento
	 */
	public void doMapGraphicViewListener(AjaxBehaviorEvent event) {
		MapGraphicViewEvent e = (MapGraphicViewEvent) event;

		String summary = "Map Graphic View Event";
		String detail = String.format("Service ID='%s', Layer ID='%s', Graphics ID='%s', Attributes='%s'",
				e.getServiceId(), e.getLayerId(), e.getGraphicId(), e.getAttributesJson());

		// System.out.println(String.format("%s: %s", summary, detail));
		log.info(String.format("%s: %s", summary, detail));
		JSFUtilities.addInfoMessage(summary, detail);
	}

	/**
	 * Método para escuchar el evento al hacer clic en un grafico
	 * 
	 * @param event evento
	 */
	public void doMapGraphicActionListener(AjaxBehaviorEvent event) {
		MapGraphicActionEvent e = (MapGraphicActionEvent) event;

		String summary = "Map Graphic Action Event";
		String detail = String.format("Action='%s', Service ID='%s', Layer ID='%s', Graphics ID='%s', Attributes='%s'",
				e.getAction(), e.getServiceId(), e.getLayerId(), e.getGraphicId(), e.getAttributesJson());

		// System.out.println(String.format("%s: %s", summary, detail));
		log.info(String.format("%s: %s", summary, detail));
		JSFUtilities.addInfoMessage(summary, detail);
	}

	/**
	 * Método para escuchar el evento al hacer clic en un grafico
	 * 
	 * @param event evento
	 */
	public void doMapGraphicDragListener(AjaxBehaviorEvent event) {
		MapGraphicDragEvent e = (MapGraphicDragEvent) event;

		String summary = "Map Graphic Drag Event";
		String detail = String.format(
				"Service ID='%s', Graphic ID='%s', Type='%s', Latitude='%s', Longitude='%s', Attributes='%s'",
				e.getServiceId(), e.getGraphicId(), e.getType(), e.getLatitude(), e.getLongitude(),
				e.getAttributesJson());

		// System.out.println(String.format("%s: %s", summary, detail));
		log.info(String.format("%s: %s", summary, detail));
		JSFUtilities.addInfoMessage(summary, detail);
	}

	/**
	 * Método para escuchar el evento al geocodificar una dirección
	 * 
	 * @param event evento
	 */
	public void doMapGeoLocationListener(AjaxBehaviorEvent event) {
		MapGeoLocationEvent e = (MapGeoLocationEvent) event;

		String summary = "Map Geo-Location Event";
		String detail = String.format(
				"Latitude='%s', Longitude='%s', Zoom='%s', Heading='%s', Speed='%s', Altitude='%s', Timestamp='%s'",
				e.getLatitude(), e.getLongitude(), e.getZoom(), e.getHeading(), e.getSpeed(), e.getAltitude(),
				e.getTimestamp());

		// System.out.println(String.format("%s: %s", summary, detail));
		log.info(String.format("%s: %s", summary, detail));
		JSFUtilities.addInfoMessage(summary, detail);
	}

	/**
	 * Evento si dispara cuando geodifica a traves de url
	 * 
	 * @param event evento
	 */
	public void geoIpActionListener(ActionEvent event) {
		log.info("Map bean geocode ip action listener fired.");

		try {
			// Build the url to the geocode service and request json format.
			String urlJson = String.format("http://freegeoip.net/json/%s", this.geoip);

			// Get the result.
			String json = GISUtilities.getUrlConnectionOutput(urlJson);

			// Parse the json result.
			Map<String, Object> attribs = StringUtilities.toMap(json);

			if (attribs.containsKey("latitude") && attribs.containsKey("longitude")) {
				double latitudeJson = Double.valueOf(attribs.get("latitude").toString());
				double longitudeJson = Double.valueOf(attribs.get("longitude").toString());

				// Create a new marker.
				PictureMarkerGraphic marker = new PictureMarkerGraphic();
				marker.setCoordinate(new Coordinate(latitudeJson, longitudeJson));
				marker.setAttributes(attribs);

				// Add the new marker to the graphics model.
				this.getGeoipGraphicsModel().getGraphics().add(marker);

				// Refresh the graphics model.
				this.getGeoipGraphicsModel().setRefresh(true);

				// Zoom to the coordinates.
				this.latitude = latitudeJson;
				this.longitude = longitudeJson;
				this.zoom = 10;
			}
		} catch (IOException e) {
			JSFUtilities.addErrorMessage("Error during geocode.", e.getMessage());
		}
	}

	/**
	 * Limpiar el mapa
	 * 
	 * @param event evento
	 */
	public void resetActionListener(ActionEvent event) {
		log.info("Map bean reset action listener fired.");
		this.reset();
	}

	/**
	 * Agregar graficos en el mapa
	 */
	private void buildGraphicsModel() {
		String marker = "marker";
		PictureMarkerGraphic marker1 = new PictureMarkerGraphic();
		marker1.setId("111");
		marker1.setType(marker);
		marker1.setCoordinate(new Coordinate(30.33, -81.66));
		marker1.setImage(
				String.format("%s/esri/dijit/GeocodeMatch/images/EsriGreenPinCircle26.png", Constants.DEFAULT_JSAPI));
		marker1.setHeight(26);
		marker1.setWidth(26);
		marker1.getAttributes().put("Picture Marker", "Green");
		marker1.setDraggable(true);

		PictureMarkerGraphic marker2 = new PictureMarkerGraphic();
		marker2.setId("222");
		marker2.setType(marker);
		marker2.setCoordinate(new Coordinate(30.33, -81.66));
		marker2.setImage(
				String.format("%s/esri/dijit/GeocodeMatch/images/EsriYellowPinCircle26.png", Constants.DEFAULT_JSAPI));
		marker2.setHeight(26);
		marker2.setWidth(26);
		marker2.getAttributes().put("Picture Marker", "Yellow");
		marker2.setDraggable(true);

		PictureMarkerGraphic marker3 = new PictureMarkerGraphic();
		marker3.setId("333");
		marker3.setType(marker);
		marker3.setCoordinate(new Coordinate(30.33, -81.66));
		marker3.setImage(
				String.format("%s/esri/dijit/GeocodeMatch/images/EsriBluePinCircle26.png", Constants.DEFAULT_JSAPI));
		marker3.setHeight(26);
		marker3.setWidth(26);
		marker3.getAttributes().put("City", "Jacksonville");
		marker3.getAttributes().put("State", "Florida");
		marker3.getAttributes().put("Website", "http://www.myflorida.com/");
		marker3.getAttributes().put("Seal", "http://dos.myflorida.com/media/8081/flag.jpg");
		marker3.setDraggable(true);

		SvgMarkerGraphic marker4 = new SvgMarkerGraphic();
		marker4.setId("444");
		marker4.setType(marker);
		marker4.setCoordinate(new Coordinate(30.33, -81.66));
		marker4.setFillColor("#FF0000");
		marker4.setFillOpacity(0.5);
		marker4.getAttributes().put("SVG Marker", "Red");
		marker4.setDraggable(true);

		TextGraphic text = new TextGraphic();
		text.setId("555");
		text.setType("text");
		text.setCoordinate(new Coordinate(30.33, -81.66));
		text.setText("Jacksonville");
		text.setFontSize("24");
		text.setFontFamily("Verdana");
		text.setFontBold(false);
		text.setFontColor("#000000");
		text.setHaloColor("#808080");
		text.setHaloSize(5);
		text.setOpacity(0.75);
		text.getAttributes().put("Location", "Downtown Jacksonville");
		text.setHorizontalOffset(20);
		text.setVerticalOffset(-8);
		text.setHorizontalAlignment(TextHorizontalAlignment.LEFT);

		PolylineGraphic polyline = new PolylineGraphic();
		polyline.setId("666");
		polyline.setType("polyline");
		polyline.getCoordinates().add(new Coordinate(30.324073168632953, -81.6584550476046));
		polyline.getCoordinates().add(new Coordinate(30.320813258514306, -81.65879837035853));
		polyline.setStyle(LineStyle.SOLID);
		polyline.setColor("#0000FF");
		polyline.setOpacity(0.5);
		polyline.setWidth(5);
		polyline.getAttributes().put("Location", "The Main Street Bridge");
		polyline.getAttributes().put("Polyline Setting", "Value");
		polyline.getAttributes().put("setColor()", polyline.getColor());
		polyline.getAttributes().put("setOpacity()", polyline.getOpacity());
		polyline.getAttributes().put("setWidth()", polyline.getWidth());

		PolygonGraphic polygon = new PolygonGraphic();
		polygon.setId("777");
		polygon.setType("polygon");
		polygon.getCoordinates().add(new Coordinate(30.32462882430922, -81.66111579894871));
		polygon.getCoordinates().add(new Coordinate(30.325202998529473, -81.66095486640785));
		polygon.getCoordinates().add(new Coordinate(30.325453041089542, -81.65989271163804));
		polygon.getCoordinates().add(new Coordinate(30.324897390089276, -81.65879837036005));
		polygon.getCoordinates().add(new Coordinate(30.324212082847886, -81.65900221824515));
		polygon.getCoordinates().add(new Coordinate(30.32462882430922, -81.66111579894871));
		polygon.setFillColor("#FF0000");
		polygon.setFillOpacity(0.25);
		polygon.setOutlineStyle(LineStyle.SHORTDASHDOT);
		polygon.setOutlineColor("#FF0000");
		polygon.getAttributes().put("Location", "The Jacksonville Landing");
		polygon.getAttributes().put("Polylgon Setting", "Value");
		polygon.getAttributes().put("setFillColor()", polygon.getFillColor());
		polygon.getAttributes().put("setFillOpacity()", polygon.getFillOpacity());
		polygon.getAttributes().put("setOutlineColor()", polygon.getOutlineColor());
		polygon.getAttributes().put("setOutlineOpacity()", polygon.getOutlineOpacity());
		polygon.getAttributes().put("setOutlineStyle()", "LineStyle.SHORTDASHDOT");

		CircleGraphic circle = new CircleGraphic();
		circle.setId("888");
		circle.setType("circle");
		circle.setCoordinate(new Coordinate(30.32003530938606, -81.65982833862175));
		circle.setFillColor("#00FF00");
		circle.setFillOpacity(0.25);
		circle.setOutlineStyle(LineStyle.SOLID);
		circle.setOutlineColor("#00FF00");
		circle.getAttributes().put("Location", "Friendship Park Fountain");
		circle.setRadius(50);
		circle.setRadiusUnit(MeasurementUnit.METERS);

		this.graphicsModel = new GraphicsModel();
		this.graphicsModel.setName("Sample Graphics");
		this.graphicsModel.getGraphics().add(marker1);
		this.graphicsModel.getGraphics().add(marker2);
		this.graphicsModel.getGraphics().add(marker3);
		this.graphicsModel.getGraphics().add(marker4);
		this.graphicsModel.getGraphics().add(polyline);
		this.graphicsModel.getGraphics().add(polygon);
		this.graphicsModel.getGraphics().add(text);
		this.graphicsModel.getGraphics().add(circle);
	}

	/**
	 * Metodo que obtiene las capas del servicio de arcgis
	 * 
	 * @param directoryMetadata El parámetro directoryMetadata define un objeto
	 *                          ServiceDirectoryMetadata que contiene los datos de
	 *                          la peticion
	 * @return Un objeto TreeNode que contiene la las capas del servicio.
	 */
	public TreeNode createTreeNode(ServiceDirectoryMetadata directoryMetadata) {
		TreeNode root = null;
		root = new CheckboxTreeNode(new LayerBean("-1", "root", "parent", "null"), null);

		for (ServiceMetadata serviceMetadata : directoryMetadata.getServices()) {
			TreeNode rootService = new CheckboxTreeNode(
					new LayerBean("-1", serviceMetadata.getMapName(), "Service", "null"), root);
			HashMap<String, TreeNode> mapGroups = new HashMap<String, TreeNode>();
			HashMap<String, ServiceLayerMetadata> mapLyrs = new HashMap<String, ServiceLayerMetadata>();
			for (ServiceLayerMetadata layerMetadata : serviceMetadata.getLayers()) {
				if (layerMetadata.getType().equals("Group Layer")) {
					TreeNode groupNode = new CheckboxTreeNode(
							new LayerBean(layerMetadata.getId(), layerMetadata.getName(), "Group Layer", "null"),
							rootService);
					mapGroups.put(layerMetadata.getId(), groupNode);
				} else {
					mapLyrs.put(layerMetadata.getId(), layerMetadata);
				}
			}
			Iterator<Entry<String, ServiceLayerMetadata>> it = mapLyrs.entrySet().iterator();
			featureLayers = new ArrayList<String>();
			while (it.hasNext()) {
				Map.Entry<String, ServiceLayerMetadata> pair = it.next();
				ServiceLayerMetadata layerMetadata = pair.getValue();
				if (mapGroups.containsKey(layerMetadata.getParentLayerId())) {
					TreeNode groupNode = mapGroups.get(layerMetadata.getParentLayerId());
					new CheckboxTreeNode(
							new LayerBean(layerMetadata.getId(), layerMetadata.getName(), "Feature Layer", "null"),
							groupNode);
				} else if (layerMetadata.getParentLayerId().equals("-1")) {
					new CheckboxTreeNode(
							new LayerBean(layerMetadata.getId(), layerMetadata.getName(), "Feature Layer", "null"),
							rootService);
				}
				this.featureLayers.add(layerMetadata.getName());
			}
		}
		root.setSelected(true);
		return root;
	}

	/**
	 * Metodo que obtiene las capas del servicio de arcgis
	 * 
	 * @param directoryMetadata El parámetro directoryMetadata define un objeto
	 *                          ServiceDirectoryMetadata que contiene los datos de
	 *                          la peticion
	 * @return Un objeto TreeNode que contiene la las capas del servicio.
	 */
	public TreeNode createTreeNode2(ServiceDirectoryMetadata directoryMetadata) {

		TreeNode root = null;
		root = new CheckboxTreeNode(new LayerBean("-1", "root", "parent", "null"), null);

		for (ServiceMetadata serviceMetadata : directoryMetadata.getServices()) {
			TreeNode rootService = new CheckboxTreeNode(
					new LayerBean("-1", serviceMetadata.getMapName(), "Service", "null"), root);
			HashMap<String, TreeNode> mapGroups = new HashMap<String, TreeNode>();
			HashMap<String, ServiceLayerMetadata> mapLyrs = new HashMap<String, ServiceLayerMetadata>();
			for (ServiceLayerMetadata layerMetadata : serviceMetadata.getLayers()) {
				if (layerMetadata.getType().equals("Group Layer")) {
					TreeNode groupNode = new CheckboxTreeNode(
							new LayerBean(layerMetadata.getId(), layerMetadata.getName(), "Group Layer", "null"),
							rootService);
					mapGroups.put(layerMetadata.getId(), groupNode);
				} else {
					mapLyrs.put(layerMetadata.getId(), layerMetadata);
				}
			}
			Iterator<Entry<String, ServiceLayerMetadata>> it = mapLyrs.entrySet().iterator();
			featureLayers2 = new ArrayList<String>();
			while (it.hasNext()) {
				Map.Entry<String, ServiceLayerMetadata> pair = it.next();
				ServiceLayerMetadata layerMetadata = pair.getValue();
				if (mapGroups.containsKey(layerMetadata.getParentLayerId())) {
					TreeNode groupNode = mapGroups.get(layerMetadata.getParentLayerId());
					new CheckboxTreeNode(
							new LayerBean(layerMetadata.getId(), layerMetadata.getName(), "Feature Layer", "null"),
							groupNode);
				} else if (layerMetadata.getParentLayerId().equals("-1")) {
					new CheckboxTreeNode(
							new LayerBean(layerMetadata.getId(), layerMetadata.getName(), "Feature Layer", "null"),
							rootService);
				}
				this.featureLayers2.add(layerMetadata.getName());
			}
		}
		root.setSelected(true);
		return root;
	}

	/**
	 * Metodo que obtiene administra el checbox de activacion de las capas
	 * 
	 * @param nodes El parámetro nodes define un objeto TreeNode que contiene las
	 *              capas del servicio.
	 */
	public void displaySelectedMultiple(TreeNode[] nodes) {
		this.layerVisualize = Constants.LAYERS_ON;
		Arrays.fill(layers, Boolean.FALSE);
		LayerBean lb;
		String savedLayer;
		String selectLayer;
		if (nodes != null && nodes.length > 0) {
			this.layerVisualize = Constants.LAYERS_OFF;
			for (TreeNode node : nodes) {
				lb = (LayerBean) node.getData();
				selectLayer = lb.getName();
				for (int i = 0; i <= featureLayers.size() - 1; i++) {
					savedLayer = featureLayers.get(i).toString();
					if (savedLayer.equals(selectLayer)) {
						this.layers[i] = true;
						i = featureLayers.size() - 1;
					}
				}
			}
		}
		PrimeFaces.current().executeScript(Constants.FUNCION_RESET_SERVICE_JS);
	}

	/**
	 * Metodo que obtiene administra el checbox de activacion de las capas
	 * 
	 * @param nodes El parámetro nodes define un objeto TreeNode que contiene las
	 *              capas del servicio.
	 */
	public void displaySelectedMultiple2(TreeNode[] nodes) {
		this.layerVisualize2 = Constants.LAYERS_ON;
		Arrays.fill(layers2, Boolean.FALSE);
		LayerBean lb;
		String savedLayer;
		String selectLayer;
		if (nodes != null && nodes.length > 0) {
			this.layerVisualize2 = Constants.LAYERS_OFF;
			for (TreeNode node : nodes) {
				lb = (LayerBean) node.getData();
				selectLayer = lb.getName();
				for (int i = 0; i <= featureLayers2.size() - 1; i++) {
					savedLayer = featureLayers2.get(i).toString();
					if (savedLayer.equals(selectLayer)) {
						this.layers2[i] = true;
						i = featureLayers2.size() - 1;
					}
				}
			}
		}
		PrimeFaces.current().executeScript(Constants.FUNCION_RESET_SERVICE2_JS);
	}

	/**
	 * Metodo que cambia la posicion de la capa hacia arriba
	 * 
	 * @param position El parámetro position define un string que contiene la
	 *                 posicion de la capa.
	 */
	public void positionUp(String position) {
		switch (position) {
		case "1":
			PrimeFaces.current().executeScript(Constants.FUNCION_MOVE_SERVICE2_JS);
			moveUpM1 = true;
			moveDownM1 = false;
			moveUpM2 = false;
			moveDownM2 = true;
			break;
		case "2":
			PrimeFaces.current().executeScript(Constants.FUNCION_MOVE_SERVICE_JS);
			moveUpM1 = false;
			moveDownM1 = true;
			moveUpM2 = true;
			moveDownM2 = false;
		}
		PrimeFaces.current().executeScript(Constants.FUNCION_RESET_SERVICE_JS);
		PrimeFaces.current().executeScript(Constants.FUNCION_RESET_SERVICE2_JS);
	}

	/**
	 * Metodo que cambia la posicion de la capa hacia abajo
	 * 
	 * @param position El parámetro position define un string que contiene la
	 *                 posicion de la capa.
	 */
	public void positionDown(String position) {
		switch (position) {
		case "1":
			PrimeFaces.current().executeScript(Constants.FUNCION_MOVE_SERVICE_JS);
			moveUpM1 = false;
			moveDownM1 = true;
			moveUpM2 = true;
			moveDownM2 = false;
			break;
		case "2":
			PrimeFaces.current().executeScript(Constants.FUNCION_MOVE_SERVICE2_JS);
			moveUpM1 = true;
			moveDownM1 = false;
			moveUpM2 = false;
			moveDownM2 = true;
			break;
		}
		PrimeFaces.current().executeScript(Constants.FUNCION_RESET_SERVICE_JS);
		PrimeFaces.current().executeScript(Constants.FUNCION_RESET_SERVICE2_JS);
	}

	/**
	 * Metodo que activa o desactiva la visulaizacion de todas las capas
	 */
	public void layersturn1() {
		if (layerVisualize.equals(Constants.LAYERS_ON)) {
			Arrays.fill(layers, Boolean.TRUE);
			this.layerVisualize = Constants.LAYERS_OFF;
			treeNodeServices.setSelected(true);
		} else {
			Arrays.fill(layers, Boolean.FALSE);
			this.layerVisualize = Constants.LAYERS_ON;
			treeNodeServices.setSelected(false);
		}
		PrimeFaces.current().executeScript(Constants.FUNCION_RESET_CAPA_JS);
	}

	/**
	 * Metodo que activa o desactiva la visulaizacion de todas las capas
	 */
	public void layersturn2() {
		if (layerVisualize2.equals(Constants.LAYERS_ON)) {
			Arrays.fill(layers2, Boolean.TRUE);
			this.layerVisualize2 = Constants.LAYERS_OFF;
			treeNodeServices2.setSelected(true);
		} else {
			Arrays.fill(layers2, Boolean.FALSE);
			this.layerVisualize2 = Constants.LAYERS_ON;
			treeNodeServices2.setSelected(false);
		}
		PrimeFaces.current().executeScript(Constants.FUNCION_RESET_CAPA2_JS);
	}

	/**
	 * Metodo que retorna todos los departamentos
	 */
	public void selectDeptos() {
		dptos = null;
		dptos = new ArrayList<Departamento>(resourceBD.getAllDepto());
	}

	/**
	 * Metodo que actualiza y retorna los municipios de un departamento determinado
	 * 
	 * @param dpto El parámetro dpto define un String que contiene el departamento
	 */
	public void selectMunicipios(String dpto) {
		setMpios(null);
		setMpios(new ArrayList<Municipios>(resourceBD.municipio(dpto)));
		PrimeFaces.current().ajax().update(Constants.ACTUALIZAR_COMPONENTE_MUNICIPIOS);
		PrimeFaces.current().executeScript(Constants.VISUALIZAR_COMPONENTE_MUNICIPIOS);
	}

	/**
	 * Metodo que ejecuta la busqueda por datos del propietario
	 */
	public void searchOwner() throws IOException {
		String selection2;
		String selection = getSearch();
		String value = getQueryVal();

		System.out.println(selection + value);
		List<ListaValores> tb = new ArrayList<ListaValores>(admon.tBusquedaA(1, selection));
		if (tb.get(0).getCOD_BUSQUEDA_BUS() == 5) {
			selection2 = getSearch2();
			List<ListaValores> tb2 = new ArrayList<ListaValores>(admon.tBusquedaA(2, selection2));
			this.setDataTable(consInterest.ownerId(tb2.get(0).getCOD_BUS(), value));
		} else if (tb.get(0).getCOD_BUSQUEDA_BUS() == 2) {
			selection2 = getSearch2();
			if (selection2.equals("Persona")) {
				String value2 = getQueryVal2();
				this.setDataTable(consInterest.ownerBasicDataNom(tb.get(0).getCOD_BUS(), value, value2));
			} else {
				this.setDataTable(consInterest.ownerBasicDataNomNit(tb.get(0).getCOD_BUS(), value));
			}
		} else if (tb.get(0).getCOD_BUSQUEDA_BUS() == 3) {
			this.setDataTable(consInterest.ownerBasicDataDir(tb.get(0).getCOD_BUS(), value));
		}
//		} else if (tb.get(0).getCOD_BUSQUEDA_BUS() == 4) {
//			selection2 = getSearch2();
//			List<ListaValores> tb2 = new ArrayList<ListaValores>(admon.tBusquedaA(2, selection2));
//			this.setDataTable(consInterest.ownerId(tb2.get(0).getCOD_BUS(), value));
//		}
//		if (selection.equals("identificacion")) {
//			selection2 = getSearch2();
//			if (selection2.equals("Cedula de ciudadanía")) {
//				selection2 = "TIPO_IDENTIFICACION_CEDULA";
//			} else if (selection2.equals("Cedula de extranjería")) {
//				selection2 = "TIPO_IDENTIFICACION_CEDULA_EXT";
//			} else if (selection2.equals("NIT")) {
//				selection2 = "TIPO_IDENTIFICACION_NIT";
//			} else if (selection2.equals("Pasaporte")) {
//				selection2 = "TIPO_IDENTIFICACION_PASS";
//			} else if (selection2.equals("Tarjeta de identidad")) {
//				selection2 = "TIPO_IDENTIFICACION_TI";
//			} else if (selection2.equals("NUIP")) {
//				selection2 = "TIPO_IDENTIFICACION_NUIP";
//			}
//
//			System.out.println(selection2);
//			System.out.println("busca por identificacion...");
//			this.setDataTable(consInterest.ownerId(selection2, value));
//		} else if (selection.equals("Nombres y apellidos")) {
//			System.out.println("busca por nombre...");
//			selection2 = getSearch2();
//			selection = admon.tipoBusquedaBus(1, selection);
//			if (selection2.equals("Persona")) {
//				String value2 = getQueryVal2();
//				this.setDataTable(consInterest.ownerBasicDataNom(selection, value, value2));
//			} else {
//				this.setDataTable(consInterest.ownerBasicDataNomNit(selection, value));
//			}
//		} else {
//			selection = admon.tipoBusquedaBus(1, selection);
//			this.setDataTable(consInterest.ownerBasicDataDir(selection, value));
//		}
		PrimeFaces.current().executeScript("$('.table').show();");
	}

	/**
	 * Metodo que ejecuta la busqueda por datos del predio
	 */
	public void searchProperty() {
		String selection = getSearch();
		System.out.println(selection);
		List<ListaValores> tb = new ArrayList<ListaValores>(admon.tBusquedaA(3, selection));
		if (tb.get(0).getCOD_BUSQUEDA_BUS() == 1) {
			String value = getQueryVal();
			this.setDataTable(consInterest.ownerId(tb.get(0).getCOD_BUS(), value));
		} else if (tb.get(0).getCOD_BUSQUEDA_BUS() == 4) {
			String type = getSearch2();
			tb = new ArrayList<ListaValores>(admon.tBusquedaA(4, type));
			String codMunicipio = getMpio();
			String minArea = getQueryValMin();
			String maxArea = getQueryValMax();
			String gisService = gisParametrosDao.getValue(Constants.URL_ARCGIS);
			String urlServiceMunicipio = gisService + Constants.CAPA_MUNICIPIOS;
			if (tb.get(0).getCOD_BUSQUEDA_BUS() == 6) {
				gisService = gisService + Constants.ZONA_URBANA;
				type = Constants.URBANO;
			} else if (tb.get(0).getCOD_BUSQUEDA_BUS() == 7) {
				gisService = gisService + Constants.ZONA_RURAL;
				type = Constants.RURAL;
			}
			System.out.println("queryByArea('" + gisService + "','" + type + "','" + urlServiceMunicipio + "','"
					+ codMunicipio + "'," + minArea + ", " + maxArea + ");");
			PrimeFaces.current().executeScript("queryByArea('" + gisService + "','" + type + "','" + urlServiceMunicipio
					+ "','" + codMunicipio + "'," + minArea + ", " + maxArea + ");");
			List<AttributeDataTable> dt = new ArrayList<AttributeDataTable>();
			dt.add(new AttributeDataTable(Constants.MENSAJE_CARGANDO, Constants.MENSAJE_CARGANDO,
					Constants.MENSAJE_CARGANDO, Constants.MENSAJE_CARGANDO, Constants.MENSAJE_CARGANDO,
					Constants.MENSAJE_CARGANDO, Constants.MENSAJE_CARGANDO, Constants.MENSAJE_CARGANDO,
					Constants.MENSAJE_CARGANDO, Constants.MENSAJE_CARGANDO, Constants.MENSAJE_CARGANDO,
					Constants.MENSAJE_CARGANDO));
			this.setDataTable(dt);
		}
		PrimeFaces.current().executeScript(Constants.VISUALIZAR_TABLA_CONSULTAR_JS);
	}

	/**
	 * Metodo que actualiza las listas segun el tipo de consulta seleccionada
	 * 
	 * @param selection El parámetro selection define un String que contiene el tipo
	 *                  de consulta elegida por el usuario
	 */
	public void radioBtn(String selection) {
		searchs = null;
		searchs = new ArrayList<String>();
		list = null;
		list = new ArrayList<DataTable>();
		if (selection.equals(Constants.CONSULTAR_TITULAR)) {
			list = admon.tipoBusquedaA(1);
		} else {
			list = admon.tipoBusquedaA(3);
		}
		for (DataTable dataTable : list) {
			searchs.add(dataTable.getValue());
		}
		PrimeFaces.current().executeScript(Constants.VISUALIZAR_TIPO_BUSQUEDA);

	}

	/**
	 * Metodo que actualiza las listas segun el tipo de busqueda seleccionada
	 * 
	 * @param selection, rdBtn El parámetro selection define un String que contiene
	 *                   el tipo de busqueda y El parámetro rdBtn define un String
	 *                   con el tipo de consulta elegida por el usuario
	 */
	public void idSelection2(String selection, String rdBtn) {
		PrimeFaces.current().executeScript("$('.idSelection0').hide();");
		searchs2 = null;
		searchs2 = new ArrayList<String>();

		if (rdBtn.equals("Datos del titular")) {
			list = null;
			list = new ArrayList<DataTable>();
			PrimeFaces.current().executeScript("$('.idSelectionA').show();$('.idSelectionBtO').show();");
			List<ListaValores> tb = new ArrayList<ListaValores>(admon.tBusquedaA(1, selection));
			System.out.println(selection + "----" + tb.get(0).getCOD_BUSQUEDA_BUS());
			if (tb.get(0).getCOD_BUSQUEDA_BUS() == 2) {
				searchs2.add("Empresa");
				searchs2.add("Persona");
				PrimeFaces.current().executeScript(
						"$('.idSelection3').show();$('.idSelectionA').show();$('.idSelectionBtO').show();");
			} else if (tb.get(0).getCOD_BUSQUEDA_BUS() == 5) {
				list = admon.tipoBusquedaA(2);
				PrimeFaces.current().executeScript(
						"$('.idSelection3').show();$('.idSelectionA').show();$('.idSelectionBtO').show();");
			}
		} else {
			list = null;
			list = new ArrayList<DataTable>();
			List<ListaValores> tb = new ArrayList<ListaValores>(admon.tBusquedaA(3, selection));
			list = admon.tipoBusquedaA(4);
			if (tb.get(0).getCOD_BUSQUEDA_BUS() == 4) {
				selectDeptos();
				PrimeFaces.current().executeScript(
						"$('.idSelection3').show();$('.idSelectionC').show();$('.idSelectionBtP').show();");
			} else {
				PrimeFaces.current().executeScript("$('.idSelectionA').show();$('.idSelectionBtP').show();");
			}
		}

		for (DataTable dataTable : list) {
			searchs2.add(dataTable.getValue());
		}

//			if (selection.equals("Nombres y apellidos")) {
//				searchs2.add("Empresa");
//				searchs2.add("Persona");
//				PrimeFaces.current().executeScript(
//						"$('.idSelection3').show();$('.idSelectionA').show();$('.idSelectionBtO').show();");
//			} else if (selection.equals("Identificación")) {
//				list = admon.tipoBusquedaA(2);
//				PrimeFaces.current().executeScript(
//						"$('.idSelection3').show();$('.idSelectionA').show();$('.idSelectionBtO').show();");
//			}
//		} else {
//			list = null;
//			list = new ArrayList<DataTable>();
//			list = admon.tipoBusquedaA(4);
//			if (selection.equals("Rango de áreas")) {
//				selectDeptos();
//				PrimeFaces.current().executeScript(
//						"$('.idSelection3').show();$('.idSelectionC').show();$('.idSelectionBtP').show();");
//			} else if (selection.equals("División político-administrativa")) {
//				PrimeFaces.current().executeScript(
//						"$('.idSelection3').show();$('.idSelectionD').show();$('.idSelectionBtP').show();");
//			} else {
//				PrimeFaces.current().executeScript("$('.idSelectionA').show();$('.idSelectionBtP').show();");
//			}
//
//		}
//
//		for (DataTable dataTable : list) {
//			searchs2.add(dataTable.getValue());
//		}
//-------------------------------------------------------------------------------------------------------------		
//		if (selection.equals("Identificación")) {
//			searchs2.add("Cedula de ciudadanía");
//			searchs2.add("Cedula de extranjería");
//			searchs2.add("NIT");
//			searchs2.add("NUIP");
//			searchs2.add("Pasaporte");
//			searchs2.add("Tarjeta de identidad");
//			PrimeFaces.current()
//					.executeScript("$('.idSelection3').show();$('.idSelectionA').show();$('.idSelectionBtO').show();");
//		} else if (selection.equals("Nombres y apellidos")) {
//			searchs2.add("Empresa");
//			searchs2.add("Persona");
//			PrimeFaces.current()
//					.executeScript("$('.idSelection3').show();$('.idSelectionA').show();$('.idSelectionBtO').show();");
//		} else if (selection.equals("Rango de áreas")) {
//			selectDeptos();
//			searchs2.add("Rural");
//			searchs2.add("Urbano");
//			PrimeFaces.current()
//					.executeScript("$('.idSelection3').show();$('.idSelectionC').show();$('.idSelectionBtP').show();");
//		} else if (selection.equals("División político-administrativa")) {
//			selectDeptos();
//			searchs2.add("Rural");
//			searchs2.add("Urbano");
//			PrimeFaces.current()
//					.executeScript("$('.idSelection3').show();$('.idSelectionD').show();$('.idSelectionBtP').show();");
//		} else if (selection.equals("Dirección")) {
//			PrimeFaces.current().executeScript("$('.idSelectionA').show();$('.idSelectionBtO').show();");
//		} else if (!selection.equals("")) {
//			PrimeFaces.current().executeScript("$('.idSelectionA').show();$('.idSelectionBtP').show();");
//		}
	}

	/**
	 * Metodo que realiza la busqueda en el panel de administracion
	 */
	public void admonSearch() {
		int admonList = this.admonList;
		if (!(admonList == 0)) {
			this.setAdmonTable(admon.tipoBusqueda(admonList));
		}
		PrimeFaces.current().executeScript(Constants.FUNCION_LIMPIAR_ADMON_JS);
	}

	/**
	 * Metodo que realiza la insercion de datos en el panel de administracion
	 */
	public void admonInsert() {
		java.util.Date fecha = new Date();
		HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
		String ip = getIpAddr(httpServletRequest);
		String inputAdmon = this.inputAdmon;
		String checkAdmon = Constants.ESTADO_INACTIVO_ADMON;
		String inputAdmonBus = Constants.BUSQUEDA_BUS_NO;
		if (this.checkAdmonBus) {
			inputAdmonBus = this.inputAdmonBus;
		}
		if (!inputAdmon.equals("") && !inputAdmonBus.equals("")) {
			if (this.checkAdmon) {
				checkAdmon = Constants.ESTADO_ACTIVO_ADMON;
			}
			try {
				admon.insertarAmon(this.admonList, inputAdmon, checkAdmon, inputAdmonBus, this.busqBus, this.name,
						fecha, ip);
				this.setAdmonTable(admon.tipoBusqueda(this.admonList));
				PrimeFaces.current().executeScript(Constants.FUNCION_LIMPIAR_ADMON_JS);
			} catch (Exception e) {
				msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.MENSAJE_MAPBEAN_5,
						Constants.MENSAJE_MAPBEAN_6);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.MENSAJE_MAPBEAN_5,
					Constants.MENSAJE_MAPBEAN_7);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	/**
	 * Metodo que busca el predio en el visor geografico cuando se selecciona un
	 * registro en la tabla consultas
	 * 
	 * @param event El parámetro event define objeto SelectEvent que contiene la
	 *              informacion de la grilla seleccionada
	 */
	public void onRowSelectConsult(SelectEvent event) {
		String[] spl = this.selectedConsult.getCode().split(" - ");
		String cCatastral = spl[0];
		PrimeFaces.current().executeScript("zoomToCode('" + cCatastral + "');");

	}

	/**
	 * Metodo que busca los predios en el visor geografico cuando se seleccionan
	 * registros en la tabla de atributos
	 */
	public void onRowSelectTableA() {
		String[] spl;
		ArrayList<String> cCatastral = new ArrayList<String>();
		int i = 0;
		for (AttributeDataTable nombre : this.selectedTable) {
			spl = nombre.getCode().split(" - ");
			cCatastral.add(spl[0]);
			i = i + 1;
		}
		PrimeFaces.current().executeScript("zoomToCodes('" + new Gson().toJson(cCatastral) + "');");
	}

	/**
	 * Metodo que selecciona la informacion de la tabla de administracion
	 * 
	 * @param event El parámetro event define objeto SelectEvent que contiene la
	 *              informacion de la grilla seleccionada
	 */
	public void onRowSelect(SelectEvent event) {
		this.busqBus = selectedAdmon.getBusquedaB();
		this.inputAdmon = selectedAdmon.getValue();
		this.inputAdmonBus = selectedAdmon.getCodBus();
		setInputAdmon(selectedAdmon.getValue());
		boolean checkAdmon = false;
		boolean checkAdmonBus = true;
		this.inputAdmdEst = false;
		if (selectedAdmon.getState().equals(Constants.ESTADO_ACTIVO_ADMON)) {
			checkAdmon = true;
		}
		if (this.inputAdmonBus.equals(Constants.BUSQUEDA_BUS_NO)) {
			checkAdmonBus = false;
			this.inputAdmdEst = true;
		}
		this.checkAdmon = checkAdmon;
		this.checkAdmonBus = checkAdmonBus;
		setCheckAdmon(checkAdmon);
		this.btnAdmdEst = false;
		PrimeFaces.current().ajax().update(Constants.ACTUALIZAR_CAMPO_ADMON);
		PrimeFaces.current().ajax().update(Constants.ACTUALIZAR_CAMPO_BUS);
		PrimeFaces.current().ajax().update(Constants.ACTUALIZAR_CHB_BUS);
		PrimeFaces.current().ajax().update(Constants.ACTUALIZAR_BOTON_MODIFICAR_ADMON);
		PrimeFaces.current().ajax().update(Constants.ACTUALIZAR_BOTON_BORRAR_ADMON);
		PrimeFaces.current().ajax().update(Constants.ACTUALIZAR_SELECCION_ADMON);

	}

	/**
	 * Metodo que actualiza los componentes cuando se deselecciona la grilla de la
	 * tabla de administracion
	 * 
	 * @param event El parámetro event define objeto SelectEvent que contiene la
	 *              informacion de la grilla deseleccionada
	 */
	public void onRowUnselect(UnselectEvent event) {
		this.btnAdmdEst = true;
		PrimeFaces.current().ajax().update(Constants.ACTUALIZAR_BOTON_MODIFICAR_ADMON);
		PrimeFaces.current().ajax().update(Constants.ACTUALIZAR_BOTON_BORRAR_ADMON);
	}

	/**
	 * Metodo que realiza la modificacion de datos en el panel de administracion
	 */
	public void admonModify() {
		java.util.Date fecha = new Date();
		String inputAdmonBus = Constants.BUSQUEDA_BUS_NO;
		if (this.checkAdmonBus) {
			inputAdmonBus = this.inputAdmonBus;
		}
		if (!this.inputAdmon.equals("") && !inputAdmonBus.equals("")) {
			String checkAdmon = Constants.ESTADO_INACTIVO_ADMON;
			if (this.checkAdmon) {
				checkAdmon = Constants.ESTADO_ACTIVO_ADMON;
			}
			admon.modificarAdmon(selectedAdmon.getList(), this.inputAdmon, checkAdmon, selectedAdmon.getValue(),
					inputAdmonBus, this.busqBus, this.name, fecha);
			this.setAdmonTable(admon.tipoBusqueda(selectedAdmon.getList()));
			PrimeFaces.current().executeScript(Constants.FUNCION_LIMPIAR_ADMON_JS);
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.MENSAJE_MAPBEAN_8,
					Constants.MENSAJE_MAPBEAN_9);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			PrimeFaces.current().ajax().update(Constants.ACTUALIZAR_MENSAJE);
		}

	}

	/**
	 * Metodo que realiza la eliminacion de datos en el panel de administracion
	 */
	public void admonRemove() {
		if (!this.inputAdmon.equals("")) {
			admon.eliminarAdmon(selectedAdmon.getList(), selectedAdmon.getValue());
			this.setAdmonTable(admon.tipoBusqueda(selectedAdmon.getList()));
			PrimeFaces.current().executeScript(Constants.FUNCION_LIMPIAR_ADMON_JS);
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.MENSAJE_MAPBEAN_10,
					Constants.MENSAJE_MAPBEAN_11);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			PrimeFaces.current().ajax().update(Constants.ACTUALIZAR_MENSAJE);
		}
	}

	/**
	 * Metodo que realiza la modificacion de datos en la cantidad de registros en el
	 * panel de administracion
	 */
	public void admonModifyR() {
		java.util.Date fecha = new Date();
		if ((this.inputAdmon2 == Constants.CERO) || (this.inputAdmon3 == Constants.CERO)
				|| (this.inputAdmon4 == Constants.CERO) || (this.inputAdmon5 == Constants.CERO)) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.MENSAJE_MAPBEAN_8,
					Constants.MENSAJE_MAPBEAN_12);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			PrimeFaces.current().ajax().update(Constants.ACTUALIZAR_MENSAJE);

		} else if ((this.inputAdmon2 > Constants.CANTIDAD_REGISTROS_CONSULTA)
				|| (this.inputAdmon3 > Constants.CANTIDAD_REGISTROS_CSV)
				|| (this.inputAdmon4 > Constants.CANTIDAD_REGISTROS_TXT)
				|| (this.inputAdmon5 > Constants.CANTIDAD_REGISTROS_TABLAA)) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.MENSAJE_MAPBEAN_8,
					Constants.MENSAJE_MAPBEAN_13);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			PrimeFaces.current().ajax().update(Constants.ACTUALIZAR_MENSAJE);
		} else {
			admon.modificarRegistros(Constants.CONSULTA_INTERESADO, this.inputAdmon2, this.name, fecha);
			admon.modificarRegistros(Constants.CONSULTA_CSV, this.inputAdmon3, this.name, fecha);
			admon.modificarRegistros(Constants.CONSULTA_TXT, this.inputAdmon4, this.name, fecha);
			admon.modificarRegistros(Constants.CONSULTA_ATRIBUTOS, this.inputAdmon5, this.name, fecha);
			PrimeFaces.current().executeScript(Constants.FUNCION_LIMPIAR_ADMON_JS);
		}

	}

	/**
	 * Metodo que obtiene las cedulas al hacer click en el mapa y despliega el poup
	 */
	public void populateCode() {
		String code = this.codeListString;
		System.out.println(code);
		this.fMatriculas = consInterest.datosPredio(Constants.CODIGO_CEDULA_CATRASTAL, code);
		this.territorioD = consInterest.territorioD();
		this.territorioM = consInterest.territorioM();
		PrimeFaces.current().executeScript(Constants.VISUALIZAR_POUP_MATRICULAS_JS);
	}

	/**
	 * Metodo que obtiene las cedulas y consulta los datos por busqueda de rango de
	 * area
	 */
	public void populateCodes() {
		int i = 0;
		int cantidadMax = admon.tBusquedaARegistro(Constants.CONSULTA_INTERESADO);
		String cadena;
		String[] codesArray = new Gson().fromJson(this.codeListString, String[].class);
		List<String> codeList = Arrays.asList(codesArray);
		List<AttributeDataTable> dt = null;
		List<AttributeDataTable> dt2 = new ArrayList<AttributeDataTable>();
		for (int x = 0; x < codeList.size(); x++) {
			dt = consInterest.propertyAreaRange(Constants.CODIGO_CEDULA_CATRASTAL, codeList.get(i));
			for (int y = 0; y < dt.size(); y++) {
				i = i + 1;
				cadena = i + "";
				dt2.add(new AttributeDataTable(cadena, dt.get(y).getNupre(), dt.get(y).getEnrollment(),
						dt.get(y).getCode(), dt.get(y).getDepto(), dt.get(y).getMunicipality(), dt.get(y).getGroup(),
						dt.get(y).getAddress(), dt.get(y).getId(), dt.get(y).getIdentification(), dt.get(y).getName(),
						dt.get(y).getLayer()));
				if (i == cantidadMax) {
					y = dt.size();
					x = codeList.size();
				}
			}
		}
		this.setDataTable(dt2);
		PrimeFaces.current().executeScript(Constants.VISUALIZAR_TABLA_CONSULTAR_JS);
		System.out.println(this.codeListString);
	}

	/**
	 * Este método pobla el combo de Capas Locales
	 */
	public void populateLayers() {
		capasLocalesItems = new ArrayList<>();
		Capa[] codesArray = new Gson().fromJson(this.capasLocalesString, Capa[].class);
		List<Capa> codeList = Arrays.asList(codesArray);
		for (Capa capa : codeList) {
			capasLocalesItems.add(new SelectItem(capa.getId(), capa.getLayerName()));
		}
	}

	/**
	 * Este método pobla el combo de los servicios OGC
	 */
	public void populateOGCLayers() {
		capasOGCItems = new ArrayList<>();
		capasOGCMap = new HashMap<>();
		Capa[] codesArray = new Gson().fromJson(this.capasOGCString, Capa[].class);
		List<Capa> codeList = Arrays.asList(codesArray);
		for (Capa capa : codeList) {
			capasOGCItems.add(new SelectItem(capa.getId(), capa.getLayerName()));
			capasOGCMap.put(capa.getId(), capa);
		}
	}

	/**
	 * Este método se dispara cuando realizo consulta por localizacion por capas
	 * locales
	 */
	public void consultarCapasLocales() {
		PrimeFaces.current().executeScript("queryByCapasLocales('" + this.capasLocalesSelected + "');");
	}

	/**
	 * Este metodo se dispara al momento de de hacer clic en el boton de imprimir
	 */
	public void printAction() {
		String print = "print('" + this.imprimirNombre + "','" + this.imprimirFormato + "','" + this.imprimirDiseno
				+ "','" + this.imprimirScalebarUnit + "'," + this.imprimirWidth + "," + this.imprimirHeight + ","
				+ this.imprimirDPI + "," + this.imprimirLegend + ");";
		PrimeFaces.current().executeScript(print);
	}

	/**
	 * Este metodo se ejecuta al momento de hacer clic en el boton de exportar KML o
	 * shapefile
	 */
	public void exportAction() {
		String export = "exportLayer('" + this.exportarCapa + "','" + this.exportarFormato + "');";
		PrimeFaces.current().executeScript(export);
	}

	/**
	 * Este metodo se ejecuta al momento de agregar un capa OGC al mapa
	 */
	public void servicioAction() {
		String export = "loadOGCServices('" + this.servicioTipo + "','" + this.servicioURL + "');";
		PrimeFaces.current().executeScript(export);
	}

	/**
	 * Este metodo limpia los servicios y capas agregadas al mapa a partir de capas
	 * locales y servicios OGC
	 */
	public void servicioClearAction() {
		this.capasOGCSelected = null;
		capasLocalesItems = new ArrayList<>();
		this.servicioURL = null;
		this.capasOGCItems = new ArrayList<>();
	}

	/**
	 * Metodo que cambia el estado del valor en el panel de administracion
	 * 
	 * @param check El parámetro check define un boolean que contiene el estado del
	 *              valor de la lista
	 */
	public void AdmdEstInput(boolean check) {
		this.inputAdmdEst = true;
		if (check) {
			this.inputAdmdEst = false;
		}
	}

//	public void datosPredio() {
//		this.fMatriculas = consInterest.datosPredio("MATRICULA", "12345");
//		PrimeFaces.current().executeScript("PF('dlg2').show();");
//	}

	/**
	 * Metodo que obtiene los datos del predio cuando es seleccionada la matricula
	 * en el poup
	 * 
	 * @param folioM El parámetro folioM define un String con la mattricula
	 *               seleccionada por el usuario.
	 */
	public void idSelectionDatosP(String folioM) {
		this.datosPredioM = consInterest.datosPredioTable();
		datosPredio = new ArrayList<DatosPredioDataTable>();

		for (int i = 0; i < datosPredioM.size(); i++) {
			if (datosPredioM.get(i).getFolioMatricula().equals(folioM)) {
				datosPredio.add(new DatosPredioDataTable(datosPredioM.get(i).getFolioMatricula(),
						datosPredioM.get(i).getIdentificacion(), datosPredioM.get(i).getNombreTitular(),
						datosPredioM.get(i).getDireccion(), datosPredioM.get(i).getParticipacion()));
			}
		}

	}

	/**
	 * Metodo que obtiene los datos juridicos del predio cuando es seleccionada la
	 * grilla en el poup
	 */
	public void onRowSelectPredio() {
		this.datosJuridicoM = consInterest.datosJuridicoTable();
		datosJuridico = new ArrayList<DatosJuridicoDataTable>();

		for (int i = 0; i < datosJuridicoM.size(); i++) {
			if (datosJuridicoM.get(i).getFolioMatricula().equals(this.selectedPredio.getFolioMatricula())) {
				datosJuridico.add(new DatosJuridicoDataTable(datosJuridicoM.get(i).getFolioMatricula(),
						datosJuridicoM.get(i).getConsecutivo(), datosJuridicoM.get(i).getDatosDocumento(),
						datosJuridicoM.get(i).getActoJuridico(), datosJuridicoM.get(i).getdRR(),
						datosJuridicoM.get(i).getFechaAnotacion(), datosJuridicoM.get(i).getValor()));
			}
		}

		PrimeFaces.current().executeScript(Constants.VISUALIZAR_POUP_ANOTACIONES_JS);
		PrimeFaces.current().executeScript(Constants.OCULTAR_POUP_MATRICULAS_JS);

	}

	/**
	 * Metodo que obtiene las anotaciones del predio cuando es seleccionada la
	 * grilla en el poup
	 */
	public void onRowSelectJuridico() {
		this.datosAnotacionesM = consInterest.datosAnotacionesTable();
		datosAnotaciones = new ArrayList<AnotacionesDataTable>();
		for (int i = 0; i < datosAnotacionesM.size(); i++) {
			if ((datosAnotacionesM.get(i).getFolioMatricula().equals(this.selectedJuridico.getFolioMatricula()))
					&& (datosAnotacionesM.get(i).getConsecutivo().equals(this.selectedJuridico.getConsecutivo()))) {

				datosAnotaciones.add(new AnotacionesDataTable(datosAnotacionesM.get(i).getFolioMatricula(),
						datosAnotacionesM.get(i).getConsecutivo(), datosAnotacionesM.get(i).getIdentificacion(),
						datosAnotacionesM.get(i).getNombInterviniente(), datosAnotacionesM.get(i).getRol()));
			}
		}
		PrimeFaces.current().executeScript(Constants.VISUALIZAR_POUP_INTERVINIENTES_JS);
		PrimeFaces.current().executeScript(Constants.OCULTAR_POUP_ANOTACIONES_JS);
	}

	/**
	 * Este evento se dispara cuando se selecciona un opción en el combo de tipos de
	 * servicios OGC
	 */
	public void tipoOGCSelectionChange() {
		this.capasOGCSelected = null;
		this.servicioURL = null;
		this.capasOGCItems = new ArrayList<>();
	}

	/**
	 * Este evento se dispara cuando se selecciona un opción en el combo de
	 * servicios OGC y agrega el servicio en el mapa
	 */
	public void capasOGCSelectionChange() {
		Capa capa = this.capasOGCMap.get(this.capasOGCSelected);
		String export = "adddOGCServices('" + this.servicioTipo + "','" + this.servicioURL + "','"
				+ this.capasOGCSelected + "','" + capa.getLayerName() + "');";
		PrimeFaces.current().executeScript(export);
		this.capasOGCSelected = null;
	}

	@Override
	public int compareTo(MapBean arg0) {
		return 0;
	}

	public String getUrlService() {
		return urlService;
	}

	public void setUrlService(String urlService) {
		this.urlService = urlService;
	}

	public TreeNode getTreeNodeServices() {
		return treeNodeServices;
	}

	public void setTreeNodeServices(TreeNode treeNodeServices) {
		this.treeNodeServices = treeNodeServices;
	}

	public TreeNode getTreeNodeOGCServices() {
		return treeNodeOGCServices;
	}

	public void setTreeNodeOGCServices(TreeNode treeNodeOGCServices) {
		this.treeNodeOGCServices = treeNodeOGCServices;
	}

	public TreeNode[] getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode[] selectedNode) {
		this.selectedNode = selectedNode;
	}

	public boolean[] getLayers() {
		return layers;
	}

	public void setLayers(boolean[] layers) {
		this.layers = layers;
	}

	public List<String> getFeatureLayers() {
		return featureLayers;
	}

	public void setFeatureLayers(List<String> featureLayers) {
		this.featureLayers = featureLayers;
	}

	public String getLayerVisualize() {
		return layerVisualize;
	}

	public void setLayerVisualize(String layerVisualize) {
		this.layerVisualize = layerVisualize;
	}

	public String getLayerVisualize2() {
		return layerVisualize2;
	}

	public void setLayerVisualize2(String layerVisualize2) {
		this.layerVisualize2 = layerVisualize2;
	}

	public boolean[] getLayers2() {
		return layers2;
	}

	public void setLayers2(boolean[] layers2) {
		this.layers2 = layers2;
	}

	public String getUrlService2() {
		return urlService2;
	}

	public void setUrlService2(String urlService2) {
		this.urlService2 = urlService2;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl2() {
		return url2;
	}

	public void setUrl2(String url2) {
		this.url2 = url2;
	}

	public List<String> getFeatureLayers2() {
		return featureLayers2;
	}

	public void setFeatureLayers2(List<String> featureLayers2) {
		this.featureLayers2 = featureLayers2;
	}

	public TreeNode getTreeNodeServices2() {
		return treeNodeServices2;
	}

	public void setTreeNodeServices2(TreeNode treeNodeServices2) {
		this.treeNodeServices2 = treeNodeServices2;
	}

	public TreeNode[] getSelectedNode2() {
		return selectedNode2;
	}

	public void setSelectedNode2(TreeNode[] selectedNode2) {
		this.selectedNode2 = selectedNode2;
	}

	public boolean isMoveUpM1() {
		return moveUpM1;
	}

	public void setMoveUpM1(boolean moveUpM1) {
		this.moveUpM1 = moveUpM1;
	}

	public boolean isMoveDownM1() {
		return moveDownM1;
	}

	public void setMoveDownM1(boolean moveDownM1) {
		this.moveDownM1 = moveDownM1;
	}

	public boolean isMoveUpM2() {
		return moveUpM2;
	}

	public void setMoveUpM2(boolean moveUpM2) {
		this.moveUpM2 = moveUpM2;
	}

	public boolean isMoveDownM2() {
		return moveDownM2;
	}

	public void setMoveDownM2(boolean moveDownM2) {
		this.moveDownM2 = moveDownM2;
	}

	public String getDpto() {
		return dpto;
	}

	public void setDpto(String dpto) {
		this.dpto = dpto;
	}

	public List<Departamento> getDptos() {
		return dptos;
	}

	public void setDptos(List<Departamento> dptos) {
		this.dptos = dptos;
	}

	public String getMpio() {
		return mpio;
	}

	public void setMpio(String mpio) {
		this.mpio = mpio;
	}

	public List<String> getDepartments() {
		return departments;
	}

	public void setDepartments(List<String> departments) {
		this.departments = departments;
	}

	public String getQueryVal() {
		return queryVal;
	}

	public void setQueryVal(String queryVal) {
		this.queryVal = queryVal;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getQueryVal2() {
		return queryVal2;
	}

	public void setQueryVal2(String queryVal2) {
		this.queryVal2 = queryVal2;
	}

	public List<AttributeDataTable> getDataTable() {
		return dataTable;
	}

	public void setDataTable(List<AttributeDataTable> dataTable) {
		this.dataTable = dataTable;
	}

	public String getRadioBtn() {
		return radioBtn;
	}

	public void setRadioBtn(String radioBtn) {
		this.radioBtn = radioBtn;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public List<String> getSearchs() {
		return searchs;
	}

	public void setSearchs(List<String> searchs) {
		this.searchs = searchs;
	}

	public String getSearch2() {
		return search2;
	}

	public void setSearch2(String search2) {
		this.search2 = search2;
	}

	public List<String> getSearchs2() {
		return searchs2;
	}

	public void setSearchs2(List<String> searchs2) {
		this.searchs2 = searchs2;
	}

	public double getOpacity() {
		return opacity;
	}

	public void setOpacity(double opacity) {
		this.opacity = opacity;
	}

	public double getOpacityCal() {
		return opacityCal;
	}

	public void setOpacityCal(double opacityCal) {
		this.opacity = opacityCal / 100;
		this.opacityCal = opacityCal;
	}

	public double getOpacityCal2() {
		return opacityCal2;
	}

	public void setOpacityCal2(double opacityCal2) {
		this.setOpacity2(opacityCal2 / 100);
		this.opacityCal2 = opacityCal2;
	}

	public double getOpacity2() {
		return opacity2;
	}

	public void setOpacity2(double opacity2) {
		this.opacity2 = opacity2;
	}

	public List<DataTable> getAdmonTable() {
		return admonTable;
	}

	public void setAdmonTable(List<DataTable> admonTable) {
		this.admonTable = admonTable;
	}

	public DataTable getSelectedAdmon() {
		return selectedAdmon;
	}

	public void setSelectedAdmon(DataTable selectedAdmon) {
		this.selectedAdmon = selectedAdmon;
	}

	public String getStateAdmon() {
		return stateAdmon;
	}

	public void setStateAdmon(String stateAdmon) {
		this.stateAdmon = stateAdmon;
	}

	public List<String> getStatesAdmon() {
		return statesAdmon;
	}

	public void setStatesAdmon(List<String> statesAdmon) {
		this.statesAdmon = statesAdmon;
	}

	public boolean isCheckAdmon() {
		return checkAdmon;
	}

	public void setCheckAdmon(boolean checkAdmon) {
		this.checkAdmon = checkAdmon;
	}

	public String getInputAdmon() {
		return inputAdmon;
	}

	public void setInputAdmon(String inputAdmon) {
		this.inputAdmon = inputAdmon;
	}

	public int getInputAdmon2() {
		return inputAdmon2;
	}

	public void setInputAdmon2(int inputAdmon2) {
		this.inputAdmon2 = inputAdmon2;
	}

	public int getInputAdmon3() {
		return inputAdmon3;
	}

	public void setInputAdmon3(int inputAdmon3) {
		this.inputAdmon3 = inputAdmon3;
	}

	public int getInputAdmon4() {
		return inputAdmon4;
	}

	public void setInputAdmon4(int inputAdmon4) {
		this.inputAdmon4 = inputAdmon4;
	}

	public int getInputAdmon5() {
		return inputAdmon5;
	}

	public void setInputAdmon5(int inputAdmon5) {
		this.inputAdmon5 = inputAdmon5;
	}

	public boolean isBtnAdmdEst() {
		return btnAdmdEst;
	}

	public void setBtnAdmdEst(boolean btnAdmdEst) {
		this.btnAdmdEst = btnAdmdEst;
	}

	public List<DataTable> getList() {
		return list;
	}

	public void setList(List<DataTable> list) {
		this.list = list;
	}

	public boolean isCheckAdmonBus() {
		return checkAdmonBus;
	}

	public void setCheckAdmonBus(boolean checkAdmonBus) {
		this.checkAdmonBus = checkAdmonBus;
	}

	public String getInputAdmonBus() {
		return inputAdmonBus;
	}

	public void setInputAdmonBus(String inputAdmonBus) {
		this.inputAdmonBus = inputAdmonBus;
	}

	public boolean isInputAdmdEst() {
		return inputAdmdEst;
	}

	public void setInputAdmdEst(boolean inputAdmdEst) {
		this.inputAdmdEst = inputAdmdEst;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<BusquedaBus> getBusqsBus() {
		return busqsBus;
	}

	public void setBusqsBus(List<BusquedaBus> busqsBus) {
		this.busqsBus = busqsBus;
	}

	public String getBusqBus() {
		return busqBus;
	}

	public void setBusqBus(String busqBus) {
		this.busqBus = busqBus;
	}

	public int getAdmonList() {
		return admonList;
	}

	public void setAdmonList(int admonList) {
		this.admonList = admonList;
	}

	public List<ListaValores> getList2() {
		return list2;
	}

	public void setList2(List<ListaValores> list2) {
		this.list2 = list2;
	}

	public AttributeDataTable getSelectedConsult() {
		return selectedConsult;
	}

	public void setSelectedConsult(AttributeDataTable selectedConsult) {
		this.selectedConsult = selectedConsult;
	}

	public List<Municipios> getMpios() {
		return mpios;
	}

	public void setMpios(List<Municipios> mpios) {
		this.mpios = mpios;
	}

	public String getQueryValMin() {
		return queryValMin;
	}

	public void setQueryValMin(String queryValMin) {
		this.queryValMin = queryValMin;
	}

	public String getQueryValMax() {
		return queryValMax;
	}

	public void setQueryValMax(String queryValMax) {
		this.queryValMax = queryValMax;
	}

	public String getCodeListString() {
		return codeListString;
	}

	public void setCodeListString(String codeListString) {
		this.codeListString = codeListString;
	}

	public String getTipoConsultaLocalizacion() {
		return tipoConsultaLocalizacion;
	}

	public void setTipoConsultaLocalizacion(String tipoConsultaLocalizacion) {
		this.tipoConsultaLocalizacion = tipoConsultaLocalizacion;
	}

	public String getCapasLocalesString() {
		return capasLocalesString;
	}

	public void setCapasLocalesString(String capasLocalesString) {
		this.capasLocalesString = capasLocalesString;
	}

	public List<SelectItem> getCapasLocalesItems() {
		return capasLocalesItems;
	}

	public void setCapasLocalesItems(List<SelectItem> capasLocalesItems) {
		this.capasLocalesItems = capasLocalesItems;
	}

	public String getCapasLocalesSelected() {
		return capasLocalesSelected;
	}

	public void setCapasLocalesSelected(String capasLocalesSelected) {
		this.capasLocalesSelected = capasLocalesSelected;
	}

	public String getCapasOGCString() {
		return capasOGCString;
	}

	public void setCapasOGCString(String capasOGCString) {
		this.capasOGCString = capasOGCString;
	}

	public List<SelectItem> getCapasOGCItems() {
		return capasOGCItems;
	}

	public void setCapasOGCItems(List<SelectItem> capasOGCItems) {
		this.capasOGCItems = capasOGCItems;
	}

	public String getCapasOGCSelected() {
		return capasOGCSelected;
	}

	public void setCapasOGCSelected(String capasOGCSelected) {
		this.capasOGCSelected = capasOGCSelected;
	}

	public String getExportarValor() {
		return exportarValor;
	}

	public void setExportarValor(String exportarValor) {
		this.exportarValor = exportarValor;
	}

	public String getImprimirNombre() {
		return imprimirNombre;
	}

	public void setImprimirNombre(String imprimirNombre) {
		this.imprimirNombre = imprimirNombre;
	}

	public String getImprimirFormato() {
		return imprimirFormato;
	}

	public void setImprimirFormato(String imprimirFormato) {
		this.imprimirFormato = imprimirFormato;
	}

	public String getImprimirDiseno() {
		return imprimirDiseno;
	}

	public void setImprimirDiseno(String imprimirDiseno) {
		this.imprimirDiseno = imprimirDiseno;
	}

	public String getImprimirScalebarUnit() {
		return imprimirScalebarUnit;
	}

	public void setImprimirScalebarUnit(String imprimirScalebarUnit) {
		this.imprimirScalebarUnit = imprimirScalebarUnit;
	}

	public int getImprimirWidth() {
		return imprimirWidth;
	}

	public void setImprimirWidth(int imprimirWidth) {
		this.imprimirWidth = imprimirWidth;
	}

	public int getImprimirHeight() {
		return imprimirHeight;
	}

	public void setImprimirHeight(int imprimirHeight) {
		this.imprimirHeight = imprimirHeight;
	}

	public int getImprimirDPI() {
		return imprimirDPI;
	}

	public void setImprimirDPI(int imprimirDPI) {
		this.imprimirDPI = imprimirDPI;
	}

	public boolean isImprimirLegend() {
		return imprimirLegend;
	}

	public void setImprimirLegend(boolean imprimirLegend) {
		this.imprimirLegend = imprimirLegend;
	}

	public String getExportarCapa() {
		return exportarCapa;
	}

	public void setExportarCapa(String exportarCapa) {
		this.exportarCapa = exportarCapa;
	}

	public String getExportarFormato() {
		return exportarFormato;
	}

	public void setExportarFormato(String exportarFormato) {
		this.exportarFormato = exportarFormato;
	}

	public String getServicioTipo() {
		return servicioTipo;
	}

	public void setServicioTipo(String servicioTipo) {
		this.servicioTipo = servicioTipo;
	}

	public String getServicioURL() {
		return servicioURL;
	}

	public void setServicioURL(String servicioURL) {
		this.servicioURL = servicioURL;
	}

	public String getUrlGisServiceU() {
		return urlGisServiceU;
	}

	public void setUrlGisServiceU(String urlGisServiceU) {
		this.urlGisServiceU = urlGisServiceU;
	}

	public String getUrlGisServiceR() {
		return urlGisServiceR;
	}

	public void setUrlGisServiceR(String urlGisServiceR) {
		this.urlGisServiceR = urlGisServiceR;
	}

	public List<AttributeDataTable> getSelectedTable() {
		return selectedTable;
	}

	public void setSelectedTable(List<AttributeDataTable> selectedTable) {
		this.selectedTable = selectedTable;
	}

	public String getFolioM() {
		return folioM;
	}

	public void setFolioM(String folioM) {
		this.folioM = folioM;
	}

	public List<DatosPredioDataTable> getDatosPredio() {
		return datosPredio;
	}

	public void setDatosPredio(List<DatosPredioDataTable> datosPredio) {
		this.datosPredio = datosPredio;
	}

	public List<DatosPredioDataTable> getDatosPredioM() {
		return datosPredioM;
	}

	public void setDatosPredioM(List<DatosPredioDataTable> datosPredioM) {
		this.datosPredioM = datosPredioM;
	}

	public List<DatosJuridicoDataTable> getDatosJuridico() {
		return datosJuridico;
	}

	public void setDatosJuridico(List<DatosJuridicoDataTable> datosJuridico) {
		this.datosJuridico = datosJuridico;
	}

	public List<DatosJuridicoDataTable> getDatosJuridicoM() {
		return datosJuridicoM;
	}

	public void setDatosJuridicoM(List<DatosJuridicoDataTable> datosJuridicoM) {
		this.datosJuridicoM = datosJuridicoM;
	}

	public List<AnotacionesDataTable> getDatosAnotaciones() {
		return datosAnotaciones;
	}

	public void setDatosAnotaciones(List<AnotacionesDataTable> datosAnotaciones) {
		this.datosAnotaciones = datosAnotaciones;
	}

	public List<AnotacionesDataTable> getDatosAnotacionesM() {
		return datosAnotacionesM;
	}

	public void setDatosAnotacionesM(List<AnotacionesDataTable> datosAnotacionesM) {
		this.datosAnotacionesM = datosAnotacionesM;
	}

	public List<String> getfMatriculas() {
		return fMatriculas;
	}

	public void setfMatriculas(List<String> fMatriculas) {
		this.fMatriculas = fMatriculas;
	}

	public DatosPredioDataTable getSelectedPredio() {
		return selectedPredio;
	}

	public void setSelectedPredio(DatosPredioDataTable selectedPredio) {
		this.selectedPredio = selectedPredio;
	}

	public DatosJuridicoDataTable getSelectedJuridico() {
		return selectedJuridico;
	}

	public void setSelectedJuridico(DatosJuridicoDataTable selectedJuridico) {
		this.selectedJuridico = selectedJuridico;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
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

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getExtent() {
		return extent;
	}

	public void setExtent(String extent) {
		this.extent = extent;
	}

	public boolean isAirports() {
		return airports;
	}

	public void setAirports(boolean airports) {
		this.airports = airports;
	}

	public boolean isRadar() {
		return radar;
	}

	public void setRadar(boolean radar) {
		this.radar = radar;
	}

	public GraphicsModel getGraphicsModel() {
		return graphicsModel;
	}

	public void setGraphicsModel(GraphicsModel graphicsModel) {
		this.graphicsModel = graphicsModel;
	}

	public GraphicsModel getStarbucksGraphicsModel() {
		return starbucksGraphicsModel;
	}

	public void setStarbucksGraphicsModel(GraphicsModel starbucksGraphicsModel) {
		this.starbucksGraphicsModel = starbucksGraphicsModel;
	}

	public String getGeoip() {
		return geoip;
	}

	public void setGeoip(String geoip) {
		this.geoip = geoip;
	}

	public GraphicsModel getGeoipGraphicsModel() {
		return geoipGraphicsModel;
	}

	public void setGeoipGraphicsModel(GraphicsModel geoipGraphicsModel) {
		this.geoipGraphicsModel = geoipGraphicsModel;
	}

}
