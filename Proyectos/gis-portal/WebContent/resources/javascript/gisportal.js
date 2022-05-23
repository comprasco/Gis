if (!gisportal) {
  var gisportal = {};
  var shapeFile,
    shapeFileLayers = [];
}

var sendJSF,
  find,
  urlPrints = [],
  indexUrlPrints = 0,
  filename,
  link,
  loadfileuploadChange = false,
  OGCLayers = [],
  legendLayers = [],
  globalGeometry,
  noDraw = false;
var urlGisServiceU, urlGisServiceR;
var dynamicLayerInfos,
  infos = { total: 0 },
  loadFirstService = false,
  dndSource,
  currentLayer;

var kmlOptions = {
  name: 'nombre',
  description: 'descripcion',
  documentName: 'SNR Export',
  documentDescription: 'KML exportado desde SNR',
  simplestyle: true,
  extendedData: true,
  styleAttributes: null,
};

var shapefileOptions = {
  folder: 'shapefile',
  types: {
    point: 'points',
    polygon: 'polygons',
    polyline: 'polylines',
  },
  wkt: null,
};

/* Create Navigation Toolbar */
gisportal.populateNavigationToolbar = function (a, b) {
  dojo.create(
    'a',
    {
      href: '#',
      class: 'fa fa-mouse-pointer',
      title: 'Paneo',
      onclick: 'gisportal.selectNavigationToolbarPan();',
    },
    a
  );
  dojo.create(
    'a',
    {
      href: '#',
      class: 'fa fa-search-plus',
      title: 'Acercar',
      onclick: 'gisportal.selectNavigationToolbarZoomIn();',
    },
    a
  );
  dojo.create(
    'a',
    {
      href: '#',
      class: 'fa fa-search-minus',
      title: 'Alejar',
      onclick: 'gisportal.selectNavigationToolbarZoomOut();',
    },
    a
  );
  dojo.create(
    'a',
    {
      href: '#',
      class: 'fa fa-refresh',
      title: 'Refrescar capas',
      onclick: 'gisportal.refreshMapLayers();',
    },
    a
  );
  dojo.create(
    'a',
    {
      href: '#',
      class: 'fa fa-arrows-alt',
      title: 'Cambiar a vista completa',
      onclick: 'gisportal.toggleFullScreenMode();',
    },
    a
  );
  dojo.create(
    'a',
    {
      href: '#',
      class: 'fa fa-globe',
      title: 'Extension Total',
      onclick: 'gisportal.selectNavigationToolbarZoomFullExtent();',
    },
    a
  );
  dojo.create(
    'a',
    {
      href: '#',
      class: 'fa fa-crosshairs',
      title: 'Ir a posicion actual',
      onclick: 'gisportal.getCurrentPosition();',
    },
    a
  );
  dojo.create(
    'a',
    {
      href: '#',
      class: 'fa fa-arrow-left',
      title: 'Extension anterior',
      onclick: 'gisportal.selectNavigationToolbarPreviousExtent();',
    },
    a
  );
  dojo.create(
    'a',
    {
      href: '#',
      class: 'fa fa-arrow-right',
      title: 'Extension siguiente',
      onclick: 'gisportal.selectNavigationToolbarNextExtent();',
    },
    a
  );
  dojo.create(
    'a',
    {
      href: '#',
      class: 'fa fa-times',
      title: 'Reiniciar herramientas',
      onclick: 'gisportal.resetNavigationToolbar();',
    },
    a
  );
  dojo.create(
    'a',
    {
      href: '#',
      class: 'fa fa-map-o',
      title: 'Mapas Base',
      onclick: 'gisportal.toggleBaseMapsGallery()',
    },
    a
  );
};

gisportal.processMapClick = function (event) {
  this.globalGeometry = event.mapPoint;
  var mapPoint = event.mapPoint;
  var screenPoint = event.screenPoint;
  if (!gisportal.isMeasurementToolSelected()) {
    gisportal.generateJsfMapClickEvent(mapPoint, screenPoint);
  }
};

var loadCapIdent = [];
var loadIdentify = true;
/* Load InfoTemplates */
gisportal.loadInfoTemplates = function (lyr) {
  if (loadIdentify) {
    loadCapIdent.push(lyr);
  }
  var lyrInfos = lyr.layerInfos;
  if (lyrInfos) {
    var arrayTpls = {};
    for (var a = 0; a < lyrInfos.length; a++) {
      var lyrInfo = lyrInfos[a];
      var lyrName = lyrInfos[a].name;
      if (
        identifiedCap === null ||
        identifiedCap === 'Todas las capas visibles' ||
        identifiedCap === undefined
      ) {
        var popupAttribs = null;
        if (lyr.popupAttributes) {
          popupAttribs = lyr.popupAttributes[lyrInfo.id];
        }
        if (popupAttribs == null || popupAttribs.length != 0) {
          arrayTpls[lyrInfo.id] = {
            infoTemplate: gisportal.template,
          };
        }
      } else {
        if (identifiedCap === lyrName) {
          var popupAttribs = null;
          if (lyr.popupAttributes) {
            popupAttribs = lyr.popupAttributes[lyrInfo.id];
          }
          if (popupAttribs == null || popupAttribs.length != 0) {
            arrayTpls[lyrInfo.id] = {
              infoTemplate: gisportal.template,
            };
          }
        }
      }
    }
    lyr.setInfoTemplates(arrayTpls);
  }
};

/* Build Generic Info Template Feature Title */
gisportal.buildGenericInfoTemplateFeatureTitle = function (graphic) {
  gisportal.setGraphicSourceProperties(graphic);
  var divTitle = dojo.create('div');
  gisportal.renderGraphicSymbology(graphic, divTitle);
  var lyrName = graphic.getLayer().name;
  if (lyrName && lyrName.length >= 35) {
    lyrName = lyrName.substr(0, 32) + '...';
  }
  var c = '';
  if (gisportal.map.infoWindow.count != 1) {
    c =
      '(' +
      (gisportal.map.infoWindow.selectedIndex + 1) +
      '/' +
      gisportal.map.infoWindow.count +
      ')';
  }
  var tbl =
    "<table class='gisportalPopupTitleTable'><tr><td>" +
    divTitle.innerHTML +
    '</td><td>' +
    lyrName +
    '</td><td>' +
    c +
    '</td></tr></table>';
  return tbl;
};

