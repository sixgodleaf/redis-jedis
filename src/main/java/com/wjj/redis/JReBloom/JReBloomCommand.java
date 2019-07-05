package com.wjj.redis.JReBloom;

import redis.clients.jedis.commands.ProtocolCommand;
import redis.clients.jedis.util.SafeEncoder;

public enum JReBloomCommand implements ProtocolCommand {

    RESERVE("BF.RESERVE"),
    ADD("BF.ADD"),
    MADD("BF.MADD"),
    EXISTS("BF.EXISTS"),
    MEXISTS("BF.MEXISTS");

    private final byte[] raw;

    JReBloomCommand(String alt) {
        raw = SafeEncoder.encode(alt);
    }

    public byte[] getRaw() {
        return raw;
    }
}
