package rml.util;

/**
 * Created by edward-echo on 2016/2/1.
 */
import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DesUtil {

    private final static String DES = "DES";

    public static void main(String[] args) throws Exception {
        String data = "123456";
        String key = "ta&^%$at";
//        System.err.println(encrypt(data, key));
        System.err.println(decrypt("YejkHntVNX547wC6NofjyAx3LuIt/yk6K0h9VtOONb7Bwa/hNQbzdsqW6g+TLQzceG40Bu0Ru139\nlfNTCKDSwBvUvdOXcDKyY+sABEeuYU10ouplKz22nzfE6a96K9hrJ5P+8x+PdjD2qd1dZ8zNLlLa\nomG94CZmEivZhrnMXbecyKdImkXbV2XMT/Md1cb+5pa3PFuRCl0jJjStt+OA5/MSYgt9AomiymYg\nmiS9hy5CN8EoUnHiQ9kp/Tprzb2YUMODTuyn7viSMeMYsxKfwlSUpepI0X7NF+/1XB8DlqicVjdS\nFG3GyZGiOw/9o9YQIckpf8RJrzsf39dtJG1nI6WaecVbBND8AYQEwyFlzNS2ec0nvBjxn+aBsbeW\nBgCTrIWH6FUlXgFccZxtJYn6Be/kZgsTkJyczw0NQiYVjbs94snStM5kMkuTMsD7UfY8qFcSrpx9\n4hVgKxCI/IZ7ZJjzzx81zVT3iL2m/sG8nUhkOYMDZ17rcUC32tzkLzNWQ9/H1lT0PONuiS4B02zO\nu0voAxLzvBapEyXUKTLBvbwnOuiBGi5cwd3pE0JLZCtZNxP4x1gz0tF1D5GuQBoleI0R6YED1CJN\n12hoCCuJFZUrlr8/HFSu3OOOU3vpx8ir79Vujf1p33AEJLrLZBiNIl1ZExferhyIMzGF/7xJQVlR\n7spm+LlDaoVhnZVGQq94O4twXtk/xzKO32LT7iUQqcGB8bsLSqo4XlijmUKbqDjMQdtRoQoDpEkY\nSuMB4U8nWYlelxfBZGcp+radZ7zNh9MuSy8JA5uJJrx1h88S/3JrnqSfi+/YO4AqIv39jMVdJ5P+\n8x+PdjD2qd1dZ8zNLlLaomG94CZmEivZhrnMXbcUEvpthGdWouV6UkVIJ2xjY+sABEeuYU2WzACT\nWk+Nau+V/2LQqcbqZauBIQmWSd64AqarjAHVy6ha/sKi06ecFiYQgUEXhdUuulNfPuXwy8gaqjnX\n0MyFoWMyubdNxR9r59UYR7vRjPaeXYdVmUlDP7aB8lGewgxjVbx1EU6SNsvH7tiFwXaapid/uTVZ\nHAdIwcvv5DS8Kkdh+wcBdIV86wLW2ymhNNESIUkY6PD3aVvEsJjHSZ1ylA6M3X1JbQ4RYMWEsMy7\n18JM5lasHQDPfuYhd3U6gIhQ6aCufu59nw9tztWMyEs219zG0RqeUvOtXpXPOiwESy66U18+5fDL\n4hV3ZRrurHO3kDd2QjW/gMEEwl2JyLeuB58fd1AMuoHvq1akE7f8ctSp/xOuEgJZDrAO3zcBtYIP\nKN2h/fV17lUnWg5b420NeAADAg/vjD7w+i5nC5VYrYfLuWuZhkT+pCsWzQhFuRUf6Tckrl4/gunN\nL5kaPGj/w5s/867yOW7MQdtRoQoDpEkYSuMB4U8nWYlelxfBZGcp+radZ7zNh9MuSy8JA5uJJrx1\nh88S/3JrnqSfi+/YO4AqIv39jMVdUHVLB6GFxjaQEDKRd2NUCdYBpBo7IPSmq1+a7R9c3Vw7OZlG\nGdjG70Z0korG+2HCResS3qjjD0IZeYo8PkYgGxxkyzODcEB73zWlsUQLisKbmj1FIYVM3/9Bpb69\nvBhNd0cZIqt7C/Rkz53HB31BPfibJ8mO/EB6KK2vVIp7pNWLgBIxzQxOBG8RC6YT9BZh1mbrlGO5\nUppTCf5y2XBwtiTCgfuYJf7uN5vd1ae6YpNL/YonuuRC3+np3i22wbiN94JyngBiBugOibNhHyTo\nhhFp+QyKCre+Sqs69BXei5SdxEu6Ww4tWTvDwBS0+1vE+y93BgMbvRHToNMTHis5gmab5jhZMPnB\nhXpG7jeofLfx2u0o+obJsfTExKs9GsYZRiENXQVCBxD+9WLwQziOhOnldj8W0x4j3XcYlKjiBv8J\n1ueBtOUQiZMJnmzeGwDbpyoxBVPSu8iM1W/HhFbXwnQyU8UP2lBSc8NG2NV+sKFdMfs2FqB9ippL\nGZjli8KKKT5pS5+rHvfDqvhPkDcpzp2Ff85OAQnTJC+fMp+glAqWNWwCc7NHxdg4NkdYwQwcY20Z\n4hDawTeGjZxukEYVSpuVuZXLzjcmOcPuo0pmSOBMgOXUMqflzg==", key));

    }

    /**
     * Description 根据键值进行加密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes("UTF-8"), key.getBytes());
        String strs = new BASE64Encoder().encode(bt);
        return strs;
    }

    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws IOException,
            Exception {
        if (data == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf,key.getBytes());
        return new String(bt, "UTF-8");
    }

    /**
     * Description 根据键值进行加密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }


    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }
}