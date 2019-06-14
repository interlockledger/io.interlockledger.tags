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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.interlockledger.iltags.ilint.ILIntCodec;
import io.interlockledger.iltags.ilint.ILIntException;
import io.interlockledger.iltags.io.SubInputStream;

/**
 * This class implements the standard tag array tag but can also be
 * used to implement other variants.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2019.06.14
 */
public class ILTagArrayTag extends ILTag {

	private ArrayList<ILTag> value = new ArrayList<ILTag>();

	public ILTagArrayTag() {
		this(ILTagStandardTags.TAG_ILTAG_ARRAY);
	}
	
	public ILTagArrayTag(long id) {
		super(id);
	}

	@Override
	protected void serializeValue(DataOutputStream out) throws ILTagException, IOException {

		try {
			ILIntCodec.encode(this.value.size(), out);
		} catch (ILIntException e) {
			throw new ILTagException(e.getMessage(), e);
		}
		for (ILTag t: this.value) {
			t.serialize(out);
		}
	}

	@Override
	public long getValueSize() {
		long size;
		
		size = ILIntCodec.getEncodedSize(this.value.size());
		for (ILTag t: this.value) {
			size += t.getTagSize();
		}
		return size;
	}

	@Override
	public void deserializeValue(ILTagFactory factory, long tagSize, DataInputStream in)
			throws ILTagException, IOException {
		SubInputStream sub = new SubInputStream(in, tagSize, false);
		this.deserializeValueCore(factory, tagSize, new DataInputStream(sub));
		if (sub.remaining() > 0) {
			throw new ILTagException("Invalid tag size.");
		}
	}
	
	private void deserializeValueCore(ILTagFactory factory, long tagSize, DataInputStream in)
			throws ILTagException, IOException {
		long count;
		try {
			count = ILIntCodec.decode(in);
		} catch (ILIntException e) {
			throw new ILTagException(e);
		}
		if (count > Integer.MAX_VALUE) {
			throw new ILTagException("Unsupported number of entries.");
		}
		this.value.clear();
		for (; count > 0; count--) {
			this.value.add(factory.deserialize(in));
		}		
	}

	public List<ILTag> getValue() {
		return this.value;
	}
	
	public void setValue(Collection<ILTag> value) {
		this.value.clear();
		this.value.addAll(value);
	}
}
