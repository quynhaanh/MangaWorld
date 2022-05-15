<?php
require "connect.php";
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $type = $_POST['Type'];

    if ($type == "count") {
        $query = "SELECT COUNT(ID_Novel) AS Count, Genre_Name 
        FROM novel_genres, genre WHERE ID_Genre = ID GROUP BY Genre_Name";
    }
    if ($type == "sum") {
        $query = "SELECT SUM(View) AS Count, Genre_Name FROM novel n, novel_genres ng, genre g
        WHERE ng.ID_Genre = g.ID AND ng.ID_Novel = n.ID GROUP BY Genre_Name";
    }

    $result = mysqli_query($connect, $query);
    $total_records = mysqli_num_rows($result);

    if ($total_records > 0) {
        while ($row = mysqli_fetch_assoc($result)) {
            $temp_array[] = $row;
        }
    }
    echo json_encode(["statistics" => $temp_array], JSON_UNESCAPED_UNICODE);

    mysqli_close($connect);
}
