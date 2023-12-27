package com.jjcdutra.integrationtests.controller.cors.withjson

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.jjcdutra.integrationtests.TestConfigs
import com.jjcdutra.integrationtests.testcontainers.AbstractIntegrationTest
import com.jjcdutra.integrationtests.vo.PersonVO
import io.restassured.RestAssured.given
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.LogDetail
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonControllerCorsWithJson : AbstractIntegrationTest() {

    private lateinit var specification: RequestSpecification
    private lateinit var objectMapper: ObjectMapper
    private lateinit var personVO: PersonVO

    @BeforeAll
    fun setUp() {
        objectMapper = ObjectMapper()
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        personVO = PersonVO()
    }

    @Test
    @Order(1)
    fun createTest() {
        mockPerson()

        specification = RequestSpecBuilder()
            .addHeader(
                TestConfigs.HEADERS_PARAM_ORIGIN,
                TestConfigs.ORIGIN_LOCALHOST
            )
            .setBasePath("/api/person/v1")
            .setPort(TestConfigs.SERVER_PORT)
            .addFilter(RequestLoggingFilter(LogDetail.ALL))
            .addFilter(ResponseLoggingFilter(LogDetail.ALL))
            .build()

        val content = given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(personVO)
            .`when`()
            .post()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString()

        val createdPerson = objectMapper.readValue(content, PersonVO::class.java)

        assertNotNull(createdPerson.id)
        assertNotNull(createdPerson.firstName)
        assertNotNull(createdPerson.lastName)
        assertNotNull(createdPerson.address)
        assertNotNull(createdPerson.gender)

        assertTrue(createdPerson.id > 0)

        assertEquals("Nelson", createdPerson.firstName)
        assertEquals("Piquet", createdPerson.lastName)
        assertEquals("Brasilia, DF, Brasil", createdPerson.address)
        assertEquals("Male", createdPerson.gender)
    }

    private fun mockPerson() {
        personVO.firstName = "Nelson"
        personVO.lastName = "Piquet"
        personVO.address = "Brasilia, DF, Brasil"
        personVO.gender = "Male"
    }
}