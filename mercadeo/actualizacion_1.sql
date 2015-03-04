use EasySeguridad
go

insert into Transacciones(Aplicacion, Modulo, Transaccion, Descripcion, Link, Panel, TipoMenu, InfoXML, InfoXML_Depende_BCO, Restringido, Codigo_NivelTres, Desc_NivelTres, Indice_Transaccion) 
values('PLANMERC', 'MDPLANMERC', 'mer_seguimiento_ver', 'Visualización de información de seguimiento de planes de mercadeo', '', '', 'M', '', '', 0, '', '', 0);
insert into Transacciones(Aplicacion, Modulo, Transaccion, Descripcion, Link, Panel, TipoMenu, InfoXML, InfoXML_Depende_BCO, Restringido, Codigo_NivelTres, Desc_NivelTres, Indice_Transaccion) 
values('PLANMERC', 'MDPLANMERC', 'mer_liquidacion_add_facts', 'Agregar facturas de gastos desde el módulo de liquidación de planes', '', '', 'M', '', '', 0, '', '', 0);


insert into Atribuciones(NombreCorto, Aplicacion, Modulo, Transaccion, Habilitado) values('pmina', 'PLANMERC', 'MDPLANMERC', 'mer_seguimiento_ver', 1);
insert into Atribuciones(NombreCorto, Aplicacion, Modulo, Transaccion, Habilitado) values('pmina', 'PLANMERC', 'MDPLANMERC', 'mer_liquidacion_add_facts', 1);
