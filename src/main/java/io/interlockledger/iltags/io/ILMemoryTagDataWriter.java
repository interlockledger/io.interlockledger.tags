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

import java.io.ByteArrayOutputStream;

import io.interlockledger.iltags.ILTagException;

/**
 * This class implements the ILTagDataWriter for memory.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2019.06.17
 */
public class ILMemoryTagDataWriter extends ILBaseTagDataWriter {

	/**
	 * The underlying output stream.
	 */
	protected ByteArrayOutputStream out;

	/**
	 * Creates a new instance of this class.
	 */
	public ILMemoryTagDataWriter() {
		this.out = new ByteArrayOutputStream();
	}

	/**
	 * Returns the contents o this writer as a byte array.
	 * 
	 * @return The contents o this writer as a byte array.
	 */
	public byte[] toByteArray() {
		return this.out.toByteArray();
	}

	@Override
	protected void writeByteCore(byte v) throws ILTagException {
		this.out.write(v & 0xFF);
	}

	@Override
	protected void writeBytesCore(byte[] v, int off, int size) throws ILTagException {
		this.out.write(v, off, size);
	}
}
