package net.coderbot.eerv.compression;

public class TCmpStruct
{
    public /*unsigned*/ int   distance;                // 0000: Backward distance of the currently found repetition, decreased by 1
    public /*unsigned*/ int   out_bytes;               // 0004: # bytes available in out_buff            
    public /*unsigned*/ int   out_bits;                // 0008: # of bits available in the last out byte
    public /*unsigned*/ int   dsize_bits;              // 000C: Number of bits needed for dictionary size. 4 = 0x400, 5 = 0x800, 6 = 0x1000
    public /*unsigned*/ int   dsize_mask;              // 0010: Bit mask for dictionary. 0x0F = 0x400, 0x1F = 0x800, 0x3F = 0x1000
    public boolean binary;                  // 0014: Compression type (CMP_ASCII or CMP_BINARY)
    public /*unsigned*/ int   dsize_bytes;             // 0018: Dictionary size in bytes
    public /*unsigned*/ byte[]  nChBits = new byte[0x306];          // 009C: Table of literal bit lengths to be put to the output stream
    public char[] nChCodes = new char[0x306];         // 03A2: Table of literal codes to be put to the output stream
    
    public char offs09AE;                // 09AE: 

    public char[] offs09BC = new char[0x204];         // 09BC:
    public /*unsigned*/ long  offs0DC4;                // 0DC4: 
    public char[] phash_to_index = new char[0x900];   // 0DC8: Array of indexes (one for each PAIR_HASH) to the "pair_hash_offsets" table
    public char phash_to_index_end;      // 1FC8: End marker for "phash_to_index" table
    public /*unsigned*/ byte[] out_buff = new byte[0x802];         // 1FCA: Compressed data
    public /*unsigned*/ byte[]  work_buff = new byte[0x2204];       // 27CC: Work buffer
                                            //  + DICT_OFFSET  => Dictionary
                                            //  + UNCMP_OFFSET => Uncompressed data
    public char[] phash_offs = new char[0x2204];      // 49D0: Table of offsets for each PAIR_HASH
}
