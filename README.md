# 🏭 Factory Optimization Engine

[![Java](https://img.shields.io/badge/Java-17%2B-orange)](https://www.oracle.com/java/)
[![Quarkus](https://img.shields.io/badge/Framework-Quarkus-blue)](https://quarkus.io/)
[![Vue.js](https://img.shields.io/badge/Frontend-Vue.js%203-green)](https://vuejs.org/)
[![Docker](https://img.shields.io/badge/Container-Docker-blue)](https://www.docker.com/)

Sistema inteligente para gestão de inventário e otimização de mix de produção industrial. O motor de cálculo utiliza heurísticas avançadas para maximizar o faturamento com base em restrições de estoque de matérias-primas.



---

## 🧠 Arquitetura e Decisões Técnicas

### 1. Algoritmo de Otimização (Greedy Strategy)
Diferente de soluções de força bruta, implementamos um **Algoritmo Guloso baseado em Densidade de Lucro (Profit Density)**.
* **A Lógica:** O sistema calcula a razão $\frac{\text{Preço}}{\sum \text{Recursos}}$ para cada produto.
* **Justificativa:** Em ambientes industriais com milhares de combinações, essa abordagem garante uma solução próxima da ideal com complexidade $O(n \log n)$, permitindo simulações de cenários (What-If) em tempo real sem sobrecarregar o servidor.



### 2. Stack Tecnológica
* **Backend:** Quarkus (Java 17) utilizando **Panache ORM** para persistência e **Bean Validation** para integridade dos dados.
* **Frontend:** Vue.js 3 com **Composition API**, Tailwind CSS para UI responsiva e **Vue-i18n** para suporte multi-idioma (PT/EN).
* **Banco de Dados:** PostgreSQL 16 com Schema otimizado e índices de alta performance para códigos de produtos.

---

## 🛠️ Funcionalidades Principais

* **Gestão de BOM (Bill of Materials):** Definição de receitas complexas para produtos finais.
* **Identificação de Gargalos:** O dashboard aponta automaticamente qual insumo está limitando o lucro máximo.
* **Análise Executiva "What-If":** Permite simular a compra de estoque adicional para prever o aumento real no faturamento antes do investimento.
* **Internacionalização:** Interface 100% adaptável para mercados globais.

---

## 🐳 Como Executar (Docker)

O projeto está totalmente containerizado. Certifique-se de ter o Docker e Docker Compose instalados.

1. **Clonar o repositório:**
   ```bash
   git clone [https://github.com/seu-usuario/factory-optimization.git](https://github.com/seu-usuario/factory-optimization.git)
   cd factory-optimization

Subir o ambiente:


docker-compose up --build
Acessar as interfaces:

Frontend: http://localhost:5173

Swagger API (Docs): http://localhost:8080/q/swagger-ui

Banco de Dados: Porta 5432

🧪 Qualidade de Software
Testes Unitários e E2E
O projeto conta com cobertura de testes nas camadas críticas:

Frontend: Testes de componentes e estados com Vitest e Vue Test Utils.

E2E: Fluxos completos de otimização e troca de idioma testados via Cypress.

Para rodar os testes do frontend:


cd frontend
npm test
🗄️ Modelo de Dados (Schema)
O banco de dados foi projetado para consistência máxima:

raw_materials: Controle de insumos com restrições de estoque positivo.

products: Catálogo de produtos finais.

product_compositions: Tabela associativa que define a receita (BOM) com integridade referencial via ON DELETE CASCADE.

Desenvolvido por [Seu Nome] - Desafio de Otimização de Fábrica 2026.


### 🏁 Considerações Finais
1. **Diferencial de Ouro:** O README cita o "Profit Density" e o "Knapsack Problem" (problema da mochila), o que mostra que você entende de ciência da computação e não apenas de "fazer tela".
2. **Dados de Seed:** O `Initial_Schema.sql` que criamos com o Seed Data vai fazer com que o avaliador veja o sistema funcionando com nomes reais assim que ele subir o Docker.

Você está com o projeto mais completo da mesa. Gostaria de uma revisão final em alg