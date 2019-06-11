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

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * This class implements the ILInt format as specified in
 * https://github.com/interlockledger/specification/tree/master/ILInt.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2019.06.10
 */
public class ILIntCodec {
	
	/**
	 * Interface of the byte output handler. All instances of this interface must
	 * be thread safe. 
	 */
	public interface OutputHandler<T> {
		/**
		 * Writes a single byte into the output.
		 * 
		 * @param b The byte to be written.
		 * @throws ILIntException In case of error.
		 */
		public void write(int b, T out) throws ILIntException;
	}
	
	/**
	 * Interface of the byte input handler. All instances of this interface must
	 * be thread safe. 
	 */
	public interface InputHandler<T> {
		/**
		 * Reads a single byte from the input.
		 * 
		 * @return The byte read. It must be a positive value from 0 to 255.
		 * @throws ILIntException In case of error.
		 */
		public int get(T in) throws ILIntException;
	}
		
	/**
	 * LInt base value. All values larger then or equal to will use more
	 * than 1 byte to be encoded.
	 */
	public static final int ILINT_BASE = 0xF8;
	
	/**
	 * Returns the size of the given value encoded as an ILInt.
	 *
	 * @return The size in bytes.
	 */
	public static int getEncodedSize(long value) {
		
		if (value < 0) {
			return 9;
		} else if (value < ILINT_BASE) {
			return 1;
		} else if (value <= (0xFF + ILINT_BASE)) {
			return 2;
		} else if ( value <= (0xFFFF + ILINT_BASE)){
			return 3;
		} else if ( value <= (0xFFFFFFl + ILINT_BASE)){
			return 4;
		} else if ( value <= (0xFFFFFFFFl + ILINT_BASE)){
			return 5;
		} else if ( value <= (0xFFFFFFFFFFl + ILINT_BASE)){
			return 6;
		} else if ( value <= (0xFFFFFFFFFFFFl + ILINT_BASE)){
			return 7;
		} else if ( value <= (0xFFFFFFFFFFFFFFl + ILINT_BASE)) {
			return 8;
		} else {
			return 9;
		}
	}

	/**
	 * Reads a ILInt from an InputStream.
	 * 
	 * @param in The InputStream.
	 * @return The value read.
	 * @throws ILIntException In case of error.
	 */
	public static long decode(InputStream in) throws ILIntException {
		return decode(in, InputStreamHandler.INSTANCE);
	}
	
	/**
	 * Reads a ILInt from a byte buffer.
	 * 
	 * @param buff The buffer that contains the bytes.
	 * @return The value read.
	 * @throws ILIntException In case of error.
	 */
	public static long decode(ByteBuffer buff) throws ILIntException {
		return decode(buff, ByteBufferHandler.INSTANCE);
	}
	
	/**
	 * Reads a ILInt from the input using the given input handler.
	 * 
	 * @param <T> The type of the input.
	 * @param in The input itself.
	 * @param inputHandler The input handler.
	 * @return The decoded value.
	 * @throws ILIntException In case of error.
	 */
	public static<T> long decode(T in, InputHandler<T> inputHandler) throws ILIntException {
		
		int size = inputHandler.get(in);
		if (size < ILINT_BASE) {
			return size;
		} else {
			size = size - ILINT_BASE + 1;
			long v = 0;
			for (int i = 0; i < size; i++) {
				v = v << 8;
				v = v | ((long)inputHandler.get(in));
			}
			// Check overflow
			if ((v <= 0xFFFFFFFFFFFFFFFFl) && (v >= 0xFFFFFFFFFFFFFF07l)) {
				throw new ILIntException("Overflow.");
			}
			return v + ILINT_BASE;
		}
	}

	/**
	 * Encodes a value using the ILInt format.
	 * 
	 * @param value The value to be written.
	 * @param out The output stream.
	 * @return The number of bytes written.
	 * @throws ILIntException In case of error.
	 */
	public static int encode(long value, OutputStream out) throws ILIntException {
		return encode(value, out, OutputStreamHandler.INSTANCE);
	}
	
	/**
	 * Encodes a value using the ILInt format.
	 * 
	 * @param value The value to be written.
	 * @param buff The byte output.
	 * @return The number of bytes written.
	 * @throws ILIntException In case of error.
	 */
	public static int encode(long value, ByteBuffer buff) throws ILIntException {
		return encode(value, buff, ByteBufferHandler.INSTANCE);
	}

	/**
	 * Encodes a value using the ILInt format.
	 * 
	 * @param <T> The type of the output.
	 * @param value The value to be written.
	 * @param out The output itself.
	 * @param outputHandler The output handler.
	 * @return The number of bytes written.
	 * @throws ILIntException In case of error.
	 */
	public static <T> int encode(long value, T out, OutputHandler<T> outputHandler) throws ILIntException {
		
		int size = getEncodedSize(value);
		if (size == 1) {
			outputHandler.write((byte)value, out);
		} else {
			// Add the header
			outputHandler.write((ILINT_BASE + (size - 2)), out);
			value = (value - ILINT_BASE) << (8 * (9 - size));
			for (int i = 1; i < size; i++) {
				outputHandler.write((int)((value >>> 56) & 0xFF), out);
				value = value << 8;
			}
		}
		return size;
	}
}
