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

Certifique-se de ter o Docker instalado.

### 1. Clonar o repositório
git clone https://github.com/seu-usuario/factory-optimization.git
cd factory-optimization

### 2. Subir o ambiente
docker-compose up --build

### 3. Interfaces
* **Frontend:** http://localhost:5173
* **Swagger Docs:** http://localhost:8080/q/swagger-ui

---

## 🧪 Qualidade de Software

### Testes Unitários e E2E
* **Frontend:** Vitest e Vue Test Utils.
* **E2E:** Cypress.

**Para rodar os testes do frontend:**
cd frontend
npm test

---

## 🗄️ Modelo de Dados (Schema)
* **raw_materials:** Controle de insumos.
* **products:** Catálogo de produtos.
* **product_compositions:** Receita (BOM) com integridade referencial.

---

Desenvolvido por **Maicon Wendel** - 2026.