/* Build Generic Info Template Content */
gisportal.buildGenericInfoTemplateContent = function (graphic) {
  gisportal.setGraphicSourceProperties(graphic);

  var popupAttribs = null;
  if (
    gisportal[graphic.source.serviceId] &&
    gisportal[graphic.source.serviceId].popupAttributes
  ) {
    popupAttribs =
      gisportal[graphic.source.serviceId].popupAttributes[
        graphic.source.layerId
      ];
  }
  var tbl = "<table class='gisportalPopupTable'>";
  var attributes = graphic.attributes;
  var lyrName = graphic.getLayer().name;
  for (var field in attributes) {
    var alias = '';
    var valueData = attributes[field];
    var fieldType = '';
    var graphicLayerField = gisportal.getGraphicLayerField(field, graphic);
    if (graphicLayerField) {
      alias = graphicLayerField.alias;
      fieldType = graphicLayerField.type;
    }
    if (valueData) {
      if (typeof valueData === 'string') {
        if (valueData.toLowerCase() == 'null') {
          valueData = '';
        } else {
          if (gisportal.isImage(valueData.toLowerCase())) {
            valueData =
              "<a target='_blank' href='" +
              valueData +
              "'><img alt='' height='50' src='" +
              valueData +
              "'/></a>";
          } else {
            if (gisportal.isLink(valueData.toLowerCase())) {
              valueData =
                "<a target='_blank' href='" + valueData + "'>Detalles</a>";
            }
          }
        }
      } else {
        if (typeof valueData === 'number') {
          if (fieldType == 'esriFieldTypeDate') {
            valueData = gisportal.getFormattedDate(valueData);
          }
        }
      }
    } else {
      valueData = '';
    }
    if (
      popupAttribs == null ||
      popupAttribs.contains(field) ||
      popupAttribs.contains(alias)
    ) {
      tbl +=
        "<tr><td width='50%'>" +
        alias +
        "</td><td width='50%'>" +
        valueData +
        '</td></tr>';
    }
  }
  tbl += '</table>';

  tbl +=
    "<table style='width:100%;text-align:center;'><tr><td><a href='#' onclick='goIrFomulario();'>Consultar Informacion Juridica</a></td></tr><table>";

  if (graphic.draggable) {
    var shp = graphic.getShape();
    if (shp) {
      shp.moveToFront();
    }
  }
  gisportal.generateJsfMapGraphicViewEvent();
  return tbl;
};
gisportal.setGraphicSourceProperties = function (graphic) {
  var lyr = graphic.getLayer();
  if (lyr) {
    var lyrId = lyr.layerId;
    var serviceId = lyr.id.replace(new RegExp('_' + lyrId + '$'), '');
    graphic.source = {};
    graphic.source.serviceId = serviceId;
    graphic.source.layerId = lyrId;
  }
};
gisportal.renderGraphicSymbology = function (graphic, node) {
  var symbol = null;
  if (graphic.symbol) {
    symbol = graphic.symbol;
  } else {
    var lyr = graphic.getLayer();
    if (lyr && lyr.renderer) {
      if (lyr.renderer.symbol) {
        symbol = lyr.renderer.symbol;
      } else {
        symbol = lyr.renderer.getSymbol(graphic);
      }
    }
  }
  if (symbol) {
    var alpha = null;
    if (symbol.color && symbol.color.a != 0) {
      if (graphic.source && graphic.source.serviceId) {
        var lyr = gisportal.map.getLayer(graphic.source.serviceId);
        if (lyr && lyr.opacity && lyr.opacity != 1) {
          alpha = symbol.color.a;
          symbol.color.a = symbol.color.a * lyr.opacity;
        }
      }
    }
    if (symbol.url) {
      dojo.create(
        'img',
        {
          src: symbol.url,
          style: {
            maxHeight: '23px',
          },
        },
        node
      );
    } else {
      try {
        var m = esri.symbol.getShapeDescriptors(symbol);
        if (m.defaultShape.type == 'text') {
          m.defaultShape.text = m.defaultShape.text.substr(0, 3);
        }
        var size = 24;
        var surface = dojox.gfx.createSurface(node, size, size);
        var shape = surface
          .createShape(m.defaultShape)
          .setFill(m.fill)
          .setStroke(m.stroke);
        var bbox = shape.getTransformedBoundingBox();
        var g = (bbox[0].x + bbox[1].x + bbox[2].x + bbox[3].x) / 4;
        var d = (bbox[0].y + bbox[1].y + bbox[2].y + bbox[3].y) / 4;
        shape.applyTransform({
          dx: size / 2 - g,
          dy: size / 2 - d,
        });
      } catch (k) {
        console.error('Error generando simbolo desde descriptor.', k.message);
      }
    }
    if (alpha) {
      symbol.color.a = alpha;
    }
  }
};
gisportal.getGraphicLayerField = function (field, graphic) {
  var fields = graphic.getLayer().fields;
  if (fields) {
    for (var i = 0; i < fields.length; i++) {
      var f = fields[i];
      if (field == f.name) {
        return f;
      }
    }
  }
  return null;
};
gisportal.addPictureMarkerGraphic = function (
  graphicLyr,
  uuid,
  type,
  y,
  x,
  attrs,
  url,
  width,
  height,
  angle,
  draggable
) {
  if (!url) {
    url =
      gisportal.jsapi +
      '/esri/dijit/GeocodeMatch/images/EsriBluePinCircle26.png';
    width = 26;
    height = 26;
  }
  try {
    var geom = esri.geometry.geographicToWebMercator(
      new esri.geometry.Point(x, y)
    );
    var pms = new esri.symbol.PictureMarkerSymbol(url, width, height);
    pms.setAngle(angle);
    var graphic = new esri.Graphic(geom, pms, attrs);
    graphic.uuid = uuid;
    graphic.type = type;
    graphic.draggable = draggable;
    graphicLyr.add(graphic);
  } catch (err) {
    console.error(
      'Error adicionando picture marker al graphic layer.',
      err.message
    );
  }
};
gisportal.addSvgMarkerGraphic = function (
  graphicLyr,
  uuid,
  type,
  y,
  x,
  attrs,
  path,
  size,
  angle,
  color,
  alpha,
  style,
  lineColor,
  lineAlpha,
  lineWidth,
  draggable
) {
  try {
    var geom = esri.geometry.geographicToWebMercator(
      new esri.geometry.Point(x, y)
    );
    var sms = new esri.symbol.SimpleMarkerSymbol();
    sms.setStyle(esri.symbol.SimpleMarkerSymbol.STYLE_PATH);
    sms.setPath(path);
    sms.setSize(size);
    sms.setAngle(angle);
    var color = gisportal.buildColor(color, alpha);
    if (color) {
      sms.setColor(color);
    }
    var sls = gisportal.buildSimpleLineSymbol(
      style,
      lineColor,
      lineAlpha,
      lineWidth
    );
    if (sls) {
      sms.setOutline(sls);
    }
    var graphic = new esri.Graphic(geom, sms, attrs);
    graphic.uuid = uuid;
    graphic.type = type;
    graphic.draggable = draggable;
    graphicLyr.add(graphic);
  } catch (err) {
    console.error(
      'Error adicionando svg marker al graphic layer.',
      err.message
    );
  }
};
gisportal.addPolylineGraphic = function (i, a, l, n, h, c, g, j, d) {
  try {
    var m = new esri.geometry.Polyline(n);
    var f = gisportal.buildSimpleLineSymbol(c, g, j, d);
    var b = new esri.Graphic(m, f, h);
    b.uuid = a;
    b.type = l;
    i.add(b);
  } catch (k) {
    console.error('Error adicionando polyline al graphics layer.', k.message);
  }
};
gisportal.addPolygonGraphic = function (
  t,
  n,
  d,
  c,
  h,
  p,
  i,
  r,
  g,
  f,
  m,
  a,
  s,
  b,
  q
) {
  try {
    var j = new esri.geometry.Polygon(c);
    var l = null;
    if (p) {
      l = gisportal.buildPictureFillSymbol(p, i, r, a, s, b, q);
    } else {
      l = gisportal.buildSimpleFillSymbol(g, f, m, a, s, b, q);
    }
    var k = new esri.Graphic(j, l, h);
    k.uuid = n;
    k.type = d;
    t.add(k);
  } catch (o) {
    console.error('Error adding graphics layer polygon.', o.message);
  }
};
gisportal.addCircleGraphic = function (
  y,
  s,
  c,
  o,
  m,
  j,
  u,
  k,
  w,
  h,
  g,
  r,
  a,
  x,
  b,
  v,
  d,
  n,
  i
) {
  try {
    var p = esri.geometry.geographicToWebMercator(
      new esri.geometry.Point(m, o)
    );
    var f = new esri.geometry.Circle(p, {
      radius: d,
      radiusUnit: n,
      numberOfPoints: i,
    });
    var q = null;
    if (u) {
      q = gisportal.buildPictureFillSymbol(u, k, w, a, x, b, v);
    } else {
      q = gisportal.buildSimpleFillSymbol(h, g, r, a, x, b, v);
    }
    var l = new esri.Graphic(f, q, j);
    l.uuid = s;
    l.type = c;
    y.add(l);
  } catch (t) {
    console.error('Error adicionando circulo al graphic layer.', t.message);
  }
};
gisportal.addTextGraphic = function (
  A,
  v,
  b,
  o,
  k,
  g,
  j,
  n,
  w,
  q,
  u,
  a,
  x,
  m,
  d,
  B,
  p,
  t,
  h
) {
  try {
    var r = esri.geometry.geographicToWebMercator(
      new esri.geometry.Point(k, o)
    );
    var s = new esri.symbol.TextSymbol(j);
    s.setAngle(x);
    if (u) {
      var z = gisportal.buildColor(u, a);
      if (z) {
        s.setColor(z);
      }
    }
    var l = gisportal.buildFont(n, w, q);
    if (l) {
      s.setFont(l);
    }
    if (m) {
      var f = gisportal.buildColor(m, a);
      if (f) {
        s.setHaloColor(f);
        s.setHaloSize(d);
      }
    }
    if (B || p) {
      s.setOffset(B, p);
    }
    if (t) {
      s.setHorizontalAlignment(t);
    }
    if (h) {
      s.setVerticalAlignment(h);
    }
    var i = new esri.Graphic(r, s, g);
    i.uuid = v;
    i.type = b;
    A.add(i);
  } catch (y) {
    console.error('Error adicionando texto al graphic layer.', y.message);
  }
};
gisportal.buildSimpleFillSymbol = function (i, c, h, f, j, b, g) {
  var d = new esri.symbol.SimpleFillSymbol();
  if (i) {
    d.setStyle(i);
  }
  var e = gisportal.buildColor(c, h);
  if (e) {
    d.setColor(e);
  }
  var a = gisportal.buildSimpleLineSymbol(f, j, b, g);
  if (a) {
    d.setOutline(a);
  }
  return d;
};
gisportal.buildPictureFillSymbol = function (h, e, d, f, i, a, g) {
  var b = gisportal.buildSimpleLineSymbol(f, i, a, g);
  var c = new esri.symbol.PictureFillSymbol(h, b, e, d);
  return c;
};
gisportal.buildSimpleLineSymbol = function (style, color, b, d) {
  var f = new esri.symbol.SimpleLineSymbol();
  f.setStyle(style);
  f.setWidth(d);
  var g = gisportal.buildColor(color, b);
  if (g) {
    f.setColor(g);
  }
  return f;
};
gisportal.buildColor = function (color, alpha) {
  var clr = null;
  if (color) {
    clr = new esri.Color(color);
    if (alpha) {
      clr.a = alpha;
    }
  }
  return clr;
};
gisportal.buildFont = function (size, family, bold) {
  var font = new esri.symbol.Font();
  if (size) {
    font.setSize(size);
  }
  if (family) {
    font.setFamily(family);
  }
  if (bold) {
    font.setWeight(esri.symbol.Font.WEIGHT_BOLD);
  }
  return font;
};
gisportal.hideInfoWindow = function () {
  if (gisportal.map) {
    gisportal.map.infoWindow.hide();
  }
};
gisportal.showProgressBar = function () {
  if (typeof gisportal.loadingDiv !== 'undefined') {
    esri.show(dojo.byId(gisportal.loadingDiv));
  }
};
gisportal.hideProgressBar = function () {
  if (typeof gisportal.loadingDiv !== 'undefined') {
    esri.hide(dojo.byId(gisportal.loadingDiv));
  }
};
gisportal.showCoordinates = function (evt, divId) {
  var pnt = esri.geometry.webMercatorToGeographic(evt.mapPoint);
  var lbl = '(' + pnt.x.toFixed(6) + ', ' + pnt.y.toFixed(6) + ')';
  if (gisportal.map) {
    lbl +=
      ' Escala 1:' +
      Number(gisportal.map.getScale().toFixed(0)).toLocaleString() +
      ' Zoom: ' +
      gisportal.map.getLevel();
  }
  dojo.byId(divId).innerHTML = lbl;
  esri.show(dojo.byId(divId));
};
gisportal.hideCoordinates = function (evt, panel) {
  esri.hide(dojo.byId(panel));
};
gisportal.setCursor = function (cursor) {
  if (gisportal.map) {
    gisportal.map.setMapCursor(cursor);
  }
};
gisportal.resetCursor = function () {
  gisportal.setCursor('default');
};
gisportal.resetNavigationToolbar = function () {
  gisportal.selectNavigationToolbarPan();
  gisportal.disableFullScreenMode();
  gisportal.hideProgressBar();
  if (gisportal.map) {
    gisportal.map.graphics.clear();
  }
};
gisportal.selectNavigationToolbarPan = function () {
  if (gisportal.navbar) {
    gisportal.navbar.deactivate();
  }
  gisportal.resetCursor();
};
gisportal.selectNavigationToolbarZoomIn = function () {
  if (gisportal.navbar) {
    gisportal.navbar.activate(esri.toolbars.Navigation.ZOOM_IN);
  }
  gisportal.setCursor('crosshair');
};
gisportal.selectNavigationToolbarZoomOut = function () {
  if (gisportal.navbar) {
    gisportal.navbar.activate(esri.toolbars.Navigation.ZOOM_OUT);
  }
  gisportal.setCursor('crosshair');
};
gisportal.selectNavigationToolbarZoomFullExtent = function () {
  gisportal.setMapCenter(
    gisportal.defaultLatitude,
    gisportal.defaultLongitude,
    gisportal.defaultZoom
  );
};
gisportal.selectNavigationToolbarPreviousExtent = function () {
  if (gisportal.navbar) {
    gisportal.navbar.zoomToPrevExtent();
  }
};
gisportal.selectNavigationToolbarNextExtent = function () {
  if (gisportal.navbar) {
    gisportal.navbar.zoomToNextExtent();
  }
};

gisportal.createOverview = function (idOverviewPanel) {
  if (gisportal.overview) {
    gisportal.overview.destroy();
  }
  var divOverviewPanel = dojo.create(
    'div',
    {
      style: {
        position: 'relative',
        overflow: 'hidden',
      },
    },
    idOverviewPanel
  );
  gisportal.overview = new esri.dijit.OverviewMap(
    {
      map: gisportal.map,
      expandFactor: 5,
      height: 150,
      opacity: 0.25,
      attachTo: 'bottom-right',
    } /* , b */
  );
  gisportal.overview.startup();
};

function goIrFomulario() {
  this.queryByGeom(this.globalGeometry, true);
}

function initToolbar() {
  gisportal.toolbar = new esri.toolbars.Draw(gisportal.map);
  dojo.connect(gisportal.toolbar, 'onDrawComplete', addGraphic);
  this.createGraphicLayers();

  var slider = new dijit.form.HorizontalSlider(
    {
      name: 'slider',
      minimum: 0,
      maximum: 100,
      discreteValues: 5,
      onChange: function (value) {
        currentLayer.setOpacity(value / 100);
      },
    },
    'slider'
  ).startup();
}

function layerAddResultEvent(e) {
  console.log(e);
}

function createGraphicLayers() {
  gisportal.pointSymbol = new esri.symbol.SimpleMarkerSymbol(
    esri.symbol.SimpleMarkerSymbol.STYLE_CIRCLE,
    10,
    new esri.symbol.SimpleLineSymbol(
      esri.symbol.SimpleLineSymbol.STYLE_SOLID,
      new esri.Color([255, 0, 0]),
      1
    ),
    new esri.Color([255, 0, 0, 1.0])
  );
  gisportal.polylineSymbol = new esri.symbol.SimpleLineSymbol(
    esri.symbol.SimpleLineSymbol.STYLE_DASH,
    new esri.Color([255, 0, 0]),
    1
  );
  gisportal.polygonSymbol = new esri.symbol.SimpleFillSymbol(
    esri.symbol.SimpleFillSymbol.STYLE_SOLID,
    new esri.symbol.SimpleLineSymbol(
      esri.symbol.SimpleLineSymbol.STYLE_DASHDOT,
      new esri.Color([255, 0, 0]),
      2
    ),
    new esri.Color([255, 255, 0, 0.0])
  );
  gisportal.pointGraphics = new esri.layers.GraphicsLayer({
    id: 'drawGraphics_point',
    title: 'Draw Graphics',
  });
  gisportal.pointRenderer = new esri.renderer.SimpleRenderer(
    gisportal.pointSymbol
  );
  gisportal.pointRenderer.label = 'User drawn points';
  gisportal.pointRenderer.description = 'User drawn points';
  gisportal.pointGraphics.setRenderer(gisportal.pointRenderer);
  gisportal.map.addLayer(gisportal.pointGraphics);
  gisportal.polylineGraphics = new esri.layers.GraphicsLayer({
    id: 'drawGraphics_line',
    title: 'Draw Graphics',
  });
  gisportal.polylineRenderer = new esri.renderer.SimpleRenderer(
    gisportal.polylineSymbol
  );
  gisportal.polylineRenderer.label = 'User drawn lines';
  gisportal.polylineRenderer.description = 'User drawn lines';
  gisportal.polylineGraphics.setRenderer(gisportal.polylineRenderer);
  gisportal.map.addLayer(gisportal.polylineGraphics);

  gisportal.polygonGraphics = new esri.layers.FeatureLayer(
    {
      layerDefinition: {
        geometryType: 'esriGeometryPolygon',
        fields: [
          {
            name: 'OBJECTID',
            type: 'esriFieldTypeOID',
            alias: 'OBJECTID',
            domain: null,
            editable: false,
            nullable: false,
          },
          {
            name: 'ren',
            type: 'esriFieldTypeInteger',
            alias: 'ren',
            domain: null,
            editable: true,
            nullable: false,
          },
        ],
      },
      featureSet: null,
    },
    {
      id: 'drawGraphics_poly',
      title: 'Draw Graphics',
      mode: esri.layers.FeatureLayer.MODE_SNAPSHOT,
    }
  );
  gisportal.polygonRenderer = new esri.renderer.UniqueValueRenderer(
    new esri.symbol.SimpleFillSymbol(),
    'ren',
    null,
    null,
    ', '
  );
  gisportal.polygonRenderer.addValue({
    value: 1,
    symbol: new esri.symbol.SimpleFillSymbol({
      color: [255, 170, 0, 255],
      outline: {
        color: [255, 170, 0, 255],
        width: 1,
        type: 'esriSLS',
        style: 'esriSLSSolid',
      },
      type: 'esriSFS',
      style: 'esriSFSForwardDiagonal',
    }),
    label: 'User drawn polygons',
    description: 'User drawn polygons',
  });
  gisportal.polygonGraphics.setRenderer(gisportal.polygonRenderer);
  gisportal.map.addLayer(gisportal.polygonGraphics);
}

