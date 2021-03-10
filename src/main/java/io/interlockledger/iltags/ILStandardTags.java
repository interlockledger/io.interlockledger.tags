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

/**
 * This enumeration represents the standard tags. The tag id is represented by
 * the ordinal() of each declaration.
 * 
 * <p>
 * It is important to notice that elements starting with RESERVED should never
 * be used by applications because they may be renamed in the future in order to
 * accommodate new standard tags.
 * </p>
 * 
 * @author Fabio Jun Takada Chino
 */
public enum ILStandardTags {

	// WARNING: Never change the order of the declarations because
	// they are used as the tag IDs.

	/**
	 * The NULL tag. It contains no value.
	 */
	TAG_NULL,
	/**
	 * The boolean tag. It contains a single byte that represents true (1) or false
	 * (0).
	 */
	TAG_BOOL,
	/**
	 * The signed 8-bit integer tag. It contains a single byte that represents the
	 * value.
	 */
	TAG_INT8,
	/**
	 * The unsigned 8-bit integer tag. It contains a single byte that represents the
	 * value.
	 */
	TAG_UINT8,
	/**
	 * The signed 16-bit integer tag. It contains 2 bytes that represents the value
	 * in Big Endian.
	 */
	TAG_INT16,
	/**
	 * The unsigned 16-bit integer tag. It contains 2 bytes that represents the
	 * value in Big Endian.
	 */
	TAG_UINT16,
	/**
	 * The signed 32-bit integer tag. It contains 4 bytes that represents the value
	 * in Big Endian.
	 */
	TAG_INT32,
	/**
	 * The unsigned 32-bit integer tag. It contains 4 bytes that represents the
	 * value in Big Endian.
	 */
	TAG_UINT32,
	/**
	 * The signed 64-bit integer tag. It contains 8 bytes that represents the value
	 * in Big Endian.
	 */
	TAG_INT64,
	/**
	 * The unsigned 64-bit integer tag. It contains 8 bytes that represents the
	 * value in Big Endian.
	 */
	TAG_UINT64,
	/**
	 * The ILInt64 tag. It contains 1 to 9 bytes that represents a 64-bit integer
	 * encoded as an ILInt.
	 */
	TAG_ILINT64,
	/**
	 * The single precision floating point tag. It contains a 4 bytes that
	 * represents the value encoded as IEEE-754 in Big Endian.
	 */
	TAG_BINARY32,
	/**
	 * The double precision floating point tag. It contains a 8 bytes that
	 * represents the value encoded as IEEE-754 in Big Endian.
	 */
	TAG_BINARY64,
	/**
	 * The quadruple precision floating point tag. It contains a 16 bytes that
	 * represents the value encoded as IEEE-754 in Big Endian. The support for this
	 * tag is optional.
	 */
	TAG_BINARY128,
	/**
	 * Reserved for future uses. It may be renamed in the future. Do not use.
	 * 
	 * @since 2019.06.22
	 */
	@Deprecated
	RESERVED_14,
	/**
	 * Reserved for future uses. It may be renamed in the future. Do not use.
	 * 
	 * @since 2019.06.22
	 */
	@Deprecated
	RESERVED_15,
	/**
	 * The byte array tag. It contains a byte array with 0 or more bytes.
	 */
	TAG_BYTE_ARRAY,
	/**
	 * The string tag. It contains a UTF-8 string with 0 or more caracters.
	 */
	TAG_STRING,
	/**
	 * The big integer tag. It contains the value encoded as two's complement big
	 * endian value.
	 */
	TAG_BINT,
	/**
	 * The big decimal tag. It contains an arbitrary precision decimal encoded as an
	 * ILInt64 for the precision and the integral part as encoded as two's
	 * complement big endian value.
	 */
	TAG_BDEC,
	/**
	 * The ILnt64 array tag. It contains an array of 64-bit integers encoded as
	 * ILInt64 values. It is composed by an ILInt64 that encodes the number of
	 * entries followed by the entries themselves.
	 */
	TAG_ILINT64_ARRAY,
	/**
	 * The ILTag array tag. It contains an ILInt64 value that encodes the number of
	 * objects followed by the serialization of the tags.
	 */
	TAG_ILTAG_ARRAY,
	/**
	 * The ILTag sequence tag. It contains the serialization of a sequence of tags.
	 */
	TAG_ILTAG_SEQ,
	/**
	 * The ILTag range tag. It contains a range of ILTagId.
	 */
	TAG_RANGE,
	/**
	 * The ILTag version tag. It contains the semantic version number
	 * (major.minor.revision.build).
	 */
	TAG_VERSION,
	/**
	 * ITU Object Identifier.
	 */
	TAG_OID,
	/**
	 * Reserved for future uses. It may be renamed in the future. Do not use.
	 * 
	 * @since 2019.06.22
	 */
	@Deprecated
	RESERVED_26,
	/**
	 * Reserved for future uses. It may be renamed in the future. Do not use.
	 * 
	 * @since 2019.06.22
	 */
	@Deprecated
	RESERVED_27,
	/**
	 * Reserved for future uses. It may be renamed in the future. Do not use.
	 * 
	 * @since 2019.06.22
	 */
	@Deprecated
	RESERVED_28,
	/**
	 * Reserved for future uses. It may be renamed in the future. Do not use.
	 * 
	 * @since 2019.06.22
	 */
	@Deprecated
	RESERVED_29,
	/**
	 * Reserved for future uses. It may be renamed in the future. Do not use.
	 * 
	 * @since 2019.06.22
	 */
	@Deprecated
	RESERVED_30,
	/**
	 * Reserved for future uses. It may be renamed in the future. Do not use.
	 * 
	 * @since 2019.06.22
	 */
	@Deprecated
	RESERVED_31,
	/**
	 * This is a special value reserved for non-standard tags.
	 * 
	 * @since 2019.06.22
	 */
	NON_STANDARD_TAG;

	/**
	 * Verifies if the given tag id is one in the standard tag space.
	 * 
	 * @param id The ID to be tested.
	 * @return true if the id represents one of the standard tags or false
	 *         otherwise.
	 * @since 2019.06.22
	 */
	public static boolean isStandard(long id) {
		return (id >= 0) && (id < 32);
	}

	/**
	 * Utility method that converts a tag id into one of the values of this
	 * enumeration. All non standard tags are mapped to NON_STANDARD_TAG.
	 * 
	 * @param id The tag id to be parsed.
	 * @return The corresponding enumeration.
	 * @since 2019.06.22
	 */
	public static ILStandardTags parseId(long id) {
		if (isStandard(id)) {
			return ILStandardTags.values()[(int) id];
		} else {
			return NON_STANDARD_TAG;
		}
	}
}
