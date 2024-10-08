package com.truemen.api.agreement.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import com.truemen.api.agreement.protocol.Packet;
import com.truemen.api.agreement.util.SerializationUtil;

/**
 * 编码器
 */
public class ObjEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet in, ByteBuf out) {
        byte[] data = SerializationUtil.serialize(in);
        out.writeInt(data.length + 1);
        out.writeByte(in.getCommand()); // 添加指令
        out.writeBytes(data);
    }

}
