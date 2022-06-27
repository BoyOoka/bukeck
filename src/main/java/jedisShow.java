import redis.clients.jedis.Jedis;

public class jedisShow {
    public static void main(String[] args){
        Jedis jedis = new Jedis("localhost", 6379);
        String data = jedis.srandmember("proxy:proxy_info");
        System.out.println(data);
        jedis.close();
    }
}
