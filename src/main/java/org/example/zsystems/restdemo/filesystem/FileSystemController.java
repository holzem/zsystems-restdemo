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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * FileSystemController grants access to the file system
 *
 * @author Markus Holzem
 */
@RestController
public class FileSystemController {

	private static final Logger LOG = LoggerFactory.getLogger(FileSystemController.class);

	private final IFileSystemService _fileSystemService;

	/**
	 * Constructor for FileSystemController
	 *
	 * @param pFileSystemService
	 *            the configured file system of the application
	 *
	 */
	@Autowired
	public FileSystemController(final IFileSystemService pFileSystemService) {
		_fileSystemService = pFileSystemService;
	}

	/**
	 * Return the content of given member
	 *
	 * @param membername
	 *            the member name
	 * @return the content of the member
	 * @throws IOException
	 *             on I/O exceptions
	 */
	@RequestMapping(value = "/files/{membername}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> getContent(@PathVariable(name = "membername") final String membername) {
		List<String> lines = null;
		try {
			lines = _fileSystemService.getMemberContent(membername);
		} catch (final FileNotFoundException e) {
			LOG.info("get {}: {}", membername, HttpStatus.NOT_FOUND.name());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (final IOException e) {
			LOG.info("get {}: {}", membername, HttpStatus.INTERNAL_SERVER_ERROR.name());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		LOG.info("get {}: {}", membername, HttpStatus.OK.name());
		return new ResponseEntity<>(lines, HttpStatus.OK);
	}

}
