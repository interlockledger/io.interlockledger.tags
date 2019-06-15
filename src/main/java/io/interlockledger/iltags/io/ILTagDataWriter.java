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
public interface ILTagDataWriter {

	public void writeByte(byte v) throws ILTagException;
	public void writeShort(short v) throws ILTagException;
	public void writeInt(int v) throws ILTagException;
	public void writeLong(long v) throws ILTagException;
	public void writeFloat(float v) throws ILTagException;
	public void writeDouble(double v) throws ILTagException;
	public void writeBytes(byte [] v) throws ILTagException;
	public void writeBytes(byte [] v, int off, int size) throws ILTagException;
	public void writeILInt(long v) throws ILTagException;
}
