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
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

/**
 * WindowsFileSystemServiceTest
 *
 * @author Markus Holzem
 */
public class WindowsFileSystemServiceTest {

	/**
	 * Test method for
	 * {@link org.example.zsystems.restdemo.filesystem.WindowsFileSystemService#getMemberContent(java.lang.String)}.
	 */
	@Test
	public void testGetMemberContent() {
		final IFileSystemService fs = new WindowsFileSystemService();
		try {
			final List<String> lines = fs.getMemberContent("TEST1");
			assertEquals(2, lines.size());
			assertEquals("TEST1 line1", lines.get(0));
			assertEquals("TEST1 line2", lines.get(1));
		} catch (final IOException e) {
			fail("should not happen");
		}
	}

}
