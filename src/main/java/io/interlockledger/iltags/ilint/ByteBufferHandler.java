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
package io.interlockledger.iltags.ilint;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * Implementation of the ILIntCodec's OutputHandler and InputHandler for ByteBuffer instances.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2019.06.10
 */
public class ByteBufferHandler implements ILIntCodec.OutputHandler<ByteBuffer>, ILIntCodec.InputHandler<ByteBuffer> {
	
	/**
	 * Preallocated instance of this class.
	 */
	public static final ByteBufferHandler INSTANCE = new ByteBufferHandler();
	
	/**
	 * Creates a new instance of this class.
	 */
	public ByteBufferHandler() {
	}
	
	@Override
	public void write(int b, ByteBuffer out) throws ILIntException {
		try {
			out.put((byte)b);
		} catch (BufferOverflowException e) {
			throw new ILIntException(e); 
		}
	}
	
	@Override
	public int get(ByteBuffer in) throws ILIntException {
		try {
			return in.get() & 0xFF;
		} catch (BufferUnderflowException e) {
			throw new ILIntException(e);
		}
	}
}
