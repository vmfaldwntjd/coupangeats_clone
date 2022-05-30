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
        return getRestaurantSortByQuery + "\n" + joinCategoryIdQuery +"\n"+"WHERE distance < 3000\n" +
                "ORDER BY "+sortBy+" "+orderBy+"\n" +
                "LIMIT ?;";
    }

    public static String getRestaurantListQuery(String sortBy, String orderBy){
        return getRestaurantSortByQuery +"\n"+"WHERE distance < 3000\n" +
                "ORDER BY "+sortBy+" "+orderBy+"\n" +
                "LIMIT ?;";
    }


    // 현재 위치를 기준으로 세네번째 파라미터에 들어가는 string 값에 따라 최대 상위 45개의 값 출력.
    // 가까운순(distance, ASC), 별점 높은 순(star_point, DESC), 신규 매장순(created_at, DESC).
    public static String getRestaurantSortByQuery =
            "SELECT R.restaurant_id,\n" +
            "       created_at,\n" +
            "       res_name,\n" +
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


    // 가게별 메인 화면. 가게별 id를 통한 단 하나의 가게 조회.
    public static String getRestaurantByIdQuery = "SELECT R.restaurant_id,\n" +
            "                   created_at,\n" +
            "                   res_name,\n" +
            "                   is_cheetah,\n" +
            "                   delivery_time,\n" +
            "                   IFNULL(star_point, 0) as star_point,\n" +
            "                   IFNULL(review_count, 0) as review_count,\n" +
            "                   round(distance/1000, 1) as distance,\n" +
            "                   min_delivery_fee\n" +
            "            FROM (\n" +
            "                SELECT restaurant_id,\n" +
            "                       created_at,\n" +
            "                        restaurant_name as res_name,\n" +
            "                        is_cheetah,\n" +
            "                        ST_Distance_Sphere(POINT(?,?), POINT(longitude, latitude)) as distance,\n" +
            "                        delivery_time\n" +
            "                FROM restaurant\n" +
            "                WHERE restaurant_id = ?\n" +
            "            ) R\n" +
            "            left join (\n" +
            "                SELECT restaurant_id,\n" +
            "                       AVG(star_point) as star_point,\n" +
            "                       COUNT(restaurant_id) as review_count\n" +
            "                FROM review\n" +
            "                GROUP BY restaurant_id\n" +
            "            ) RV ON R.restaurant_id = RV.restaurant_id\n" +
            "            join (\n" +
            "                SELECT restaurant_id,\n" +
            "                    MIN(delivery_fee) as min_delivery_fee\n" +
            "                FROM res_delivery_fee\n" +
            "                GROUP BY restaurant_id\n" +
            "            ) RDF ON R.restaurant_id = RDF.restaurant_id;";

    public static String getResImageUrlByIdQuery = "SELECT url as res_image_Url\n" +
            "FROM res_image\n" +
            "WHERE restaurant_id = ? AND hide_flag = 0\n" +
            "ORDER BY image_id ASC;";

    public static String getResKindQuery = "SELECT kind_id,\n" +
            "       res_kind_name as kind_name\n" +
            "FROM res_kind\n" +
            "WHERE restaurant_id = ? AND status = 1\n" +
            "ORDER BY kind_id ASC;";

    public static String getResKindMenuQuery = "SELECT kind_id,\n" +
            "       kind_name,\n" +
            "       RM.menu_id,\n" +
            "       menu_name,\n" +
            "       menu_price,\n" +
            "       menu_description,\n" +
            "       url as menu_image_url\n" +
            "FROM (\n" +
            "    SELECT restaurant_id,\n" +
            "           res_menu_id,\n" +
            "           menu_id,\n" +
            "           name as menu_name,\n" +
            "           description as menu_description,\n" +
            "           price as menu_price\n" +
            "     FROM res_menu\n" +
            "    WHERE restaurant_id = ?\n" +
            ") RM\n" +
            "join (\n" +
            "    SELECT res_kind_id,\n" +
            "           menu_id\n" +
            "    FROM res_menu_kind\n" +
            ") RMK ON RMK.menu_id = RM.menu_id\n" +
            "join (\n" +
            "    SELECT res_kind_id,\n" +
            "           res_kind_name as kind_name,\n" +
            "           kind_id\n" +
            "    FROM res_kind\n" +
            ") RK ON RMK.res_kind_id = RK.res_kind_id\n" +
            "left join (\n" +
            "    SELECT res_menu_id,\n" +
            "           url\n" +
            "    FROM res_menu_image\n" +
            "    WHERE image_id = 1\n" +
            ") RMI ON RM.res_menu_id = RMI.res_menu_id\n" +
            "ORDER BY kind_id, menu_id ASC;";


    public static String getResMenuQuery = "SELECT\n" +
            "menu_id,\n" +
            "       name as menu_name,\n" +
            "       price as menu_price,\n" +
            "       description as menu_description\n" +
            "From res_menu\n" +
            "WHERE restaurant_id = ? AND menu_id = ?;\n";

    public static String getResMenuImageUrlListQuery ="SELECT menu_image_url, image_id\n" +
            "FROM (\n" +
            "    SELECT res_menu_id\n" +
            "    FROM res_menu\n" +
            "    WHERE restaurant_id = ? AND menu_id = ?\n" +
            "    ) RM\n" +
            "JOIN (\n" +
            "    SELECT res_menu_id,\n" +
            "           image_id,\n" +
            "           url as menu_image_url\n" +
            "    FROM res_menu_image\n" +
            ") RMI ON RM.res_menu_id = RMI.res_menu_id\n" +
            "ORDER BY image_id";

    public static String getResMenuOptionQuery = "SELECT RO.option_id,\n" +
            "       option_name,\n" +
            "       is_optional,\n" +
            "       res_option_id\n" +
            "FROM (SELECT res_menu_id\n" +
            "    FROM res_menu\n" +
            "    WHERE restaurant_id =? AND menu_id=?\n" +
            ") RM join\n" +
            "     ( SELECT res_menu_id,\n" +
            "              option_id\n" +
            "       FROM res_menu_option\n" +
            "     ) RMO ON RM.res_menu_id =RMO.res_menu_id\n" +
            "join (\n" +
            "    SELECT res_option_id,\n" +
            "           option_id,\n" +
            "    option_name,\n" +
            "    is_optional\n" +
            "    FROM res_option\n" +
            ") RO ON RMO.option_id = RO.option_id;";

    public static String getResMenuOptionListQuery = "SELECT res_option_id,\n" +
            "       option_list_name,\n" +
            "       option_price as option_list_price\n" +
            "FROM res_option_list\n" +
            "WHERE res_option_id = ?";
}
