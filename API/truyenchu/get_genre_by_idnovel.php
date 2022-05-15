<?php
require "connect.php";
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $idNovel = $_POST['IDNovel'];
    $query = "SELECT ID, Genre_Name FROM genre , novel_genres WHERE novel_genres.ID_Novel = '$idNovel' AND novel_genres.ID_Genre = genre.ID";
    $result = mysqli_query($connect, $query);
    $total_records = mysqli_num_rows($result);

    if ($total_records > 0) {
        while ($row = mysqli_fetch_assoc($result)) {
            $temp_array[] = $row;
        }
    }
    echo json_encode(["genres" => $temp_array], JSON_UNESCAPED_UNICODE);
    mysqli_close($connect);
}
