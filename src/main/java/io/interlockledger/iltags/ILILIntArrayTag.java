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
import java.util.List;

import io.interlockledger.iltags.ilint.ILIntCodec;
import io.interlockledger.iltags.io.ILTagDataReader;
import io.interlockledger.iltags.io.ILTagDataWriter;

/**
 * This class implements the standard ILInt array tag but can also be
 * used to implement other variants.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2019.06.18
 */
public class ILILIntArrayTag extends ILTag {

	private ArrayList<Long> value = new ArrayList<>();
	
	public ILILIntArrayTag() {
		this(ILStandardTags.TAG_ILINT64_ARRAY.ordinal());
	}
	
	public ILILIntArrayTag(long id) {
		super(id);
	}

	@Override
	protected void serializeValue(ILTagDataWriter out) throws ILTagException {

		out.writeILInt(this.value.size());
		for (Long v: this.value) {
			out.writeILInt(v);
		}		
	}

	@Override
	public long getValueSize() {

		long size = ILIntCodec.getEncodedSize(this.value.size());
		for (Long v: this.value) {
			size += ILIntCodec.getEncodedSize(v);
		}
		return size;
	}

	@Override
	public void deserializeValue(ILTagFactory factory, long tagSize, ILTagDataReader in) throws ILTagException {

		long count = in.readILInt();
		this.value.clear();
		for (;count > 0; count--) {
			this.value.add(in.readILInt());
		}
	}

	public List<Long> getValue() {
		return value;
	}
	
	@Override
	protected boolean sameValue(ILTag other) {
		ILILIntArrayTag t = (ILILIntArrayTag)other;
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
