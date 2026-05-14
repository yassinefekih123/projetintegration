describe('Orders list and detail view', () => {
  it('displays orders list and navigates to order detail', () => {
    const orderId = '33333333-3333-3333-3333-333333333333'

    const orders = [
      { id: orderId, totalPrice: 19.99, status: 'PLACED', createdAt: new Date().toISOString() }
    ]

    const orderDetail = {
      id: orderId,
      totalPrice: 19.99,
      status: 'PLACED',
      createdAt: new Date().toISOString(),
      items: [{ accessoryName: 'Screen Protector', quantity: 1, priceSnapshot: 19.99 }]
    }

    cy.intercept('GET', '/api/v1/orders', { statusCode: 200, body: orders }).as('getOrders')
    cy.intercept('GET', `/api/v1/orders/${orderId}`, { statusCode: 200, body: orderDetail }).as('getOrder')

    cy.visit('/orders')
    cy.wait('@getOrders')

    cy.contains(orderId).should('exist')
    cy.contains(orderId).click()

    cy.wait('@getOrder')
    cy.contains(/status/i).should('exist')
    cy.contains(/placed|PLACED/i).should('exist')
    cy.contains('Screen Protector').should('exist')
  })
})
