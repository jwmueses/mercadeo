/*
use EasySeguridad
go

insert into Aplicaciones(Aplicacion, Descripcion, PerteneceA, web, orden, link) values('PLANMERC', 'Planes de mercadeo', 'M', 1, 0, 'http://192.168.263.10:8080/mercadeo');

insert into Modulos(Aplicacion, Modulo, Descripcion, Indice_Modulo) values('PLANMERC', 'MDPLANMERC', 'Planes de mercadeo', 0);


insert into Transacciones(Aplicacion, Modulo, Transaccion, Descripcion, Link, Panel, TipoMenu, InfoXML, InfoXML_Depende_BCO, Restringido, Codigo_NivelTres, Desc_NivelTres, Indice_Transaccion) 
values('PLANMERC', 'MDPLANMERC', 'mer_tipo_planes', 'Administración de tipos de planes de mercadeo', '', '', 'M', '', '', 0, '', '', 0);
insert into Transacciones(Aplicacion, Modulo, Transaccion, Descripcion, Link, Panel, TipoMenu, InfoXML, InfoXML_Depende_BCO, Restringido, Codigo_NivelTres, Desc_NivelTres, Indice_Transaccion) 
values('PLANMERC', 'MDPLANMERC', 'mer_asignaciones', 'Asignación de tipos de planes de mercadeo a usuarios del sistema', '', '', 'M', '', '', 0, '', '', 0);
insert into Transacciones(Aplicacion, Modulo, Transaccion, Descripcion, Link, Panel, TipoMenu, InfoXML, InfoXML_Depende_BCO, Restringido, Codigo_NivelTres, Desc_NivelTres, Indice_Transaccion) 
values('PLANMERC', 'MDPLANMERC', 'mer_laboratorios', 'Administración de laboratorios estratégicos', '', '', 'M', '', '', 0, '', '', 0);
insert into Transacciones(Aplicacion, Modulo, Transaccion, Descripcion, Link, Panel, TipoMenu, InfoXML, InfoXML_Depende_BCO, Restringido, Codigo_NivelTres, Desc_NivelTres, Indice_Transaccion) 
values('PLANMERC', 'MDPLANMERC', 'mer_planes_mercadeo', 'Gestión de planes de mercadeo', '', '', 'M', '', '', 0, '', '', 0);
insert into Transacciones(Aplicacion, Modulo, Transaccion, Descripcion, Link, Panel, TipoMenu, InfoXML, InfoXML_Depende_BCO, Restringido, Codigo_NivelTres, Desc_NivelTres, Indice_Transaccion) 
values('PLANMERC', 'MDPLANMERC', 'mer_aut_operaciones', 'Autorización de planes de mercadeo por parte de operaciones', '', '', 'M', '', '', 0, '', '', 0);
insert into Transacciones(Aplicacion, Modulo, Transaccion, Descripcion, Link, Panel, TipoMenu, InfoXML, InfoXML_Depende_BCO, Restringido, Codigo_NivelTres, Desc_NivelTres, Indice_Transaccion) 
values('PLANMERC', 'MDPLANMERC', 'mer_aut_marketing', 'Autorización de planes de mercadeo por el departamento de Marketing', '', '', 'M', '', '', 0, '', '', 0);
insert into Transacciones(Aplicacion, Modulo, Transaccion, Descripcion, Link, Panel, TipoMenu, InfoXML, InfoXML_Depende_BCO, Restringido, Codigo_NivelTres, Desc_NivelTres, Indice_Transaccion) 
values('PLANMERC', 'MDPLANMERC', 'mer_aut_ventas', 'Autorización de planes de mercadeo por el departamento de ventas', '', '', 'M', '', '', 0, '', '', 0);
insert into Transacciones(Aplicacion, Modulo, Transaccion, Descripcion, Link, Panel, TipoMenu, InfoXML, InfoXML_Depende_BCO, Restringido, Codigo_NivelTres, Desc_NivelTres, Indice_Transaccion) 
values('PLANMERC', 'MDPLANMERC', 'mer_aut_comercial', 'Autorización de planes de mercadeo por el departamento comercial', '', '', 'M', '', '', 0, '', '', 0);
insert into Transacciones(Aplicacion, Modulo, Transaccion, Descripcion, Link, Panel, TipoMenu, InfoXML, InfoXML_Depende_BCO, Restringido, Codigo_NivelTres, Desc_NivelTres, Indice_Transaccion) 
values('PLANMERC', 'MDPLANMERC', 'mer_gastos', 'Registro de gastos de planes de mercadeo', '', '', 'M', '', '', 0, '', '', 0);
insert into Transacciones(Aplicacion, Modulo, Transaccion, Descripcion, Link, Panel, TipoMenu, InfoXML, InfoXML_Depende_BCO, Restringido, Codigo_NivelTres, Desc_NivelTres, Indice_Transaccion) 
values('PLANMERC', 'MDPLANMERC', 'mer_liquidacion', 'Liquidación de planes de mercadeo', '', '', 'M', '', '', 0, '', '', 0);
insert into Transacciones(Aplicacion, Modulo, Transaccion, Descripcion, Link, Panel, TipoMenu, InfoXML, InfoXML_Depende_BCO, Restringido, Codigo_NivelTres, Desc_NivelTres, Indice_Transaccion) 
values('PLANMERC', 'MDPLANMERC', 'mer_seguimiento', 'Seguimiento y ejecución de planes de mercadeo', '', '', 'M', '', '', 0, '', '', 0);
insert into Transacciones(Aplicacion, Modulo, Transaccion, Descripcion, Link, Panel, TipoMenu, InfoXML, InfoXML_Depende_BCO, Restringido, Codigo_NivelTres, Desc_NivelTres, Indice_Transaccion) 
values('PLANMERC', 'MDPLANMERC', 'mer_seguimiento_ver', 'Visualización de información de seguimiento de planes de mercadeo', '', '', 'M', '', '', 0, '', '', 0);
insert into Transacciones(Aplicacion, Modulo, Transaccion, Descripcion, Link, Panel, TipoMenu, InfoXML, InfoXML_Depende_BCO, Restringido, Codigo_NivelTres, Desc_NivelTres, Indice_Transaccion) 
values('PLANMERC', 'MDPLANMERC', 'mer_liquidacion_add_facts', 'Agregar facturas de gastos desde el módulo de liquidación de planes', '', '', 'M', '', '', 0, '', '', 0);

insert into Transacciones(Aplicacion, Modulo, Transaccion, Descripcion, Link, Panel, TipoMenu, InfoXML, InfoXML_Depende_BCO, Restringido, Codigo_NivelTres, Desc_NivelTres, Indice_Transaccion) 
values('PLANMERC', 'MDPLANMERC', 'mer_reportes', 'Impresión de reportes', '', '', 'M', '', '', 0, '', '', 0);

insert into Transacciones(Aplicacion, Modulo, Transaccion, Descripcion, Link, Panel, TipoMenu, InfoXML, InfoXML_Depende_BCO, Restringido, Codigo_NivelTres, Desc_NivelTres, Indice_Transaccion) 
values('PLANMERC', 'MDPLANMERC', 'mer_configuraciones', 'Configuraciones de parámetros para el sistema de planes de mercadeo', '', '', 'M', '', '', 0, '', '', 0);
insert into Transacciones(Aplicacion, Modulo, Transaccion, Descripcion, Link, Panel, TipoMenu, InfoXML, InfoXML_Depende_BCO, Restringido, Codigo_NivelTres, Desc_NivelTres, Indice_Transaccion) 
values('PLANMERC', 'MDPLANMERC', 'mer_conceptos', 'Administración de montos por concepto', '', '', 'M', '', '', 0, '', '', 0);
--insert into Transacciones(Aplicacion, Modulo, Transaccion, Descripcion, Link, Panel, TipoMenu, InfoXML, InfoXML_Depende_BCO, Restringido, Codigo_NivelTres, Desc_NivelTres, Indice_Transaccion) 
--values('PLANMERC', 'MDPLANMERC', 'mer_admin_tiempos', 'Administración de tiempos para confirmacion de auspicios', '', '', 'M', '', '', 0, '', '', 0);


insert into Atribuciones(NombreCorto, Aplicacion, Modulo, Transaccion, Habilitado) values('pmina', 'PLANMERC', 'MDPLANMERC', 'mer_tipo_planes', 1);
insert into Atribuciones(NombreCorto, Aplicacion, Modulo, Transaccion, Habilitado) values('pmina', 'PLANMERC', 'MDPLANMERC', 'mer_asignaciones', 1);
insert into Atribuciones(NombreCorto, Aplicacion, Modulo, Transaccion, Habilitado) values('pmina', 'PLANMERC', 'MDPLANMERC', 'mer_laboratorios', 1);
insert into Atribuciones(NombreCorto, Aplicacion, Modulo, Transaccion, Habilitado) values('pmina', 'PLANMERC', 'MDPLANMERC', 'mer_planes_mercadeo', 1);
insert into Atribuciones(NombreCorto, Aplicacion, Modulo, Transaccion, Habilitado) values('pmina', 'PLANMERC', 'MDPLANMERC', 'mer_aut_operaciones', 1);
insert into Atribuciones(NombreCorto, Aplicacion, Modulo, Transaccion, Habilitado) values('pmina', 'PLANMERC', 'MDPLANMERC', 'mer_aut_marketing', 1);
insert into Atribuciones(NombreCorto, Aplicacion, Modulo, Transaccion, Habilitado) values('pmina', 'PLANMERC', 'MDPLANMERC', 'mer_aut_ventas', 1);
insert into Atribuciones(NombreCorto, Aplicacion, Modulo, Transaccion, Habilitado) values('pmina', 'PLANMERC', 'MDPLANMERC', 'mer_aut_comercial', 1);
insert into Atribuciones(NombreCorto, Aplicacion, Modulo, Transaccion, Habilitado) values('pmina', 'PLANMERC', 'MDPLANMERC', 'mer_gastos', 1);
insert into Atribuciones(NombreCorto, Aplicacion, Modulo, Transaccion, Habilitado) values('pmina', 'PLANMERC', 'MDPLANMERC', 'mer_liquidacion', 1);
insert into Atribuciones(NombreCorto, Aplicacion, Modulo, Transaccion, Habilitado) values('pmina', 'PLANMERC', 'MDPLANMERC', 'mer_seguimiento', 1);
insert into Atribuciones(NombreCorto, Aplicacion, Modulo, Transaccion, Habilitado) values('pmina', 'PLANMERC', 'MDPLANMERC', 'mer_liquidacion_add_facts', 1);
insert into Atribuciones(NombreCorto, Aplicacion, Modulo, Transaccion, Habilitado) values('pmina', 'PLANMERC', 'MDPLANMERC', 'mer_seguimiento_ver', 1);
insert into Atribuciones(NombreCorto, Aplicacion, Modulo, Transaccion, Habilitado) values('pmina', 'PLANMERC', 'MDPLANMERC', 'mer_reportes', 1);
insert into Atribuciones(NombreCorto, Aplicacion, Modulo, Transaccion, Habilitado) values('pmina', 'PLANMERC', 'MDPLANMERC', 'mer_configuraciones', 1);
insert into Atribuciones(NombreCorto, Aplicacion, Modulo, Transaccion, Habilitado) values('pmina', 'PLANMERC', 'MDPLANMERC', 'mer_conceptos', 1);
--insert into Atribuciones(NombreCorto, Aplicacion, Modulo, Transaccion, Habilitado) values('pmina', 'PLANMERC', 'MDPLANMERC', 'mer_admin_tiempos', 1);

*/



