package org.kafka.redis_cache.service;


import lombok.RequiredArgsConstructor;
import org.kafka.redis_cache.dto.CreateUserDto;
import org.kafka.redis_cache.dto.UpdateUserDto;
import org.kafka.redis_cache.model.User;
import org.kafka.redis_cache.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Cacheable(value = "users", key = "#root.methodName", unless = "#result == null")
    public List<User> getUsers() {
        long startTime = System.currentTimeMillis();
        List<User> users = userRepository.findAll();
        long endTime = System.currentTimeMillis();
        System.out.println("getUsers metodu çalışma süresi: " + (endTime - startTime) + " ms");
        return users;
    }

    @Cacheable(cacheNames = "user_id", key = "#root.methodName + #id", unless = "#result == null")
    public User getUserById(Long id) {
        long startTime = System.currentTimeMillis();
        User user = userRepository.findById(id).orElse(null);
        long endTime = System.currentTimeMillis();
        System.out.println("getUserById metodu çalışma süresi: " + (endTime - startTime) + " ms");
        return user;
    }

    @CacheEvict(value = {"users", "user_id"}, allEntries = true)
    public User createUser(CreateUserDto dto){
        var user = userRepository.save(dto.toEntity(dto));
        return user;
    }

    @CacheEvict(value = {"users", "user_id"}, allEntries = true)
    public String deleteUser(Long id){
        userRepository.deleteById(id);
        return "user deleted";
    }

    @Caching(
            put = {@CachePut(cacheNames = "user_id", key = "'getUserById' + #dto.id", unless = "#result == null")},
            evict = {@CacheEvict(value = "users", allEntries = true)}
    )
    public User updateUser(UpdateUserDto dto) {
        Optional<User> user = userRepository.findById(dto.getId());
        if (user.isPresent()) {
            User user1 = user.get();
            user1.setPassword(dto.getPassword());
            return userRepository.save(user1);
        } else {
            return null;
        }
    }

}
