package co.gov.supernotariado.bachue.portalgeografico.component;

/**
 * Clase que define las constantes de la aplicacion.
 * @author datatools
 */
public final class Constants
{
  public static final String DEFAULT_JSAPI = "https://js.arcgis.com/3.27";
  public static final String DEFAULT_PROXY_PAGE = "/proxy/proxy.jsp";
  public static final double DEFAULT_LATITUDE = 4.528175D;
  public static final double DEFAULT_LONGITUDE = -74.4795D;
  public static final int DEFAULT_ZOOM = 3;
  public static final String DEFAULT_POPUP_ACTION = "Select";
  public static final String ATTRIBUTE_JSAPI = "jsapi";
  public static final String ATTRIBUTE_PROXY_ENABLED = "proxyEnabled";
  public static final String ATTRIBUTE_PROXY_URL = "proxyUrl";
  public static final String ATTRIBUTE_PROXY_ALL_REQUESTS = "proxyAllRequests";
  public static final String ATTRIBUTE_LATITUDE = "latitude";
  public static final String ATTRIBUTE_LONGITUDE = "longitude";
  public static final String ATTRIBUTE_ZOOM = "zoom";
  public static final String ATTRIBUTE_ZOOM_FACTOR = "zoomFactor";
  public static final String ATTRIBUTE_BACKGROUND = "background";
  public static final String ATTRIBUTE_LODS = "lods";
  public static final String ATTRIBUTE_WKID = "wkid";
  public static final String ATTRIBUTE_TYPE = "type";
  public static final String ATTRIBUTE_URL = "url";
  public static final String ATTRIBUTE_VISIBLE = "visible";
  public static final String ATTRIBUTE_WHERE = "where";
  public static final String ATTRIBUTE_EXTENT = "extent";
  public static final String ATTRIBUTE_OPACITY = "opacity";
  public static final String ATTRIBUTE_NUMBER = "number";
  public static final String ATTRIBUTE_MODEL = "model";
  public static final String ATTRIBUTE_PANEL_MAP = "mapPanel";
  public static final String ATTRIBUTE_PANEL_OVERVIEW = "overviewPanel";
  public static final String ATTRIBUTE_PANEL_GEOCODER = "geocoderPanel";
  public static final String ATTRIBUTE_PANEL_LEGEND = "legendPanel";
  public static final String ATTRIBUTE_PANEL_COORDINATES = "coordinatesPanel";
  public static final String ATTRIBUTE_PANEL_LOADING = "loadingPanel";
  public static final String ATTRIBUTE_PANEL_NAVIGATION = "navigationPanel";
  public static final String ATTRIBUTE_PANEL_MEASUREMENT = "measurementPanel";
  public static final String ATTRIBUTE_POPUP_ACTIONS = "popupActions";
  public static final String ATTRIBUTE_POPUP_ATTRIBUTES = "popupAttributes";
  public static final String ATTRIBUTE_POPUP_WIDTH = "popupWidth";
  public static final String ATTRIBUTE_POPUP_HEIGHT = "popupHeight";
  public static final String ATTRIBUTE_SHOW_SLIDER = "showSlider";
  public static final String ATTRIBUTE_SHOW_ARROWS = "showArrows";
  public static final String ATTRIBUTE_SHOW_POPUP = "showPopup";
  public static final String ATTRIBUTE_SHOW_POPUP_EXPORT = "showPopupExport";
  public static final String ATTRIBUTE_SHOW_SCALEBAR = "showScalebar";
  public static final String ATTRIBUTE_SHOW_LOGO = "showLogo";
  public static final String ATTRIBUTE_SHOW_ATTRIBUTION = "showAttribution";
  public static final String ATTRIBUTE_ENABLE_NAVIGATION = "enableNavigation";
  public static final String ATTRIBUTE_REFRESH_INTERVAL = "refreshInterval";
  public static final String ATTRIBUTE_SCALE_MIN = "minScale";
  public static final String ATTRIBUTE_SCALE_MAX = "maxScale";
  public static final String SERVICE_TILED = "tiled";
  public static final String SERVICE_IMAGE = "image";
  public static final String SERVICE_DYNAMIC = "dynamic";
  public static final String SERVICE_KML = "kml";
  public static final String SERVICE_FEATURE = "feature";
  public static final String SERVICE_GRAPHICS = "graphics";
  
  
  public static final String RUTA_RELATIVA_WAR = "/war/";
  public static final String RUTA_RELATIVA_TEMPLATE = "/war/resources/template/";
  public static final String RUTA_RELATIVA_IMAGES = "/war/resources/images/";
  public static final String RUTA_RELATIVA_FILE = "file:/";
  public static final String PLANTILLA_EXCEL = "PLANTILLA_EXCEL_ARCGIS";
  public static final String EXCEL_TABLA_ATRIBUTOS = "Tabla_Atributos.xlsx";
  public static final String HOJA_EXCEL_TABLA_ATRIBUTOS = "Hoja1";
  public static final String LOGO_EXCEL_1 = "LOGO_1_EXPORT_ARCGIS";
  public static final String LOGO_EXCEL_2 = "LOGO_2_EXPORT_ARCGIS";
  public static final String CONTENT_TYPE_EXCEL = "application/vnd.ms-excel";
  public static final String CONTENT_TYPE_PDF = "application/pdf";
  public static final String CONTENT_DISPOSITION = "Content-Disposition";
  public static final String COLOR_CABECERA_PDF = "#B22222";
  public static final String PDF_TABLA_ATRIBUTOS = "Tabla_Atributos.pdf";
  public static final String LOGO_PDF_1 = "LOGO_3_EXPORT_ARCGIS";
  public static final String LOGO_PDF_2 = "LOGO_4_EXPORT_ARCGIS";
  public static final String CABECERA_NUMERO = "Numero";
  public static final String CABECERA_NUPRE = "NUPRE";
  public static final String CABECERA_MATRICULA = "Matricula inmobiliaria";
  public static final String CABECERA_CHIP = "Cedula catastral + CHIP";
  public static final String CABECERA_DEPTO = "Departamento";
  public static final String CABECERA_MUNICIPIO = "Municipio";
  public static final String CABECERA_GRUPO = "Grupo";
  public static final String CABECERA_DIRECCION = "Direccion";
  public static final String CABECERA_TIPO_ID = "Tipo ID";
  public static final String CABECERA_IDENTIFICACION = "Identificacion";
  public static final String CABECERA_NOMBRE = "Nombre";
  
  
  public static final String MENSAJE_UPLOAD_1 = "Succesful";
  public static final String MENSAJE_UPLOAD_2 = " is uploaded.";
  public static final String MENSAJE_UPLOAD_3 = "Exito";
  public static final String MENSAJE_UPLOAD_4 = " Carga correcta. ";
  public static final String MENSAJE_UPLOAD_5 = "Advertencia";
  public static final String MENSAJE_UPLOAD_6 = " El máximo de registros valido para la consulta es de ";
  public static final String MENSAJE_UPLOAD_7 = " La estructura del archivo no se encuentra conforme a la esperada.";
  public static final String MENSAJE_UPLOAD_8 = " Archivo vacio.";
  public static final String MENSAJE_UPLOAD_9 = ": Coordenadas se encuentran con inconsistencias";
  public static final String MENSAJE_UPLOAD_10 = ": El archivo no contiene coordenadas";
  public static final String MENSAJE_UPLOAD_11 = ": El encabezado no concuerda (Consecutivo, Longitud, Latitud)";
  public static final String ARCHIVO_TXT = "archivo_txt";
  public static final String UTF8 = "UTF-8";
  public static final String DELIMITADOR_PUNTO = ".";
  public static final String DELIMITADOR_COMA = ",";
  public static final String FUNCION_LIMPIAR_JS = "CleanFUp()";
  public static final String EXTENCION_CSV = "CSV";
  public static final String CONSECUTIVO_UPLOAD = "consecutivo";
  public static final String LATITUD_UPLOAD = "latitud";
  public static final String LONGITUD_UPLOAD = "longitud";
  
  
  public static final String APLICACION_ES = "resources.application_ES";
  public static final String APLICACION_EN = "resources.application_EN";
  public static final String PAGINA_ERROR = "/gisportal/faces/view/error.xhtml";
  public static final String MENSAJE_SESION_1 = "Error en direccionamiento en pagina de error: ";
  public static final String MENSAJE_SESION_2 = "Error en direccionamiento a pagina de cierre de sesion: ";
  public static final String PAGINA_CIERRE_SESION = "http://vmwtstagw1.snr.local/salirsm";
  
  
  public static final String BACKGR = "BACKGROUND";
  public static final String LONGITUD = "LONGITUDE";
  public static final String LATITUD = "LATITUDE";
  public static final String UNIFORMRL = "URL";
  public static final String URL_SERVICE = "URL_SERVICE";
  public static final String MENSAJE_MAPBEAN_1 = "Constructor...";
  public static final String MENSAJE_MAPBEAN_2 = "Postconstructor Init...";
  public static final String MENSAJE_MAPBEAN_3 = "No se han encontrado datos";
  public static final String MENSAJE_MAPBEAN_4 = "reset...";
  public static final String MENSAJE_MAPBEAN_5 = "Error al Insertar";
  public static final String MENSAJE_MAPBEAN_6 = "El registro ya existe";
  public static final String MENSAJE_MAPBEAN_7 = "Campos Vacios";
  public static final String MENSAJE_MAPBEAN_8 = "Error al Modificar";
  public static final String MENSAJE_MAPBEAN_9 = "Debe llenar los campos";
  public static final String MENSAJE_MAPBEAN_10 = "Error al Eliminar";
  public static final String MENSAJE_MAPBEAN_11 = "Debe Seleccionar un valor";
  public static final String MENSAJE_MAPBEAN_12 = "No puede haber campos sin información";
  public static final String MENSAJE_MAPBEAN_13 = "Excede el valor máximo, por favor verificar";
  public static final String URL_ARCGIS = "URL_ARCGIS";
  public static final String URL_FORM_ARCGIS = "URL_FORM_ARCGIS";
  public static final String LATITUD_ARGIS = "LATITUD_ARGIS";
  public static final String LONGITUD_ARCGIS = "LONGITUD_ARCGIS";
  public static final String BACKGR_ARCGIS = "BACKGR_ARCGIS";
  public static final String ZONA_RURAL = "/6";
  public static final String ZONA_URBANA = "/2";
  public static final String CAPA = "CAPA";
  public static final String FORMATO_SHP = "SHP";  
  public static final String LAYERS_OFF = "Apagar todas las capas";
  public static final String LAYERS_ON = "Abrir todas las capas";
  public static final String WHERE_MAGNITUDE = "magnitude >= 0";
  public static final String EXTENT_MAGNITUDE = "magnitude >= 1";
  public static final String GEOCODED_IP = "Geocoded IP Addresses";
  public static final String ESTADO_ACTIVO_ADMON = "A";
  public static final String ESTADO_INACTIVO_ADMON = "I";
  public static final String HEADER_LIST_1 = "X-Forwarded-For";
  public static final String HEADER_LIST_1_1 = "X-FORWARDED-FOR";
  public static final String HEADER_LIST_1_2 = "x-forwarded-for";
  public static final String HEADER_LIST_2 = "Proxy-Client-IP";
  public static final String HEADER_LIST_3 = "WL-Proxy-Client-IP";
  public static final String HEADER_LIST_4 = "HTTP_X_FORWARDED_FOR";
  public static final String HEADER_LIST_5 = "HTTP_X_FORWARDED";
  public static final String HEADER_LIST_6 = "HTTP_X_CLUSTER_CLIENT_IP";
  public static final String HEADER_LIST_7 = "HTTP_CLIENT_IP";
  public static final String HEADER_LIST_8 = "HTTP_FORWARDED_FOR";
  public static final String HEADER_LIST_9 = "HTTP_FORWARDED";
  public static final String HEADER_LIST_10 = "HTTP_VIA";
  public static final String HEADER_LIST_11 = "REMOTE_ADDR";
  public static final String UNKNOWN = "unknown";
  public static final String FUNCION_RESET_SERVICE_JS = "menu1();";
  public static final String FUNCION_RESET_SERVICE2_JS = "menu2();";
  public static final String FUNCION_MOVE_SERVICE_JS = "moveLayer();";
  public static final String FUNCION_MOVE_SERVICE2_JS = "moveLayer2();";
  public static final String FUNCION_RESET_CAPA_JS = "layer1();";
  public static final String FUNCION_RESET_CAPA2_JS = "layer2();";
  public static final String FUNCION_LIMPIAR_ADMON_JS = "cleanAdmon();";
  public static final String ACTUALIZAR_COMPONENTE_MUNICIPIOS = "westForm:idSelection5";
  public static final String VISUALIZAR_COMPONENTE_MUNICIPIOS = "document.getElementById('westForm:idSelection5').style.display = 'block';";
  public static final String CAPA_MUNICIPIOS = "/8";
  public static final String URBANO = "U";
  public static final String RURAL = "R";
  public static final String MENSAJE_CARGANDO = "Cargando...";
  public static final String VISUALIZAR_TABLA_CONSULTAR_JS = "$('.table').show();";
  public static final String CONSULTAR_TITULAR = "Datos del titular";
  public static final String VISUALIZAR_TIPO_BUSQUEDA = "$('.idSelection2').show();$('.idSelection0').hide();";
  public static final String BUSQUEDA_BUS_NO = "No";
  public static final String ACTUALIZAR_CAMPO_ADMON = "centerForm:dlgForm:admonTabs:inputMod";
  public static final String ACTUALIZAR_CAMPO_BUS = "centerForm:dlgForm:admonTabs:inputModBus";
  public static final String ACTUALIZAR_CHB_BUS = "centerForm:dlgForm:admonTabs:chbModBus";
  public static final String ACTUALIZAR_BOTON_MODIFICAR_ADMON = "centerForm:dlgForm:admonTabs:btnAdmdMod";
  public static final String ACTUALIZAR_BOTON_BORRAR_ADMON = "centerForm:dlgForm:admonTabs:btnAdmdDelet";
  public static final String ACTUALIZAR_SELECCION_ADMON = "centerForm:dlgForm:admonTabs:idSelectionAdmon";
  public static final String ACTUALIZAR_MENSAJE = "centerForm:dlgForm:admonTabs:msgDlg";
  public static final int CERO = 0;
  public static final int CANTIDAD_REGISTROS_CONSULTA = 200;
  public static final int CANTIDAD_REGISTROS_CSV = 500;
  public static final int CANTIDAD_REGISTROS_TXT = 500;
  public static final int CANTIDAD_REGISTROS_TABLAA = 5000;
  public static final String CODIGO_CEDULA_CATRASTAL = "CEDULA_CATASTRAL";
  public static final String CONSULTA_INTERESADO = "consulta_interesado";
  public static final String CONSULTA_CSV = "archivo_csv";
  public static final String CONSULTA_TXT = "archivo_txt";
  public static final String CONSULTA_ATRIBUTOS = "tabla_atributos";
  public static final String VISUALIZAR_POUP_MATRICULAS_JS = "PF('dlg2').show();";
  public static final String OCULTAR_POUP_MATRICULAS_JS = "PF('dlg2').hide();"; 
  public static final String VISUALIZAR_POUP_ANOTACIONES_JS = "PF('dlg3').show();";
  public static final String OCULTAR_POUP_ANOTACIONES_JS = "PF('dlg3').hide();";
  public static final String VISUALIZAR_POUP_INTERVINIENTES_JS = "PF('dlg4').show();";
  public static final String CAPAS_OGC_VARIABLE_1 = "-2";
  public static final String SERVICIOS_OGC = "Servicios OGC";
  public static final String SERVICE = "Service";
  public static final String NULO = "null";
  
  
  public static final String BACKGROUND_SERVICE_BEAN = "gray";
  public static final double LATITUDE_SERVICE_BEAN = 39.828175;
  public static final double LONGITUDE_SERVICE_BEAN = -98.5795;
  public static final int ZOOM_SERVICE_BEAN = 4;
  public static final String URL_SERVICE_BEAN = "http://sampleserver1.arcgisonline.com/ArcGIS/rest/services/PublicSafety/PublicSafetyOperationalLayers/MapServer";
  public static final String CLICK_SERVICE_BEAN = "Map Click Event";
  public static final String EXTENT_SERVICE_BEAN = "Map Extent Update Event";
  public static final String GRAPHIC_SERVICE_BEAN = "Map Graphic View Event";
  public static final String MAP_SERVICE_BEAN = "/mapserver";
  public static final String SERVICEBEAN_MENSAJE_1 = "Not a valid ESRI dynamic map serivce.";
  public static final String SERVICEBEAN_MENSAJE_2 = "URL must end in '/MapServer'.";
  public static final String SERVICEBEAN_PAGINA_REDIRECCION = "service?faces-redirect=true";
  
  
  public static final String MENSAJE_CONTROLLER_1 = "Constructor...";
  
  
  public static final String MENSAJE_ADMON_1 = "getAll...";
  public static final String LISTAVALORES_FINDTWO = "ListaValores.findTwo";
  public static final String LISTAVALORES_FINDTWOA = "ListaValores.findTwoA";
  public static final String LISTAVALORES_FINDVALUE = "ListaValores.findValue";
  public static final String LISTAVALORES_FINDALLVALUEA = "ListaValores.findAllValueA";
  public static final String TLISTA = "tLista";
  public static final String TVALOR = "tvalor";
  public static final String BUSQUEDABUS_FINDALL = "BusquedaBus.findAll";
  public static final String ADMONREGISTROS_FINDVALUE = "AdmonRegistros.findValue";
  public static final String TREGISTRO = "tregistro";
  public static final String LISTAVALORES_FINDVALUEA = "ListaValores.findValueA";
  public static final String LISTAVALORES_FINDTWOAMAX = "ListaValores.findTwoAMax";
  public static final String TDESCRIP = "tdescrip";
  public static final String BUSQUEDABUS_FINDVALUE = "BusquedaBus.findValue";
  public static final String TDESCRIPCION = "tdescripcion";
  public static final String LISTAVALORES_CHANGEVALUE = "ListaValores.changeValue";
  public static final String TVALORP = "tvalorp";
  public static final String TESTADO = "testado";
  public static final String USUMOD = "usuMod";
  public static final String FECHAMOD = "fechaMod";
  public static final String CODBUS = "codbus";
  public static final String TBUSQBUS = "tbusqBus";
  public static final String LISTAVALORES_DELETEVALUE = "ListaValores.deleteValue";
  public static final String ADMONREGISTROS_CHANGEVALUE = "AdmonRegistros.changeValue";
  public static final String CANTIDAD = "cantidad";
  public static final String REGISTRO = "registro";
  
  
  public static final String ENDPOINT_BUS_ARCGIS = "ENDPOINT_BUS_ARCGIS";
  public static final String MODULO_ARCGIS = "MODULO_ARCGIS";
  public static final String ESPACIO_NOMBRE_ARCGIS = "ESPACIO_NOMBRE_ARCGIS";
  public static final String METODO_ARCGIS = "METODO_ARCGIS";
  public static final String OTROS =" y otros";
  public static final String DELIMITADOR_GUION_ESPACIO = " - ";
  public static final String LAYER2_6 = "layer2-6";
  public static final String TABLA_ATRIBUTOS = "tabla_atributos";
  
  
  public static final String COD_MODULO_ARCGIS = "COD_MODULO_ARCGIS";
  public static final String URL_WS_GESTION_USUARIOS = "URL_WS_GESTION_USUARIOS";
  
  
  public static final String CONSTANTESBACHUE_FINDVALUE ="ConstantesBachue.findValue";
  public static final String IDCONST = "idConst";
  public static final String CONSTANTESBACHUE_FINDALL = "ConstantesBachue.findAll";
  public static final String CONSTANTESBACHUE_FINDALLWHERE = "ConstantesBachue.findAllWhere";
  public static final String MENSAJE_GISPARAMETRO_1 = "getAll...";
  
  
  public static final String MENSAJE_RESOURCEBD_1 = "getAll...";
  public static final String DEPARTAMENTO_FINDALL = "Departamento.findAll";
  public static final String MUNICIPIOS_FINDALLVALUE = "Municipios.findAllValue";
  public static final String COD_DEPTO = "cod_depto";
  
  
  public static final String VEREDA_SELECT_1 = "select v from ";
  public static final String VEREDA_ALIAS = " v";
  
  
  public static final String URL_SERVICESBEAN = "http://sampleserver1.arcgisonline.com/ArcGIS/rest/services/PublicSafety";
  
  
  public static final String TABLA_ADMON_REGISTROS = "SDB_BNG_ADMON_REGISTROS";
  public static final String SELECT_ADMON_REGISTROS_CANTIDAD = "SELECT ar.CANTIDAD FROM AdmonRegistros ar where ar.REGISTRO = :tregistro";
  public static final String UPDATE_ADMON_REGISTROS_CANTIDAD = "UPDATE AdmonRegistros SET CANTIDAD = :cantidad, ID_USUARIO_MODIFICACION = :usuMod, FECHA_MODIFICACION = :fechaMod where REGISTRO = :registro";
  
  
  public static final String TABLA_BUSQUEDA_BUS = "SDB_BNG_BUSQUEDA_BUS";
  public static final String SELECT_BUSQUEDA_BUS = "SELECT b FROM BusquedaBus b";
  public static final String SELECT_BUSQUEDA_BUS_COD ="SELECT b.COD_TIPO_BUSQUEDA FROM BusquedaBus b WHERE b.DESCRIPCION = :tdescripcion";
  
  
  public static final String TABLA_CONSTANTES_BACHUE ="SDB_PGN_CONSTANTES";
  public static final String SELECT_CONSTANTES_BACHUE = "SELECT c FROM ConstantesBachue c";
  public static final String SELECT_CONSTANTES_BACHUE_WHERE = "SELECT c FROM ConstantesBachue c where c.ID_CONSTANTE = :idConst";
  public static final String SELECT_CONSTANTES_BACHUE_CARACTER = "SELECT c.CARACTER FROM ConstantesBachue c where c.ID_CONSTANTE = :idConst";
  
  
  public static final String TABLA_DEPARTAMENTO = "SDB_BGN_DEPARTAMENTO";
  public static final String SELECT_DEPARTAMENTO = "SELECT d FROM Departamento d";
  public static final String DEPARTAMENTO_FINDVALUE = "Departamento.findValue";
  public static final String SELECT_DEPARTAMENTO_DEPTO = "SELECT d FROM Departamento d where d.ID_DEPARTAMENTO = :depto and d.ID_PAIS = :pais";
  
  
  public static final String TABLA_LISTA_VALORES = "SDB_ACC_LISTA_VALORES";
  public static final String SELECT_LISTA_VALORES_DATATABLE = "SELECT NEW co.gov.supernotariado.bachue.portalgeografico.model.DataTable(1,lv.VALOR,lv.ESTADO,lv.TIPO_LISTA,lv.COD_BUS,b.DESCRIPCION) "
	+ "FROM ListaValores lv, BusquedaBus b where lv.TIPO_LISTA = :tLista AND lv.COD_BUSQUEDA_BUS = b.COD_TIPO_BUSQUEDA";
  public static final String SELECT_LISTA_VALORES_DATATABLE_ACTIVO = "SELECT NEW co.gov.supernotariado.bachue.portalgeografico.model.DataTable(1,lv.VALOR,lv.ESTADO,lv.TIPO_LISTA,lv.COD_BUS,b.DESCRIPCION) "
	+ "FROM ListaValores lv, BusquedaBus b where lv.TIPO_LISTA = :tLista AND lv.COD_BUSQUEDA_BUS = b.COD_TIPO_BUSQUEDA AND lv.ESTADO = 'A'";
  public static final String SELECT_LISTA_VALORES_DATATABLE_DESCRIPCION = "SELECT NEW co.gov.supernotariado.bachue.portalgeografico.model.DataTable(1,lv.VALOR,lv.ESTADO,lv.TIPO_LISTA,lv.COD_BUS,b.DESCRIPCION) "
	+ "FROM ListaValores lv, BusquedaBus b where lv.TIPO_LISTA = :tLista AND b.DESCRIPCION = :tdescrip AND lv.COD_BUSQUEDA_BUS = b.COD_TIPO_BUSQUEDA";

