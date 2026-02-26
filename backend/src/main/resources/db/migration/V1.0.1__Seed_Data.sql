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