package com.supplychain.bcc

import com.supplychain.bcc.contractstates.ProductState
import org.testng.annotations.Listeners
import org.testng.annotations.Test
import java.util.*
import kotlin.test.assertEquals

@Listeners(AgentListener::class)

class UpdateProductTests : SupplyChainTests() {

    @Test
    fun `Update a Product`() {
        val trackingID = UUID.randomUUID()
        val updateRequest = CreateProductRequest("New Name", "UnHealth", mapOf(), trackingID, listOf("BB"))
        createProduct(a, CreateProductRequest("Product Name", "Health", mapOf(), trackingID, listOf("BB")))

        updateProduct(a, trackingID, updateRequest)

        //vaultCheck
        val state = a.transaction { a.services.vaultService.queryBy(ProductState::class.java).states.first().state }

        assertEquals("New Name", state.data.productName)
        assertEquals("UnHealth", state.data.health)

        println("The ProductState was updated successfully")
    }

}