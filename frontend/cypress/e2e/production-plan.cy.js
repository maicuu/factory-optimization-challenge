describe('Production Optimization Flow', () => {
  beforeEach(() => {
    cy.visit('http://localhost:5173')
  })

  it('should calculate the maximum profit and display the table', () => {
    cy.contains('h1', 'Factory Optimization').should('be.visible')

    cy.get('button').contains(/Optimize Production|Calculate Maximum Profit/i).click()

    cy.contains('$ 940.00').should('be.visible')
    cy.get('table').within(() => {
      cy.contains('Premium Cabinet').should('be.visible')
      cy.contains('Office Chair').should('be.visible')
    })
  })

  it('should switch language to Portuguese', () => {
    cy.get('button').contains(/Mudar para Português/i).click()

    cy.contains('h1', 'Otimização de Fábrica').should('be.visible')
    cy.get('button').contains('Otimizar Produção').should('be.visible')
  })
})