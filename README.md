# 🏭 Factory Optimization Engine

[![Java](https://img.shields.io/badge/Java-17%2B-orange)](https://www.oracle.com/java/)
[![Quarkus](https://img.shields.io/badge/Framework-Quarkus-blue)](https://quarkus.io/)
[![Vue.js](https://img.shields.io/badge/Frontend-Vue.js%203-green)](https://vuejs.org/)
[![Docker](https://img.shields.io/badge/Container-Docker-blue)](https://www.docker.com/)

Sistema inteligente para gestão de inventário e otimização de mix de produção industrial. O motor de cálculo utiliza heurísticas avançadas para maximizar o faturamento com base em restrições de estoque de matérias-primas.

---

## 🧠 Arquitetura e Decisões Técnicas

### 1. Algoritmo de Otimização (Greedy Strategy)
Implementamos um **Algoritmo Guloso baseado em Densidade de Lucro (Profit Density)** para resolver o *Knapsack Problem*.

* **Lógica:** O sistema calcula a razão entre o preço e a soma de recursos necessários para cada produto.
* **Justificativa:** Complexidade O(n log n), ideal para simulações instantâneas.

### 2. Stack Tecnológica
* **Backend:** Quarkus (Java 17), Panache ORM.
* **Frontend:** Vue.js 3 (Composition API), Tailwind CSS.
* **Banco de Dados:** PostgreSQL 16.

---

## 🛠️ Funcionalidades Principais

* **Gestão de BOM:** Definição de receitas complexas para produtos finais.
* **Identificação de Gargalos:** Dashboard aponta o insumo limitante.
* **Análise "What-If":** Simulação de ROI sobre compra de estoque adicional.

---

## 🐳 Como Executar (Docker)

O projeto está totalmente containerizado. Certifique-se de ter o Docker e Docker Compose instalados.

### 1. Clonar o repositório:
```bash
git clone [https://github.com/seu-usuario/factory-optimization.git](https://github.com/seu-usuario/factory-optimization.git)
cd factory-optimization
2. Subir o ambiente:
Bash
docker-compose up --build
3. Acessar as interfaces:
Frontend: http://localhost:5173

---

## 🧪 Qualidade de Software

Bash
cd frontend
npm test

---

## 🗄️ Modelo de Dados (Schema)
* **raw_materials:** Controle de insumos.
* **products:** Catálogo de produtos.
* **product_compositions:** Receita (BOM) com integridade referencial.

---

🏁 Considerações Finais
Diferencial de Ouro: O projeto não apenas gerencia dados, mas aplica conceitos de Ciência da Computação (Problema da Mochila) para resolver problemas reais de logística e produção.

Seed Data: O banco de dados já inicializa com dados reais para permitir testes imediatos das funcionalidades de otimização.

Desenvolvido por Maicon Wendel - Desafio de Otimização de Fábrica 2026.
