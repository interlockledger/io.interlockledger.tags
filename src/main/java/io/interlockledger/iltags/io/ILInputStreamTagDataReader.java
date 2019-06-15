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
import java.io.InputStream;

import io.interlockledger.iltags.ILTagException;

/**
 * @author Fabio Jun Takada Chino
 * @since 2019.06.14
 */
public class ILInputStreamTagDataReader extends ILBaseTagDataReader implements Closeable  {

	protected final InputStream in;
	
	public ILInputStreamTagDataReader(InputStream in) {
		this.in = in;
	}

	@Override
	protected byte readByteCore() throws ILTagException {
		
		try {
			int r = in.read();
			if (r < 0) {
				throw new ILTagNotEnoughDataException();
			}
			return (byte)r;
		} catch (IOException e) {
			throw new ILTagException(e.getMessage(), e);
		}
	}

	@Override
	protected void readBytesCore(byte[] v, int off, int size) throws ILTagException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void skipCore(long n) throws ILTagException {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() throws IOException {
		this.in.close();
	}
}
