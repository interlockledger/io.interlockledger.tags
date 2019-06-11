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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import io.interlockledger.iltags.ilint.ILIntCodec;
import io.interlockledger.iltags.ilint.ILIntException;

/**
 * This class implements the base class for all ILTag classes.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2019.06.10
 */
public abstract class ILTag { 
	
	private static long [] STANDARD_SIZES = {
			0,  // TAG_NULL
			1,  // TAG_BOOL
			1,  // TAG_INT8
			1,  // TAG_UINT8
			2,  // TAG_INT16
			2,  // TAG_UINT16
			4,  // TAG_INT32
			4,  // TAG_UINT32
			8,  // TAG_INT64
			8,  // TAG_UINT64
			-1,  // TAG_ILINT64 - Not defined in this table
			4,  // TAG_BINARY32
			8,  // TAG_BINARY64
			16, // TAG_BINARY128
			0,  // Reserved
			0   // Reserved
	};
	
	private long id;
	
	protected ILTag(long id) {
		this.id = id;
	}
	
	protected abstract void serializeValue(DataOutputStream out) throws ILTagException, IOException;

	public abstract long getValueSize();
	
	/**
	 * Returns the encoded size of this tag.
	 * 
	 * @return The encoded tag.
	 */
	public long getTagSize() {
		long size;
		long valueSize;
		
		valueSize = this.getValueSize();
		size = ILIntCodec.getEncodedSize(this.getId() + valueSize);
		if (!this.isImplicity()) {
			size += ILIntCodec.getEncodedSize(valueSize);
		}
		return size;
	}
	
	/**
	 * Returns the ID of the tag.
	 * 
	 * @return The tag id.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Serializes this tag. The serialization must be written to the current
	 * position of the buffer out.
	 *  
	 * @param out
	 * @throws ILTagException
	 * @throws IOException
	 */
	public void serialize(DataOutputStream out)  throws ILTagException {
		
		try {
			ILIntCodec.encode(this.getId(), out);
			if (!this.isImplicity() ) {
				ILIntCodec.encode(this.getValueSize(), out);
			}
			this.serializeValue(out);			
		} catch (ILIntException | IOException e) {
			throw new ILTagException(e.getMessage(), e);
		}
	}
	
	/**
	 * Deserializes the value of the tag.
	 *
	 * @param factory The ILTagFactory if required.
	 * @param buff The buffer that contains the tag value.
	 * @param in The size of the buff in bytes. Use -1 if the tag size is unknown.
	 * @return true for success or false otherwise.
	 */	
	public abstract void deserializeValue(ILTagFactory factory, long tagSize, DataInputStream in) throws ILTagException, IOException;
	
	/**
	 * Verifies if this tag is implicit or not.
	 * @return true if this tag is implicit or false otherwise.
	 */	
	public boolean isImplicity() {
		return isImplicity(this.getId());
	}

	/**
	 * Verifies if a given tag id denotes an implicit or explicit tag.
	 *
	 * @param tagID the tagId.
	 * @return true if the tag is implicit or false otherwise.
	 */	
	public static boolean isImplicity(long tagId) {
		return tagId < 16;
	}
	
	/**
	 * Verifies if this tag is standard or not.
	 *
	 * @return true if this tag is standard or false otherwise.
	 */	
	public boolean isStandard() {
		return isStandard(this.getId());
	}
	
	/**
	 * Verifies if a given tag id standard or not. All tags with IDs lower than
	 * 32 are considered part of the standard and should not be redefined by
	 * applications.
	 *
	 * @param tagID the tagId.
	 * @return true if the tag is standard or false otherwise.
	 */
	public static boolean isStandard(long tagId) {
		return tagId < 32;
	}
	
	/**
	 * Returns the size of the values for all implicit tags that have a fixed
	 * size. This means that the size of TAG_ILINT64 is not covered by this
	 * method.
	 *
	 * @param tagId The tag Id.
	 * @return The implicit tag size.
	 */	
	public static long getImplicitValueSize(long tagId) {

		if (tagId == ILTagStandardTags.TAG_ILINT64) {
			throw new IllegalArgumentException("TAG_ILINT64 does not have a fixed size.");
		} else if (isImplicity(tagId)) {
			return STANDARD_SIZES[(int)tagId];
		} else {
			throw new IllegalArgumentException("The specified tag is not implicity.");
		}
	}
}
