$wnd.org_semanticsoft_vaaclipse_widgetset_DefaultWidgetset.runAsyncCallback9("var $intern_185 = {889:1, 191:1, 5:1, 30:1, 941:1, 388:1, 111:1, 68:1, 3:1};\ndefineClass(1, null, {});\n_.getClass$ = function getClass_0(){\n  return this.___clazz$;\n}\n;\nfunction isProdMode(){\n  return true;\n}\n\nfunction $getNodeName(this$static){\n  return this$static.nodeName;\n}\n\nfunction $getOffsetParent(this$static){\n  return this$static.offsetParent;\n}\n\nfunction $createColElement(this$static){\n  return ($clinit_DOMImpl() , impl_0).createElement_0(this$static, 'col');\n}\n\nfunction $getY(this$static){\n  var relativeElem;\n  relativeElem = $getRelativeElement(this$static);\n  if (isNotNull(relativeElem)) {\n    return $getRelativeY(this$static, relativeElem);\n  }\n  return $getClientY_1(this$static);\n}\n\nfunction $add_4(this$static, w, left, top_0){\n  var beforeIndex;\n  $removeFromParent_0(w);\n  beforeIndex = this$static.getWidgetCount();\n  this$static.setWidgetPositionImpl(w, left, top_0);\n  $insert_0(this$static, w, beforeIndex);\n  $verifyPositionNotStatic(this$static, w);\n}\n\nfunction $insert_0(this$static, w, beforeIndex){\n  this$static.insert_0(w, $getElement(this$static), beforeIndex, true);\n}\n\nfunction $setWidgetPositionImpl(w, left, top_0){\n  var h_0;\n  h_0 = $getElement(w);\n  if (left == -1 && top_0 == -1) {\n    changeToStaticPositioning(h_0);\n  }\n   else {\n    $setProperty_0($getStyle(h_0), 'position', 'absolute');\n    $setProperty_0($getStyle(h_0), 'left', left + 'px');\n    $setProperty_0($getStyle(h_0), 'top', top_0 + 'px');\n  }\n}\n\nfunction $verifyPositionNotStatic(this$static, child){\n  var className;\n  if (isProdMode()) {\n    return;\n  }\n  if (!this$static.isAttached()) {\n    return;\n  }\n  if (isNull_1($getOffsetParent($getElement(child)))) {\n    return;\n  }\n  if (jsEquals($getOffsetParent($getElement(child)), $getElement(this$static))) {\n    return;\n  }\n  if ($equals_12('body', toLower($getNodeName($getElement(this$static))))) {\n    return;\n  }\n  className = $getName_8(this$static.___clazz$);\n  log_2(new IllegalStateException_0(className + \" is missing CSS 'position:{relative,absolute,fixed}'\"));\n}\n\nfunction AbsolutePanel(){\n  $clinit_UIObject();\n  AbsolutePanel_0.call(this, createDiv());\n  $setProperty_0($getStyle($getElement(this)), 'position', 'relative');\n  $setProperty_0($getStyle($getElement(this)), 'overflow', 'hidden');\n}\n\ndefineClass(2019, 142, $intern_69, AbsolutePanel);\n_.setWidgetPositionImpl = function setWidgetPositionImpl(w, left, top_0){\n  $setWidgetPositionImpl(w, left, top_0);\n}\n;\ndefineClass(305, 9, $intern_70);\n_.addClickHandler = function addClickHandler(handler){\n  return $addClickHandler(this, handler);\n}\n;\nfunction $addClickHandler_0(this$static, handler){\n  return $addDomHandler(this$static, handler, getType_10());\n}\n\nfunction $createCell(){\n  return createTD();\n}\n\nfunction $getColumnFormatter(this$static){\n  return this$static.columnFormatter;\n}\n\nfunction $insertCell(this$static, row, column){\n  var td, tr;\n  tr = $getRow(this$static.bodyElem, row);\n  td = this$static.createCell();\n  insertChild(tr, td, column);\n}\n\nfunction $removeCell(this$static, row, column){\n  var td, tr;\n  $checkCellBounds(this$static, row, column);\n  td = $cleanCell(this$static, row, column, false);\n  tr = $getRow(this$static.bodyElem, row);\n  $removeChild(tr, td);\n}\n\ndefineClass(1288, 157, $intern_77);\n_.addClickHandler = function addClickHandler_0(handler){\n  return $addClickHandler_0(this, handler);\n}\n;\n_.createCell = function createCell(){\n  return $createCell();\n}\n;\n_.insertCell = function insertCell(row, column){\n  $insertCell(this, row, column);\n}\n;\n_.removeCell = function removeCell(row, column){\n  $removeCell(this, row, column);\n}\n;\ndefineClass(1308, 1288, $intern_77);\n_.insertCell = function insertCell_0(beforeRow, beforeColumn){\n  $insertCell(this, beforeRow, beforeColumn);\n}\n;\n_.removeCell = function removeCell_0(row, col){\n  $removeCell(this, row, col);\n}\n;\nfunction $$init_451(){\n}\n\nfunction $getCellCount_0(this$static){\n  return this$static.numColumns;\n}\n\nfunction $getRowCount_0(this$static){\n  return this$static.numRows;\n}\n\nfunction $prepareRow_0(this$static, row){\n  if (row < 0) {\n    throw new IndexOutOfBoundsException_0('Cannot access a row with a negative index: ' + row);\n  }\n  if (row >= this$static.numRows) {\n    throw new IndexOutOfBoundsException_0('Row index: ' + row + ', Row size: ' + this$static.numRows);\n  }\n}\n\nfunction $removeRow_1(this$static, row){\n  $removeRow(this$static, row);\n  --this$static.numRows;\n}\n\nfunction $resize(this$static, rows_0, columns){\n  $resizeColumns(this$static, columns);\n  $resizeRows(this$static, rows_0);\n}\n\nfunction $resizeColumns(this$static, columns){\n  var i, j;\n  if (this$static.numColumns == columns) {\n    return;\n  }\n  if (columns < 0) {\n    throw new IndexOutOfBoundsException_0('Cannot set number of columns to ' + columns);\n  }\n  if (this$static.numColumns > columns) {\n    for (i = 0; i < this$static.numRows; i++) {\n      for (j = this$static.numColumns - 1; j >= columns; j--) {\n        this$static.removeCell(i, j);\n      }\n    }\n  }\n   else {\n    for (i = 0; i < this$static.numRows; i++) {\n      for (j = this$static.numColumns; j < columns; j++) {\n        this$static.insertCell(i, j);\n      }\n    }\n  }\n  this$static.numColumns = columns;\n  $resizeColumnGroup($getColumnFormatter(this$static), columns, false);\n}\n\nfunction $resizeRows(this$static, rows_0){\n  if (this$static.numRows == rows_0) {\n    return;\n  }\n  if (rows_0 < 0) {\n    throw new IndexOutOfBoundsException_0('Cannot set number of rows to ' + rows_0);\n  }\n  if (this$static.numRows < rows_0) {\n    addRows($getBodyElement(this$static), rows_0 - this$static.numRows, this$static.numColumns);\n    this$static.numRows = rows_0;\n  }\n   else {\n    while (this$static.numRows > rows_0) {\n      $removeRow_1(this$static, this$static.numRows - 1);\n    }\n  }\n}\n\nfunction Grid(){\n  HTMLTable.call(this);\n  $$init_451();\n  $setCellFormatter(this, new HTMLTable$CellFormatter(this));\n  $setRowFormatter(this, new HTMLTable$RowFormatter(this));\n  $setColumnFormatter(this, new HTMLTable$ColumnFormatter(this));\n}\n\nfunction Grid_0(rows_0, columns){\n  $clinit_HTMLTable();\n  Grid.call(this);\n  $resize(this, rows_0, columns);\n}\n\nfunction addRows(table, rows_0, columns){\n  var td = $doc.createElement('td');\n  td.innerHTML = '&nbsp;';\n  var row = $doc.createElement('tr');\n  for (var cellNum = 0; cellNum < columns; cellNum++) {\n    var cell = td.cloneNode(true);\n    row.appendChild(cell);\n  }\n  table.appendChild(row);\n  for (var rowNum = 1; rowNum < rows_0; rowNum++) {\n    table.appendChild(row.cloneNode(true));\n  }\n}\n\ndefineClass(979, 1288, $intern_77, Grid_0);\n_.createCell = function createCell_0(){\n  var td;\n  td = $createCell();\n  $setInnerHTML(td, '&nbsp;');\n  return asOld(td);\n}\n;\n_.getCellCount = function getCellCount_0(row){\n  return $getCellCount_0(this);\n}\n;\n_.getRowCount = function getRowCount_0(){\n  return $getRowCount_0(this);\n}\n;\n_.prepareCell = function prepareCell_0(row, column){\n  $prepareRow_0(this, row);\n  if (column < 0) {\n    throw new IndexOutOfBoundsException_0('Cannot access a column with a negative index: ' + column);\n  }\n  if (column >= this.numColumns) {\n    throw new IndexOutOfBoundsException_0('Column index: ' + column + ', Column size: ' + this.numColumns);\n  }\n}\n;\n_.prepareRow = function prepareRow_0(row){\n  $prepareRow_0(this, row);\n}\n;\n_.numColumns = 0;\n_.numRows = 0;\nfunction $addMouseDownHandler_0(this$static, handler){\n  return $addDomHandler(this$static, handler, getType_21());\n}\n\nfunction $addMouseMoveHandler(this$static, handler){\n  return $addDomHandler(this$static, handler, getType_22());\n}\n\nfunction $addMouseUpHandler_1(this$static, handler){\n  return $addDomHandler(this$static, handler, getType_25());\n}\n\ndefineClass(168, 638, $intern_79);\n_.addClickHandler = function addClickHandler_2(handler){\n  return $addClickHandler_1(this, handler);\n}\n;\nfunction $resizeColumnGroup(this$static, columns, growOnly){\n  var i, num;\n  columns = max_3(columns, 1);\n  num = $getChildCount(this$static.columnGroup);\n  if (num < columns) {\n    for (i = num; i < columns; i++) {\n      $appendChild(this$static.columnGroup, $createColElement(get_2()));\n    }\n  }\n   else if (!growOnly && num > columns) {\n    for (i = num; i > columns; i--) {\n      $removeChild(this$static.columnGroup, $getLastChild(this$static.columnGroup));\n    }\n  }\n}\n\ndefineClass(1651, 9, $intern_83);\n_.addClickHandler = function addClickHandler_3(handler){\n  return $addHandler_3(this, handler, getType_10());\n}\n;\ndefineClass(1893, 192, $intern_91);\n_.setWidgetPositionImpl = function setWidgetPositionImpl_0(w, left, top_0){\n  left -= $getBodyOffsetLeft(get_2());\n  top_0 -= $getBodyOffsetTop(get_2());\n  $setWidgetPositionImpl(w, left, top_0);\n}\n;\ndefineClass(1662, 1, $intern_128);\n_.openPopup = function openPopup(p0){\n  this.val$handler.invoke(this, $getMethod_0(getType_37(Lcom_vaadin_shared_ui_colorpicker_ColorPickerServerRpc_2_classLit), 'openPopup'), initValues(_3Ljava_lang_Object_2_classLit, $intern_7, 1, 3, [valueOf_51(p0)]));\n}\n;\nfunction $$init_1432(){\n}\n\nfunction $getCaption_6(this$static){\n  if ($getState_17(this$static).showDefaultCaption && (jsEquals($getState_17(this$static).caption_0, null) || $equals_12('', $getState_17(this$static).caption_0))) {\n    return $getState_17(this$static).color_0;\n  }\n  return $getState_17(this$static).caption_0;\n}\n\nfunction $getState_17(this$static){\n  return dynamicCast($getState_4(this$static), 336);\n}\n\nfunction AbstractColorPickerConnector(){\n  AbstractComponentConnector.call(this);\n  $$init_1432();\n}\n\ndefineClass(1052, 388, $intern_185);\n_.delegateCaptionHandling = function delegateCaptionHandling_3(){\n  return false;\n}\n;\n_.getState = function getState_35(){\n  return $getState_17(this);\n}\n;\n_.getState_0 = function getState_36(){\n  return $getState_17(this);\n}\n;\n_.init = function init_18(){\n  $init_3();\n  instanceOf(this.getWidget_0(), 296) && dynamicCast(this.getWidget_0(), 296).addClickHandler(this);\n}\n;\n_.onStateChanged = function onStateChanged_14(stateChangeEvent){\n  $onStateChanged_0(this, stateChangeEvent);\n  if (stateChangeEvent.hasPropertyChanged('color')) {\n    this.refreshColor();\n    $getState_17(this).showDefaultCaption && (jsEquals($getState_17(this).caption_0, null) || $equals_12('', $getState_17(this).caption_0)) && this.setCaption_0($getState_17(this).color_0);\n  }\n  (stateChangeEvent.hasPropertyChanged('caption') || stateChangeEvent.hasPropertyChanged('htmlContentAllowed') || stateChangeEvent.hasPropertyChanged('showDefaultCaption')) && this.setCaption_0($getCaption_6(this));\n}\n;\nfunction $$init_1632(this$static){\n  this$static.primaryStyleName = 'v-colorpicker';\n  this$static.popupVisible = false;\n  this$static.color_0 = null;\n}\n\nfunction ColorPickerState(){\n  AbstractComponentState.call(this);\n  $$init_1632(this);\n}\n\ndefineClass(336, 129, {129:1, 447:1, 336:1, 3:1}, ColorPickerState);\n_.htmlContentAllowed = false;\n_.popupVisible = false;\n_.showDefaultCaption = false;\nvar Lcom_vaadin_shared_ui_colorpicker_ColorPickerState_2_classLit = createForClass('com.vaadin.shared.ui.colorpicker.', 'ColorPickerState', 336, Lcom_vaadin_shared_AbstractComponentState_2_classLit), Lcom_vaadin_client_ui_colorpicker_AbstractColorPickerConnector_2_classLit = createForClass('com.vaadin.client.ui.colorpicker.', 'AbstractColorPickerConnector', 1052, Lcom_vaadin_client_ui_AbstractComponentConnector_2_classLit), Lcom_google_gwt_user_client_ui_Grid_2_classLit = createForClass('com.google.gwt.user.client.ui.', 'Grid', 979, Lcom_google_gwt_user_client_ui_HTMLTable_2_classLit);\n$entry(onLoad)(9);\n\n//# sourceURL=org.semanticsoft.vaaclipse.widgetset.DefaultWidgetset-9.js\n")