create database PlanMercadeo
go

use PlanMercadeo
go

CREATE TABLE tbl_configuracion(
 parametro varchar(30) primary key,
 valor varchar(40) not null
)
GO


insert into tbl_configuracion(parametro, valor) values('mail_remitente', 'remitente@farmaenlace.com')
go
insert into tbl_configuracion(parametro, valor) values('mail_operaciones', 'operaciones@farmaenlace.com')
go
insert into tbl_configuracion(parametro, valor) values('mail_marketing', 'marketing@farmaenlace.com')
go
insert into tbl_configuracion(parametro, valor) values('mail_ventas', 'ventas@farmaenlace.com')
go
insert into tbl_configuracion(parametro, valor) values('mail_comercial', 'comercial@farmaenlace.com')
go
insert into tbl_configuracion(parametro, valor) values('mail_compras_internas', 'compras_internas@farmaenlace.com')
go
insert into tbl_configuracion(parametro, valor) values('admin_tiempos_conf', '30')
go



CREATE TABLE tbl_tipo_plan(
 id_tipo_plan varchar(10) primary key,
 fecha_creacion datetime not null,
 usuario_creacion varchar(30),
 nombre varchar(80) not null,
 cedula varchar(10) not null,
 coordinador varchar(110) not null,
 mail_responsable varchar(50),

 p_incremento numeric(5,2),
 dias_prolonga int,

 estado bit default 1
)
GO

