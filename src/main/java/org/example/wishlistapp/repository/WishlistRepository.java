package org.example.wishlistapp.repository;

import org.example.wishlistapp.model.Wish;
import org.example.wishlistapp.model.Wishlist;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@org.springframework.stereotype.Repository
public class WishlistRepository extends Repository<Wishlist> {

    private final JdbcTemplate jdbcTemplate;

    public WishlistRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Adds wishlist to wishlists in DB.
    @Override
    @Transactional
    public void addToDatabase(Wishlist newWishlistToAdd) {
        String sql = "INSERT INTO wishlists (user_id, title) VALUES (?,?)";

        jdbcTemplate.update(sql, newWishlistToAdd.getUserId(), newWishlistToAdd.getWishlistTitle());

    }

    //Deletes the wishlist from the DB
    @Override
    @Transactional
    public void deleteFromDatabase(Wishlist wishlistToDelete) {
        String sql = "DELETE FROM wishlists WHERE wishlist_id = ?";

        jdbcTemplate.update(sql, wishlistToDelete.getWishlistId());
    }

    //Returns the wishlist that is clicked on, on the website with the associated ID.
    @Override
    @Transactional
    public Wishlist findById(int wishlistId) {
        String sql = "SELECT wishlist_id, user_id, title AS wishlistTitle FROM wishlists WHERE wishlist_id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Wishlist.class), wishlistId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public Boolean edit(Wishlist newWishlist) {
        String sql = "UPDATE wishlists " +
                "SET title = ? " +
                "WHERE wishlist_id = ?";

        try {
            return jdbcTemplate.update(sql, newWishlist.getWishlistTitle(),
                    newWishlist.getWishlistId()) == 1;
        } catch (DataAccessException e) {
            return false;
        }
    }

    public List<Wishlist> getAllWishlistsById(int id) {
        String sql = "SELECT wishlist_id, user_id, title AS wishlistTitle FROM wishlists WHERE user_id = ?";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Wishlist.class), id);
    }


    //Wish Methods
    @Transactional
    public void addWishToWishlist(Wish newWishToAdd, int wishlistId) {
        String sql = "INSERT INTO wishes (wishlist_id, title, description, price, url, image_url) VALUES (?,?,?,?,?,?)";

        jdbcTemplate.update(sql,
                wishlistId,
                newWishToAdd.getWishTitle(),
                newWishToAdd.getWishDescription(),
                newWishToAdd.getWishPrice(),
                newWishToAdd.getProductUrl(),
                newWishToAdd.getImageUrl());
    }

    @Transactional
    public void deleteWishFromWishlist(Wish wishToDelete) {
        String sql = "DELETE FROM wishes WHERE wish_id = ?";

        jdbcTemplate.update(sql, wishToDelete.getWishId());
    }

    @Transactional
    public boolean updateWish(Wish newWish) {
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
        } catch (DataAccessException e) {
            return false;
        }
    }

    //Gets all the wishes on a wishlist.
    public Map<Integer, Wish> getWishesByWishlistId(int wishlistId) {
        String sql = "SELECT wish_id, wishlist_id, " +
                "title AS wishTitle, " +
                "description AS wishDescription, " +
                "price AS wishPrice, " +
                "url AS productUrl, " +
                "image_url AS imageUrl " +
                "FROM wishes WHERE wishlist_id = ?";

        List<Wish> wishList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Wish.class), wishlistId);

        return wishList.stream().collect(Collectors.toMap(Wish::getWishId, wish -> wish));
    }

    public Wish getWishById(int wishId){
        String sql = "SELECT * FROM wishes WHERE wish_id = ?";

        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Wish.class), wishId);
    }
}
