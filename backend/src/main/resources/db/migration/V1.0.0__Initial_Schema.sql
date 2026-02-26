-- =============================================================================
-- SCRIPT DE DEFINIÇÃO DE SCHEMA - FACTORY OPTIMIZATION
-- Foco: Integridade Referencial, Performance e Precisão Decimal
-- =============================================================================

-- 1. Tabela de Matérias-Primas (Insumos)
CREATE TABLE raw_materials (
    id             BIGSERIAL PRIMARY KEY,
    code           VARCHAR(50) UNIQUE NOT NULL,
    name           VARCHAR(255) NOT NULL,
    -- Usamos NUMERIC para garantir precisão em cálculos de pesagem/medida
    stock_quantity NUMERIC(15, 4) NOT NULL DEFAULT 0.0000,
    unit           VARCHAR(10) NOT NULL DEFAULT 'un',
    
    CONSTRAINT ck_stock_positive CHECK (stock_quantity >= 0)
);

-- 2. Tabela de Produtos (Catálogo)
CREATE TABLE products (
    id    BIGSERIAL PRIMARY KEY,
    code  VARCHAR(50) UNIQUE NOT NULL,
    name  VARCHAR(255) NOT NULL,
    price NUMERIC(15, 2) NOT NULL,
    
    CONSTRAINT ck_price_positive CHECK (price > 0)
);

-- 3. Tabela de Composição (Bill of Materials - BOM)
-- Gerencia a relação Many-to-Many entre Produtos e Insumos
CREATE TABLE product_compositions (
    id              BIGSERIAL PRIMARY KEY,
    product_id      BIGINT NOT NULL,
    raw_material_id BIGINT NOT NULL,
    quantity_needed NUMERIC(15, 4) NOT NULL,
    
    CONSTRAINT fk_product 
        FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    CONSTRAINT fk_material 
        FOREIGN KEY (raw_material_id) REFERENCES raw_materials(id),
    CONSTRAINT ck_quantity_needed_positive 
        CHECK (quantity_needed > 0),
    -- Garante que um produto não tenha o mesmo insumo duplicado na receita
    CONSTRAINT uk_product_material UNIQUE (product_id, raw_material_id)
);

-- 4. Otimização de busca para integrações e consultas de código
CREATE INDEX idx_raw_materials_code ON raw_materials(code);
CREATE INDEX idx_products_code ON products(code);

-- =============================================================================
-- DATA SEED (Cenário de Teste para Otimização)
-- Insumos críticos para validação do Algoritmo de Mochila/Gargalo
-- =============================================================================

-- Cadastro de Insumos base
INSERT INTO raw_materials (code, name, stock_quantity, unit) VALUES 
('RM-STEEL', 'Aço Carbono Industrial', 50.0000, 'kg'),
('RM-WOOD', 'Placa de MDF 15mm', 120.0000, 'm²');

-- Cadastro de Produtos (Exemplo de disputa de recursos)
INSERT INTO products (code, name, price) VALUES 
('PRD-DESK', 'Escrivaninha Office v2', 450.00),
('PRD-CHAIR', 'Cadeira Ergometria Basic', 180.00);

-- Composição da Escrivaninha (Consome muito aço e madeira)
INSERT INTO product_compositions (product_id, raw_material_id, quantity_needed) VALUES 
(1, 1, 12.5000), -- 12.5kg de aço
(1, 2, 8.0000);   -- 8m² de madeira

-- Composição da Cadeira (Otimizada para giro de estoque)
INSERT INTO product_compositions (product_id, raw_material_id, quantity_needed) VALUES 
(2, 1, 3.2000),  -- 3.2kg de aço
(2, 2, 1.5000);  -- 1.5m² de madeira