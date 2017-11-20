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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ibm.jzos.ZFile;
import com.ibm.jzos.ZUtil;

/**
 * ZosFileSystem is the z/OS implementation of {@link IFileSystemService}.
 * <p>
 * Based on the examples from <a href="https://github.com/zsystems/java-samples">JZOS samples on GitHub</a>.
 *
 * @author Markus Holzem
 */
public class ZosFileSystemService implements IFileSystemService {

	/*
	 * (non-Javadoc)
	 * @see org.example.zsystems.restdemo.filesystem.FileSystem#getMemberContent(java.lang.String)
	 */
	@Override
	public List<String> getMemberContent(final String pMemberName) throws IOException {
		// filename is <userid>.RESTDEMO.TEXT(<membername>)
		final String currentUser = ZUtil.getCurrentUser().trim();
		final String fileName = "//'" + currentUser + "." + "RESTDEMO.TEXT" + "(" + pMemberName + ")'";
		final List<String> lines = new ArrayList<>();
		ZFile dsnFile = null;
		try {
			dsnFile = new ZFile(fileName, "rb,type=record,noseek");
			final byte[] recBuf = new byte[dsnFile.getLrecl()];
			while (dsnFile.read(recBuf) != -1) {
				final String line = new String(recBuf);
				lines.add(line);
			}
		} catch (final Exception e) {
			final String msg = e.getMessage();
			if (msg != null && msg.contains("EDC5067I")) {
				throw new FileNotFoundException(pMemberName + "not found.");
			} else {
				throw new IOException(e);
			}
		} finally {
			try {
				if (dsnFile != null) {
					dsnFile.close();
				}
			} catch (final Exception e) {
				// do nothing, hide this exception
			}
		}
		return lines;
	}

}
