$wnd.org_semanticsoft_vaaclipse_widgetset_DefaultWidgetset.runAsyncCallback4("function $load_4(this$static){\n  $setSuperClass(this$static.val$store, Lcom_vaadin_shared_ui_colorpicker_ColorPickerState_2_classLit, Lcom_vaadin_shared_AbstractComponentState_2_classLit);\n  $setClass(this$static.val$store, 'com.vaadin.ui.ColorPicker', Lcom_vaadin_client_ui_colorpicker_ColorPickerConnector_2_classLit);\n  $setConstructor(this$static.val$store, Lcom_vaadin_shared_ui_colorpicker_ColorPickerState_2_classLit, new ConnectorBundleLoaderImpl$4$1$1(this$static));\n  $setConstructor(this$static.val$store, Lcom_vaadin_client_ui_colorpicker_ColorPickerConnector_2_classLit, new ConnectorBundleLoaderImpl$4$1$2(this$static));\n  $setReturnType(this$static.val$store, Lcom_vaadin_client_ui_colorpicker_ColorPickerConnector_2_classLit, 'getState', new Type(Lcom_vaadin_shared_ui_colorpicker_ColorPickerState_2_classLit));\n  $setReturnType(this$static.val$store, Lcom_vaadin_client_ui_colorpicker_ColorPickerConnector_2_classLit, 'getWidget', new Type(Lcom_vaadin_client_ui_VColorPicker_2_classLit));\n  $setInvoker(this$static.val$store, Lcom_vaadin_client_ui_VColorPicker_2_classLit, 'setOpen', new ConnectorBundleLoaderImpl$4$1$3(this$static));\n  $setInvoker(this$static.val$store, Lcom_vaadin_client_ui_VColorPicker_2_classLit, 'setColor', new ConnectorBundleLoaderImpl$4$1$4(this$static));\n  $loadJsBundle_2(this$static.val$store);\n  $setPropertyType(this$static.val$store, Lcom_vaadin_shared_ui_colorpicker_ColorPickerState_2_classLit, 'htmlContentAllowed', new Type(Ljava_lang_Boolean_2_classLit));\n  $setPropertyType(this$static.val$store, Lcom_vaadin_shared_ui_colorpicker_ColorPickerState_2_classLit, 'showDefaultCaption', new Type(Ljava_lang_Boolean_2_classLit));\n  $setPropertyType(this$static.val$store, Lcom_vaadin_shared_ui_colorpicker_ColorPickerState_2_classLit, 'popupVisible', new Type(Ljava_lang_Boolean_2_classLit));\n  $setPropertyType(this$static.val$store, Lcom_vaadin_shared_ui_colorpicker_ColorPickerState_2_classLit, 'color', new Type(Ljava_lang_String_2_classLit));\n  $setDelegateToWidget(this$static.val$store, Lcom_vaadin_shared_ui_colorpicker_ColorPickerState_2_classLit, 'popupVisible', 'setOpen');\n  $setDelegateToWidget(this$static.val$store, Lcom_vaadin_shared_ui_colorpicker_ColorPickerState_2_classLit, 'color', 'setColor');\n}\n\nfunction $loadJsBundle_2(store){\n  $loadNativeJs_2(store);\n}\n\nfunction $loadNativeJs_2(store){\n  var data_0 = {setter:function(bean, value_0){\n    bean.htmlContentAllowed = value_0.booleanValue();\n  }\n  , getter:function(bean){\n    return valueOf_51(bean.htmlContentAllowed);\n  }\n  };\n  store.setPropertyData(Lcom_vaadin_shared_ui_colorpicker_ColorPickerState_2_classLit, 'htmlContentAllowed', data_0);\n  var data_0 = {setter:function(bean, value_0){\n    bean.showDefaultCaption = value_0.booleanValue();\n  }\n  , getter:function(bean){\n    return valueOf_51(bean.showDefaultCaption);\n  }\n  };\n  store.setPropertyData(Lcom_vaadin_shared_ui_colorpicker_ColorPickerState_2_classLit, 'showDefaultCaption', data_0);\n  var data_0 = {setter:function(bean, value_0){\n    bean.popupVisible = value_0.booleanValue();\n  }\n  , getter:function(bean){\n    return valueOf_51(bean.popupVisible);\n  }\n  };\n  store.setPropertyData(Lcom_vaadin_shared_ui_colorpicker_ColorPickerState_2_classLit, 'popupVisible', data_0);\n  var data_0 = {setter:function(bean, value_0){\n    bean.color_0 = value_0;\n  }\n  , getter:function(bean){\n    return bean.color_0;\n  }\n  };\n  store.setPropertyData(Lcom_vaadin_shared_ui_colorpicker_ColorPickerState_2_classLit, 'color', data_0);\n}\n\ndefineClass(1776, 1, $intern_100);\n_.onSuccess_1 = function onSuccess_5(){\n  $load_4(this);\n  $setLoaded_0(get_24(), $getName_4(this.this$1));\n}\n;\nfunction $$init_1065(){\n}\n\nfunction ConnectorBundleLoaderImpl$4$1$1(this$2){\n  this , this$2;\n  Object_0.call(this);\n  $$init_1065();\n}\n\ndefineClass(870, 1, $intern_121, ConnectorBundleLoaderImpl$4$1$1);\n_.invoke_0 = function invoke_240(target, params){\n  return new ColorPickerState;\n}\n;\nfunction $$init_1066(){\n}\n\nfunction ConnectorBundleLoaderImpl$4$1$2(this$2){\n  this , this$2;\n  Object_0.call(this);\n  $$init_1066();\n}\n\ndefineClass(1463, 1, $intern_121, ConnectorBundleLoaderImpl$4$1$2);\n_.invoke_0 = function invoke_241(target, params){\n  return new ColorPickerConnector;\n}\n;\nfunction $$init_1067(){\n}\n\nfunction ConnectorBundleLoaderImpl$4$1$3(this$2){\n  this , this$2;\n  Object_0.call(this);\n  $$init_1067();\n}\n\ndefineClass(937, 1, $intern_121, ConnectorBundleLoaderImpl$4$1$3);\n_.invoke_0 = function invoke_242(target, params){\n  $setOpen(dynamicCast(target, 144), $booleanValue(dynamicCast(params[0], 23)));\n  return null;\n}\n;\nfunction $$init_1068(){\n}\n\nfunction ConnectorBundleLoaderImpl$4$1$4(this$2){\n  this , this$2;\n  Object_0.call(this);\n  $$init_1068();\n}\n\ndefineClass(515, 1, $intern_121, ConnectorBundleLoaderImpl$4$1$4);\n_.invoke_0 = function invoke_243(target, params){\n  $setColor_0(dynamicCast(target, 144), dynamicCast(params[0], 2));\n  return null;\n}\n;\nfunction $$init_1164(this$static){\n  this$static.color_0 = null;\n  this$static.isOpen = false;\n}\n\nfunction $isOpen_1(this$static){\n  return this$static.isOpen;\n}\n\nfunction $refreshColor(this$static){\n  if (jsNotEquals(this$static.color_0, null)) {\n    if (isNull_1(this$static.colorIcon)) {\n      this$static.colorIcon = new HTML;\n      this$static.colorIcon.setStylePrimaryName('v-colorpicker-button-color');\n      $insertBefore(this$static.wrapper, $getElement(this$static.colorIcon), this$static.captionElement);\n    }\n    $setProperty_0($getStyle($getElement(this$static.colorIcon)), 'background', this$static.color_0);\n  }\n}\n\nfunction $setColor_0(this$static, color_0){\n  this$static.color_0 = color_0;\n}\n\nfunction $setOpen(this$static, open_0){\n  this$static.isOpen = open_0;\n}\n\nfunction VColorPicker(){\n  $clinit_FocusWidget();\n  VButton.call(this);\n  $$init_1164(this);\n}\n\ndefineClass(144, 206, {889:1, 2288:1, 2270:1, 2410:1, 2367:1, 2047:1, 2158:1, 2382:1, 296:1, 397:1, 2458:1, 2089:1, 2267:1, 2256:1, 2239:1, 2416:1, 2195:1, 2026:1, 2340:1, 2205:1, 2114:1, 2303:1, 2045:1, 2028:1, 2414:1, 2336:1, 2099:1, 2129:1, 2324:1, 2193:1, 2400:1, 2374:1, 2282:1, 2354:1, 2478:1, 191:1, 2259:1, 720:1, 305:1, 55:1, 140:1, 2115:1, 2275:1, 2272:1, 2177:1, 2542:1, 2472:1, 2137:1, 177:1, 9:1, 206:1, 144:1}, VColorPicker);\n_.onClick = function onClick_30(event_0){\n  $onClick_1(this);\n  $setOpen(this, !this.isOpen);\n}\n;\n_.isOpen = false;\nfunction $$init_1434(this$static){\n  this$static.rpc = dynamicCast(create_14(Lcom_vaadin_shared_ui_colorpicker_ColorPickerServerRpc_2_classLit, this$static), 384);\n}\n\nfunction $getWidget_14(this$static){\n  return dynamicCast($getWidget_2(this$static), 144);\n}\n\nfunction ColorPickerConnector(){\n  AbstractColorPickerConnector.call(this);\n  $$init_1434(this);\n}\n\ndefineClass(1839, 1052, $intern_185, ColorPickerConnector);\n_.createWidget = function createWidget_2(){\n  return new VColorPicker;\n}\n;\n_.getWidget_0 = function getWidget_20(){\n  return $getWidget_14(this);\n}\n;\n_.onClick = function onClick_49(event_0){\n  this.rpc.openPopup($isOpen_1($getWidget_14(this)));\n}\n;\n_.refreshColor = function refreshColor_0(){\n  $refreshColor($getWidget_14(this));\n}\n;\n_.setCaption_0 = function setCaption_1(caption){\n  $getState_17(this).htmlContentAllowed?$setHtml($getWidget_14(this), caption):$setText_7($getWidget_14(this), caption);\n}\n;\nvar Lcom_vaadin_client_ui_colorpicker_ColorPickerConnector_2_classLit = createForClass('com.vaadin.client.ui.colorpicker.', 'ColorPickerConnector', 1839, Lcom_vaadin_client_ui_colorpicker_AbstractColorPickerConnector_2_classLit), Lcom_vaadin_client_ui_VColorPicker_2_classLit = createForClass('com.vaadin.client.ui.', 'VColorPicker', 144, Lcom_vaadin_client_ui_VButton_2_classLit), Lcom_vaadin_client_metadata_ConnectorBundleLoaderImpl$4$1$1_2_classLit = createForClass('com.vaadin.client.metadata.', 'ConnectorBundleLoaderImpl$4$1$1', 870, Ljava_lang_Object_2_classLit), Lcom_vaadin_client_metadata_ConnectorBundleLoaderImpl$4$1$2_2_classLit = createForClass('com.vaadin.client.metadata.', 'ConnectorBundleLoaderImpl$4$1$2', 1463, Ljava_lang_Object_2_classLit), Lcom_vaadin_client_metadata_ConnectorBundleLoaderImpl$4$1$3_2_classLit = createForClass('com.vaadin.client.metadata.', 'ConnectorBundleLoaderImpl$4$1$3', 937, Ljava_lang_Object_2_classLit), Lcom_vaadin_client_metadata_ConnectorBundleLoaderImpl$4$1$4_2_classLit = createForClass('com.vaadin.client.metadata.', 'ConnectorBundleLoaderImpl$4$1$4', 515, Ljava_lang_Object_2_classLit);\n$entry(onLoad)(4);\n\n//# sourceURL=org.semanticsoft.vaaclipse.widgetset.DefaultWidgetset-4.js\n")