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

import io.interlockledger.iltags.ILTagException;

/**
 * This is the interface of the data writer for the ILTags.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2019.06.14
 */
public interface ILTagDataWriter {

	/**
	 * Returns the number of bytes written.
	 * 
	 * @return The current offset.
	 */
	public long getOffset();

	/**
	 * Writes a byte.
	 * 
	 * @param v The value to write.
	 * @throws ILTagException In case of error.
	 */
	public void writeByte(byte v) throws ILTagException;

	/**
	 * Writes bytes.
	 * 
	 * @param v The bytes to be written.
	 * @throws ILTagException In case of error.
	 */
	public void writeBytes(byte[] v) throws ILTagException;

	/**
	 * Writes bytes.
	 * 
	 * @param v    The bytes to be written.
	 * @param off  The offset.
	 * @param size The number of bytes to write.
	 * @throws ILTagException In case of error.
	 */
	public void writeBytes(byte[] v, int off, int size) throws ILTagException;

	/**
	 * Writes a 64-bit single precision floating point in IEEE 754-2008 format.
	 * 
	 * @param v The value to write.
	 * @throws ILTagException In case of error.
	 */
	public void writeDouble(double v) throws ILTagException;

	/**
	 * Writes a 32-bit single precision floating point in IEEE 754-2008 format.
	 * 
	 * @param v The value to write.
	 * @throws ILTagException In case of error.
	 */
	public void writeFloat(float v) throws ILTagException;

	/**
	 * Writes a ILInt.
	 * 
	 * @param v The value to write.
	 * @throws ILTagException In case of error.
	 */
	public void writeILInt(long v) throws ILTagException;

	/**
	 * Writes a 32-bit big endian integer.
	 * 
	 * @param v The value to write.
	 * @throws ILTagException In case of error.
	 */
	public void writeInt(int v) throws ILTagException;

	/**
	 * Writes a 64-bit big endian integer.
	 * 
	 * @param v The value to write.
	 * @throws ILTagException In case of error.
	 */
	public void writeLong(long v) throws ILTagException;

	/**
	 * Writes a 16-bit big endian integer.
	 * 
	 * @param v The value to write.
	 * @throws ILTagException In case of error.
	 */
	public void writeShort(short v) throws ILTagException;

	/**
	 * Writes a string as its UTF-8 byte representation.
	 * 
	 * @param v The string to be written.
	 * @throws ILTagException
	 * @since 2019.06.17
	 */
	public void writeString(CharSequence v) throws ILTagException;
}
