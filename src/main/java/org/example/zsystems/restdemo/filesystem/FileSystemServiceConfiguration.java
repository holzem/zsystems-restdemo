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

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FileSystemServiceConfiguration
 *
 * @author Markus Holzem
 */
@Configuration
public class FileSystemServiceConfiguration {

	/**
	 * Return the {@link WindowsFileSystemService} when running on Windows 7
	 *
	 * @return the {@link IFileSystemService} for the operating system
	 */
	@Bean
	@ConditionalOnProperty(name = "os.name", havingValue = "Windows 7")
	public IFileSystemService windowsFileSystemService() {
		return new WindowsFileSystemService();
	}

	/**
	 * Return the {@link ZosFileSystemService} when running on z/OS
	 *
	 * @return the {@link IFileSystemService} for the operating system
	 */
	@Bean
	@ConditionalOnProperty(name = "os.name", havingValue = "z/OS")
	public IFileSystemService zosFileSystemService() {
		return new ZosFileSystemService();
	}
}
