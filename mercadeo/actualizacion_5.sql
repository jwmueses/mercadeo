/*use EasySeguridad
go

insert into Transacciones(Aplicacion, Modulo, Transaccion, Descripcion, Link, Panel, TipoMenu, InfoXML, InfoXML_Depende_BCO, Restringido, Codigo_NivelTres, Desc_NivelTres, Indice_Transaccion) 
values('PLANMERC', 'MDPLANMERC', 'mer_ejecucion', 'Seguimiento de ejecución de actividades de planes de mercadeo', '', '', 'M', '', '', 0, '', '', 0);

insert into Atribuciones(NombreCorto, Aplicacion, Modulo, Transaccion, Habilitado) values('pmina', 'PLANMERC', 'MDPLANMERC', 'mer_ejecucion', 1);

*/


use PlanMercadeo
go


alter table tbl_tipo_plan add centro_costos_coordinador varchar(60)
go
alter table tbl_tipo_plan add auspicio_manual bit
go






alter table tbl_plan_mercadeo add nombre_solicitante varchar(160)
go
alter table tbl_plan_mercadeo add usuario_operaciones varchar(30)
go
alter table tbl_plan_mercadeo add fecha_operaciones datetime
go
alter table tbl_plan_mercadeo add usuario_mark_vent varchar(30)
go
alter table tbl_plan_mercadeo add fecha_mark_vent datetime
go
alter table tbl_plan_mercadeo add motivo_rechazo_mark_vent text
go
alter table tbl_plan_mercadeo add usuario_comercial varchar(30)
go
alter table tbl_plan_mercadeo add total_auspicio numeric(13, 2)
go
alter table tbl_plan_mercadeo add evalua_ventas bit
go
alter table tbl_plan_mercadeo add porcentaje_crecimiento numeric(5,2)
go
alter table tbl_plan_mercadeo add ventas_reales numeric(16,2)
go
alter table tbl_plan_mercadeo add porcentaje_cumplimiento numeric(5,2)
go
alter table tbl_plan_mercadeo add total_gasto numeric(16,2)
go
alter table tbl_plan_mercadeo add porcentaje_cumplimiento_gasto numeric(5,2)
go
alter table tbl_plan_mercadeo add fecha_anulacion datetime
go
alter table tbl_plan_mercadeo add motivo_anulacion text
go
alter table tbl_plan_mercadeo add centro_costos varchar(60)
go
update tbl_plan_mercadeo set total_auspicio=0
go


alter table tbl_plan_mercadeo_proveedor add fecha_confirmacion datetime
go

update tbl_plan_mercadeo_proveedor set fecha_confirmacion=plazo_confirmacion where confirmado=1
go



alter table tbl_actividad add eje_comentario text
go




alter table tbl_plan_mercadeo_farmacia add centro_costos varchar(60)
go
alter table tbl_plan_mercadeo_farmacia add p_ventas_reales numeric(16,2)
go
alter table tbl_plan_mercadeo_farmacia add porcentaje_cumplimiento numeric(5,2)
go
alter table tbl_plan_mercadeo_farmacia add auspicio numeric(16,2)
go
alter table tbl_plan_mercadeo_farmacia add gasto numeric(16,2)
go
--alter table tbl_plan_mercadeo_farmacia add gasto_actividad numeric(16,2)
--go
alter table tbl_plan_mercadeo_farmacia add porcentaje_cumplimiento_gasto numeric(5,2)
go

