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

import static org.junit.Assert.*;

import org.junit.Test;

import io.interlockledger.iltags.io.ILMemoryTagDataReader;
import io.interlockledger.iltags.io.ILMemoryTagDataWriter;

public class ILVersionTagTest {

	@Test
	public void testSerializeValue() throws Exception {
		ILVersionTag t = new ILVersionTag();

		ILMemoryTagDataWriter w = new ILMemoryTagDataWriter();
		t.setValue(1, 2, 3, 4);
		t.serializeValue(w);
		
		ILMemoryTagDataWriter expected = new ILMemoryTagDataWriter();
		expected.writeInt(1);
		expected.writeInt(2);
		expected.writeInt(3);
		expected.writeInt(4);
		assertArrayEquals(expected.toByteArray(), w.toByteArray());
	}

	@Test
	public void testDeserializeValueCore() throws Exception {
		ILVersionTag t = new ILVersionTag();

		ILMemoryTagDataWriter raw = new ILMemoryTagDataWriter();
		raw.writeInt(1);
		raw.writeInt(2);
		raw.writeInt(3);
		raw.writeInt(4);

		t.deserializeValue(null, 16, new ILMemoryTagDataReader(raw.toByteArray()));		
		assertEquals(1, t.getMajor());
		assertEquals(2, t.getMinor());
		assertEquals(3, t.getRevision());
		assertEquals(4, t.getBuild());
	}

	@Test
	public void testILVersionTag() {
		ILVersionTag t = new ILVersionTag();
		assertEquals(ILStandardTags.TAG_VERSION, t.getId());
		assertEquals(16, t.getValueSize());
		assertEquals(0, t.getMajor());
		assertEquals(0, t.getMinor());
		assertEquals(0, t.getRevision());
		assertEquals(0, t.getBuild());
	}

	@Test
	public void testILVersionTagLong() {
		ILVersionTag t = new ILVersionTag(123);
		assertEquals(123, t.getId());
		assertEquals(16, t.getValueSize());
		assertEquals(0, t.getMajor());
		assertEquals(0, t.getMinor());
		assertEquals(0, t.getRevision());
		assertEquals(0, t.getBuild());
	}

	@Test
	public void testSetValue() {
		ILVersionTag t = new ILVersionTag();

		t.setValue(1, 2, 3, 4);
		assertEquals(1, t.getMajor());
		assertEquals(2, t.getMinor());
		assertEquals(3, t.getRevision());
		assertEquals(4, t.getBuild());
	}

	@Test
	public void testGetSetMajor() {
		ILVersionTag t = new ILVersionTag();

		assertEquals(0, t.getMajor());
		assertEquals(0, t.getMinor());
		assertEquals(0, t.getRevision());
		assertEquals(0, t.getBuild());

		t.setMajor(1);
		assertEquals(1, t.getMajor());
		assertEquals(0, t.getMinor());
		assertEquals(0, t.getRevision());
		assertEquals(0, t.getBuild());
	}


	@Test
	public void testGetSetMinor() {
		ILVersionTag t = new ILVersionTag();

		assertEquals(0, t.getMajor());
		assertEquals(0, t.getMinor());
		assertEquals(0, t.getRevision());
		assertEquals(0, t.getBuild());

		t.setMinor(1);
		assertEquals(0, t.getMajor());
		assertEquals(1, t.getMinor());
		assertEquals(0, t.getRevision());
		assertEquals(0, t.getBuild());
	}

	@Test
	public void testGetSetRevision() {
		ILVersionTag t = new ILVersionTag();

		assertEquals(0, t.getMajor());
		assertEquals(0, t.getMinor());
		assertEquals(0, t.getRevision());
		assertEquals(0, t.getBuild());

		t.setRevision(1);
		assertEquals(0, t.getMajor());
		assertEquals(0, t.getMinor());
		assertEquals(1, t.getRevision());
		assertEquals(0, t.getBuild());
	}

	@Test
	public void testGetSetBuild() {
		ILVersionTag t = new ILVersionTag();

		assertEquals(0, t.getMajor());
		assertEquals(0, t.getMinor());
		assertEquals(0, t.getRevision());
		assertEquals(0, t.getBuild());

		t.setBuild(1);
		assertEquals(0, t.getMajor());
		assertEquals(0, t.getMinor());
		assertEquals(0, t.getRevision());
		assertEquals(1, t.getBuild());
	}
}
