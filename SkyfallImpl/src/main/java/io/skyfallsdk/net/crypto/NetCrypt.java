package io.skyfallsdk.net.crypto;

import com.google.common.collect.Maps;
import io.skyfallsdk.Server;
import io.skyfallsdk.net.NetClient;

import java.net.InetAddress;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.Map;

public class NetCrypt {

    private static final int TOKEN_LENGTH = 4;
    private static final SecureRandom RANDOM = new SecureRandom();

    private static final int KEYPAIR_SIZE = 1024;
    private static final String GENERATION_ALGORITHM = "RSA";

    private static final Map<InetAddress, NetCrypt> CRYPT_INSTANCES = Maps.newConcurrentMap();

    private final NetClient client;

    private final KeyPair keyPair;
    private final byte[] token;

    private NetCrypt(NetClient client) {
        this.client = client;

        this.keyPair = this.generateNewKeyPair();
        this.token = new byte[TOKEN_LENGTH];

        RANDOM.nextBytes(this.token);
    }

    private KeyPair generateNewKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(GENERATION_ALGORITHM);

            generator.initialize(KEYPAIR_SIZE);

            return generator.generateKeyPair();
        } catch (Exception e) {
            Server.get().getLogger().error("Could not generate KeyPair for " + (this.client.getUuid() != null ? this.client.getUuid().toString() : this.client.getAddress().getHostAddress()));
            return null;
        }
    }

    public KeyPair getKeyPair() {
        return this.keyPair;
    }

    public byte[] getToken() {
        return this.token;
    }

    public static NetCrypt get(NetClient client) {
        return CRYPT_INSTANCES.computeIfAbsent(client.getAddress(), (address) -> new NetCrypt(client));
    }
}
