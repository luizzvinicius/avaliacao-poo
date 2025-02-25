CREATE TABLE IF NOT EXISTS cliente(
	"id" serial,
	"nome" varchar(100) constraint NN_cliente_nome not null,
	"telefone" varchar(11) unique constraint NN_cliente_tel not null,
	"cpf" varchar(11) unique,
	"data_nascimento" date,
	"cnpj" varchar(14) unique,
	"nome_responsavel" varchar(100),
	primary key (id)
);
--
create table if not exists produto(
	"id" serial,
	"nome" varchar(100) constraint NN_produto_nome not null,
	"preco" float8 constraint NN_produto_preco not null check(preco > 0),
	"quantidade" float8 constraint NN_produto_qtd not null check(quantidade >= 0),
	primary key (id)
);
--
create table if not exists pagamentos (
	"id" serial,
	"id_compra" int constraint NN_pagamentos_id_compra not null,
	"tipo" varchar(10) constraint NN_pagamentos_tipo not null,
	"valor_total" float8 constraint NN_pagamentos_valor not null check(valor_total > 0),
	primary key (id)
);
--
create table if not exists compras(
	"id" serial,
	"id_cliente" int constraint NN_pagamento_id_cliente not null,
	"id_pagamento" int constraint NN_pagamento_id_pag not null,
	"data_compra" date constraint NN_pagamento_data not null,
	primary key (id),
	foreign key (id_cliente) references cliente(id) on delete cascade,
	foreign key (id_pagamento) references pagamentos(id) on delete cascade
);
--
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE constraint_name = 'fk_pag_compras'
    ) THEN
        ALTER TABLE pagamentos ADD CONSTRAINT FK_pag_compras
        FOREIGN KEY (id_compra) REFERENCES compras(id) ON DELETE CASCADE;
    END IF;
END $$ LANGUAGE plpgsql;
--
create table if not exists item_venda (
	"id" serial,
	"id_compra" int constraint NN_item_venda_id_compra not null,
	"id_produto" int constraint NN_item_venda_id_prod not null,
	"preco" float8 constraint NN_item_venda_preco not null check(preco > 0),
	"quantidade" float8 constraint NN_item_venda_qtd not null check(quantidade > 0),
	primary key (id),
	foreign key (id_compra) references compras(id) on delete cascade,
	foreign key (id_produto) references produto(id) on delete cascade
);