function activeDraw(tool, dibujo, f) {
  if (activeText === true) {
    esri.bundle.toolbars.draw.addPoint = 'Haz click para agregar el texto';
    $('.modoDibujo').text('Modo de Dibujo: Crear Texto');
  } else {
    esri.bundle.toolbars.draw.addPoint = 'Haz click para agregar un punto';
    $('.modoDibujo').text('Modo de Dibujo: ' + dibujo);
  }
  $('.modoDibujo').show();
  gisportal.map.infoWindow = null;
  gisportal.toolbar.activate(tool);
  find = f;
}

function addGraphic(evt) {
  var symbol;
  gisportal.toolbar.deactivate();
  var graphic;
  switch (evt.geometry.type) {
    case 'point':
      if (activeText === true) {
        evento = evt;
        textEvent();
      } else {
        graphic = new esri.Graphic(evt.geometry);
        gisportal.pointGraphics.add(graphic);
      }
      break;
    case 'polyline':
      graphic = new esri.Graphic(evt.geometry);
      gisportal.polylineGraphics.add(graphic);
      break;
    case 'polygon':
      graphic = new esri.Graphic(evt.geometry, null, {
        ren: 1,
      });
      gisportal.polygonGraphics.add(graphic);
      break;
    default:
  }
  gisportal.map.infoWindow = gisportal.infoWindow;
  activeText = false;
  $('.modoDibujo').hide();
  if (find) {
    queryByGeom(evt.geometry);
  }
}

function clean() {
  this.clearDraw();
  document.getElementById('westForm:capasLocalesString').value = JSON.stringify(
    []
  );
  populateLayers();
  if (document.getElementById('upload-status')) {
    document.getElementById('upload-status').innerHTML = '';
  }
}

function clearDraw() {
  gisportal.pointGraphics.clear();
  gisportal.polylineGraphics.clear();
  gisportal.polygonGraphics.clear();
  gisportal.toolbar.deactivate();
  if (find) {
    clearGraphics();
  }
}

gisportal.refreshLegend = function (layer, idLegendPanel) {
  if (gisportal.legend) {
    gisportal.legend.refresh();
  } else {
    if (idLegendPanel) {
      gisportal.legend = new esri.dijit.Legend(
        {
          map: gisportal.map,
          respectCurrentMapScale: true,
        },
        idLegendPanel
      );
      gisportal.legend.startup();
    }
  }
};
gisportal.refreshMapSize = function () {
  if (gisportal.map) {
    gisportal.map.reposition();
    gisportal.map.resize();
  }
};
gisportal.refreshMapLayers = function () {
  if (gisportal.map) {
    var lyrs = gisportal.map.getLayersVisibleAtScale(gisportal.map.getScale());
    if (lyrs) {
      for (var i = 0; i < lyrs.length; i++) {
        var lyr = lyrs[i];
        if (lyr && lyr.loaded && lyr.visible && lyr.refresh) {
          lyr.refresh();
        }
      }
    }
  }
};
gisportal.setBackgroundLayer = function (backgroundLyr) {
  if (
    backgroundLyr &&
    gisportal.map &&
    gisportal.map.getBasemap() != backgroundLyr
  ) {
    gisportal.map.setBasemap(backgroundLyr);
    var lodCount = gisportal.getBackgroundLodCount(backgroundLyr) - 1;
    if (gisportal.map.getLevel() > lodCount) {
      gisportal.map.setLevel(lodCount);
    }
  }
};
gisportal.getBackgroundLodCount = function (basemap) {
  var lod = null;
  switch (basemap) {
    case 'streets':
    case 'satellite':
    case 'hybrid':
    case 'topo':
    case 'gray':
    case 'dark-gray':
      lod = 24;
      break;
    case 'osm':
    case 'streets-vector':
    case 'streets-navigation-vector':
    case 'streets-night-vector':
    case 'streets-relief-vector':
    case 'topo-vector':
    case 'gray-vector':
    case 'dark-gray-vector':
      lod = 20;
      break;
    case 'oceans':
    case 'national-geographic':
      lod = 17;
      break;
    case 'terrain':
      lod = 14;
      break;
  }
  return lod;
};
gisportal.setMapCenter = function (y, x, level) {
  if (gisportal.map && y && x && level) {
    var d = gisportal.map.extent.getCenter();
    if (
      d.getLatitude() != y ||
      d.getLongitude() != x ||
      gisportal.map.getLevel() != level
    ) {
      var pnt = esri.geometry.geographicToWebMercator(
        new esri.geometry.Point(x, y)
      );
      gisportal.map.centerAndZoom(pnt, level);
    }
  }
};
gisportal.hasGraphicsAndFeatureLayers = function () {
  var isGraphicLayer = false;
  var isFeatureLayer = false;
  if (gisportal.map) {
    var graphicsLayerIds = gisportal.map.graphicsLayerIds;
    for (var i = 0; i < graphicsLayerIds.length; i++) {
      var lyrId = graphicsLayerIds[i];
      var lyr = gisportal.map.getLayer(lyrId);
      if (lyr.visible) {
        if (lyr.declaredClass === 'esri.layers.GraphicsLayer') {
          isGraphicLayer = true;
        } else {
          if (lyr.declaredClass === 'esri.layers.FeatureLayer') {
            isFeatureLayer = true;
          }
        }
      }
    }
  }
  // return (isGraphicLayer && d)
  return isGraphicLayer;
};
gisportal.reorderGraphicsLayers = function () {
  if (gisportal.map && gisportal.hasGraphicsAndFeatureLayers()) {
    var idx =
      gisportal.map.layerIds.length + gisportal.map.graphicsLayerIds.length - 1;
    var slice = gisportal.map.graphicsLayerIds.slice(0);
    for (var i = 0; i < slice.length; i++) {
      var idLyr = slice[i];
      var lyr = gisportal.map.getLayer(idLyr);
      if (lyr.declaredClass === 'esri.layers.GraphicsLayer') {
        gisportal.map.reorderLayer(lyr, idx);
        idx++;
      }
    }
  }
};
gisportal.generateLodLevels = function (c) {
  var lodLevels = [];
  var scale = 591657527.591555;
  var resolution = 156543.03392800014;
  for (var i = 0; i < c; i++) {
    var lod = {};
    lod.level = i;
    lod.scale = scale;
    lod.resolution = resolution;
    lodLevels.push(d);
    scale /= 2;
    resolution /= 2;
  }
  return lodLevels;
};
gisportal.holdGraphic = function (graphicLayer) {
  gisportal.hideInfoWindow();
  if (graphicLayer.graphic.draggable) {
    gisportal.logGraphicCoordinates('clic grafico', graphicLayer);
    gisportal.map.disablePan();
    graphicLayer.graphic.dragging = true;
    graphicLayer.graphic.dragged = false;
  }
};
gisportal.dragGraphic = function (graphicLayer) {
  if (graphicLayer.graphic.draggable && graphicLayer.graphic.dragging) {
    gisportal.logGraphicCoordinates('Grafico arrastrado', graphicLayer);
    graphicLayer.graphic.geometry = graphicLayer.mapPoint;
    graphicLayer.graphic.draw();
    graphicLayer.graphic.dragged = true;
  }
};
gisportal.releaseGraphic = function (graphicLayer) {
  if (
    graphicLayer.graphic.draggable &&
    graphicLayer.graphic.dragging &&
    graphicLayer.graphic.dragged
  ) {
    gisportal.logGraphicCoordinates('grafico liberado', graphicLayer);
    var lyr = graphicLayer.graphic.getLayer();
    var graphic = new esri.Graphic(graphicLayer.graphic.toJson());
    graphic.uuid = graphicLayer.graphic.uuid;
    graphic.type = graphicLayer.graphic.type;
    graphic.draggable = graphicLayer.graphic.draggable;
    graphic.source = {};
    graphic.source.serviceId = lyr.id;
    lyr.remove(graphicLayer.graphic);
    lyr.add(graphic);
    gisportal.generateJsfMapGraphicDragEvent(graphic);
  }
  gisportal.map.enablePan();
  graphicLayer.graphic.dragging = false;
  graphicLayer.graphic.dragged = false;
};
gisportal.logGraphicCoordinates = function (e, b) {
  var a = esri.geometry.webMercatorToGeographic(b.mapPoint);
};
gisportal.exportPopupGraphicToHtml = function (evt) {
  var h = gisportal.map.infoWindow.getSelectedFeature();
  var html = '<html><body>';
  html += '<style>';
  html += 'table { border-collapse: collapse; }';
  html += 'table th { background-color: #B0C4DE; }';
  html += 'table td { padding: 2px; }';
  html += 'table tr:nth-child(odd) td { background-color: #FFFFFF; }';
  html += 'table tr:nth-child(even) td { background-color: #DFE7F2; }';
  html += '</style>';
  html += "<table border='1'>";
  html += "<tr><th colspan='4'>" + h.getLayer().name + '</th></tr>';
  html += '<tr><th>Name</th><th>Alias</th><th>Value</th><th>Type</th></tr>';
  for (name in h.attributes) {
    var attrib = h.attributes[name];
    var alias = '';
    var type = '';
    var field = gisportal.getGraphicLayerField(name, h);
    if (field) {
      alias = field.alias;
      type = field.type;
    }
    html +=
      '<tr><td>' +
      name +
      '</td><td>' +
      alias +
      '</td><td>' +
      attrib +
      '</td><td>' +
      type +
      '</td></tr>';
  }
  html += '</table>';
  html += '</body></html>';
  var win = window.open();
  win.document.write(html);
  win.document.close();
};
gisportal.addPopupFooterLink = function (innerHTML, exportFunction) {
  var a = dojo.create(
    'a',
    {
      class: 'action',
      innerHTML: innerHTML,
      href: 'javascript:void(0);',
    },
    dojo.query('.actionList', gisportal.map.infoWindow.domNode)[0]
  );
  dojo.connect(a, 'onclick', exportFunction);
};
gisportal.getCurrentPosition = function () {
  if (navigator.geolocation) {
    gisportal.showProgressBar();
    navigator.geolocation.getCurrentPosition(
      gisportal.processMapGeoLocationEvent,
      gisportal.processGeoLocationError,
      {
        enableHighAccuracy: true,
        timeout: 60000,
        maximumAge: 0,
      }
    );
  } else {
    alert('Su navegador no soporta geo-localizacion.');
  }
};
gisportal.processMapGeoLocationEvent = function (position) {
  var pnt = esri.geometry.geographicToWebMercator(
    new esri.geometry.Point(position.coords.longitude, position.coords.latitude)
  );
  var pms = new esri.symbol.PictureMarkerSymbol(
    gisportal.jsapi +
      '/esri/dijit/GeocodeMatch/images/EsriYellowPinCircle26.png',
    26,
    26
  );
  var attrs = {
    Timestamp: gisportal.getFormattedDate(position.timestamp),
    Latitude: position.coords.latitude.toFixed(6),
    Longitude: position.coords.longitude.toFixed(6),
    Heading: gisportal.getNumericValue(position.coords.heading, 0),
    Speed: gisportal.getNumericValue(position.coords.speed, 0),
    Altitude: gisportal.getNumericValue(position.coords.altitude, 0),
  };
  var infoTpl = new esri.InfoTemplate(
    'Geolocation',
    gisportal.buildGenericInfoTemplateContent
  );
  var g = new esri.Graphic(pnt, pms, attrs, infoTpl);
  gisportal.map.graphics.add(g);
  var level = gisportal.getBackgroundLodCount(gisportal.map.getBasemap()) - 1;
  gisportal.setMapCenter(
    position.coords.latitude,
    position.coords.longitude,
    level
  );
  gisportal.generateJsfMapGeoLocationEvent(position, level);
};
gisportal.startWatchCurrentPosition = function (
  e,
  enableHighAccuracy,
  timeout,
  maximumAge
) {
  if (navigator.geolocation) {
    gisportal.stopWatchCurrentPosition();
    var b = {
      enableHighAccuracy: enableHighAccuracy,
      timeout: timeout,
      maximumAge: maximumAge,
    };
    if (e) {
      gisportal.geolocationWatchId = navigator.geolocation.watchPosition(
        gisportal.processGisGeoLocationEvent,
        gisportal.processGeoLocationError,
        b
      );
    } else {
      navigator.geolocation.getCurrentPosition(
        gisportal.processGisGeoLocationEvent,
        gisportal.processGeoLocationError,
        b
      );
    }
  } else {
    alert('Su navegador no soporta geo-localizacion.');
  }
};
gisportal.stopWatchCurrentPosition = function () {
  if (navigator.geolocation) {
    if (gisportal.geolocationWatchId != null) {
      navigator.geolocation.clearWatch(gisportal.geolocationWatchId);
      gisportal.geolocationWatchId = null;
    }
  }
};
gisportal.processGisGeoLocationEvent = function (a) {
  gisportal.generateJsfGisGeoLocationEvent(a);
};
gisportal.processGeoLocationError = function (err) {
  var msg;
  switch (err.code) {
    case err.PERMISSION_DENIED:
      msg = 'El usuario denego el servicio de geo-localizacion.';
      break;
    case err.POSITION_UNAVAILABLE:
      msg = 'Posicion no disponible.';
      break;
    case err.TIMEOUT:
      msg = 'Tiempo de espera excedido para obtener la localizacion.';
      break;
    default:
      msg = 'Error de geo-localizacion desconocido.';
      break;
  }
  alert(msg);
  gisportal.hideProgressBar();
};
gisportal.zoomToExtent = function (urlKey, extent) {
  if (urlKey && extent) {
    gisportal.showProgressBar();
    var query = new esri.tasks.Query();
    query.outSpatialReference = gisportal.map.spatialReference;
    query.where = extent;
    query.outFields = [];
    query.returnGeometry = true;
    var queryTask = new esri.tasks.QueryTask(urlKey);
    queryTask.execute(
      query,
      gisportal.zoomToFeatures,
      gisportal.genericErrorHandler
    );
  }
};
gisportal.zoomToFeatures = function (result) {
  gisportal.showProgressBar();
  var features = result.features;
  if (features && features.length > 0) {
    if (features.length == 1 && features[0].geometry.type == 'point') {
      var level =
        gisportal.getBackgroundLodCount(gisportal.map.getBasemap()) - 1;
      gisportal.map.centerAndZoom(features[0].geometry, level);
    } else {
      var extent = esri.graphicsExtent(features);
      if (extent) {
        gisportal.map.setExtent(extent, true);
      }
    }
  } else {
  }
  gisportal.hideInfoWindow();
  gisportal.hideProgressBar();
};
gisportal.genericErrorHandler = function (err) {
  console.error(err);
  gisportal.hideProgressBar();
};
gisportal.toggleFullScreenMode = function () {
  if (gisportal.isFullScreenMode()) {
    gisportal.disableFullScreenMode();
  } else {
    gisportal.enableFullScreenMode();
  }
};
gisportal.isFullScreenMode = function () {
  var isFullScreenMode =
    document.fullscreen ||
    document.mozFullScreen ||
    document.webkitIsFullScreen ||
    document.msFullscreenElement
      ? true
      : false;
  return isFullScreenMode;
};
gisportal.enableFullScreenMode = function () {
  if (gisportal.map) {
    var elId = document.getElementById(gisportal.map.id);
    if (elId) {
      if (elId.requestFullscreen) {
        elId.requestFullscreen();
      } else {
        if (elId.mozRequestFullScreen) {
          elId.mozRequestFullScreen();
        } else {
          if (elId.webkitRequestFullscreen) {
            elId.webkitRequestFullscreen();
          } else {
            if (elId.msRequestFullscreen) {
              elId.msRequestFullscreen();
            }
          }
        }
      }
    }
  }
};
gisportal.disableFullScreenMode = function () {
  if (document.exitFullscreen) {
    document.exitFullscreen();
  } else {
    if (document.mozCancelFullScreen) {
      document.mozCancelFullScreen();
    } else {
      if (document.webkitExitFullscreen) {
        document.webkitExitFullscreen();
      } else {
        if (document.msExitFullscreen) {
          document.msExitFullscreen();
        }
      }
    }
  }
};
gisportal.toggleMeasurementTool = function () {
  gisportal.hideInfoWindow();
  if (gisportal.measurement) {
    gisportal.resetMeasurementSubTool();
    gisportal.measurement.clearResult();
    if (gisportal.measurement.selected) {
      gisportal.measurement.selected = false;
    } else {
      gisportal.hideAllPanels();
      gisportal.measurement.selected = true;
      gisportal.measurement.show();
    }
  }
};
gisportal.toggleBaseMapsGallery = function () {
  gisportal.hideInfoWindow();
  if (gisportal.basemapGallery) {
    if (gisportal.basemapGallery.selected) {
      gisportal.basemapGallery.selected = false;
      gisportal.basemapGallery.domNode.style.display = 'none';
    } else {
      gisportal.hideAllPanels();
      gisportal.basemapGallery.selected = true;
      gisportal.basemapGallery.domNode.style.display = 'block';
    }
  }
};
gisportal.hideAllPanels = function () {
  gisportal.measurement.selected = false;
  gisportal.basemapGallery.selected = false;
  gisportal.basemapGallery.domNode.style.display = 'none';
};
gisportal.resetMeasurementTool = function () {
  if (gisportal.measurement) {
    gisportal.resetMeasurementSubTool();
    gisportal.measurement.clearResult();
    gisportal.measurement.selected = false;
  }
};
gisportal.resetMeasurementSubTool = function () {
  if (gisportal.measurement && gisportal.measurement.getTool()) {
    gisportal.measurement.setTool(
      gisportal.measurement.getTool().toolName,
      false
    );
  }
};
gisportal.isMeasurementToolSelected = function () {
  return gisportal.measurement && gisportal.measurement.selected;
};
gisportal.isLink = function (a) {
  return (
    a.startsWith('http://') ||
    a.startsWith('https://') ||
    a.startsWith('ftp://') ||
    a.startsWith('news://') ||
    a.startsWith('irc://') ||
    a.startsWith('data://') ||
    a.startsWith('mailto:')
  );
};
gisportal.isImage = function (a) {
  return (
    gisportal.isLink(a) &&
    (a.endsWith('.png') ||
      a.endsWith('.jpg') ||
      a.endsWith('.jpeg') ||
      a.endsWith('.gif') ||
      a.endsWith('.bmp'))
  );
};
gisportal.isNumeric = function (a) {
  return a && isFinite(a) && !isNaN(a);
};
gisportal.getNumericValue = function (a, b) {
  return gisportal.isNumeric(a) ? a : b;
};
gisportal.getFormattedDate = function (b) {
  var c = b;
  try {
    var a = new Date(b);
    c = a.toISOString();
  } catch (d) {}
  return c;
};
String.prototype.startsWith = function (a) {
  return a && this.trim().toLowerCase().indexOf(a.trim().toLowerCase()) == 0;
};
String.prototype.endsWith = function (a) {
  return (
    a &&
    this.trim()
      .toLowerCase()
      .indexOf(a.trim().toLowerCase(), this.trim().length - a.trim().length) !==
      -1
  );
};
Array.prototype.contains = function (b) {
  for (var a = 0; a < this.length; a++) {
    if (this[a] == b) {
      return true;
    }
  }
  return false;
};
Array.prototype.addUnique = function (a) {
  if (!this.contains(a)) {
    this.push(a);
  }
};
Array.prototype.remove = function (b) {
  if (this.contains(b)) {
    for (var a = 0; a < this.length; a++) {
      if (this[a] == b) {
        this.splice(a, 1);
      }
    }
  }
};