CREATE TABLE tbl_tipo_plan_cuenta(
 id_tipo_plan_cuenta int identity(1,1) primary key,
 id_tipo_plan varchar(10) not null references tbl_tipo_plan(id_tipo_plan),
 cuenta varchar(50) not null,
 descripcion varchar(80)
)
go

CREATE TABLE tbl_tipo_plan_sucursal(
 id_tipo_plan varchar(10) not null references tbl_tipo_plan(id_tipo_plan),
 codigo varchar(3) not null,
 nombre varchar(60),
 primary key(id_tipo_plan, codigo)
)
go


CREATE TABLE tbl_tipo_plan_usuario(
 id_tipo_plan_usuario int identity(1,1) primary key,
 id_tipo_plan varchar(10) not null references tbl_tipo_plan(id_tipo_plan),
 alias varchar(65) not null,
 usuario varchar(85) not null
)
go


CREATE TABLE tbl_laboratorio(
 id_laboratorio int identity(1,1) primary key,
 numero_idproveedor varchar(20) not null,
 nombre_comercial varchar(70) not null,
 fecha_creacion datetime not null,
 usuario_creacion varchar(30),
 anio_vigencia int not null,
 monto numeric(13,2) default 0,
 saldo numeric(13,2) default 0,
 --id_tipo_plan varchar(10) not null references tbl_tipo_plan(id_tipo_plan),
 estado bit default 1
)
GO


