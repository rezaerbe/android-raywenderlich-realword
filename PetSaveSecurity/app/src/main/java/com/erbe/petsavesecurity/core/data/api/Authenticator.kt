package com.erbe.petsavesecurity.core.data.api

import java.security.*
import java.security.spec.X509EncodedKeySpec

class Authenticator {

    private val publicKey: PublicKey
    private val privateKey: PrivateKey

    init {
        val keyPairGenerator = KeyPairGenerator.getInstance("EC")
        keyPairGenerator.initialize(256)
        val keyPair = keyPairGenerator.genKeyPair()

        publicKey = keyPair.public
        privateKey = keyPair.private
    }

    fun sign(data: ByteArray): ByteArray {
        val signature = Signature.getInstance("SHA512withECDSA")
        signature.initSign(privateKey)
        signature.update(data)
        return signature.sign()
    }

    //You can use this method to test your sign method above
    fun verify(signature: ByteArray, data: ByteArray): Boolean {
        val verifySignature = Signature.getInstance("SHA512withECDSA")
        verifySignature.initVerify(publicKey)
        verifySignature.update(data)
        return verifySignature.verify(signature)
    }

    fun verify(signature: ByteArray, data: ByteArray, publicKeyString: String): Boolean {
        val verifySignature = Signature.getInstance("SHA512withECDSA")
        val bytes = android.util.Base64.decode(publicKeyString, android.util.Base64.NO_WRAP)
        val publicKey = KeyFactory.getInstance("EC").generatePublic(X509EncodedKeySpec(bytes))
        verifySignature.initVerify(publicKey)
        verifySignature.update(data)
        return verifySignature.verify(signature)
    }

    fun publicKey(): String {
        return android.util.Base64.encodeToString(publicKey.encoded, android.util.Base64.NO_WRAP)
    }
}