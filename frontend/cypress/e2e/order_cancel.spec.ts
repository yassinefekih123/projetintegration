describe('Order cancellation flow', () => {
  it('shows confirm modal, calls cancel API, and displays toast', () => {
    const orderId = '11111111-1111-1111-1111-111111111111'

    const orderBefore = {
      id: orderId,
      totalPrice: 49.99,
      status: 'PLACED',
      createdAt: new Date().toISOString(),
      items: [{ accessoryName: 'Phone Case', quantity: 1, priceSnapshot: 49.99 }],
    }

    const orderAfter = { ...orderBefore, status: 'CANCELLED' }

    cy.intercept('GET', `/api/v1/orders/${orderId}`, {
      statusCode: 200,
      body: orderBefore,
    }).as('getOrder')

    cy.intercept('POST', `/api/v1/orders/${orderId}/cancel`, {
      statusCode: 200,
      body: orderAfter,
    }).as('cancelOrder')

    cy.visit(`/orders/${orderId}`)
    cy.wait('@getOrder')

    cy.contains(/cancel order/i).click()

    // Confirm modal should appear; click the confirm button
    cy.get('button').contains(/confirm/i).click()

    cy.wait('@cancelOrder')

    // Expect toast with cancellation message (app uses toast after cancel)
    cy.contains(/cancelled|order cancelled/i).should('exist')

    // Expect status text on page updated to CANCELLED
    cy.contains(/status/i).should('exist')
    cy.contains(/cancelled/i).should('exist')
  })
})
