/*
 * MIT License Copyright (c) 2017 Markus Holzem Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.example.zsystems.restdemo.filesystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * WindowsFileSystem is the windows implementation of {@link IFileSystemService}.
 *
 * @author Markus Holzem
 */
public class WindowsFileSystemService implements IFileSystemService {

	private static String ROOT_DIRECTORY = "src/test/resources";

	/*
	 * (non-Javadoc)
	 * @see org.example.zsystems.restdemo.filesystem.FileSystem#getMemberContent(java.lang.String)
	 */
	@Override
	public List<String> getMemberContent(final String pMemberName) throws IOException
	{
		final List<String> lines = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(ROOT_DIRECTORY + "\\" + pMemberName))) {
			String sCurrentLine;
			while ((sCurrentLine = reader.readLine()) != null) {
				lines.add(sCurrentLine);
			}
		}
		return lines;
	}
}
