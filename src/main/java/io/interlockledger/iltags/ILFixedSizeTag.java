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
package io.interlockledger.iltags;

import java.io.IOException;

import io.interlockledger.iltags.io.ILTagDataReader;

/**
 * This class implements the base fixed size tag.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2019.06.12
 */
public abstract class ILFixedSizeTag extends ILTag {
	
	/**
	 * Size of the value in bytes.
	 */
	protected final int valueSize;

	/**
	 * Creates a new instance of this class.
	 * 
	 * @param id The tag ID.
	 * @param valueSize The size of the value in bytes.
	 */
	protected ILFixedSizeTag(long id, int valueSize) {
		super(id);
		this.valueSize = valueSize;
	}

	@Override
	public long getValueSize() {
		return this.valueSize;
	}

	@Override
	public void deserializeValue(ILTagFactory factory, long tagSize, ILTagDataReader in)
			throws ILTagException {
		if (tagSize != this.valueSize) {
			throw new ILTagException("Invalid byte tag size.");
		}
		deserializeValueCore(factory, in);
	}

	/**
	 * Actually deserializes the value of the tag. It is called by 
	 * deserializeValue(ILTagFactory,long,DataInputStream) after the
	 * verification of the intial parameters.
	 * 
	 * @param factory The ILTagFactory if required.
	 * @param in The size of the buff in bytes. Use -1 if the tag size is unknown.
	 * @throws ILTagException In case of erros.
	 * @throws IOException  In case of I/O erros.
	 */
	protected abstract void deserializeValueCore(ILTagFactory factory, ILTagDataReader in)
			throws ILTagException;
}
