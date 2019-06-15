/*
 * Copyright 2019 InterlockLedger Network
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.interlockledger.iltags.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

import io.interlockledger.iltags.ILTagException;

/**
 * @author Fabio Jun Takada Chino
 * @since 2019.06.14
 */
public class ILOutputStreamTagDataWriter extends ILBaseTagDataWriter implements Closeable {

	protected OutputStream out;
	
	public ILOutputStreamTagDataWriter(OutputStream out) {
		this.out = out; 
	}

	@Override
	public void writeByte(byte v) throws ILTagException {
		try {
			this.out.write(v & 0xFF);
		} catch (IOException e) {
			throw new ILTagException(e.getMessage());
		}
	}

	@Override
	public void writeBytes(byte[] v, int off, int size) throws ILTagException {
		try {
			this.out.write(v, off, size);
		} catch (IOException e) {
			throw new ILTagException(e.getMessage());
		}
	}

	@Override
	public void close() throws IOException {
		this.out.close();		
	}
}
