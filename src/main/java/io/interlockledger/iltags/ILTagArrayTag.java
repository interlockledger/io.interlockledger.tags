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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.interlockledger.iltags.ilint.ILIntCodec;
import io.interlockledger.iltags.io.ILTagDataReader;
import io.interlockledger.iltags.io.ILTagDataWriter;

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
		this(ILStandardTags.TAG_ILTAG_ARRAY);
	}
	
	public ILTagArrayTag(long id) {
		super(id);
	}

	@Override
	protected void serializeValue(ILTagDataWriter out) throws ILTagException {
		out.writeILInt(this.getValueSize());
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
	public void deserializeValue(ILTagFactory factory, long tagSize, ILTagDataReader in)
			throws ILTagException {
		long count;

		count = in.readILInt();
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
	
	@Override
	protected boolean sameValue(ILTag other) {
		ILTagArrayTag t = (ILTagArrayTag)other;
		if (this.getValue().size() != t.getValue().size()) {
			return false;
		}
		for (int i = 0; i < this.getValue().size(); i++) {
			if (!this.getValue().get(i).equals(t.getValue().get(i))) {
				return false;
			}
		}
		return true;
	}
}
