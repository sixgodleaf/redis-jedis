redis.call('zremrangeByScore',KEYS[1],0,ARGV[1]) ;

if(redis.call('zcard',KEYS[1]) < tonumber(ARGV[2])) then
    return redis.call('zadd',KEYS[1],ARGV[3],ARGV[4]) ;
else
    return 0;
end