CREATE TABLE tbl_labortorio_tipo_plan_presupuesto(
 id_laboratorio int not null references tbl_laboratorio(id_laboratorio),
 id_tipo_plan varchar(10) not null references tbl_tipo_plan(id_tipo_plan),
 presupuesto numeric(18,2) default 0,
 saldo  numeric(18,2) default 0,
 primary key(id_laboratorio, id_tipo_plan)
)
go

CREATE TRIGGER trg_actualizaMonto ON tbl_labortorio_tipo_plan_presupuesto AFTER INSERT, UPDATE, DELETE AS
declare @monto numeric(13, 2), @saldo numeric(13, 2), @id_laboratorio int, @id_tipo_plan varchar(10), @presupuesto numeric(18,2), @presupuesto_ant numeric(18,2)
	select @id_laboratorio=I.id_laboratorio, @id_tipo_plan=I.id_tipo_plan, @presupuesto=I.presupuesto  from inserted as I
	select @presupuesto_ant = D.presupuesto from deleted as D
	if @presupuesto_ant is null
		set @presupuesto_ant = 0
	update tbl_labortorio_tipo_plan_presupuesto set saldo = saldo + (@presupuesto-@presupuesto_ant) where id_laboratorio=@id_laboratorio and id_tipo_plan=@id_tipo_plan
	
	select @monto = case when sum(presupuesto) > 0 then sum(presupuesto) else 0 end, @saldo = case when sum(saldo) > 0 then sum(saldo) else 0 end from tbl_labortorio_tipo_plan_presupuesto where id_laboratorio=@id_laboratorio
	update tbl_laboratorio set monto=@monto, saldo=@saldo where id_laboratorio=@id_laboratorio	
