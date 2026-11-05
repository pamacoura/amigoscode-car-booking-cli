package com.macoura.user;

import com.macoura.car.Brand;
import com.macoura.car.Car;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class UserDao {
    private static User[] users;
    static {
        users=new User[]{
                new User("Papa"),
                new User("Macoura"),
                new User("Samba")
        };

    }

    public static User[] getUsers() {
        User[] us=new User[users.length];
        int i=0;
        for(User u:users){
            if(u!=null)
                us[i++]=u;

        }
        return Arrays.copyOf(us,i);
    }
    public Optional<User> getUserById(UUID id){
        Optional<User> u=Optional.ofNullable(null);
        for (User user:users){
            if(user.getId().equals(id)){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}