var previousMarker = '';
$('.markersGrid').on('click', 'div', function () {
  var markerId = $(this).attr('id');
  var idM = String(markerId);
  var compareId = idM.substring(0, 10);
  if (compareId === 'dojoUnique') {
    markerId = '#' + markerId;
    $(previousMarker).css('background', '#FFFFFF');
    $(markerId).css('background', '#fff19b');
    previousMarker = markerId;
  }
});

var identifiedCap;
$('#idSelection').on('change', function () {
  identifiedCap = this.value;
  loadIdentify = false;
  for (x = 0; x < loadCapIdent.length; x++) {
    gisportal.loadInfoTemplates(loadCapIdent[x]);
  }
});

$('.subirmapa').click(function () {
  insertarmapa();
});

function sortFileUpload() {
  if (PF('upload2') && !this.loadfileuploadChange) {
    this.loadfileuploadChange = true;
    PF('upload2').jq.change(
      dojo._base.lang.hitch(this, function () {
        this.shapeFile = PF('upload2').files[PF('upload2').files.length - 1];
      })
    );
  }
}

function insertarmapa() {
  // var formElement = document.getElementById("uploadForm");
  console.log('entro a cargar yeah');
}

$('.gisportalmeasurement').click(function () {
  var stateInf = $('span.esriToggleButton').css('visibility');
  if (stateInf === 'hidden') {
    gisportal.map.infoWindow = gisportal.infoWindow;
  } else {
    gisportal.map.infoWindow = null;
  }
});

$('.tableAttribute').click(function () {
  var stateAtt = $('.attrVisible').css('display');
  if (stateAtt === 'none') {
    $('.attrVisible').show();
  } else {
    $('.attrVisible').hide();
  }
});

$('.ui-fileupload-upload').click(function () {
  $('.upload').click();
});

function ResetPanel() {
  $('.capas').click();
}

function menu1() {
  $('.submenu1').click();
}

function menu2() {
  $('.submenu2').click();
}

function layer1() {
  $('.layer1').click();
}

function layer2() {
  $('.layer2').click();
}

function menuItemMD1() {
  $('.menuItemMD1').click();
}

function menuItemMU1() {
  $('.menuItemMU1').click();
}

function menuItemMU2() {
  $('.menuItemMU2').click();
}

function menuItemMD2() {
  $('.menuItemMD2').click();
}

function menuItemMV1() {
  $('.menuItemMV1').click();
}

function menuItemMV2() {
  $('.menuItemMV2').click();
}

function CleanFUp() {
  $('.CleanFUp').click();
}

function exportExcel() {
  $('.exportExcel').click();
}

var drawText;
var activeText = false;
var clickText;
function createText() {
  activeText = true;
  esri.bundle.toolbars.draw.addPoint = 'Haz click para agregar un texto';
  clickText = document.getElementById('centerForm');
  clickText.addEventListener('click', textEvent2);
  activeDraw('point', 'Texto');
}

var coordx, coordy;
function textEvent2(e) {
  coordy = e.pageY;
  coordx = e.pageX;
}

function writeText() {
  drawText = $('#drawText').val();
  $('#drawText').remove();
  $('#buttonText').remove();
  clickText.removeEventListener('click', textEvent2);
  var font = new esri.symbol.Font(
    '20px',
    esri.symbol.Font.STYLE_NORMAL,
    esri.symbol.Font.VARIANT_NORMAL,
    esri.symbol.Font.WEIGHT_BOLDER,
    'Arial'
  );
  var textSymbol = new esri.symbol.TextSymbol(
    drawText,
    font,
    new esri.Color([255, 0, 0])
  );
  var graphic = new esri.Graphic(evento.geometry, textSymbol);
  gisportal.pointGraphics.add(graphic);
  esri.bundle.toolbars.draw.addPoint = 'Haz click para agregar un punto';
}