GO


CREATE TABLE tbl_labortorio_tipo_plan_presupuesto_kardex(
 id_labortorio_tipo_plan_presupuesto_kardex bigint identity(1,1) primary key,
 id_laboratorio int not null references tbl_laboratorio(id_laboratorio),
 id_tipo_plan varchar(10) not null references tbl_tipo_plan(id_tipo_plan),
 fecha_registro datetime,
 usuario varchar(65),
 descripcion varchar(250),
 valor numeric(13,2) default 0,
 saldo numeric(13, 2),
 es_entrada bit default 1
)
go




CREATE TRIGGER trg_actualizaPresupuesto ON tbl_labortorio_tipo_plan_presupuesto_kardex AFTER INSERT, UPDATE, DELETE AS
declare @id_labortorio_tipo_plan_presupuesto_kardex bigint, @id_laboratorio int, @id_tipo_plan varchar(10), @valor numeric(13, 2), @es_entrada bit, @saldo numeric(13, 2), @saldo_ant numeric(13, 2), @saldo_ant1 numeric(13, 2), @valor1 numeric(13, 2), @valor2 numeric(13, 2)
	select @id_labortorio_tipo_plan_presupuesto_kardex=id_labortorio_tipo_plan_presupuesto_kardex, @id_laboratorio=I.id_laboratorio, @id_tipo_plan=I.id_tipo_plan, @valor=valor, @es_entrada=es_entrada from inserted as I
	select @saldo_ant = case when saldo>0 then saldo else 0 end from tbl_labortorio_tipo_plan_presupuesto_kardex where id_labortorio_tipo_plan_presupuesto_kardex = (select max(id_labortorio_tipo_plan_presupuesto_kardex) from tbl_labortorio_tipo_plan_presupuesto_kardex where id_laboratorio=@id_laboratorio and id_labortorio_tipo_plan_presupuesto_kardex<>@id_labortorio_tipo_plan_presupuesto_kardex)
	if @saldo_ant is null
		set @saldo_ant = 0


	select @valor1 = case when sum(valor) > 0 then sum(valor) else 0 end from tbl_labortorio_tipo_plan_presupuesto_kardex where id_laboratorio=@id_laboratorio and id_tipo_plan=@id_tipo_plan and es_entrada=1
	select @valor2 = case when sum(valor) > 0 then sum(valor) else 0 end from tbl_labortorio_tipo_plan_presupuesto_kardex where id_laboratorio=@id_laboratorio and id_tipo_plan=@id_tipo_plan and es_entrada=0
	select @saldo_ant1 = case when saldo>0 then saldo else 0 end from tbl_labortorio_tipo_plan_presupuesto_kardex where id_labortorio_tipo_plan_presupuesto_kardex = (select max(id_labortorio_tipo_plan_presupuesto_kardex) from tbl_labortorio_tipo_plan_presupuesto_kardex where id_laboratorio=@id_laboratorio and id_tipo_plan=@id_tipo_plan and id_labortorio_tipo_plan_presupuesto_kardex<>@id_labortorio_tipo_plan_presupuesto_kardex)
	if @saldo_ant1 is null
		set @saldo_ant1 = 0


	update tbl_labortorio_tipo_plan_presupuesto_kardex set saldo = case @es_entrada when 1 then @saldo_ant+@valor else @saldo_ant-@valor end where id_labortorio_tipo_plan_presupuesto_kardex=@id_labortorio_tipo_plan_presupuesto_kardex	
	update tbl_labortorio_tipo_plan_presupuesto set presupuesto = @valor1 - @valor2 where id_laboratorio=@id_laboratorio and id_tipo_plan=@id_tipo_plan	
GO


