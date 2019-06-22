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

import org.junit.Test;

import io.interlockledger.iltags.io.ILMemoryTagDataReader;
import io.interlockledger.iltags.io.ILMemoryTagDataWriter;
import io.interlockledger.iltags.io.ILTagDataReader;

public class ILTagFactoryTest {
	
	@Test
	public void testCreate() {
		ILTagFactory f = new ILTagFactory();
		ILTag t;
		ILStandardTags tagId;
		
		tagId = ILStandardTags.TAG_NULL;
		t = f.create(tagId.ordinal());
		assertTrue(t instanceof ILNullTag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_BOOL;
		t = f.create(tagId.ordinal());
		assertTrue(t instanceof ILBooleanTag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_INT8;
		t = f.create(tagId.ordinal());
		assertTrue(t instanceof ILInt8Tag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_UINT8;
		t = f.create(tagId.ordinal());
		assertTrue(t instanceof ILUInt8Tag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_INT16;
		t = f.create(tagId.ordinal());
		assertTrue(t instanceof ILInt16Tag);
		assertEquals(tagId.ordinal(), t.getId());

		tagId = ILStandardTags.TAG_UINT16;
		t = f.create(tagId.ordinal());
		assertTrue(t instanceof ILUInt16Tag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_INT32;
		t = f.create(tagId.ordinal());
		assertTrue(t instanceof ILInt32Tag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_UINT32;
		t = f.create(tagId.ordinal());
		assertTrue(t instanceof ILUInt32Tag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_INT64;
		t = f.create(tagId.ordinal());
		assertTrue(t instanceof ILInt64Tag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_UINT64;
		t = f.create(tagId.ordinal());
		assertTrue(t instanceof ILUInt64Tag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_ILINT64;
		t = f.create(tagId.ordinal());
		assertTrue(t instanceof ILILIntTag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_BINARY32;
		t = f.create(tagId.ordinal());
		assertTrue(t instanceof ILBinary32Tag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_BINARY64;
		t = f.create(tagId.ordinal());
		assertTrue(t instanceof ILBinary64Tag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_BINARY128;
		t = f.create(tagId.ordinal());
		assertTrue(t instanceof ILBinary128Tag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_BYTE_ARRAY;
		t = f.create(tagId.ordinal());
		assertTrue(t instanceof ILByteArrayTag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_STRING;
		t = f.create(tagId.ordinal());
		assertTrue(t instanceof ILStringTag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_BINT;
		t = f.create(tagId.ordinal());
		assertTrue(t instanceof ILBigIntTag);
		assertEquals(tagId.ordinal(), t.getId());

		tagId = ILStandardTags.TAG_BDEC;
		t = f.create(tagId.ordinal());
		assertTrue(t instanceof ILBigDecimalTag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_ILINT64_ARRAY;
		t = f.create(tagId.ordinal());
		assertTrue(t instanceof ILILIntArrayTag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_ILTAG_ARRAY;
		t = f.create(tagId.ordinal());
		assertTrue(t instanceof ILTagArrayTag	);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_ILTAG_SEQ;
		t = f.create(tagId.ordinal());
		assertTrue(t instanceof ILTagSequenceTag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_RANGE;
		t = f.create(tagId.ordinal());
		assertTrue(t instanceof ILRangeTag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_VERSION;
		t = f.create(tagId.ordinal());
		assertTrue(t instanceof ILVersionTag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_OID;
		t = f.create(tagId.ordinal());
		assertTrue(t instanceof ILOIDTag);
		assertEquals(tagId.ordinal(), t.getId());
		
		assertNull(f.create(-1));
		assertNull(f.create(32));
	}

	@Test
	@SuppressWarnings("deprecation")
	public void testCreateNotSupported() {
		ILTagFactory f = new ILTagFactory();
		assertNull(f.create(ILStandardTags.RESERVED_14.ordinal()));
		assertNull(f.create(ILStandardTags.RESERVED_15.ordinal()));
		assertNull(f.create(ILStandardTags.RESERVED_26.ordinal()));
		assertNull(f.create(ILStandardTags.RESERVED_27.ordinal()));
		assertNull(f.create(ILStandardTags.RESERVED_28.ordinal()));
		assertNull(f.create(ILStandardTags.RESERVED_29.ordinal()));
		assertNull(f.create(ILStandardTags.RESERVED_30.ordinal()));
		assertNull(f.create(ILStandardTags.RESERVED_31.ordinal()));
	}
	
	private static <T extends ILTag> void serializeAndDeserialize(T tag, Class<T> tagClass) throws ILTagException {
		ILTagFactory f = new ILTagFactory();
		ILMemoryTagDataWriter out = new ILMemoryTagDataWriter();
		tag.serialize(out);
		ILTagDataReader in = new ILMemoryTagDataReader(out.toByteArray());
		ILTag ret =  f.deserialize(in);
		assertNotSame(tag, ret);
		assertEquals(tag, ret);
	}

	@Test
	public void testDeserializeILNullTag() throws Exception {
		ILNullTag src = new ILNullTag();
		serializeAndDeserialize(src, ILNullTag.class);
	}

	@Test
	public void testDeserializeILBooleanTag() throws Exception {
		ILBooleanTag src = new ILBooleanTag();
		src.setValue(true);
		serializeAndDeserialize(src, ILBooleanTag.class);
	}
	
	@Test
	public void testDeserializeILInt8Tag() throws Exception {
		ILInt8Tag src = new ILInt8Tag();
		src.setValue((byte)0x12);
		serializeAndDeserialize(src, ILInt8Tag.class);
	}
	
	@Test
	public void testDeserializeILUInt8Tag() throws Exception {
		ILUInt8Tag src = new ILUInt8Tag();
		src.setValue((byte)0x12);
		serializeAndDeserialize(src, ILUInt8Tag.class);
	}

	@Test
	public void testDeserializeILInt16Tag() throws Exception {
		ILInt16Tag src = new ILInt16Tag();
		src.setValue((short)0x1234);
		serializeAndDeserialize(src, ILInt16Tag.class);
	}
	
	@Test
	public void testDeserializeILUInt16Tag() throws Exception {
		ILUInt16Tag src = new ILUInt16Tag();
		src.setValue((short)0x1234);
		serializeAndDeserialize(src, ILUInt16Tag.class);
	}

	@Test
	public void testDeserializeILInt32Tag() throws Exception {
		ILInt32Tag src = new ILInt32Tag();
		src.setValue(0x12345678);
		serializeAndDeserialize(src, ILInt32Tag.class);
	}
	
	@Test
	public void testDeserializeILUInt32Tag() throws Exception {
		ILUInt32Tag src = new ILUInt32Tag();
		src.setValue(0x12345678);
		serializeAndDeserialize(src, ILUInt32Tag.class);
	}
	
	@Test
	public void testDeserializeILInt64Tag() throws Exception {
		ILInt64Tag src = new ILInt64Tag();
		src.setValue(0x1234567890ABCDEFl);
		serializeAndDeserialize(src, ILInt64Tag.class);
	}
	
	@Test
	public void testDeserializeILUInt64Tag() throws Exception {
		ILUInt64Tag src = new ILUInt64Tag();
		src.setValue(0x1234567890ABCDEFl);
		serializeAndDeserialize(src, ILUInt64Tag.class);
	}
	
	@Test
	public void testDeserializeILILIntTag() throws Exception {
		ILILIntTag src = new ILILIntTag();
		src.setValue(0x1234567890ABCDEFl);
		serializeAndDeserialize(src, ILILIntTag.class);
	}	
	
	@Test
	public void testDeserializeILBinary32Tag() throws Exception {
		ILBinary32Tag src = new ILBinary32Tag();
		src.setValue(1.1f);
		serializeAndDeserialize(src, ILBinary32Tag.class);
	}
	
	@Test
	public void testDeserializeILBinary64Tag() throws Exception {
		ILBinary64Tag src = new ILBinary64Tag();
		src.setValue(1.1d);
		serializeAndDeserialize(src, ILBinary64Tag.class);
	}
	
	@Test
	public void testDeserializeILBinary128Tag() throws Exception {
		ILBinary128Tag src = new ILBinary128Tag();
		TestUtils.fillSampleByteArray(src.getValue(), 0, 16);
		serializeAndDeserialize(src, ILBinary128Tag.class);
	}
	
	@Test
	public void testDeserializeILByteArrayTag() throws Exception {
		ILByteArrayTag src = new ILByteArrayTag();
		src.setValue(TestUtils.createSampleByteArray(15));
		serializeAndDeserialize(src, ILByteArrayTag.class);
	}
	
	@Test
	public void testDeserializeILStringTag() throws Exception {
		ILStringTag src = new ILStringTag();
		src.setValue("This is just a test!");
		serializeAndDeserialize(src, ILStringTag.class);
	}

	@Test
	public void testDeserializeILBigIntTag() throws Exception {
		ILBigIntTag src = new ILBigIntTag();
		src.setValue(BigInteger.TEN);
		serializeAndDeserialize(src, ILBigIntTag.class);
	}

	@Test
	public void testDeserializeILBigDecimalTag() throws Exception {
		ILBigDecimalTag src = new ILBigDecimalTag();
		src.setValue(BigDecimal.TEN.setScale(1));
		serializeAndDeserialize(src, ILBigDecimalTag.class);
	}

	@Test
	public void testDeserializeILILIntArrayTag() throws Exception {
		ILILIntArrayTag src = new ILILIntArrayTag();
		src.getValue().add(1l);
		src.getValue().add(2l);
		src.getValue().add(3l);
		serializeAndDeserialize(src, ILILIntArrayTag.class);
	}
	
	@Test
	public void testDeserializeILTagArrayTag() throws Exception {
		ILTagArrayTag src = new ILTagArrayTag();
		src.getValue().add(new ILNullTag());
		src.getValue().add(new ILBooleanTag());
		src.getValue().add(new ILBinary32Tag());
		
		ILTagArrayTag sub = new ILTagArrayTag();
		src.getValue().add(sub);
		sub.getValue().add(new ILBinary32Tag());
		
		serializeAndDeserialize(src, ILTagArrayTag.class);
	}
	
	@Test
	public void testDeserializeILTagSequenceTag() throws Exception {
		ILTagSequenceTag src = new ILTagSequenceTag();
		src.getValue().add(new ILNullTag());
		src.getValue().add(new ILBooleanTag());
		src.getValue().add(new ILBinary32Tag());
		
		ILTagSequenceTag sub = new ILTagSequenceTag();
		src.getValue().add(sub);
		sub.getValue().add(new ILBinary32Tag());
		
		serializeAndDeserialize(src, ILTagSequenceTag.class);
	}

	@Test
	public void testDeserializeILRangeTag() throws Exception {
		ILRangeTag src = new ILRangeTag();
		src.setValue(1, 2);
		
		serializeAndDeserialize(src, ILRangeTag.class);
	}
	
	@Test
	public void testDeserializeILVersionTag() throws Exception {
		ILVersionTag src = new ILVersionTag();
		src.setValue(1, 2, 3, 4);
		
		serializeAndDeserialize(src, ILVersionTag.class);
	}
	
	@Test
	public void testDeserializeILOIDTag() throws Exception {
		ILOIDTag src = new ILOIDTag();
		src.getValue().add(123l);
		src.getValue().add(456l);
		src.getValue().add(789l);
		serializeAndDeserialize(src, ILOIDTag.class);
	}
	
	
	@Test
	public void testDeserializeUnknownPermissive() throws Exception {
		ILTagFactory f = new ILTagFactory();
		f.setStrictMode(false);
		
		byte [] b = new byte[4];
		b[0] = (byte)32;
		b[1] = (byte)2;
		b[2] = (byte)3;
		b[3] = (byte)4;
		ILTag tag = f.deserialize(new ILMemoryTagDataReader(b));

		assertNotNull(tag);
		assertTrue(tag instanceof ILByteArrayTag);
		assertEquals(32, tag.getId());
		ILByteArrayTag bt = (ILByteArrayTag)tag;
		assertEquals(2, bt.getValue().length);
		assertEquals(3, bt.getValue()[0]);
		assertEquals(4, bt.getValue()[1]);
	}

	@Test(expected = ILUnknownTagException.class)
	public void testDeserializeUnknownStrict() throws Exception {
		ILTagFactory f = new ILTagFactory();
		f.setStrictMode(true);
		
		byte [] b = new byte[4];
		b[0] = (byte)32;
		b[1] = (byte)2;
		b[2] = (byte)3;
		b[3] = (byte)4;
		f.deserialize(new ILMemoryTagDataReader(b));
	}
	
	@Test
	public void testILTagFactory() {
		ILTagFactory f = new ILTagFactory();
		assertFalse(f.isStrictMode());
	}	
	
	@Test
	public void testIsSetStrictMode() {
		ILTagFactory f = new ILTagFactory();
		
		assertFalse(f.isStrictMode());
		f.setStrictMode(true);
		assertTrue(f.isStrictMode());
		f.setStrictMode(false);
		assertFalse(f.isStrictMode());
	}
	
	@Test
	public void testCreateStandardILStandardTags() {
		ILTag t;
		ILStandardTags tagId;
		
		tagId = ILStandardTags.TAG_NULL;
		t = ILTagFactory.createStandard(tagId);
		assertTrue(t instanceof ILNullTag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_BOOL;
		t = ILTagFactory.createStandard(tagId);
		assertTrue(t instanceof ILBooleanTag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_INT8;
		t = ILTagFactory.createStandard(tagId);
		assertTrue(t instanceof ILInt8Tag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_UINT8;
		t = ILTagFactory.createStandard(tagId);
		assertTrue(t instanceof ILUInt8Tag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_INT16;
		t = ILTagFactory.createStandard(tagId);
		assertTrue(t instanceof ILInt16Tag);
		assertEquals(tagId.ordinal(), t.getId());

		tagId = ILStandardTags.TAG_UINT16;
		t = ILTagFactory.createStandard(tagId);
		assertTrue(t instanceof ILUInt16Tag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_INT32;
		t = ILTagFactory.createStandard(tagId);
		assertTrue(t instanceof ILInt32Tag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_UINT32;
		t = ILTagFactory.createStandard(tagId);
		assertTrue(t instanceof ILUInt32Tag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_INT64;
		t = ILTagFactory.createStandard(tagId);
		assertTrue(t instanceof ILInt64Tag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_UINT64;
		t = ILTagFactory.createStandard(tagId);
		assertTrue(t instanceof ILUInt64Tag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_ILINT64;
		t = ILTagFactory.createStandard(tagId);
		assertTrue(t instanceof ILILIntTag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_BINARY32;
		t = ILTagFactory.createStandard(tagId);
		assertTrue(t instanceof ILBinary32Tag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_BINARY64;
		t = ILTagFactory.createStandard(tagId);
		assertTrue(t instanceof ILBinary64Tag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_BINARY128;
		t = ILTagFactory.createStandard(tagId);
		assertTrue(t instanceof ILBinary128Tag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_BYTE_ARRAY;
		t = ILTagFactory.createStandard(tagId);
		assertTrue(t instanceof ILByteArrayTag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_STRING;
		t = ILTagFactory.createStandard(tagId);
		assertTrue(t instanceof ILStringTag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_BINT;
		t = ILTagFactory.createStandard(tagId);
		assertTrue(t instanceof ILBigIntTag);
		assertEquals(tagId.ordinal(), t.getId());

		tagId = ILStandardTags.TAG_BDEC;
		t = ILTagFactory.createStandard(tagId);
		assertTrue(t instanceof ILBigDecimalTag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_ILINT64_ARRAY;
		t = ILTagFactory.createStandard(tagId);
		assertTrue(t instanceof ILILIntArrayTag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_ILTAG_ARRAY;
		t = ILTagFactory.createStandard(tagId);
		assertTrue(t instanceof ILTagArrayTag	);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_ILTAG_SEQ;
		t = ILTagFactory.createStandard(tagId);
		assertTrue(t instanceof ILTagSequenceTag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_RANGE;
		t = ILTagFactory.createStandard(tagId);
		assertTrue(t instanceof ILRangeTag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_VERSION;
		t = ILTagFactory.createStandard(tagId);
		assertTrue(t instanceof ILVersionTag);
		assertEquals(tagId.ordinal(), t.getId());
		
		tagId = ILStandardTags.TAG_OID;
		t = ILTagFactory.createStandard(tagId);
		assertTrue(t instanceof ILOIDTag);
		assertEquals(tagId.ordinal(), t.getId());

		assertNull(ILTagFactory.createStandard(ILStandardTags.NON_STANDARD_TAG));
	}

	@Test
	@SuppressWarnings("deprecation")
	public void testtestCreateStandardILStandardTagsNotSupported() {
		assertNull(ILTagFactory.createStandard(ILStandardTags.RESERVED_14));
		assertNull(ILTagFactory.createStandard(ILStandardTags.RESERVED_15));
		assertNull(ILTagFactory.createStandard(ILStandardTags.RESERVED_26));
		assertNull(ILTagFactory.createStandard(ILStandardTags.RESERVED_27));
		assertNull(ILTagFactory.createStandard(ILStandardTags.RESERVED_28));
		assertNull(ILTagFactory.createStandard(ILStandardTags.RESERVED_29));
		assertNull(ILTagFactory.createStandard(ILStandardTags.RESERVED_30));
		assertNull(ILTagFactory.createStandard(ILStandardTags.RESERVED_31));
	}
}
