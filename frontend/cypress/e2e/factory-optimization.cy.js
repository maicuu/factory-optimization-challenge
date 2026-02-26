describe('Robô da Fábrica - Teste End-to-End', () => {

  it('Deve cadastrar o cenário da Fábrica de Móveis e rodar a Otimização', () => {
    
    cy.visit('http://localhost:5173')
    
    // ==========================================
    //  PASSO 1: CADASTRAR INSUMOS
    // ==========================================
    cy.contains(/Materials|Insumos/i).click()

    const addMaterial = (code, name, unit, stock) => {
      cy.get('input').eq(0).clear().type(code)
      cy.get('input').eq(1).clear().type(name)
      cy.get('select').select(unit)
      cy.get('input[type="number"]').clear().type(stock)
      
      cy.contains(/Save Material|Salvar Insumo/i).click()
      cy.contains(code).should('be.visible')
      cy.wait(500) 
    }

    addMaterial('RM-WOOD', 'Oak Wood', 'kg', '100')
    addMaterial('RM-STEEL', 'Carbon Steel', 'kg', '40')
    addMaterial('RM-SCRW', 'Premium Screws', 'un', '500')


    // ==========================================
    //  PASSO 2: CADASTRAR PRODUTOS 
    // ==========================================
    cy.contains(/Products|Produtos/i).click()

    const addProduct = (code, name, price, ingredients) => {
      cy.get('input[placeholder="PRD-XXXX"]').clear().type(code)
      cy.get('input').eq(1).clear().type(name) 
      cy.get('input[type="number"]').eq(0).clear().type(price) 

      ingredients.forEach(ing => {
        cy.get('select').contains(ing.name).then($option => {
          cy.get('select').select($option.val())
        })
        cy.get('input[type="number"]').eq(1).clear().type(ing.qty) 
        cy.contains(/Add to Recipe|Adicionar/i).click()
      })

      cy.contains(/Save Complete Product|Salvar Produto/i).click()
      cy.contains(code).should('be.visible')
      cy.wait(500) 
    }

    addProduct('PRD-DESK', 'Luxury Desk', '800', [
      { name: 'Oak Wood', qty: '20' },
      { name: 'Carbon Steel', qty: '20' },
      { name: 'Premium Screws', qty: '50' }
    ])

    addProduct('PRD-CHAIR', 'Minimalist Chair', '250', [
      { name: 'Oak Wood', qty: '4' },
      { name: 'Carbon Steel', qty: '5' },
      { name: 'Premium Screws', qty: '10' }
    ])


    // ==========================================
    // PASSO 3: O GRANDE FINAL
    // ==========================================
    cy.contains(/Dashboard|Painel/i).click()
    
    cy.contains(/Optimize Production|Otimizar Produção/i).click()

    
    cy.contains('2000.00').should('be.visible')
    
    cy.contains('Carbon Steel').should('be.visible')

    
    cy.contains(/Simulate|Simular Cenário/i).click()
    
    
    cy.contains('2250.00').should('be.visible')
  })
})