CREATE TRIGGER trg_actualizaCumplimientoFarmacia ON tbl_plan_mercadeo_farmacia INSTEAD OF UPDATE AS
declare @ventas_100 numeric(19,2), @p_ventas numeric(19,2), @p_crecimiento numeric(5,2)
begin
	select @p_ventas=I.p_ventas, @p_crecimiento=I.p_crecimiento from inserted as I
	select @ventas_100 = @p_ventas + (@p_ventas*@p_crecimiento/100)
	update tbl_plan_mercadeo_farmacia set nombre=inserted.nombre, p_ventas=inserted.p_ventas, p_crecimiento=inserted.p_crecimiento, 
	proy_ventas=inserted.proy_ventas, p_gasto=inserted.p_gasto, p_ventas_reales=inserted.p_ventas_reales, 
	porcentaje_cumplimiento = inserted.p_ventas_reales * 100 / @ventas_100, auspicio=inserted.auspicio, 
	gasto=inserted.gasto, porcentaje_cumplimiento_gasto=inserted.porcentaje_cumplimiento_gasto 
	from inserted, tbl_plan_mercadeo_farmacia as PF
	where PF.id_plan_mercadeo=inserted.id_plan_mercadeo and PF.oficina=inserted.oficina
end
GO




BEGIN TRANSACTION
SET QUOTED_IDENTIFIER ON
SET ARITHABORT ON
SET NUMERIC_ROUNDABORT OFF
SET CONCAT_NULL_YIELDS_NULL ON
SET ANSI_NULLS ON
SET ANSI_PADDING ON
SET ANSI_WARNINGS ON
COMMIT
BEGIN TRANSACTION
GO
EXECUTE sp_rename N'dbo.tbl_actividad_compra.subtotal', N'Tmp_base_12_1', 'COLUMN' 
GO
EXECUTE sp_rename N'dbo.tbl_actividad_compra.Tmp_base_12_1', N'base_12', 'COLUMN' 
GO
ALTER TABLE dbo.tbl_actividad_compra SET (LOCK_ESCALATION = TABLE)
GO
COMMIT


alter table tbl_actividad_compra add base_0 numeric(13,2)
go
alter table tbl_actividad_compra add fecha_ingreso date
go

update tbl_actividad_compra set base_0 = 0, fecha_ingreso=fecha
go



/*  Informacion de facturas para ser leidos por el sistema Easy Soft  */

create view vta_factura_compra as
select PM.sec_tipo_plan as num_plan_mercadeo, FG.ruc, FG.proveedor, FG.num_documento, CONVERT(VARCHAR, FG.fecha, 103) as fecha, 
TP.nombre as tipo_plan_mercadeo, PM.nombre_solicitante, FG.detalle, TPS.nombre as sucursal, PMF.nombre as farmacia, 
case when PM.evalua_ventas=1 then PMF.centro_costos else PM.centro_costos end as centro_costos, TPC.cuenta, TPC.descripcion as descripcion_cuenta, PMF.gasto 
from ((((((tbl_actividad_compra as FG with(nolock) inner join tbl_actividad as A with(nolock) on FG.id_actividad=A.id_actividad) 
inner join tbl_estrategia as E with(nolock) on E.id_estrategia=A.id_estrategia) 
inner join tbl_plan_mercadeo as PM with(nolock) on PM.id_plan_mercadeo=e.id_plan_mercadeo)
inner join tbl_tipo_plan as TP with(nolock) on TP.id_tipo_plan=PM.id_tipo_plan)
inner join tbl_tipo_plan_sucursal as TPS with(nolock) on TPS.id_tipo_plan=TP.id_tipo_plan)
inner join tbl_plan_mercadeo_farmacia as PMF with(nolock) on PMF.id_plan_mercadeo=PM.id_plan_mercadeo) 
inner join tbl_tipo_plan_cuenta as TPC on TPC.id_tipo_plan_cuenta=A.pre_id_tipo_plan_cuenta 
go



drop TRIGGER trg_actualizaPresupuestoActividad
go

CREATE TRIGGER trg_actualizaPresupuestoActividad ON tbl_actividad_compra AFTER INSERT, UPDATE, DELETE AS
declare @id_actividad bigint, @total numeric(16,2) 
	select @id_actividad = I.id_actividad from inserted as I
	select @total = sum(total) from tbl_actividad_compra where id_actividad = @id_actividad
	update tbl_actividad set monto=@total where id_actividad = @id_actividad
GO