create view vta_labortorio_tipo_plan_presupuesto as
select L.*, P.id_tipo_plan, P.presupuesto, T.fecha_creacion as fecha_creacion_tipo_plan, 
T.usuario_creacion as usuario_creacion_tipo_plan, T.nombre, T.cedula, T.coordinador, T.mail_responsable, T.estado as estado_tipo_plan 
from (tbl_tipo_plan as T with (nolock) left outer join tbl_labortorio_tipo_plan_presupuesto as P with (nolock) on P.id_tipo_plan=T.id_tipo_plan) 
left outer join tbl_laboratorio as L with (nolock) on L.id_laboratorio=P.id_laboratorio
 
go


CREATE TABLE tbl_plan_mercadeo(
 id_plan_mercadeo bigint identity(1,1) primary key,
 sec_tipo_plan int not null,
 id_tipo_plan varchar(10) not null references tbl_tipo_plan(id_tipo_plan),
 plan_mercadeo varchar(80) not null,
 fecha_creacion datetime not null default getdate(),
 fecha_terminacion datetime,
 usuario_creacion varchar(30) not null,
 plan_completo bit default 0,
 
 tipo_alcance char(1) default '0',  /* 0=farmacias,  1=distribucion */
 tipo_alcance_de char(1) default 'm', /*  i=inauguracion	m=mercadeo(defecto)		c=convenciones   */
 fecha_ini datetime,    /* FARMACIA: para promedio de ventas, inauguracion=eval_fecha_ini.  DISTRIBUCION: facturas vendidas  */
 fecha_fin datetime,	/* FARMACIA: para promedio de ventas, inauguracion=eval_fecha_fin.  DISTRIBUCION: facturas vendidas  */
 promedio_ventas numeric(19,2),   /* FARMACIA: promedio de ventas, inauguracion=valor_venta distribucion=valor a comparar.  DISTRIBUCION: sumatoria de facturas  */
 tipo_dist_gasto char(1) default 'e', /* tipo de asignacion de presupuesto en farmacias  */
 proyeccion_ventas numeric(19,2),
 fecha_ini_averificar datetime,
 fecha_fin_averificar datetime,

 premio varchar(80),
 adjuntos text,

 mecanica_tipo char(1) default 't',	/*	t=texto		o=operaciones	*/
 mecanica text,	/*	si es de texto	*/
 aplica_prom_p_v char(1) default 'p',	/*	operaciones (p=promociones	c=convenios)		*/
 ope_fecha_ini datetime,	/*	operaciones	*/
 ope_fecha_fin datetime,	/*	operaciones	*/


 registro_operaciones bit default 0,
 aprobada_operaciones bit default 0,
 motivo_rechazo_operacion text,

 aprobado_mark_vent bit default 0,
 aprobado_comercial bit default 0,
 motivo_rechazo text,

 estado int default 1, /* 1=creados		2=rechazado marketing	3=aprobados operaciones		4=autorizacion marketing	
						  5=autorizacion ventas		6=autorizacion comercial	7=rechazado ventas	8=rechazado ventas
						  9=terminados  10=Anulados*/
 fecha_aprobacion datetime,  /* fecha de probacion del dep. comercial */
 fecha_cierre datetime,
 abierto bit default 1
)
GO




CREATE TABLE tbl_plan_mercadeo_farmacia(
	id_plan_mercadeo bigint not null references tbl_plan_mercadeo(id_plan_mercadeo),
	oficina varchar(3) not null,
	nombre varchar(60),
	p_ventas numeric(19,2) default 0,
	p_crecimiento numeric(5,2) default 0,
	proy_ventas numeric(19,2) default 0, 
	p_gasto numeric(5,2) default 0,
	primary key (id_plan_mercadeo, oficina)
)
go

CREATE TABLE tbl_plan_mercadeo_producto(
	id_plan_mercadeo bigint not null references tbl_plan_mercadeo(id_plan_mercadeo),
	codigo varchar(15) not null,
	descripcion varchar(60),
	primary key (id_plan_mercadeo, codigo)
)
go


