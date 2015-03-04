use PlanMercadeo
go

alter table tbl_plan_mercadeo add fecha_terminacion datetime;

update tbl_plan_mercadeo set fecha_terminacion = fecha_fin;

drop TRIGGER trg_actualizaTerminoPlanMercadeo
go

CREATE TRIGGER trg_actualizaTerminoPlanMercadeo ON tbl_actividad AFTER INSERT, UPDATE, DELETE AS
declare @id_estrategia bigint, @id_plan_mercadeo bigint, @fecha_termino datetime
	select @id_estrategia = I.id_estrategia from inserted as I
	select @id_plan_mercadeo = id_plan_mercadeo from tbl_estrategia where id_estrategia=@id_estrategia

	select @fecha_termino = max(fecha_fin) from tbl_actividad where id_estrategia in (select id_estrategia from tbl_estrategia where id_plan_mercadeo=@id_plan_mercadeo)

	update tbl_plan_mercadeo set fecha_terminacion=@fecha_termino where id_plan_mercadeo=@id_plan_mercadeo
go

