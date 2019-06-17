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
 * This is the interface of the data reader for the ILTags.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2019.06.14
 */
public interface ILTagDataReader {

	/**
	 * Reads a single byte.
	 * 
	 * @return The byte read.
	 * @throws ILTagException In case of error.
	 */
	public byte readByte() throws ILTagException;

	/**
	 * Reads a 16-bit big endian integer.
	 * 
	 * @return The value read.
	 * @throws ILTagException In case of error.
	 */
	public short readShort() throws ILTagException;

	/**
	 * Reads a 32-bit big endian integer.
	 * 
	 * @return The value read.
	 * @throws ILTagException In case of error.
	 */
	public int readInt() throws ILTagException;
	
	/**
	 * Reads a 32-bit big endian integer.
	 * 
	 * @return The value read.
	 * @throws ILTagException In case of error.
	 */
	public long readLong() throws ILTagException;
	
	/**
	 * Reads a 32-bit single precision floating point in
	 * IEEE 754-2008 format.
	 * 
	 * @return The value read.
	 * @throws ILTagException In case of error.
	 */
	public float readFloat() throws ILTagException;

	/**
	 * Reads a 64-bit single precision floating point in
	 * IEEE 754-2008 format.
	 * 
	 * @return The value read.
	 * @throws ILTagException In case of error.
	 */
	public double readDouble() throws ILTagException;
	
	/**
	 * Reads an array of bytes.
	 * 
	 * @param v The buffer that will receive the bytes.
	 * @throws ILTagException In case of error.
	 */
	public void readBytes(byte [] v) throws ILTagException;

	/**
	 * Reads an array of bytes.
	 * 
	 * @param v The buffer that will receive the bytes.
	 * @param off The initial offset.
	 * @param size The number of bytes to read.
	 * @throws ILTagException In case of error.
	 */
	public void readBytes(byte [] v, int off, int size) throws ILTagException;
	
	/**
	 * Reads an ILInt.
	 *
	 * @return The value read.
	 * @throws ILTagException In case of error.
	 */
	public long readILInt() throws ILTagException;
	
	/**
	 * Skips n bytes.
	 * 
	 * @param n Number of bytes to skip.
	 * @throws ILTagException In case of error.
	 */
	public void skip(long n) throws ILTagException;

	/**
	 * Imposes a limit to the number of bytes that can be read
	 * without a read error. Multiple calls to this method will
	 * stack the limits, allowing multiple sub limits to be
	 * defined.
	 * 
	 * <p>Limits defined by this method may be removed by
	 * calls of popLimit(boolean).</p>
	 * 
	 * @param size The number of bytes to set the limit. 
	 */
	public void pushLimit(long size);
	
	/**
	 * Removes a previous established limit.
	 * 
	 * @param checkRemaining If true, this method will fail if the limit was
	 * not achieved.
	 * @throws ILTagException In case of error.
	 */
	public void popLimit(boolean checkRemaining) throws ILTagException;
	
	/**
	 * Returns the number of bytes remaining if a limit is on effect. If
	 * the limit is not set, the return of this method is undefined.
	 * 
	 * @return The number of bytes remaining.
	 */
	public long getRemaining();
	
	/**
	 * Checks if a limit is in effect.
	 * 
	 * @return True if a limit was imposed or false otherwise.
	 */
	public boolean isLimited();
	
	/**
	 * Returns the current read position.
	 *  
	 * @return The read position.
	 */
	public long getOffset();
	
	/**
	 * Reads a n bytes and converts it into a UTF-8 string.
	 * 
	 * @param n The number of bytes to read.
	 * @return The string read.
	 * @since 2019.06.17
	 * @throws ILTagException In case of error.
	 */
	public String readString(long n) throws ILTagException;
	
	/**
	 * Reads a n bytes and converts it into a UTF-8 string.
	 * 
	 * @param n The number of bytes to read.
	 * @param v The appendable that will receive the result.
	 * @return The number of characters read.
	 * @throws ILTagException In case of error.
	 */
	public long readString(long n, Appendable v) throws ILTagException;
}
