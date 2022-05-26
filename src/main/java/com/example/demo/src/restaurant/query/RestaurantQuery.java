package com.example.demo.src.restaurant.query;


public class RestaurantQuery {

    public static String getFNRestaurantListQuery = "SELECT R.restaurant_id,\n" +
            "       res_name,\n" +
            "       res_image_url,\n" +
            "       delivery_time,\n" +
            "       IFNULL(star_point, 0) as star_point,\n" +
            "       IFNULL(review_count, 0) as review_count,\n" +
            "       round(distance/1000, 1) as distance,\n" +
            "       min_delivery_fee\n" +
            "FROM (\n" +
            "    SELECT restaurant_id,\n" +
            "            restaurant_name as res_name,\n" +
            "            ST_Distance_Sphere(POINT(?,?), POINT(longitude, latitude)) as distance,\n" +
            "            delivery_time\n" +
            "    FROM restaurant\n" +
            ") R\n" +
            "left join (\n" +
            "    SELECT restaurant_id,\n" +
            "           AVG(star_point) as star_point,\n" +
            "           COUNT(restaurant_id) as review_count\n" +
            "    FROM review\n" +
            "    GROUP BY restaurant_id\n" +
            ") RV ON R.restaurant_id = RV.restaurant_id\n" +
            "join (\n" +
            "    SELECT restaurant_id,\n" +
            "        MIN(delivery_fee) as min_delivery_fee\n" +
            "    FROM res_delivery_fee\n" +
            "    GROUP BY restaurant_id\n" +
            ") RDF ON R.restaurant_id = RDF.restaurant_id\n" +
            "join (\n" +
            "    SELECT restaurant_id,\n" +
            "           url as res_image_url\n" +
            "    FROM res_image\n" +
            "    WHERE image_id = 1\n" +
            ") I ON R.restaurant_id = I.restaurant_id\n" +
            "join (\n" +
            "    SELECT restaurant_id,\n" +
            "           category_id\n" +
            "    FROM res_category\n" +
            "    WHERE category_id = ?\n" +
            ") RC ON R.restaurant_id = RC.restaurant_id\n" +
            "WHERE distance < 3000\n" +
            "ORDER BY star_point DESC \n" +
            "LIMIT 10;";



    public static String getRestaurantListByCategoryIdQuery(String sortBy, String orderBy){
        return getRestaurantSortByQuery + "\n" + joinMainImageQuery + "\n" + joinCategoryIdQuery +"\n"+"WHERE distance < 3000\n" +
                "ORDER BY "+sortBy+" "+orderBy+"\n" +
                "LIMIT ?;";
    }

    public static String getRestaurantListQuery(String sortBy, String orderBy){
        return getRestaurantSortByQuery + "\n" + joinMainImageQuery +"\n"+"WHERE distance < 3000\n" +
                "ORDER BY "+sortBy+" "+orderBy+"\n" +
                "LIMIT ?;";
    }


    // 현재 위치를 기준으로 세네번째 파라미터에 들어가는 string 값에 따라 최대 상위 45개의 값 출력.
    // 가까운순(distance, ASC), 별점 높은 순(star_point, DESC), 신규 매장순(created_at, DESC).
    public static String getRestaurantSortByQuery =
            "SELECT R.restaurant_id,\n" +
            "       created_at,\n" +
            "       res_name,\n" +
            "       res_image_url,\n" +
            "       is_cheetah,\n" +
            "       delivery_time,\n" +
            "       IFNULL(star_point, 0) as star_point,\n" +
            "       IFNULL(review_count, 0) as review_count,\n" +
            "       round(distance/1000, 1) as distance,\n" +
            "       min_delivery_fee\n" +
            "FROM (\n" +
            "    SELECT restaurant_id,\n" +
            "           created_at,\n" +
            "            restaurant_name as res_name,\n" +
            "            is_cheetah,\n" +
            "            ST_Distance_Sphere(POINT(?,?), POINT(longitude, latitude)) as distance,\n" +
            "            delivery_time\n" +
            "    FROM restaurant\n" +
            ") R\n" +
            "left join (\n" +
            "    SELECT restaurant_id,\n" +
            "           AVG(star_point) as star_point,\n" +
            "           COUNT(restaurant_id) as review_count\n" +
            "    FROM review\n" +
            "    GROUP BY restaurant_id\n" +
            ") RV ON R.restaurant_id = RV.restaurant_id\n" +
            "join (\n" +
            "    SELECT restaurant_id,\n" +
            "        MIN(delivery_fee) as min_delivery_fee\n" +
            "    FROM res_delivery_fee\n" +
            "    GROUP BY restaurant_id\n" +
            ") RDF ON R.restaurant_id = RDF.restaurant_id";

    // 카테고리별 검색시 덧붙여 사용.
    public static String joinCategoryIdQuery = "join (\n" +
            "    SELECT restaurant_id,\n" +
            "           category_id\n" +
            "    FROM res_category\n" +
            "    WHERE category_id = ?\n" +
            ") RC ON R.restaurant_id = RC.restaurant_id";

//    // 왜인지 적용이 안됨.
//    // 카테고리별 화면 = LIMIT 100, 메인 화면 LIMIT = 45
//    public static String commonQuery = "WHERE distance < 3000\n" +
//            "ORDER BY ? ?\n" +
//            "LIMIT ?;";

    // 대표이미지 조회시 덧붙여 사용
    public static String joinMainImageQuery = "join (\n" +
            "    SELECT restaurant_id,\n" +
            "           url as res_image_url\n" +
            "    FROM res_image\n" +
            "    WHERE image_id = 1 \n" +
            ") I ON R.restaurant_id = I.restaurant_id";

}
