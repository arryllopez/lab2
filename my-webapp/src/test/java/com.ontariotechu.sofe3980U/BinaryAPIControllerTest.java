package com.ontariotechu.sofe3980U;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@WebMvcTest(BinaryAPIController.class)
public class BinaryAPIControllerTest {

    @Autowired
    private MockMvc mvc;


    @Test
    public void add() throws Exception {
        this.mvc.perform(get("/add").param("operand1","111").param("operand2","1010"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("10001"));
    }
	@Test
    public void add2() throws Exception {
        this.mvc.perform(get("/add_json").param("operand1","111").param("operand2","1010"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(111))
			.andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(1010))
			.andExpect(MockMvcResultMatchers.jsonPath("$.result").value(10001))
			.andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("add"));
    }

	// 3 additional test cases for the binary API service

	@Test
	public void addWithZero() throws Exception {
		this.mvc.perform(get("/add").param("operand1","1010").param("operand2","0"))
			.andExpect(status().isOk())
			.andExpect(content().string("1010"));
	}

	@Test
	public void addWithCarry() throws Exception {
		this.mvc.perform(get("/add").param("operand1","1111").param("operand2","1"))
			.andExpect(status().isOk())
			.andExpect(content().string("10000"));
	}

	@Test
	public void addBothZeros() throws Exception {
		this.mvc.perform(get("/add").param("operand1","0").param("operand2","0"))
			.andExpect(status().isOk())
			.andExpect(content().string("0"));
	}

	// Test cases for multiply operation

	@Test
	public void multiply() throws Exception {
		this.mvc.perform(get("/multiply").param("operand1","10").param("operand2","11"))
			.andExpect(status().isOk())
			.andExpect(content().string("110"));
	}

	@Test
	public void multiplyJSON() throws Exception {
		this.mvc.perform(get("/multiply_json").param("operand1","10").param("operand2","11"))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(10))
			.andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(11))
			.andExpect(MockMvcResultMatchers.jsonPath("$.result").value(110))
			.andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("multiply"));
	}

	@Test
	public void multiplyWithZero() throws Exception {
		this.mvc.perform(get("/multiply").param("operand1","0").param("operand2","101"))
			.andExpect(status().isOk())
			.andExpect(content().string("0"));
	}

	// Test cases for AND operation

	@Test
	public void and() throws Exception {
		this.mvc.perform(get("/and").param("operand1","1010").param("operand2","1100"))
			.andExpect(status().isOk())
			.andExpect(content().string("1000"));
	}

	@Test
	public void andJSON() throws Exception {
		this.mvc.perform(get("/and_json").param("operand1","110").param("operand2","010"))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(110))
			.andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(10))
			.andExpect(MockMvcResultMatchers.jsonPath("$.result").value(10))
			.andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("and"));
	}

	@Test
	public void andBothOnes() throws Exception {
		this.mvc.perform(get("/and").param("operand1","1").param("operand2","1"))
			.andExpect(status().isOk())
			.andExpect(content().string("1"));
	}

	// Test cases for OR operation

	@Test
	public void or() throws Exception {
		this.mvc.perform(get("/or").param("operand1","101").param("operand2","010"))
			.andExpect(status().isOk())
			.andExpect(content().string("111"));
	}

	@Test
	public void orJSON() throws Exception {
		this.mvc.perform(get("/or_json").param("operand1","1010").param("operand2","1100"))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(1010))
			.andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(1100))
			.andExpect(MockMvcResultMatchers.jsonPath("$.result").value(1110))
			.andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("or"));
	}

	@Test
	public void orBothZeros() throws Exception {
		this.mvc.perform(get("/or").param("operand1","0").param("operand2","0"))
			.andExpect(status().isOk())
			.andExpect(content().string("0"));
	}

	// Lab 1 unit test cases for OR operation

	@Test
	public void or1() {
		Binary b1 = new Binary("101");
		Binary b2 = new Binary("010");
		Binary result = Binary.or(b1, b2);
		assertEquals("111", result.getValue());
	}

	@Test
	public void or2() {
		Binary b1 = new Binary("1");
		Binary b2 = new Binary("0");
		Binary result = Binary.or(b1, b2);
		assertEquals("1", result.getValue());
	}

	@Test
	public void or3() {
		Binary b1 = new Binary("0");
		Binary b2 = new Binary("0");
		Binary result = Binary.or(b1, b2);
		assertEquals("0", result.getValue());
	}

	// Lab 1 unit test cases for AND operation

	@Test
	public void and1() {
		Binary b1 = new Binary("101");
		Binary b2 = new Binary("010");
		Binary result = Binary.and(b1, b2);
		assertEquals("0", result.getValue());
	}

	@Test
	public void and2() {
		Binary b1 = new Binary("110");
		Binary b2 = new Binary("010");
		Binary result = Binary.and(b1, b2);
		assertEquals("10", result.getValue());
	}

	@Test
	public void and3() {
		Binary b1 = new Binary("1");
		Binary b2 = new Binary("1");
		Binary result = Binary.and(b1, b2);
		assertEquals("1", result.getValue());
	}

	// Lab 1 unit test cases for multiply operation

	@Test
	public void multiply1() {
		Binary b1 = new Binary("10");
		Binary b2 = new Binary("11");
		Binary result = Binary.multiply(b1, b2);
		assertEquals("110", result.getValue());
	}

	@Test
	public void multiply2() {
		Binary b1 = new Binary("0");
		Binary b2 = new Binary("101");
		Binary result = Binary.multiply(b1, b2);
		assertEquals("0", result.getValue());
	}

	@Test
	public void multiply3() {
		Binary b1 = new Binary("1000");
		Binary b2 = new Binary("10");
		Binary result = Binary.multiply(b1, b2);
		assertEquals("10000", result.getValue());
	}

}