function textEvent() {
  gisportal.map.infoWindow = null;
  $('#drawText').remove();
  $('#buttonText').remove();
  var txt = '<input type="text" id="drawText" onclick=" "/>';
  var btn =
    '<input type="button" id="buttonText" value="Crear" onclick="writeText();" />';
  // var campo = '<p id="mover" contenteditable="true">texto</p>';
  $('#centerForm').append(btn);
  $('#centerForm').append(txt);
  $('#drawText').focus();
  $('#drawText').offset({
    top: coordy,
    left: coordx,
  });
  $('#buttonText').offset({
    top: coordy,
    left: coordx - 50,
  });
  $('#drawText').keypress(function (e) {
    var keycode = e.keyCode ? e.keyCode : e.which;
    if (keycode == '13') {
      writeText();
    }
  });
}

function identify() {
  $('[id*=legend_drawGraphics_poly]').remove();
  $('[id*=legend_layer0]').remove();
  var selectedOp = $('#idSelection option:selected').val();
  var stateLeg = $('[id*=conventions_content]').css('display');
  if (stateLeg === 'none') {
    $('[id*=conventions_content]').show();
    stateLeg = 'blockAct';
  }
  var idSelection = document.getElementById('idSelection');
  $(idSelection).empty();
  var option = document.createElement('option');
  option.text = 'Todas las capas visibles';
  idSelection.add(option);
  $('div.esriLegendService').each(function () {
    var markerId = $(this).attr('id');
    var idM = String(markerId);
    var compareId = idM.substring(16, 28);
    if (compareId !== 'drawGraphics') {
      var identifyL = document.getElementById(idM).innerText;
      identifyL = identifyL.split(/\r|\n/);
      var layers = identifyL[1];
      for (x = 2; x < identifyL.length; x++) {
        if (identifyL[x] === '\t') {
          var idSelection = document.getElementById('idSelection');
          var option = document.createElement('option');
          option.value = layers;
          layers = identifyL[0] + ' / ' + layers;
          option.text = layers;
          idSelection.add(option);
        } else {
          layers = identifyL[x];
        }
      }
    }
  });
  if (stateLeg === 'blockAct') {
    $('[id*=conventions_content]').hide();
  }
  $("#idSelection option[value='" + selectedOp + "']").attr('selected', true);
  identifiedCap = $('#idSelection option:selected').val();
  if (selectedOp !== identifiedCap) {
    $('#idSelection').trigger('change');
  }
}

function moveLayer() {
  $('.panelGServices2class').after($('.panelGServicesclass'));
  gisportal.map.reorderLayer(loadCapIdent[0], 2);
  gisportal.map.reorderLayer(loadCapIdent[1], 1);
}

function moveLayer2() {
  $('.panelGServices2class').before($('.panelGServicesclass'));
  gisportal.map.reorderLayer(loadCapIdent[0], 1);
  gisportal.map.reorderLayer(loadCapIdent[1], 2);
}

function queryOption(el) {
  var seleccion = el.value;
  if (seleccion === 'Empresa') {
    $('.idSelectionB').hide();
    $('.idSelectionA').show();
  } else if (seleccion === 'Persona') {
    $('.idSelectionA').hide();
    $('.idSelectionB').show();
  }
}

function desactiveText() {
  activeText = false;
}

function cleanTxt() {
  $('input[type="text"]').val('');
  $('.table').hide();
}

function cleanAdmon() {
  $('input[type="text"]').val('');
}

function initUrlServices() {
  urlGisServiceU = document.getElementById('westForm:urlGisServiceU').value;
  urlGisServiceR = document.getElementById('westForm:urlGisServiceR').value;
}

function clearGraphics() {
  gisportal.map.graphics.clear();
  if (gisportal.graphicsLayer) {
    gisportal.graphicsLayer.clear();
  }
}

function generateFeatureCollection(fileName) {
  if (shapeFileLayers.length > 0) {
    for (var index = 0; index < shapeFileLayers.length; index++) {
      gisportal.map.removeLayer(shapeFileLayers[index]);
    }
  }

  var name = fileName.split('.');
  // Chrome and IE add c:\fakepath to the value - we need to remove it
  // See this link for more info: http://davidwalsh.name/fakepath
  name = name[0].replace('c:\\fakepath\\', '');

  document.getElementById('upload-status').innerHTML =
    '<b>Espere un momento.. </b><br />';

  // Define the input params for generate see the rest doc for details
  // http://www.arcgis.com/apidocs/rest/index.html?generate.html
  var params = {
    name: name,
    targetSR: gisportal.map.spatialReference,
    maxRecordCount: 1000,
    enforceInputFileSizeLimit: true,
    enforceOutputJsonSizeLimit: true,
  };

  // generalize features for display Here we generalize at 1:40,000 which is
  // approx 10 meters
  // This should work well when using web mercator.
  var extent = esri.geometry.scaleUtils.getExtentForScale(gisportal.map, 40000);
  var resolution = extent.getWidth() / gisportal.map.width;
  params.generalize = true;
  params.maxAllowableOffset = resolution;
  params.reducePrecision = true;
  params.numberOfDigitsAfterDecimal = 0;

  var myContent = {
    filetype: 'shapefile',
    publishParameters: JSON.stringify(params),
    f: 'json',
  };

  var formData = new FormData();
  formData.append('file', this.shapeFile);

  // use the rest generate operation to generate a feature collection from the
  // zipped shapefile
  esri.request({
    url: 'https://www.arcgis.com/sharing/rest/content/features/generate',
    content: myContent,
    form: formData,
    handleAs: 'json',
    load: dojo._base.lang.hitch(this, function (response) {
      if (response.error) {
        errorHandler(response.error);
        return;
      }
      addShapefileToMap(response.featureCollection);
    }),
    error: dojo._base.lang.hitch(this, errorHandler),
  });
}

function errorHandler(error) {
  document.getElementById('upload-status').innerHTML =
    "<p style='color:red'>Archivo Shapefile Inválido</p>";
}

function addShapefileToMap(featureCollection) {
  var fullExtent;

  var index = 0;
  var shapeFileLayersArray = [];
  dojo._base.array.forEach(featureCollection.layers, function (layer) {
    var infoTemplate = new esri.InfoTemplate('Detalles', '${*}');
    var featureLayer = new esri.layers.FeatureLayer(layer, {
      id: 'lyr' + index,
      infoTemplate: infoTemplate,
    });
    // associate the feature with the popup on click to enable highlight and
    // zoom to
    featureLayer.on('click', function (event) {
      gisportal.map.infoWindow.setFeatures([event.graphic]);
    });
    // change default symbol if desired. Comment this out and the layer will
    // draw with the default symbology
    changeRenderer(featureLayer);
    fullExtent = fullExtent
      ? fullExtent.union(featureLayer.fullExtent)
      : featureLayer.fullExtent;
    shapeFileLayers.push(featureLayer);
    shapeFileLayersArray.push({
      id: 'lyr' + index,
      layerName: layer.layerDefinition.name,
    });
    index++;
  });
  gisportal.map.addLayers(shapeFileLayers);
  gisportal.map.setExtent(fullExtent.expand(1.25), true);
  document.getElementById('upload-status').innerHTML =
    '<b>Se ha cargado existosamente<b/><br />';
  document.getElementById('westForm:capasLocalesString').value = JSON.stringify(
    shapeFileLayersArray
  );
  populateLayers();
}

function changeRenderer(layer) {
  var symbol = null;
  switch (layer.geometryType) {
    case 'esriGeometryPoint':
      symbol = new esri.symbol.PictureMarkerSymbol({
        angle: 0,
        xoffset: 0,
        yoffset: 0,
        type: 'esriPMS',
        url:
          'https://static.arcgis.com/images/Symbols/Shapes/BluePin1LargeB.png',
        contentType: 'image/png',
        width: 20,
        height: 20,
      });
      break;
    case 'esriGeometryPolygon':
      symbol = new esri.symbol.SimpleFillSymbol(
        esri.symbol.SimpleFillSymbol.STYLE_SOLID,
        new esri.symbol.SimpleLineSymbol(
          esri.symbol.SimpleLineSymbol.STYLE_SOLID,
          new dojo.Color([112, 112, 112]),
          1
        ),
        new dojo.Color([136, 136, 136, 1])
      );
      break;
  }
  if (symbol) {
    layer.setRenderer(new esri.renderer.SimpleRenderer(symbol));
  }
}

function queryCodes(gisServiceU, gisServiceR, codes) {
  initUrlServices();
  clearGraphics();
  sendJSF = false;
  var arr = JSON.parse(codes);

  var queryTaskU = new esri.tasks.QueryTask(urlGisServiceR);
  var queryU = new esri.tasks.Query();
  queryU.where = 'codigo IN (' + "'" + arr.join("', '") + "'" + ')';
  queryU.outSpatialReference = gisportal.map.spatialReference;
  queryU.returnGeometry = true;
  var execU = queryTaskU.execute(queryU);

  var queryTaskR = new esri.tasks.QueryTask(urlGisServiceR);
  var queryR = new esri.tasks.Query();
  queryR.where = 'codigo IN (' + "'" + arr.join("', '") + "'" + ')';
  queryR.outSpatialReference = gisportal.map.spatialReference;
  queryR.returnGeometry = true;
  var execR = queryTaskR.execute(queryR);

  var promises = dojo.promise.all([execU, execR]);
  promises.then(handleQueryResults);
}

function queryByArea(
  urlService,
  type,
  urlServiceMunicipio,
  codMunicipio,
  minArea,
  maxArea
) {
  clearGraphics();
  var minAreaC = minArea,
    maxAreaC = maxArea;
  if (type === 'R') {
    minAreaC = minArea * 10000;
    maxAreaC = maxArea * 10000;
  }
  gisportal.queryTask = new esri.tasks.QueryTask(urlServiceMunicipio);
  gisportal.query = new esri.tasks.Query();
  gisportal.query.where = "NUM_DANE = '" + codMunicipio + "'";
  // gisportal.query.outSpatialReference = gisportal.map.spatialReference;
  gisportal.query.returnGeometry = true;
  gisportal.queryTask.execute(gisportal.query, function (fset) {
    var features = fset.features;
    if (features.length > 0) {
      var queryTaskU = new esri.tasks.QueryTask(urlService);
      var queryU = new esri.tasks.Query();
      queryU.geometry = features[0].geometry;
      queryU.where = 'SHAPE.AREA BETWEEN ' + minAreaC + ' AND ' + maxAreaC;
      queryU.outSpatialReference = gisportal.map.spatialReference;
      queryU.returnGeometry = true;
      queryTaskU.execute(queryU, function (fset) {
        var features = fset.features;
        var codes = [];
        if (features.length > 0) {
          for (var index = 0; index < features.length; index++) {
            codes.push(features[index].attributes.CODIGO);
            showFeature(features[index]);
          }
          var extent = esri.graphicsExtent(gisportal.map.graphics.graphics);
          gisportal.map.setExtent(extent, true);
        }

        document.getElementById(
          'westForm:codeListString'
        ).value = JSON.stringify(codes);
        populateCodes();
      });
    }
  });
}

function queryByGeom(geom, noDraw) {
  this.noDraw = noDraw;
  initUrlServices();
  clearGraphics();
  sendJSF = true;

  var queryTaskU = new esri.tasks.QueryTask(urlGisServiceU);
  var queryU = new esri.tasks.Query();
  queryU.where = '1 = 1';
  queryU.geometry = geom;
  queryU.outSpatialReference = gisportal.map.spatialReference;
  queryU.returnGeometry = true;
  var execU = queryTaskU.execute(queryU);

  var queryTaskR = new esri.tasks.QueryTask(urlGisServiceR);
  var queryR = new esri.tasks.Query();
  queryR.where = '1 = 1';
  queryR.geometry = geom;
  queryR.outSpatialReference = gisportal.map.spatialReference;
  queryR.returnGeometry = true;
  var execR = queryTaskR.execute(queryR);

  var promises = dojo.promise.all([execU, execR]);
  promises.then(handleQueryResults);
}

