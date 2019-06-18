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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;

import org.junit.Test;

import io.interlockledger.iltags.io.ILMemoryTagDataReader;
import io.interlockledger.iltags.io.ILMemoryTagDataWriter;

public class ILBigDecimalTagTest {

	private static final byte [] SAMPLE_UNSCALED = TestUtils.createSampleByteArray(64);
	private static final int SAMPLE_SCALE = 11;
	private static final BigDecimal SAMPLE = new BigDecimal(new BigInteger(SAMPLE_UNSCALED), SAMPLE_SCALE);
	

	@Test
	public void testSerializeValue() throws Exception {
		ILBigDecimalTag t = new ILBigDecimalTag();

		t.setValue(SAMPLE);
		ILMemoryTagDataWriter w = new ILMemoryTagDataWriter();
		t.serializeValue(w);
		
		ByteBuffer b = ByteBuffer.allocate(SAMPLE_UNSCALED.length + 4 - 1);
		b.putInt(SAMPLE_SCALE);
		b.put(SAMPLE_UNSCALED, 1, SAMPLE_UNSCALED.length - 1);
		assertArrayEquals(b.array(), w.toByteArray());
	}

	@Test
	public void testGetValueSize() {
		ILBigDecimalTag t = new ILBigDecimalTag();
		
		t.setValue(SAMPLE);
		assertEquals(4 + SAMPLE_UNSCALED.length - 1, t.getValueSize());
		
		t.setValue(BigDecimal.ONE);
		assertEquals(4 + 1, t.getValueSize());
		
		t.setValue(BigDecimal.ONE.negate());
		assertEquals(4 + 1, t.getValueSize());
		
		t.setValue(new BigDecimal(0xFFFF));
		assertEquals(4 + 3, t.getValueSize());
	}

	@Test
	public void testDeserializeValue() throws Exception {
		ByteBuffer b = ByteBuffer.allocate(SAMPLE_UNSCALED.length + 4);
		
		b.putInt(SAMPLE_SCALE);
		b.put(SAMPLE_UNSCALED);

		ILBigDecimalTag t = new ILBigDecimalTag();
		t.deserializeValue(null, b.position(), new ILMemoryTagDataReader(b.array()));
		assertEquals(SAMPLE, t.getValue());
	}
	
	@Test(expected = ILTagException.class)
	public void testDeserializeValueFail() throws Exception {
		ByteBuffer b = ByteBuffer.allocate(SAMPLE_UNSCALED.length + 4 - 1);
		
		b.putInt(SAMPLE_SCALE);
		b.put(SAMPLE_UNSCALED, 0, SAMPLE_UNSCALED.length - 1);

		ILBigDecimalTag t = new ILBigDecimalTag();
		t.deserializeValue(null, b.position() + 1, new ILMemoryTagDataReader(b.array()));
	}

	@Test
	public void testILBigDecimalTag() {
		ILBigDecimalTag t = new ILBigDecimalTag();
		
		assertEquals(ILStandardTags.TAG_BDEC, t.getId());
		assertNull(t.getValue());
	}

	@Test
	public void testILBigDecimalTagLong() {
		ILBigDecimalTag t = new ILBigDecimalTag(1234);
		assertEquals(1234, t.getId());
		assertNull(t.getValue());
	}

	@Test
	public void testGetSetValue() {
		ILBigDecimalTag t = new ILBigDecimalTag();

		assertNull(t.getValue());
		t.setValue(BigDecimal.ONE);
		assertEquals(BigDecimal.ONE, t.getValue());

		t.setValue(SAMPLE);
		assertEquals(SAMPLE, t.getValue());
	}
}
