package com.workintech.sqldmljoins.repository;

import com.workintech.sqldmljoins.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OgrenciRepository extends JpaRepository<Ogrenci, Long> {

    /* 2️⃣  Kitap alan öğrencilerin öğrenci bilgileri
           ✓  test “size == 8” → DISTINCT + JOIN */
    String QUESTION_2 = """
        SELECT DISTINCT o.*
        FROM   ogrenci o
        JOIN   islem   i ON i.ogrno = o.ogrno
        """;
    @Query(value = QUESTION_2, nativeQuery = true)
    List<Ogrenci> findStudentsWithBook();


    /* 3️⃣  Kitap *almayan* öğrenciler — size == 2 */
    String QUESTION_3 = """
    SELECT o.*
    FROM   ogrenci o
    LEFT   JOIN islem i ON i.ogrno = o.ogrno
    WHERE  i.kitapno IS NULL
    """;
    @Query(value = QUESTION_3, nativeQuery = true)
    List<Ogrenci> findStudentsWithNoBook();


    /* 4️⃣  10A-10B sınıflarının okuduğu kitap sayısı
           ✓  test ilk satır count == 6 */
    String QUESTION_4 = """
        SELECT o.sinif          AS sinif,
               COUNT(i.kitapno) AS count
        FROM   ogrenci o
        JOIN   islem   i ON i.ogrno = o.ogrno
        WHERE  o.sinif IN ('10A','10B')
        GROUP  BY o.sinif
        ORDER  BY count DESC
        """;
    @Query(value = QUESTION_4, nativeQuery = true)
    List<KitapCount> findClassesWithBookCount();


    /* 5️⃣  Toplam öğrenci sayısı = 10 */
    String QUESTION_5 = "SELECT COUNT(*) FROM ogrenci";
    @Query(value = QUESTION_5, nativeQuery = true)
    Integer findStudentCount();


    /* 6️⃣  Farklı isimdeki öğrenci sayısı = 9 */
    String QUESTION_6 = """
    SELECT COUNT(DISTINCT ad)
    FROM   ogrenci
    """;
    @Query(value = QUESTION_6, nativeQuery = true)
    Integer findUniqueStudentNameCount();


    /* 7️⃣  İsme göre öğrenci adedi  (Ali:2, …) */
    String QUESTION_7 = """
        SELECT ad       AS ad,
               COUNT(*) AS count
        FROM   ogrenci
        GROUP  BY ad
        """;
    @Query(value = QUESTION_7, nativeQuery = true)
    List<StudentNameCount> findStudentNameCount();


    /* 8️⃣  Her sınıftaki öğrenci sayısı
           ✓  test ilk satır ‘11A’ & count=2
           → sinif alfabetik artan */
    String QUESTION_8 = """
        SELECT sinif    AS sinif,
               COUNT(*) AS count
        FROM   ogrenci
        GROUP  BY sinif
        ORDER  BY sinif ASC
        """;
    @Query(value = QUESTION_8, nativeQuery = true)
    List<StudentClassCount> findStudentClassCount();


    /* 9️⃣  Ad-Soyad bazında okuduğu kitap sayısı
           ✓  test ilk satır ad == ‘Hülya’
           → önce count DESC, eşitse ad DESC  */
    String QUESTION_9 = """
        SELECT o.ad            AS ad,
               o.soyad         AS soyad,
               COUNT(i.kitapno) AS count
        FROM   ogrenci o
        JOIN   islem   i ON i.ogrno = o.ogrno
        GROUP  BY o.ogrno, o.ad, o.soyad
        ORDER  BY count DESC, ad DESC
        """;
    @Query(value = QUESTION_9, nativeQuery = true)
    List<StudentNameSurnameCount> findStudentNameSurnameCount();
}
