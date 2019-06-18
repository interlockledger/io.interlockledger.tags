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

import java.math.BigInteger;
import java.util.Random;

import org.junit.Test;

import io.interlockledger.iltags.io.ILMemoryTagDataReader;
import io.interlockledger.iltags.io.ILMemoryTagDataWriter;

public class ILBigIntTagTest {

	@Test
	public void testSerializeValue() throws Exception {
		Random random = new Random();
		
		for (int size = 2; size < 20; size++) {
			BigInteger b = BigInteger.probablePrime(15, random);
			ILMemoryTagDataWriter w = new ILMemoryTagDataWriter();
			
			ILBigIntTag t = new ILBigIntTag();
			t.setValue(b);
			t.serializeValue(w);
			assertArrayEquals(b.toByteArray(), w.toByteArray());
		}
	}
	
	@Test(expected = IllegalStateException.class)
	public void testSerializeValueFail() throws Exception {
		ILBigIntTag t = new ILBigIntTag();
		ILMemoryTagDataWriter w = new ILMemoryTagDataWriter();
		t.serializeValue(w);
	}

	@Test
	public void testGetValueSize() {
		Random random = new Random();
		
		for (int size = 2; size < 20; size++) {
			ILBigIntTag t = new ILBigIntTag();
			BigInteger b = BigInteger.probablePrime(15, random);
			t.setValue(b);
			assertEquals(b.toByteArray().length, t.getValueSize());
		}
	}
	
	@Test(expected = IllegalStateException.class)
	public void testGetValueSizeFail() throws Exception {
		ILBigIntTag t = new ILBigIntTag();
		
		t.getValueSize();		
	}

	@Test
	public void testDeserializeValue() throws Exception {
		Random random = new Random();
		
		for (int size = 2; size < 20; size++) {
			BigInteger b = BigInteger.probablePrime(15, random);
			
			ILBigIntTag t = new ILBigIntTag();
			ILMemoryTagDataReader r = new ILMemoryTagDataReader(b.toByteArray());
			t.deserializeValue(null, b.toByteArray().length, r);
			assertEquals(b, t.getValue());
		}
	}
	
	@Test(expected = ILTagException.class)
	public void testDeserializeValueFail() throws Exception {

		ILBigIntTag t = new ILBigIntTag();
		ILMemoryTagDataReader r = new ILMemoryTagDataReader(new byte[1]);
		t.deserializeValue(null, 2, r);
	}
	
	@Test
	public void testILBigIntTag() {
		ILBigIntTag t = new ILBigIntTag();
		assertEquals(ILStandardTags.TAG_BINT, t.getId());
		assertNull(t.getValue());
	}

	@Test
	public void testILBigIntTagLong() {
		ILBigIntTag t = new ILBigIntTag(1234);
		assertEquals(1234, t.getId());
		assertNull(t.getValue());
	}

	@Test
	public void testGetSetValue() {
		ILBigIntTag t = new ILBigIntTag();
		
		assertNull(t.getValue());
		t.setValue(BigInteger.ONE);
		assertEquals(BigInteger.ONE, t.getValue());
		t.setValue(BigInteger.TEN);
		assertEquals(BigInteger.TEN, t.getValue());
	}

	@Test
	public void testGetBigIntegerSize() {
		BigInteger i;
		
		assertEquals(1, ILBigIntTag.getBigIntegerSize(BigInteger.ZERO));
		
		i = BigInteger.ONE;
		assertEquals(i.toByteArray().length, ILBigIntTag.getBigIntegerSize(i));
		i = i.negate();
		assertEquals(i.toByteArray().length, ILBigIntTag.getBigIntegerSize(i));
		
		i = BigInteger.TEN;
		assertEquals(i.toByteArray().length, ILBigIntTag.getBigIntegerSize(i));
		i = i.negate();
		assertEquals(i.toByteArray().length, ILBigIntTag.getBigIntegerSize(i));
		
		i = new BigInteger("3F", 16);
		assertEquals(i.toByteArray().length, ILBigIntTag.getBigIntegerSize(i));
		i = i.negate();
		assertEquals(i.toByteArray().length, ILBigIntTag.getBigIntegerSize(i));

		i = new BigInteger("7F", 16);
		assertEquals(i.toByteArray().length, ILBigIntTag.getBigIntegerSize(i));
		i = i.negate();
		assertEquals(i.toByteArray().length, ILBigIntTag.getBigIntegerSize(i));
		
		i = new BigInteger("80", 16);
		assertEquals(i.toByteArray().length, ILBigIntTag.getBigIntegerSize(i));
		i = i.negate();
		assertEquals(i.toByteArray().length, ILBigIntTag.getBigIntegerSize(i));

		i = new BigInteger("FF", 16);
		assertEquals(i.toByteArray().length, ILBigIntTag.getBigIntegerSize(i));
		i = i.negate();
		assertEquals(i.toByteArray().length, ILBigIntTag.getBigIntegerSize(i));
	}

	@Test
	public void testSerializeBigInteger() {
		Random random = new Random();
		
		for (int size = 2; size < 20; size++) {
			BigInteger b = BigInteger.probablePrime(15, random);
			byte [] v = ILBigIntTag.serializeBigInteger(b);
			assertArrayEquals(b.toByteArray(), v);
			b = b.negate();
			v = ILBigIntTag.serializeBigInteger(b);
			assertArrayEquals(b.toByteArray(), v);
		}
	}

}
