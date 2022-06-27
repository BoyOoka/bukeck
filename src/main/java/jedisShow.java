import redis.clients.jedis.Jedis;

public class jedisShow {
    public static void main(String[] args){
        Jedis jedis = new Jedis("192.168.2.9", 6379);
        String data = jedis.srandmember("proxy:proxy_info");
        System.out.println(data);
        jedis.close();
    }
}
