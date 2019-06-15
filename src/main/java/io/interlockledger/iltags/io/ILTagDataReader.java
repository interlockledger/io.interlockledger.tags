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
 * @author Fabio Jun Takada Chino
 * @since 2019.06.14
 */
public interface ILTagDataReader {

	public byte readByte() throws ILTagException;
	public short readShort() throws ILTagException;
	public int readInt() throws ILTagException;
	public long readLong() throws ILTagException;
	public float readFloat() throws ILTagException;
	public double reatDouble() throws ILTagException;
	public void readBytes(byte [] v) throws ILTagException;
	public void readBytes(byte [] v, int off, int size) throws ILTagException;
	public long readILInt() throws ILTagException;
	
	public void skip(long n) throws ILTagException;

	/**
	 * Pushes a limit to the number of bytes that can be read.
	 * 
	 * @param remaining
	 */
	public void pushLimit(long newLimit);
	public void popLimit(boolean checkRemaining) throws ILTagException;
	public long getRemaining();
	public boolean isLimited();
}
