package org.example.wishlistapp.repository;

import org.example.wishlistapp.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Repository
public class UserRepository extends Repository<User>{

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void addToDatabase(User newUserToAdd) {
        String sql = "INSERT INTO users (first_name, last_name, email, password) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql,
                newUserToAdd.getFirstName(),
                newUserToAdd.getLastName(),
                newUserToAdd.getEmail(),
                newUserToAdd.getPassword());
    }

    @Override
    @Transactional
    public void deleteFromDatabase(User userToDelete) {
        String sql = "DELETE FROM users WHERE user_id = ?";

        jdbcTemplate.update(sql, userToDelete.getUserId());
    }

    @Override
    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(User.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    @Transactional
    public List<User> getAll() {
        String sql = "SELECT * FROM users";

        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(User.class));
    }

    @Override
    @Transactional
    public Boolean edit(User newUser) {
        String sql = "UPDATE users " +
                "SET first_name = ?, last_name = ?, email = ?, password = ? " +
                "WHERE user_id = ?";

        try{
            return jdbcTemplate.update(sql, newUser.getFirstName(),
                    newUser.getLastName(),
                    newUser.getEmail(),
                    newUser.getPassword(),
                    newUser.getUserId()) == 1;
        } catch (DataAccessException e){
            return false;
        }
    }

    @Transactional
    public User findByEmailAndPassword(String email, String password){
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(User.class), email, password);
    }

    public Boolean doesEmailExist(String email){
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }
}
