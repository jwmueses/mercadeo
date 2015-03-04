use PlanMercadeo
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


