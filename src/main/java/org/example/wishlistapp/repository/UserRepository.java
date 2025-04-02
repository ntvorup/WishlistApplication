package org.example.wishlistapp.repository;

import org.example.wishlistapp.model.User;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@org.springframework.stereotype.Repository
public class UserRepository extends Repository<User>{

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addToDatabase(User newUserToAdd) {
        String sql = "INSERT INTO users (username, first_name, last_name, email, password) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(sql, newUserToAdd.g)
    }

    @Override
    public void deleteFromDatabase(User objectToDelete) {

    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return List.of();
    }

    @Override
    public Boolean edit(User newObject) {
        return null;
    }
}
