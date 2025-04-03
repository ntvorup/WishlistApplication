package org.example.wishlistapp.repository;

import org.example.wishlistapp.model.Wish;
import org.example.wishlistapp.model.Wishlist;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Repository
public class WishlistRepository extends Repository<Wish> {

    private final JdbcTemplate jdbcTemplate;

    public WishlistRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    //Adds the wish to the wishlist
    //Should only be used when already in a wishlist
    @Override
    @Transactional
    public void addToDatabase(Wish newWishToAdd) {
        String sql = "INSERT INTO wishes (wishlist_id, title, description, price, url, image_url) VALUES (?,?,?,?,?,?)";

        jdbcTemplate.update(sql,
                newWishToAdd.getWishlistId(),
                newWishToAdd.getWishTitle(),
                newWishToAdd.getWishDescription(),
                newWishToAdd.getWishPrice(),
                newWishToAdd.getProductUrl(),
                newWishToAdd.getImageUrl());
    }

    //Deletes the wish from the wishlist.
    //Should only be called whilst already in a wishlist
    @Override
    @Transactional
    public void deleteFromDatabase(Wish objectToDelete) {
        String sql = "DELETE FROM wishes WHERE wish_id = ?";

        jdbcTemplate.update(sql, objectToDelete.getWishId());
    }

    //Returns the wish that is clicked upon on the website.
    //Should only be called when already in a wishlist.
    @Override
    @Transactional
    public Wish findById(int wishId) {
        String sql = "SELECT * FROM wishes WHERE wish_id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Wish.class), wishId);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    //Gets all the wishes on a wishlist.
    //Should only be called when already in a wishlist.
    public List<Wish> getWishesByWishlistId(int wishlistId){
        String sql = "SELECT * FROM wishes WHERE wishlist_id = ?";

        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Wish.class), wishlistId);
    }


    @Override
    public Boolean edit(Wish newWish) {
        String sql = "UPDATE wishes " +
                "SET wishlist_id = ?, title = ?, description = ?, price = ?, url = ?, image_url = ? " +
                "WHERE wish_id = ?";
        try {
            return jdbcTemplate.update(sql, newWish.getWishlistId(),
                    newWish.getWishTitle(),
                    newWish.getWishDescription(),
                    newWish.getWishPrice(),
                    newWish.getProductUrl(),
                    newWish.getImageUrl(),
                    newWish.getWishId()) == 1;
        } catch (DataAccessException e){
            return false;
        }
    }

    //Wishlist Methods

    public List<Wishlist> getAllWishlists(){
        String sql = "SELECT * FROM wishlists";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Wishlist.class));
    }

    public void deleteWishlistFromDB (Wishlist wishlistToDelete){
        String sql = "DELETE FROM wishlists WHERE wishlist_id = ?";

        jdbcTemplate.update(sql, wishlistToDelete.getWishlistId());
    }

    public boolean updateWishlist (Wishlist newWishlist){
        String sql = "UPDATE wishlists " +
                "SET title = ? " +
                "WHERE wishlist_id = ?";

        try {
            return jdbcTemplate.update(sql, newWishlist.getWishlistTitle(),
                    newWishlist.getWishlistId()) == 1;
        } catch (DataAccessException e){
            return false;
        }
    }

    public void addWishlistToDatabase(Wishlist wishlist) {
        String sql = "INSERT INTO wishlists (user_id, title) VALUES (?,?)";

        jdbcTemplate.update(sql, wishlist.getUserId(), wishlist.getWishlistTitle());
    }

}
