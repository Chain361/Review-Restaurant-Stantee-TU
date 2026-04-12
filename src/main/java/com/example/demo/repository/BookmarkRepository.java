package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Bookmark;
import com.example.demo.entity.BookmarkId;


@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, BookmarkId> {

    @Query(value = """
        SELECT
            p."placeID",
            p."placeName",
            COALESCE(pc."CategoryName", '') AS category,
            COALESCE(
                (
                    SELECT MIN(pi.file_path)
                    FROM place_images pi
                    WHERE pi.placeid = p."placeID"
                ),
                ''
            ) AS filepath,
            b."AddDate"
        FROM "Bookmark" b
        JOIN places p ON p."placeID" = b."PlaceID"
        LEFT JOIN "PlaceCategory" pc ON pc."PlaceID" = p."placeID"
        WHERE b."UserID" = :userId
        ORDER BY b."AddDate" DESC
        """, nativeQuery = true)
    List<Object[]> findUserBookmarks(@Param("userId") int userId);

    @Query(value = """
        SELECT EXISTS (
            SELECT 1
            FROM "Bookmark"
            WHERE "PlaceID" = :placeId AND "UserID" = :userId
        )
        """, nativeQuery = true)
    boolean existsBookmark(@Param("placeId") int placeId, @Param("userId") int userId);
}