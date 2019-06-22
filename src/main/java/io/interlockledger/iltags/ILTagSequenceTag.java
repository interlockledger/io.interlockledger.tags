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

import io.interlockledger.iltags.io.ILTagDataReader;
import io.interlockledger.iltags.io.ILTagDataWriter;

/**
 * This class implements the standard tag array tag but can also be
 * used to implement other variants.
 * 
 * @author Fabio Jun Takada Chino
 * @since 2019.06.14
 */
public class ILTagSequenceTag extends ILTag {

	protected ArrayList<ILTag> value = new ArrayList<>();

	public ILTagSequenceTag() {
		this(ILStandardTags.TAG_ILTAG_SEQ.ordinal());
	}
	
	public ILTagSequenceTag(long id) {
		super(id);
	}

	@Override
	protected void serializeValue(ILTagDataWriter out) throws ILTagException {

		for (ILTag t: this.value) {
			if (t != null) {
				t.serialize(out);
			} else {
				ILNullTag.NULL.serialize(out);
			}
		}
	}

	@Override
	public long getValueSize() {
		long size;
		
		size = 0;
		for (ILTag t: this.value) {
			if (t != null) {
				size += t.getTagSize();
			} else {
				size += ILNullTag.NULL.getTagSize();
			}
		}
		return size;
	}

	@Override
	public void deserializeValue(ILTagFactory factory, long tagSize, ILTagDataReader in)
			throws ILTagException {
		this.value.clear();
		in.pushLimit(tagSize);
		while(in.getRemaining() > 0) {
			this.value.add(factory.deserialize(in));
		}		
		in.popLimit(true);
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
		ILTagSequenceTag t = (ILTagSequenceTag)other;
		return equals(this.getValue(), t.getValue());
	}
	
	/**
	 * Verifies if two list of tags are equal. This method returns true
	 * if both lists are equal or if both lists are null.
	 * 
	 * <p>This method considers that both null and instances of standard 
	 * ILNullTag are equal.</p>
	 * 
	 * @param a List a.
	 * @param b List b.
	 * @return true if they are equal or false otherwise.
	 * @since 2019.06.22
	 */
	public static boolean equals(List<ILTag> a, List<ILTag> b) {
		if (a == b) {
			return true;
		}
		if ((a == null) || (b == null)) {
			return false;
		}
		if (a.size() != b.size()) {
			return false;
		}
		for (int i = 0; i < a.size(); i++) {
			ILTag ta = (a.get(i) == null)? ILNullTag.NULL: a.get(i);
			ILTag tb = (b.get(i) == null)? ILNullTag.NULL: b.get(i);
			if ((ta != tb) && (!ta.equals(tb))) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	protected int getValueHashCode() {
		int code = 0;
		
		for (ILTag t: this.getValue()) {
			code += t.hashCode();
		}
		return code;
	}
}