  public static final String LISTAVALORES_FINDALL = "ListaValores.findAll";
  public static final String SELECT_LISTA_VALORES = "SELECT lv FROM ListaValores lv";
  public static final String SELECT_LISTA_VALORES_CODBUS = "SELECT lv.COD_BUS FROM ListaValores lv where lv.TIPO_LISTA = :tLista AND lv.VALOR = :tvalor";
  public static final String LISTAVALORES_FINDALLVALUE ="ListaValores.findAllValue";
  public static final String SELECT_LISTA_VALORES_TLISTA = "SELECT lv FROM ListaValores lv where lv.TIPO_LISTA = :tLista";
  public static final String SELECT_LISTA_VALORES_TLISTA_ACTIVO = "SELECT lv FROM ListaValores lv where lv.TIPO_LISTA = :tLista AND lv.ESTADO = 'A'";
  public static final String SELECT_LISTA_VALORES_TLISTA_ACTIVO_VALOR = "SELECT lv FROM ListaValores lv where lv.TIPO_LISTA = :tLista AND lv.ESTADO = 'A' AND lv.VALOR = :tvalor";
  public static final String UPDATE_LISTA_VALORES = "UPDATE ListaValores SET VALOR = :tvalor, ESTADO = :testado, COD_BUS = :codbus, COD_BUSQUEDA_BUS = :tbusqBus, ID_USUARIO_MODIFICACION = :usuMod, FECHA_MODIFICACION = :fechaMod where TIPO_LISTA = :tLista AND VALOR = :tvalorp";
  public static final String DELETE_LISTA_VALORES = "DELETE FROM ListaValores where TIPO_LISTA = :tLista AND VALOR = :tvalorp";

  
  public static final String TABLA_MUNICIPIOS = "MUNICIPIOS";
  public static final String SELECT_MUNICIPIOS = "SELECT m FROM Municipios m where m.COD_DANE = :cod_depto";
  
  
  public static final String TABLA_PAIS = "SDB_BGN_PAIS";
  
  
  public static final String TABLA_VEREDA = "VEREDA";
  public static final String VEREDA_GETALL = "Vereda.getAll";
  public static final String SELECT_VEREDA = "SELECT v FROM Vereda v";
  
  
  public static final String NAME_LAYER_METADATA = "name";
  public static final String TYPE_LAYER_METADATA = "type";
  public static final String GEOMETRYTYPE_LAYER_METADATA = "geometryType";
  public static final String FIELDS_LAYER_METADATA = "fields";
  public static final String ALIAS_LAYER_METADATA = "alias";
  public static final String LENGTH_LAYER_METADATA = "length";
  public static final String DOMAIN_LAYER_METADATA = "domain";
  public static final String CODEDVALUES_LAYER_METADATA = "codedValues";
  public static final String CODE_LAYER_METADATA = "code";
  
  
  public static final String CURRENTV_METADATA = "currentVersion";
  public static final String SERVICED_METADATA = "serviceDescription";
  public static final String LAYERS_METADATA = "layers";
  public static final String ID_METADATA = "id";
  
  public static final String VERSION_POSTCONSTRUCT = "version";
  public static final String MENSAJE_POSTCONSTRUCT_1 = "Running on gisportal ";
  public static final String POM_PROPERTIES_POSTCONSTRUCT = "/META-INF/maven/com.gisportal/gisportal/pom.properties";
  public static final String MENSAJE_POSTCONSTRUCT_2 = "Error loading gisportal.properties file.";
  
  
}