function zoomToCode(code) {
  if (!gisportal.graphicsLayer) {
    gisportal.graphicsLayer = new esri.layers.GraphicsLayer({
      id: 'highLight_graphics',
      title: 'Draw HighLight',
    });
    gisportal.map.addLayer(gisportal.graphicsLayer);
  } else {
    gisportal.graphicsLayer.clear();
  }

  var filter = gisportal.map.graphics.graphics.filter(function (graphic) {
    return graphic.attributes.CODIGO === code;
  });

  if (filter.length > 0) {
    var graphic = filter[0];
    var stateExtent = graphic.geometry.getExtent();
    gisportal.map.setExtent(stateExtent);
    showHighLight(graphic);
  }
}

function zoomToCodes(codes) {
  if (!gisportal.graphicsLayer) {
    gisportal.graphicsLayer = new esri.layers.GraphicsLayer({
      id: 'highLight_graphics',
      title: 'Draw HighLight',
    });
    gisportal.map.addLayer(gisportal.graphicsLayer);
  } else {
    gisportal.graphicsLayer.clear();
  }

  var arr = JSON.parse(codes);
  for (var index = 0; index < arr.length; index++) {
    var filter = gisportal.map.graphics.graphics.filter(function (graphic) {
      return graphic.attributes.CODIGO === arr[index];
    });

    if (filter.length > 0) {
      var graphic = filter[0];
      showHighLight(graphic);
    }
  }

  if (gisportal.map.graphics.graphics.length > 0) {
    var extent = esri.graphicsExtent(gisportal.map.graphics.graphics);
    gisportal.map.setExtent(extent, true);
  }
}

function handleQueryResults(results) {
  var hasFeatures = false;
  var codes = [];

  if (results[0].hasOwnProperty('features') && results[0].features.length > 0) {
    var features = results[0].features;
    hasFeatures = true;
    for (var index = 0; index < features.length; index++) {
      codes.push(features[index].attributes.CODIGO);
      if (!this.noDraw) {
        showFeature(features[index]);
      }
    }
  }

  if (results[1].hasOwnProperty('features') && results[1].features.length > 0) {
    var features = results[1].features;
    hasFeatures = true;
    for (var index = 0; index < features.length; index++) {
      codes.push(features[index].attributes.CODIGO);
      if (!this.noDraw) {
        showFeature(features[index]);
      }
    }
  }

  if (this.noDraw && hasFeatures) {
    document.getElementById('westForm:codeListString').value = codes[0];
    populateCode();
    return;
  }

  if (hasFeatures) {
    var extent = esri.graphicsExtent(gisportal.map.graphics.graphics);
    gisportal.map.setExtent(extent, true);
    if (handleQueryResults) {
      document.getElementById('westForm:codeListString').value = JSON.stringify(
        codes
      );
      populateCodes();
    }
  }
}

function clearCSVPoints() {
  if (shapeFileLayers.length > 0) {
    for (var index = 0; index < shapeFileLayers.length; index++) {
      gisportal.map.removeLayer(shapeFileLayers[index]);
    }
  }
  if (gisportal.csvGraphics) {
    gisportal.csvGraphics.clear();
  }
}

function addCSVPoints(points) {
  if (!gisportal.csvGraphics) {
    gisportal.csvGraphics = new esri.layers.GraphicsLayer({
      id: 'csv_point',
      title: 'Draw CSV Graphics',
    });
    gisportal.map.addLayer(gisportal.csvGraphics);
  }

  var hexa = getRandomColor();
  for (var point of points) {
    var geom = esri.geometry.geographicToWebMercator(
      new esri.geometry.Point(point.longitud, point.latitud)
    );
    var symbol = new esri.symbol.SimpleMarkerSymbol(
      esri.symbol.SimpleMarkerSymbol.STYLE_CIRCLE,
      10,
      new esri.symbol.SimpleLineSymbol(
        esri.symbol.SimpleLineSymbol.STYLE_SOLID,
        new esri.Color(hexa),
        1
      ),
      new esri.Color(hexa)
    );
    var attrs = {
      consecutivo: point.consecutivo,
    };
    var graphic = new esri.Graphic(geom, symbol, attrs);
    gisportal.csvGraphics.add(graphic);
  }
}

function getRandomColor() {
  var letters = '0123456789ABCDEF';
  var color = '#';
  for (var i = 0; i < 6; i++) {
    color += letters[Math.floor(Math.random() * 16)];
  }
  return color;
}

function showFeature(feature) {
  var symbol8 = new esri.symbol.SimpleFillSymbol(
    esri.symbol.SimpleFillSymbol.STYLE_SOLID,
    new esri.symbol.SimpleLineSymbol(
      esri.symbol.SimpleLineSymbol.STYLE_SOLID,
      new dojo.Color([255, 0, 0]),
      2
    ),
    new dojo.Color([255, 255, 0, 0.5])
  );
  feature.setSymbol(symbol8);
  gisportal.map.graphics.add(feature);
}

function showHighLight(feature) {
  var symbol = new esri.symbol.SimpleFillSymbol(
    esri.symbol.SimpleFillSymbol.STYLE_SOLID,
    new esri.symbol.SimpleLineSymbol(
      esri.symbol.SimpleLineSymbol.STYLE_SOLID,
      new dojo.Color([255, 255, 0]),
      2
    ),
    new dojo.Color([255, 0, 0, 0.5])
  );
  var graphic = new esri.Graphic(feature.geometry, symbol);
  gisportal.graphicsLayer.add(graphic);
}

function queryByCapasLocales(layerId) {
  if (layerId && gisportal.map.graphicsLayerIds.length > 0) {
    for (var j = 0, jl = gisportal.map.graphicsLayerIds.length; j < jl; j++) {
      var currentLayer = gisportal.map.getLayer(
        gisportal.map.graphicsLayerIds[j]
      );
      if (currentLayer.id === layerId) {
        if (currentLayer._graphicsVal.length > 0) {
          var geom = currentLayer._graphicsVal[0].geometry;
          queryByGeom(geom);
        }
        break;
      }
    }
  }
}

function mapai() {
  var url =
    'http://sampleserver6.arcgisonline.com/arcgis/rest/services/Utilities/PrintingTools/GPServer/Export%20Web%20Map%20Task';
  gisportal.print = new esri.tasks.PrintTask(url);
  var params = new esri.tasks.PrintParameters();
  params.map = gisportal.map;
  gisportal.print.execute(params, function (result) {
    window.open(result.url);
  });
}

function desplegarModal() {
  PF('dlg').show();
}

function print(
  title,
  format,
  layout,
  scalebarUnit,
  width,
  height,
  dpi,
  legend
) {
  initUrlServices();

  if (gisportal.map.graphics.graphics.length > 5) {
    PF('btnExportar').enable();
    $('#printResultsNode').append(
      '<div class="printResult" id="resultNode"">' +
        '<table class="printResultTable"><tr><td id="nameNode' +
        this.indexUrlPrints +
        '" style="font-weight:bold;color:red;">Debe exportar los datos en formato PDF, tiene más de 5 matrículas inmobiliarias</div></td></tr></table></div>'
    );
    this.indexUrlPrints = indexUrlPrints + 1;
    return;
  }

  var template = new esri.tasks.PrintTemplate();
  template.format = format;
  template.layout = layout;
  template.preserveScale = true;
  template.outScale = null;
  template.label = title;
  template.exportOptions = {
    width: width,
    height: height,
    dpi: dpi,
  };

  var legends = [];
  // incluir leyenda
  for (var i = 1; i < gisportal.map.layerIds.length; ++i) {
    var layer = gisportal.map.getLayer(gisportal.map.layerIds[i]);
    if (
      layer.visible &&
      layer.visibleLayers &&
      layer.visibleLayers.length > 0
    ) {
      var legendLayer = new esri.tasks.LegendLayer();
      legendLayer.layerId = layer.id;
      legendLayer.subLayerIds = layer.visibleLayers;
      legends.push(legendLayer);
    }
  }

  template.layoutOptions = {
    authorText: 'SNR',
    copyrightText: 'Copyright © 2020. Todos los derechos reservados.',
    legendLayers: legends,
    titleText: title,
    scalebarUnit: scalebarUnit,
    customTextElements: [],
  };

  var arr = this.urlGisServiceU.split('/');
  var url =
    arr[0] +
    '//' +
    arr[2] +
    '/arcgis/rest/services/Utilities/PrintingTools/GPServer/Export%20Web%20Map%20Task';
  gisportal.print = new esri.tasks.PrintTask(url);
  var printparams = new esri.tasks.PrintParameters();
  printparams.map = gisportal.map;
  printparams.outSpatialReference = gisportal.map.spatialReference;
  printparams.template = template;

  var image = 'pdf';
  if (format != 'PDF') {
    image = 'image';
  }

  $('#printResultsNode').append(
    '<div class="printResult" id="resultNode" onclick="openPrint(' +
      indexUrlPrints +
      ');">' +
      '<table class="printResultTable"><tr><td width="30px"><img src="faces/javax.faces.resource/' +
      image +
      '.png?ln=images' +
      '" /></td><td id="nameNode' +
      this.indexUrlPrints +
      '">Cargando...</div></td></tr></table></div>'
  );

  gisportal.print.execute(
    printparams,
    dojo._base.lang.hitch(this, function (result) {
      $('#nameNode' + this.indexUrlPrints).html(title ? title : 'Sin titulo');
      this.urlPrints[this.indexUrlPrints] = result.url;
      PF('btnExportar').enable();
      this.indexUrlPrints = indexUrlPrints + 1;
    }),
    dojo._base.lang.hitch(this, function (result) {
      $('#nameNode' + this.indexUrlPrints).html('Error, inténtelo de nuevo');
      PF('btnExportar').enable();
      this.indexUrlPrints = indexUrlPrints + 1;
    })
  );
}

function exportLayer(layer, exportType) {
  var geojson = this.createGeoJSON(exportType);
  if (!geojson) {
    PF('btnExportarDatos').enable();
    return;
  }

  if (exportType == 'kml') {
    require([
      'https://cdn.rawgit.com/mapbox/tokml/v0.4.0/tokml.js',
    ], dojo._base.lang.hitch(this, function (tokml) {
      var kml = tokml(geojson, this.kmlOptions);
      PF('btnExportarDatos').enable();
      if (!kml) {
        return;
      }
      this.downloadFile(
        kml,
        'application/vnd.google-earth.kml+xml;charset=utf-8;',
        this.getFileName('.kml'),
        true
      );
    }));
  } else {
    require([
      'https://unpkg.com/shp-write@0.2.6/shpwrite.js',
    ], dojo._base.lang.hitch(this, function (shpWrite) {
      var zipFile = shpWrite.download(geojson, this.shapefileOptions);
      PF('btnExportarDatos').enable();
    }));
  }
}

function openPrint(index) {
  window.open(this.urlPrints[index]);
}

function clearPrint() {
  this.indexUrlPrints = 0;
  this.urlPrints = [];
  $('#printResultsNode').empty();
}

function handleCloseDlgSettingsPrint(xhr, status, args) {
  if (!args.validationFailed) {
    dlgSettingsPrint.hide();
  }
}

