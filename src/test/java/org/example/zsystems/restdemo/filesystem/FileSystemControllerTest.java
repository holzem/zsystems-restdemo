/*
 * MIT License
 *
 * Copyright (c) 2017 Markus Holzem
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.example.zsystems.restdemo.filesystem;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.example.zsystems.restdemo.RestdemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * FileSystemControllerTest
 *
 * @author Markus Holzem
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestdemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
		"management.port=0"
})
public class FileSystemControllerTest {

	@LocalServerPort
	private int port;

	@Value("${local.management.port}")
	private int mgt;

	@Autowired
	private TestRestTemplate testRestTemplate;

	/**
	 * Test method for
	 * {@link org.example.zsystems.restdemo.filesystem.FileSystemController#getContent(java.lang.String)}.
	 */
	@Test
	public void testGetContent() {
		@SuppressWarnings("rawtypes")
		final ResponseEntity<List> entity = this.testRestTemplate
				.getForEntity("http://localhost:" + this.port + "/files/TEST1", List.class);
		assertEquals(entity.getStatusCode(), HttpStatus.OK);
		@SuppressWarnings("unchecked")
		final List<String> lines = entity.getBody();
		assertEquals(2, lines.size());
		assertEquals("TEST1 line1", lines.get(0));
		assertEquals("TEST1 line2", lines.get(1));

	}

}