CREATE TABLE tbl_plan_mercadeo_producto_filtro(
	id_plan_mercadeo_producto_filtro bigint identity(1,1) primary key,
	id_plan_mercadeo bigint not null references tbl_plan_mercadeo(id_plan_mercadeo),
	cod_nivel int,
	desc_nivel varchar(80),
	clase varchar(10),
	desc_clase varchar(40),
	linea varchar(10),
	desc_linea varchar(40),
)
go

CREATE TABLE tbl_plan_mercadeo_laboratorio(
	id_plan_mercadeo bigint not null references tbl_plan_mercadeo(id_plan_mercadeo),
	id_tipo_plan varchar(10) not null references tbl_tipo_plan(id_tipo_plan),
	id_laboratorio int not null references tbl_laboratorio(id_laboratorio),
	monto numeric(13,2),
	primary key (id_plan_mercadeo, id_laboratorio)
)
go

/*CREATE TRIGGER trg_actualizaSaldo ON tbl_plan_mercadeo_laboratorio AFTER INSERT, update AS
declare @saldo1 numeric(13, 2), @id_laboratorio int, @id_tipo_plan varchar 
	select @id_laboratorio = I.id_laboratorio, @id_tipo_plan=id_tipo_plan from inserted as I
	select @saldo1 = sum(monto) from tbl_plan_mercadeo_laboratorio where id_laboratorio=@id_laboratorio and id_tipo_plan=@id_tipo_plan 
	--update tbl_labortorio_tipo_plan_presupuesto set saldo=presupuesto-@saldo1 where id_laboratorio=@id_laboratorio and id_tipo_plan=@id_tipo_plan 
	--update tbl_laboratorio set saldo=monto-@saldo1 where id_laboratorio=@id_laboratorio	
GO*/


CREATE TABLE tbl_plan_mercadeo_vendedor(
	id_plan_mercadeo bigint not null references tbl_plan_mercadeo(id_plan_mercadeo),
	codigo_vendedor varchar(10) not null,
	nombre_vendedor varchar(40),
	primary key (id_plan_mercadeo, codigo_vendedor)
)
go

CREATE TABLE tbl_plan_mercadeo_cliente(
	id_plan_mercadeo bigint not null references tbl_plan_mercadeo(id_plan_mercadeo),
	numero_idcliente varchar(20) not null,
	nombre_comercial varchar(70),
	primary key (id_plan_mercadeo, numero_idcliente)
)
go


CREATE TABLE tbl_plan_mercadeo_proveedor(
	id_plan_mercadeo bigint not null references tbl_plan_mercadeo(id_plan_mercadeo),
	numero_idproveedor varchar(20) not null,
	nombre_comercial varchar(70),
	num_formulario varchar(30),
	monto numeric(13,2),
	fecha_registro datetime not null,
	plazo_confirmacion datetime,
	confirmado bit default 0,
	eliminado bit default 0,
	fecha_confirmacion datetime,
	primary key (id_plan_mercadeo, numero_idproveedor, num_formulario)
)
go


CREATE TABLE tbl_plan_mercadeo_adjunto(
	id_plan_mercadeo_adjunto bigint identity(1,1) primary key,
	id_plan_mercadeo bigint references tbl_plan_mercadeo(id_plan_mercadeo),
	archivo varchar(80) not null,
	descripcion text not null
)
go


CREATE TABLE tbl_estrategia(
	id_estrategia bigint identity(1,1) primary key,
	id_plan_mercadeo bigint not null references tbl_plan_mercadeo(id_plan_mercadeo),
	fecha_creacion_est datetime default getdate(),
	estrategia varchar(60),
	concepto text,
	tactica text
)
go



