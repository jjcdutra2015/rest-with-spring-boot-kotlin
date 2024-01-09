package com.jjcdutra.integrationtests.controller.withjson

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.jjcdutra.data.vo.v1.AccountCredentialsVO
import com.jjcdutra.data.vo.v1.TokenVO
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
class PersonControllerTest : AbstractIntegrationTest() {

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
    @Order(0)
    fun authorization() {
        val user = AccountCredentialsVO(
            username = "leandro",
            password = "admin123"
        )

        val token = given()
            .basePath("/auth/signin")
            .port(TestConfigs.SERVER_PORT)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(user)
            .`when`()
            .post()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(TokenVO::class.java)
            .accessToken!!

        specification = RequestSpecBuilder()
            .addHeader(
                "Authorization",
                "Bearer $token"
            )
            .setBasePath("/api/person/v1")
            .setPort(TestConfigs.SERVER_PORT)
            .addFilter(RequestLoggingFilter(LogDetail.ALL))
            .addFilter(ResponseLoggingFilter(LogDetail.ALL))
            .build()
    }

    @Test
    @Order(1)
    fun createTest() {
        mockPerson()

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

        val item = objectMapper.readValue(content, PersonVO::class.java)
        personVO = item

        assertNotNull(item.id)
        assertNotNull(item.firstName)
        assertNotNull(item.lastName)
        assertNotNull(item.address)
        assertNotNull(item.gender)

        assertTrue(item.id > 0)

        assertEquals("Nelson", item.firstName)
        assertEquals("Piquet", item.lastName)
        assertEquals("Brasilia, DF, Brasil", item.address)
        assertEquals("Male", item.gender)
        assertEquals(true, item.enabled)
    }

    @Test
    @Order(2)
    fun updateTest() {
        personVO.lastName = "Matthew Stallman"

        val content = given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(personVO)
            .`when`()
            .put()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString()

        val item = objectMapper.readValue(content, PersonVO::class.java)
        personVO = item

        assertNotNull(item.id)
        assertNotNull(item.firstName)
        assertNotNull(item.lastName)
        assertNotNull(item.address)
        assertNotNull(item.gender)

        assertEquals(personVO.id, item.id)

        assertEquals("Nelson", item.firstName)
        assertEquals("Matthew Stallman", item.lastName)
        assertEquals("Brasilia, DF, Brasil", item.address)
        assertEquals("Male", item.gender)
        assertEquals(true, item.enabled)
    }

    @Test
    @Order(3)
    fun findByIdTest() {
        val content = given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("id", personVO.id)
            .`when`()
            .get("{id}")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString()

        val item = objectMapper.readValue(content, PersonVO::class.java)
        personVO = item

        assertNotNull(item.id)
        assertNotNull(item.firstName)
        assertNotNull(item.lastName)
        assertNotNull(item.address)
        assertNotNull(item.gender)

        assertEquals(personVO.id, item.id)

        assertEquals("Nelson", item.firstName)
        assertEquals("Matthew Stallman", item.lastName)
        assertEquals("Brasilia, DF, Brasil", item.address)
        assertEquals("Male", item.gender)
        assertEquals(true, item.enabled)
    }

    @Test
    @Order(4)
    fun disablePersonByIdTest() {
        val content = given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("id", personVO.id)
            .`when`()
            .patch("{id}")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString()

        val item = objectMapper.readValue(content, PersonVO::class.java)
        personVO = item

        assertNotNull(item.id)
        assertNotNull(item.firstName)
        assertNotNull(item.lastName)
        assertNotNull(item.address)
        assertNotNull(item.gender)

        assertEquals(personVO.id, item.id)

        assertEquals("Nelson", item.firstName)
        assertEquals("Matthew Stallman", item.lastName)
        assertEquals("Brasilia, DF, Brasil", item.address)
        assertEquals("Male", item.gender)
        assertEquals(false, item.enabled)
    }

    @Test
    @Order(5)
    fun deleteTest() {
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("id", personVO.id)
            .`when`()
            .delete("{id}")
            .then()
            .statusCode(204)
    }

    @Test
    @Order(6)
    fun findAll() {
        val content = given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .`when`()
            .get()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString()

        val people = objectMapper.readValue(content, Array<PersonVO>::class.java)

        val item1 = people[0]

        assertNotNull(item1.id)
        assertNotNull(item1.firstName)
        assertNotNull(item1.lastName)
        assertNotNull(item1.address)
        assertNotNull(item1.gender)

        assertEquals("Ayrton", item1.firstName)
        assertEquals("Senna", item1.lastName)
        assertEquals("São Paulo", item1.address)
        assertEquals("Male", item1.gender)
        assertEquals(true, item1.enabled)

        val item2 = people[6]

        assertNotNull(item2.id)
        assertNotNull(item2.firstName)
        assertNotNull(item2.lastName)
        assertNotNull(item2.address)
        assertNotNull(item2.gender)

        assertEquals("Nikola", item2.firstName)
        assertEquals("Tesla", item2.lastName)
        assertEquals("Smiljan - Croatia", item2.address)
        assertEquals("Male", item2.gender)
        assertEquals(true, item2.enabled)
    }

    @Test
    @Order(7)
    fun findAllWithoutToken() {

        val specificationWithoutToken = RequestSpecBuilder()
            .setBasePath("/api/person/v1")
            .setPort(TestConfigs.SERVER_PORT)
            .addFilter(RequestLoggingFilter(LogDetail.ALL))
            .addFilter(ResponseLoggingFilter(LogDetail.ALL))
            .build()

        given()
            .spec(specificationWithoutToken)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .`when`()
            .get()
            .then()
            .statusCode(403)
            .extract()
            .body()
            .asString()
    }

    private fun mockPerson() {
        personVO.firstName = "Nelson"
        personVO.lastName = "Piquet"
        personVO.address = "Brasilia, DF, Brasil"
        personVO.gender = "Male"
        personVO.enabled = true
    }
}