function createGeoJSON(exportType) {
  if (
    gisportal.map.graphics.graphics.length < 1 ||
    !gisportal.map.graphics.graphics[0].attributes
  ) {
    return null;
  }

  if (!Terraformer || !Terraformer.ArcGIS) {
    return null;
  }

  var maxRecords = gisportal.map.graphics.graphics.length;
  if (maxRecords > 1000) {
    maxRecords = 1000;
  }

  var features = [];
  for (var index = 0; index < maxRecords; index++) {
    features.push(gisportal.map.graphics.graphics[index]);
  }

  var geojson = {
    type: 'FeatureCollection',
    features: [],
  };

  var includeStyle = false;
  if (
    (exportType === 'kml' || exportType === 'kmz') &&
    this.kmlOptions &&
    this.kmlOptions.simplestyle
  ) {
    includeStyle = true;
  } else if (
    exportType === 'geojson' &&
    this.geojsonOptions &&
    this.geojsonOptions.simplestyle
  ) {
    includeStyle = true;
  }

  var sourceProj = proj4.defs['EPSG:3857'];
  var destProj = proj4.defs['EPSG:4326'];
  var featureID = 1;
  dojo._base.array.forEach(
    features,
    dojo._base.lang.hitch(this, function (feature) {
      var attr = feature.attributes;
      if (typeof attr.feature === 'object') {
        delete attr.feature;
      }

      // remove any attributes that have null values
      for (var key in attr) {
        if (!attr[key]) {
          delete attr[key];
        }
      }

      if (
        (exportType === 'kml' || exportType === 'kmz') &&
        feature.geometry &&
        this.kmlOptions &&
        this.kmlOptions.styleAttributes
      ) {
        feature.attributes = this.addKMLStyleAttributes(feature);
      }
      if (feature.symbol && includeStyle) {
        feature.attributes = this.convertSymbolToAttributes(feature);
      }

      if (feature.geometry) {
        if (sourceProj && destProj) {
          feature.geometry = this.projectGeometry(feature.geometry);
        }

        var geoFeature = Terraformer.ArcGIS.parse(feature);
        var geom = geoFeature.geometry;

        // split multi-polygon/linestrings geojson into multiple single
        // polygons/linstrings
        if (
          exportType === 'shapefile' &&
          (geom.type === 'MultiPolygon' || geom.type === 'MultiLineString')
        ) {
          var props = geoFeature.properties;
          for (var i = 0, len = geom.coordinates.length; i < len; i++) {
            var feat = {
              geometry: {
                type: geom.type.replace('Multi', ''),
                coordinates: geom.coordinates[i],
              },
              id: featureID++,
              properties: props,
              type: 'Feature',
            };
            geojson.features.push(feat);
          }

          // not a multi-polygon, so just push it
        } else {
          geoFeature.id = featureID++;
          geojson.features.push(geoFeature);
        }
      } else {
        topic.publish('viewer/handleError', 'feature has no geometry');
      }
    })
  );

  return geojson;
}

function convertSymbolToAttributes(feature) {
  var geometry = feature.geometry;
  var symbol = feature.symbol;
  var attributes = dojo._base.lang.clone(feature.attributes);
  var outline = null,
    color = null;

  switch (geometry.type) {
    case 'point':
      switch (symbol.type) {
        case 'picturemarkersymbol':
          attributes.href = symbol.imageData;
          if (symbol.xscale) {
            attributes.scale = symbol.xscale;
          } else if (symbol.width && symbol.size) {
            attributes.scale = symbol.width / symbol.size;
          }

          attributes['marker-opacity'] = 1.0;
          attributes['label-scale'] = 0;
          break;

        case 'simplemarkersymbol':
          color = symbol.color;
          if (color) {
            attributes['marker-color'] = color.toHex();
            attributes['marker-opacity'] = color.a;
          }

          attributes['label-scale'] = 0;
          break;

        case 'textsymbol':
          attributes.href = '#';
          attributes.scale = 0;

          attributes['label-scale'] = symbol.font.size / 16;
          color = symbol.color;
          if (color) {
            attributes['label-color'] = color.toHex();
            attributes['label-opacity'] = color.a;
          }
          break;
        default:
          break;
      }

      break;

    case 'polyline':
      color = symbol.color;
      if (color) {
        attributes.stroke = color.toHex();
        attributes['stroke-opacity'] = color.a;
        attributes['stroke-width'] = symbol.width;
      }

      attributes['label-scale'] = 0;
      break;

    case 'polygon':
      outline = symbol.outline;
      if (outline) {
        color = outline.color;
        if (color) {
          attributes.stroke = color.toHex();
          attributes['stroke-opacity'] = color.a;
          attributes['stroke-width'] = outline.width;
        }
      }

      color = symbol.color;
      if (color) {
        attributes.fill = color.toHex();
        attributes['fill-opacity'] = color.a;
      }

      attributes['label-scale'] = 0;
      break;

    default:
      break;
  }

  return attributes;
}

function projectGeometry(geometry) {
  var pt = null,
    newPt = null;
  switch (geometry.type) {
    case 'point':
      newPt = this.projectPoint(geometry);
      geometry = new esri.geometry.Point({
        x: newPt.x,
        y: newPt.y,
        spatialReference: new esri.SpatialReference('EPSG:4326'),
      });
      break;

    case 'polyline':
    case 'polygon':
      var paths = geometry.paths || geometry.rings;
      var len = paths.length;
      for (var k = 0; k < len; k++) {
        var len2 = paths[k].length;
        for (var j = 0; j < len2; j++) {
          pt = geometry.getPoint(k, j);
          newPt = this.projectPoint(pt);
          geometry.setPoint(
            k,
            j,
            new esri.geometry.Point({
              x: newPt.x,
              y: newPt.y,
              spatialReference: new esri.SpatialReference('EPSG:4326'),
            })
          );
        }
      }
      geometry.setSpatialReference(new esri.SpatialReference('EPSG:4326'));
      break;

    default:
      break;
  }

  return geometry;
}

function projectPoint(point) {
  return proj4(proj4.defs['EPSG:3857'], proj4.defs['EPSG:4326']).forward(point);
}

function downloadFile(content, mimeType, fileName, useBlob) {
  mimeType = mimeType || 'application/octet-stream';
  var url = null;
  var dataURI = 'data:' + mimeType + ',' + content;
  this.removeLink();
  this.link = document.createElement('a');
  var blob = new Blob([content], {
    type: mimeType,
  });

  // feature detection
  if (typeof this.link.download !== 'undefined') {
    // Browsers that support HTML5 download attribute
    if (useBlob) {
      url = window.URL.createObjectURL(blob);
    } else {
      url = dataURI;
    }
    this.link.setAttribute('href', url);
    this.link.setAttribute('download', fileName);
    this.link.innerHTML = 'Haga clic para descargar ' + fileName;
    // $('#divExportLink').append(this.link);
    this.link.click();

    return null;

    // feature detection using IE10+ routine
  } else if (navigator.msSaveOrOpenBlob) {
    return navigator.msSaveOrOpenBlob(blob, fileName);
  }

  // catch all. for which browsers?
  window.open(dataURI);
  window.focus();
  return null;
}

function removeLink() {
  if (
    this.link &&
    this.divExportLink &&
    $('#divExportLink').children.length > 0
  ) {
    $('#divExportLink').remove();
    $('#divExportLink').html('&nbsp;');
  }
  this.link = null;
}

function getFileName(extension) {
  if (this.filename) {
    return typeof this.filename === 'function'
      ? this.filename(this) + extension
      : this.filename + extension;
  }
  return 'result' + extension;
}

function reorderLayers() {
  // get layer name nodes, build an array corresponding to new layer order
  var layerOrder = [];
  dojo.query('#layerList .dojoDndItem label').forEach(function (n, idx) {
    for (var info in infos) {
      var i = infos[info];
      if (i.name === n.innerHTML) {
        layerOrder[idx] = i.id;
        // keep track of a layer's position in the layer list
        i.position = idx;
        break;
      }
    }
  });

  var newOrderLayers = [];
  for (var j = 0; j < layerOrder.length; j++) {
    var layerId = layerOrder[j];
    var lyr = gisportal.map.getLayer(layerId);
    newOrderLayers.push(lyr);
    gisportal.map.removeLayer(lyr);
  }

  for (var j = 0; j < newOrderLayers.length; j++) {
    var lyr = newOrderLayers[j];
    gisportal.map.addLayers([lyr]);
  }
}

function buildLayerList() {
  dndSource.clearItems();
  var idSelection = document.getElementById('layerList');
  $(idSelection).empty();

  var layerNames = [];
  for (var info in infos) {
    if (!infos[info].hasOwnProperty('id')) {
      continue;
    }
    // only want the layer's name, don't need the db name and owner name
    var nameParts = infos[info].name.split('.');
    var layerName = nameParts[nameParts.length - 1];
    var layerDiv = createToggle(infos[info].id, layerName, infos[info].visible);
    layerNames[infos[info].position] = layerDiv;
  }

  dndSource.insertNodes(false, layerNames);

  for (var info in infos) {
    if (!infos[info].hasOwnProperty('id')) {
      continue;
    }
    createMenu(infos[info].id, infos[info].name);
  }
}

function createMenu(id, name) {
  var pMenu = new dijit.Menu({
    targetNodeIds: ['div' + id],
  });

  pMenu.addChild(
    new dijit.MenuItem({
      id:
        'item-' +
        Math.random().toString(36).substr(2, 9) +
        '-' +
        id +
        '-' +
        name,
      label: 'Apagar Todas las Capas',
      onClick: dojo._base.lang.hitch(this, offAllLayers),
    })
  );

  pMenu.addChild(
    new dijit.MenuItem({
      id:
        'item-' +
        Math.random().toString(36).substr(2, 9) +
        '-' +
        id +
        '-' +
        name,
      label: 'Ampliar Capa',
      onClick: dojo._base.lang.hitch(this, zoomtoOGCLayer),
    })
  );

  pMenu.addChild(
    new dijit.MenuItem({
      id:
        'item-' +
        Math.random().toString(36).substr(2, 9) +
        '-' +
        id +
        '-' +
        name,
      label: 'Transparencia',
      onClick: dojo._base.lang.hitch(this, activateOpacity),
    })
  );
}

function toggleLayer(e) {
  var layerId = e.target.id;

  for (var info in infos) {
    var i = infos[info];
    if (i.name === e.target.name) {
      i.visible = !i.visible;
    }
  }

  var lyr = gisportal.map.getLayer(layerId);
  lyr.setVisibility(!lyr.visible);
}

function removerOGCLayer(e) {
  var layerId = e.target.name;
  var lyr = gisportal.map.getLayer(layerId);
  gisportal.map.removeLayer(lyr);
  dojo.destroy('div' + e.target.name);

  this.legendLayers = this.legendLayers.filter(function (f) {
    return f.title !== e.target.title;
  });

  this.OGCLayers = this.OGCLayers.filter(function (f) {
    return f.id !== e.target.name;
  });

  for (let key in infos) {
    if (infos[key].id === e.target.name) {
      delete infos[key];
      infos.total = infos.total - 1;
      break;
    }
  }
}

function createToggle(id, name, visible) {
  var div = dojo.create('div', {
    id: 'div' + id,
    name: 'div' + id,
  });
  var layerVis = dojo.create(
    'input',
    {
      checked: visible,
      id: id,
      name: name,
      type: 'checkbox',
    },
    div
  );
  dojo.on(layerVis, 'click', toggleLayer);
  var layerSpan = dojo.create(
    'label',
    {
      for: name,
      innerHTML: name,
    },
    div
  );
  var deleteSpan = dojo.create(
    'a',
    {
      name: id,
      href: '#',
      innerHTML: '   X',
      style: 'text-align:right;color:red;font-weight:bold',
      title: name,
    },
    div
  );
  dojo.on(deleteSpan, 'click', dojo._base.lang.hitch(this, removerOGCLayer));

  return div;
}

function zoomtoOGCLayer(e) {
  var id = e.target.id.split('-')[2];
  var lyr = gisportal.map.getLayer(id);
  if (lyr.extent) {
    gisportal.map.setExtent(lyr.extent, true);
  }
}

function activateOpacity(e) {
  var id = e.target.id.split('-')[2];
  currentLayer = gisportal.map.getLayer(id);
  dijit.byId('slider').set('value', currentLayer.opacity * 100);
  var span = document.getElementsByClassName('close')[0];
  span.onclick = function () {
    $('#opacityModal').offset({ top: 0, left: 0 });
    $('#opacityModal').hide();
  };

  var top = $('#' + id).offset().top;
  var left = 200;
  $('#opacityModal').offset({ top: top, left: left });
  $('#opacityModal').show();
}

function offAllLayers() {
  for (var info in infos) {
    var i = infos[info];
    i.visible = false;
    $('#' + i.id).prop('checked', false);
  }

  var lyrs = gisportal.map.getLayersVisibleAtScale(gisportal.map.getScale());
  if (lyrs) {
    for (var i = 0; i < lyrs.length; i++) {
      var lyr = lyrs[i];
      if (lyr && lyr.loaded && lyr.visible && lyr.id.indexOf('snrLayer') >= 0) {
        lyr.setVisibility(false);
      }
    }
  }
}