create table tbl_actividad_tipo(
	id_actividad_tipo int identity(1,1) primary key,
	id_db_externa varchar(10) not null,
	concepto varchar(160) not null,
	monto numeric(13, 2) default 0,
	revisada bit default 0
);
insert into tbl_actividad_tipo(id_db_externa, concepto, monto) values('1', 'Payasos', 50);
insert into tbl_actividad_tipo(id_db_externa, concepto, monto) values('2', 'Zanqueros', 50);
insert into tbl_actividad_tipo(id_db_externa, concepto, monto) values('3', 'Animadores', 50);
insert into tbl_actividad_tipo(id_db_externa, concepto, monto) values('4', 'Algodón de azúcar', 50);
insert into tbl_actividad_tipo(id_db_externa, concepto, monto) values('5', 'Crispetas', 50);
insert into tbl_actividad_tipo(id_db_externa, concepto, monto) values('6', 'Otros', 50);



CREATE TABLE tbl_actividad(
	id_actividad bigint identity(1,1) primary key,
	id_actividad_padre bigint default null,
	id_estrategia bigint not null references tbl_estrategia(id_estrategia),
	tipo_pago char(1) default 'i', /* f=en farmacia		i=compras internas  */
	actividad text,
	monto numeric(13, 2) default 0,
	fecha_creacion_act datetime default getdate(),
	fecha_ini datetime,
	fecha_fin datetime,	
	usuario_seg varchar(30),
	responsable_seg varchar(80),	/*	empleado - opcional	*/
	observacion_seg text,
	usuario_eje varchar(30),
	responsable_eje varchar(80) not null,	/*	empleado obligatorio	*/
	eje_finalizada bit default 0,
	eje_finalizada_fecha datetime,
	pre_tipo char(1) not null,
	pre_proveedor varchar(80),
	pre_cantidad int default 0,
	pre_p_u	numeric(13, 2) default 0,
	pre_total numeric(13, 2) default 0,
	visto_operaciones bit default 0,
	visto_marketing bit default 0,
	visto_ventas bit default 0,
	visto_comercial bit default 0,
	pre_id_tipo_plan_cuenta int references tbl_tipo_plan_cuenta(id_tipo_plan_cuenta)
)
go


CREATE TRIGGER trg_actualizaTerminoPlanMercadeo ON tbl_actividad AFTER INSERT, UPDATE, DELETE AS
declare @id_estrategia bigint, @id_plan_mercadeo bigint, @fecha_termino datetime
	select @id_estrategia = I.id_estrategia from inserted as I
	select @id_plan_mercadeo = id_plan_mercadeo from tbl_estrategia where id_estrategia=@id_estrategia

	select @fecha_termino = max(fecha_fin) from tbl_actividad where id_estrategia in (select id_estrategia from tbl_estrategia where id_plan_mercadeo=@id_plan_mercadeo)

	update tbl_plan_mercadeo set fecha_terminacion=@fecha_termino where id_plan_mercadeo=@id_plan_mercadeo
GO




CREATE TABLE tbl_actividad_cronograma(
	id_actividad_cronograma bigint identity(1,1) primary key,
	id_actividad bigint references tbl_actividad(id_actividad),
	fecha datetime not null,
	descripcion text not null
)
go

/*CREATE TABLE tbl_actividad_adjunto(
	id_actividad_adjunto bigint identity(1,1) primary key,
	id_actividad bigint references tbl_actividad(id_actividad),
	archivo varchar(80) not null,
	descripcion text not null
)
go*/


CREATE TABLE tbl_actividad_compra(
	id_actividad_compra bigint identity(1,1) primary key,
	id_actividad bigint references tbl_actividad(id_actividad),
	num_documento varchar(20) not null,
	ruc varchar(13) not null,
	proveedor varchar(160) not null,
	fecha datetime not null,
	cantidad numeric(13, 2) default 0,
	subtotal numeric(13, 2) default 0,
	iva numeric(13, 2) default 0,
	total numeric(16, 2) default 0,
	detalle text
)
go



CREATE TRIGGER trg_actualizaPresupuestoActividad ON tbl_actividad_compra AFTER INSERT, UPDATE, DELETE AS
declare @id_actividad bigint, @total numeric(16,2) 
	select @id_actividad = I.id_actividad from inserted as I
	select @total = sum(total) from tbl_actividad_compra where id_actividad = @id_actividad
	update tbl_actividad set monto=@total where id_actividad = @id_actividad
GO