function loadOGCServices(type, url) {
  if (!loadFirstService) {
    dndSource = new dojo.dnd.Source('layerList');
    dndSource.on('DndDrop', reorderLayers);
  }

  var hostname = new URL(url).hostname;
  esri.config.defaults.io.corsEnabledServers.push(hostname);

  //esriConfig.defaults.io.proxyUrl = 'proxy.jsp';

  if (type == 'WMS') {
    fetch(url + '?REQUEST=GetCapabilities&service=WMS')
      .then(function (response) {
        return response.text();
      })
      .then(function (text) {
        var xmlDoc = dojox.xml.parser.parse(text);
        var childNodes = xmlDoc.documentElement.childNodes;
        var layersArray = [];
        for (var i = 0; i < childNodes.length; i++) {
          var node = childNodes[i];
          if (
            node.nodeType !== Node.TEXT_NODE &&
            node.nodeName === 'Capability'
          ) {
            var capabilities = node.childNodes;
            for (var j = 0; j < capabilities.length; j++) {
              var capability = capabilities[j];
              if (
                capability.nodeType !== Node.TEXT_NODE &&
                capability.nodeName === 'Layer'
              ) {
                var layers = capability.childNodes;
                for (var k = 0; k < layers.length; k++) {
                  var layer = layers[k];
                  var name = null;
                  var title = null;
                  if (
                    layer.nodeType !== Node.TEXT_NODE &&
                    layer.nodeName === 'Layer'
                  ) {
                    var sublayers = layer.childNodes;
                    for (var l = 0; l < layers.length; l++) {
                      var sublayer = sublayers[l];
                      if (
                        sublayer.nodeType !== Node.TEXT_NODE &&
                        sublayer.nodeName === 'Name'
                      ) {
                        name = sublayer.textContent;
                      }
                      if (
                        sublayer.nodeType !== Node.TEXT_NODE &&
                        sublayer.nodeName === 'Title'
                      ) {
                        title = sublayer.textContent;
                      }
                      if (name && title) {
                        layersArray.push({ id: name, layerName: title });
                        break;
                      }
                    }
                  }
                }
              }
            }
            break;
          }
        }
        document.getElementById(
          'westForm:capasOGCString'
        ).value = JSON.stringify(layersArray);
        populateOGCLayers();
        PF('btnServiciosOGC').enable();
        document.getElementById('msgServiciosOGC').innerHTML =
          'El servicio se cargó correctamente!';
      })
      .catch(function (error) {
        document.getElementById(
          'westForm:capasOGCString'
        ).value = JSON.stringify([]);
        populateOGCLayers();
        PF('btnServiciosOGC').enable();
        document.getElementById('msgServiciosOGC').innerHTML =
          'La URL digitada no coincide con un servicio válido';
      });
  } else if (type == 'WFS') {
    fetch(url + '?SERVICE=WFS&REQUEST=GetCapabilities')
      .then(function (response) {
        return response.text();
      })
      .then(function (text) {
        var xmlDoc = dojox.xml.parser.parse(text);
        var childNodes = xmlDoc.documentElement.childNodes;
        var layersArray = [];
        for (var i = 0; i < childNodes.length; i++) {
          var node = childNodes[i];
          if (
            node.nodeType !== Node.TEXT_NODE &&
            node.nodeName.indexOf('FeatureTypeList') >=0
          ) {
            var capabilities = node.childNodes;
            for (var j = 0; j < capabilities.length; j++) {
              var capability = capabilities[j];
              if (
                capability.nodeType !== Node.TEXT_NODE &&
                capability.nodeName.indexOf('FeatureType') >=0
              ) {
                var attributes = capability.childNodes;
                var name = null;
                var title = null;
                for (var k = 0; k < attributes.length; k++) {
                  var attribute = attributes[k];
                  if (
                    attribute.nodeType !== Node.TEXT_NODE &&
                    attribute.nodeName.indexOf('Name') >=0
                  ) {
                    name = attribute.textContent;
                  }
                  if (
                    attribute.nodeType !== Node.TEXT_NODE &&
                    attribute.nodeName.indexOf('Title') >=0
                  ) {
                    title = attribute.textContent;
                  }
                  if (name && title) {
                    layersArray.push({ id: name, layerName: title });
                    break;
                  }
                }
              }
            }
            break;
          }
        }
        document.getElementById(
          'westForm:capasOGCString'
        ).value = JSON.stringify(layersArray);
        populateOGCLayers();
        PF('btnServiciosOGC').enable();
        document.getElementById('msgServiciosOGC').innerHTML =
          'El servicio se cargó correctamente!';
      })
      .catch(function (error) {
        document.getElementById(
          'westForm:capasOGCString'
        ).value = JSON.stringify([]);
        populateOGCLayers();
        PF('btnServiciosOGC').enable();
        document.getElementById('msgServiciosOGC').innerHTML =
          'La URL digitada no coincide con un servicio válido';
      });
  } else if (type == 'WCS') {
    fetch(url + '?SERVICE=WCS&VERSION=1.0.0&REQUEST=GetCapabilities')
      .then(function (response) {
        return response.text();
      })
      .then(function (text) {
        var xmlDoc = dojox.xml.parser.parse(text);
        var childNodes = xmlDoc.documentElement.childNodes;
        var layersArray = [];
        for (var i = 0; i < childNodes.length; i++) {
          var node = childNodes[i];
          if (
            node.nodeType !== Node.TEXT_NODE &&
            node.nodeName.indexOf('ContentMetadata') >= 0
          ) {
            var capabilities = node.childNodes;
            for (var j = 0; j < capabilities.length; j++) {
              var capability = capabilities[j];
              if (
                capability.nodeType !== Node.TEXT_NODE &&
                capability.nodeName.indexOf('CoverageOfferingBrief') >= 0
              ) {
                var attributes = capability.childNodes;
                var name = null;
                var title = null;
                for (var k = 0; k < attributes.length; k++) {
                  var attribute = attributes[k];
                  if (
                    attribute.nodeType !== Node.TEXT_NODE &&
                    attribute.nodeName.indexOf('name') >= 0
                  ) {
                    name = attribute.textContent;
                  }
                  if (
                    attribute.nodeType !== Node.TEXT_NODE &&
                    attribute.nodeName.indexOf('label') >= 0
                  ) {
                    title = attribute.textContent;
                  }
                  if (name && title) {
                    layersArray.push({ id: name, layerName: title });
                    break;
                  }
                }
              }
            }
            break;
          }
        }
        document.getElementById(
          'westForm:capasOGCString'
        ).value = JSON.stringify(layersArray);
        populateOGCLayers();
        PF('btnServiciosOGC').enable();
        document.getElementById('msgServiciosOGC').innerHTML =
          'El servicio se cargó correctamente!';
      })
      .catch(function (error) {
        document.getElementById(
          'westForm:capasOGCString'
        ).value = JSON.stringify([]);
        populateOGCLayers();
        PF('btnServiciosOGC').enable();
        document.getElementById('msgServiciosOGC').innerHTML =
          'La URL digitada no coincide con un servicio válido';
      });
  }
}

function adddOGCServices(type, url, layerId, layerTitle) {
  try {
    var id = 'snrLayer' + (this.OGCLayers.length + 1);

    var filter = this.legendLayers.filter(function (f) {
      return f.title === layerTitle;
    });

    if (filter.length > 0) {
      return;
    }

    if (type == 'WFS') {
      var spl = layerId.split(':');
      var newLayerId = layerId;
      if (spl.length > 0) {
        newLayerId = spl[1];
      }

      var opts = {
        id: id,
        url: url,
        name: newLayerId,
      };

      var wfsLayer = new esri.layers.WFSLayer();
      wfsLayer.fromJson(opts);

      wfsLayer.on('load', function (evt) {
        infos[infos.total] = {
          id: id,
          name: layerTitle,
          position: infos.total,
          visible: true,
          opacity: 1,
        };
        infos.total += 1;
        buildLayerList();

        if (evt.err) {
          document.getElementById('msgServiciosOGC').innerHTML =
            'La URL digitada no coincide con un servicio válido';
        } else {
          document.getElementById('msgServiciosOGC').innerHTML =
            'El servicio se cargó correctamente!';
        }
      });

      wfsLayer.on('error', function (error) {
        if (error.error === 'WFSLayer: invalid layerName') {
          infos[infos.total] = {
            id: id,
            name: layerTitle,
            position: infos.total,
            visible: true,
            opacity: 1,
          };
          infos.total += 1;
          buildLayerList();
          document.getElementById('msgServiciosOGC').innerHTML =
              'El servicio se cargó correctamente!';
        } else {
          document.getElementById('msgServiciosOGC').innerHTML =
            'La URL digitada no coincide con un servicio válido';
        }

        console.log('[ERROR] ', error);
        console.log('[ERROR] ', error.error.responseText);
      });

      this.legendLayers.push({ layer: wfsLayer, title: layerTitle });
      this.OGCLayers.push(wfsLayer);
      gisportal.map.addLayers([wfsLayer]);
    } else if (type == 'WCS') {
      var wcsLayer = new esri.layers.WCSLayer(url, {
        id: id,
        version: '1.0.0',
        visible: true,
        coverageId: layerId,
      });

      wcsLayer.on('load', function (evt) {
        infos[infos.total] = {
          id: id,
          name: layerTitle,
          position: infos.total,
          visible: true,
          opacity: 1,
        };
        infos.total += 1;
        buildLayerList();

        if (evt.err) {
          document.getElementById('msgServiciosOGC').innerHTML =
            'La URL digitada no coincide con un servicio válido';
        } else {
          document.getElementById('msgServiciosOGC').innerHTML =
            'El servicio se cargó correctamente!';
        }
      });

      wcsLayer.on('error', function (error) {
        if (error.error === 'WCSLayer: invalid layerName') {
          infos[infos.total] = {
            id: id,
            name: layerTitle,
            position: infos.total,
            visible: true,
            opacity: 1,
          };
          infos.total += 1;
          buildLayerList();
        } else {
          document.getElementById('msgServiciosOGC').innerHTML =
            'La URL digitada no coincide con un servicio válido';
        }

        console.log('[ERROR] ', error);
        console.log('[ERROR] ', error.error.responseText);
      });

      this.legendLayers.push({ layer: wcsLayer, title: layerTitle });
      this.OGCLayers.push(wcsLayer);
      gisportal.map.addLayers([wcsLayer]);
    } else {
      var wmsLayer = new esri.layers.WMSLayer(url, {
        id: id,
        title: 'Servicio WMS',
        visible: true,
        visibleLayers: [layerId],
      });

      dojo.on.once(wmsLayer, 'update-end', function (evt) {
        infos[infos.total] = {
          id: id,
          name: layerTitle,
          position: infos.total,
          visible: true,
          opacity: 1,
        };
        infos.total += 1;
        buildLayerList();

        if (evt.err) {
          document.getElementById('msgServiciosOGC').innerHTML =
            'La URL digitada no coincide con un servicio válido';
        } else {
          document.getElementById('msgServiciosOGC').innerHTML =
            'El servicio se cargó correctamente!';
        }
      });

      wmsLayer.on('error', function (error) {
        document.getElementById('msgServiciosOGC').innerHTML =
          'La URL digitada no coincide con un servicio válido';

        console.log('[ERROR] ', error);
        console.log('[ERROR] ', error.error.responseText);
      });

      this.legendLayers.push({ layer: wmsLayer, title: layerTitle });
      this.OGCLayers.push(wmsLayer);
      gisportal.map.addLayers([wmsLayer]);
    }
  } catch (e) {
    document.getElementById('msgServiciosOGC').innerHTML =
      'La URL digitada no coincide con un servicio válido';
  }

  document.getElementById('msgServiciosOGC').innerHTML = 'Cargando...';
  PF('btnServiciosOGC').enable();
}

function clearOGCServices() {
  currentLayer = null;
  PF('btnServiciosOGC').enable();
  for (var index = 0; index < this.OGCLayers.length; index++) {
    gisportal.map.removeLayer(this.OGCLayers[index]);
  }
  this.OGCLayers = [];
  this.legendLayers = [];
  if (document.getElementById('msgServiciosOGC')) {
    document.getElementById('msgServiciosOGC').innerHTML = '';
    // document.getElementById('txtOGCUrl').value = '';
  }
  dndSource.clearItems();
  infos = { total: 0 };
  var idSelection = document.getElementById('layerList');
  $(idSelection).empty();
}

window.onload = function () {
  go();
};

function go() {
  $('.loader-page').css({ visibility: 'hidden', opacity: '0' });
}

